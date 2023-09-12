package com.redmath.assignment.bankingapplication.config;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@EnableCaching
@Configuration
public class ApplicationConfiguration {

    public static final String TRACEID = "traceId";
    public static final String SPANID = "spanId";

    @Bean
    public FilterRegistrationBean<Filter> traceFilter() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        bean.addUrlPatterns("/api/*");
        bean.setFilter((request, response, chain) -> doFilter(request, response, chain));
        return bean;
    }

    private void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            UUID uuid = UUID.randomUUID();
            String spanId = Long.toHexString(uuid.getLeastSignificantBits());
            String traceId = Long.toHexString(uuid.getMostSignificantBits());
            MDC.put(TRACEID, traceId);
            MDC.put(SPANID, spanId);
            chain.doFilter(request, response);
        } finally {
            MDC.remove(TRACEID);
            MDC.remove(SPANID);
        }
    }
}