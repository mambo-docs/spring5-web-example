package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Resolve exception using ResponseStatusExceptionResolver
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.badRequest")
public class BadRequestException extends RuntimeException {
}
