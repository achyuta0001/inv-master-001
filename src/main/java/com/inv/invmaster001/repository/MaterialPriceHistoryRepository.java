package com.inv.invmaster001.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.inv.invmaster001.entity.MaterialPriceHistory;

@Repository
public interface MaterialPriceHistoryRepository extends JpaRepository<MaterialPriceHistory, Long> {

}