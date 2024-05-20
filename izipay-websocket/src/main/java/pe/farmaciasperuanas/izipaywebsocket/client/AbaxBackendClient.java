package pe.farmaciasperuanas.izipaywebsocket.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import pe.farmaciasperuanas.izipaywebsocket.dto.JwtValidateAbaxDto;

@FeignClient(name = "${abax-backend.name}", url = "${abax-backend.host}")
public interface AbaxBackendClient {

	@PostMapping(value="/jwt/validate", consumes = APPLICATION_JSON_VALUE)
	ResponseEntity<JwtValidateAbaxDto> jwtValidate(@RequestHeader("Authorization") String authorizationHeader);
	
}
