package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.dto.request.CustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.request.UserCredentialRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CustomerResponse;
import com.enigma.challengetokonyadiaapi.dto.response.UserCredentialResponse;

public interface UserCredentialService {
    UserCredentialResponse save(UserCredentialRequest request);

}
