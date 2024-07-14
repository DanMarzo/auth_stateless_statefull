package br.danmarzo.statefull_auth_api.core.service;

import br.danmarzo.statefull_auth_api.core.dto.TokenData;
import br.danmarzo.statefull_auth_api.infra.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@AllArgsConstructor
public class TokenService {
    private static final Long ONE_DAY_IN_SECONDS = 86400L;
    private static final Integer TOKEN_INDEX = 1;
    private static final String EMPTY_SPACE = " ";

    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public String createToken(String username) {

    }

    public TokenData getTokenData(String token) {

    }

    public void deleteRedisToken(String token) {

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
