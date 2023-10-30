package com.enigma.challengetokonyadiaapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransactionResponse {
    private String id;
    private CustomerResponse customerResponse;
    private Date transDate;
    private List<TransactionDetailResponse> product;
}
