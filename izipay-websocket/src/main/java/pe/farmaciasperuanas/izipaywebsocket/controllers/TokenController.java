package pe.farmaciasperuanas.izipaywebsocket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pe.farmaciasperuanas.izipaywebsocket.dto.Device;
import pe.farmaciasperuanas.izipaywebsocket.dto.EncryptedToken;
import pe.farmaciasperuanas.izipaywebsocket.services.impl.EncryptionService;
import pe.farmaciasperuanas.izipaywebsocket.services.impl.JwtService;

@RestController
public class TokenController {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private EncryptionService encryptionService;

    @PostMapping("/processToken")
    public ResponseEntity<String> processToken(@RequestBody EncryptedToken token) {
        try {
            String tokenDesencriptado = encryptionService.decrypt(token.getToken());
            return ResponseEntity.ok(tokenDesencriptado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solicitud incorrecta");
        }
    }
    
    @PostMapping("/extractUniqueId")
    public ResponseEntity<String> extractUniqueId(@RequestBody EncryptedToken token) {
        try {
            String tokenDesencriptado = encryptionService.decrypt(token.getToken());
            String uniqueId = jwtService.extractUniqueId(tokenDesencriptado);
            return ResponseEntity.ok(uniqueId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solicitud incorrecta");
        }
    }
    
    @PostMapping("/generateToken")
    public ResponseEntity<String> generateToken(@RequestBody Device device) {
        try {
            String token = jwtService.generateToken(device);
            String tokenEncriptado = encryptionService.encrypt(token);
            return ResponseEntity.ok(tokenEncriptado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Solicitud incorrecta");
        }
    }
}
