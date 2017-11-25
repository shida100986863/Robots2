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
/**��������ҵĺ��Ѻ�İ��������JPanel���
 * ���һ����ʾһ����Ƭ
 * ����һ��д������Ƭ���ÿ�Ƭ�������
 * */
public class FriendList extends JFrame implements ActionListener,MouseListener{
	String myId;//�����Լ���id������ʾ�������������
	//��һ������е����
	JPanel jp1_1,jp1_2,jp1_3;	//�ҵĺ��ѡ����к��ѡ�İ�������
	JButton jb1_friend,jb1_stranger;//����İ���˰�ť
	JScrollPane jsp1;//������
	//�ڶ�������е����
	JPanel jp2_1,jp2_2,jp2_3;//�ҵĺ��ѡ�İ���ˡ�İ�����������
	JButton jb2_friend,jb2_stranger;
	JScrollPane jsp2;//������
	
	JLabel []jbls;//�����б�
	CardLayout cl;//�ÿ�Ƭ����
	
	public FriendList(String Id){
		this.myId=Id;
		//��ʼ����һ�ſ�Ƭ
		jb1_friend=new JButton("�ҵĺ���");
		jb1_stranger=new JButton("İ����");
		jb1_stranger.addActionListener(this);
		//�߿򲼾�  �ٶ�50������
		jp1_1=new JPanel(new BorderLayout());
		jp1_2=new JPanel(new GridLayout(50,1,4,4));//���񲼾�
		//��jbls��ʼ��50������
		jbls=new JLabel[50];
		for(int i=0;i<jbls.length;i++){
			//�����б����ƺ�ͼƬѭ���ӵ�jphy2��
			jbls[i]=new JLabel(i+1+"",new ImageIcon(this.getClass().getResource("/image/friendlist_mm.jpg")),JLabel.LEFT);
			jbls[i].setEnabled(false);
			//����Ǳ���ID�Ļ�����Ϊ��ʾ
			if(jbls[i].getText().equals(myId)){
				jbls[i].setEnabled(true);
			}
			//��������Ѽ��������¼�
			jbls[i].addMouseListener(this);
			jp1_2.add(jbls[i]);
		}
		//��ӹ�����
		jsp1=new JScrollPane(jp1_2);
		//İ���˰�ť
		jp1_3=new JPanel(new GridLayout(1,1));
		jp1_3.add(jb1_stranger);
		jp1_1.add(jb1_friend,"North");
		jp1_1.add(jsp1,"Center");
		jp1_1.add(jp1_3,"South");
		
		//��ʼ���ڶ��ſ�Ƭ
		jb2_friend=new JButton("�ҵĺ���");
		jb2_friend.addActionListener(this);
		jb2_stranger=new JButton("İ����");
		//��ʼ�����
		jp2_1=new JPanel(new BorderLayout());
		//�ٶ���20��İ����
		jp2_2=new JPanel(new GridLayout(20, 1, 4, 4));
		JLabel []jbls2=new JLabel[20];
		for(int i=0;i<jbls2.length;i++){
			//�����б����ƺ�ͼƬѭ���ӵ�jphy2��
			jbls2[i]=new JLabel(i+1+"",new ImageIcon(this.getClass().getResource("/image/friendlist_mm.jpg")),JLabel.LEFT);
			jp2_2.add(jbls2[i]);
		}
		jp2_3=new JPanel(new GridLayout(2,1));
		jp2_3.add(jb2_friend);//�ҵĺ���
		jp2_3.add(jb2_stranger);//İ����
		//������
		jsp2=new JScrollPane(jp2_2);
		
		jp2_1.add(jp2_3,"North");
		jp2_1.add(jsp2,"Center");
		
		cl=new CardLayout();
		this.setLayout(cl);//���ò��ַ�ʽΪ��Ƭ����
		this.add(jp1_1,"1");//�������ǵı�ţ������л�����
		this.add(jp2_1,"2");
		this.setTitle(myId);//������ʾ��ǰ��ID
		this.setSize(300,600);//
		this.setLocation(800,20);           //��ʾ�����Ե500����,����ʾ���ϱ�Ե200����
		this.setIconImage((new ImageIcon(this.getClass().getResource("/image/friendchat_qq.gif")).getImage()));
		//���ùرմ����¼�
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				//��ֹ��ǰ�߳�
				//����myId�ҽ��̣�Ȼ����ֹ
				ClientToServerThread c=SaveThread.getClientToServer(myId);
				try {
					Message m=new Message();
					//���͸�������Ϣ��,���ߵ��û�
					m.setMesType(MessageType.message_downLine);
					m.setSender(myId);
					ObjectOutputStream oos=new ObjectOutputStream(SaveThread.getClientToServer(myId).getS().getOutputStream());
					oos.writeObject(m);
					c.getS().close();
					c.stop();
					System.exit(0);
				} catch (IOException e1) {
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		this.setVisible(true);
	}
	//�������ߵĺ������
	//message.getCon()��ȡ���������������̣߳��û����ÿո�ָ�
	//����~~~~����뻻��ƴ�����ֵĻ�jbls[i].setText();������ʾ�ɱ�ע
	//123����
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
		// TODO �Զ����ɵķ������
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
		// TODO �Զ����ɵķ������
		//��Ӧ�û�˫��������ú��ѵı��
		//=2Ϊ˫��  ˫��ͨ���Ļ��Ž�������
		if(e.getClickCount()==2){
			String frindNo=((JLabel)e.getSource()).getText();
			//˫�����������
			FriendChat friendChat=new FriendChat(this.myId,frindNo);
			//���շ����ߣ������߱���
			SaveChat.addFriendChat(this.myId+" "+frindNo, friendChat);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO �Զ����ɵķ������
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO �Զ����ɵķ������
		//��ȡ��jlabelȻ����������ǰ��ɫ
		JLabel l1=(JLabel)e.getSource();
		l1.setForeground(Color.red);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �Զ����ɵķ������
		JLabel l1=(JLabel)e.getSource();
		l1.setForeground(Color.black);
	}
	
}
