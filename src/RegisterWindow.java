import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class RegisterWindow
{
    String account;
    String password;
    String rePassword;
    String name;
    String phone;
    RegisterWindow()
    {
        //新建窗口
        JFrame jFrame=new JFrame("注册");
        jFrame.setSize(300,350);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(null);
        jFrame.setResizable(false);
        Common common=new Common();

        //账号标签
        JLabel jLabel=new JLabel("账号");
        Font font=new Font("",Font.BOLD,20);
        jLabel.setFont(font);
        JPanel jPanel=new JPanel();
        jPanel.add(jLabel);
        jPanel.setBounds(10,10,50,50);
        jFrame.add(jPanel);
        jPanel.setOpaque(false);

        //账号输入框
        JTextField jTextField=new JTextField();
        Dimension dimension=new Dimension(200,30);
        jTextField.setPreferredSize(dimension);
        JPanel jPanel1=new JPanel();
        jPanel1.add(jTextField);
        jPanel1.setBounds(60,10,200,30);
        jFrame.add(jPanel1);
        jPanel1.setOpaque(false);

        //密码标签
        JLabel jLabel1=new JLabel("密码");
        jLabel1.setFont(font);
        JPanel jPanel2=new JPanel();
        jPanel2.add(jLabel1);
        jPanel2.setBounds(10,60,50,50);
        jFrame.add(jPanel2);
        jPanel2.setOpaque(false);

        //密码输入框
        JPasswordField jPasswordField=new JPasswordField();
        jPasswordField.setPreferredSize(dimension);
        JPanel jPanel3=new JPanel();
        jPanel3.add(jPasswordField);
        jPanel3.setBounds(60,60,200,30);
        jFrame.add(jPanel3);
        jPanel3.setOpaque(false);

        //密码提示标签
        JLabel jLabel2=new JLabel("*密码由6-18位数字、字母组成*");
        Font font1=new Font("",Font.BOLD,12);
        jLabel2.setFont(font1);
        jLabel2.setForeground(Color.red);
        JPanel jPanel4=new JPanel();
        jPanel4.add(jLabel2);
        jPanel4.setBounds(60,90,200,30);
        jFrame.add(jPanel4);
        jPanel4.setOpaque(false);

        //重复密码标签
        JLabel jLabel3=new JLabel("重复密码");
        jLabel3.setFont(font);
        JPanel jPanel5=new JPanel();
        jPanel5.add(jLabel3);
        jPanel5.setBounds(10,110,90,50);
        jFrame.add(jPanel5);
        jPanel5.setOpaque(false);

        //重复密码输入框
        JPasswordField jPasswordField1=new JPasswordField();
        Dimension dimension1=new Dimension(155,30);
        jPasswordField1.setPreferredSize(dimension1);
        JPanel jPanel6=new JPanel();
        jPanel6.add(jPasswordField1);
        jPanel6.setBounds(100,110,155,30);
        jFrame.add(jPanel6);
        jPanel6.setOpaque(false);

        //姓名标签
        JLabel jLabel4=new JLabel("姓名");
        jLabel4.setFont(font);
        JPanel jPanel7=new JPanel();
        jPanel7.add(jLabel4);
        jPanel7.setBounds(10,160,50,50);
        jFrame.add(jPanel7);
        jPanel7.setOpaque(false);

        //姓名输入框
        JTextField jTextField1=new JTextField();
        jTextField1.setPreferredSize(dimension);
        JPanel jPanel8=new JPanel();
        jPanel8.add(jTextField1);
        jPanel8.setBounds(60,160,200,30);
        jFrame.add(jPanel8);
        jPanel8.setOpaque(false);

        //手机号标签
        JLabel jLabel5=new JLabel("手机号");
        jLabel5.setFont(font);
        JPanel jPanel9=new JPanel();
        jPanel9.add(jLabel5);
        jPanel9.setBounds(10,210,70,50);
        jFrame.add(jPanel9);
        jPanel9.setOpaque(false);

        //手机号输入框
        JTextField jTextField2=new JTextField();
        Dimension dimension2=new Dimension(175,30);
        jTextField2.setPreferredSize(dimension2);
        JPanel jPanel10=new JPanel();
        jPanel10.add(jTextField2);
        jPanel10.setBounds(80,210,175,30);
        jFrame.add(jPanel10);
        jPanel10.setOpaque(false);

        //限制手机号输入框只能输入数字
        jTextField2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                int keyChar=e.getKeyChar();
                if (keyChar<KeyEvent.VK_0||keyChar>KeyEvent.VK_9) e.consume();
            }
        });

        //注册按钮
        JButton jButton=new JButton("注册");
        Dimension dimension3=new Dimension(100,50);
        jButton.setPreferredSize(dimension3);
        JPanel jPanel11=new JPanel();
        jPanel11.add(jButton);
        jPanel11.setBounds(100,250,100,50);
        jFrame.add(jPanel11);
        jPanel11.setOpaque(false);

        //注册按钮事件
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                account=jTextField.getText().trim();
                password=jPasswordField.getText().trim();
                rePassword=jPasswordField1.getText().trim();
                name=jTextField1.getText().trim();
                phone=jTextField2.getText().trim();
                try
                {
                    if (account.equals("")) JOptionPane.showMessageDialog(null, "账号不能为空","提示" , JOptionPane.PLAIN_MESSAGE);
                    else if (password.equals("")) JOptionPane.showMessageDialog(null, "密码不能为空","提示" , JOptionPane.PLAIN_MESSAGE);
                    else if (rePassword.equals("")) JOptionPane.showMessageDialog(null, "重复密码不能为空","提示" , JOptionPane.PLAIN_MESSAGE);
                    else if (name.equals("")) JOptionPane.showMessageDialog(null, "姓名不能为空","提示" , JOptionPane.PLAIN_MESSAGE);
                    else if (phone.equals("")) JOptionPane.showMessageDialog(null, "手机号不能为空","提示" , JOptionPane.PLAIN_MESSAGE);
                    else
                    {
                        int flag=common.register(account,password,rePassword,name,phone);
                        switch (flag)
                        {
                            case 0:JOptionPane.showMessageDialog(null, "账号已存在","提示" , JOptionPane.PLAIN_MESSAGE);break;
                            case 1:JOptionPane.showMessageDialog(null, "密码应为6-18位数字、字母组成","提示" , JOptionPane.PLAIN_MESSAGE);break;
                            case 2:JOptionPane.showMessageDialog(null, "重复密码不一致","提示" , JOptionPane.PLAIN_MESSAGE);break;
                            case 3:JOptionPane.showMessageDialog(null, "手机号应为11位整数","提示" , JOptionPane.PLAIN_MESSAGE);break;
                            case 4:JOptionPane.showMessageDialog(null, "手机号已存在","提示" , JOptionPane.PLAIN_MESSAGE);break;
                            case 5:
                                JOptionPane.showMessageDialog(null, "注册成功","提示" , JOptionPane.PLAIN_MESSAGE);
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

        //打开窗口
        jFrame.setVisible(true);
    }
}
