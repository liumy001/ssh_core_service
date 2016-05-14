package com.liumy.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="util_info")
public class UtilInfo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="util_name",length=500)
	private String utilName;
	
	@Column(name="util_class",length=100)
	private String utilClass;
	@Column(name="util_method",length=500)
	private String util_method;
	@Column(name="util_return",length=500)
	private String utilReturn;
	@Column(name="util_des",length=500)
	private String utilDes;
	@Column(name="create_time")
	private Date createTime;
	@Column(name="is_ok")
	private Integer isOk;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUtilName() {
		return utilName;
	}
	public void setUtilName(String utilName) {
		this.utilName = utilName;
	}
	public String getUtilClass() {
		return utilClass;
	}
	public void setUtilClass(String utilClass) {
		this.utilClass = utilClass;
	}
	public String getUtil_method() {
		return util_method;
	}
	public void setUtil_method(String util_method) {
		this.util_method = util_method;
	}
	public String getUtilReturn() {
		return utilReturn;
	}
	public void setUtilReturn(String utilReturn) {
		this.utilReturn = utilReturn;
	}
	public String getUtilDes() {
		return utilDes;
	}
	public void setUtilDes(String utilDes) {
		this.utilDes = utilDes;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getIsOk() {
		return isOk;
	}
	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}
	@Override
	public String toString() {
		return "UtilInfo [id=" + id + ", utilName=" + utilName + ", utilClass="
				+ utilClass + ", util_method=" + util_method + ", utilReturn="
				+ utilReturn + ", utilDes=" + utilDes + ", createTime="
				+ createTime + ", isOk=" + isOk + "]";
	}
	
	

}
