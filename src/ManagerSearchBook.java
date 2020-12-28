import com.sun.org.apache.bcel.internal.generic.FADD;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Vector;

public class ManagerSearchBook
{
    JLayeredPane jLayeredPane=new JLayeredPane();
    DefaultTableModel defaultTableModel;
    String select="������ѯ";
    Book[] books;
    ManagerSearchBook(JTabbedPane jTabbedPane)
    {
        Common common=new Common();

        //��ѯ������
        JComboBox<String> jComboBox=new JComboBox<>();
        Dimension dimension=new Dimension(150,50);
        jComboBox.setSize(dimension);
        jComboBox.addItem("������ѯ");
        jComboBox.addItem("���߲�ѯ");
        jComboBox.addItem("ISBN�Ų�ѯ");
        jComboBox.setBounds(50,50,150,50);
        jLayeredPane.add(jComboBox);

        //��ѯ�����
        JTextField jTextField=new JTextField();
        Dimension dimension1=new Dimension(300,50);
        jTextField.setSize(dimension1);
        jTextField.setBounds(200,50,300,50);
        jLayeredPane.add(jTextField);

        //��ѯ��ť
        JButton jButton=new JButton("��ѯ");
        Dimension dimension2=new Dimension(100,50);
        jButton.setPreferredSize(dimension2);
        jButton.setBounds(500,50,100,50);
        jLayeredPane.add(jButton);

        //���
        defaultTableModel=new DefaultTableModel();
        defaultTableModel.addColumn("ISBN��",new Vector<String>());
        defaultTableModel.addColumn("����",new Vector<String>());
        defaultTableModel.addColumn("����",new Vector<String>());
        defaultTableModel.addColumn("�۸�",new Vector<Double>());
        defaultTableModel.addColumn("���",new Vector<String>());
        defaultTableModel.addColumn("������",new Vector<String>());
        defaultTableModel.addColumn("�ݲ�����",new Vector<Integer>());
        defaultTableModel.addColumn("�ѽ�����",new Vector<Integer>());
        DefaultTableCellRenderer defaultTableCellRenderer=new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        JTable jTable=new JTable(defaultTableModel)
        {
            public boolean isCellEditable(int row,int column)
            {
                return false;
            }
        };
        JTableHeader jTableHeader=jTable.getTableHeader();
        jTableHeader.setPreferredSize(new Dimension(jTableHeader.getWidth(),35));
        jTableHeader.setFont(new Font("",Font.BOLD,15));
        jTable.setRowHeight(30);
        jTable.setFont(new Font("",Font.BOLD,13));
        jTable.setDefaultRenderer(Object.class,defaultTableCellRenderer);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane jScrollPane=new JScrollPane(jTable);
        jScrollPane.setBounds(50,100,550,350);
        jLayeredPane.add(jScrollPane);
        try
        {
            books=common.getAllBook();
            setDefaultTableModel();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        //���İ�ť
        JButton jButton1=new JButton("�޸�");
        jButton1.setPreferredSize(dimension2);
        jButton1.setBounds(175,465,100,50);
        jLayeredPane.add(jButton1);

        //�¼ܰ�ť
        JButton jButton2=new JButton("�¼�");
        jButton2.setPreferredSize(dimension2);
        jButton2.setBounds(375,465,100,50);
        jLayeredPane.add(jButton2);

        //�����¼�
        jTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jComboBox.setSelectedIndex(0);
                select="������ѯ";
                books=common.getAllBook();
                try {
                    setDefaultTableModel();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                jTextField.setText("");
            }
        });

        //��ѯ�������¼�
        jComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange()==ItemEvent.SELECTED) select=(String)e.getItem();
            }
        });

        //��ѯ��ť�¼�
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (select) {
                    case "������ѯ":
                        String bookName = jTextField.getText().trim();
                        try {
                            books = common.bookNameSearch(bookName);
                            setDefaultTableModel();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case "���߲�ѯ":
                        String author = jTextField.getText().trim();
                        try {
                            books = common.authorSearch(author);
                            setDefaultTableModel();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case "ISBN�Ų�ѯ":
                        String ISBN = jTextField.getText().trim();
                        try {
                            Book book=common.ISBNSearch(ISBN);
                            books=new Book[1];
                            books[0]=book;
                            setDefaultTableModel();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        break;
                }
            }
        });

        //�޸İ�ť�¼�
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //�½�����
                JFrame jFrame=new JFrame("�޸�ͼ��");
                jFrame.setSize(300,500);
                jFrame.setLocationRelativeTo(null);
                jFrame.setLayout(null);
                jFrame.setResizable(false);

                //��ȡѡ����
                int row=jTable.getSelectedRow();

                //ISBN�ű�ǩ
                JLabel jLabel=new JLabel("ISBN��");
                Font font=new Font("",Font.BOLD,20);
                jLabel.setFont(font);
                JPanel jPanel=new JPanel();
                jPanel.add(jLabel);
                jPanel.setBounds(10,10,75,50);
                jFrame.add(jPanel);
                jPanel.setOpaque(false);

                //ISBN�������
                JTextField jTextField=new JTextField(books[row].ISBN);
                Dimension dimension3=new Dimension(175,30);
                jTextField.setPreferredSize(dimension3);
                JPanel jPanel1=new JPanel();
                jPanel1.add(jTextField);
                jPanel1.setBounds(85,10,175,30);
                jFrame.add(jPanel1);
                jPanel1.setOpaque(false);

                //������ǩ
                JLabel jLabel1=new JLabel("����");
                jLabel1.setFont(font);
                JPanel jPanel2=new JPanel();
                jPanel2.add(jLabel1);
                jPanel2.setBounds(10,60,50,50);
                jFrame.add(jPanel2);
                jPanel2.setOpaque(false);

                //���������
                JTextField jTextField1=new JTextField(books[row].bookName);
                Dimension dimension4=new Dimension(200,30);
                jTextField1.setPreferredSize(dimension4);
                JPanel jPanel3=new JPanel();
                jPanel3.add(jTextField1);
                jPanel3.setBounds(60,60,200,30);
                jFrame.add(jPanel3);
                jPanel3.setOpaque(false);

                //���߱�ǩ
                JLabel jLabel2=new JLabel("����");
                jLabel2.setFont(font);
                JPanel jPanel4=new JPanel();
                jPanel4.add(jLabel2);
                jPanel4.setBounds(10,110,50,50);
                jFrame.add(jPanel4);
                jPanel4.setOpaque(false);

                //���������
                JTextField jTextField2=new JTextField(books[row].author);
                jTextField2.setPreferredSize(dimension4);
                JPanel jPanel5=new JPanel();
                jPanel5.add(jTextField2);
                jPanel5.setBounds(60,110,200,30);
                jFrame.add(jPanel5);
                jPanel5.setOpaque(false);

                //�۸��ǩ
                JLabel jLabel3=new JLabel("�۸�");
                jLabel3.setFont(font);
                JPanel jPanel6=new JPanel();
                jPanel6.add(jLabel3);
                jPanel6.setBounds(10,160,50,50);
                jFrame.add(jPanel6);
                jPanel6.setOpaque(false);

                //�۸������
                JTextField jTextField3=new JTextField(String.valueOf(books[row].price));
                jTextField3.setPreferredSize(dimension4);
                JPanel jPanel7=new JPanel();
                jPanel7.add(jTextField3);
                jPanel7.setBounds(60,160,200,30);
                jFrame.add(jPanel7);
                jPanel7.setOpaque(false);

                //����ǩ
                JLabel jLabel4=new JLabel("���");
                jLabel4.setFont(font);
                JPanel jPanel8=new JPanel();
                jPanel8.add(jLabel4);
                jPanel8.setBounds(10,210,50,50);
                jFrame.add(jPanel8);
                jPanel8.setOpaque(false);

                //��������
                JTextField jTextField4=new JTextField(books[row].type);
                jTextField4.setPreferredSize(dimension4);
                JPanel jPanel9=new JPanel();
                jPanel9.add(jTextField4);
                jPanel9.setBounds(60,210,200,30);
                jFrame.add(jPanel9);
                jPanel9.setOpaque(false);

                //�������ǩ
                JLabel jLabel5=new JLabel("������");
                jLabel5.setFont(font);
                JPanel jPanel10=new JPanel();
                jPanel10.add(jLabel5);
                jPanel10.setBounds(10,260,75,50);
                jFrame.add(jPanel10);
                jPanel10.setOpaque(false);

                //�����������
                JTextField jTextField5=new JTextField(books[row].publish);
                jTextField5.setPreferredSize(dimension3);
                JPanel jPanel11=new JPanel();
                jPanel11.add(jTextField5);
                jPanel11.setBounds(85,260,175,30);
                jFrame.add(jPanel11);
                jPanel11.setOpaque(false);

                //�ݲ�����
                JLabel jLabel6=new JLabel("�ݲ�����");
                jLabel6.setFont(font);
                JPanel jPanel12=new JPanel();
                jPanel12.add(jLabel6);
                jPanel12.setBounds(10,310,100,50);
                jFrame.add(jPanel12);
                jPanel12.setOpaque(false);

                //�ݲ����������
                JTextField jTextField6=new JTextField(String.valueOf(books[row].inNum));
                Dimension dimension5=new Dimension(150,30);
                jTextField6.setPreferredSize(dimension5);
                JPanel jPanel13=new JPanel();
                jPanel13.add(jTextField6);
                jPanel13.setBounds(110,310,150,30);
                jFrame.add(jPanel13);
                jPanel13.setOpaque(false);

                //���ƹݲ����������ֻ����������
                jTextField6.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        super.keyTyped(e);
                        int keyChar=e.getKeyChar();
                        if (keyChar<KeyEvent.VK_0||keyChar>KeyEvent.VK_9) e.consume();
                    }
                });

                //�ѽ�����
                JLabel jLabel7=new JLabel("�ѽ�����");
                jLabel7.setFont(font);
                JPanel jPanel14=new JPanel();
                jPanel14.add(jLabel7);
                jPanel14.setBounds(10,360,100,50);
                jFrame.add(jPanel14);
                jPanel14.setOpaque(false);

                //�ѽ����������
                JTextField jTextField7=new JTextField(String.valueOf(books[row].outNum));
                jTextField7.setPreferredSize(dimension5);
                jTextField7.setEditable(false);
                JPanel jPanel15=new JPanel();
                jPanel15.add(jTextField7);
                jPanel15.setBounds(110,360,150,30);
                jFrame.add(jPanel15);
                jPanel15.setOpaque(false);

                //ȷ�ϰ�ť
                JButton jButton3=new JButton("ȷ��");
                Dimension dimension6=new Dimension(100,50);
                jButton3.setPreferredSize(dimension6);
                JPanel jPanel16=new JPanel();
                jPanel16.add(jButton3);
                jPanel16.setBounds(100,400,100,50);
                jFrame.add(jPanel16);
                jPanel16.setOpaque(false);

                //ȷ�ϰ�ť�¼�
                jButton3.addActionListener(new ActionListener() {
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
                                    int flag=common.modifyBook(ISBN,bookName,author,price,type,publish,inNum,books[row]);
                                    switch (flag)
                                    {
                                        case 0:JOptionPane.showMessageDialog(null, "ISBN���Ѵ���","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                        case 1:JOptionPane.showMessageDialog(null, "�۸���Ϊ����","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                        case 2:JOptionPane.showMessageDialog(null, "�ݲ���������Ϊ����","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                        case 3:JOptionPane.showMessageDialog(null, "��ǰ�ѽ��������ܴ�������Ĺݲ�����","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                                        case 4:
                                            JOptionPane.showMessageDialog(null, "�޸ĳɹ�","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                                            try {
                                                setDefaultTableModel();
                                            } catch (Exception e1) {
                                                e1.printStackTrace();
                                            }
                                            jFrame.dispose();
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

                //�򿪴���
                jFrame.setVisible(true);
            }
        });

        //�¼ܰ�ť�¼�
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=jTable.getSelectedRow();
                if (row>=0)
                {
                    int temp=JOptionPane.showConfirmDialog(null,"�Ƿ�Ҫ�¼�ѡ�е�ͼ�飿","��ʾ",JOptionPane.YES_NO_OPTION);
                    if (temp==JOptionPane.YES_OPTION)
                    {
                        int flag=common.deleteBook(row,books);
                        switch (flag)
                        {
                            case 0:JOptionPane.showMessageDialog(null, "��ͼ���ѽ�������Ϊ0���޷��¼�","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                            case 1:
                                JOptionPane.showMessageDialog(null, "�¼ܳɹ�","��ʾ" , JOptionPane.PLAIN_MESSAGE);
                                try {
                                    setDefaultTableModel();
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }
                                break;
                        }
                    }
                }
            }
        });
    }
    void setDefaultTableModel() throws Exception
    {
        defaultTableModel.setRowCount(0);
        for (Book book : books) defaultTableModel.addRow(new Vector<>(Arrays.asList(book.ISBN, book.bookName, book.author, book.price, book.type, book.publish, book.inNum, book.outNum)));
    }
}
