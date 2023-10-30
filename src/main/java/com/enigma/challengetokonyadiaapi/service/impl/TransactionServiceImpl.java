package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.dto.request.TransactionRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CustomerResponse;
import com.enigma.challengetokonyadiaapi.dto.response.TransactionDetailResponse;
import com.enigma.challengetokonyadiaapi.dto.response.TransactionResponse;
import com.enigma.challengetokonyadiaapi.entity.Customer;
import com.enigma.challengetokonyadiaapi.entity.Product;
import com.enigma.challengetokonyadiaapi.entity.Transaction;
import com.enigma.challengetokonyadiaapi.entity.TransactionDetail;
import com.enigma.challengetokonyadiaapi.repository.TransactionRepository;
import com.enigma.challengetokonyadiaapi.service.CustomerService;
import com.enigma.challengetokonyadiaapi.service.ProductService;
import com.enigma.challengetokonyadiaapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public TransactionResponse save(TransactionRequest request) {
        CustomerResponse customerResponse = customerService.findById(request.getCustomerId());
        List<TransactionDetail> transactionDetails = request.getTransactionDetails().stream().map(t -> {
                    Product product = productService.getById(t.getProductId());
                    return TransactionDetail.builder()
                            .price(t.getPrice())
                            .quantity(t.getQty())
                            .product(product)
                            .build();

                })
                .collect(Collectors.toList());
        Customer customer = Customer.builder()
                .id(customerResponse.getId())
                .name(customerResponse.getName())
                .address(customerResponse.getAddress())
                .phoneNumber(customerResponse.getPhoneNumber())
                .build();

        Transaction transaction = Transaction.builder()
                .customer(customer)
                .transactionDetails(transactionDetails)
                .transDate(new Date())
                .build();
        Transaction saveTrans = transactionRepository.save(transaction);

        List<TransactionDetailResponse> detailResponses = getDetailResponses(saveTrans);


        return TransactionResponse.builder()
                .id(saveTrans.getId())
                .customerResponse(customerResponse)
                .transDate(saveTrans.getTransDate())
                .product(detailResponses)
                .build();
    }

    @Override
    public TransactionResponse getById(String id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "transaction not found")
        );

        CustomerResponse customerResponse = getCustomerResponse(transaction);

        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerResponse(customerResponse)
                .transDate(transaction.getTransDate())
                .product(getDetailResponses(transaction))
                .build();
    }

    @Override
    public List<TransactionResponse> getAll() {
        List<Transaction> trasaction = transactionRepository.findAll();
        return trasaction.stream()
                .map(transaction -> {
                    CustomerResponse customerResponse = getCustomerResponse(transaction);
                    return TransactionResponse.builder()
                            .id(transaction.getId())
                            .customerResponse(customerResponse)
                            .transDate(transaction.getTransDate())
                            .product(getDetailResponses(transaction))
                            .build();
                })
                .collect(Collectors.toList());

    }

    private static CustomerResponse getCustomerResponse(Transaction transaction) {
        return CustomerResponse.builder()
                .id(transaction.getCustomer().getId())
                .address(transaction.getCustomer().getAddress())
                .name(transaction.getCustomer().getName())
                .phoneNumber(transaction.getCustomer().getPhoneNumber())
                .build();
    }

    private List<TransactionDetailResponse> getDetailResponses(Transaction saveTrans) {
        return saveTrans.getTransactionDetails()
                .stream().map(t -> {
                    Integer stock = t.getProduct().getStock() - t.getQuantity();
                    t.getProduct().setStock(stock);
                    productService.udpate(t.getProduct());
                    t.setTransaction(saveTrans);
                    return TransactionDetailResponse.builder()
                            .productName(t.getProduct().getName())
                            .price(t.getPrice())
                            .quantity(t.getQuantity())
                            .id(t.getId())
                            .build();
                }).collect(Collectors.toList());
    }
}