package com.heishuidi.model;

import java.util.HashMap;

public class SaveThread {
	//ClientToServerThread保存了想要的线程连接的端口，可以根据这个端口去链接
	public static HashMap hm=new HashMap<String,ClientToServerThread>();
	public static void addClientToServer(String qqId,ClientToServerThread cts){
		hm.put(qqId, cts);
	}
	public static ClientToServerThread getClientToServer(String qqId){
		return (ClientToServerThread)hm.get(qqId);
	}
}
