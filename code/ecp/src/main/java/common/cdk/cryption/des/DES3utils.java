package common.cdk.cryption.des;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.log4j.Logger;

/***
 * [已测试] 
 * @author cdk 　 3DES（Triple DES）：是基于DES的对称算法，对一块数据用三个不同的密钥进行三次加密，
 * 									强度更高；
 * 密钥长度：24位
 */
public class DES3utils {
	private static Logger logger = Logger.getLogger(DES3utils.class);
	private static String mode = "DESede";///CBC/PKCS5Padding;

	public static String getMode() {
		return mode;
	}

	public static void setMode(String modev) {
		mode = modev;
	}

	/*****
	 * 加密算法
	 * 
	 * @param datasource
	 *            需要加密的数据
	 * @param password   
	 *            密钥  24
	 * @return
	 */
	public static byte[] encryption(byte[] datasource, byte[] key) {

		try {
			SecureRandom sr = new SecureRandom();
			DESedeKeySpec dks = new DESedeKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(mode);
			cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
			return  cipher.doFinal(datasource);
		} catch (Exception e) {
			System.out.println("加密失败");
			logger.error(e);
			e.printStackTrace();
		}
		return null;
 }

	/*****
	 * 解密算法
	 * 
	 * @param datasource
	 *            需要解密的数据
	 * @param password
	 *            密钥
	 * @return
	 */
	public static byte[] decryption(byte[] datasource, byte[] key) {
		try {
			SecureRandom random = new SecureRandom();
			DESedeKeySpec desKey = new DESedeKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
			SecretKey securekey = keyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance(mode);
			cipher.init(Cipher.DECRYPT_MODE, securekey, random);
			return cipher.doFinal(datasource);
		} catch (Exception e)   {
			System.out.println("解密失败");
			logger.error(e); 
			e.printStackTrace();
		}
		return null;
	}

	/************** test *******************/
	public static void main(String[] args) {
		try {
			// 待加密内容
			String str = "测试内容";
			// 密码，长度要是8的倍数
			String password = "123456789012345678901234";
			System.out.println(password.getBytes("UTF-8").length);
			byte[] result;
			result = DES3utils.encryption(str.getBytes("utf-8"), password.getBytes("UTF-8"));
			 System.out.println("加密后内容为：" + new String(result));
			// 直接将如上内容解密
			byte[] decryResult = DES3utils.decryption(result, password.getBytes("UTF-8"));
			System.out.println("加密后内容为：" + new String(decryResult)); 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
