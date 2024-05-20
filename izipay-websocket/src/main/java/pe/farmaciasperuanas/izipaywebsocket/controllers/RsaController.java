package pe.farmaciasperuanas.izipaywebsocket.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import pe.farmaciasperuanas.izipaywebsocket.dto.DecryptedDto;
import pe.farmaciasperuanas.izipaywebsocket.dto.EncryptedDto;
import pe.farmaciasperuanas.izipaywebsocket.services.IEncryptionService;
import pe.farmaciasperuanas.izipaywebsocket.services.impl.EncryptionService;

@RestController
@RequiredArgsConstructor
public class RsaController {

    private final IEncryptionService encryptionService;
	private final ObjectMapper objectMapper;
	
	
	@PostMapping("/encrypt")
    public ResponseEntity<String> encrypt(@RequestBody EncryptedDto encryptedDto) {
        try {
            String encrypt = encryptionService.encrypt(encryptedDto.getMessage());
            return ResponseEntity.ok(encrypt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solicitud incorrecta");
        }
    }
	
	@PostMapping("/decrypt")
    public ResponseEntity<Map<String, Object>> decrypt(@RequestBody DecryptedDto decryptedDto) {
        try {
            String decrypt = encryptionService.decrypt(decryptedDto.getData());
            try {
				Map<String, Object> jsonMap = objectMapper.readValue(decrypt, Map.class);
                return ResponseEntity.ok(jsonMap);
            } catch (Exception e) {
            	Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Internal Server Error: Decrypted data is not a valid JSON");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
            }
        } catch (Exception e) {
        	Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Bad Request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
	
	
	
}
