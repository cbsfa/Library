import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerWindow
{
    ManagerWindow(String account)
    {
        //新建窗口
        JFrame jFrame=new JFrame("管理员菜单");
        jFrame.setSize(700,600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Common common=new Common();

        //返回按钮
        JButton jButton=new JButton("退出");
        Dimension dimension=new Dimension(100,50);
        jButton.setPreferredSize(dimension);
        JPanel jPanel=new JPanel();
        jPanel.add(jButton);
        jPanel.setBounds(0,0,100,50);
        jFrame.add(jPanel);
        jPanel.setOpaque(false);

        //边栏
        Container container=jFrame.getContentPane();
        JTabbedPane jTabbedPane=new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane.setFont(new Font("",Font.BOLD,20));
        jTabbedPane.setBackground(Color.black);
        container.add(jTabbedPane,BorderLayout.CENTER);

        //查询图书界面
        ManagerSearchBook managerSearchBook=new ManagerSearchBook(jTabbedPane);
        jTabbedPane.addTab("查询图书",managerSearchBook.jLayeredPane);

        //添加图书界面
        ManagerAddBook managerAddBook=new ManagerAddBook(jTabbedPane);
        jTabbedPane.addTab("添加图书",managerAddBook.jLayeredPane);

        //个人信息界面
        ManagerInformation managerInformation=new ManagerInformation(account,jTabbedPane,jFrame);
        jTabbedPane.addTab("个人信息", managerInformation.jLayeredPane);

        //返回按钮事件
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "已登出","提示" , JOptionPane.PLAIN_MESSAGE);
                jFrame.dispose();
                LoginWindow loginWindow =new LoginWindow();
            }
        });

        //打开窗口
        jFrame.setVisible(true);
    }
}
