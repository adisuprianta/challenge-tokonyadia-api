package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.dto.request.CustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CustomerResponse;
import com.enigma.challengetokonyadiaapi.entity.Customer;
import com.enigma.challengetokonyadiaapi.repository.CustomerRepository;
import com.enigma.challengetokonyadiaapi.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
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
    public Page<CustomerResponse> findAll(Integer page, Integer size) {
        try {
            PageRequest pageRequest = PageRequest.of(page, size);
            Page<Customer> customerPage = customerRepository.findAll(pageRequest);

            //coba coba page ke list 
            List <CustomerResponse> responses = customerPage.getContent().stream()
                    .map(customer -> {
                        return CustomerResponse.builder()
                                .name(customer.getName())
                                .build();
                    }).collect(Collectors.toList());

//        return all.stream().map(customer -> {
//            return CustomerResponse.builder()
//                    .phoneNumber(customer.getPhoneNumber())
//                    .id(customer.getId())
//                    .name(customer.getName())
//                    .address(customer.getAddress())
//                    .build();
//        }).collect(Collectors.toList());
            return new PageImpl<>(responses,pageRequest,customerPage.getTotalElements());
        }catch (IllegalArgumentException exception){
            throw new IllegalArgumentException("The page index cannot be zero");
        }

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

//    @Transactional(rollbackOn = Exception.class)
    @Override
    public void delete(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "customer tidak ada")
        );
        customerRepository.delete(customer);
    }
}
