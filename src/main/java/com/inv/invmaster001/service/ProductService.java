package com.inv.invmaster001.service;

import com.inv.invmaster001.dto.request.product.CreateProductRequest;
import com.inv.invmaster001.dto.request.product.MaterialRequest;
import com.inv.invmaster001.dto.request.product.UpdateProductRequest;
import com.inv.invmaster001.dto.response.product.MaterialResponse;
import com.inv.invmaster001.dto.response.product.ProductFullResponse;
import com.inv.invmaster001.dto.response.product.ProductResponse;
import com.inv.invmaster001.entity.Company;
import com.inv.invmaster001.entity.Material;
import com.inv.invmaster001.entity.MaterialPriceHistory;
import com.inv.invmaster001.entity.Product;
import com.inv.invmaster001.entity.ProductPriceHistory;
import com.inv.invmaster001.exception.CompanyNotFoundException;
import com.inv.invmaster001.exception.ProductNotFoundException;
import com.inv.invmaster001.repository.CompanyRepository;
import com.inv.invmaster001.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;

    // =========================================================
    // GET ALL PRODUCTS
    // =========================================================

    public List<ProductFullResponse> getAllProducts(Long companyId) {

        return productRepository
                .findByCompanyIdAndDeletedAtIsNull(companyId)
                .stream()
                .map(this::mapToFullResponse)
                .toList();
    }

    // =========================================================
    // CREATE PRODUCT
    // =========================================================

    public ProductResponse createProduct(
            CreateProductRequest request,
            Long companyId) {

        Company company =
                companyRepository.findById(companyId)
                        .orElseThrow(() ->
                                new CompanyNotFoundException(
                                        "Company not found"));

        boolean productExists =
                productRepository
                        .existsByCompanyIdAndProductNameIgnoreCaseAndDeletedAtIsNull(
                                companyId,
                                request.getProductName());

        if (productExists) {
            throw new RuntimeException("Product already exists");
        }

        Product product = new Product();

        product.setCompany(company);
        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setHsnCode(request.getHsnCode());
        product.setActive(true);
        // ==========================================
        // ADD MATERIALS
        // ==========================================

        if (request.getMaterials() != null) {

            for (MaterialRequest req : request.getMaterials()) {

                Material material = new Material();

                material.setMaterialName(req.getMaterialName());
                material.setUnit(req.getUnit());
                material.setCurrentPrice(req.getCurrentPrice());
                material.setActive(true);

                product.addMaterial(material);

                addNewMaterialPriceVersion(
                        material,
                        req.getCurrentPrice()
                );
            }
        }

        // ==========================================
        // CALCULATE PRICING
        // ==========================================

        BigDecimal manufacturingCost =
                calculateManufacturingCost(
                        product,
                        request.getLabourCharges()
                );

        BigDecimal sellingPrice =
                calculateSellingPrice(
                        manufacturingCost,
                        request.getProfitMargin()
                );

        // ==========================================
        // CREATE PRODUCT PRICE HISTORY
        // ==========================================

        addNewProductPriceVersion(
                product,
                manufacturingCost,
                request.getLabourCharges(),
                sellingPrice,
                request.getProfitMargin()
        );

        Product saved = productRepository.save(product);

        return ProductResponse.builder()
                .id(saved.getId())
                .message("Product created successfully")
                .build();
    }
    // =========================================================
    // UPDATE PRODUCT
    // =========================================================

    public ProductResponse updateProduct(
            Long productId,
            UpdateProductRequest request,
            Long companyId) {

        Product product =
                productRepository
                        .findByIdAndCompanyIdAndDeletedAtIsNull(
                                productId,
                                companyId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found"));

        boolean duplicateProduct =
                productRepository
                        .existsByCompanyIdAndProductNameIgnoreCaseAndDeletedAtIsNullAndIdNot(
                                companyId,
                                request.getProductName(),
                                productId);

        if (duplicateProduct) {
            throw new RuntimeException("Product already exists");
        }

        product.setProductName(request.getProductName());
        product.setDescription(request.getDescription());
        product.setHsnCode(request.getHsnCode());

        // ==========================================
        // UPDATE MATERIALS
        // ==========================================

        syncMaterials(
                product,
                request.getMaterials()
        );

        // ==========================================
        // RECALCULATE PRICING
        // ==========================================

        BigDecimal manufacturingCost =
                calculateManufacturingCost(
                        product,
                        request.getLabourCharges()
                );

        BigDecimal sellingPrice =
                calculateSellingPrice(
                        manufacturingCost,
                        request.getProfitMargin()
                );

        ProductPriceHistory latestPrice =
                product.getPriceHistory()
                        .stream()
                        .filter(history ->
                                history.getEffectiveTo() == null)
                        .findFirst()
                        .orElse(null);

        boolean priceChanged =
                latestPrice == null
                        || latestPrice.getManufacturingCost().compareTo(manufacturingCost) != 0
                        || latestPrice.getLabourCharges().compareTo(request.getLabourCharges()) != 0
                        || latestPrice.getProfitMargin().compareTo(request.getProfitMargin()) != 0
                        || latestPrice.getSellingPrice().compareTo(sellingPrice) != 0;

        if (priceChanged) {

            addNewProductPriceVersion(
                    product,
                    manufacturingCost,
                    request.getLabourCharges(),
                    sellingPrice,
                    request.getProfitMargin()
            );
        }

        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .message("Product updated successfully")
                .build();
    }
    // =========================================================
    // DELETE PRODUCT
    // =========================================================

    public void deleteProduct(
            Long productId,
            Long companyId) {

        Product product =
                productRepository
                        .findByIdAndCompanyIdAndDeletedAtIsNull(
                                productId,
                                companyId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found"));

        product.setActive(false);
        product.setDeletedAt(LocalDateTime.now());

        product.getMaterials()
                .stream()
                .filter(Material::getActive)
                .forEach(material -> {

                    material.setActive(false);
                    material.setDeletedAt(LocalDateTime.now());

                });

        productRepository.save(product);
    }
    // =========================================================
    // CALCULATE MANUFACTURING COST
    // =========================================================

    private BigDecimal calculateManufacturingCost(
            Product product,
            BigDecimal labourCharges) {

        BigDecimal materialCost =
                product.getMaterials()
                        .stream()
                        .filter(Material::getActive)
                        .map(Material::getCurrentPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        return materialCost.add(labourCharges);
    }


    // =========================================================
    // CALCULATE SELLING PRICE
    // =========================================================

    private BigDecimal calculateSellingPrice(
            BigDecimal manufacturingCost,
            BigDecimal profitMargin) {

        if (profitMargin == null) {
            return manufacturingCost;
        }

        return manufacturingCost.add(
                manufacturingCost
                        .multiply(profitMargin)
                        .divide(
                                BigDecimal.valueOf(100),
                                2,
                                RoundingMode.HALF_UP
                        )
        );
    }


    // =========================================================
    // PRODUCT PRICE HISTORY
    // =========================================================

    private void addNewProductPriceVersion(
            Product product,
            BigDecimal manufacturingCost,
            BigDecimal labourCharges,
            BigDecimal sellingPrice,
            BigDecimal profitMargin) {

        product.getPriceHistory()
                .stream()
                .filter(history -> history.getEffectiveTo() == null)
                .findFirst()
                .ifPresent(history ->
                        history.setEffectiveTo(LocalDate.now()));

        ProductPriceHistory history =
                ProductPriceHistory.builder()
                        .product(product)
                        .manufacturingCost(manufacturingCost)
                        .labourCharges(labourCharges)
                        .sellingPrice(sellingPrice)
                        .profitMargin(profitMargin)
                        .effectiveFrom(LocalDate.now())
                        .effectiveTo(null)
                        .build();

        product.addPriceHistory(history);
    }
    // =========================================================
    // MATERIAL SYNC
    // =========================================================

    private void syncMaterials(
            Product product,
            List<MaterialRequest> requests) {

        if (requests == null || requests.isEmpty()) {
            return;
        }

        Map<Long, Material> existingMaterials =
                product.getMaterials()
                        .stream()
                        .filter(material ->
                                material.getId() != null &&
                                        Boolean.TRUE.equals(material.getActive()))
                        .collect(Collectors.toMap(
                                Material::getId,
                                material -> material
                        ));

        Set<Long> incomingIds = new HashSet<>();

        for (MaterialRequest request : requests) {

            // =====================================================
            // NEW MATERIAL
            // =====================================================

            if (request.getId() == null) {

                Material material = new Material();

                material.setMaterialName(request.getMaterialName());
                material.setUnit(request.getUnit());
                material.setCurrentPrice(request.getCurrentPrice());
                material.setActive(true);

                product.addMaterial(material);

                addNewMaterialPriceVersion(
                        material,
                        request.getCurrentPrice()
                );

                continue;
            }

            incomingIds.add(request.getId());

            Material material =
                    existingMaterials.get(request.getId());

            if (material == null) {

                throw new RuntimeException(
                        "Material not found: " + request.getId()
                );
            }

            material.setMaterialName(request.getMaterialName());
            material.setUnit(request.getUnit());

            boolean priceChanged =
                    material.getCurrentPrice() == null
                            || material.getCurrentPrice()
                            .compareTo(request.getCurrentPrice()) != 0;

            if (priceChanged) {

                material.setCurrentPrice(
                        request.getCurrentPrice()
                );

                addNewMaterialPriceVersion(
                        material,
                        request.getCurrentPrice()
                );
            }
        }

        // =====================================================
        // SOFT DELETE REMOVED MATERIALS
        // =====================================================

        product.getMaterials()
                .stream()
                .filter(material ->
                        Boolean.TRUE.equals(material.getActive())
                                && material.getId() != null
                                && !incomingIds.contains(material.getId()))
                .forEach(material -> {

                    material.setActive(false);
                    material.setDeletedAt(LocalDateTime.now());

                });
    }
    // =========================================================
    // MATERIAL PRICE HISTORY
    // =========================================================

    private void addNewMaterialPriceVersion(
            Material material,
            BigDecimal price) {

        material.getPriceHistory()
                .stream()
                .filter(history ->
                        history.getEffectiveTo() == null)
                .findFirst()
                .ifPresent(history ->
                        history.setEffectiveTo(LocalDate.now()));

        MaterialPriceHistory history =
                MaterialPriceHistory.builder()
                        .material(material)
                        .price(price)
                        .effectiveFrom(LocalDate.now())
                        .effectiveTo(null)
                        .build();

        material.addPriceHistory(history);
    }
    // =========================================================
    // RESPONSE MAPPER
    // =========================================================

    private ProductFullResponse mapToFullResponse(
            Product product) {

        ProductPriceHistory latestPrice =
                product.getPriceHistory()
                        .stream()
                        .filter(history ->
                                history.getEffectiveTo() == null)
                        .findFirst()
                        .orElse(null);

        List<MaterialResponse> materials =
                product.getMaterials()
                        .stream()
                        .filter(Material::getActive)
                        .map(material ->
                                MaterialResponse.builder()
                                        .materialId(material.getId())
                                        .materialName(material.getMaterialName())
                                        .unit(material.getUnit())
                                        .currentPrice(material.getCurrentPrice())
                                        .build()
                        )
                        .toList();

        return ProductFullResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .active(product.getActive())
                .hsnCode(product.getHsnCode())
                .manufacturingCost(
                        latestPrice != null
                                ? latestPrice.getManufacturingCost()
                                : BigDecimal.ZERO
                )
                .labourCharges(
                        latestPrice != null
                                ? latestPrice.getLabourCharges()
                                : BigDecimal.ZERO
                )
                .sellingPrice(
                        latestPrice != null
                                ? latestPrice.getSellingPrice()
                                : BigDecimal.ZERO
                )
                .profitMargin(
                        latestPrice != null
                                ? latestPrice.getProfitMargin()
                                : BigDecimal.ZERO
                )
                .materials(materials)
                .build();
    }
}