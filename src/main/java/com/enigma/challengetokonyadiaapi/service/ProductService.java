package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.dto.request.ProductRequest;
import com.enigma.challengetokonyadiaapi.entity.Product;

public interface ProductService {
    Product getById (String id);
    Product udpate(Product request);
}