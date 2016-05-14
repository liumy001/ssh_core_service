package com.liumy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity(name="message")
public class Message {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="open_id",length=50)
	private String openId;
	
	@Column(name="msg_type",length=50)
	private String MsgType;
	
	@Column(name="creator",length=50)
	private String creator;
	
	@Column(name="to_username",length=50)
    private String toUserName;
	
	@Column(name="is_res")
	private Integer isRes;
	
	@Column(name="content",length=200)
	private String content;

	@Column(name="create_time")
	private Date createTime;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenId() {
		return openId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}





	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public Integer getIsRes() {
		return isRes;
	}

	public void setIsRes(Integer isRes) {
		this.isRes = isRes;
	}
	
	
	
}
