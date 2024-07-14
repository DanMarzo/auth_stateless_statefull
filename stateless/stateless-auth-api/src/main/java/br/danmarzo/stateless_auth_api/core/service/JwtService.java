package br.danmarzo.stateless_auth_api.core.service;

import br.danmarzo.stateless_auth_api.core.model.User;
import br.danmarzo.stateless_auth_api.infra.exception.AuthenticationException;
import br.danmarzo.stateless_auth_api.infra.exception.ValidationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class JwtService {
    private static final Integer ONE_DAY_IN_HOURS = 24;
    private static final Integer TOKEN_INDEX = 1;
    private static final String EMPTY_SPACE = " ";

    @Value("${app.token.secret-key}")
    private String secretKey;

    private Date generateExpiresAt() {
        return Date.from(
                LocalDateTime.now()
                        .plusHours(ONE_DAY_IN_HOURS)
                        .atZone(
                                ZoneId.systemDefault()
                        ).toInstant());
    }

    private SecretKey generateSign() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String createToken(User user) {
        var data = new HashMap<String, String>();
        data.put("id", user.getId().toString());
        data.put("username", user.getUsername());
        return Jwts.builder()
                .claims(data)
                .expiration(generateExpiresAt())
                .signWith(generateSign())
                .compact();
    }

    public void validateAccessToken(String token) {
        var accesstoken = extractToken(token);
        try {
            Jwts
                    .parser()
                    .setSigningKey(generateSign())
                    .build()
                    .parseClaimsJws(accesstoken)
                    .getBody();
        } catch (Exception ex) {
            throw new AuthenticationException("Invalid token " + ex.getMessage());
        }
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
}
