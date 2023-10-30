package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.dto.request.TransactionRequest;
import com.enigma.challengetokonyadiaapi.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse save(TransactionRequest request);

    TransactionResponse getById(String id);

    List<TransactionResponse> getAll();
}
