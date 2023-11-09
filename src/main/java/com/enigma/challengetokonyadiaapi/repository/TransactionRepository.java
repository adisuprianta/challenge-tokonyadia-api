package com.enigma.challengetokonyadiaapi.repository;

import com.enigma.challengetokonyadiaapi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,String> {

}
