package com.guia.service.exception;

public class NotFoundException extends BusinessException {
    public NotFoundException() {
        super("O ID não encontrado.");
    }
}