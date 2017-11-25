package com.heishuidi.common;
/**协议类
 * 此类封装 服务器与客户端相互传输的信息
 * 有什么类型的包，根据类型不同，对消息内容做不同的处理。
 * 		1:登陆成功包
 * 		2:登录失败包
 * 		3:普通信息包
 * 		4:获取在线好友包
 * 		5:返回在线好友包
 * 谁发送的
 * 发送给谁
 * 消息内容
 * 发给谁 
 * 在网上传输对象需要序列化implements java.io.Serializable
 * */
public class Message implements java.io.Serializable{
	private String mesType;//包的类型
	private String sender;//发送者
	private String getter;//接收者
	private String con;//消息内容
	private String sendTime;//发送时间
	public String getMesType() {
		return mesType;
	}
	public void setMesType(String mesType) {
		this.mesType = mesType;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getGetter() {
		return getter;
	}
	public void setGetter(String getter) {
		this.getter = getter;
	}
	public String getCon() {
		return con;
	}
	public void setCon(String con) {
		this.con = con;
	}
	public String getSendTime() {
		return sendTime;
	}
	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
}
