package com.enigma.challengetokonyadiaapi.repository;

import com.enigma.challengetokonyadiaapi.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDetailRepository extends JpaRepository<TransactionDetail,String> {
}
