package application;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class EncryptionController {

    private KeyPair rsaKeyPair; // Store the RSA key pair

    public EncryptionController() {
        try {
            // Generate RSA key pair
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            this.rsaKeyPair = keyGen.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Encrypts the given text using the specified algorithm and password
    public String encrypt(String text, String algorithm, String password) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            if ("RSA".equals(algorithm)) {
                cipher.init(Cipher.ENCRYPT_MODE, rsaKeyPair.getPublic());
            } else {
                SecretKey key = generateKey(algorithm, password);
                cipher.init(Cipher.ENCRYPT_MODE, key);
            }
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Decrypts the given text using the specified algorithm and password
    public String decrypt(String encryptedText, String algorithm, String password) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            if ("RSA".equals(algorithm)) {
                cipher.init(Cipher.DECRYPT_MODE, rsaKeyPair.getPrivate());
            } else {
                SecretKey key = generateKey(algorithm, password);
                cipher.init(Cipher.DECRYPT_MODE, key);
            }
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Generates a SecretKey based on the algorithm and password
    private SecretKey generateKey(String algorithm, String password) throws NoSuchAlgorithmException {
        if ("AES".equals(algorithm) || "DES".equals(algorithm)) {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] keyBytes = digest.digest(password.getBytes());
            int length = "AES".equals(algorithm) ? 16 : 8; // AES uses 16 bytes (128 bits), DES uses 8 bytes (64 bits)
            byte[] keyBytesShortened = new byte[length];
            System.arraycopy(keyBytes, 0, keyBytesShortened, 0, length);
            return new SecretKeySpec(keyBytesShortened, algorithm);
        }
        return null;
    }
}