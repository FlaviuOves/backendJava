package com.backend.backend.exception;

import com.backend.backend.exception.error.ApiError;
import com.backend.backend.exception.product.ProductNotFoundException;
import com.backend.backend.exception.user.RoleAlreadyExistException;
import com.backend.backend.exception.user.RoleNotFoundException;
import com.backend.backend.exception.user.UserAlreadyTakenException;
import com.backend.backend.exception.user.UserNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler{


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @Override
    public ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.METHOD_NOT_ALLOWED);
        apiError.setMessage(exception.getMessage());
        apiError.setDebugMessage((ExceptionUtils.getRootCauseMessage(exception)));
        apiError.setPath(((ServletWebRequest)request).getRequest().getRequestURI());
        return buildResponseEntity(apiError);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception,HttpServletRequest httpServletRequest)
    {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setMessage(exception.getMessage());
        apiError.setDebugMessage((ExceptionUtils.getRootCauseMessage(exception)));
        apiError.setPath(httpServletRequest.getRequestURI());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(UserAlreadyTakenException.class)
    public ResponseEntity<Object> handleUserAlreadyTakenException(UserAlreadyTakenException exception,HttpServletRequest httpServletRequest)
    {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.IM_USED);
        apiError.setMessage(exception.getMessage());
        apiError.setDebugMessage((ExceptionUtils.getRootCauseMessage(exception)));
        apiError.setPath(httpServletRequest.getRequestURI());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RoleAlreadyExistException.class)
    public ResponseEntity<Object> handleRoleAlreadyExistException(RoleAlreadyExistException exception,HttpServletRequest httpServletRequest)
    {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.IM_USED);
        apiError.setMessage(exception.getMessage());
        apiError.setDebugMessage((ExceptionUtils.getRootCauseMessage(exception)));
        apiError.setPath(httpServletRequest.getRequestURI());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException exception,HttpServletRequest httpServletRequest)
    {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setMessage(exception.getMessage());
        apiError.setDebugMessage(ExceptionUtils.getRootCauseMessage(exception));
        apiError.setPath(httpServletRequest.getRequestURI());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException exception,HttpServletRequest httpServletRequest)
    {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setMessage(exception.getMessage());
        apiError.setDebugMessage(ExceptionUtils.getRootCauseMessage(exception));
        apiError.setPath(httpServletRequest.getRequestURI());
        return buildResponseEntity(apiError);
    }

}
