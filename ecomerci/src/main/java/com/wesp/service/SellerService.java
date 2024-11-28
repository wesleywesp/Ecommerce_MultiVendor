package com.wesp.service;

import com.wesp.domain.AccountStatus;
import com.wesp.model.Seller;

import java.util.List;

public interface SellerService {
    Seller getSellerProfile(String jwtToken);
    Seller createSellerProfile(Seller seller);
    Seller getSellerById(Long id);
    Seller getSellerByEmail(String email);
    List<Seller> getAllSellers(AccountStatus status);
    Seller updateSeller(Long id, Seller seller);
    void deleteSeller(Long id);
    Seller verifyEmail(String email, String otp);
    Seller updateSellerAccountStatus(Long sellerid, AccountStatus status);



    Seller updatepassword(Long id, String password);


}
