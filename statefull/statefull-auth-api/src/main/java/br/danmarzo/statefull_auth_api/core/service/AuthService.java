package br.danmarzo.statefull_auth_api.core.service;

import br.danmarzo.statefull_auth_api.core.dto.AuthRequest;
import br.danmarzo.statefull_auth_api.core.dto.AuthUserResponse;
import br.danmarzo.statefull_auth_api.core.dto.TokenDTO;
import br.danmarzo.statefull_auth_api.core.dto.TokenData;
import br.danmarzo.statefull_auth_api.core.model.User;
import br.danmarzo.statefull_auth_api.core.repository.UserRepository;
import br.danmarzo.statefull_auth_api.infra.exception.AuthenticationException;
import br.danmarzo.statefull_auth_api.infra.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthUserResponse getAuthenticatedUser(String accessToken) {
        var tokenData = tokenService.getTokenData(accessToken);
        var user = findByUsername(tokenData.username());
        return new AuthUserResponse(user.getId(), user.getUsername());
    }

    public void logout(String accessToken) {
        tokenService.deleteRedisToken(accessToken);
    }

    public TokenDTO login(AuthRequest request) {
        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ValidationException(("")));
        var accessToken = tokenService.createToken(user.getUsername());
        validatePassword(request.password(), user.getPassword());
        return new TokenDTO(accessToken);
    }

    public TokenDTO validateToken(String accessToken) {
        validateExistingToken(accessToken);
        var valid = tokenService.validateAccessToken(accessToken);
        if (valid) {
            return new TokenDTO(accessToken);
        } else {
            throw new AuthenticationException("Invalid token!");
        }
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ValidationException(("")));
    }

    public void validatePassword(String rawPassword, String encodedPassword) {
        if (ObjectUtils.isEmpty(rawPassword)) {
            throw new ValidationException("The password must be informed");
        }
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ValidationException("The password is incorrect");
        }
    }

    private void validateExistingToken(String accessToken) {
        if (ObjectUtils.isEmpty(accessToken)) {
            throw new ValidationException("The access token must be informed");
        }
    }
}
