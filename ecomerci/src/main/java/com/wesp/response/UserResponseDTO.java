package com.wesp.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wesp.domain.USER_ROLE;
import com.wesp.model.Address;
import com.wesp.model.Coupon;
import com.wesp.request.SignupRequestDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

public record UserResponseDTO(Long id,
                              String password,
                              String email,
                              String name,
                              String lastName,
                              String phone) {

}
