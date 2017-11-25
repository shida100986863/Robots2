package com.heishuidi.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.heishuidi.common.Message;
import com.heishuidi.common.MessageType;
import com.heishuidi.view.FriendChat;
import com.heishuidi.view.FriendList;

public class ClientToServerThread extends Thread{
	private Socket s;//保存客户端与服务器连接
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
		//不停的读取从服务器发来的消息
		while(true){
			try {
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				message=(Message)ois.readObject();
//				System.out.println("读取到从服务器发来的消息"+message.getSender()+"给"+message.getGetter()
//						+"内容是"+message.getCon());
				//返回在线好友包
				if(message.getMesType().equals(MessageType.message_ret_onLineFriend)){
					//发给谁的包
					String getter=message.getGetter();
					//从hashmap中找到发给谁的这个界面然后更新这个界面
					FriendList friendList=SaveList.getFrindList(getter);
					
					if(friendList!=null){
						friendList.updateFriend(message);
					}
				}
				//如果是普通包，则显示消息
				else if(message.getMesType().equals(MessageType.message_message)){
					//接过来一个普通包，转发给接受者所以用接收者名字去查
					FriendChat friendChat=SaveChat.getFriendChat(message.getGetter()+" "+message.getSender());
					friendChat.showMessage(message);
				}
				//如果是其他用户下线的包，更新显示页面
				else if(message.getMesType().equals(MessageType.message_downLine)){
					String []friends=message.getCon().split(" ");
					for(int i=0;i<friends.length;i++){
						String getter=friends[i];
						FriendList friendList=SaveList.getFrindList(getter);
						//从hashmap中找到发给谁的这个界面然后更新这个界面
						if(friendList!=null){
							friendList.updateFriend1(message);
						}
					}
					//System.out.println("用户下线了"+message.getCon());
				}
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			
		}
	}
}
