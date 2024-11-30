package com.wesp.service.impl;

import com.wesp.domain.AccountStatus;
import com.wesp.domain.USER_ROLE;
import com.wesp.infra.security.TokenService;
import com.wesp.model.Address;
import com.wesp.model.BankDetails;
import com.wesp.model.Seller;
import com.wesp.repository.AddressRepository;
import com.wesp.repository.SellerRepository;
import com.wesp.request.SellerRequestDTO;
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
    public Seller createSellerProfile(SellerRequestDTO selle) {

        Seller seller = sellerRepository.findByEmail(selle.email()).orElse(null);
        if (seller != null) {
            throw new BadCredentialsException("Seller already exists with email: " + selle.email());
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
        newSeller.setBusinessDetails(selle.businessDetails());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);

        return sellerRepository.save(newSeller);

    }

    @Override
    public Seller getSellerById(Long id) {
        Seller seller =sellerRepository.findById(id).orElseThrow(() -> new BadCredentialsException("Seller not found with id: " + id));
        return seller;
    }

    @Override
    public Seller getSellerByEmail(String email) {
        Seller seller = sellerRepository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("Seller not found with email: " + email));
        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) {

        return sellerRepository.findByAccountStatusAndIsActiveIsTrue(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) {
        Seller sellerexists = sellerRepository.findById(id).orElseThrow(() -> new BadCredentialsException("Seller not found with id: " + id));
    if(seller.getSellerName()!=null){
        sellerexists.setSellerName(seller.getSellerName());
    }
    if(seller.getSellerPhone()!=null){
        sellerexists.setSellerPhone(seller.getSellerPhone());
    }
    if(seller.getEmail()!=null){
        sellerexists.setEmail(seller.getEmail());
    }
    if(seller.getGSTIN()!=null){
        sellerexists.setGSTIN(seller.getGSTIN());
    }
    if(seller.getBankDetails()!=null
            && seller.getBankDetails().getIfscCode()!=null
            && seller.getBankDetails().getBankName()!=null
            && seller.getBankDetails().getAccountNumber()!=null
            && seller.getBankDetails().getAccountHolderName()!=null
    )
    {
        sellerexists.getBankDetails().setBankName(
                seller.getBankDetails().getBankName());
        sellerexists.getBankDetails().setAccountHolderName(
                seller.getBankDetails().getAccountHolderName());
        sellerexists.getBankDetails().setAccountNumber(
                seller.getBankDetails().getAccountNumber());
        sellerexists.getBankDetails().setIfscCode(
                seller.getBankDetails().getIfscCode());
    }
    if(seller.getBusinessDetails()!=null
            && seller.getBusinessDetails().getBusinessName()!=null
            && seller.getBusinessDetails().getBusinessEmail()!=null
            && seller.getBusinessDetails().getBusinessPhone()!=null
            && seller.getBusinessDetails().getBusinessAddress()!=null
            && seller.getBusinessDetails().getLogo()!=null
            && seller.getBusinessDetails().getBanner()!=null){

        sellerexists.getBusinessDetails().setBusinessName(
                seller.getBusinessDetails().getBusinessName());
        sellerexists.getBusinessDetails().setBusinessEmail(seller.getBusinessDetails().getBusinessEmail());
        sellerexists.getBusinessDetails().setBusinessPhone(seller.getBusinessDetails().getBusinessPhone());
        sellerexists.getBusinessDetails().setBusinessAddress(seller.getBusinessDetails().getBusinessAddress());
        sellerexists.getBusinessDetails().setLogo(seller.getBusinessDetails().getLogo());
        sellerexists.getBusinessDetails().setBanner(seller.getBusinessDetails().getBanner());
    }
    if(seller.getAccountStatus()!=null){
        sellerexists.setAccountStatus(seller.getAccountStatus());
    }
    if(seller.getPickupAddress()!=null
            && seller.getPickupAddress().getCity()!=null
            && seller.getPickupAddress().getCodePostal()!=null
            && seller.getPickupAddress().getCountry()!=null
            && seller.getPickupAddress().getName()!=null
            && seller.getPickupAddress().getPhone()!=null
            && seller.getPickupAddress().getState()!=null
            && seller.getPickupAddress().getStreet()!=null)
    {

        sellerexists.getPickupAddress().setCity(
                seller.getPickupAddress().getCity());
        sellerexists.getPickupAddress().setCodePostal(
                seller.getPickupAddress().getCodePostal());
        sellerexists.getPickupAddress().setCountry(
                seller.getPickupAddress().getCountry());
        sellerexists.getPickupAddress().setName(
                seller.getPickupAddress().getName());
        sellerexists.getPickupAddress().setPhone(
                seller.getPickupAddress().getPhone());
        sellerexists.getPickupAddress().setState(
                seller.getPickupAddress().getState());
        sellerexists.getPickupAddress().setStreet(
                seller.getPickupAddress().getStreet());


    }
    return sellerRepository.save(sellerexists);
    }

    @Override
    public void deleteSeller(Long id) {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> new BadCredentialsException("Seller not found with id: " + id));
        seller.delete();
        sellerRepository.save(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) {
        Seller seller = sellerRepository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("Seller not found with email: " + email));
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long sellerid, AccountStatus status) {
        Seller seller = getSellerById(sellerid);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updatepassword(Long id, String password) {
        Seller seller = sellerRepository.findById(id).orElseThrow(() -> new BadCredentialsException("Seller not found with id: " + id));
        seller.setPassword(passwordEncoder.encode(password));
        return sellerRepository.save(seller);
    }
}
