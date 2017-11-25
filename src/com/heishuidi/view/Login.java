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
 * ��½ҳ�桢�ͻ��˳��������
 * ��½����Ϊһ��JLable����JPanel
 * ��һ����ͼƬ���ڶ������û�����������򡣵������ŵ�¼��ť
 * */
public class Login extends JFrame implements ActionListener{
	JFrame frame=new JFrame("Ѷ�ĵ�¼����");
	
	//������ͼƬ
	JLabel jl_img;
	//�ϲ���������ť
	JPanel jp1;
	JButton jp1_jb_submit,jp1_jb_result;//��½���ð�ť
	//�����в���Ҫ�����
	JPanel jp2;
	JLabel jp2_jl_user,jp2_jl_pwd;//�û��������ı���ʾ��
	JTextField jp2_jt_user;//�û��������
	JPasswordField jp2_jp_pwd;//���������
	public static void main(String []args){
		new Login();
	}
	public Login(){
		//���Ǳ���ͼƬ·��   ��ȡͼƬ·�����������Դ��jar��
		//
		ImageIcon img = new ImageIcon(Login.class.getResource("/image/login_backgroup.gif"));
		JLabel imgLabel = new JLabel(img);//������ͼ���ڱ�ǩ�
		//ע�������ǹؼ�����������ǩ��ӵ�jfram��LayeredPane����
		frame.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		//���ñ�����ǩ��λ��this.getWidth(), this.getHeight()
		imgLabel.setBounds(0,0,img.getIconWidth(), img.getIconHeight());
		Container cp=frame.getContentPane();//����
		cp.setLayout(new BorderLayout());//�߿򲼾�
		
		
		
		//�������ͼƬ
		//jl_img=new JLabel(new ImageIcon("/image/login_tou.gif"));
		jl_img=new JLabel(new ImageIcon(this.getClass().getResource("/image/login_tou.gif")));
		//�ϲ�������ť���ͼƬ
		jp1=new JPanel();

		
		jp1_jb_submit=new JButton(new ImageIcon(this.getClass().getResource("/image/login_denglu.gif")));
		jp1_jb_submit.addActionListener(this);
		//jp1_jb_result=new JButton(new ImageIcon("./image/login_result.gif"));
		jp1_jb_result=new JButton(new ImageIcon(this.getClass().getResource("/image/login_result.gif")));
		//�в�������ı���
		jp2=new JPanel(new BorderLayout());//��񲼾�
		jp2_jl_user=new JLabel("QQ����",JLabel.CENTER);
		jp2_jl_pwd=new JLabel("QQ����",JLabel.CENTER);
		jp2_jt_user=new JTextField(15);//�û��������
		jp2_jp_pwd=new JPasswordField(15);//���������
		
		JPanel jp2_2=new JPanel();
		jp2_2.add(jp2_jl_user);
		jp2_2.add(jp2_jt_user);
		JPanel jp2_3=new JPanel();
		jp2_3.add(jp2_jl_pwd);
		jp2_3.add(jp2_jp_pwd);
		jp2_2.setOpaque(false);
		jp2_3.setOpaque(false);
		
		//��˳����뵽jpanel��
		jp2.add(jp2_2,"North");
		jp2.add(jp2_3,"Center");
		
		//������ť�ŵ�jp1��������Ϊjp1
		jp1.add(jp1_jb_submit);
		jp1.add(jp1_jb_result);
		
		//���ñ�����ɫ�ķ�ʽ
		jl_img.setOpaque(false);//�÷���setOpaque()��ʹ���ݴ���͸��
		jp2.setOpaque(false);
		jp1.setOpaque(false);
		cp.add(jl_img,"North");//����ť����봰�ڵ��������
		cp.add(jp1,"South");
		cp.add(jp2,"Center");
		
		((JPanel)cp).setOpaque(false); //ע����������������Ϊ͸��������LayeredPane����еı���������ʾ������
		frame.setSize(440,300);
		frame.setLocation(500,200);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage((new ImageIcon(this.getClass().getResource("/image/friendchat_qq.gif")).getImage()));
		
		//����ͼƬ
		//this.add(jl_img,"North");
		//�ϲ���ť
		//this.add(jp1,"South");
		//�в����롢��ʾ��
		//this.add(jp2,"Center");
		
		//���ô�����ʾλ�ô�С
		//this.setSize(350,240);
		//this.setLocation(500,200);           //��ʾ�����Ե500����,����ʾ���ϱ�Ե200����
		//this.setIconImage((new ImageIcon(this.getClass().getResource("/image/friendchat_qq.gif")).getImage()));
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		//��������¼��ť
		if(e.getSource()==jp1_jb_submit){
			User user=new User();
			user.setUser(jp2_jt_user.getText().trim());
			user.setPasswd(new String(jp2_jp_pwd.getPassword()));
			ClientToServer clientToServer=new ClientToServer();
			//��½�ɹ�Ȼ�󴴽�һ���̣߳�������̼߳����������ͨ��
			if(clientToServer.checkUser(user)){
				//��½�ɹ��󣬴����ҵĺ����б�Ȼ���������ȡ������Ϣ
				FriendList friendList=new FriendList(user.getUser());
				//����ϵ�˴��ڼ��뵽hashmap��
				SaveList.addFrindList(user.getUser(), friendList);
				try {
					ObjectOutputStream oos=new ObjectOutputStream(SaveThread.getClientToServer(user.getUser()).getS().getOutputStream());
					Message m=new Message();
					//��ȡ�����б�İ�,�������ͺͷ����� 
					m.setMesType(MessageType.message_get_onLineFriend);
					//m.setSender(user.getUser());
					oos.writeObject(m);
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
				frame.dispose();//��¼���ڹر��ͷ���Դ
			}else{
				JOptionPane.showMessageDialog(this, "�û����������");
			}
		}
	}	
}
