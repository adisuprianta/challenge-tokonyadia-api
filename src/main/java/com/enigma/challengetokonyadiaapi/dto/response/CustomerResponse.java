package com.enigma.challengetokonyadiaapi.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
public class CustomerResponse {
    private String id;
    private String name;

    private String phoneNumber;

    private String address;

}
