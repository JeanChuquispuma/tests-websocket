package pe.farmaciasperuanas.izipaywebsocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import pe.farmaciasperuanas.izipaywebsocket.components.JwtAuthFilter;
import pe.farmaciasperuanas.izipaywebsocket.services.impl.JwtService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    @Autowired
    private JwtService jwtService;

    private static final String[] AUTH_WHITELIST = {
            "/hola",
            "/generateToken",
            "/processToken",
            "/gs-guide-websocket/**",
            "/gs-guide-websocket/*"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers(HttpMethod.POST, "/generateToken").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                //.formLogin(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter(), BasicAuthenticationFilter.class);
        http.cors(cors -> cors.disable());
        return http.build();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter();
    }
}