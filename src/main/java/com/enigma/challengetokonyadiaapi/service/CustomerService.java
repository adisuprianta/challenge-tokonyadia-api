package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.dto.request.UserCustomerRequest;
import com.enigma.challengetokonyadiaapi.entity.Customer;

public interface CustomerService {
    Customer save(Customer customer);
}
