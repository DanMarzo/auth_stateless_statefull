package br.danmarzo.statefull_any_api.core.service;

import br.danmarzo.statefull_any_api.core.client.TokenClient;
import br.danmarzo.statefull_any_api.core.dto.AuthUserResponse;
import br.danmarzo.statefull_any_api.infra.exception.AuthenticationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class TokenService {
    private final TokenClient tokenClient;

    public void validateToken(String token) {
        try {
            log.info("sending request for token validation {}", token);
            var response = tokenClient.tokenValidate(token);
            log.info("token is valid {}", response.accessToken());
        } catch (Exception ex) {
            throw new AuthenticationException("Auth error: " + ex.getMessage());
        }
    }

    public AuthUserResponse getAuthenticatedUser(String token) {
        try {
            log.info("sending request for auth user {}", token);
            var response = tokenClient.getAuthenticatedUser(token);
            log.info("auth user found {} with token {}", response.toString(), token);
            return response;
        } catch (Exception ex) {
            throw new AuthenticationException("Auth to get authenticated user: " + ex.getMessage());
        }
    }
}