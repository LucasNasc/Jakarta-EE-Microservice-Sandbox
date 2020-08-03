package br.com.lucas.exceptions;

import javax.servlet.ServletException;

public class BusinessException extends ServletException {
    public BusinessException(String message) {
        super(message);
    }
}
