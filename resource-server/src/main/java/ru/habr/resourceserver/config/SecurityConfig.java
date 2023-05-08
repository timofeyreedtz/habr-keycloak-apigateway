package ru.habr.resourceserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private  JwtAuthConverter jwtAuthConverter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> {
                            try {
                                authz
                                        .requestMatchers("/admin").hasRole("ADMIN")
                                        .requestMatchers("/user").hasAnyRole("USER")
                                        .anyRequest().authenticated()
                                        .and()
                                        .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                                        .and()
                                        .oauth2ResourceServer()
                                        .jwt()
                                        .jwtAuthenticationConverter(jwtAuthConverter);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
        return http.build();
    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new CustomAccessDeniedHandler();
    }
}
