package com.wesp.model;

import com.wesp.domain.PaymentMethod;
import com.wesp.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class PaymentOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;

    private PaymentMethod paymentMethod;
    private PaymentOrderStatus status= PaymentOrderStatus.PENDING;
    private String paymentLinkId;

    @ManyToOne
    private User  user;
    @OneToMany
    private Set<Order> order = new HashSet<>();

}
