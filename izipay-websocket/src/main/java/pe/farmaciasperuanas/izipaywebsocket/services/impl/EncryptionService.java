package pe.farmaciasperuanas.izipaywebsocket.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import pe.farmaciasperuanas.izipaywebsocket.components.JwtAuthFilter;
import pe.farmaciasperuanas.izipaywebsocket.services.IEncryptionService;

import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.util.Base64;

@Service
public class EncryptionService implements IEncryptionService {
    private static final Logger log = LoggerFactory.getLogger(EncryptionService.class);

    @Autowired
    private PublicKey publicKey;
    @Autowired
    private PrivateKey privateKey;

    public String encrypt(String plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedText) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }
}
