import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow
{
    String account;
    String password;
    LoginWindow()
    {
        //�½�����
        JFrame jFrame=new JFrame("��¼");
        jFrame.setSize(700,600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Common common=new Common();

        //����
        JLabel jLabel=new JLabel("ͼ���");
        Font font=new Font("",Font.BOLD,40);
        jLabel.setFont(font);
        JPanel jPanel=new JPanel();
        jPanel.add(jLabel);
        jPanel.setBounds(200,50,300,100);
        jFrame.add(jPanel);
        jPanel.setOpaque(false);

        //�˺ű�ǩ
        JLabel jLabel1=new JLabel("�˺�");
        Font font1=new Font("",Font.BOLD,25);
        jLabel1.setFont(font1);
        JPanel jPanel1=new JPanel();
        jPanel1.add(jLabel1);
        jPanel1.setBounds(100,210,50,50);
        jFrame.add(jPanel1);
        jPanel1.setOpaque(false);

        //�˺������
        JTextField jTextField=new JTextField();
        Dimension dimension=new Dimension(300,50);
        jTextField.setPreferredSize(dimension);
        JPanel jPanel2=new JPanel();
        jPanel2.add(jTextField);
        jPanel2.setBounds(200,200,300,50);
        jFrame.add(jPanel2);
        jPanel2.setOpaque(false);

        //�����ǩ
        JLabel jLabel2=new JLabel("����");
        jLabel2.setFont(font1);
        JPanel jPanel3=new JPanel();
        jPanel3.add(jLabel2);
        jPanel3.setBounds(100,320,50,50);
        jFrame.add(jPanel3);
        jPanel3.setOpaque(false);

        //���������
        JPasswordField jPasswordField=new JPasswordField();
        jPasswordField.setPreferredSize(dimension);
        JPanel jPanel4=new JPanel();
        jPanel4.add(jPasswordField);
        jPanel4.setBounds(200,300,300,50);
        jFrame.add(jPanel4);
        jPanel4.setOpaque(false);

        //��¼��ť
        JButton jButton=new JButton("��¼");
        Dimension dimension1=new Dimension(100,50);
        jButton.setPreferredSize(dimension1);
        JPanel jPanel5=new JPanel();
        jPanel5.add(jButton);
        jPanel5.setBounds(300,400,100,50);
        jFrame.add(jPanel5);
        jPanel5.setOpaque(false);

        //ע�ᰴť
        JButton jButton1=new JButton("ע��");
        jButton1.setPreferredSize(dimension1);
        JPanel jPanel6=new JPanel();
        jPanel6.add(jButton1);
        jPanel6.setBounds(550,200,100,50);
        jFrame.add(jPanel6);
        jPanel6.setOpaque(false);

        //�һ����밴ť
        JButton jButton2=new JButton("�һ�����");
        jButton2.setPreferredSize(dimension1);
        JPanel jPanel7=new JPanel();
        jPanel7.add(jButton2);
        jPanel7.setBounds(550,300,100,50);
        jFrame.add(jPanel7);
        jPanel7.setOpaque(false);

        //��¼��ť�¼�
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                account=jTextField.getText().trim();
                password=jPasswordField.getText().trim();
                int flag=common.login(account,password);
                switch (flag)
                {
                    case 0:JOptionPane.showMessageDialog(null, "�˺Ŵ���","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                    case 1:JOptionPane.showMessageDialog(null, "�������","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                    case 2:
                        JOptionPane.showMessageDialog(null, "��ӭ�û�"+account+"����ͼ���","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                        jFrame.dispose();
                        UserWindow userWindow =new UserWindow(account);
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(null, "��ӭ����Ա"+account+"����ͼ���","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                        jFrame.dispose();
                        ManagerWindow managerWindow =new ManagerWindow(account);
                        break;
                }
            }
        });

        //ע�ᰴť�¼�
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    RegisterWindow registerWindow =new RegisterWindow();
                } catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        //�һ����밴ť�¼�
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    RetrieveWindow retrieveWindow =new RetrieveWindow();
                } catch (Exception e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        //�򿪴���
        jFrame.setVisible(true);
    }
}
