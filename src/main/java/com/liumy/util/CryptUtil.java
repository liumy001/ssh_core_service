package com.liumy.util;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;




/**
 * 加密解密算法工具类，其中包含 DES/3DES 两种算法
 * @author chenht
 *
 */
public class CryptUtil {
	private static Logger errLog = Logger
	.getLogger("com.hispeed.exception.CryptException");
	private static Logger mesLog = Logger
	.getLogger("com.hispeed.exception.CryptException");
	private String key; // 密钥值
	private SecretKey secretKey;
	private static final String HEX_CHARS = "0123456789ABCDEF";

	public void setDesKey( String key ) throws Exception{
		if (!StringUtil.isEmpty(key) ) {
			this.key = key;
			byte[] staticKey = key.getBytes();
			try {
				SecretKeyFactory keyfact = SecretKeyFactory.getInstance( "DES" );
				DESKeySpec dks = new DESKeySpec( staticKey ); 
				this.secretKey = keyfact.generateSecret( dks );
			} catch ( Exception e ) {
				throw new Exception( "设置 DES 密钥出错，key=" + key, e );
			}		   
		}
	}
	
	public void setDes3Key( String key ){
		if (!StringUtil.isEmpty(key) ) {
			this.key = key;
			byte[] staticKey = key.getBytes();
			this.secretKey =  new SecretKeySpec( staticKey, "DESede" );  
		}
	}
	
	/**
	 * DES 加密
	 * @param src
	 * @return
	 * @throws Exception 
	 * @throws CryptException
	 */
	public String desEncrypt( String src ) throws Exception {
		try {
			return encrypt( "DES/ECB/PKCS5Padding", this.secretKey, src );
		} catch ( Exception e ) {
			throw new Exception( "DES 加密失败，src=" + src + "，key=" + this.key, e );
		}
	}

	/**
	 * DES 解密
	 * @param src
	 * @return
	 * @throws Exception 
	 * @throws CryptException
	 */
	public String desDecrypt( String dst ) throws Exception {
		try {
			return decrypt( "DES/ECB/PKCS5Padding", this.secretKey, dst );
		} catch ( Exception e ) {
			throw new Exception( "DES 解密失败，src=" + dst + "，key=" + this.key, e );
		}
	}

	/**
	 * DES3 加密
	 * @param src
	 * @return
	 * @throws CryptException
	 */
	public String des3Encrypt( String src ) throws Exception {
		try {
			return encrypt( "DESede/ECB/PKCS5Padding", this.secretKey, src );
		} catch ( Exception e ) {
			throw new Exception( "DES3 加密失败，src=" + src + "，key=" + this.key, e );
		}
	}	

	/**
	 * DES3 解密
	 * @param src
	 * @return
	 * @throws CryptException
	 */
	public String des3Decrypt( String dst ) throws Exception {
		try {
			mesLog.info("解密开始");
			return decrypt( "DESede/ECB/PKCS5Padding", this.secretKey, dst );
		} catch ( Exception e ) {
			errLog.error("解密异常可能是模拟的秘钥或密码不正确");
			throw new Exception( "DES 解密失败，dst=" + dst + "，key=" + this.key, e );
		}
	}
	
	/**
	 * 加密
	 * @param transformation 转换的名称，例如 DES/CBC/PKCS5Padding
	 * @param secretKey 密钥对象
	 * @param src 加密源串
	 * @return
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	private String encrypt( String transformation, SecretKey secretKey, String src ) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		String str = "";
		Cipher cipher = Cipher.getInstance( transformation );
		cipher.init( Cipher.ENCRYPT_MODE, secretKey );
		byte[] encoded = cipher.doFinal( src.getBytes() );
		for( int i = 0; i < encoded.length; i++ ){
			str += byteToHex( encoded[ i ] );
		}
		return str;	   
	}
	
	private String byteToHex( byte b ) {
		char digest[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] ch = new char[ 2 ];
		ch[0] = digest[ ( b >>> 4 & 0X0F ) ];
		ch[1] = digest[ b & 0X0F ];
		return new String( ch );
	}
	
	/**
	 * 解密
	 * @param transformation 转换的名称，例如 DES/CBC/PKCS5Padding
	 * @param secretKey 密钥对象
	 * @param dst 加密串
	 * @return
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	private String decrypt( String transformation, SecretKey secretKey, String dst ) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher c1 = Cipher.getInstance( transformation );
		c1.init( Cipher.DECRYPT_MODE, secretKey );
		byte[] ByteRequest = hexStringToByte( dst );
		byte[] decoded = c1.doFinal( ByteRequest );
		return new String( decoded );
	}

	private byte[] hexStringToByte( String hex ) {  
		int len = ( hex.length() / 2 );  
		byte[] result = new byte[ len ];  
		char[] achar = hex.toCharArray();  
		for ( int i = 0; i < len; i++ ) {  
			int pos = i * 2;  
			result[ i ] = ( byte ) ( toByte( achar[ pos ] ) << 4 | toByte( achar[ pos + 1 ] ) );  
		}  
		return result;  
	}  

	private byte toByte( char c ) {  
		byte b = ( byte ) HEX_CHARS.indexOf( c );  
		return b;  
	}  

	public static void main( String[] args ) {
		CryptUtil c = new CryptUtil();
		try {
			c.setDes3Key( "InyocBykGheenyizCoadlods" );
			System.out.println( c.des3Encrypt( "28992591" ) );
			System.out.println( c.des3Decrypt( "95C428F948B02470AE44850EFE2C23CF72B4A05C0742AF65" ) );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
		try {
			c.setDesKey( "11111111" );
			System.out.println( c.desEncrypt( "1234567890123456789" ) );
			System.out.println( c.desDecrypt( "858B176DA8B1250350C6F225C4EA4EC3E7A874886A714969" ) );
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
}
