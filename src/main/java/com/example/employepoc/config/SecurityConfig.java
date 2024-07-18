package com.example.employepoc.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
//@ComponentScan({"com.hydatis.cqrs.command.infrastructure.exceptions"})
@SecurityScheme(name = "keycloak", type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",

        scheme = "bearer",
        flows = @OAuthFlows(clientCredentials = @OAuthFlow(authorizationUrl = "http://localhost:8080/auth/",
                tokenUrl = "http://localhost:8080/realms/timestamp-realm/protocol/openid-connect/token"
        )))
@EnableWebSecurity
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {
    @Autowired
    RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    CustomKeycloakAuthenticationHandler customKeycloakAuthenticationHandler;

    @Autowired
    TokenAuthenticationFailureHandler tokenAuthenticationFailureHandler;



    /**
     * Configures HTTP security for the application, specifying access rules for different endpoints.
     * It disables CSRF protection and CORS configuration, authorizes access to specific endpoints,
     * and enforces authentication for all other requests. It also sets up an access-denied handler.
     *
     * @param http The HTTP security configuration.
     * @throws Exception If an error occurs during configuration.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable().cors().disable()
                .authorizeRequests()
                .antMatchers("/checking/create").hasRole("user")
                .antMatchers("/ws-endpoint/**").permitAll()
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest()
                .authenticated();

        http.exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(new CustomTokenAuthenticationEntryPoint(tokenAuthenticationFailureHandler));

    }

    /**
     * Configures the global authentication manager to use Keycloak for authentication.
     * It sets up the KeycloakAuthenticationProvider and specifies how authorities are mapped.
     *
     * @param auth The AuthenticationManagerBuilder.
     * @throws Exception If an error occurs during configuration.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    /**
     * Specifies the session authentication strategy for the application.
     * It returns a RegisterSessionAuthenticationStrategy using a SessionRegistryImpl.
     *
     * @return The session authentication strategy.
     */
    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    /**
     * Creates and configures the KeycloakAuthenticationProcessingFilter.
     * It sets the authentication manager, session authentication strategy, and authentication failure handler.
     *
     * @return The KeycloakAuthenticationProcessingFilter.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    @Override
    protected KeycloakAuthenticationProcessingFilter keycloakAuthenticationProcessingFilter() throws Exception {
        KeycloakAuthenticationProcessingFilter filter = new KeycloakAuthenticationProcessingFilter(this.authenticationManagerBean());
        filter.setSessionAuthenticationStrategy(this.sessionAuthenticationStrategy());
        filter.setAuthenticationFailureHandler(customKeycloakAuthenticationHandler);
        return filter;
    }

}