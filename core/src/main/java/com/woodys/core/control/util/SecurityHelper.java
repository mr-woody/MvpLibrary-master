package com.woodys.core.control.util;

import android.util.Base64;

import com.woodys.core.control.logcat.Logcat;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class SecurityHelper {
    private final static int ITERATIONS = 20;
    private final static int DES_SALT_LENGTH = 8;//这个必须是8位,DES算法限制问题

    /**
     * PBEWithMD5AndDES加密算法
     * @param key
     * @param plainText
     * @return
     */
    public static String encryptPBE(String key, String plainText){
        String encryptTxt = "";
        try {
            byte[] salt = new byte[8];
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(key.getBytes());
            byte[] digest = md.digest();
            for (int i = 0; i < 8; i++) {
                salt[i] = digest[i];
            }
            PBEKeySpec pbeKeySpec = new PBEKeySpec(key.toCharArray());
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("PBEWithMD5AndDES");
            SecretKey skey = keyFactory.generateSecret(pbeKeySpec);
            PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATIONS);
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            cipher.init(Cipher.ENCRYPT_MODE, skey, paramSpec);
            byte[] cipherText = cipher.doFinal(plainText.getBytes());
            String saltString = new String(Base64.encode(salt,Base64.URL_SAFE | Base64.NO_WRAP));
            String ciphertextString = new String(Base64.encode(cipherText,Base64.URL_SAFE | Base64.NO_WRAP));
            return saltString + ciphertextString;
        } catch (Exception e) {
            Logcat.e("encryptPBE Text Error:" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * PBEWithMD5AndDES解密算法
     * @param key
     * @param encryptTxt
     * @return
     */
    public static String decryptPBE(String key, String encryptTxt) {
        int saltLength = 12;
        try {
            String salt = encryptTxt.substring(0, saltLength);
            String ciphertext = encryptTxt.substring(saltLength, encryptTxt
                    .length());
            byte[] saltarray = Base64.decode(salt.getBytes(),Base64.URL_SAFE | Base64.NO_WRAP);
            byte[] ciphertextArray = Base64.decode(ciphertext.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
            PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray());
            SecretKeyFactory keyFactory = SecretKeyFactory
                    .getInstance("PBEWithMD5AndDES");
            SecretKey skey = keyFactory.generateSecret(keySpec);
            PBEParameterSpec paramSpec = new PBEParameterSpec(saltarray,
                    ITERATIONS);
            Cipher cipher = Cipher.getInstance("PBEWithMD5AndDES");
            cipher.init(Cipher.DECRYPT_MODE, skey, paramSpec);
            byte[] plaintextArray = cipher.doFinal(ciphertextArray);
            return new String(plaintextArray);
        } catch (Exception e) {
            Logcat.e("decryptPBE Text Error:" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * DES加密算法
     * @param key
     * @param plainText
     * @return
     */
    public static String encryptDES(String key, String plainText) {
        try {
            byte[] salt = new byte[8];
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(key.getBytes());
            byte[] digest = md.digest();
            for (int i = 0; i < 8; i++) {
                salt[i] = digest[i];
            }
            String saltString = new String(Base64.encode(salt, Base64.URL_SAFE | Base64.NO_WRAP));
            IvParameterSpec zeroIv = new IvParameterSpec((saltString.substring(0,DES_SALT_LENGTH)).getBytes());
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key skey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey, zeroIv);
            byte[] cipherText = cipher.doFinal(plainText.getBytes());
            String ciphertextString = new String(Base64.encode(cipherText,Base64.URL_SAFE | Base64.NO_WRAP));
            return saltString + ciphertextString;
        }catch (Exception e) {
            Logcat.e("encryptDES Text Error:" + e.getMessage(), e);
            return null;
        }
    }

    /**
     * DES解密算法
     * @param key
     * @param encryptTxt
     * @return
     */
    public static String decryptDES(String key, String encryptTxt) {
        int saltLength = 12;
        try {
            String salt = encryptTxt.substring(0, DES_SALT_LENGTH);
            String ciphertext = encryptTxt.substring(saltLength, encryptTxt.length());
            byte[] ciphertextArray = Base64.decode(ciphertext.getBytes(), Base64.URL_SAFE | Base64.NO_WRAP);
            IvParameterSpec zeroIv = new IvParameterSpec(salt.getBytes());
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key skey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey, zeroIv);
            byte[] plaintextArray = cipher.doFinal(ciphertextArray);
            return new String(plaintextArray);
        } catch (Exception e) {
            Logcat.e("decryptDES Text Error:" + e.getMessage(), e);
            return null;
        }
    }
}