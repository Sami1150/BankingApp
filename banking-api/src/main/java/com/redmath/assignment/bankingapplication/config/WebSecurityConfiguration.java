package com.redmath.assignment.bankingapplication.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.*;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.concurrent.TimeUnit;

@EnableMethodSecurity
@CrossOrigin
@Configuration
public class WebSecurityConfiguration  {
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/h2-console/**", "GET"), // Allow GET requests to h2-console
                new AntPathRequestMatcher("/h2-console/**", "POST"),
                new AntPathRequestMatcher("/actuator", "GET")
                , new AntPathRequestMatcher("/actuator/**", "GET")
//                , new AntPathRequestMatcher("/login/**", "GET")
//                , new AntPathRequestMatcher("/logout/**", "POST")
        );
    }

    @Bean
    @CrossOrigin
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http.formLogin(formLogin -> Customizer.withDefaults());

//        http.formLogin(config -> config.successHandler(authenticationSuccessHandler()));
//        http.logout(config->config.loginSuccessHandler((request, response, authentication) -> {}));

//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authe);
        http.authorizeHttpRequests(config -> config.anyRequest().authenticated());
        http.csrf(config -> config.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));
//        CsrfToken token = new HttpSessionCsrfTokenRepository().loadToken();
//        http.rememberMe()  //default to 2 weeks, if it is not present then it stays there for about 30 mins cuz after 30 mins session expires
//                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//                .key("Something VerySuspiciousIsComing");

        http.formLogin(config->config.loginPage("http://localhost:3000/Login"));
//        http.formLogin(formLogin->formLogin.defaultSuccessUrl("http://localhost:3000", true));

        return http.build();

    }
}