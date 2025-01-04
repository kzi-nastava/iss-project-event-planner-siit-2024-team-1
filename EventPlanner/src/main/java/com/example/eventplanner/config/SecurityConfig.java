package com.example.eventplanner.config;

import com.example.eventplanner.filter.JwtAuthenticationFilter;
import com.example.eventplanner.services.UserDetailsServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImp userDetailsServiceImp;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomLogoutHandler logoutHandler;

    public SecurityConfig(UserDetailsServiceImp userDetailsServiceImp,
                          JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomLogoutHandler logoutHandler) {
        this.userDetailsServiceImp = userDetailsServiceImp;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.logoutHandler = logoutHandler;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req->req.requestMatchers("/api/v1/auth/login", "/api/v1/auth/refresh_token/**","/api/v1/events/top",
                                        "api/v1/events/search","api/v1/merchandise/top","api/v1/services/search","api/v1/products/search",
                                        "api/v1/events/{id}","api/v1/merchandise/{id}", "/api/v1/notifications/**", "api/v1/photos/{filename}")
                                .permitAll()
                                .anyRequest().permitAll()
                                //.authenticated()
                ).userDetailsService(userDetailsServiceImp)
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        e->e.accessDeniedHandler(
                                        (request, response, accessDeniedException)->response.setStatus(403)
                                )
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .logout(l->l
                        .logoutUrl("/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                        ))
                .build();

    }
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        return http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(
//                        req->req//.requestMatchers("/login/**","/register-eo/**", "/refresh_token/**")
//                                //.permitAll()
//                                //.requestMatchers("/admin_only/**").hasAuthority("ADMIN")
//                                .anyRequest().permitAll()
//                                //.authenticated()
//                ).userDetailsService(userDetailsServiceImp)
//                .sessionManagement(session->session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//                .exceptionHandling(
//                        e->e.accessDeniedHandler(
//                                        (request, response, accessDeniedException)->response.setStatus(403)
//                                )
//                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
//                .logout(l->l
//                        .logoutUrl("/logout")
//                        .addLogoutHandler(logoutHandler)
//                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
//                        ))
//                .build();
//
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


}
