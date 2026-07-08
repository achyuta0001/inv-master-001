package com.inv.invmaster001.repository;

import com.inv.invmaster001.entity.ProductPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductPriceHistoryRepository extends JpaRepository<ProductPriceHistory, Long> {

    Optional<ProductPriceHistory> findByProductIdAndEffectiveToIsNull(
            Long productId
    );

}