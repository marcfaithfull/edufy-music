package org.example.edufymusic.config;

import org.example.edufymusic.converters.JwtAuthConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    private final JwtAuthConverter jwtAuthConverter;

    @Autowired
    public SecurityConfig(JwtAuthConverter jwtAuthConverter) {
        this.jwtAuthConverter = jwtAuthConverter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(withDefaults())
                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.GET, "/api/v1/music/**").hasAnyRole("edufy_USER", "edufy_ADMIN")
                                .requestMatchers("/api/v1/music/**").hasRole("edufy_ADMIN")
                                .anyRequest().authenticated()
                        //.requestMatchers("/**").permitAll()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter))
                )
                .csrf(csrf -> csrf
                                .disable()
                        //.ignoringRequestMatchers("/h2-console/**")
                        //.ignoringRequestMatchers("/**")
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable
                        )
                );
        return http.build();
    }
}
