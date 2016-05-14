package com.liumy.util;


import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * <pre>
 * 该配置文件读取类在程序运行过程如果配置文件更改了的话
 * 会自动重新装载配置文件，不用停应用服�?
 * </pre>
 * 
 * @author luoxian
 * @since Jun 24, 2008 5:19:22 PM
 * @version 1.0
 */
@SuppressWarnings({"unchecked", "unused"})
public class ConfigFile {

	private Properties properties;
	private long configLastModifiedTime; //上次修改时间
	private String config_file_name; //="application.properties";
	private String config_file_path;
	
	public ConfigFile(){}
	/**
	 * 
	 * 该构造函数表示配置文件和resendClass这个类具有相同的目录
	 * 
	 * @param configFileName
	 * @param presentClass
	 * @throws Exception
	 */
	public ConfigFile(String configFileName, Class presentClass) throws Exception {
		this(configFileName,getPathByPackage(presentClass));
	}
	
	/**
	 * 该构造函数表示配置文件在根目录，及在classes目录�?
	 * @param configFileName
	 * @throws Exception
	 */
	public ConfigFile(String configFileName) throws Exception {
		this(configFileName, "");
	}
	
	/**
	 * 该构造函数读取配置文件，文件目录由参数path指定<br>
	 * <pre>
	 * 例如：配置文件config.properties在包com.maywide.gcost.test包下
	 * 则传入的path参数 "/com/maywide/gcost/test/"
	 * </pre>
	 * @param configFileName
	 * @param path
	 * @throws Exception
	 */
	public ConfigFile(String configFileName, String path) throws Exception {
		setConfig_file_name(configFileName);
		setConfig_file_path(path);
		properties = loadConfigFile(configFileName);
	}
	
	private Properties loadConfigFile(String configFileName) throws Exception {
		File cfile = getConfigFile(config_file_name, config_file_path);
		if (configLastModifiedTime != 0L
				&& cfile.lastModified() <= configLastModifiedTime) {
			return properties;
		}
		configLastModifiedTime = cfile.lastModified();
		try {
			InputStream in = new FileInputStream(
					getConfigFile(config_file_name, config_file_path));
			properties = new Properties();
			properties.load(in);
			in.close();
		} catch (FileNotFoundException ex) {
			throw new Exception(ex.getMessage());
		} catch (IOException ex) {
			throw new Exception(ex.getMessage());
		}
		return properties;
	}
	
	

	private String getConfig_file_path() {
		return config_file_path;
	}

	private void setConfig_file_path(String config_file_path) {
		this.config_file_path = config_file_path;
	}

	private String getConfig_file_name() {
		return config_file_name;
	}



	private void setConfig_file_name(String config_file_name) {
		this.config_file_name = config_file_name;
	}


	/**
	 * 传入的样子为包路�?
	 * 例如：path="/com/first/second/"
	 * 
	 * @param filename
	 * @param path
	 * @return
	 */
	private File getConfigFile(String filename, String path) throws FileNotFoundException {
		URL url = null;
		if (path == null || path.length() == 0 ){
			url = ConfigFile.class.getResource("/" + filename); // 放在根目录下
		} else
			url = ConfigFile.class.getResource(path + filename); //放在类的目录
		if (url == null)
			throw new FileNotFoundException(path + filename +"文件没有找到");
		String newUrl=url.getPath();
		try {
			newUrl=URLDecoder.decode(newUrl,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(newUrl.replaceAll("%20", " "));
		if (file.isFile() && file.exists() && file.canRead())
			return file;
		return null;
	}
	
	private static String getPathByPackage(Class presentClass){
		Package p = presentClass.getPackage();
//		System.out.println(p.getName());
		String path = p.getName().replaceAll("\\.", "/");
		return "/"+path+"/";
	}
	/**
	 * 返回对应的
	 * @param name
	 * @return
	 */
	public String getValue(String name) {
		try {
			properties = loadConfigFile(getConfig_file_name());
			return properties.getProperty(name);
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 返回��对应的，如果配置文件中没有或定义为空，则返回默认
	 * @param name
	 * @param defaultValue	默认
	 * @return
	 */
	/*public String getValueByName(String name, String defaultValue) {
		try {
			properties = loadConfigFile(getConfig_file_name());
			String result = properties.getProperty(name);
			if (result == null )
				result = defaultValue;
			
			return result ;
		} catch (Exception e) {
			return null;
		}
	}*/
	
	public String getValue(String name, String defaultValue) {
		try {
			properties = loadConfigFile(getConfig_file_name());
			String result = StringUtils.defaultIfEmpty(properties.getProperty(name), defaultValue);
			
			return  result;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取属值，并转换为Integer类型
	 * 
	 * @param name 属
	 * @param defaultValue 当属性为null时，返回默认
	 * @return Integer
	 *
	 * @Copyright: Copyright (c) 2006
	 * @Company: Revenco Tech Ltd.
	 * @anthor chenminhui<chenminhui@revenco.com>
	 * @since 2014-10-22 上午10:15:36
	 * @version V1.0
	 */
	public Integer getToInteger(String name, int defaultValue){
		try {
			properties = loadConfigFile(getConfig_file_name());
			String result = properties.getProperty(name);
			if (result == null ){
				return Integer.valueOf(defaultValue);
			}
			return Integer.valueOf(result) ;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 获取属值，并转换为Boolean类型
	 * 
	 * @param name 
	 * @param defaultValue 当属性为null时，返回默认
	 * @return Boolean
	 *
	 * @Copyright: Copyright (c) 2006
	 * @Company: Revenco Tech Ltd.
	 * @anthor chenminhui<chenminhui@revenco.com>
	 * @since 2014-10-22 上午10:15:41
	 * @version V1.0
	 */
	public Boolean getToBoolean(String name, boolean defaultValue){
		try {
			properties = loadConfigFile(getConfig_file_name());
			String result = properties.getProperty(name);
			
			if (result == null ){
				return defaultValue;
			}
			return Boolean.valueOf(result);
		} catch (Exception e) {
			return null;
		}
	}
	

	public static void main(String[] args) throws Exception {
//		ConfigFile f = new ConfigFile("application.properties");
		ConfigFile f = new ConfigFile("sms.properties","/com/maywide/common/");
		System.out.println(f.getValue("hello"));
		for(int i=0; i<10; i++){
		try {
			Thread.sleep(3000);
		} catch (Exception e) {
		}
		System.out.println(f.getValue("shit"));
		}
	}
}
