package br.danmarzo.statefull_auth_api.core.controller;

import br.danmarzo.statefull_auth_api.core.dto.AuthRequest;
import br.danmarzo.statefull_auth_api.core.dto.AuthUserResponse;
import br.danmarzo.statefull_auth_api.core.dto.TokenDTO;
import br.danmarzo.statefull_auth_api.core.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Objects;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public TokenDTO login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }

    @PostMapping("token/validate")
    public TokenDTO tokenValidate(@RequestHeader String accessToken) {
        return authService.validateToken(accessToken);
    }

    @PostMapping("logout")
    public HashMap<String, Object> logout(@RequestHeader String accessToken) {
        authService.logout(accessToken);
        var response = new HashMap<String, Object>();
        var ok = HttpStatus.OK;
        response.put("status", ok.name());
        response.put("code", ok.value());
        return response;
    }

    @GetMapping("user")
    public AuthUserResponse getAuthenticatedUser(@RequestHeader String accessToken) {
        return authService.getAuthenticatedUser(accessToken);
    }
}
