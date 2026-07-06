package com.inv.invmaster001.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inv.invmaster001.entity.Material;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    // Find by product
    java.util.List<Material> findByProductId(Long productId);

    // Find by active status
    java.util.List<Material> findByActive(Boolean active);
}