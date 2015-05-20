package common.cdk.config.files.sqlconfig.encryption;

/***
 * 若对SQL进行了密码，则另解密类继承此接口
 * 
 * @author cdk
 *
 */
public interface SQLDecryption {
	/***
	 * 对SQL解密，它将在获取sql时被调用 
	 * @param SQLString
	 * @return
	 */
 public String decryption(String SQLString);
}
