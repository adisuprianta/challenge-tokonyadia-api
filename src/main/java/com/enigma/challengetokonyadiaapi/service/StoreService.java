package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.dto.request.StoreRequest;
import com.enigma.challengetokonyadiaapi.dto.response.StoreResponse;

import java.util.List;

public interface StoreService {
    StoreResponse save(StoreRequest request);
    StoreResponse findById(String id);
    List<StoreResponse> findAll();
    StoreResponse update(StoreRequest request);
    void delete(String id);

}
