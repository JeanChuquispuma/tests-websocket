package pe.farmaciasperuanas.izipaywebsocket.components;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import pe.farmaciasperuanas.izipaywebsocket.services.IAbaxBackendJwtService;
import pe.farmaciasperuanas.izipaywebsocket.services.impl.AbaxBackendJwtService;
import pe.farmaciasperuanas.izipaywebsocket.services.impl.JwtService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);
    
    @Autowired
	private AbaxBackendJwtService abaxBackendJwtService;

    public JwtAuthFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
    	String authHeader = request.getHeader("Authorization");
        
        if(request.getRequestURI().startsWith("/encrypt") || request.getRequestURI().startsWith("/decrypt")) {
        	
        	
        	if(authHeader != null && authHeader.startsWith("Bearer ")) {
        		String token = authHeader.substring(7);
        		if(abaxBackendJwtService.validateAuthHeaderJwt(token)){
        			Authentication authentication = getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
        		}else {
        			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
        			 return;
        		}
            } else if(authHeader != null && authHeader.startsWith("Basic ")){
                filterChain.doFilter(request, response);
            } else {
            	response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                return;
            }
        }else {
        	filterChain.doFilter(request, response);
        }
    }

    private Authentication getAuthentication(String token) {
        String username = "GUEST";
        List<String> roles = List.of("ROLE_USER");

        // Convertir los roles a una lista de GrantedAuthority
        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        // Crear una instancia de UserDetails representando al usuario autenticado
        User principal = new User(username, "", authorities);

        // Crear una instancia de Authentication con el usuario autenticado y sus roles
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
