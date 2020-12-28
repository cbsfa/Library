import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserWindow
{
    UserWindow(String account)
    {
        //�½�����
        JFrame jFrame=new JFrame("�û��˵�");
        jFrame.setSize(700,600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Common common=new Common();

        //���ذ�ť
        JButton jButton=new JButton("�˳�");
        Dimension dimension=new Dimension(100,50);
        jButton.setPreferredSize(dimension);
        JPanel jPanel=new JPanel();
        jPanel.add(jButton);
        jPanel.setBounds(0,0,100,50);
        jFrame.add(jPanel);
        jPanel.setOpaque(false);

        //����
        Container container=jFrame.getContentPane();
        JTabbedPane jTabbedPane=new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane.setFont(new Font("",Font.BOLD,20));
        jTabbedPane.setBackground(Color.black);
        container.add(jTabbedPane,BorderLayout.CENTER);

        //��ѯͼ�����
        UserSearchBook userSearchBook=new UserSearchBook(account,jTabbedPane);
        jTabbedPane.addTab("��ѯͼ��", userSearchBook.jLayeredPane);

        //�黹ͼ�����
        UserBorrowReport userBorrowReport=new UserBorrowReport(account,jTabbedPane);
        jTabbedPane.addTab("���ļ�¼",userBorrowReport.jLayeredPane);

        //������Ϣ����
        UserInformation userInformation=new UserInformation(account,jTabbedPane,jFrame);
        jTabbedPane.addTab("������Ϣ",userInformation.jLayeredPane);

        //���ذ�ť�¼�
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "�ѵǳ�","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                jFrame.dispose();
                LoginWindow loginWindow =new LoginWindow();
            }
        });

        //�򿪴���
        jFrame.setVisible(true);
    }
}
