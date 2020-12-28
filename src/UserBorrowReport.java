import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Vector;

public class UserBorrowReport
{
    JLayeredPane jLayeredPane=new JLayeredPane();
    DefaultTableModel defaultTableModel;
    Report[] reports;
    UserBorrowReport(String account,JTabbedPane jTabbedPane)
    {
        Common common=new Common();

        //表格
        defaultTableModel=new DefaultTableModel();
        defaultTableModel.addColumn("ISBN号",new Vector<String>());
        defaultTableModel.addColumn("书名",new Vector<String>());
        defaultTableModel.addColumn("借阅时间",new Vector<String>());
        defaultTableModel.addColumn("归还时间",new Vector<String>());
        defaultTableModel.addColumn("借阅状态",new Vector<String>());
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
            reports=common.getReport(account);
            setDefaultTableModel();
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        //还书按钮
        JButton jButton=new JButton("还书");
        Dimension dimension=new Dimension(100,50);
        jButton.setPreferredSize(dimension);
        jButton.setBounds(275,465,100,50);
        jLayeredPane.add(jButton);

        //边栏事件
        jTabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                reports=common.getReport(account);
                try {
                    setDefaultTableModel();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        //还书按钮事件
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=jTable.getSelectedRow();
                int flag=common.returnBook(reports[row],account);
                switch (flag)
                {
                    case 0:JOptionPane.showMessageDialog(null, "还书失败，该书已被归还","提示" , JOptionPane.PLAIN_MESSAGE);break;
                    case 1:
                        try {
                            setDefaultTableModel();
                            JOptionPane.showMessageDialog(null, "还书成功","提示" , JOptionPane.PLAIN_MESSAGE);
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
        Common common=new Common();
        defaultTableModel.setRowCount(0);
        for (Report report : reports)
        {
            Book book= common.ISBNSearch(report.ISBN);
            String temp;
            if (report.borrowState) temp="未归还";
            else temp="已归还";
            defaultTableModel.addRow(new Vector<>(Arrays.asList(report.ISBN, book.bookName, report.borrowTime, report.returnTime, temp)));
        }
    }
}
