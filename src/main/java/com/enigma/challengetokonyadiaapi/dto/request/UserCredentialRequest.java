package com.enigma.challengetokonyadiaapi.dto.request;

import lombok.*;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder(toBuilder = true)
public class UserCredentialRequest {

    private String email;

    private String password;
}
