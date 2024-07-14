package br.danmarzo.stateless_auth_api.core.controller;

import br.danmarzo.stateless_auth_api.core.dto.AuthRequest;
import br.danmarzo.stateless_auth_api.core.dto.TokenDTO;
import br.danmarzo.stateless_auth_api.core.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public TokenDTO login(@RequestBody AuthRequest request){
        return authService.login(request);
    }

    @PostMapping("token/validate")
    public TokenDTO tokenValidate(@RequestHeader String accessToken){
        return authService.validateToken(accessToken);
    }
}