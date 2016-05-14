package com.liumy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * PublicUtil.java
 * 公用工具类
 * @author zhangzb
 * @Date Sep 3, 2014
 */
public class PublicUtil {

	private static final Logger errLog = Logger.getLogger("zhdh_error");
	
	private static Pattern mobilePattern = Pattern.compile("1\\d{10}");// 手机号的正则

	private static Pattern pattern = Pattern.compile("^0\\d{9,11}$");// 固定电话、小灵通的正则
	
	private static Pattern imPattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8}$");// i码格式正则 8位数字和字母组成的
	

	/**
	 * 校验字符串是否为空或者为null或者-1
	 * 如果为空或者为null返回true
	 * @author liuyl
	 * @Date Apr 1, 2014
	 */
	public static boolean isNullFuyi(String str){
		try {
			if("".equals(str)||str==null /*||"-1".equals(str)*/ ){
				return true;
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}
	/**
	 * 验证日期格式yyyyMMddhhMMss
	 * 
	 * @param date
	 * @return
	 * @author liuyl
	 * @Date Apr 1, 2014
	 */
	public static boolean verifyDate2(String date) {
		Pattern p = Pattern.compile("^\\d{4}\\d{2}\\d{2}\\s\\d{2}[:]\\d{2}[:]\\d{2}$");
		return p.matcher(date).matches();
	}
	
	/**
	 * 验证用户输入金额,格式0.000,小数点后最多3位数字
	 * @author zhangzb
	 * @Date Sep 3, 2014
	 */
	public static boolean veryPayAmountValue(String value) {
		java.util.regex.Pattern p = java.util.regex.Pattern
		.compile("^[0-9]+(.[0-9]{1,3})?$");
		return p.matcher(value).matches();
	}

	/**
	 * 纯数字校验
	 * @author liuyl
	 * @Date Apr 2, 2014
	 */
	public static boolean isNumber(String number){
		Pattern pattern_isNumber=Pattern.compile("^[0-9]\\d*$");
		Matcher match=pattern_isNumber.matcher(number);
		if(!match.matches()){
			return false;
		}else{
			return true;
		}
	}
	
	public static boolean isPhoneNum(String num) {
		Matcher mr = mobilePattern.matcher(num.trim());
		return mr.matches();
	}

	public static boolean isGuhuaOrXiaoLingTong(String num) {
		Matcher mr = pattern.matcher(num.trim());
		return mr.matches();
	}
	
	/**
	 * ip格式校验
	 * 
	 * */
	public static boolean isboolIP(String ipAddress){  
		String  ip="(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";   
		Pattern pattern = Pattern.compile(ip);    
		Matcher matcher = pattern.matcher(ipAddress);   
		return matcher.matches();   
	}
	
	/**
	 * 字符串转换成时间格式
	 * @param aDate  时间
	 * @return Date 格式为"yyyy-MM-dd HH:mm:ss"
	 */
	public static Date parserDateFromString(String aDate) {
		Date dt = null;
		if(aDate ==null){
			return null;
		}
		try {
			dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(aDate);
		} catch (ParseException e) {
			errLog.error("parserDateFromString(String aDate) format is error!\n"+e.getMessage());
		}
		return dt;
	}
	public static SimpleDateFormat dyms=new SimpleDateFormat("yyyyMMddHHmmss");
	public static String transDateFormat(String date){
		Date dateStr = PublicUtil.parserDateFromString(date);
		return dyms.format(dateStr);
	}



	/**
	 * 判断一个数组内所有元素不能为空
	 */
	public static  boolean isNull(String[] items)
	{
		boolean result = false;
		for (int i = 0; i < items.length; i++)
		{
			if(items[i] == null){
				return true;
			}
			if (items[i] == null&&items[i].trim().equals(""))
			{
				return true;
			}
		}
		return result;
	}
	
	/**
	 * 获取请求IP地址
	 * @author liuyl
	 * @Date Apr 11, 2014
	 */
	public static String getIpAddr(HttpServletRequest request) {

		if (null == request) {
			return null;
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获取请求IP地址
	 */
	public static String getRequestURL(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		String RequestURL = request.getRequestURI();
		return RequestURL;
	}
	
	/**
	 * 验证日期有效性判断
	 * @author liuyl
	 * @Date Apr 11, 2014
	 */
	public static boolean validateDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            Date d = sdf.parse(date);
            String s = sdf.format(d);
            return date.compareTo(s)==0;
        } catch (ParseException e) {
        	e.printStackTrace();
            return false;
        }
    }
	
	/**
	 * 生成当前时间 日期格式为yyyyMMddHHmmss
	 * */
	public static String dateFormatTrans2() {
	     SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	     String date = formatter.format(new Date());
		return date;
	}
	
	/**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     * 
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
	
	  /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }
    

    
    /**
	 * 验证输入的邮箱格式是否符合
	 * @author zhangzb
	 * @param email
	 * @return 是否合法
	 */
	public static boolean emailFormat(String email) {
		boolean tag = true;
		final String pattern1 = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		final Pattern pattern = Pattern.compile(pattern1);
		final Matcher mat = pattern.matcher(email);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}
    

    

    

	
   
   public static String createDealerOrderId(){
	   return UUID.randomUUID().toString();
   }
   

	
	/**
	 * 根据卡号和密码判断出PCID 单卡形式移动
	 * 
	 * @param card
	 *            卡号
	 * @param pwd
	 *            密码
	 * @return
	 */
	public static String getPcIdByCards(String card, String pwd) {
		String pcid = null;
		if (card == null || pwd == null) {
			return pcid;
		}
		if (card.length() == 17 && pwd.length() == 18) {
			pcid = "CMJFK00010001"; // 移动全国卡
//		} else if (card.length() == 16 && pwd.length() == 21) {
//			pcid = "CMJFK00010102"; // 移动辽宁本地卡
//		} else if (card.length() == 10 && pwd.length() == 8) {
//			pcid = "CMJFK00010112"; // 移动浙江本地卡
//		} else if (card.length() == 16 && pwd.length() == 17) {
//			if ((card.substring(0, 1).equals("2") || card.substring(0, 1).equals("3"))&& pwd.substring(0, 3).equals("110"))
//				pcid = "CMJFK00010111"; // 移动江苏本地卡
//			else
//				pcid = "CMJFK00010014"; // 移动福建本地卡
		} else if (card.length() == 15 && pwd.length() == 19) {
			pcid = "LTJFK00020000";// 全国联通一卡充
		} else if (card.length() == 19 && pwd.length() == 18) {
			pcid = "DXJFK00010001";// 中国电信充值付费卡
		} 

		return pcid;

	}
	

	public static String getRealIP(HttpServletRequest request) {   
        String ipAddress = null;
        try{
	        //ipAddress = this.getRequest().getRemoteAddr();    
	        ipAddress = request.getHeader("x-forwarded-for");    
	        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {    
	         ipAddress = request.getHeader("Proxy-Client-IP");    
	        }    
	        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {    
	            ipAddress = request.getHeader("WL-Proxy-Client-IP");    
	        }    
	        if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {    
	         ipAddress = request.getRemoteAddr();   
	        }
	         //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割    
	        if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15    
	            if(ipAddress.indexOf(",")>0){    
	                ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));    
	            }    
	        }    
	        
	        //判断格式是否正确
			String rule = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b"; 
		    Pattern pattern = Pattern.compile(rule); 
		    Matcher matcher = pattern.matcher(ipAddress); 
		    if(!matcher.matches()){
		    	ipAddress = null;
		    }
        }catch(Exception e){
        	errLog.error("IpUtils getRealIP出现错误", e);
        	ipAddress = null;
        }
        return ipAddress;     
    } 
	public static boolean checkIfIM(String imPwd){
   	 Matcher matcher = imPattern.matcher(imPwd);
   	 return matcher.matches();
    }

}
