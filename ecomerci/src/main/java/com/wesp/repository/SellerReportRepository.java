package com.wesp.repository;

import com.wesp.model.Seller;
import com.wesp.model.SellerReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
    Optional<SellerReport> findBySellerId(Seller sellerId);
}
