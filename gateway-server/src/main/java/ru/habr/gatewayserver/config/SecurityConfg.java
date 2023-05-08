package ru.habr.gatewayserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
@Configuration
@EnableWebFluxSecurity
public class SecurityConfg {
    @Autowired
    private ReactiveClientRegistrationRepository registrationRepository;
    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange()
                .anyExchange()
                .authenticated()
                .and()
                .oauth2Login()
                .and()
                .logout()
                .logoutSuccessHandler(oidcLogoutSuccessHandler())
        ;

        return http.build();
    }
    @Bean
    public ServerLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedServerLogoutSuccessHandler successHandler = new OidcClientInitiatedServerLogoutSuccessHandler(registrationRepository);
        successHandler.setPostLogoutRedirectUri("http://localhost:8070/");
        return successHandler;
    }
}
