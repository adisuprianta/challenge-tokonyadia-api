package com.enigma.challengetokonyadiaapi.dto.request;

import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder(toBuilder = true)
public class SearchCustomerRequest {
    private Integer page;
    private Integer size;
    private String direction;
    private String sortBy;
    private String name;
    private Date minBod,maxBod;
}
