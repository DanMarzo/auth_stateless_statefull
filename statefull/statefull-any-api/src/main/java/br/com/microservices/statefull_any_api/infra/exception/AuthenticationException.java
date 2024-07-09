package br.com.microservices.statefull_any_api.infra.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException (String message){
        super(message);
    }
}
