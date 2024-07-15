package br.danmarzo.statefull_any_api.core.client;

import br.danmarzo.statefull_any_api.core.dto.AuthUserResponse;
import br.danmarzo.statefull_any_api.core.dto.TokenDTO;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/auth")
public interface TokenClient {
    @PostExchange("token/validate")
    TokenDTO tokenValidate(@RequestHeader String accessToken);

    @GetExchange("user")
    AuthUserResponse getAuthenticatedUser(@RequestHeader String accessToken);
}
