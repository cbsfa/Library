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

        //ISBN�ű�ǩ
        JLabel jLabel=new JLabel("ISBN��");
        Font font=new Font("",Font.BOLD,20);
        jLabel.setFont(font);
        jLabel.setBounds(100,60,75,50);
        jLayeredPane.add(jLabel);

        //ISBN�������
        JTextField jTextField=new JTextField();
        Dimension dimension=new Dimension(375,30);
        jTextField.setSize(dimension);
        jTextField.setBounds(175,70,375,30);
        jLayeredPane.add(jTextField);

        //������ǩ
        JLabel jLabel1=new JLabel("����");
        jLabel1.setFont(font);
        jLabel1.setBounds(100,110,50,50);
        jLayeredPane.add(jLabel1);

        //���������
        JTextField jTextField1=new JTextField();
        Dimension dimension1=new Dimension(400,30);
        jTextField1.setSize(dimension1);
        jTextField1.setBounds(150,120,400,30);
        jLayeredPane.add(jTextField1);

        //���߱�ǩ
        JLabel jLabel2=new JLabel("����");
        jLabel2.setFont(font);
        jLabel2.setBounds(100,160,50,50);
        jLayeredPane.add(jLabel2);

        //���������
        JTextField jTextField2=new JTextField();
        jTextField2.setSize(dimension1);
        jTextField2.setBounds(150,170,400,30);
        jLayeredPane.add(jTextField2);

        //�۸��ǩ
        JLabel jLabel3=new JLabel("�۸�");
        jLabel3.setFont(font);
        jLabel3.setBounds(100,210,50,50);
        jLayeredPane.add(jLabel3);

        //�۸������
        JTextField jTextField3=new JTextField();
        jTextField3.setSize(dimension1);
        jTextField3.setBounds(150,220,400,30);
        jLayeredPane.add(jTextField3);

        //����ǩ
        JLabel jLabel4=new JLabel("���");
        jLabel4.setFont(font);
        jLabel4.setBounds(100,260,50,50);
        jLayeredPane.add(jLabel4);

        //��������
        JTextField jTextField4=new JTextField();
        jTextField4.setSize(dimension1);
        jTextField4.setBounds(150,270,400,30);
        jLayeredPane.add(jTextField4);

        //�������ǩ
        JLabel jLabel5=new JLabel("������");
        jLabel5.setFont(font);
        jLabel5.setBounds(100,310,75,50);
        jLayeredPane.add(jLabel5);

        //�����������
        JTextField jTextField5=new JTextField();
        jTextField5.setSize(dimension);
        jTextField5.setBounds(175,320,375,30);
        jLayeredPane.add(jTextField5);

        //�ݲ�������ǩ
        JLabel jLabel6=new JLabel("�ݲ�����");
        jLabel6.setFont(font);
        jLabel6.setBounds(100,360,100,50);
        jLayeredPane.add(jLabel6);

        //�ݲ����������
        JTextField jTextField6=new JTextField();
        Dimension dimension2=new Dimension(350,30);
        jTextField6.setSize(dimension2);
        jTextField6.setBounds(200,370,350,30);
        jLayeredPane.add(jTextField6);

        //���ƹݲ����������ֻ����������
        jTextField6.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                int keyChar=e.getKeyChar();
                if (keyChar<KeyEvent.VK_0||keyChar>KeyEvent.VK_9) e.consume();
            }
        });

        //��Ӱ�ť
        JButton jButton=new JButton("���");
        Dimension dimension3=new Dimension(100,50);
        jButton.setPreferredSize(dimension3);
        jButton.setBounds(275,465,100,50);
        jLayeredPane.add(jButton);

        //�����¼�
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

        //��Ӱ�ť�¼�
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
                    if (jTextField6.getText().trim().equals("")) JOptionPane.showMessageDialog(null, "�ݲ������������Ϊ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                    else
                    {
                        int inNum=Integer.parseInt(jTextField6.getText().trim());
                        if (ISBN.equals("")) JOptionPane.showMessageDialog(null, "ISBN���������Ϊ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                        else if (bookName.equals("")) JOptionPane.showMessageDialog(null, "�����������Ϊ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                        else if (author.equals("")) JOptionPane.showMessageDialog(null, "�����������Ϊ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                        else if (type.equals("")) JOptionPane.showMessageDialog(null, "�����������Ϊ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                        else if (publish.equals("")) JOptionPane.showMessageDialog(null, "�������������Ϊ��","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                        else
                        {
                            int flag=common.addBook(ISBN,bookName,author,price,type,publish,inNum,0);
                            switch (flag)
                            {
                                case 0:JOptionPane.showMessageDialog(null, "ISBN���Ѵ���","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                case 1:JOptionPane.showMessageDialog(null, "�۸���Ϊ����","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                case 2:JOptionPane.showMessageDialog(null, "�ݲ���������Ϊ����","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                case 3:JOptionPane.showMessageDialog(null, "�ѽ���������Ϊ����","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                case 4:JOptionPane.showMessageDialog(null, "�ݲ���������С���ѽ�����","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                case 5:
                                    JOptionPane.showMessageDialog(null, "�޸ĳɹ�","��ʾ" , JOptionPane.PLAIN_MESSAGE);
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
                    JOptionPane.showMessageDialog(null, "�۸�ӦΪС��������","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
    }
}
