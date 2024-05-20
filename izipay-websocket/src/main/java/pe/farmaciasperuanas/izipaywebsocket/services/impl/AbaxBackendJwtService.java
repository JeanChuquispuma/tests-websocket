package pe.farmaciasperuanas.izipaywebsocket.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import pe.farmaciasperuanas.izipaywebsocket.client.AbaxBackendClient;
import pe.farmaciasperuanas.izipaywebsocket.dto.JwtValidateAbaxDto;
import pe.farmaciasperuanas.izipaywebsocket.services.IAbaxBackendJwtService;

@Service
@RequiredArgsConstructor
public class AbaxBackendJwtService implements IAbaxBackendJwtService{

    private final AbaxBackendClient abaxBackendClient;
    
    private static final Logger log = LoggerFactory.getLogger(AbaxBackendJwtService.class);
	@Override
	public boolean validateAuthHeaderJwt(String bearerToken) {
		try {
			ResponseEntity<JwtValidateAbaxDto> response = abaxBackendClient.jwtValidate("Bearer "+bearerToken);
			if (response.getStatusCode().is2xxSuccessful()) {
	             return response.getBody().isValid();
	        }
			return false;
		}catch (Exception e) {
			log.info("ERROR en validateAuthHeaderJwt: {}", e.getMessage());
			return false;
		}
	}

}
