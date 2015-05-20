package common.cdk.config.files.sqlconfig.encryption;

public interface SQLEncryption {
/***
 * 对sql加密
 * @param SQLString
 * @return
 */
	public String encryption(String SQLString);
	
	
}
