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
    String select="书名查询";
    Book[] books;
    ManagerSearchBook(JTabbedPane jTabbedPane)
    {
        Common common=new Common();

        //查询下拉框
        JComboBox<String> jComboBox=new JComboBox<>();
        Dimension dimension=new Dimension(150,50);
        jComboBox.setSize(dimension);
        jComboBox.addItem("书名查询");
        jComboBox.addItem("作者查询");
        jComboBox.addItem("ISBN号查询");
        jComboBox.setBounds(50,50,150,50);
        jLayeredPane.add(jComboBox);

        //查询输入框
        JTextField jTextField=new JTextField();
        Dimension dimension1=new Dimension(300,50);
        jTextField.setSize(dimension1);
        jTextField.setBounds(200,50,300,50);
        jLayeredPane.add(jTextField);

        //查询按钮
        JButton jButton=new JButton("查询");
        Dimension dimension2=new Dimension(100,50);
        jButton.setPreferredSize(dimension2);
        jButton.setBounds(500,50,100,50);
        jLayeredPane.add(jButton);

        //表格
        defaultTableModel=new DefaultTableModel();
        defaultTableModel.addColumn("ISBN号",new Vector<String>());
        defaultTableModel.addColumn("书名",new Vector<String>());
        defaultTableModel.addColumn("作者",new Vector<String>());
        defaultTableModel.addColumn("价格",new Vector<Double>());
        defaultTableModel.addColumn("类别",new Vector<String>());
        defaultTableModel.addColumn("出版社",new Vector<String>());
        defaultTableModel.addColumn("馆藏数量",new Vector<Integer>());
        defaultTableModel.addColumn("已借数量",new Vector<Integer>());
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

        //借阅按钮
        JButton jButton1=new JButton("修改");
        jButton1.setPreferredSize(dimension2);
        jButton1.setBounds(175,465,100,50);
        jLayeredPane.add(jButton1);

        //下架按钮
        JButton jButton2=new JButton("下架");
        jButton2.setPreferredSize(dimension2);
        jButton2.setBounds(375,465,100,50);
        jLayeredPane.add(jButton2);

        //边栏事件
        jTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jComboBox.setSelectedIndex(0);
                select="书名查询";
                books=common.getAllBook();
                try {
                    setDefaultTableModel();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                jTextField.setText("");
            }
        });

        //查询下拉框事件
        jComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange()==ItemEvent.SELECTED) select=(String)e.getItem();
            }
        });

        //查询按钮事件
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (select) {
                    case "书名查询":
                        String bookName = jTextField.getText().trim();
                        try {
                            books = common.bookNameSearch(bookName);
                            setDefaultTableModel();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case "作者查询":
                        String author = jTextField.getText().trim();
                        try {
                            books = common.authorSearch(author);
                            setDefaultTableModel();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case "ISBN号查询":
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

        //修改按钮事件
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //新建窗口
                JFrame jFrame=new JFrame("修改图书");
                jFrame.setSize(300,500);
                jFrame.setLocationRelativeTo(null);
                jFrame.setLayout(null);
                jFrame.setResizable(false);

                //获取选中行
                int row=jTable.getSelectedRow();

                //ISBN号标签
                JLabel jLabel=new JLabel("ISBN号");
                Font font=new Font("",Font.BOLD,20);
                jLabel.setFont(font);
                JPanel jPanel=new JPanel();
                jPanel.add(jLabel);
                jPanel.setBounds(10,10,75,50);
                jFrame.add(jPanel);
                jPanel.setOpaque(false);

                //ISBN号输入框
                JTextField jTextField=new JTextField(books[row].ISBN);
                Dimension dimension3=new Dimension(175,30);
                jTextField.setPreferredSize(dimension3);
                JPanel jPanel1=new JPanel();
                jPanel1.add(jTextField);
                jPanel1.setBounds(85,10,175,30);
                jFrame.add(jPanel1);
                jPanel1.setOpaque(false);

                //书名标签
                JLabel jLabel1=new JLabel("书名");
                jLabel1.setFont(font);
                JPanel jPanel2=new JPanel();
                jPanel2.add(jLabel1);
                jPanel2.setBounds(10,60,50,50);
                jFrame.add(jPanel2);
                jPanel2.setOpaque(false);

                //书名输入框
                JTextField jTextField1=new JTextField(books[row].bookName);
                Dimension dimension4=new Dimension(200,30);
                jTextField1.setPreferredSize(dimension4);
                JPanel jPanel3=new JPanel();
                jPanel3.add(jTextField1);
                jPanel3.setBounds(60,60,200,30);
                jFrame.add(jPanel3);
                jPanel3.setOpaque(false);

                //作者标签
                JLabel jLabel2=new JLabel("作者");
                jLabel2.setFont(font);
                JPanel jPanel4=new JPanel();
                jPanel4.add(jLabel2);
                jPanel4.setBounds(10,110,50,50);
                jFrame.add(jPanel4);
                jPanel4.setOpaque(false);

                //作者输入框
                JTextField jTextField2=new JTextField(books[row].author);
                jTextField2.setPreferredSize(dimension4);
                JPanel jPanel5=new JPanel();
                jPanel5.add(jTextField2);
                jPanel5.setBounds(60,110,200,30);
                jFrame.add(jPanel5);
                jPanel5.setOpaque(false);

                //价格标签
                JLabel jLabel3=new JLabel("价格");
                jLabel3.setFont(font);
                JPanel jPanel6=new JPanel();
                jPanel6.add(jLabel3);
                jPanel6.setBounds(10,160,50,50);
                jFrame.add(jPanel6);
                jPanel6.setOpaque(false);

                //价格输入框
                JTextField jTextField3=new JTextField(String.valueOf(books[row].price));
                jTextField3.setPreferredSize(dimension4);
                JPanel jPanel7=new JPanel();
                jPanel7.add(jTextField3);
                jPanel7.setBounds(60,160,200,30);
                jFrame.add(jPanel7);
                jPanel7.setOpaque(false);

                //类别标签
                JLabel jLabel4=new JLabel("类别");
                jLabel4.setFont(font);
                JPanel jPanel8=new JPanel();
                jPanel8.add(jLabel4);
                jPanel8.setBounds(10,210,50,50);
                jFrame.add(jPanel8);
                jPanel8.setOpaque(false);

                //类别输入框
                JTextField jTextField4=new JTextField(books[row].type);
                jTextField4.setPreferredSize(dimension4);
                JPanel jPanel9=new JPanel();
                jPanel9.add(jTextField4);
                jPanel9.setBounds(60,210,200,30);
                jFrame.add(jPanel9);
                jPanel9.setOpaque(false);

                //出版社标签
                JLabel jLabel5=new JLabel("出版社");
                jLabel5.setFont(font);
                JPanel jPanel10=new JPanel();
                jPanel10.add(jLabel5);
                jPanel10.setBounds(10,260,75,50);
                jFrame.add(jPanel10);
                jPanel10.setOpaque(false);

                //出版社输入框
                JTextField jTextField5=new JTextField(books[row].publish);
                jTextField5.setPreferredSize(dimension3);
                JPanel jPanel11=new JPanel();
                jPanel11.add(jTextField5);
                jPanel11.setBounds(85,260,175,30);
                jFrame.add(jPanel11);
                jPanel11.setOpaque(false);

                //馆藏数量
                JLabel jLabel6=new JLabel("馆藏数量");
                jLabel6.setFont(font);
                JPanel jPanel12=new JPanel();
                jPanel12.add(jLabel6);
                jPanel12.setBounds(10,310,100,50);
                jFrame.add(jPanel12);
                jPanel12.setOpaque(false);

                //馆藏数量输入框
                JTextField jTextField6=new JTextField(String.valueOf(books[row].inNum));
                Dimension dimension5=new Dimension(150,30);
                jTextField6.setPreferredSize(dimension5);
                JPanel jPanel13=new JPanel();
                jPanel13.add(jTextField6);
                jPanel13.setBounds(110,310,150,30);
                jFrame.add(jPanel13);
                jPanel13.setOpaque(false);

                //限制馆藏数量输入框只能输入数字
                jTextField6.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        super.keyTyped(e);
                        int keyChar=e.getKeyChar();
                        if (keyChar<KeyEvent.VK_0||keyChar>KeyEvent.VK_9) e.consume();
                    }
                });

                //已借数量
                JLabel jLabel7=new JLabel("已借数量");
                jLabel7.setFont(font);
                JPanel jPanel14=new JPanel();
                jPanel14.add(jLabel7);
                jPanel14.setBounds(10,360,100,50);
                jFrame.add(jPanel14);
                jPanel14.setOpaque(false);

                //已借数量输入框
                JTextField jTextField7=new JTextField(String.valueOf(books[row].outNum));
                jTextField7.setPreferredSize(dimension5);
                jTextField7.setEditable(false);
                JPanel jPanel15=new JPanel();
                jPanel15.add(jTextField7);
                jPanel15.setBounds(110,360,150,30);
                jFrame.add(jPanel15);
                jPanel15.setOpaque(false);

                //确认按钮
                JButton jButton3=new JButton("确认");
                Dimension dimension6=new Dimension(100,50);
                jButton3.setPreferredSize(dimension6);
                JPanel jPanel16=new JPanel();
                jPanel16.add(jButton3);
                jPanel16.setBounds(100,400,100,50);
                jFrame.add(jPanel16);
                jPanel16.setOpaque(false);

                //确认按钮事件
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
                                    int flag=common.modifyBook(ISBN,bookName,author,price,type,publish,inNum,books[row]);
                                    switch (flag)
                                    {
                                        case 0:JOptionPane.showMessageDialog(null, "ISBN号已存在","提示" , JOptionPane.PLAIN_MESSAGE);break;
                                        case 1:JOptionPane.showMessageDialog(null, "价格不能为负数","提示" , JOptionPane.PLAIN_MESSAGE);break;
                                        case 2:JOptionPane.showMessageDialog(null, "馆藏数量不能为负数","提示" , JOptionPane.PLAIN_MESSAGE);break;
                                        case 3:JOptionPane.showMessageDialog(null, "当前已借数量不能大于输入的馆藏数量","提示" , JOptionPane.PLAIN_MESSAGE);break;
                                        case 4:
                                            JOptionPane.showMessageDialog(null, "修改成功","提示" , JOptionPane.PLAIN_MESSAGE);
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
                            JOptionPane.showMessageDialog(null, "价格应为小数或整数","提示" , JOptionPane.PLAIN_MESSAGE);
                        }
                    }
                });

                //打开窗口
                jFrame.setVisible(true);
            }
        });

        //下架按钮事件
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=jTable.getSelectedRow();
                if (row>=0)
                {
                    int temp=JOptionPane.showConfirmDialog(null,"是否要下架选中的图书？","提示",JOptionPane.YES_NO_OPTION);
                    if (temp==JOptionPane.YES_OPTION)
                    {
                        int flag=common.deleteBook(row,books);
                        switch (flag)
                        {
                            case 0:JOptionPane.showMessageDialog(null, "该图书已借数量不为0，无法下架","提示" , JOptionPane.PLAIN_MESSAGE);break;
                            case 1:
                                JOptionPane.showMessageDialog(null, "下架成功","提示" , JOptionPane.PLAIN_MESSAGE);
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
