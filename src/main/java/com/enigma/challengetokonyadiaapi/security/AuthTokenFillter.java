package com.enigma.challengetokonyadiaapi.security;

import com.enigma.challengetokonyadiaapi.dto.response.TokenResponse;
import com.enigma.challengetokonyadiaapi.entity.AppUser;
import com.enigma.challengetokonyadiaapi.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthTokenFillter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserCredentialService userCredentialService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String headerAuth = request.getHeader("Authorization");
            String token = null;
            if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
                token = headerAuth.substring(7);
            }

            if (token!=null&&jwtUtil.verifyJwtToken(token)){
//                Map<String, String> userInfoByToken = jwtUtil.getUserInfoByToken(token);
                TokenResponse userInfoByToken = jwtUtil.getUserInfoByToken(token);
                log.info(userInfoByToken.getUserId());
                //dapet user detail karena appuser di entity udh ada kaitannya dengan user detail
//                UserDetails appUser = userCredentialService.loadById(userInfoByToken.get("userId"));
                AppUser appUser = userCredentialService.loadById(userInfoByToken.getUserId());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        appUser,
                        null,
                        appUser.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }catch (Exception e){
            log.error("Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
