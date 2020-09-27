package com.example.demo.controller;

import com.example.demo.exception.AccountSignupException;
import com.example.demo.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    private final ErrorController errorController;

    public UserController(ErrorController errorController) {
        this.errorController = errorController;
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() {
        throw new BadRequestException();
    }

    @PostMapping
    public ResponseEntity<Object> signUp() {
        throw new AccountSignupException();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUser(@PathVariable String userId) {
        throw new BadRequestException();
    }

    @ExceptionHandler(value = {AccountSignupException.class})
    public ResponseEntity handleError(HttpServletRequest request, HttpServletResponse response, Principal principal, AccountSignupException ex) {
        request.setAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        request.setAttribute(RequestDispatcher.ERROR_REQUEST_URI, request.getRequestURI());
        request.setAttribute(RequestDispatcher.ERROR_MESSAGE, ex.getMessage());
        request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, ex);
        return errorController.handleErrorEntity(request, response, principal);
    }
}
