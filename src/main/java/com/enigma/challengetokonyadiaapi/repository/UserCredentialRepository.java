package com.enigma.challengetokonyadiaapi.repository;

import com.enigma.challengetokonyadiaapi.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, String> {
}
