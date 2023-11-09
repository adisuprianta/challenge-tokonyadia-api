package com.enigma.challengetokonyadiaapi.service;

import com.enigma.challengetokonyadiaapi.entity.AppUser;
import com.enigma.challengetokonyadiaapi.entity.Role;
import com.enigma.challengetokonyadiaapi.entity.UserCredential;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserCredentialService extends UserDetailsService {
    AppUser loadById(String id);
    UserCredential findById(String id);
    UserCredential addRoleToUserCredential(String id, Role role);

}
