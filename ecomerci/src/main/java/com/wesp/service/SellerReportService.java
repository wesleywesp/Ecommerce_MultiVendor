package com.wesp.service;

import com.wesp.model.Seller;
import com.wesp.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller sellerId);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
