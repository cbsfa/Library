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
        //新建窗口
        JFrame jFrame=new JFrame("登录");
        jFrame.setSize(700,600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Common common=new Common();

        //标题
        JLabel jLabel=new JLabel("图书馆");
        Font font=new Font("",Font.BOLD,40);
        jLabel.setFont(font);
        JPanel jPanel=new JPanel();
        jPanel.add(jLabel);
        jPanel.setBounds(200,50,300,100);
        jFrame.add(jPanel);
        jPanel.setOpaque(false);

        //账号标签
        JLabel jLabel1=new JLabel("账号");
        Font font1=new Font("",Font.BOLD,25);
        jLabel1.setFont(font1);
        JPanel jPanel1=new JPanel();
        jPanel1.add(jLabel1);
        jPanel1.setBounds(100,210,50,50);
        jFrame.add(jPanel1);
        jPanel1.setOpaque(false);

        //账号输入框
        JTextField jTextField=new JTextField();
        Dimension dimension=new Dimension(300,50);
        jTextField.setPreferredSize(dimension);
        JPanel jPanel2=new JPanel();
        jPanel2.add(jTextField);
        jPanel2.setBounds(200,200,300,50);
        jFrame.add(jPanel2);
        jPanel2.setOpaque(false);

        //密码标签
        JLabel jLabel2=new JLabel("密码");
        jLabel2.setFont(font1);
        JPanel jPanel3=new JPanel();
        jPanel3.add(jLabel2);
        jPanel3.setBounds(100,320,50,50);
        jFrame.add(jPanel3);
        jPanel3.setOpaque(false);

        //密码输入框
        JPasswordField jPasswordField=new JPasswordField();
        jPasswordField.setPreferredSize(dimension);
        JPanel jPanel4=new JPanel();
        jPanel4.add(jPasswordField);
        jPanel4.setBounds(200,300,300,50);
        jFrame.add(jPanel4);
        jPanel4.setOpaque(false);

        //登录按钮
        JButton jButton=new JButton("登录");
        Dimension dimension1=new Dimension(100,50);
        jButton.setPreferredSize(dimension1);
        JPanel jPanel5=new JPanel();
        jPanel5.add(jButton);
        jPanel5.setBounds(300,400,100,50);
        jFrame.add(jPanel5);
        jPanel5.setOpaque(false);

        //注册按钮
        JButton jButton1=new JButton("注册");
        jButton1.setPreferredSize(dimension1);
        JPanel jPanel6=new JPanel();
        jPanel6.add(jButton1);
        jPanel6.setBounds(550,200,100,50);
        jFrame.add(jPanel6);
        jPanel6.setOpaque(false);

        //找回密码按钮
        JButton jButton2=new JButton("找回密码");
        jButton2.setPreferredSize(dimension1);
        JPanel jPanel7=new JPanel();
        jPanel7.add(jButton2);
        jPanel7.setBounds(550,300,100,50);
        jFrame.add(jPanel7);
        jPanel7.setOpaque(false);

        //登录按钮事件
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                account=jTextField.getText().trim();
                password=jPasswordField.getText().trim();
                int flag=common.login(account,password);
                switch (flag)
                {
                    case 0:JOptionPane.showMessageDialog(null, "账号错误","提示" , JOptionPane.PLAIN_MESSAGE);break;
                    case 1:JOptionPane.showMessageDialog(null, "密码错误","提示" , JOptionPane.PLAIN_MESSAGE);break;
                    case 2:
                        JOptionPane.showMessageDialog(null, "欢迎用户"+account+"登入图书馆","提示" , JOptionPane.PLAIN_MESSAGE);
                        jFrame.dispose();
                        UserWindow userWindow =new UserWindow(account);
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(null, "欢迎管理员"+account+"登入图书馆","提示" , JOptionPane.PLAIN_MESSAGE);
                        jFrame.dispose();
                        ManagerWindow managerWindow =new ManagerWindow(account);
                        break;
                }
            }
        });

        //注册按钮事件
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

        //找回密码按钮事件
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

        //打开窗口
        jFrame.setVisible(true);
    }
}
