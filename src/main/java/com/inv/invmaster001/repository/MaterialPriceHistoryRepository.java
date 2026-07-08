package com.inv.invmaster001.repository;

import com.inv.invmaster001.entity.Material;
import com.inv.invmaster001.entity.MaterialPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MaterialPriceHistoryRepository extends JpaRepository<MaterialPriceHistory, Long> {

    Optional<MaterialPriceHistory> findFirstByMaterialOrderByEffectiveFromDesc(Material material);

}