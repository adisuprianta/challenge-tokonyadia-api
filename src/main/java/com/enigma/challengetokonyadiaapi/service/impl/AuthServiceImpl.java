package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.constant.ERole;
import com.enigma.challengetokonyadiaapi.dto.request.AuthRequest;
import com.enigma.challengetokonyadiaapi.dto.response.LoginResponse;
import com.enigma.challengetokonyadiaapi.dto.response.RegisterResponse;
import com.enigma.challengetokonyadiaapi.entity.*;
import com.enigma.challengetokonyadiaapi.repository.UserCredentialRepository;
import com.enigma.challengetokonyadiaapi.security.JwtUtil;
import com.enigma.challengetokonyadiaapi.service.*;
import com.enigma.challengetokonyadiaapi.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final ValidationUtil validationUtil;
    private final AuthenticationManager authenticationManager;
    private  final SellerService sellerService;
    private final  AdminService adminService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerCustomer(AuthRequest request) {
        try {
            validationUtil.validate(request);
            Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_USER).build());
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);
            customerService.save(Customer.builder().userCredential(userCredential).build());

            return RegisterResponse.builder()
                    .role(role.getName().name())
                    .username(userCredential.getUsername())
                    .build();
        }catch (DataIntegrityViolationException e){
            throw  new ResponseStatusException(HttpStatus.CONFLICT,"user alredy exits");
        }
    }
    @Override
    public RegisterResponse registerSeller(AuthRequest request) {
        try {
            validationUtil.validate(request);
            Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_SELLER).build());
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);
            sellerService.save(Seller.builder().userCredential(userCredential).build());

            return RegisterResponse.builder()
                    .role(role.getName().name())
                    .username(userCredential.getUsername())
                    .build();
        }catch (DataIntegrityViolationException e){
            throw  new ResponseStatusException(HttpStatus.CONFLICT,"Seller alredy exits");
        }
    }
    @Override
    public RegisterResponse registerAdmin(AuthRequest request) {
        try {
            validationUtil.validate(request);
            Role role = roleService.getOrSave(Role.builder().name(ERole.ROLE_ADMIN).build());
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);
            adminService.save(Admin.builder().userCredential(userCredential).build());

            return RegisterResponse.builder()
                    .role(role.getName().name())
                    .username(userCredential.getUsername())
                    .build();
        }catch (DataIntegrityViolationException e){
            throw  new ResponseStatusException(HttpStatus.CONFLICT,"Seller alredy exits");
        }
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        validationUtil.validate(request);
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        AppUser appUser = (AppUser) authenticate.getPrincipal();
        String token =jwtUtil.generateToken(appUser);
        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }
}
