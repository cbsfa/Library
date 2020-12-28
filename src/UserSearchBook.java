import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Vector;

public class UserSearchBook
{
    JLayeredPane jLayeredPane=new JLayeredPane();
    DefaultTableModel defaultTableModel;
    String select="书名查询";
    Book[] books;
    UserSearchBook(String account,JTabbedPane jTabbedPane)
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
        JButton jButton1=new JButton("借阅");
        jButton1.setPreferredSize(dimension2);
        jButton1.setBounds(275,465,100,50);
        jLayeredPane.add(jButton1);

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

        //借阅按钮事件
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=jTable.getSelectedRow();
                int flag=common.borrowBook(books[row],account);
                switch (flag)
                {
                    case 0:JOptionPane.showMessageDialog(null, "借阅失败，图书剩余数量为0","提示" , JOptionPane.PLAIN_MESSAGE);break;
                    case 1:
                        try {
                            setDefaultTableModel();
                            JOptionPane.showMessageDialog(null, "借阅成功","提示" , JOptionPane.PLAIN_MESSAGE);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        break;
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