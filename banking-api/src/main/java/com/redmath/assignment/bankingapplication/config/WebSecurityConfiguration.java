package com.redmath.assignment.bankingapplication.config;

import jakarta.servlet.FilterRegistration;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableMethodSecurity
@Configuration
public class WebSecurityConfiguration {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

//        return web -> web.ignoring().requestMatchers(ignored).requestMatchers(HttpMethod.GET, ignoredGet);
        return web -> web.ignoring().requestMatchers(
//                new AntPathRequestMatcher("/api/v1/**", "GET"),
//                new AntPathRequestMatcher("/api/v1/**","POST"),
                new AntPathRequestMatcher("/h2-console/**", "GET"), // Allow GET requests to h2-console
                new AntPathRequestMatcher("/h2-console/**", "POST"),
                new AntPathRequestMatcher("/actuator", "GET")
                , new AntPathRequestMatcher("/actuator/**", "GET")
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*Enable and customize  form based authentication

          Form-based authentication is a common way to authenticate users by presenting them with a login form where they can enter their credentials.

         */


        http.formLogin(formLogin -> Customizer.withDefaults());
        http.formLogin(formLogin -> formLogin.defaultSuccessUrl("http://localhost:3000", true));


//        http.exceptionHandling(config -> config.defaultAuthenticationEntryPointFor(
//                (request, response, ex) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not Authorized"),
//                AntPathRequestMatcher.antMatcher(api)));

//        http.csrf(config -> config.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));

        http.authorizeHttpRequests(config -> config.anyRequest().authenticated());


        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}