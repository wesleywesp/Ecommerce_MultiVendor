package com.wesp.service.impl;

import com.wesp.domain.AccountStatus;
import com.wesp.domain.USER_ROLE;
import com.wesp.infra.exception.SellerException;
import com.wesp.infra.security.TokenService;
import com.wesp.model.Address;
import com.wesp.model.BankDetails;
import com.wesp.model.BusinessDetails;
import com.wesp.model.Seller;
import com.wesp.repository.AddressRepository;
import com.wesp.repository.SellerRepository;
import com.wesp.request.SellerRequestDTO;
import com.wesp.request.UpdateSellerRequestDTO;
import com.wesp.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Seller getSellerProfile(String jwtToken) throws SellerException {
        String email = tokenService.getSubject(jwtToken);
        Seller seller = this.getSellerByEmail(email);
        return seller;
    }

    @Override
    public Seller createSellerProfile(SellerRequestDTO selle) throws SellerException {

        Seller seller = sellerRepository.findByEmail(selle.email()).orElse(null);
        if (seller != null) {
            throw new SellerException("Seller already exists with email: " + selle.email());
        }
        Address newAddress = new Address(selle.pickupAddress());
        Address savedAddress = addressRepository.save(newAddress);
        Seller newSeller = new Seller();
        newSeller.setSellerName(selle.sellerName());
        newSeller.setEmail(selle.email());
        newSeller.setPassword(passwordEncoder.encode(selle.password()));
        newSeller.setPickupAddress(savedAddress);
        newSeller.setSellerPhone(selle.sellerPhone());
        newSeller.setGSTIN(selle.GSTIN());
        BankDetails bankDetails = new BankDetails(selle.bankDetails());
        newSeller.setBankDetails(bankDetails);
        BusinessDetails businessDetails = new BusinessDetails(selle.businessDetails());
        newSeller.setBusinessDetails(businessDetails);
        newSeller.setRole(USER_ROLE.ROLE_SELLER);

        return sellerRepository.save(newSeller);

    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        Seller seller =sellerRepository.findById(id).orElseThrow(() -> new SellerException("Seller not found with id: " + id));
        return seller;
    }

    @Override
    public Seller getSellerByEmail(String email) throws SellerException {
        Seller seller = sellerRepository.findByEmail(email).orElseThrow(() -> new SellerException("Seller not found with email: " + email));
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {

        return sellerRepository.findByAccountStatusAndIsActiveIsTrue(status);
    }

    @Override
    public Seller updateSeller(Long id, UpdateSellerRequestDTO seller) throws SellerException {
        Seller sellerexists = sellerRepository.findById(id).orElseThrow(() -> new SellerException("Seller not found with id: " + id));
    if(seller.sellerName()!=null){
        sellerexists.setSellerName(seller.sellerName());
    }
    if(seller.sellerPhone()!=null){
        sellerexists.setSellerPhone(seller.sellerPhone());
    }
    if(seller.email()!=null){
        sellerexists.setEmail(seller.email());
    }
    if(seller.GSTIN()!=null){
        sellerexists.setGSTIN(seller.GSTIN());
    }
    if(seller.bankDetails()!=null
            && seller.bankDetails().ifscCode()!=null
            && seller.bankDetails().bankName()!=null
            && seller.bankDetails().accountNumber()!=null
            && seller.bankDetails().accountHolderName()!=null
    )
    {
        sellerexists.getBankDetails().setBankName(
                seller.bankDetails().bankName());
        sellerexists.getBankDetails().setAccountHolderName(
                seller.bankDetails().accountHolderName());
        sellerexists.getBankDetails().setAccountNumber(
                seller.bankDetails().accountNumber());
        sellerexists.getBankDetails().setIfscCode(
                seller.bankDetails().ifscCode());
    }
    if(seller.businessDetails()!=null
            && seller.businessDetails().businessName()!=null
            && seller.businessDetails().businessEmail()!=null
            && seller.businessDetails().businessPhone()!=null
            && seller.businessDetails().businessAddress()!=null
            && seller.businessDetails().logo()!=null
            && seller.businessDetails().banner()!=null){

        sellerexists.getBusinessDetails().setBusinessName(
                seller.businessDetails().businessName());
        sellerexists.getBusinessDetails().setBusinessEmail(seller.businessDetails().businessEmail());
        sellerexists.getBusinessDetails().setBusinessPhone(seller.businessDetails().businessPhone());
        sellerexists.getBusinessDetails().setBusinessAddress(seller.businessDetails().businessAddress());
        sellerexists.getBusinessDetails().setLogo(seller.businessDetails().logo());
        sellerexists.getBusinessDetails().setBanner(seller.businessDetails().banner());
    }
    if(seller.accountStatus()!=null){
        sellerexists.setAccountStatus(seller.accountStatus());
    }
    if(seller.pickupAddress()!=null
            && seller.pickupAddress().city()!=null
            && seller.pickupAddress().codePostal()!=null
            && seller.pickupAddress().country()!=null
            && seller.pickupAddress().name()!=null
            && seller.pickupAddress().phone()!=null
            && seller.pickupAddress().state()!=null
            && seller.pickupAddress().street()!=null)
    {

        sellerexists.getPickupAddress().setCity(
                seller.pickupAddress().city());
        sellerexists.getPickupAddress().setCodePostal(
                seller.pickupAddress().codePostal());
        sellerexists.getPickupAddress().setCountry(
                seller.pickupAddress().country());
        sellerexists.getPickupAddress().setName(
                seller.pickupAddress().name());
        sellerexists.getPickupAddress().setPhone(
                seller.pickupAddress().phone());
        sellerexists.getPickupAddress().setState(
                seller.pickupAddress().state());
        sellerexists.getPickupAddress().setStreet(
                seller.pickupAddress().street());


    }
    return sellerRepository.save(sellerexists);
    }

    @Override
    public void deleteSeller(Long id) throws SellerException {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> new SellerException("Seller not found with id: " + id));
        sellerRepository.delete(seller);
    }

    @Override
    public void desativaSeller(Long id) throws SellerException {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> new SellerException("Seller not found with id: " + id));
        seller.desativa();
        sellerRepository.save(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws SellerException {
        Seller seller = sellerRepository.findByEmail(email).orElseThrow(() -> new SellerException("Seller not found with email: " + email));
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerid, AccountStatus status) throws SellerException {
        Seller seller = getSellerById(sellerid);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updatepassword(Long id, String password) throws SellerException {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> new SellerException("Seller not found with id: " + id));
        seller.setPassword(passwordEncoder.encode(password));
        return sellerRepository.save(seller);
    }
}
