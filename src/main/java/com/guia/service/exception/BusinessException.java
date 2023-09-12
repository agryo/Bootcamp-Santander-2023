package com.guia.service.exception;

public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BusinessException(String messagem) {
        super(messagem);
    }
}
