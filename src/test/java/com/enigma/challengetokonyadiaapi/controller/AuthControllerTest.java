package com.enigma.challengetokonyadiaapi.controller;

import com.enigma.challengetokonyadiaapi.constant.ERole;
import com.enigma.challengetokonyadiaapi.dto.request.AuthRequest;
import com.enigma.challengetokonyadiaapi.dto.response.CommonResponse;
import com.enigma.challengetokonyadiaapi.dto.response.LoginResponse;
import com.enigma.challengetokonyadiaapi.dto.response.RegisterResponse;
import com.enigma.challengetokonyadiaapi.service.AuthService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@WithMockUser()
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
class AuthControllerTest {
    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void register() throws Exception {
        AuthRequest request = AuthRequest.builder()
                .username("username")
                .password("password")
                .build();
        RegisterResponse registerResponse = RegisterResponse.builder()
                .username("username")
                .role(ERole.ROLE_CUSTOMER.name())
                .build();
        Mockito.when(authService.registerCustomer(request)).thenReturn(registerResponse);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/register/customer")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> {
                    CommonResponse<RegisterResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );
                    assertEquals(201, response.getStatusCode());
                    assertEquals(ERole.ROLE_CUSTOMER.name(), response.getData().getRole());
                });


    }

    @Test
    void registerSeller() throws Exception {
        AuthRequest request = AuthRequest.builder()
                .username("username")
                .password("password")
                .build();
        RegisterResponse registerResponse = RegisterResponse.builder()
                .username("username")
                .role(ERole.ROLE_SELLER.name())
                .build();
//        Mockito.when(authService.registerSeller(request)).thenReturn(registerResponse);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/register/seller")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> {
                    CommonResponse<RegisterResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );
                    assertEquals(201, response.getStatusCode());
                    assertEquals(ERole.ROLE_SELLER.name(), response.getData().getRole());
                });
    }

    @Test
    void registerAdmin() throws Exception {
        AuthRequest request = AuthRequest.builder()
                .username("username")
                .password("password")
                .build();
        RegisterResponse registerResponse = RegisterResponse.builder()
                .username("username")
                .role(ERole.ROLE_ADMIN.name())
                .build();
        Mockito.when(authService.registerAdmin(request)).thenReturn(registerResponse);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/register/admin")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(result -> {
                    CommonResponse<RegisterResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );
                    assertEquals(201, response.getStatusCode());
                    assertEquals(ERole.ROLE_ADMIN.name(), response.getData().getRole());
                });
    }

    @Test
    void login() throws Exception {
        AuthRequest request = AuthRequest.builder()
                .username("username")
                .password("password")
                .build();
        LoginResponse loginResponse = LoginResponse.builder()
//                .role(ERole.ROLE_CUSTOMER.name())
                .token("aaaa")
                .build();

        Mockito.when(authService.login(request)).thenReturn(loginResponse);


        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/login")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(result -> {
                    CommonResponse<LoginResponse> response = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            }
                    );
                    assertEquals(200, response.getStatusCode());
                    assertNotNull(response.getData());
                    assertEquals(ERole.ROLE_CUSTOMER.name(), response.getData().getRole());
                });

    }

}