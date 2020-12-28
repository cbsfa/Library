import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManagerInformation
{
    JLayeredPane jLayeredPane=new JLayeredPane();
    Manager manager;
    ManagerInformation(String account,JTabbedPane jTabbedPane,JFrame jFrame)
    {
        Common common=new Common();
        manager=common.getManager(account);

        //�˺ű�ǩ
        JLabel jLabel=new JLabel("�˺�");
        Font font=new Font("",Font.BOLD,50);
        jLabel.setFont(font);
        jLabel.setBounds(50,50,100,100);
        jLayeredPane.add(jLabel);

        //�˺��ı���
        JTextField jTextField=new JTextField(manager.account);
        Dimension dimension=new Dimension(400,50);
        jTextField.setPreferredSize(dimension);
        jTextField.setBounds(175,75,400,50);
        jTextField.setEditable(false);
        jLayeredPane.add(jTextField);

        //������ǩ
        JLabel jLabel1=new JLabel("����");
        jLabel1.setFont(font);
        jLabel1.setBounds(50,150,100,100);
        jLayeredPane.add(jLabel1);

        //�����ı���
        JTextField jTextField1=new JTextField(manager.name);
        jTextField1.setPreferredSize(dimension);
        jTextField1.setBounds(175,175,400,50);
        jTextField1.setEditable(false);
        jLayeredPane.add(jTextField1);

        //�ֻ��ű�ǩ
        JLabel jLabel2=new JLabel("�ֻ���");
        jLabel2.setFont(font);
        jLabel2.setBounds(50,250,150,100);
        jLayeredPane.add(jLabel2);

        //�ֻ����ı���
        JTextField jTextField2=new JTextField(manager.phone.substring(0,4)+"***"+manager.phone.substring(7,11));
        Dimension dimension1=new Dimension(350,50);
        jTextField2.setPreferredSize(dimension1);
        jTextField2.setBounds(225,275,350,50);
        jTextField2.setEditable(false);
        jLayeredPane.add(jTextField2);

        //�޸�������ť
        JButton jButton=new JButton("�޸�����");
        Dimension dimension2=new Dimension(100,50);
        jButton.setPreferredSize(dimension2);
        jButton.setBounds(50,400,100,50);
        jLayeredPane.add(jButton);

        //�޸����밴ť
        JButton jButton1=new JButton("�޸�����");
        jButton1.setPreferredSize(dimension2);
        jButton1.setBounds(300,400,100,50);
        jLayeredPane.add(jButton1);

        //ע���˺Ű�ť
        JButton jButton2=new JButton("ע���˺�");
        jButton2.setPreferredSize(dimension2);
        jButton2.setBounds(550,400,100,50);
        jLayeredPane.add(jButton2);

        //�����¼�
        jTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                manager=common.getManager(account);
                jTextField.setText(manager.account);
                jTextField1.setText(manager.name);
                jTextField2.setText(manager.phone.substring(0,4)+"***"+manager.phone.substring(7,11));
            }
        });

        //�޸�������ť�¼�
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //�½�����
                JFrame jFrame=new JFrame("�޸�����");
                jFrame.setSize(300,150);
                jFrame.setLocationRelativeTo(null);
                jFrame.setLayout(null);
                jFrame.setResizable(false);

                //������ǩ
                JLabel jLabel3=new JLabel("����");
                Font font=new Font("",Font.BOLD,20);
                jLabel3.setFont(font);
                JPanel jPanel=new JPanel();
                jPanel.add(jLabel3);
                jPanel.setBounds(10,10,50,50);
                jFrame.add(jPanel);
                jPanel.setOpaque(false);

                //���������
                JTextField jTextField3=new JTextField();
                Dimension dimension=new Dimension(200,30);
                jTextField3.setPreferredSize(dimension);
                JPanel jPanel1=new JPanel();
                jPanel1.add(jTextField3);
                jPanel1.setBounds(60,10,200,30);
                jFrame.add(jPanel1);
                jPanel1.setOpaque(false);

                //ȷ�ϰ�ť
                JButton jButton3=new JButton("ȷ��");
                Dimension dimension3=new Dimension(100,50);
                jButton3.setPreferredSize(dimension3);
                JPanel jPanel2=new JPanel();
                jPanel2.add(jButton3);
                jPanel2.setBounds(100,50,100,50);
                jFrame.add(jPanel2);
                jPanel2.setOpaque(false);

                //ȷ�ϰ�ť�¼�
                jButton3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name=jTextField3.getText().trim();
                        if (name.equals("")) JOptionPane.showMessageDialog(null, "������������Ϊ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                        else
                        {
                            int flag=common.modifyManagerName(name,manager);
                            switch (flag)
                            {
                                case 0:JOptionPane.showMessageDialog(null, "����������ԭ������һ�£������޸�","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                case 1:
                                    JOptionPane.showMessageDialog(null, "�޸ĳɹ�","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                                    jFrame.dispose();
                                    jTextField1.setText(manager.name);
                                    break;
                            }
                        }
                    }
                });

                //�򿪴���
                jFrame.setVisible(true);
            }
        });

        //�޸����밴ť�¼�
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //�½�����
                JFrame jFrame=new JFrame("�޸�����");
                jFrame.setSize(300,260);
                jFrame.setLocationRelativeTo(null);
                jFrame.setLayout(null);
                jFrame.setResizable(false);

                //�������ǩ
                JLabel jLabel3=new JLabel("������");
                Font font=new Font("",Font.BOLD,20);
                jLabel3.setFont(font);
                JPanel jPanel=new JPanel();
                jPanel.add(jLabel3);
                jPanel.setBounds(10,10,65,50);
                jFrame.add(jPanel);
                jPanel.setOpaque(false);

                //�����������
                JPasswordField jPasswordField=new JPasswordField();
                Dimension dimension=new Dimension(175,30);
                jPasswordField.setPreferredSize(dimension);
                JPanel jPanel1=new JPanel();
                jPanel1.add(jPasswordField);
                jPanel1.setBounds(80,10,175,30);
                jFrame.add(jPanel1);
                jPanel1.setOpaque(false);

                //�������ǩ
                JLabel jLabel4=new JLabel("������");
                jLabel4.setFont(font);
                JPanel jPanel2=new JPanel();
                jPanel2.add(jLabel4);
                jPanel2.setBounds(10,60,65,50);
                jFrame.add(jPanel2);
                jPanel2.setOpaque(false);

                //�����������
                JPasswordField jPasswordField1=new JPasswordField();
                jPasswordField1.setPreferredSize(dimension);
                JPanel jPanel3=new JPanel();
                jPanel3.add(jPasswordField1);
                jPanel3.setBounds(80,60,175,30);
                jFrame.add(jPanel3);
                jPanel3.setOpaque(false);

                //������ʾ��ǩ
                JLabel jLabel5=new JLabel("*������6-18λ���֡���ĸ���*");
                Font font1=new Font("",Font.BOLD,12);
                jLabel5.setFont(font1);
                jLabel5.setForeground(Color.red);
                JPanel jPanel4=new JPanel();
                jPanel4.add(jLabel5);
                jPanel4.setBounds(60,90,200,30);
                jFrame.add(jPanel4);
                jPanel4.setOpaque(false);

                //�ظ������ǩ
                JLabel jLabel6=new JLabel("�ظ�����");
                jLabel6.setFont(font);
                JPanel jPanel5=new JPanel();
                jPanel5.add(jLabel6);
                jPanel5.setBounds(10,110,90,50);
                jFrame.add(jPanel5);
                jPanel5.setOpaque(false);

                //�ظ����������
                JPasswordField jPasswordField2=new JPasswordField();
                Dimension dimension1=new Dimension(155,30);
                jPasswordField2.setPreferredSize(dimension1);
                JPanel jPanel6=new JPanel();
                jPanel6.add(jPasswordField2);
                jPanel6.setBounds(100,110,155,30);
                jFrame.add(jPanel6);
                jPanel6.setOpaque(false);

                //ȷ�ϰ�ť
                JButton jButton3=new JButton("ȷ��");
                Dimension dimension2=new Dimension(100,50);
                jButton3.setPreferredSize(dimension2);
                JPanel jPanel7=new JPanel();
                jPanel7.add(jButton3);
                jPanel7.setBounds(100,160,100,50);
                jFrame.add(jPanel7);
                jPanel7.setOpaque(false);

                //ȷ�ϰ�ť�¼�
                jButton3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String oldPassword=jPasswordField.getText().trim();
                        String newPassword=jPasswordField1.getText().trim();
                        String rePassword=jPasswordField2.getText().trim();
                        if (oldPassword.equals("")) JOptionPane.showMessageDialog(null, "�����벻��Ϊ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                        else if (newPassword.equals("")) JOptionPane.showMessageDialog(null, "�����벻��Ϊ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                        else if (rePassword.equals("")) JOptionPane.showMessageDialog(null, "�ظ����벻��Ϊ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                        else
                        {
                            int flag=common.modifyManagerPassword(oldPassword,newPassword,rePassword,manager);
                            switch (flag)
                            {
                                case 0:JOptionPane.showMessageDialog(null, "���������","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                case 1:JOptionPane.showMessageDialog(null, "����ӦΪ6-18λ���֡���ĸ���","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                case 2:JOptionPane.showMessageDialog(null, "�ظ����벻һ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                case 3:
                                    JOptionPane.showMessageDialog(null, "�޸ĳɹ�","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                                    jFrame.dispose();
                                    break;
                            }
                        }
                    }
                });

                //�򿪴���
                jFrame.setVisible(true);
            }
        });

        //ע���˺Ű�ť�¼�
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //�½�����
                JFrame jFrame1=new JFrame("ע���˺�");
                jFrame1.setSize(300,300);
                jFrame1.setLocationRelativeTo(null);
                jFrame1.setLayout(null);
                jFrame1.setResizable(false);

                //�˺ű�ǩ
                JLabel jLabel3=new JLabel("�˺�");
                Font font=new Font("",Font.BOLD,20);
                jLabel3.setFont(font);
                JPanel jPanel=new JPanel();
                jPanel.add(jLabel3);
                jPanel.setBounds(10,10,50,50);
                jFrame1.add(jPanel);
                jPanel.setOpaque(false);

                //�˺������
                JTextField jTextField3=new JTextField();
                Dimension dimension=new Dimension(200,30);
                jTextField3.setPreferredSize(dimension);
                JPanel jPanel1=new JPanel();
                jPanel1.add(jTextField3);
                jPanel1.setBounds(60,10,200,30);
                jFrame1.add(jPanel1);
                jPanel1.setOpaque(false);

                //�����ǩ
                JLabel jLabel4=new JLabel("����");
                jLabel4.setFont(font);
                JPanel jPanel2=new JPanel();
                jPanel2.add(jLabel4);
                jPanel2.setBounds(10,60,50,50);
                jFrame1.add(jPanel2);
                jPanel2.setOpaque(false);

                //���������
                JPasswordField jPasswordField=new JPasswordField();
                jPasswordField.setPreferredSize(dimension);
                JPanel jPanel3=new JPanel();
                jPanel3.add(jPasswordField);
                jPanel3.setBounds(60,60,200,30);
                jFrame1.add(jPanel3);
                jPanel3.setOpaque(false);

                //������ǩ
                JLabel jLabel6=new JLabel("����");
                jLabel6.setFont(font);
                JPanel jPanel4=new JPanel();
                jPanel4.add(jLabel6);
                jPanel4.setBounds(10,110,50,50);
                jFrame1.add(jPanel4);
                jPanel4.setOpaque(false);

                //���������
                JTextField jTextField4=new JTextField();
                jTextField4.setPreferredSize(dimension);
                JPanel jPanel5=new JPanel();
                jPanel5.add(jTextField4);
                jPanel5.setBounds(60,110,200,30);
                jFrame1.add(jPanel5);
                jPanel5.setOpaque(false);

                //�ֻ��ű�ǩ
                JLabel jLabel7=new JLabel("�ֻ���");
                jLabel7.setFont(font);
                JPanel jPanel6=new JPanel();
                jPanel6.add(jLabel7);
                jPanel6.setBounds(10,160,70,50);
                jFrame1.add(jPanel6);
                jPanel6.setOpaque(false);

                //�ֻ��������
                JTextField jTextField5=new JTextField();
                Dimension dimension2=new Dimension(175,30);
                jTextField5.setPreferredSize(dimension2);
                JPanel jPanel7=new JPanel();
                jPanel7.add(jTextField5);
                jPanel7.setBounds(80,160,175,30);
                jFrame1.add(jPanel7);
                jPanel7.setOpaque(false);

                //�����ֻ��������ֻ����������
                jTextField5.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        super.keyTyped(e);
                        int keyChar=e.getKeyChar();
                        if (keyChar<KeyEvent.VK_0||keyChar>KeyEvent.VK_9) e.consume();
                    }
                });

                //ȷ�ϰ�ť
                JButton jButton3=new JButton("ȷ��");
                Dimension dimension3=new Dimension(100,50);
                jButton3.setPreferredSize(dimension3);
                JPanel jPanel8=new JPanel();
                jPanel8.add(jButton3);
                jPanel8.setBounds(100,210,100,50);
                jFrame1.add(jPanel8);
                jPanel8.setOpaque(false);

                //ȷ�ϰ�ť�¼�
                jButton3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String account=jTextField3.getText().trim();
                        String password=jPasswordField.getText().trim();
                        String name=jTextField4.getText().trim();
                        String phone=jTextField5.getText().trim();
                        int flag=common.logoutManager(account,password,name,phone,manager);
                        switch (flag)
                        {
                            case 0:JOptionPane.showMessageDialog(null, "�˻���Ϣ��ƥ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                            case 1:
                                JOptionPane.showMessageDialog(null, "ע���ɹ�","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                                jFrame.dispose();
                                jFrame1.dispose();
                                LoginWindow loginWindow=new LoginWindow();
                                break;
                        }
                    }
                });

                //�򿪴���
                jFrame1.setVisible(true);
            }
        });
    }
}
