package com.heishuidi.model;

import java.util.HashMap;

import com.heishuidi.view.FriendList;

/**
 * ������ѡ�������
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
