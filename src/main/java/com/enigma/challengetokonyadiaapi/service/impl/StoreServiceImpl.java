package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.constant.ERole;
import com.enigma.challengetokonyadiaapi.dto.request.StoreRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CommonResponse;
import com.enigma.challengetokonyadiaapi.dto.response.StoreResponse;
import com.enigma.challengetokonyadiaapi.entity.AppUser;
import com.enigma.challengetokonyadiaapi.entity.Role;
import com.enigma.challengetokonyadiaapi.entity.Store;
import com.enigma.challengetokonyadiaapi.repository.StoreRepository;
import com.enigma.challengetokonyadiaapi.service.AuthService;
import com.enigma.challengetokonyadiaapi.service.RoleService;
import com.enigma.challengetokonyadiaapi.service.StoreService;
import com.enigma.challengetokonyadiaapi.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final RoleService roleService;
    private final UserCredentialService userCredentialService;

    @Override
    public StoreResponse save(StoreRequest request) {
        Store store = Store.builder()
                .name(request.getName())
                .siupNumber(request.getSiupNumber())
                .address(request.getAddress())
                .build();
        store=storeRepository.save(store);
        return StoreResponse.builder()
                .id(store.getId())
                .siupNumber(store.getSiupNumber())
                .name(store.getName())
                .address(store.getAddress())
                .build();
    }

    @Override
    public Store save(Store store) {
        return storeRepository.saveAndFlush(store);
    }

    @Override
    public StoreResponse findById(String id) {
        Store store = storeRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Store Not Found")
        );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser principal = (AppUser) authentication.getPrincipal();
        if (!principal.getId().equals(store.getCustomer().getUserCredential().getId())) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"author");

        return StoreResponse.builder()
                .id(store.getId())
                .address(store.getAddress())
                .siupNumber(store.getSiupNumber())
                .name(store.getName())
                .build();
    }

    @Override
    public Store getById(String id) {
        return  storeRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Store Not Found")
        );
    }

    @Override
    public List<StoreResponse> findAll() {
        List<Store> stores = storeRepository.findAll();

        return stores.stream().map(store -> {
                    return StoreResponse.builder()
                            .id(store.getId())
                            .address(store.getAddress())
                            .siupNumber(store.getSiupNumber())
                            .name(store.getName())
                            .build();
                }).collect(Collectors.toList());
    }

    @Override
    public StoreResponse update(StoreRequest request) {

        Store store = storeRepository.findById(request.getId()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,"Store Cannot Be Updated")
        );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AppUser principal = (AppUser) authentication.getPrincipal();
        if (!principal.getId().equals(store.getCustomer().getUserCredential().getId())) throw  new ResponseStatusException(HttpStatus.BAD_REQUEST,"author");

        Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_SELLER).build());
        userCredentialService.addRoleToUserCredential(principal.getId(), role);

        store.setAddress(request.getAddress());
        store.setName(request.getName());
        store.setSiupNumber(request.getSiupNumber());
        storeRepository.save(store);
        return StoreResponse.builder()
                .id(store.getId())
                .address(store.getAddress())
                .siupNumber(store.getSiupNumber())
                .name(store.getName())
                .build();
    }

    @Override
    public void delete(String id) {
        Store store = storeRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,"Store Cannot Be Updated")
        );
        storeRepository.delete(store);
    }
}
