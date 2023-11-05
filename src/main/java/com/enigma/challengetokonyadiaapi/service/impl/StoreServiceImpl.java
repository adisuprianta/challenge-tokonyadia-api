package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.dto.request.StoreRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CommonResponse;
import com.enigma.challengetokonyadiaapi.dto.response.StoreResponse;
import com.enigma.challengetokonyadiaapi.entity.Store;
import com.enigma.challengetokonyadiaapi.repository.StoreRepository;
import com.enigma.challengetokonyadiaapi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;

    @Override
    public StoreResponse save(StoreRequest request) {
        Store store = Store.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .siupNumber(request.getSiupNumber())
                .address(request.getAddress())
                .build();
        store=storeRepository.save(store);
        return StoreResponse.builder()
                .id(store.getId())
                .siupNumber(store.getSiupNumber())
                .name(store.getName())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .build();
    }

    @Override
    public StoreResponse findById(String id) {
        Store store = storeRepository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Store Not Found")
        );
        return StoreResponse.builder()
                .id(store.getId())
                .phoneNumber(store.getPhoneNumber())
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
                            .phoneNumber(store.getPhoneNumber())
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
        store.setAddress(request.getAddress());
        store.setName(request.getName());
        store.setPhoneNumber(request.getPhoneNumber());
        store.setSiupNumber(request.getSiupNumber());
        storeRepository.save(store);
        return StoreResponse.builder()
                .id(store.getId())
                .phoneNumber(store.getPhoneNumber())
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
