package com.wesp.service.impl;

import com.wesp.domain.USER_ROLE;
import com.wesp.model.Seller;
import com.wesp.model.User;
import com.wesp.repository.SellerRepository;
import com.wesp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class CustumerServicerImpl implements UserDetailsService {


    private final UserRepository userRepository;

    private final SellerRepository sellerRepository;

    private static final String SELLER_PREFIX = "SELLER_";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.startsWith(SELLER_PREFIX)) {
            String sellerUsername = username.substring(SELLER_PREFIX.length());
            Seller seller = sellerRepository.findByEmail(sellerUsername).orElseThrow(()-> new UsernameNotFoundException("Seller not found"));
            return buildUserDetails(seller.getEmail(), seller.getPassword(), seller.getRole());
        }else {
            User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return buildUserDetails(user.getEmail(), user.getPassword(), user.getRole());
        }

    }

    private UserDetails buildUserDetails(String email, String password, USER_ROLE role) {
        // Define o papel padrão como ROLE_CUSTOMER, caso o role seja nulo
        if (role == null) {
            role = USER_ROLE.ROLE_CUSTOMER;
        }

        // Adiciona o papel como uma autoridade com o prefixo "ROLE_"
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority( role.name())
        );

        // Retorna um objeto UserDetails com as credenciais e authorities
        return new org.springframework.security.core.userdetails.User(email, password, authorities);
    }

}
