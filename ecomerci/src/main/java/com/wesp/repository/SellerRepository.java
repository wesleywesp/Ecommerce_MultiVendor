package com.wesp.repository;

import com.wesp.domain.AccountStatus;
import com.wesp.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus accountStatus);

    List<Seller> findByAccountStatusAndIsActiveIsTrue(AccountStatus status);

}
