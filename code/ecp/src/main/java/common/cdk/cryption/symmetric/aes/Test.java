package common.cdk.cryption.symmetric.aes;

import java.io.UnsupportedEncodingException;

import common.cdk.cryption.fileUtils.FileUtils;

public class Test {

	/***
	 * 
	 * @param args
	 * @throws Exception 
	 */
		public static void main(String[] args) throws Exception {
			try {
				String fileName="D:file1";
				AESutils aesutil=new AESutils();
				
				String content = "中文在测试中，动作：加密！！！";
				String password = "12345678123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
				System.out.println(password.length());
				// 加密
				System.out.println("加密前：" + content);
				byte[]  encryptResult = aesutil.encryption(content.getBytes("UTF-8"), password.getBytes("UTF-8"));
				FileUtils.saveFile(encryptResult, fileName);
				
				Object tobject=FileUtils.readFromFile(fileName);
				byte[] zvs=(byte[]) tobject;
				System.out.println("加密后："+new String(zvs));
				// 解密
				byte[] decryptResult = aesutil.decryption(zvs, password .getBytes("UTF-8"));
				System.out.println("解密后：" + new String(decryptResult));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
}
