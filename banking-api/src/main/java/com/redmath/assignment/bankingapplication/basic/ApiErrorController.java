package com.redmath.assignment.bankingapplication.basic;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ApiErrorController implements ErrorController {

    private final BasicErrorController basic;

    public ApiErrorController(ErrorAttributes errorAttributes, ServerProperties serverProperties) {
        basic = new BasicErrorController(errorAttributes, serverProperties.getError());
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> get(HttpServletRequest request) {
        return error(request);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> post(HttpServletRequest request) {
        return error(request);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, Object>> delete(HttpServletRequest request) {
        return error(request);
    }

    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        return basic.error(request);
    }
}
