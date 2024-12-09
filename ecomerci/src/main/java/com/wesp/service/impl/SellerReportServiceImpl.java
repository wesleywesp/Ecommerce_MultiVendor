package com.wesp.service.impl;


import com.wesp.model.Seller;
import com.wesp.model.SellerReport;
import com.wesp.repository.SellerReportRepository;
import com.wesp.service.SellerReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerReportServiceImpl implements SellerReportService {
    private final SellerReportRepository sellerReportRepository;
    @Override
    public SellerReport getSellerReport(Seller sellerId) {
        SellerReport sellerReport = sellerReportRepository.findBySellerId(sellerId).orElse(null);
        if (sellerReport == null) {
            SellerReport sellerReport1 = new SellerReport();
            sellerReport1.setSeller(sellerId);
            return sellerReportRepository.save(sellerReport1);
        }
        return sellerReport;


    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return sellerReportRepository.save(sellerReport);
    }
}
