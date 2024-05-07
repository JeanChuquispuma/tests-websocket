package pe.farmaciasperuanas.izipaywebsocket.config;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class RSAKeyConfiguration {

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public PublicKey publicKey() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:static/public.pem");
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8); PemReader pemReader = new PemReader(reader)) {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PemObject pemObject = pemReader.readPemObject();
            byte[] keyContentAsBytesFromBC = pemObject.getContent();
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(keyContentAsBytesFromBC);
            PublicKey publicKey = keyFactory.generatePublic(pubKeySpec);
            return publicKey;
        }
    }

    @Bean
    public PrivateKey privateKey() throws Exception {
        Resource resource = resourceLoader.getResource("classpath:static/private.pem");
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8); PemReader pemReader = new PemReader(reader)) {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PemObject pemObject = pemReader.readPemObject();
            byte[] keyContentAsBytesFromBC = pemObject.getContent();
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(keyContentAsBytesFromBC);
            PrivateKey privateKey = keyFactory.generatePrivate(privKeySpec);
            return privateKey;
        }
        /*byte[] privateKeyBytes = loadKeyBytes("static/private.pem");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);*/
    }

    private byte[] loadKeyBytes(String filename) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + filename);
        return Files.readAllBytes(resource.getFile().toPath());
    }
}
