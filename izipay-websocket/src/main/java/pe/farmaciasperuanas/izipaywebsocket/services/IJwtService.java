package pe.farmaciasperuanas.izipaywebsocket.services;

import io.jsonwebtoken.Claims;
//import org.springframework.security.core.userdetails.UserDetails;
import pe.farmaciasperuanas.izipaywebsocket.dto.Device;

import java.util.Date;
import java.util.function.Function;

public interface IJwtService {
    String generateToken(Device device);
    String extractUsername(String token);
    Date extractExpiration(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    //Boolean validateToken(String token, UserDetails userDetails);
}
