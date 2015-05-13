package common.cdk.cryption.symmetric;

public interface SymmeticEncryption {
	 public  byte[] encryption(byte[] datasource, byte[] key) ;
	 public  byte[] decryption(byte[] datasource, byte[] key) ;
}
