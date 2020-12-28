import java.sql.*;

public class DataBase {

    public static  Connection conn=null;
    public static PreparedStatement ps=null;
    public static ResultSet rs=null;//��ѯ�����Ľ����

    private DataBase(){}

    //���ݿ�����
    public static void connection (){
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=ͼ���";
        String user="sa";
        String password="";

        try{
            //1.ע������
            Class.forName(driver);
            //2.��ȡ����
            conn=DriverManager.getConnection(url,user,password);
            //ȡ��sql�Զ��ύ
            conn.setAutoCommit(false);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //ִ��sql�Ĳ������������ھ�̬��rs��
    public static void sqlQuery(String sqlStr){

        try {
            ps=conn.prepareStatement(sqlStr,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //4.ִ��sql
            rs=ps.executeQuery();
            conn.commit();
        }catch(SQLException e){
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            e.printStackTrace();
        }
    }

    //ִ��sql����ɾ�Ĳ���,�����ܵ�Ӱ����е�����
    public static int sqlUpdate(String sqlStr){
        try {
            ps=conn.prepareStatement(sqlStr,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //4.ִ��sql
            int count=ps.executeUpdate();
            conn.commit();
            return count;
        }catch(SQLException e){
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            e.printStackTrace();
            return -1;
        }
    }

    //��Դ�ر�
    public  static void close(){
        if(rs!=null){
            try{
                rs.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        if(ps!=null){
            try{
                ps.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }

        if(conn!=null){
            try{
                conn.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
