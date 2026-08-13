package com.fiap.mercadoexpress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Tratamento global de excecoes da API, garantindo respostas HTTP
 * padronizadas (ex.: 404 quando o produto nao existe).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MercadoNotFoundException.class)
    public ResponseEntity<String> handleMercadoNotFound(MercadoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
