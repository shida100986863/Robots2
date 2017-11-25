package com.heishuidi.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.heishuidi.common.Message;
import com.heishuidi.common.MessageType;
import com.heishuidi.common.User;
import com.heishuidi.model.ClientToServer;
import com.heishuidi.model.SaveList;
import com.heishuidi.model.SaveThread;
/**
 * 登陆页面、客户端程序主入口
 * 登陆布局为一个JLable两个JPanel
 * 第一个放图片。第二个放用户名密码输入框。第三个放登录按钮
 * */
public class Login extends JFrame implements ActionListener{
	JFrame frame=new JFrame("讯聊登录窗口");
	
	//北部放图片
	JLabel jl_img;
	//南部放两个按钮
	JPanel jp1;
	JButton jp1_jb_submit,jp1_jb_result;//登陆重置按钮
	//定义中部需要的组件
	JPanel jp2;
	JLabel jp2_jl_user,jp2_jl_pwd;//用户名密码文本提示框
	JTextField jp2_jt_user;//用户名输入框
	JPasswordField jp2_jp_pwd;//密码输入框
	public static void main(String []args){
		new Login();
	}
	public Login(){
		//这是背景图片路径   获取图片路径方法，可以打成jar包
		//
		ImageIcon img = new ImageIcon(Login.class.getResource("/image/login_backgroup.gif"));
		JLabel imgLabel = new JLabel(img);//将背景图放在标签里。
		//注意这里是关键，将背景标签添加到jfram的LayeredPane面板里。
		frame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		//设置背景标签的位置this.getWidth(), this.getHeight()
		imgLabel.setBounds(0,0,img.getIconWidth(), img.getIconHeight());
		Container cp=frame.getContentPane();//容器
		cp.setLayout(new BorderLayout());//边框布局
		
		
		
		//北部添加图片
		//jl_img=new JLabel(new ImageIcon("/image/login_tou.gif"));
		jl_img=new JLabel(new ImageIcon(this.getClass().getResource("/image/login_tou.gif")));
		//南部三个按钮添加图片
		jp1=new JPanel();

		
		jp1_jb_submit=new JButton(new ImageIcon(this.getClass().getResource("/image/login_denglu.gif")));
		jp1_jb_submit.addActionListener(this);
		//jp1_jb_result=new JButton(new ImageIcon("./image/login_result.gif"));
		jp1_jb_result=new JButton(new ImageIcon(this.getClass().getResource("/image/login_result.gif")));
		//中部输入框文本框
		jp2=new JPanel(new BorderLayout());//表格布局
		jp2_jl_user=new JLabel("QQ号码",JLabel.CENTER);
		jp2_jl_pwd=new JLabel("QQ密码",JLabel.CENTER);
		jp2_jt_user=new JTextField(15);//用户名输入框
		jp2_jp_pwd=new JPasswordField(15);//密码输入框
		
		JPanel jp2_2=new JPanel();
		jp2_2.add(jp2_jl_user);
		jp2_2.add(jp2_jt_user);
		JPanel jp2_3=new JPanel();
		jp2_3.add(jp2_jl_pwd);
		jp2_3.add(jp2_jp_pwd);
		jp2_2.setOpaque(false);
		jp2_3.setOpaque(false);
		
		//按顺序加入到jpanel中
		jp2.add(jp2_2,"North");
		jp2.add(jp2_3,"Center");
		
		//两个按钮放到jp1中最下面为jp1
		jp1.add(jp1_jb_submit);
		jp1.add(jp1_jb_result);
		
		//设置背景颜色的方式
		jl_img.setOpaque(false);//用方法setOpaque()来使内容窗格透明
		jp2.setOpaque(false);
		jp1.setOpaque(false);
		cp.add(jl_img,"North");//将按钮添加入窗口的内容面板
		cp.add(jp1,"South");
		cp.add(jp2,"Center");
		
		((JPanel)cp).setOpaque(false); //注意这里，将内容面板设为透明。这样LayeredPane面板中的背景才能显示出来。
		frame.setSize(440,300);
		frame.setLocation(500,200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage((new ImageIcon(this.getClass().getResource("/image/friendchat_qq.gif")).getImage()));
		
		//北部图片
		//this.add(jl_img,"North");
		//南部按钮
		//this.add(jp1,"South");
		//中部输入、提示框
		//this.add(jp2,"Center");
		
		//设置窗体显示位置大小
		//this.setSize(350,240);
		//this.setLocation(500,200);           //显示屏左边缘500像素,离显示屏上边缘200像素
		//this.setIconImage((new ImageIcon(this.getClass().getResource("/image/friendchat_qq.gif")).getImage()));
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		//如果点击登录按钮
		if(e.getSource()==jp1_jb_submit){
			User user=new User();
			user.setUser(jp2_jt_user.getText().trim());
			user.setPasswd(new String(jp2_jp_pwd.getPassword()));
			ClientToServer clientToServer=new ClientToServer();
			//登陆成功然后创建一个线程，用这个线程继续与服务器通信
			if(clientToServer.checkUser(user)){
				//登陆成功后，创建我的好友列表，然后发送请求获取好友信息
				FriendList friendList=new FriendList(user.getUser());
				//把联系人窗口加入到hashmap中
				SaveList.addFrindList(user.getUser(), friendList);
				try {
					ObjectOutputStream oos=new ObjectOutputStream(SaveThread.getClientToServer(user.getUser()).getS().getOutputStream());
					Message m=new Message();
					//获取好友列表的包,发送类型和发送者 
					m.setMesType(MessageType.message_get_onLineFriend);
					//m.setSender(user.getUser());
					oos.writeObject(m);
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
				frame.dispose();//登录窗口关闭释放资源
			}else{
				JOptionPane.showMessageDialog(this, "用户名密码错误");
			}
		}
	}	
}
