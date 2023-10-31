package com.enigma.challengetokonyadiaapi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionDetailRequest {
    private String productId;
    private Integer qty;
//    private Long price;
}
