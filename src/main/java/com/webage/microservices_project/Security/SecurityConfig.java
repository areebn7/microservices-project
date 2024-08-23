package com.webage.microservices_project.Security;

import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration class for the application, setting up security measures
 * and configuring the application as a Resource Server.
 * <p>
 * This class establishes the application as a Resource Server that expects each request
 * to include an Authorization header with a JWT (JSON Web Token) as a Bearer token.
 * The JWT's signature is verified using a public key.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${rsa.public-key}")
    private RSAPublicKey publicKey;

    /**
     * Creates a {@link JwtDecoder} using the public key for decoding and validating JWTs.
     * 
     * @return A {@link JwtDecoder} instance for decoding JWTs.
     */
    private JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(publicKey).build();
    }

    /**
     * Configures the security settings for the application.
     * 
     * @param http The {@link HttpSecurity} object to configure.
     * @return A {@link SecurityFilterChain} instance configured for the application.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // Disable sessions to create a stateless application
            .sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) 

            // Disable CSRF protection as sessions are disabled
            .csrf(csrf -> csrf.disable())

            // Require authentication for all inbound requests except those to the root
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated()
            )

            // Configure the application to use JWTs for authorization
            .oauth2ResourceServer(resourceServer ->
                resourceServer.jwt(customizer -> customizer.decoder(jwtDecoder()))
            )
            .build();
    }    

}
