package pe.farmaciasperuanas.izipaywebsocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pe.farmaciasperuanas.izipaywebsocket.components.JwtAuthFilter;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    public static final String[] AUTH_WHITELIST = {
            "/generateToken",
            "/gs-guide-websocket/**",
            "/gs-guide-websocket/*"
    };

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
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
    UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user")
                .password("{noop}password") // {noop} indica que no se realiza ningún cifrado en la contraseña
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("admin")
                .password("{noop}admin")
                .roles("ADMIN")
                .build());
        return manager;
    }

    @Bean
    JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter();
    }
}