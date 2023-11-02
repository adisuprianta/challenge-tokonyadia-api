package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.dto.request.CustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.request.SearchCustomerRequest;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
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
    public Page<CustomerResponse> findAll(SearchCustomerRequest request) {
        try {
            Specification<Customer> specification = (root,query,criteriaBuilder)->{
                List<Predicate> predicates = new ArrayList<>();
                if(request.getName()!=null){
                    Predicate predicate =  criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            "%"+request.getName().toLowerCase()+"%"
                    );
                    predicates.add(predicate);
                }
                if (request.getMaxBod()!=null){
                    predicates.add(
                            criteriaBuilder.lessThanOrEqualTo(root.get("bod"),request.getMaxBod())
                    );
                }
                if (request.getMinBod()!=null){
                    predicates.add(
                            criteriaBuilder.greaterThanOrEqualTo(root.get("bod"),request.getMinBod())
                    );
                }
                return query.where(predicates.toArray(new Predicate[]{})).getRestriction();

            };
            PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
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
    public Customer findById(String id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "customer tidak ada")
        );


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
