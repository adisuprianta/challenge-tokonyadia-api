package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserCredentialService extends UserDetailsService {
    AppUser loadById(String id);

}
