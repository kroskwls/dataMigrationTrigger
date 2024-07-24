package com.migration.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AesUtil {
    @Value("${spring.aes.secretKey}")
    private String SECRET_KEY;

    private Cipher initCipher(int mode) throws Exception {
        SecretKeySpec spec = new SecretKeySpec(SECRET_KEY.getBytes("UTF-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(mode, spec);

        return cipher;
    }

    public String encrypt(String plain) throws Exception {
        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);

        byte[] encrptionBytes = cipher.doFinal(plain.getBytes("UTF-8"));
        return Hex.encodeHexString(encrptionBytes);
    }

    public String decrypt(String encoded) throws Exception {
        Cipher cipher = initCipher(Cipher.DECRYPT_MODE);

        byte[] decrptionBytes = Hex.decodeHex(encoded.toCharArray());
        return new String(cipher.doFinal(decrptionBytes), "UTF-8");
    }
}
