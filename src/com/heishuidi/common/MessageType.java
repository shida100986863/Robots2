package com.heishuidi.common;

public interface MessageType {
	String message_login_ok="1";//登陆成功的包
	String message_login_no="2";//表明登录失败
	String message_message="3";//普通信息包
	//返回在线好友的包
	String message_get_onLineFriend="4";
	//返回在线好友的包
	String message_ret_onLineFriend="5";
	String message_downLine="6";//下线
}
