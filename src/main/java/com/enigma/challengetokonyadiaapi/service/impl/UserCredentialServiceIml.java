package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.dto.request.CustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.request.UserCredentialRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CustomerResponse;
import com.enigma.challengetokonyadiaapi.dto.response.UserCredentialResponse;
import com.enigma.challengetokonyadiaapi.repository.CustomerRepository;
import com.enigma.challengetokonyadiaapi.repository.UserCredentialRepository;
import com.enigma.challengetokonyadiaapi.service.UserCredentialService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceIml implements UserCredentialService {

    private final CustomerRepository customerRepository;
    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserCredentialResponse save(UserCredentialRequest request) {

        return null;
    }
}
