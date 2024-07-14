package br.danmarzo.stateless_any_api.infra.core.dto;

public record AnyResponse(String status, Integer code, AuthUserResponse authUserResponse) {
}
