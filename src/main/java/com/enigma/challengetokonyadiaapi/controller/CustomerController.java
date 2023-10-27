package com.enigma.challengetokonyadiaapi.controller;


import com.enigma.challengetokonyadiaapi.entity.Customer;
import com.enigma.challengetokonyadiaapi.entity.UserCredential;
import com.enigma.challengetokonyadiaapi.repository.CustomerRepository;
import com.enigma.challengetokonyadiaapi.repository.UserCredentialRepository;
import com.enigma.challengetokonyadiaapi.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class CustomerController {
    private  final CustomerService customerService;
    private final CustomerRepository customerRepository;
    @PostMapping("/api/customers")
    public Customer createNewCustomer(@RequestBody Customer customer){
        Customer save = customerService.save(customer);
        return save;
    }


//    @GetMapping("/test")
//    public List<Customer> getAll(){
//        return customerRepository.findAll();
//    }


}
