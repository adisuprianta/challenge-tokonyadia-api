package com.enigma.challengetokonyadiaapi.repository;


import com.enigma.challengetokonyadiaapi.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, String> {

}
