//package com.inv.invmaster001.service;
//
//import com.inv.invmaster001.dto.entitydto.ProductDTO;
//import com.inv.invmaster001.entity.Product;
//import com.inv.invmaster001.repository.ProductRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ProductService {
//
//    private final ProductRepository productRepository;
//
//    public ProductDTO saveProduct(ProductDTO productDTO) {
//        Product product = Product.builder()
//                .companyId(productDTO.getCompanyId())
//                .productName(productDTO.getProductName())
//                .description(productDTO.getDescription())
//                .active(productDTO.getActive())
//                .productMaterials(productDTO.getProductMaterials())
//                .priceHistory(productDTO.getPriceHistory())
//                .createdAt(productDTO.getCreatedAt())
//                .updatedAt(productDTO.getUpdatedAt())
//                .deletedAt(productDTO.getDeletedAt())
//                .build();
//        Product saved = productRepository.save(product);
//        return mapToDto(saved);
//    }
//
//    public ProductDTO getProductById(Long id) {
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//        return mapToDto(product);
//    }
//
//    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
//        Product existing = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//        existing.setCompanyId(productDTO.getCompanyId());
//        existing.setProductName(productDTO.getProductName());
//        existing.setDescription(productDTO.getDescription());
//        existing.setActive(productDTO.getActive());
//        existing.setProductMaterials(productDTO.getProductMaterials());
//        existing.setPriceHistory(productDTO.getPriceHistory());
//        existing.setCreatedAt(productDTO.getCreatedAt());
//        existing.setUpdatedAt(productDTO.getUpdatedAt());
//        existing.setDeletedAt(productDTO.getDeletedAt());
//        Product updated = productRepository.save(existing);
//        return mapToDto(updated);
//    }
//
//    public void deleteProduct(Long id) {
//        if (!productRepository.existsById(id)) {
//            throw new RuntimeException("Product not found");
//        }
//        productRepository.deleteById(id);
//    }
//
//    private ProductDTO mapToDto(Product product) {
//        ProductDTO dto = new ProductDTO();
//        dto.setId(product.getId());
//        dto.setCompanyId(product.getCompanyId());
//        dto.setProductName(product.getProductName());
//        dto.setDescription(product.getDescription());
//        dto.setActive(product.getActive());
//        dto.setProductMaterials(product.getProductMaterials());
//        dto.setPriceHistory(product.getPriceHistory());
//        dto.setCreatedAt(product.getCreatedAt());
//        dto.setUpdatedAt(product.getUpdatedAt());
//        dto.setDeletedAt(product.getDeletedAt());
//        return dto;
//    }
//}