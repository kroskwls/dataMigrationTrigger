package com.migration.configurations;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.migration.jwt.filter.AuthFilter;
import com.migration.jwt.handler.AuthEntryPoint;
import com.migration.utils.JwtEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private static final String[] PERMIT_URL_ARRAY = {
		"/api/auth/token",
    };
    private static final String[] DENY_URL_ARRAY = {
        "/api/auth/create-account",
        "/v3/api-docs/**",
		"/swagger-ui/**",
		"/api-docs/**",
		"/swagger-ui.html"
    };
    
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    private AuthEntryPoint unauthorizedHandler;

    @Bean
    public AuthFilter authenticationJwtTokenFilter() {
        return new AuthFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(PasswordEncoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new JwtEncoder();
    }

    // @Bean
    // public PasswordEncoder PasswordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
            .cors(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req -> req
                .antMatchers(PERMIT_URL_ARRAY).permitAll()
                .antMatchers(DENY_URL_ARRAY).denyAll()
                .anyRequest()
                .authenticated())
            .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
            .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
