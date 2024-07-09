package br.com.microservices.stateless.infra.exception;

public record ExceptionDetails(int status, String message) {
}
