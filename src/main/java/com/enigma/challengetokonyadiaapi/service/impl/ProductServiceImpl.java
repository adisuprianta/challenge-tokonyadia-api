package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.dto.request.ProductRequest;
import com.enigma.challengetokonyadiaapi.dto.response.ProductResponse;
import com.enigma.challengetokonyadiaapi.entity.Product;
import com.enigma.challengetokonyadiaapi.repository.ProductRepository;
import com.enigma.challengetokonyadiaapi.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    @Override
    public Product getById(String id) {
        return repository.findById(id).orElseThrow(
                ()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found")
        );

//        return ProductResponse.builder()
//                .id(product.getId())
//                .stock(product.getStock())
//                .description(product.getDescription())
//                .price(product.getPrice())
//                .name(product.getName())
//                .build();
    }

    @Override
    public Product udpate(Product request) {
        return null;
    }
}
