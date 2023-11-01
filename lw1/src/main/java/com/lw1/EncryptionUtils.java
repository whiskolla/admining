package com.lw1;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

public class EncryptionUtils {

    private static final String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String KEY_HASH_ALGORITHM = "SHA-256";
    private static final String ENCODING = "UTF-8";
    private static final String RANDOM_KEY_VALUE = "123";

    public static String encrypt(String text, String key) throws Exception {
        try {
            byte[] ivBytes = new byte[16];
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            SecretKeySpec secretKeySpec = generateSecretKey(key);

            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);

            byte[] encryptedBytes = cipher.doFinal(text.getBytes(ENCODING));
            return java.util.Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new Exception("Error encrypting data: " + e.getMessage());
        }
    }
    public static String decrypt(String encryptedText, String key) throws Exception {
        try {
            byte[] ivBytes = new byte[16];
            IvParameterSpec iv = new IvParameterSpec(ivBytes);
            SecretKeySpec secretKeySpec = generateSecretKey(key);

            Cipher cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

            byte[] decryptedBytes = cipher.doFinal(java.util.Base64.getDecoder().decode(encryptedText));
            return new String(decryptedBytes, ENCODING);
        } catch (Exception e) {
            throw new Exception("Error decrypting data: " + e.getMessage());
        }
    }
    private static SecretKeySpec generateSecretKey(String key) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance(KEY_HASH_ALGORITHM);
        byte[] keyBytes = (key + RANDOM_KEY_VALUE).getBytes();

        byte[] hashedBytes = digest.digest(keyBytes);
        return new SecretKeySpec(hashedBytes, "AES");
    }
    public static String hashPassWithSalt(String passwordToHash) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //ввести соль
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        //настроить шэш-функциюSHA-512 с солью
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);

        //генерация хеш-пароля
        byte[] hashedPassword = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
        String hp = new String(hashedPassword, "UTF-8");
        return hp;
    }
}
