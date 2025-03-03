package com.trustrace.eventmanagementauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.trustrace.eventmanagementauth.security.jwt.AuthEntryPointJwt;
import com.trustrace.eventmanagementauth.security.jwt.AuthTokenFilter;
import com.trustrace.eventmanagementauth.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
(securedEnabled = true,
jsr250Enabled = true,
prePostEnabled = true)
public class WebSecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configure(http))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/registrations/save").permitAll()
                        .requestMatchers(HttpMethod.POST,"/api/test").permitAll()
                        .requestMatchers("/api/users/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/api/feedback/**").hasAnyRole("ADMIN", "USER","ORGANIZATION")
                        .requestMatchers("/api/registrations/**").hasAnyRole("ADMIN", "USER", "ORGANIZATION")
                        .requestMatchers(HttpMethod.GET, "/api/events/**").hasAnyRole("ADMIN", "USER", "ORGANIZATION")
                        .requestMatchers(HttpMethod.POST, "/api/events/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/organization/**").hasAnyRole( "ORGANIZATION")
                        .requestMatchers(HttpMethod.PATCH, "/api/events/**").hasAnyRole("ADMIN", "USER", "ORGANIZATION")
                        .requestMatchers(HttpMethod.PATCH, "/api/organization/**").hasAnyRole( "ORGANIZATION")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated());
        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}