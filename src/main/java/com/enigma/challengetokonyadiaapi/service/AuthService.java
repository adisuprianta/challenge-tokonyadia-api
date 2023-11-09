package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.dto.request.AuthRequest;
import com.enigma.challengetokonyadiaapi.dto.request.StoreRequest;
import com.enigma.challengetokonyadiaapi.dto.response.LoginResponse;
import com.enigma.challengetokonyadiaapi.dto.response.RegisterResponse;
import com.enigma.challengetokonyadiaapi.entity.Role;
import com.enigma.challengetokonyadiaapi.entity.UserCredential;

public interface AuthService {
    RegisterResponse registerCustomer(AuthRequest request);
    LoginResponse login(AuthRequest request);
//    RegisterResponse registerSeller(StoreRequest request);

    RegisterResponse registerAdmin(AuthRequest request);
}
