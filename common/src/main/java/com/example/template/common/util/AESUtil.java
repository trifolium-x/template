package com.example.template.common.util;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author trifolium
 * @version 1.0
 */
@Slf4j
public class AESUtil {

    //偏移量
    public static final String OFFSET = "MIICdwIBADANBgkq";

    private AESUtil() {
    }

    /**
     * AES加密
     *
     * @param data 要加密的字符串
     * @param key  加密key
     * @return 加密后的字符串
     */
    public static String encrypt(String data, String key) {
        try {

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

            return Base64Encoder.encode(encrypted);
        } catch (Exception e) {

            log.error(e.getMessage(), e);

            return null;
        }
    }


    /**
     * AES解密
     *
     * @param data 加密后的字符串
     * @param key  加密key
     * @return 解密后的字符串
     */
    public static String decrypt(String data, String key) {
        try {

            byte[] encrypted1 = Base64Decoder.decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
            IvParameterSpec iv = new IvParameterSpec(OFFSET.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            byte[] original = cipher.doFinal(encrypted1);

            return new String(original, StandardCharsets.UTF_8);
        } catch (Exception e) {

            return null;
        }
    }

}
