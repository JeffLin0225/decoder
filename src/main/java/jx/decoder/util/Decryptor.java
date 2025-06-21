package jx.decoder.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class Decryptor {
    @Value("${spring.decrypt.key}")
    private String KEY;
    @Value("${spring.decrypt.iv}")
    private String IV;

    public String decrypt(String encoded) throws Exception {
        // Base64 解碼
        byte[] ciphertext = Base64.getDecoder().decode(encoded);

        // 初始化 AES 解密器
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        // 解密並移除填充
        byte[] decrypted = cipher.doFinal(ciphertext);
        return new String(decrypted);
    }
}
