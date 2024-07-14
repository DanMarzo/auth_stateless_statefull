package br.danmarzo.stateless_auth_api.core.service;

import br.danmarzo.stateless_auth_api.core.dto.AuthRequest;
import br.danmarzo.stateless_auth_api.core.dto.TokenDTO;
import br.danmarzo.stateless_auth_api.core.repository.UserRepository;
import br.danmarzo.stateless_auth_api.infra.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenDTO login(AuthRequest request) {
        var user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new ValidationException(("")));
        var accessToken = jwtService.createToken(user);
        validatePassword(request.password(), user.getPassword());
        return new TokenDTO(accessToken);
    }

    public void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new ValidationException("The password is incorrect");
        }
    }

    public TokenDTO validateToken(String accessToken) {
        validateExistingToken(accessToken);
        jwtService.validateAccessToken(accessToken);
        return new TokenDTO(accessToken);
    }

    private void validateExistingToken(String accessToken) {
        if (ObjectUtils.isEmpty(accessToken)) {
            throw new ValidationException("The access token must be informed");
        }
    }
}
