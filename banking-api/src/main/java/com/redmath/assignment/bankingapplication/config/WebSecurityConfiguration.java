package com.redmath.assignment.bankingapplication.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.redmath.assignment.bankingapplication.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenIntrospector;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.util.WebUtils;

import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;


@EnableMethodSecurity
@Configuration
public class WebSecurityConfiguration  {
    @Value("${spring.web.security.ignored:/error,/ui/**,/favicon.ico,/h2-console/**}")
    private String[] ignored = { "/error", "/ui/**", "/favicon.ico","/h2-console/**" };

    @Value("${spring.web.security.ignored.get:/h2-console/**,/actuator,/actuator/**}")
    private String[] ignoredGet = { "/h2-console/**","/actuator","/actuator/**" };

    @Value("${spring.web.security.api:/api/**}")
    private String api = "/api/**";

    @Value("${spring.web.security.session.cookie.name:JWT-Token}")
    private String sessionId = "JWT-Token";

    @Value("${spring.web.security.jwt.secret.key:fBnKDJkuDDBeejkgYCK+zz4pcyc+bfrYeTTkOqyj7Uo}")
    private String secretKey = "fBnKDJkuDDBeejkgYCK+zz4pcyc+bfrYeTTkOqyj7Uo";

    @Value("${spring.web.security.session.expiry.seconds:28800}")
    private int sessionExpirySeconds = 28800;

    private final UserService userService;
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public WebSecurityConfiguration(UserService userService) {
        this.userService = userService;
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), "RSA");
        this.jwtEncoder = new NimbusJwtEncoder(new ImmutableSecret<>(secretKeySpec));
        this.jwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec).build();
    }


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
                , new AntPathRequestMatcher("/actuator/**", "GET"),
                new AntPathRequestMatcher("/actuator", "POST")
                , new AntPathRequestMatcher("/actuator/**", "POST"));
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//        http.formLogin(formLogin -> Customizer.withDefaults());
        http.formLogin(config -> config.successHandler(authenticationSuccessHandler()));
//        http
//                .formLogin(config->config.loginPage("/login"));
//        http.formLogin(formLogin->formLogin.defaultSuccessUrl("/", true));


        http.exceptionHandling(config -> config.defaultAuthenticationEntryPointFor(authenticationEntryPoint(),
                AntPathRequestMatcher.antMatcher(api)));


        http.csrf(config -> config.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));
        http.authorizeHttpRequests(config -> config.anyRequest().authenticated());

        http.sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.oauth2ResourceServer(config -> config.opaqueToken(Customizer.withDefaults()));
        http.logout(config -> config.addLogoutHandler(new CookieClearingLogoutHandler(sessionId)));
        return http.build();




//        http.formLogin(config -> config.successHandler(authenticationSuccessHandler()));
//        http.logout(config->config.loginSuccessHandler((request, response, authentication) -> {}));

//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authe);
//        http.authorizeHttpRequests(config -> config.anyRequest().authenticated());
//        http.csrf(config -> config.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler()));
//        CsrfToken token = new HttpSessionCsrfTokenRepository().loadToken();
//        http.rememberMe()  //default to 2 weeks, if it is not present then it stays there for about 30 mins cuz after 30 mins session expires
//                .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//                .key("Something VerySuspiciousIsComing");

//        http.formLogin(config->config.loginPage());
//        http.formLogin(formLogin->formLogin.defaultSuccessUrl("http://localhost:3000", true));


//        return http.build();

    }

    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, ex) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Not Authorized");
    }

    private AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, auth) -> response.addCookie(createSessionCookie(encode(auth)));
    }

    private Cookie createSessionCookie(String token) {
        Cookie cookie = new Cookie(sessionId, token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        return cookie;
    }

    private String encode(Authentication auth) {
        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(auth.getName())
                .id(UUID.randomUUID().toString())
                .expiresAt(LocalDateTime.now().plusSeconds(sessionExpirySeconds).toInstant(ZoneOffset.UTC))
                .issuedAt(LocalDateTime.now().toInstant(ZoneOffset.UTC))
//                .subject(auth.getCredentials().toString())
                .build();
        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(header, claims));

        System.out.print("Token Starts: "+jwt.getTokenValue()+" :Token ends");
        return jwt.getTokenValue();
    }

    @Bean
    public BearerTokenResolver bearerTokenResolver() {
        return request -> resolveBearerToken(WebUtils.getCookie(request, sessionId));
    }

    private String resolveBearerToken(Cookie cookie) {
        String token = null;
        if (cookie != null) {
            token = cookie.getValue();
        }
        return token;
    }

    @Bean
    public OpaqueTokenIntrospector opaqueTokenIntrospector() {
        return token -> introspectorToken(token);
    }

    private OAuth2AuthenticatedPrincipal introspectorToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            UserDetails userDetails = userService.loadUserByUsername(jwt.getId(), jwt.getSubject());
            return new DefaultOAuth2User(userDetails.getAuthorities(), Map.of("sub", userDetails.getUsername()), "sub");
        } catch (Exception e) {
            throw new CredentialsExpiredException(e.getMessage(), e);
        }
    }
}