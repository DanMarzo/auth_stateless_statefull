package br.danmarzo.stateless_any_api.infra.exception;

public class ValidationException extends RuntimeException {
    public ValidationException (String message) {
        super(message);
    }
}
