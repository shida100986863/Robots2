package com.heishuidi.model;

import java.util.HashMap;

public class SaveThread {
	//ClientToServerThread��������Ҫ���߳����ӵĶ˿ڣ����Ը�������˿�ȥ����
	public static HashMap hm=new HashMap<String,ClientToServerThread>();
	public static void addClientToServer(String qqId,ClientToServerThread cts){
		hm.put(qqId, cts);
	}
	public static ClientToServerThread getClientToServer(String qqId){
		return (ClientToServerThread)hm.get(qqId);
	}
}
