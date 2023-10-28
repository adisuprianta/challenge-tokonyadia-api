package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.dto.request.CustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CustomerResponse;
import com.enigma.challengetokonyadiaapi.entity.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponse save(Customer customer);
    CustomerResponse update(CustomerRequest request);
    List<CustomerResponse> findAll();

    CustomerResponse findById(String id);
    void delete(String id);
}
