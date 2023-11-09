package com.enigma.challengetokonyadiaapi.dto.response;

import com.enigma.challengetokonyadiaapi.constant.ERole;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {
    private String userId;
    private List<ERole> roles;
}
