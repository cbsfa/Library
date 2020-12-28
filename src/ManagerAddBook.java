import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ManagerAddBook
{
    JLayeredPane jLayeredPane=new JLayeredPane();
    ManagerAddBook(JTabbedPane jTabbedPane)
    {
        Common common=new Common();

        //ISBN号标签
        JLabel jLabel=new JLabel("ISBN号");
        Font font=new Font("",Font.BOLD,20);
        jLabel.setFont(font);
        jLabel.setBounds(100,60,75,50);
        jLayeredPane.add(jLabel);

        //ISBN号输入框
        JTextField jTextField=new JTextField();
        Dimension dimension=new Dimension(375,30);
        jTextField.setSize(dimension);
        jTextField.setBounds(175,70,375,30);
        jLayeredPane.add(jTextField);

        //书名标签
        JLabel jLabel1=new JLabel("书名");
        jLabel1.setFont(font);
        jLabel1.setBounds(100,110,50,50);
        jLayeredPane.add(jLabel1);

        //书名输入框
        JTextField jTextField1=new JTextField();
        Dimension dimension1=new Dimension(400,30);
        jTextField1.setSize(dimension1);
        jTextField1.setBounds(150,120,400,30);
        jLayeredPane.add(jTextField1);

        //作者标签
        JLabel jLabel2=new JLabel("作者");
        jLabel2.setFont(font);
        jLabel2.setBounds(100,160,50,50);
        jLayeredPane.add(jLabel2);

        //作者输入框
        JTextField jTextField2=new JTextField();
        jTextField2.setSize(dimension1);
        jTextField2.setBounds(150,170,400,30);
        jLayeredPane.add(jTextField2);

        //价格标签
        JLabel jLabel3=new JLabel("价格");
        jLabel3.setFont(font);
        jLabel3.setBounds(100,210,50,50);
        jLayeredPane.add(jLabel3);

        //价格输入框
        JTextField jTextField3=new JTextField();
        jTextField3.setSize(dimension1);
        jTextField3.setBounds(150,220,400,30);
        jLayeredPane.add(jTextField3);

        //类别标签
        JLabel jLabel4=new JLabel("类别");
        jLabel4.setFont(font);
        jLabel4.setBounds(100,260,50,50);
        jLayeredPane.add(jLabel4);

        //类别输入框
        JTextField jTextField4=new JTextField();
        jTextField4.setSize(dimension1);
        jTextField4.setBounds(150,270,400,30);
        jLayeredPane.add(jTextField4);

        //出版社标签
        JLabel jLabel5=new JLabel("出版社");
        jLabel5.setFont(font);
        jLabel5.setBounds(100,310,75,50);
        jLayeredPane.add(jLabel5);

        //出版社输入框
        JTextField jTextField5=new JTextField();
        jTextField5.setSize(dimension);
        jTextField5.setBounds(175,320,375,30);
        jLayeredPane.add(jTextField5);

        //馆藏数量标签
        JLabel jLabel6=new JLabel("馆藏数量");
        jLabel6.setFont(font);
        jLabel6.setBounds(100,360,100,50);
        jLayeredPane.add(jLabel6);

        //馆藏数量输入框
        JTextField jTextField6=new JTextField();
        Dimension dimension2=new Dimension(350,30);
        jTextField6.setSize(dimension2);
        jTextField6.setBounds(200,370,350,30);
        jLayeredPane.add(jTextField6);

        //限制馆藏数量输入框只能输入数字
        jTextField6.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                int keyChar=e.getKeyChar();
                if (keyChar<KeyEvent.VK_0||keyChar>KeyEvent.VK_9) e.consume();
            }
        });

        //添加按钮
        JButton jButton=new JButton("添加");
        Dimension dimension3=new Dimension(100,50);
        jButton.setPreferredSize(dimension3);
        jButton.setBounds(275,465,100,50);
        jLayeredPane.add(jButton);

        //边栏事件
        jTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jTextField.setText("");
                jTextField1.setText("");
                jTextField2.setText("");
                jTextField3.setText("");
                jTextField4.setText("");
                jTextField5.setText("");
                jTextField6.setText("");
            }
        });

        //添加按钮事件
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    String ISBN=jTextField.getText().trim();
                    String bookName=jTextField1.getText().trim();
                    String author=jTextField2.getText().trim();
                    double price=Double.parseDouble(jTextField3.getText().trim());
                    String type=jTextField4.getText().trim();
                    String publish=jTextField5.getText().trim();
                    if (jTextField6.getText().trim().equals("")) JOptionPane.showMessageDialog(null, "馆藏数量输入框不能为空","提示" , JOptionPane.PLAIN_MESSAGE);
                    else
                    {
                        int inNum=Integer.parseInt(jTextField6.getText().trim());
                        if (ISBN.equals("")) JOptionPane.showMessageDialog(null, "ISBN号输入框不能为空","提示" , JOptionPane.PLAIN_MESSAGE);
                        else if (bookName.equals("")) JOptionPane.showMessageDialog(null, "书名输入框不能为空","提示" , JOptionPane.PLAIN_MESSAGE);
                        else if (author.equals("")) JOptionPane.showMessageDialog(null, "作者输入框不能为空","提示" , JOptionPane.PLAIN_MESSAGE);
                        else if (type.equals("")) JOptionPane.showMessageDialog(null, "类型输入框不能为空","提示" , JOptionPane.PLAIN_MESSAGE);
                        else if (publish.equals("")) JOptionPane.showMessageDialog(null, "出版社输入框不能为空","提示" , JOptionPane.PLAIN_MESSAGE);
                        else
                        {
                            int flag=common.addBook(ISBN,bookName,author,price,type,publish,inNum,0);
                            switch (flag)
                            {
                                case 0:JOptionPane.showMessageDialog(null, "ISBN号已存在","提示" , JOptionPane.PLAIN_MESSAGE);break;
                                case 1:JOptionPane.showMessageDialog(null, "价格不能为负数","提示" , JOptionPane.PLAIN_MESSAGE);break;
                                case 2:JOptionPane.showMessageDialog(null, "馆藏数量不能为负数","提示" , JOptionPane.PLAIN_MESSAGE);break;
                                case 3:JOptionPane.showMessageDialog(null, "已借数量不能为负数","提示" , JOptionPane.PLAIN_MESSAGE);break;
                                case 4:JOptionPane.showMessageDialog(null, "馆藏数量不能小于已借数量","提示" , JOptionPane.PLAIN_MESSAGE);break;
                                case 5:
                                    JOptionPane.showMessageDialog(null, "修改成功","提示" , JOptionPane.PLAIN_MESSAGE);
                                    jTextField.setText("");
                                    jTextField1.setText("");
                                    jTextField2.setText("");
                                    jTextField3.setText("");
                                    jTextField4.setText("");
                                    jTextField5.setText("");
                                    jTextField6.setText("");
                                    break;
                            }
                        }
                    }
                }catch (Exception e1)
                {
                    JOptionPane.showMessageDialog(null, "价格应为小数或整数","提示" , JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
    }
}
