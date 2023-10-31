package com.enigma.challengetokonyadiaapi.controller;

import com.enigma.challengetokonyadiaapi.dto.request.TransactionRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CommonResponse;
import com.enigma.challengetokonyadiaapi.dto.response.TransactionResponse;
import com.enigma.challengetokonyadiaapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    @GetMapping(path = "/date")
    public Date date(){

        return new Date();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.save(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Transaction Successfully")
                        .data(response)
                        .build());
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findByid(@PathVariable String id){
        TransactionResponse response = transactionService.getById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Transaction Successfully")
                        .data(response)
                        .build());
    }
    @GetMapping
    public ResponseEntity<?> findAll(){
        List<TransactionResponse> responses = transactionService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Transaction Successfully")
                        .data(responses)
                        .build());
    }

}
