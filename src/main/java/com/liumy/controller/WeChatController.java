package com.liumy.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liumy.constant.SysConstant;
import com.liumy.service.CoreService;
import com.liumy.service.UtilCoreService;
import com.liumy.util.SignUtil;
/**
 * @author liumy
 * 微信核心控制
 */
@RequestMapping("weChantController")
@Controller
public class WeChatController {
	
	
	private static Logger log = Logger.getLogger(WeChatController.class);
	
	@Resource
	private CoreService coreService;
	
	@Resource
	private UtilCoreService utilCoreService;
	
	
	@RequestMapping("index")
	public String index(HttpServletRequest request,HttpServletResponse response){
		
		log.info("开始响应微信接口...........");
		
		PrintWriter out = null;
		
		try {
			//获取请求方式
			String requestMethod=request.getMethod();
			
			log.info("本次请求的方式是-->"+requestMethod);
			
			out= response.getWriter();
			
			if (SysConstant.REQUEST_GET.equalsIgnoreCase(requestMethod)) {
				responseGetRequest(request, out);
			}else if (SysConstant.REQUEST_POST.equalsIgnoreCase(requestMethod)) {

				responsePostRequest(request, out);
			}else {
				throw new Exception("获取微信相应请求方式失败...");
			}
			
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("相应微信端接口异常", e);
			return null;
		}finally{
			if (out!=null) {
				out.close();
			}
			out=null;
		}
		
	}

	

/***********************************私有方法开始**********************************************************/
	
	private void responsePostRequest(HttpServletRequest request, PrintWriter out) throws Exception{
		// 调用核心业务类接收消息、处理消息
		//String respMessage = coreService.processRequest(request);
		List<String> respMessage = utilCoreService.processRequest(request);
		// 响应消息
		for(String srr:respMessage){
			out.print(srr);
		}
		
	}
	
	private void responseGetRequest(HttpServletRequest request, PrintWriter out) {
		// 微信加密签名
		String signature = request.getParameter("signature");
		// 时间戳
		String timestamp = request.getParameter("timestamp");
		// 随机数
		String nonce = request.getParameter("nonce");
		// 随机字符串
		String echostr = request.getParameter("echostr");
        
		if (StringUtils.isEmpty(signature)
				||StringUtils.isEmpty(timestamp)
				||StringUtils.isEmpty(nonce)
		        ||StringUtils.isEmpty(echostr)){
			    log.info("微信端必要参数为空验证失败...");
			    return;
		}
		
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
			log.info("微信服务端验证成功");
		}else {
			log.info("微信服务端验证失败");
		}
	}

}
