package com.liumy.util;


import java.lang.reflect.Method;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author user
 * @date Jan 16, 2015 11:28:54 AM
 */
public class CommonUtil {

	public static SimpleDateFormat sdf1 = new SimpleDateFormat("yyMMdd");
	public static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
	public static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy-MM");
	public static SimpleDateFormat sdf6 = new SimpleDateFormat("yyMMddHHmmss");
	public static String getDateStr_yyMMdd(){
		return sdf1.format(new Date());
	}

	public static String getDateStr_yyyyMMddHHmmss(){
		return sdf2.format(new Date());
	}
	
	public static String getDateStr_yyyyMMddHHmmss_4_file(){
		return sdf3.format(new Date());
	}

	public static Date getDate_yyyyMMddHHmmss(){
		try {
			return sdf2.parse(sdf2.format(new Date()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String date2String_yyyyMMddHHmmss(Date date){
		return sdf2.format(date);
	}
	
	public static String date2String(Date date){
		return sdf4.format(date);
	}
	
	public static String date2StringMonth(Date date){
		return sdf5.format(date);
	}
	
	public static Date String2Date(String date){
		try {
			return sdf2.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Date String2Date_yyMMddHHmmss(String date){
		try {
			return sdf6.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Date String2Date_yyyyMMddHHmmss(String date){
		try {
			return sdf3.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	public final static String REG_VALID_CODE = "reg";
	public final static String RESET_PWD_VALID_CODE = "resetpwd";
	public final static String RESET_MOBILE_VALID_CODE = "resetmobile";
	
	public final static String OPT_RESET_PWD_BY_SMS = "resetpwdsms";
	public final static String OPT_RESET_PWD_BY_EMAIL = "resetpwdemail";
	
	public final static String OPT_RESET_MOBILE_BY_SMS = "resetmobilesms";
	public final static String OPT_RESET_MOBILE_BY_EMAIL  = "resetmobileemail";
	
	public final static String SEND_EMAIL = "1";
	public final static String SEND_SMS = "2";
	
	public final static String OPT_UPDATE_PROVIDER_PRODUCTINFO = "updateproviderproduct";
	public final static String OPT_ADD_PROVIDER_PRODUCT = "addproviderproduct";
	public final static String OPT_DEL_PROVIDER_PRODUCT = "delproviderproduct";
	public final static String OPT_UPDATE_BUYER_PRODUCTINFO = "updatebuyerproduct";
	public final static String OPT_ADD_BUYER_PRODUCT = "addbuyerproduct";
	public final static String OPT_DEL_BUYER_PRODUCT = "delbuyerproduct";
	public final static String OPT_UPDATE_DEALER_CMSTATUS = "updatedealercmstatus";
	public final static String OPT_UPDATE_DEALER_BDSTATUS = "updatedealerbdstatus";
	

	//首字母转大写     
	public static String toUpperCaseFirstOne(String s){   
		if(Character.isUpperCase(s.charAt(0)))            
			return s;        
		else           
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();  
	}

	//通过方法名反射获取值
	public static Object invokeMethod(Object owner, String methodName, Object[] args) throws Exception {   

		Class ownerClass = owner.getClass();   
		Class[] argsClass = new Class[args.length];   
		for (int i = 0, j = args.length; i < j; i++) {   
			argsClass[i] = args[i].getClass();   
		}   
		Method method = ownerClass.getMethod("get" + toUpperCaseFirstOne(methodName),argsClass);   
		return method.invoke(owner, args);   
	}  




	//获得当前公钥和描述
	public static String[] getRSAKey(){
		String[] res = null;
		try {
			RSAUtil.setRSAKeyStore(getWebInfPath());
			RSAPublicKey pk = (RSAPublicKey) RSAUtil.getKeyPair().getPublic();
			res = new String[2];
			res[0] = pk.getModulus().toString(16);
			res[1] = pk.getPublicExponent().toString(16);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	//获得WEB-INF路径
	public static String getWebInfPath(){
		String path=Thread.currentThread().getContextClassLoader().getResource("").toString();
		path=path.replace('/', '\\'); // 将/换成\   
		path=path.replace("file:", ""); //去掉file:   
		path=path.replace("classes\\", ""); //去掉class\   
		path=path.substring(1); //去掉第一个\,如 \D:\JavaWeb...   
		return path;
	}

	public static String getNullStr(String value){
		return null==value?"":value.trim();
	}
	
	public static String getNullStr0(String value){
		return null==value||"".equals(value)?"0":value.trim();
	}
	
	public static boolean isEmpty(String value){
		if(value==null||"".equals(value)){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
	}
	

	public final static String  OPT_SUCCESS = "0";//操作成功
	public final static String  OPT_FAILURE  = "1";//操作失败
	public final static String  OPT_DEALING  = "2";//操作处理中
	
	/**
	 * 字符串是否包含
	 * @param value1  样本串
	 * @param value2  需要比对样本中是否包含的串
	 * @return
	 */
	public static boolean contain(String value1,String value2,String split){
		value1 = split + value1 + split;
		value2 = split + value2 + split; 
		if(value1.indexOf(value2) >=0)return true;
		return false;
	}
	
}
