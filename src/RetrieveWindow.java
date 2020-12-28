import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RetrieveWindow
{
    String account;
    String password;
    String rePassword;
    String phone;
    RetrieveWindow()
    {
        //�½�����
        JFrame jFrame=new JFrame("�һ�����");
        jFrame.setSize(300,300);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(null);
        jFrame.setResizable(false);
        Common common=new Common();

        //�˺ű�ǩ
        JLabel jLabel=new JLabel("�˺�");
        Font font=new Font("",Font.BOLD,20);
        jLabel.setFont(font);
        JPanel jPanel=new JPanel();
        jPanel.add(jLabel);
        jPanel.setBounds(10,10,50,50);
        jFrame.add(jPanel);
        jPanel.setOpaque(false);

        //�˺������
        JTextField jTextField=new JTextField();
        Dimension dimension=new Dimension(200,30);
        jTextField.setPreferredSize(dimension);
        JPanel jPanel1=new JPanel();
        jPanel1.add(jTextField);
        jPanel1.setBounds(60,10,200,30);
        jFrame.add(jPanel1);
        jPanel1.setOpaque(false);

        //�ֻ��ű�ǩ
        JLabel jLabel1=new JLabel("�ֻ���");
        jLabel1.setFont(font);
        JPanel jPanel2=new JPanel();
        jPanel2.add(jLabel1);
        jPanel2.setBounds(10,60,70,50);
        jFrame.add(jPanel2);
        jPanel2.setOpaque(false);

        //�ֻ��������
        JTextField jTextField1=new JTextField();
        Dimension dimension1=new Dimension(175,30);
        jTextField1.setPreferredSize(dimension1);
        JPanel jPanel3=new JPanel();
        jPanel3.add(jTextField1);
        jPanel3.setBounds(80,60,175,30);
        jFrame.add(jPanel3);
        jPanel3.setOpaque(false);

        //�����ֻ��������ֻ����������
        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                int keyChar=e.getKeyChar();
                if (keyChar<KeyEvent.VK_0||keyChar>KeyEvent.VK_9) e.consume();
            }
        });

        //�����ǩ
        JLabel jLabel2=new JLabel("����");
        jLabel2.setFont(font);
        JPanel jPanel4=new JPanel();
        jPanel4.add(jLabel2);
        jPanel4.setBounds(10,110,50,50);
        jFrame.add(jPanel4);
        jPanel4.setOpaque(false);

        //���������
        JPasswordField jPasswordField=new JPasswordField();
        jPasswordField.setPreferredSize(dimension);
        JPanel jPanel5=new JPanel();
        jPanel5.add(jPasswordField);
        jPanel5.setBounds(60,110,200,30);
        jFrame.add(jPanel5);
        jPanel5.setOpaque(false);

        //������ʾ��ǩ
        JLabel jLabel3=new JLabel("*������6-18λ���֡���ĸ���*");
        Font font1=new Font("",Font.BOLD,12);
        jLabel3.setFont(font1);
        jLabel3.setForeground(Color.red);
        JPanel jPanel6=new JPanel();
        jPanel6.add(jLabel3);
        jPanel6.setBounds(60,140,200,30);
        jFrame.add(jPanel6);
        jPanel6.setOpaque(false);

        //�ظ������ǩ
        JLabel jLabel4=new JLabel("�ظ�����");
        jLabel4.setFont(font);
        JPanel jPanel7=new JPanel();
        jPanel7.add(jLabel4);
        jPanel7.setBounds(10,160,90,50);
        jFrame.add(jPanel7);
        jPanel7.setOpaque(false);

        //�ظ����������
        JPasswordField jPasswordField1=new JPasswordField();
        Dimension dimension2=new Dimension(155,30);
        jPasswordField1.setPreferredSize(dimension2);
        JPanel jPanel8=new JPanel();
        jPanel8.add(jPasswordField1);
        jPanel8.setBounds(100,160,155,30);
        jFrame.add(jPanel8);
        jPanel8.setOpaque(false);

        //�һ����밴ť
        JButton jButton=new JButton("�һ�����");
        Dimension dimension3=new Dimension(100,50);
        jButton.setPreferredSize(dimension3);
        JPanel jPanel9=new JPanel();
        jPanel9.add(jButton);
        jPanel9.setBounds(100,200,100,50);
        jFrame.add(jPanel9);
        jPanel9.setOpaque(false);

        //�һ����밴ť�¼�
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                account = jTextField.getText().trim();
                password = jPasswordField.getText().trim();
                rePassword = jPasswordField1.getText().trim();
                phone = jTextField1.getText().trim();
                try
                {
                    if (account.equals("")) JOptionPane.showMessageDialog(null, "�˺Ų���Ϊ��", "��ʾ", JOptionPane.PLAIN_MESSAGE);
                    else if (phone.equals("")) JOptionPane.showMessageDialog(null, "�ֻ��Ų���Ϊ��", "��ʾ", JOptionPane.PLAIN_MESSAGE);
                    else if (password.equals("")) JOptionPane.showMessageDialog(null, "���벻��Ϊ��", "��ʾ", JOptionPane.PLAIN_MESSAGE);
                    else if (rePassword.equals("")) JOptionPane.showMessageDialog(null, "�ظ����벻��Ϊ��", "��ʾ", JOptionPane.PLAIN_MESSAGE);
                    else
                    {
                        int flag=common.retrieve(account,password,rePassword,phone);
                        switch (flag)
                        {
                            case 0:JOptionPane.showMessageDialog(null, "�˺Ŵ���","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                            case 1:JOptionPane.showMessageDialog(null, "�ֻ��Ŵ���","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                            case 2:JOptionPane.showMessageDialog(null, "����ӦΪ6-18λ���֡���ĸ���","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                            case 3:JOptionPane.showMessageDialog(null, "�ظ����벻һ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                            case 4:
                                JOptionPane.showMessageDialog(null, "�һ�����ɹ�","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                                jFrame.dispose();
                                break;
                        }
                    }
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
