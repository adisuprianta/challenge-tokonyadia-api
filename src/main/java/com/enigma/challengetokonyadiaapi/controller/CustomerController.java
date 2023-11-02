package com.enigma.challengetokonyadiaapi.controller;


import com.enigma.challengetokonyadiaapi.dto.request.CustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.request.SearchCustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CommonResponse;
import com.enigma.challengetokonyadiaapi.dto.response.CustomerResponse;
import com.enigma.challengetokonyadiaapi.dto.response.PagingResponse;
import com.enigma.challengetokonyadiaapi.entity.Customer;
import com.enigma.challengetokonyadiaapi.entity.UserCredential;
import com.enigma.challengetokonyadiaapi.repository.CustomerRepository;
import com.enigma.challengetokonyadiaapi.repository.UserCredentialRepository;
import com.enigma.challengetokonyadiaapi.service.CustomerService;
import com.enigma.challengetokonyadiaapi.util.PagingUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
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
    @PreAuthorize("hasRole('USER')")
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
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", required = false,defaultValue = "1") Integer page,
            @RequestParam(name = "size",required = false,defaultValue = "10") Integer size,
            @RequestParam(required = false, defaultValue = "asc") String direction,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Date minBod,
            @RequestParam(required = false) Date maxBod
    ){
        page = PagingUtil.validatePage(page);
        size = PagingUtil.validateSize(size);
        direction = PagingUtil.validateDirection(direction);
        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .sortBy(sortBy)
                .direction(direction)
                .page(page-1)
                .size(size)
                .minBod(minBod)
                .maxBod(maxBod)
                .name(name)
                .build();
//karena page mulai dari 0 dimana sama kayak index di array makanya page -1
        Page<CustomerResponse> customerPage = customerService.findAll(request);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(customerPage.getTotalPages())
                .size(size)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Get Customer by id")
                        .data(customerPage.getContent())
                        .pagingResponse(pagingResponse)
                        .build()
        );
    }
    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        Customer customer = customerService.findById(id);
        CustomerResponse response = CustomerResponse.builder()
                .id(customer.getId())
                .address(customer.getAddress())
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .build();
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
