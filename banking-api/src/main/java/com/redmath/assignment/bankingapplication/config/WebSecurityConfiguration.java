package com.redmath.assignment.bankingapplication.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

@EnableMethodSecurity
@CrossOrigin(origins = "http://localhost:3000",methods = {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE,RequestMethod.PUT})
@Configuration
public class WebSecurityConfiguration {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers(
                new AntPathRequestMatcher("/h2-console/**", "GET"), // Allow GET requests to h2-console
                new AntPathRequestMatcher("/h2-console/**", "POST"),
                new AntPathRequestMatcher("/actuator", "GET")
                , new AntPathRequestMatcher("/actuator/**", "GET")
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.formLogin(formLogin -> Customizer.withDefaults());
        http.formLogin(formLogin->formLogin.defaultSuccessUrl("http://localhost:3000", true));
//        http.logout(config->config.logoutSuccessHandler((request, response, authentication) -> {}));
        http.authorizeHttpRequests(config -> config.anyRequest().authenticated());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

}