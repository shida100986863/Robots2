package com.heishuidi.model;

import java.util.HashMap;

import com.heishuidi.view.FriendChat;
import com.heishuidi.view.FriendList;

public class SaveChat {
	//���ݡ����˺ͺ��ѱ�š����뵽hashmap
	private static HashMap hm=new HashMap<String,FriendChat>();
	public static void addFriendChat(String MyId_FrindId,FriendChat friendChat){
		hm.put(MyId_FrindId, friendChat);
	}
	public static FriendChat getFriendChat(String MyId){
		return (FriendChat)hm.get(MyId);
	}
}
