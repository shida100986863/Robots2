package com.heishuidi.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.heishuidi.common.Message;
import com.heishuidi.common.MessageType;
import com.heishuidi.view.FriendChat;
import com.heishuidi.view.FriendList;

public class ClientToServerThread extends Thread{
	private Socket s;//����ͻ��������������
	Message message=new Message();
	public ClientToServerThread(Socket s){
		this.s=s;
	}
	public Socket getS() {
		return s;
	}
	public void setS(Socket s) {
		this.s = s;
	}
	public void run(){
		//��ͣ�Ķ�ȡ�ӷ�������������Ϣ
		while(true){
			try {
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				message=(Message)ois.readObject();
//				System.out.println("��ȡ���ӷ�������������Ϣ"+message.getSender()+"��"+message.getGetter()
//						+"������"+message.getCon());
				//�������ߺ��Ѱ�
				if(message.getMesType().equals(MessageType.message_ret_onLineFriend)){
					//����˭�İ�
					String getter=message.getGetter();
					//��hashmap���ҵ�����˭���������Ȼ������������
					FriendList friendList=SaveList.getFrindList(getter);
					
					if(friendList!=null){
						friendList.updateFriend(message);
					}
				}
				//�������ͨ��������ʾ��Ϣ
				else if(message.getMesType().equals(MessageType.message_message)){
					//�ӹ���һ����ͨ����ת���������������ý���������ȥ��
					FriendChat friendChat=SaveChat.getFriendChat(message.getGetter()+" "+message.getSender());
					friendChat.showMessage(message);
				}
				//����������û����ߵİ���������ʾҳ��
				else if(message.getMesType().equals(MessageType.message_downLine)){
					String []friends=message.getCon().split(" ");
					for(int i=0;i<friends.length;i++){
						String getter=friends[i];
						FriendList friendList=SaveList.getFrindList(getter);
						//��hashmap���ҵ�����˭���������Ȼ������������
						if(friendList!=null){
							friendList.updateFriend1(message);
						}
					}
					//System.out.println("�û�������"+message.getCon());
				}
			} catch (Exception e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			
		}
	}
}
