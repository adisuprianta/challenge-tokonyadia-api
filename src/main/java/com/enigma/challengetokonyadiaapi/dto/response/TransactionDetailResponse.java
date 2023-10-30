package com.enigma.challengetokonyadiaapi.dto.response;

import com.enigma.challengetokonyadiaapi.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionDetailResponse {
    private String id;
    private String productName;
    private Integer quantity;
    private Long price;
}
