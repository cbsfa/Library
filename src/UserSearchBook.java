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
    String select="������ѯ";
    Book[] books;
    UserSearchBook(String account,JTabbedPane jTabbedPane)
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
        JButton jButton1=new JButton("����");
        jButton1.setPreferredSize(dimension2);
        jButton1.setBounds(275,465,100,50);
        jLayeredPane.add(jButton1);

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

        //���İ�ť�¼�
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=jTable.getSelectedRow();
                int flag=common.borrowBook(books[row],account);
                switch (flag)
                {
                    case 0:JOptionPane.showMessageDialog(null, "����ʧ�ܣ�ͼ��ʣ������Ϊ0","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                    case 1:
                        try {
                            setDefaultTableModel();
                            JOptionPane.showMessageDialog(null, "���ĳɹ�","��ʾ" , JOptionPane.PLAIN_MESSAGE);
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