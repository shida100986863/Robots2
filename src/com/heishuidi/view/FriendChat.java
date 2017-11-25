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
	String myId;//����ID
	String friendId;//����ĺ���ID
	JFrame frame; 
	JTextArea jta;//�ı���ʾ��
	JScrollPane jsp;//�ı���ʾ����ӹ�����
	JTextField jtf;//�ı������
	JButton jb;
	JPanel jp;
	
	public FriendChat(String myId,String friendId){
		this.myId=myId;
		this.friendId=friendId;
		///////////////////
		final ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("/image/login_backgroup.gif"));  
		jta = new JTextArea() {  
			//Image image = imageIcon.getImage();  �ڰ׵�
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
		jb=new JButton("������Ϣ");
		jb.addActionListener(this);
		jp=new JPanel();
		jp.add(jtf);//�ı���ʾ��
		jp.add(jb);	//�ı������
		
		Container content = frame.getContentPane(); //���뵽����
	    content.add(jsp,"Center");
	    content.add(jp,"South");
		frame.setIconImage((new ImageIcon(this.getClass().getResource("/image/friendchat_qq.gif")).getImage()));
		frame.setTitle(myId+" ���ں� "+friendId+" ����");
		frame.setLocation(500,200); 
		frame.setSize(400, 300);  
		frame.setVisible(true);  
	}
//	public static void main(String []args){
//		new FriendChat("1", "2");
//	}
	public void showMessage(Message m){
		String info="ʱ��"+m.getSendTime()+" "+m.getSender()+" ��"+m.getGetter()+"˵��\n\t"+m.getCon()+"\n";
		jta.append(info);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		//�ȷ�����Ϣ����������Ȼ�󿴼������̻�ȡ��������ʲô��������Ӧ�Ĳ���
		if(e.getSource()==jb){
			Message m=new Message();
			//���͸���ͨ��Ϣ��
			m.setMesType(MessageType.message_message);
			//���÷����ߺͽ�����ID���÷�����֪��ת����˭
			m.setSender(this.myId);
			m.setGetter(friendId);
			m.setCon(jtf.getText());
			String date=new java.util.Date().getHours()+"��"+new java.util.Date().getMinutes()+"��";
			m.setSendTime(date+"");
			
			//����ʾ�ڵ�ǰ���ڣ�Ȼ���͸�������ת��
			String info="ʱ��"+date+"\t"+myId+"��"+friendId+"˵��\n\t"+jtf.getText()+"\n";
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
