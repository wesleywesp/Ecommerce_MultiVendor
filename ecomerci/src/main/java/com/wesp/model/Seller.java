package com.wesp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wesp.domain.AccountStatus;
import com.wesp.domain.USER_ROLE;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sellerName;

    private String sellerPhone;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "business_id")),
    })
    private BusinessDetails businessDetails = new BusinessDetails();

    @Enumerated(EnumType.STRING)
    private USER_ROLE role = USER_ROLE.ROLE_SELLER;

    private boolean isEmailVerified = false;

    private boolean isActive = true;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "id", column = @Column(name = "bank_id")),
    })
    private BankDetails bankDetails = new BankDetails();

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pickup_address_id", referencedColumnName = "id")
    private Address pickupAddress;

    @Column(unique = true)
    private String GSTIN;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;


    @PrePersist
    public void prePersist() {
        this.isActive = true;
        this.isEmailVerified = false;
        this.accountStatus = AccountStatus.PENDING_VERIFICATION;
    }

}

