package com.enigma.challengetokonyadiaapi.dto.request;

import com.enigma.challengetokonyadiaapi.entity.Customer;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCustomerRequest {
    private Customer customer;
}
