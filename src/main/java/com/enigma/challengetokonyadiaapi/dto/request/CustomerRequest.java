package com.enigma.challengetokonyadiaapi.dto.request;

import com.enigma.challengetokonyadiaapi.entity.Customer;
import com.enigma.challengetokonyadiaapi.entity.UserCredential;
import lombok.*;

import javax.persistence.Column;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class CustomerRequest {

    private String id;
    private String name;

    private String phoneNumber;

    private String address;
    private String userCredentialId;
}
