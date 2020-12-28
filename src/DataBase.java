import java.sql.*;

public class DataBase {

    public static  Connection conn=null;
    public static PreparedStatement ps=null;
    public static ResultSet rs=null;//查询操作的结果集

    private DataBase(){}

    //数据库连接
    public static void connection (){
        String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String url="jdbc:sqlserver://127.0.0.1:1433;DatabaseName=图书馆";
        String user="sa";
        String password="";

        try{
            //1.注册驱动
            Class.forName(driver);
            //2.获取连接
            conn=DriverManager.getConnection(url,user,password);
            //取消sql自动提交
            conn.setAutoCommit(false);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    //执行sql的查操作，结果集在静态的rs中
    public static void sqlQuery(String sqlStr){

        try {
            ps=conn.prepareStatement(sqlStr,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //4.执行sql
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

    //执行sql的增删改操作,返回受到影响的行的数量
    public static int sqlUpdate(String sqlStr){
        try {
            ps=conn.prepareStatement(sqlStr,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //4.执行sql
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

    //资源关闭
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
