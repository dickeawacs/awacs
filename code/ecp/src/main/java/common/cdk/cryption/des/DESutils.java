package common.cdk.cryption.des;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;

/***
 * [已测试] 
 * @author cdk 　
 * @see DES（Data Encryption Standard）：对称算法，数据加密标准，速度较快，适用于加密大量数据的场合;
 *         DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。
 *         DES加密算法出自IBM的研究，后来被美国政府正式采用，之后开始广泛流传，
 *         但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，24小时内即可被破解。
 *         虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现。
 * 密钥长度：8位
 */
public class DESutils {
	private static Logger logger = Logger.getLogger(DES3utils.class);
	private static String mode = "DES";///CBC/PKCS5Padding;

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
			DESKeySpec dks = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
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
			DESKeySpec desKey = new DESKeySpec(key);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
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
			String password = "1234567890";
			System.out.println(password.getBytes("UTF-8").length);
			byte[] result;
			result = DESutils.encryption(str.getBytes("utf-8"), password.getBytes("UTF-8"));
			 System.out.println("加密后内容为：" + new String(result));
			// 直接将如上内容解密
			byte[] decryResult = DESutils.decryption(result, password.getBytes("UTF-8"));
			System.out.println("加密后内容为：" + new String(decryResult)); 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
