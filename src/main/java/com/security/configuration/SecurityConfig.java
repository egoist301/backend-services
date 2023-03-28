package com.security.configuration;

import com.security.handler.CustomAuthenticationFailureHandler;
import com.security.model.ERole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String BCRYPT = "bcrypt";
    private static final String VIEW_ADMIN = ERole.VIEW_ADMIN.name();
    private static final String VIEW_INFO = ERole.VIEW_INFO.name();
    private static final String[] PUBLIC_URLS = {"/about", "/login*"};
    private static final String[] ADMIN_URLS = {"/admin", "/blocked"};


    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http, CustomAuthenticationFailureHandler authenticationFailureHandler) throws Exception {
        return http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .requestMatchers(PUBLIC_URLS).permitAll()
                                .requestMatchers("/info").hasAnyAuthority(VIEW_INFO, VIEW_ADMIN)
                                .requestMatchers(ADMIN_URLS).hasAnyAuthority(VIEW_ADMIN)
                                .anyRequest().authenticated()
                )
                .formLogin(formLogin ->
                        formLogin.loginPage("/login")
                                .failureHandler(authenticationFailureHandler)
                                .permitAll())
                .logout(formLogout -> formLogout.deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/logoutSuccess")
                        .permitAll())
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public DelegatingPasswordEncoder passwordEncoder() {
        Map<String, PasswordEncoder> passwordEncoders = new HashMap<>();
        passwordEncoders.put(BCRYPT, new BCryptPasswordEncoder());

        return new DelegatingPasswordEncoder(BCRYPT, passwordEncoders);
    }

}
