package common.cdk.login.rsa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;


/***
 * @see 产生公钥和私钥对，并且保存在文件中，公钥 pk.dat，私钥 sk.dat
 * @author Administrator
 ** */
public class KeyGen {
	private String encoding="ISO-8859-1";//"UTF-8"
	/**
	 * 创建加密文件的公钥和密钥
	 * @param keyInfo  加密种子
	 * @param publicName 公钥文件名称
	 * @param privateName 密钥文件名称
	 * @throws Exception 
	 */
	public void buildKeyFile(String keyInfo,String publicName,String privateName) throws Exception {
		if(keyInfo!=null&&keyInfo.getBytes(encoding).length>=512) {
			KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = new SecureRandom();
			random.setSeed(keyInfo.getBytes()); // 初始加密，长度为512，必须是大于512才可以的
			keygen.initialize(keyInfo.getBytes().length, random); // 取得密钥对
			
			KeyPair kp = keygen.generateKeyPair(); //密钥对工具
			
			PublicKey publicKey = kp.getPublic();// 取得公钥
			System.out.println("key pair publicKey:"+publicKey.toString());
			saveFile(publicKey,publicName); 
			
			PrivateKey privateKey = kp.getPrivate();// 取得私钥
			saveFile(privateKey, privateName);
			System.out.println("key pair privateKey:"+privateKey);
			
			
		}else 
			throw new Exception("加密种子的字节总长度必须大于或等于 512");
	}
	/***
	 * 保存种子文件
	 * @param obj
	 * @param fileName
	 * @throws Exception
	 */
	private void saveFile(Object obj, String fileName) throws Exception {
		createFile(fileName);
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName));
		output.writeObject(obj);
		output.close();
	}
	public  boolean createFile(String filePath) {
		boolean mk = false;
		try {
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.getParentFile().mkdirs();
				mk = myFilePath.createNewFile();
			} else
				mk = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mk;
	}
	/** * * 
	 * @param args 
	 * @throws Exception 
	 * */
/*	public static void main(String[] args) throws Exception {
		// 加密的种子信息
		String keyInfo = "007841296p@sswa0rdTZJKeyPaQWERTYUIOPASDFGHJKLZXCVBNM,.12345678908796454514568764DLKSDFIJKFDKLOINMLKERT;LIDFGS;KLJFSGD;IOSUET;KGLJFSGVISDRKJHKJSADFGKWHGEFRJASDRKJHGYETFIVASWETGKirGeneratorbyRonRivestAdiShamirhand LenAdleman developed (Massachusetts Institute of Technology). RSA name from the development of their three names. RSA public key encryption algorithm is the most influential, and it can resist all so far known password attack, has been recommended by the ISO standard for public-key data encryption. RSA algorithm base";
		System.out.println(keyInfo.getBytes().length);
	    KeyGen kg = new KeyGen();
		kg.buildKeyFile(keyInfo,"F:/01tzjreg/public.tzj","F:/01tzjreg/private.tzj");
	}
  */

}