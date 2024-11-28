package com.wesp.service.impl;

import com.wesp.domain.AccountStatus;
import com.wesp.domain.USER_ROLE;
import com.wesp.infra.security.TokenService;
import com.wesp.model.Address;
import com.wesp.model.Seller;
import com.wesp.repository.AddressRepository;
import com.wesp.repository.SellerRepository;
import com.wesp.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    @Autowired
    private final SellerRepository sellerRepository;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public Seller getSellerProfile(String jwtToken) {
        String email = tokenService.getSubject(jwtToken);
        Seller seller = this.getSellerByEmail(email);
        return seller;
    }

    @Override
    public Seller createSellerProfile(Seller selle) {

        Seller seller = sellerRepository.findByEmail(selle.getEmail()).orElse(null);
        if (seller != null) {
            throw new BadCredentialsException("Seller already exists with email: " + selle.getEmail());
        }
        Address savedAddress = addressRepository.save(selle.getPickupAddress());
        Seller newSeller = new Seller();
        newSeller.setSellerName(selle.getSellerName());
        newSeller.setEmail(selle.getEmail());
        newSeller.setPassword(passwordEncoder.encode(selle.getPassword()));
        newSeller.setPickupAddress(savedAddress);
        newSeller.setSellerPhone(selle.getSellerPhone());
        newSeller.setGSTIN(selle.getGSTIN());
        newSeller.setBankDetails(selle.getBankDetails());
        newSeller.setBusinessDetails(selle.getBusinessDetails());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);

        return sellerRepository.save(newSeller);

    }

    @Override
    public Seller getSellerById(Long id) {
        return null;
    }

    @Override
    public Seller getSellerByEmail(String email) {
        Seller seller = sellerRepository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("Seller not found with email: " + email));
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {
        return List.of();
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) {
        return null;
    }

    @Override
    public void deleteSeller(Long id) {

    }

    @Override
    public Seller verifyEmail(String email, String otp) {
        return null;
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerid, AccountStatus status) {
        return null;
    }

    @Override
    public Seller updatepassword(Long id, String password) {
        return null;
    }
}
