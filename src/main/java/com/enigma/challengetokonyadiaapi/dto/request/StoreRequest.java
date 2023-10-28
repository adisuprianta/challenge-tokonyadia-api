package com.enigma.challengetokonyadiaapi.dto.request;

import lombok.*;

import javax.persistence.Column;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StoreRequest {
    private String id;
    private String siupNumber;
    private String name;
    private String address;
    private String phoneNumber;
}
