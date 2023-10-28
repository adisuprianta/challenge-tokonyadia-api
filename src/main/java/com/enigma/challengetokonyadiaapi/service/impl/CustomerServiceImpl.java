package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.dto.request.CustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CustomerResponse;
import com.enigma.challengetokonyadiaapi.entity.Customer;
import com.enigma.challengetokonyadiaapi.repository.CustomerRepository;
import com.enigma.challengetokonyadiaapi.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    @Override
    public CustomerResponse save(Customer customer) {

        customer = customerRepository.save(customer);
        return  CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .build();
    }

    @Override
    public CustomerResponse update(CustomerRequest request) {
        Customer customer = customerRepository.findById(request.getId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "customer tidak ada")
        );
        customer.setName(request.getName());
        customer.setAddress(request.getAddress());
        customer.setPhoneNumber(request.getPhoneNumber());
        customerRepository.save(customer);

        return CustomerResponse.builder()
                .id(customer.getId())
                .address(customer.getAddress())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

    @Override
    public List<CustomerResponse> findAll() {
        List<Customer> all = customerRepository.findAll();

        return all.stream().map(customer -> {
            return CustomerResponse.builder()
                    .phoneNumber(customer.getPhoneNumber())
                    .id(customer.getId())
                    .name(customer.getName())
                    .address(customer.getAddress())
                    .build();
        }).collect(Collectors.toList());
    }


    @Override
    public CustomerResponse findById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "customer tidak ada")
        );

        return CustomerResponse.builder()
                .id(customer.getId())
                .address(customer.getAddress())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }

    @Override
    public void delete(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "customer tidak ada")
        );
        customerRepository.delete(customer);
    }
}
