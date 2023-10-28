package com.enigma.challengetokonyadiaapi.controller;


import com.enigma.challengetokonyadiaapi.dto.request.CustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CommonResponse;
import com.enigma.challengetokonyadiaapi.dto.response.CustomerResponse;
import com.enigma.challengetokonyadiaapi.entity.Customer;
import com.enigma.challengetokonyadiaapi.entity.UserCredential;
import com.enigma.challengetokonyadiaapi.repository.CustomerRepository;
import com.enigma.challengetokonyadiaapi.repository.UserCredentialRepository;
import com.enigma.challengetokonyadiaapi.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/customers")
public class CustomerController {
    private  final CustomerService customerService;
    @PostMapping
    public ResponseEntity<?> createNewCustomer(@RequestBody CustomerRequest request){
        Customer customer = Customer.builder()
                .address(request.getAddress())
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber()).build();
        CustomerResponse response = customerService.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully Create New Customer")
                        .data(response)
                        .build());

    }
    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerRequest request){
        CustomerResponse response = customerService.update(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Update Customer")
                        .data(response)
                        .build());
    }
    @GetMapping
    public ResponseEntity<?> findAll(){

        List<CustomerResponse> responses = customerService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get All Customer")
                        .data(responses)
                        .build());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        CustomerResponse response = customerService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get Customer by id")
                        .data(response)
                        .build());
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete customer")
                        .build());

    }


//    @GetMapping("/test")
//    public List<Customer> getAll(){
//        return customerRepository.findAll();
//    }


}
