package com.enigma.challengetokonyadiaapi.controller;

import com.enigma.challengetokonyadiaapi.dto.request.AuthRequest;
import com.enigma.challengetokonyadiaapi.dto.request.StoreRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CommonResponse;
import com.enigma.challengetokonyadiaapi.dto.response.LoginResponse;
import com.enigma.challengetokonyadiaapi.dto.response.RegisterResponse;
import com.enigma.challengetokonyadiaapi.entity.AppUser;
import com.enigma.challengetokonyadiaapi.entity.UserCredential;
import com.enigma.challengetokonyadiaapi.service.AuthService;
import com.enigma.challengetokonyadiaapi.service.UserCredentialService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserCredentialService userCredentialService;

    @PostMapping(path = "/register/customer")
    public ResponseEntity<?> register(@RequestBody AuthRequest request){
        RegisterResponse response = authService.registerCustomer(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        CommonResponse.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Successfully create account")
                                .data(response)
                                .build()
                );
    }
//
    @PostMapping(path = "/register/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AuthRequest request){
        RegisterResponse response = authService.registerAdmin(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        CommonResponse.builder()
                                .statusCode(HttpStatus.CREATED.value())
                                .message("Successfully create   account")
                                .data(response)
                                .build()
                );
    }
    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        LoginResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        CommonResponse.builder()
                                .statusCode(HttpStatus.OK.value())
                                .message("Successfully login")
                                .data(response)
                                .build()
                );
    }
//    @GetMapping(path = "/{id}")
//    public  ResponseEntity<?> test(@PathVariable String id){
//        AppUser userCredential = userCredentialService.loadById(id);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(
//                        CommonResponse.builder()
//                                .statusCode(HttpStatus.OK.value())
//                                .message("Successfully login")
//                                .data(userCredential)
//                                .build()
//                );
//    }

}
