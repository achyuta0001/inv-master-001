package com.inv.invmaster001.repository;

import com.inv.invmaster001.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCompanyIdAndDeletedAtIsNull(Long companyId);

    boolean existsByCompanyIdAndProductNameIgnoreCaseAndDeletedAtIsNull(
            Long companyId,
            String productName
    );

    boolean existsByCompanyIdAndProductNameIgnoreCaseAndDeletedAtIsNullAndIdNot(
            Long companyId,
            String productName,
            Long id
    );

    Optional<Product> findByIdAndCompanyIdAndDeletedAtIsNull(
            Long id,
            Long companyId
    );
}