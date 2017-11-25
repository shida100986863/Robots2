package com.heishuidi.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.heishuidi.common.Message;
import com.heishuidi.common.MessageType;
import com.heishuidi.model.ClientToServerThread;
import com.heishuidi.model.SaveChat;
import com.heishuidi.model.SaveList;
import com.heishuidi.model.SaveThread;
/**面板是由我的好友和陌生人两个JPanel组成
 * 点击一个显示一个卡片
 * 所以一共写两个卡片，用卡片布局最简单
 * */
public class FriendList extends JFrame implements ActionListener,MouseListener{
	String myId;//保存自己的id用于显示和与服务器交互
	//第一个面板有的组件
	JPanel jp1_1,jp1_2,jp1_3;	//我的好友、所有好友、陌生人面板
	JButton jb1_friend,jb1_stranger;//好友陌生人按钮
	JScrollPane jsp1;//滚动条
	//第二个面板有的组件
	JPanel jp2_1,jp2_2,jp2_3;//我的好友、陌生人、陌生人名单面板
	JButton jb2_friend,jb2_stranger;
	JScrollPane jsp2;//滚动条
	
	JLabel []jbls;//好友列表
	CardLayout cl;//用卡片布局
	
	public FriendList(String Id){
		this.myId=Id;
		//初始化第一张卡片
		jb1_friend=new JButton("我的好友");
		jb1_stranger=new JButton("陌生人");
		jb1_stranger.addActionListener(this);
		//边框布局  假定50个好友
		jp1_1=new JPanel(new BorderLayout());
		jp1_2=new JPanel(new GridLayout(50,1,4,4));//网格布局
		//给jbls初始化50个好友
		jbls=new JLabel[50];
		for(int i=0;i<jbls.length;i++){
			//好友列表名称和图片循环加到jphy2中
			jbls[i]=new JLabel(i+1+"",new ImageIcon(this.getClass().getResource("/image/friendlist_mm.jpg")),JLabel.LEFT);
			jbls[i].setEnabled(false);
			//如果是本人ID的话设置为显示
			if(jbls[i].getText().equals(myId)){
				jbls[i].setEnabled(true);
			}
			//对这个好友加鼠标监听事件
			jbls[i].addMouseListener(this);
			jp1_2.add(jbls[i]);
		}
		//添加滚动条
		jsp1=new JScrollPane(jp1_2);
		//陌生人按钮
		jp1_3=new JPanel(new GridLayout(1,1));
		jp1_3.add(jb1_stranger);
		jp1_1.add(jb1_friend,"North");
		jp1_1.add(jsp1,"Center");
		jp1_1.add(jp1_3,"South");
		
		//初始化第二张卡片
		jb2_friend=new JButton("我的好友");
		jb2_friend.addActionListener(this);
		jb2_stranger=new JButton("陌生人");
		//初始化面板
		jp2_1=new JPanel(new BorderLayout());
		//假定有20个陌生人
		jp2_2=new JPanel(new GridLayout(20, 1, 4, 4));
		JLabel []jbls2=new JLabel[20];
		for(int i=0;i<jbls2.length;i++){
			//好友列表名称和图片循环加到jphy2中
			jbls2[i]=new JLabel(i+1+"",new ImageIcon(this.getClass().getResource("/image/friendlist_mm.jpg")),JLabel.LEFT);
			jp2_2.add(jbls2[i]);
		}
		jp2_3=new JPanel(new GridLayout(2,1));
		jp2_3.add(jb2_friend);//我的好友
		jp2_3.add(jb2_stranger);//陌生人
		//滚动条
		jsp2=new JScrollPane(jp2_2);
		
		jp2_1.add(jp2_3,"North");
		jp2_1.add(jsp2,"Center");
		
		cl=new CardLayout();
		this.setLayout(cl);//设置布局方式为卡片布局
		this.add(jp1_1,"1");//设置他们的编号，用于切换界面
		this.add(jp2_1,"2");
		this.setTitle(myId);//标题显示当前的ID
		this.setSize(300,600);//
		this.setLocation(800,20);           //显示屏左边缘500像素,离显示屏上边缘200像素
		this.setIconImage((new ImageIcon(this.getClass().getResource("/image/friendchat_qq.gif")).getImage()));
		//设置关闭窗口事件
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//终止当前线程
				//根据myId找进程，然后终止
				ClientToServerThread c=SaveThread.getClientToServer(myId);
				try {
					Message m=new Message();
					//发送个下线信息包,下线的用户
					m.setMesType(MessageType.message_downLine);
					m.setSender(myId);
					ObjectOutputStream oos=new ObjectOutputStream(SaveThread.getClientToServer(myId).getS().getOutputStream());
					oos.writeObject(m);
					c.getS().close();
					c.stop();
					System.exit(0);
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		this.setVisible(true);
	}
	//更新在线的好友情况
	//message.getCon()获取服务器运行所有线程，用户名用空格分隔
	//想象~~~~如果想换成拼音或汉字的话jbls[i].setText();这样显示成备注
	//123在线
	public void updateFriend(Message message){
		String []friends=message.getCon().split(" ");
		for(int i=0;i<friends.length;i++){
			jbls[Integer.parseInt(friends[i])-1].setEnabled(true);
		}
	}
	public void updateFriend1(Message message){
		String []friends=message.getCon().split(" ");
		//int num=friends.length-1;
		for(int i=0;i<jbls.length;i++){
			jbls[i].setEnabled(false);
		}
		for(int i=0;i<friends.length;i++){
			System.out.println(friends[i]+"  ");
			jbls[Integer.parseInt(friends[i])-1].setEnabled(true);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==jb1_stranger){
			cl.show(this.getContentPane(), "2");
		}
		if(e.getSource()==jb2_friend){
			cl.show(this.getContentPane(), "1");
		}
	}
//	public static void main(String []args){
//		new FriendList("1");
//	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根
		//相应用户双击，并获得好友的编号
		//=2为双击  双击通话的话才建立连接
		if(e.getClickCount()==2){
			String frindNo=((JLabel)e.getSource()).getText();
			//双击打开聊天界面
			FriendChat friendChat=new FriendChat(this.myId,frindNo);
			//按照发送者，接收者保存
			SaveChat.addFriendChat(this.myId+" "+frindNo, friendChat);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根
		//先取出jlabel然后设置它的前景色
		JLabel l1=(JLabel)e.getSource();
		l1.setForeground(Color.red);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根
		JLabel l1=(JLabel)e.getSource();
		l1.setForeground(Color.black);
	}
	
}
