package br.danmarzo.stateless_any_api.infra.core.service;

import br.danmarzo.stateless_any_api.infra.core.dto.AuthUserResponse;
import br.danmarzo.stateless_any_api.infra.exception.AuthenticationException;
import br.danmarzo.stateless_any_api.infra.exception.ValidationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;

@Service
//Usamos o required args quando nao sao todos que precisam de DI
@RequiredArgsConstructor
public class JwtService {
    private static final Integer TOKEN_INDEX = 1;
    private static final String EMPTY_SPACE = " ";

    @Value("${app.token.secret-key}")
    private String secretKey;

    public AuthUserResponse getAuthenticatedUser(String token) {
        var tokenClaims = getClaims(token);
        var userId = Integer.valueOf((String) tokenClaims.get("id"));
        var username = (String) tokenClaims.get("username");
        return new AuthUserResponse(userId, username);
    }

    public Claims getClaims(String token) {
        var accesstoken = extractToken(token);
        try {
            return Jwts
                    .parser()
                    .setSigningKey(generateSign())
                    .build()
                    .parseClaimsJws(accesstoken)
                    .getBody();
        } catch (Exception ex) {
            throw new AuthenticationException("Invalid token " + ex.getMessage());
        }
    }

    public void validateAccessToken(String token) {
        getClaims(token);
    }

    private String extractToken(String token) {
        if (ObjectUtils.isEmpty(token)) {
            throw new ValidationException("The access token was not informed");
        }
        if (token.contains(EMPTY_SPACE)) {
            return token.split(EMPTY_SPACE)[TOKEN_INDEX];
        }
        return token;
    }

    private SecretKey generateSign() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

}
