package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.dto.request.StoreRequest;
import com.enigma.challengetokonyadiaapi.dto.response.StoreResponse;
import com.enigma.challengetokonyadiaapi.entity.Store;

import java.util.List;

public interface StoreService {
    StoreResponse save(StoreRequest request);
    Store save(Store store);
    StoreResponse findById(String id);
    Store getById(String id);
    List<StoreResponse> findAll();
    StoreResponse update(StoreRequest request);
    void delete(String id);

}
