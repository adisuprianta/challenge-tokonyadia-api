package com.enigma.challengetokonyadiaapi.dto.request;

import com.enigma.challengetokonyadiaapi.entity.TransactionDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionRequest {
    private String customerId;
    private List<TransactionDetailRequest> product;
}
