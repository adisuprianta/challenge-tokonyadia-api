package com.enigma.challengetokonyadiaapi.dto.response;

import lombok.*;

import java.util.Date;

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
    private Date date;

}
