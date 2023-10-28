package com.enigma.challengetokonyadiaapi.controller;

import com.enigma.challengetokonyadiaapi.dto.request.StoreRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CommonResponse;
import com.enigma.challengetokonyadiaapi.dto.response.StoreResponse;
import com.enigma.challengetokonyadiaapi.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<?> createNewStore(@RequestBody StoreRequest request){
        StoreResponse response = storeService.save(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Create New Store")
                        .data(response)
                        .build()
                );
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findById(@PathVariable String id){
        StoreResponse response = storeService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Get Store By Id")
                        .data(response)
                        .build()
                );
    }
    @GetMapping
    public ResponseEntity<?> findAll(){
        List<StoreResponse> responses = storeService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Get All Store")
                        .data(responses)
                        .build()
                );
    }
    @PutMapping
    public ResponseEntity<?> update(@RequestBody StoreRequest request){
        StoreResponse response = storeService.update(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Updated Store")
                        .data(response)
                        .build()
                );
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        storeService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully Delete Store")
                        .build()
                );
    }
}
