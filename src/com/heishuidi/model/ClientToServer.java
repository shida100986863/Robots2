package com.heishuidi.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.heishuidi.common.Message;
import com.heishuidi.common.MessageType;
import com.heishuidi.common.User;

/**
 * 此类用于与服务器连接
 * */
public class ClientToServer {
	public Socket s;
	//用于第一次与服务器交互，验证用户名密码是否正确，只发送了一次
	public boolean checkUser(Object user){
		boolean b=false;
		try{//45.32.104.48
			s=new Socket("45.32.104.48",9999);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			//把用户名密码发给服务器
			oos.writeObject(user);
			//读取从服务器发送来的包
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			//传回来的是一个Message包
			Message ms=(Message)ois.readObject();
			//为1的话就是登陆成功的包
			if(ms.getMesType().equals(MessageType.message_login_ok)){
				//启动一个线程与服务器联系
				ClientToServerThread cts=new ClientToServerThread(s);
				cts.start();
				SaveThread.addClientToServer(((User)user).getUser(), cts);
				b=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return b;
	}
}
