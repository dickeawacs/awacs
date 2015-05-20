package common.cdk.cryption.blowfish;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/***
 * 
 * @author cdk
 *   Blowfish
 *　BLOWFISH，它使用变长的密钥，长度可达448位，运行速度很快；  
 */
public class BlowfishUtils {/*       // 密钥
    public static final String ENCRYPT_KEY = "WkoxWT0kJik=";
    // 初始化向量
    public static final String INITIALIZATION_VECTOR = "cnBHdE9F";
    // 转换模式
    public static final String TRANSFORMATION = "Blowfish/CBC/PKCS5Padding";
    // 密钥算法名称
    public static final String BLOWFISH = "Blowfish";
    *//**
     * 加密
     *
     * @param key
     *            密钥
     * @param text
     *           加密文本
     * @param initializationVector
     *           初始化向量
     *//*
    public static String encrypt(byte[] datasource, byte[] key, String initializationVector)
         throws Exception {
           // 根据给定的字节数组构造一个密钥  Blowfish-与给定的密钥内容相关联的密钥算法的名称
           SecretKeySpec sksSpec = new SecretKeySpec(key , BLOWFISH);
           // 使用 initializationVector 中的字节作为 IV 来构造一个 IvParameterSpec 对象
           AlgorithmParameterSpec iv = new IvParameterSpec(initializationVector.getBytes());
           // 返回实现指定转换的 Cipher 对象
           Cipher cipher = Cipher.getInstance(TRANSFORMATION);
           // 用密钥和随机源初始化此 Cipher
           cipher.init(Cipher.ENCRYPT_MODE, sksSpec, iv);
           // 加密文本
           byte[] encrypted = cipher.doFinal(text.getBytes());
           return new String(Hex.encodeHex(encrypted));
    }
    *//**
     * 解密
     *
     * @param key
     *            密钥
     * @param text
     *           加密文本
     * @param initializationVector
     *           初始化向量
     *//*
    public static String decrypt(String key, String text, String initializationVector)
         throws Exception {
           byte[] encrypted = null;
           try{
                  encrypted = Hex.decodeHex(text.toCharArray());
           } catch (Exception e)
           {
                  e.printStackTrace();
           }
           SecretKeySpec skeSpect = new SecretKeySpec(key.getBytes(), BLOWFISH);
           AlgorithmParameterSpec iv = new IvParameterSpec(initializationVector.getBytes());
           Cipher cipher = Cipher.getInstance(TRANSFORMATION);
           cipher.init(Cipher.DECRYPT_MODE, skeSpect, iv);
           byte[] decrypted = cipher.doFinal(encrypted);
           return new String(decrypted);
    }
    *//**
     * Base64字符解码
     *
     * @param base64String
     *            -- 被解码字符
     * @return 解码后字符
     *//*
    public static String base64Decoder(String base64String) {
           if(StringUtils.isEmpty(base64String))
           {
                  return base64String;
           }
           else
           {
                  return new String(Base64.decodeBase64(base64String));
           }   
    }
    *//**
     * Base64字符编码
     *
     * @param sourceString
     *            -- 字符
     * @return 编码后字符
     *//*
    public static String base64Encoder(String sourceString) {
           return sourceString;
    }
    *//**
     * @param 测试方法
     *//*
    public static void main(String[] args) {
           // TODO Auto-generated method stub
           try {
                  String encryptStr = base64Encoder(encrypt(ENCRYPT_KEY, "tester ", INITIALIZATION_VECTOR));
                  System.out.print("tester加密后得到：" + encryptStr +"n");
                  String decryptStr = decrypt(ENCRYPT_KEY, base64Decoder(encryptStr), INITIALIZATION_VECTOR);
                  System.out.print(encryptStr+"解密后得到："+decryptStr);
           } catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
           }
    }


*/}
