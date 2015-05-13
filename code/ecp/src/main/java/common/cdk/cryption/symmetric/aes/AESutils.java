package common.cdk.cryption.symmetric.aes;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

import common.cdk.cryption.symmetric.SymmeticEncryption;

/***
 * 
 * @author cdk
 * [已测试]
 *         　AES（Advanced Encryption Standard）：高级加密标准，对称算法，是下一代的加密算法标准，
 *         速度快，安全级别高，目前 AES 标准的一个实现是 Rijndael 算法；
 *         密钥长度为128位
 */
public class AESutils implements SymmeticEncryption{
	private static Logger logger = Logger.getLogger(AESutils.class);
	private static String mode = "AES";//AES/CBC/PKCS5Padding

	public static String getMode() {
		return mode;
	}

	public static void setMode(String modevalue) {
		mode = modevalue;
	}

	/**
	 * 加密
	 * 
	 * @param content
	 *            需要加密的内容
	 * @param password
	 *            加密密码
	 * @return
	 */
	public  byte[] encryption(byte[] datasource, byte[] key) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(mode);
			kgen.init(128, new SecureRandom(key));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec securekey = new SecretKeySpec(enCodeFormat, mode);
			Cipher cipher = Cipher.getInstance(mode);// 创建密码器
			cipher.init(Cipher.ENCRYPT_MODE, securekey);// 初始化
			return cipher.doFinal(datasource);
		} catch (Exception e) {
			System.out.println(" 加密失败");
			logger.error("encryption  failed.",e);
		} 
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param content
	 *            待解密内容
	 * @param password
	 *            解密密钥
	 * @return
	 */
	public  byte[] decryption(byte[] datasource, byte[] key) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(mode);
			kgen.init(128, new SecureRandom(key));
			SecretKey secretKey = kgen.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec securekey = new SecretKeySpec(enCodeFormat, mode);
			Cipher cipher = Cipher.getInstance(mode);// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, securekey);// 初始化
			return cipher.doFinal(datasource);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (BadPaddingException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}


}
