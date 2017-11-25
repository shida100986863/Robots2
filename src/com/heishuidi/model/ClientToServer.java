package com.heishuidi.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.heishuidi.common.Message;
import com.heishuidi.common.MessageType;
import com.heishuidi.common.User;

/**
 * �������������������
 * */
public class ClientToServer {
	public Socket s;
	//���ڵ�һ�����������������֤�û��������Ƿ���ȷ��ֻ������һ��
	public boolean checkUser(Object user){
		boolean b=false;
		try{//45.32.104.48
			s=new Socket("45.32.104.48",9999);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			//���û������뷢��������
			oos.writeObject(user);
			//��ȡ�ӷ������������İ�
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			//����������һ��Message��
			Message ms=(Message)ois.readObject();
			//Ϊ1�Ļ����ǵ�½�ɹ��İ�
			if(ms.getMesType().equals(MessageType.message_login_ok)){
				//����һ���߳����������ϵ
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
