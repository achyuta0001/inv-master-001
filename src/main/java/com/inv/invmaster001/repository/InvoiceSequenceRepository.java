package com.inv.invmaster001.repository;

import com.inv.invmaster001.entity.InvoiceSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceSequenceRepository extends JpaRepository<InvoiceSequence, Long> {

    Optional<InvoiceSequence> findTopByCompanyIdOrderByIdDesc(Long companyId);

}