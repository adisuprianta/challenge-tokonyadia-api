package com.enigma.challengetokonyadiaapi.service.impl;

import com.enigma.challengetokonyadiaapi.constant.ERole;
import com.enigma.challengetokonyadiaapi.entity.Role;
import com.enigma.challengetokonyadiaapi.repository.RoleRepository;
import com.enigma.challengetokonyadiaapi.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @MockBean
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;

    @Test
    void testGet() {
        Role role = Role.builder().id("1")
                .name(ERole.ROLE_CUSTOMER).build();
        Mockito.when(roleRepository.findByName(role.getName()))
                .thenReturn(Optional.of(role));
        if (!Optional.of(role).isEmpty()){
            Role actual = roleService.getOrSave(role);
            assertNotNull(actual);
            assertEquals(ERole.ROLE_CUSTOMER,actual.getName());
        }


    }
    @Test
    void testSave(){
        Role role = Role.builder().id("1")
                .name(ERole.ROLE_CUSTOMER).build();
//        Optional<Role> optionalRole= Optional.empty();
        Mockito.when(roleRepository.findByName(role.getName()))
                .thenReturn(Optional.empty());
        Mockito.when(roleRepository.save(role))
                .thenReturn(role);
        Role actual = roleService.getOrSave(role);
        assertNotNull(actual);
        assertEquals(ERole.ROLE_CUSTOMER,actual.getName());
    }

}
