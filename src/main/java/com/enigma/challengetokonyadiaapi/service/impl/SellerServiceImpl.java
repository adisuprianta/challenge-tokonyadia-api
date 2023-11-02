package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.entity.Seller;
import com.enigma.challengetokonyadiaapi.repository.SellerRepository;
import com.enigma.challengetokonyadiaapi.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    @Override
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }
}
