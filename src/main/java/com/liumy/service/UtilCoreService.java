package com.liumy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.liumy.entity.Message;
import com.liumy.entity.UtilInfo;
import com.liumy.message.TextMessage;
import com.liumy.util.MessageUtil;

/**
 * @author liumy
 * 核心控制
 */
@Service
@Transactional
public class UtilCoreService {
	
	private static Logger log = Logger.getLogger(UtilCoreService.class);
	
	@Resource
	private WeChatMessageService weChatMessageService;
	
	@Resource
	private UtilInfoService utilInfoService;
	
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public  List<String> processRequest(HttpServletRequest request) throws Exception{
		Message weChatMessage=null;
		List<String> strList=null;
		try {
			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			log.info("msgType:"+msgType);
			log.info("fromUserName:"+fromUserName);
			log.info("toUserName:"+toUserName);
			
			
			weChatMessage=new Message();
			weChatMessage.setCreator("system");
			weChatMessage.setIsRes(0);
			weChatMessage.setMsgType(msgType);
			weChatMessage.setOpenId(fromUserName);
			weChatMessage.setToUserName(toUserName);
			weChatMessage.setCreateTime(new Date());
			weChatMessage.setContent(requestMap.get("Content"));
			weChatMessageService.save(weChatMessage);

			// 默认回复此文本消息
			TextMessage textMessage = new TextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new java.util.Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			// 由于href属性值必须用双引号引起，这与字符串本身的双引号冲突，所以要转义			
			StringBuffer contentMsg = new StringBuffer();  
			contentMsg.append("欢迎来到工具api搜索平台").append("\n");
			// 文本消息
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");
				log.info("content:"+content);
				List<UtilInfo> list= utilInfoService.findUtilInfoList(content);
				if (list==null||list.size()<=0) {
					contentMsg.append("-------------------------").append("\n");
					contentMsg.append("暂无匹配结果，工具库完善中!").append("\n");
					contentMsg.append("-------------------------").append("\n");
				}else {
					log.info("查询结果-->"+list.toString());
					int i=0;
					for(UtilInfo utilInfo:list){
						i++;
						contentMsg.append("-------------------------").append("\n");
						contentMsg.append("工具类名称     :").append(utilInfo.getUtilName()).append("。\n");
						contentMsg.append("类名称             :").append(utilInfo.getUtilClass()).append("。\n");
						contentMsg.append("方法名称          :").append(utilInfo.getUtil_method()).append("。\n");
						contentMsg.append("返回值类型     :").append(utilInfo.getUtilReturn()).append("。\n");
						contentMsg.append("-------------------------").append("\n");
						if (i%6==0) {
							contentMsg.append("@");
						}
					}
					
				}
			}else {
				contentMsg = new StringBuffer();
				contentMsg.append("消息格式错误!");
			}
			
			String[] contents=contentMsg.toString().split("@");
			strList=new ArrayList<String>();
			for(String str:contents){
				textMessage.setContent(str);
	            
				
				String strXml= MessageUtil.textMessageToXml(textMessage);
				
				strList.add(strXml);
				
	            textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new java.util.Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setFuncFlag(0);
			}
			
			
			
			// 将文本消息对象转换成xml字符串
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("核心处理出现异常", e);
			throw e;
		}
		
		if (weChatMessage!=null) {
			weChatMessage.setIsRes(1);
			weChatMessageService.save(weChatMessage);
		}
		
		return strList;
	}

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
}