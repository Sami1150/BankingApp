package com.redmath.assignment.bankingapplication.exceptions;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ApiError {

    private HttpStatus httpStatus;
    private String message;
    private LocalDateTime timestamp;
    private int status;

    //

    public ApiError() {
        super();
    }

    public ApiError(final HttpStatus httpStatus, final String message, final LocalDateTime timestamp, final int status) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.status=status;
        this.timestamp=timestamp;
    }

//    public ApiError(final HttpStatus httpStatus, final String message, final LocalDateTime timestamp, final int status) {
//        this.httpStatus = httpStatus;
//        this.message = message;
//        this.status=status;
//        this.timestamp=timestamp;
//
//    }

    //

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}