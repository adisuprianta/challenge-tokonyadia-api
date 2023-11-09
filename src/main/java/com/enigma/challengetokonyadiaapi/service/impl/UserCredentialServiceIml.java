package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.constant.ERole;
import com.enigma.challengetokonyadiaapi.dto.request.CustomerRequest;
import com.enigma.challengetokonyadiaapi.dto.request.UserCredentialRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CustomerResponse;
import com.enigma.challengetokonyadiaapi.dto.response.UserCredentialResponse;
import com.enigma.challengetokonyadiaapi.entity.AppUser;
import com.enigma.challengetokonyadiaapi.entity.Role;
import com.enigma.challengetokonyadiaapi.entity.UserCredential;
import com.enigma.challengetokonyadiaapi.repository.CustomerRepository;
import com.enigma.challengetokonyadiaapi.repository.UserCredentialRepository;
import com.enigma.challengetokonyadiaapi.service.UserCredentialService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCredentialServiceIml implements UserCredentialService {

    private final UserCredentialRepository userCredentialRepository;


    // verifikasi Authentication Login - loadByUsername ini dipanggil pada saat authenticate oleh
    //authenticationManager di
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException("invalid username or password")
        );
        List<ERole> roles = userCredential.getLikeRole().stream().map(Role::getName).collect(Collectors.toList());

        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .roles(roles)
                .build();
    }

    @Override
    public AppUser loadById(String id) {
        UserCredential userCredential = userCredentialRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("invalid username or password")
                );
//        log.info(userCredential.getLikeRole());
        List<ERole> roles = userCredential.getLikeRole().stream().map(role -> role.getName()).collect(Collectors.toList());

        return AppUser.builder()
                .id(userCredential.getId())
                .username(userCredential.getUsername())
                .password(userCredential.getPassword())
                .roles(roles)
                .build();
    }

    @Override
    public UserCredential findById(String id) {
        return userCredentialRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("invalid username or password")
        );
    }

    @Override
    public UserCredential addRoleToUserCredential(String id, Role role) {
        UserCredential userCredential = userCredentialRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User Credential Not Found");
        });

        //menambahkan role agar role sebelumnya tidak terhapus
        Set<Role> userRoles = userCredential.getLikeRole();
        userRoles.add(role);

        //menambahkan rolenya
        userCredential.setLikeRole(userRoles);


        return userCredentialRepository.saveAndFlush(userCredential);
    }
}
