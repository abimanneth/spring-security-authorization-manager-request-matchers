package com.example.hello_world_security_auth_manager_req_matcher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationManagers;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        AuthorizationManager<RequestAuthorizationContext> adminRoles = AuthorizationManagers.anyOf(
                AuthorityAuthorizationManager.hasRole("ADMIN")
        );
        AuthorizationManager<RequestAuthorizationContext> superRoles = AuthorizationManagers.allOf(
                AuthorityAuthorizationManager.hasRole("ADMIN"),
                AuthorityAuthorizationManager.hasAuthority("REPORT_VIEW")
        );
        AuthorizationManager<RequestAuthorizationContext> managerRoles = AuthorizationManagers.anyOf(AuthorityAuthorizationManager.hasRole("MANAGER"));

        return
                http
                        .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                .requestMatchers("/admin")
                                .access(adminRoles)
                                .requestMatchers("/manager")
                                .access(managerRoles)
                                .requestMatchers("/report")
                                .access(superRoles)
                                .anyRequest()
                                .authenticated())
                        .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.accessDeniedPage("/access-denied"))
                        .formLogin(Customizer.withDefaults())
                        .build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails admin = User.withUsername("admin").password("{noop}admin").roles("ADMIN").build();
        UserDetails superUser = User.withUsername("abi").password("{noop}varghese").authorities("ROLE_ADMIN", "REPORT_VIEW").build();
        UserDetails manager = User.withUsername("manager").password("{noop}manager").roles("MANAGER").build();
        return new InMemoryUserDetailsManager(admin, superUser, manager);
    }
}
