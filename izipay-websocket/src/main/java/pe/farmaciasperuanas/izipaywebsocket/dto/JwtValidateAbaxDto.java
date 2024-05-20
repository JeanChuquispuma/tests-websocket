package pe.farmaciasperuanas.izipaywebsocket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtValidateAbaxDto {
	private boolean valid;
	private String message;
}
