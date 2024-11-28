package com.wesp.repository;

import com.wesp.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByUserId(Long userId);
    Optional<Address> findByUserIdAndId(Long userId, Long id);
    void deleteByUserId(Long userId);
}
