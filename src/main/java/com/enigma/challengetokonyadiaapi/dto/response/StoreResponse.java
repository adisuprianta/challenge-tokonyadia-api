package com.enigma.challengetokonyadiaapi.dto.response;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponse {
    private String id;
    private String siupNumber;
    private String name;
    private String address;
    private String phoneNumber;
}
