package com.enigma.challengetokonyadiaapi.repository;

import com.enigma.challengetokonyadiaapi.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller,String> {
}
