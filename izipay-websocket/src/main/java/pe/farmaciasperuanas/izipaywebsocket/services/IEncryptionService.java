package pe.farmaciasperuanas.izipaywebsocket.services;

public interface IEncryptionService {
    String encrypt(String plaintext) throws Exception;
    String decrypt(String encryptedText) throws Exception;
}
