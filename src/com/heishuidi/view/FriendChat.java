package com.heishuidi.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;

import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.heishuidi.common.Message;
import com.heishuidi.common.MessageType;
import com.heishuidi.model.SaveThread;


public class FriendChat extends JFrame implements ActionListener{
	String myId;//本人ID
	String friendId;//点击的好友ID
	JFrame frame; 
	JTextArea jta;//文本显示框
	JScrollPane jsp;//文本显示框添加滚动条
	JTextField jtf;//文本输入框
	JButton jb;
	JPanel jp;
	
	public FriendChat(String myId,String friendId){
		this.myId=myId;
		this.friendId=friendId;
		///////////////////
		final ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/image/login_backgroup.gif"));  
		jta = new JTextArea() {  
			//Image image = imageIcon.getImage();  黑白的
			//Image grayImage = GrayFilter.createDisabledImage(image);  
			{  
				setOpaque(false);  
			} 
		    public void paint(Graphics g) {  
		    	g.drawImage(imageIcon.getImage(), 0, 0, this);  
		    	super.paint(g);  
		    }  
		};   
		frame=new JFrame();
		jsp=new JScrollPane(jta);
		jtf=new JTextField(15);
		jb=new JButton("发送消息");
		jb.addActionListener(this);
		jp=new JPanel();
		jp.add(jtf);//文本显示框
		jp.add(jb);	//文本输入框
		
		Container content = frame.getContentPane(); //加入到容器
	    content.add(jsp,"Center");
	    content.add(jp,"South");
		frame.setIconImage((new ImageIcon(this.getClass().getResource("/image/friendchat_qq.gif")).getImage()));
		frame.setTitle(myId+" 正在和 "+friendId+" 聊天");
		frame.setLocation(500,200); 
		frame.setSize(400, 300);  
		frame.setVisible(true);  
	}
//	public static void main(String []args){
//		new FriendChat("1", "2");
//	}
	public void showMessage(Message m){
		String info="时间"+m.getSendTime()+" "+m.getSender()+" 对"+m.getGetter()+"说：\n\t"+m.getCon()+"\n";
		jta.append(info);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		//先发送消息给服务器，然后看监听进程获取过来的是什么包，做相应的操作
		if(e.getSource()==jb){
			Message m=new Message();
			//发送个普通信息包
			m.setMesType(MessageType.message_message);
			//设置发送者和接收者ID，让服务器知道转发给谁
			m.setSender(this.myId);
			m.setGetter(friendId);
			m.setCon(jtf.getText());
			String date=new java.util.Date().getHours()+"点"+new java.util.Date().getMinutes()+"分";
			m.setSendTime(date+"");
			
			//先显示在当前窗口，然后发送给服务器转发
			String info="时间"+date+"\t"+myId+"对"+friendId+"说：\n\t"+jtf.getText()+"\n";
			jta.append(info);
			jtf.setText("");
			
			try{
				ObjectOutputStream oos=new ObjectOutputStream(SaveThread.getClientToServer(myId).getS().getOutputStream());
				oos.writeObject(m);
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
	}
	
}
