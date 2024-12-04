package com.wesp.service;

import com.wesp.domain.AccountStatus;
import com.wesp.infra.exception.SellerException;
import com.wesp.model.Seller;
import com.wesp.request.SellerRequestDTO;
import com.wesp.request.UpdateSellerRequestDTO;

import java.util.List;

public interface SellerService {
    Seller getSellerProfile(String jwtToken) throws SellerException;
    Seller createSellerProfile(SellerRequestDTO seller) throws SellerException;
    Seller getSellerById(Long id) throws SellerException;
    Seller getSellerByEmail(String email) throws SellerException;
    List<Seller> getAllSellers(AccountStatus status);
    Seller updateSeller(Long id, UpdateSellerRequestDTO seller) throws SellerException;
    void deleteSeller(Long id) throws SellerException;
    void desativaSeller(Long id) throws SellerException;
    Seller verifyEmail(String email, String otp) throws SellerException;
    Seller updateSellerAccountStatus(Long sellerid, AccountStatus status) throws SellerException;



    Seller updatepassword(Long id, String password) throws SellerException;


}
