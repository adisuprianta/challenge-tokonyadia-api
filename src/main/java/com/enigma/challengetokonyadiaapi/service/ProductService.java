package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.dto.request.ProductRequest;
import com.enigma.challengetokonyadiaapi.dto.response.ProductResponse;
import com.enigma.challengetokonyadiaapi.entity.Product;

public interface ProductService {
    Product getById (String id);
    ProductResponse findById(String id);

    ProductResponse udpate(ProductRequest request);
    ProductResponse createNew(ProductRequest request);

}
