package edu.sabIA.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req ->
                req.requestMatchers(
                    "/v1/api/**",
                    "/v2/api-docs",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-resources",
                    "/swagger-resources/**",
                    "/swagger-ui/index.html",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-ui.html",
                    "/users",
                    "/users/**",
                    "/quizzes",
                    "/quizzes/**"
                ).permitAll()
                .anyRequest()
                .authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
        return http.build();
    }
}