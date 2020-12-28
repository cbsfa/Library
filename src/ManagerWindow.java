import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerWindow
{
    ManagerWindow(String account)
    {
        //�½�����
        JFrame jFrame=new JFrame("����Ա�˵�");
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
        ManagerSearchBook managerSearchBook=new ManagerSearchBook(jTabbedPane);
        jTabbedPane.addTab("��ѯͼ��",managerSearchBook.jLayeredPane);

        //���ͼ�����
        ManagerAddBook managerAddBook=new ManagerAddBook(jTabbedPane);
        jTabbedPane.addTab("���ͼ��",managerAddBook.jLayeredPane);

        //������Ϣ����
        ManagerInformation managerInformation=new ManagerInformation(account,jTabbedPane,jFrame);
        jTabbedPane.addTab("������Ϣ", managerInformation.jLayeredPane);

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
