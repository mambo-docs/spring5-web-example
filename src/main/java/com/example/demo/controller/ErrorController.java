package com.example.demo.controller;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@ControllerAdvice
@Controller
@RequestMapping(value = "/error")
public class ErrorController implements MessageSourceAware {

    private MessageSource messageSource;

    @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView handleErrorPage(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        Map<String, Object> errorAttributes = getErrorAttributes(new ServletWebRequest(request, response));
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addAllObjects(errorAttributes);
        return modelAndView;
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> handleErrorEntity(HttpServletRequest request, HttpServletResponse response, Principal principal) {
        Map<String, Object> errorAttributes = getErrorAttributes(new ServletWebRequest(request, response));
        return ResponseEntity
                .status((Integer) errorAttributes.get("status"))
                .body(errorAttributes);
    }

    public Map<String, Object> getErrorAttributes(WebRequest webRequest) {
        Map<String, Object> errorAttributes = new HashMap<>();
        errorAttributes.put("timestamp", new Date());
        errorAttributes.put("path", webRequest.getAttribute(RequestDispatcher.ERROR_REQUEST_URI, RequestAttributes.SCOPE_REQUEST));

        // Error Status
        Integer status = (Integer) webRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE, RequestAttributes.SCOPE_REQUEST);
        errorAttributes.put("status", status);

        try {
            errorAttributes.put("error", HttpStatus.valueOf(status).getReasonPhrase());
        } catch (Exception e) {
            errorAttributes.put("error", "");
        }

        // Error Details
        Throwable exception = (Throwable) webRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION, RequestAttributes.SCOPE_REQUEST);
        if(exception == null && status != null) {
            // Get SecurityException from RequestAttributes
            if(403 == status) {
                exception = (Throwable) webRequest.getAttribute(WebAttributes.ACCESS_DENIED_403, RequestAttributes.SCOPE_REQUEST);
            } else if(401 == status) {
                exception = (Throwable) webRequest.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION, RequestAttributes.SCOPE_REQUEST);
            }
        }
        String exceptionName = "";
        if(exception != null) {
            exceptionName = exception.getClass().getName();
        }
        errorAttributes.put("exception", exceptionName);

        Object message = webRequest.getAttribute(RequestDispatcher.ERROR_MESSAGE, RequestAttributes.SCOPE_REQUEST);
        if(message != null) {
            message = messageSource.getMessage((String) message, null, (String) message, LocaleContextHolder.getLocale());
        }
        errorAttributes.put("message", message);

        return errorAttributes;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
