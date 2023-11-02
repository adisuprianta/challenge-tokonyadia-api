package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.dto.request.CustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.request.SearchCustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CustomerResponse;
import com.enigma.challengetokonyadiaapi.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    CustomerResponse save(Customer customer);
    CustomerResponse update(CustomerRequest request);
    Page<CustomerResponse> findAll(SearchCustomerRequest request);

    Customer findById(String id);
    void delete(String id);
}
