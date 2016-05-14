package com.liumy.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
	
	
	  private static final CloseableHttpClient httpClient;
	  
	    public static final String CHARSET = "UTF-8";
	    
	    private static DefaultHttpClient client;
	 
	    static {
	        RequestConfig config = RequestConfig.custom().setConnectTimeout(60000).setSocketTimeout(15000).build();
	        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	    }
	 
	    public static String doGet(String url, Map<String, String> params){
	        return doGet(url, params,CHARSET);
	    }
	    public static String doPost(String url, Map<String, String> params){
	        return doPost(url, params,CHARSET);
	    }
	    /**
	     * HTTP Get 获取内容
	     * @param url  请求的url地址 ?之前的地址
	     * @param params 请求的参数
	     * @param charset    编码格式
	     * @return    页面内容
	     */
	    public static String doGet(String url,Map<String,String> params,String charset){
	        if(StringUtil.isEmpty(url)){
	            return null;
	        }
	        try {
	            if(params != null && !params.isEmpty()){
	                List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>(params.size());
	                for(Map.Entry<String,String> entry : params.entrySet()){
	                    String value = entry.getValue();
	                    if(value != null){
	                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
	                    }
	                }
	                url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
	            }
	            HttpGet httpGet = new HttpGet(url);
	            CloseableHttpResponse response = httpClient.execute(httpGet);
	            int statusCode = response.getStatusLine().getStatusCode();
	            if (statusCode != 200) {
	                httpGet.abort();
	                throw new RuntimeException("HttpClient,error status code :" + statusCode);
	            }
	            HttpEntity entity = response.getEntity();
	            String result = null;
	            if (entity != null){
	                result = EntityUtils.toString(entity, "utf-8");
	            }
	            EntityUtils.consume(entity);
	            response.close();
	            return result;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	     
	    /**
	     * HTTP Post 获取内容
	     * @param url  请求的url地址 ?之前的地址
	     * @param params 请求的参数
	     * @param charset    编码格式
	     * @return    页面内容
	     */
	    public static String doPost(String url,Map<String,String> params,String charset){
	        if(StringUtil.isEmpty(url)){
	            return null;
	        }
	        try {
	            List<BasicNameValuePair> pairs = null;
	            if(params != null && !params.isEmpty()){
	                pairs = new ArrayList<BasicNameValuePair>(params.size());
	                for(Map.Entry<String,String> entry : params.entrySet()){
	                    String value = entry.getValue();
	                    if(value != null){
	                        pairs.add(new BasicNameValuePair(entry.getKey(),value));
	                    }
	                }
	            }
	            HttpPost httpPost = new HttpPost(url);
	            if(pairs != null && pairs.size() > 0){
	                httpPost.setEntity(new UrlEncodedFormEntity(pairs,CHARSET));
	            }
	            CloseableHttpResponse response = httpClient.execute(httpPost);
	            int statusCode = response.getStatusLine().getStatusCode();
	            if (statusCode != 200) {
	                httpPost.abort();
	                throw new RuntimeException("HttpClient,error status code :" + statusCode);
	            }
	            HttpEntity entity = response.getEntity();
	            String result = null;
	            if (entity != null){
	                result = EntityUtils.toString(entity, "utf-8");
	            }
	            EntityUtils.consume(entity);
	            response.close();
	            return result;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    
	    public static Integer sendXMLDataByPost(String url, String xmlData)
	    		   throws ClientProtocolException, IOException {
	    		  Integer statusCode = -1;
	    		  if (client == null) {
	    		   // Create HttpClient Object
	    		   client = new DefaultHttpClient();
	    		  }
	    		// Send data by post method in HTTP protocol,use HttpPost instead of
	    		  // PostMethod which was occurred in former version
	    		  HttpPost post = new HttpPost(url);
	    		// Construct a string entity
	    		  StringEntity entity = new StringEntity(xmlData);
	    		  // Set XML entity
	    		  post.setEntity(entity);
	    		  // Set content type of request header
	    		  post.setHeader("Content-Type", "text/xml;charset=utf-8");
	    		// Execute request and get the response
	    		  HttpResponse response = client.execute(post);
	    		  // Response Header - StatusLine - status code
	    		  statusCode = response.getStatusLine().getStatusCode();
	    		  return statusCode;
	    		}

	    
	    public static void main(String []args)throws Exception{
	        System.out.println("----------------------分割线-----------------------");
	        Map<String, String> paramMap=new HashMap<String,String>();
	        StringBuffer sb=new StringBuffer();
	        sb.append("<xml version='1.0' encoding='UTF-8'>");
	        sb.append("<ToUserName><![CDATA[toUser]]></ToUserName>");
	        sb.append("<FromUserName><![CDATA[fromUser]]></FromUserName>");
	        sb.append("<Content><![CDATA[is]]></Content>");
	        sb.append("<CreateTime>1357290913</CreateTime>");
	        sb.append("<MsgType><![CDATA[text]]></MsgType>");
	        sb.append("<MediaId><![CDATA[media_id]]></MediaId>");
	        sb.append("<Format><![CDATA[Format]]></Format>");
	        sb.append("<MsgId>1234567890123456</MsgId>");
	        sb.append("</xml>");
	        paramMap.put("param", sb.toString());
	       
	        
	        Integer statusCode = sendXMLDataByPost("http://localhost:8080/ssh/weChantController/index", sb.toString());
	        
	        System.out.println(statusCode);
	        
	        if (statusCode==200) {
				System.out.println("ok...........................");
			}
	        
	    }

	

}
