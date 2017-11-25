package com.heishuidi.model;

import java.util.HashMap;

import com.heishuidi.view.FriendList;

/**
 * 管理好友、黑名单
 * */
public class SaveList {
	public static HashMap hm=new HashMap<String,FriendList>();
	public static void addFrindList(String myId,FriendList friendList){
		hm.put(myId, friendList);
	}
	public static FriendList getFrindList(String myId){
		return (FriendList)hm.get(myId);
	}
}
