package com.thalesgroup.tshpaysample.logic.network;

import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



public class ThreeDexEncryption {
    public static String decryptData(String encryptedData, String issuerSecret) throws Exception {
//        Security.addProvider(new BouncyCastleProvider());
//        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", BouncyCastleProvider.PROVIDER_NAME);
//        cipher.init(Cipher.DECRYPT_MODE, convertToSecret(issuerSecret), getIvParamSpec());
//
//        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
//        System.out.println(decodedData);
//        byte[] decryptedData = cipher.doFinal(decodedData);
//        return new String(decryptedData, "UTF-8");
        return  "jjjjjjjjjjjjjjjj";
    }
    private static AlgorithmParameterSpec getIvParamSpec() { byte[] initVector = new byte[16]; Arrays.fill(initVector, (byte) 0x0); return new IvParameterSpec(initVector);
    }
    private static SecretKeySpec convertToSecret(String itgSecretKey) { return new SecretKeySpec(itgSecretKey.getBytes(), "AES"); }
}
