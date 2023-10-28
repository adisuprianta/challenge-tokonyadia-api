package com.enigma.challengetokonyadiaapi.dto.response;

import lombok.*;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class UserCredentialResponse {
    private String id;
    private String email;
    private String password;
    private CustomerResponse customerResponse;
}
