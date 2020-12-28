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

        //���
        defaultTableModel=new DefaultTableModel();
        defaultTableModel.addColumn("ISBN��",new Vector<String>());
        defaultTableModel.addColumn("����",new Vector<String>());
        defaultTableModel.addColumn("����ʱ��",new Vector<String>());
        defaultTableModel.addColumn("�黹ʱ��",new Vector<String>());
        defaultTableModel.addColumn("����״̬",new Vector<String>());
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

        //���鰴ť
        JButton jButton=new JButton("����");
        Dimension dimension=new Dimension(100,50);
        jButton.setPreferredSize(dimension);
        jButton.setBounds(275,465,100,50);
        jLayeredPane.add(jButton);

        //�����¼�
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

        //���鰴ť�¼�
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=jTable.getSelectedRow();
                int flag=common.returnBook(reports[row],account);
                switch (flag)
                {
                    case 0:JOptionPane.showMessageDialog(null, "����ʧ�ܣ������ѱ��黹","��ʾ" , JOptionPane.PLAIN_MESSAGE);break;
                    case 1:
                        try {
                            setDefaultTableModel();
                            JOptionPane.showMessageDialog(null, "����ɹ�","��ʾ" , JOptionPane.PLAIN_MESSAGE);
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
            if (report.borrowState) temp="δ�黹";
            else temp="�ѹ黹";
            defaultTableModel.addRow(new Vector<>(Arrays.asList(report.ISBN, book.bookName, report.borrowTime, report.returnTime, temp)));
        }
    }
}
