package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.dto.request.UserCustomerRequest;
import com.enigma.challengetokonyadiaapi.entity.Customer;
import com.enigma.challengetokonyadiaapi.entity.UserCredential;
import com.enigma.challengetokonyadiaapi.repository.CustomerRepository;
import com.enigma.challengetokonyadiaapi.repository.UserCredentialRepository;
import com.enigma.challengetokonyadiaapi.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {


    private final CustomerRepository customerRepository;
    private final UserCredentialRepository userCredentialRepository;
    @Override
    public Customer save(Customer customer) {

        if (customer.getUserCredential().getId()!=null){
            UserCredential userCredential = userCredentialRepository.findById(customer.getUserCredential().getId()).get();
            customer.setUserCredential(userCredential);
            Customer saveCustomer = customerRepository.save(customer);
            return saveCustomer;
        }

        Customer save = customerRepository.save(customer);
        return save;
    }
}
