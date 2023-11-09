package com.enigma.challengetokonyadiaapi.repository;

import com.enigma.challengetokonyadiaapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
}
