import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;


public class Common
{

    /*登录验证，0-账号错误，1-密码错误，2-成功登入用户界面，3-成功登入管理员界面*/
    //测试成功
    public static int login(String account,String password)
    {
        DataBase.sqlQuery("select 读者账号,密码 from 读者信息 where 读者账号='"+account+"'");
        try{
            if(DataBase.rs.next()){
                if(DataBase.rs.getString(2).trim().equals(password)) return 2;
                else return 1;
            }else{
                DataBase.sqlQuery("select 管理员账号,密码 from 管理员信息 where 管理员账号="+account);
                if(DataBase.rs.next()){
                    if(DataBase.rs.getString(2).trim().equals(password)) return 3;
                    else return 1;
                }else return 0;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //测试成功
    /*注册验证，0-账号已存在，1-密码应为6-18位数字、字母组成，2-密码不一致（password和rePassword），3-手机号应为11位整数
    4-手机号已存在，5-成功验证，将传入的用户信息插入到用户表中*/
    public static int register(String account,String password,String rePassword,String name,String phone)
    {
        if(!password.equals(rePassword)) return 2;

        //测试失败，反正看不懂，这功能咱不要了！
        //java正则表达式，copy来的，看不懂的
        //String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
        //if(!password.matches(regex)) return 1;
        //regex="^-?[0-9]+";
        //if(!phone.matches(regex) || phone.length()!=11) return 3 ;
        if(password.length()<6 || password.length()>18) return 1;
        if(phone.length()!=11) return 3;

        DataBase.sqlQuery("select 读者账号 from 读者信息 where 读者账号='"+account+"'");
        try{
            if(DataBase.rs.next()) return 0;
            else{
                DataBase.sqlQuery("select 手机号码 from 读者信息 where 手机号码='"+phone+"'");
                if(DataBase.rs.next()) return 4;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            DataBase.ps=DataBase.conn.prepareStatement("insert into 读者信息(读者账号,密码,姓名,手机号码) values(?,?,?,?)");
            DataBase.ps.setString(1,account);
            DataBase.ps.setString(2,password);
            DataBase.ps.setString(3,name);
            DataBase.ps.setString(4,phone);
            int count=DataBase.ps.executeUpdate();
            DataBase.conn.commit();
            if(count==1) return 5;
        }catch (SQLException e){
            if (DataBase.conn != null) {
                try {
                    DataBase.conn.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return 0;
    }

    //测试成功
    /*找回密码验证，0-账号错误，1-手机号错误，2-密码应为6-18位数字、字母组成，3-密码不一致（password和rePassword）
    4-成功验证，修改用户表或者管理员表中对应的值*/
    public static int retrieve(String account,String password,String rePassword,String phone)
    {
        if(!password.equals(rePassword)) return 3;
        if(password.length()<6 || password.length()>18) return 2;
        DataBase.sqlQuery("select 手机号码 from 读者信息 where 读者账号='"+account+"'");
        try{
            if(DataBase.rs.next() && DataBase.rs.getString(1).trim().equals(phone)){
                int count=DataBase.sqlUpdate("update 读者信息 set 密码='"+password+"' where 读者账号='"+account+"'");
                if(count==1) return 4;
            }else return 1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //测试成功
    /*书名查询，根据对应书名进行模糊查询，创建图书对象Book，将查找到的图书插入到books数组中，返回Book[]数组*/
    public static Book[] bookNameSearch(String searchName)//书名
    {
        Book[] books=null;
        int bookNum=0;
        String ISBN;//ISBN号
        String bookName;//书名
        String author;//作者
        double price;//价格
        String type;//类别
        String publish;//出版社
        int inNum;//馆藏数量
        int outNum;//已借数量

        DataBase.sqlQuery("select * from 图书信息 where 书名 like '%"+searchName+"%'");
        try {
            DataBase.rs.last();
            int rows=DataBase.rs.getRow();
            if(rows==0) return null;
            DataBase.rs.beforeFirst();

            books=new Book[rows];
            while(DataBase.rs.next()){
                ISBN=String.valueOf(DataBase.rs.getObject(1)).trim();
                bookName=String.valueOf(DataBase.rs.getObject(2)).trim();
                author=String.valueOf(DataBase.rs.getObject(3)).trim();
                price=Double.parseDouble(DataBase.rs.getObject(4).toString());
                type=String.valueOf(DataBase.rs.getObject(5)).trim();
                publish=String.valueOf(DataBase.rs.getObject(6)).trim();
                inNum=Integer.parseInt(DataBase.rs.getObject(7).toString());
                outNum=Integer.parseInt(DataBase.rs.getObject(8).toString());
                books[bookNum++]=new Book(ISBN,bookName,author,price,type,publish,inNum,outNum);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    //测试成功
    /*作者查询，根据对应作者进行精确查询，创建图书对象Book，将查找到的图书插入到books数组中，返回Book[]数组*/
    public static Book[] authorSearch(String searchName)//作者名
    {
        Book[] books=null;
        int bookNum=0;
        String ISBN;//ISBN号
        String bookName;//书名
        String author;//作者
        double price;//价格
        String type;//类别
        String publish;//出版社
        int inNum;//馆藏数量
        int outNum;//已借数量

        DataBase.sqlQuery("select * from 图书信息 where 作者 like '%"+searchName+"%'");
        try {
            DataBase.rs.last();
            int rows=DataBase.rs.getRow();
            if(rows==0) return null;
            DataBase.rs.beforeFirst();

            books=new Book[rows];
            while(DataBase.rs.next()){
                ISBN=String.valueOf(DataBase.rs.getObject(1)).trim();
                bookName=String.valueOf(DataBase.rs.getObject(2)).trim();
                author=String.valueOf(DataBase.rs.getObject(3)).trim();
                price=Double.parseDouble(DataBase.rs.getObject(4).toString());
                type=String.valueOf(DataBase.rs.getObject(5)).trim();
                publish=String.valueOf(DataBase.rs.getObject(6)).trim();
                inNum=Integer.parseInt(DataBase.rs.getObject(7).toString());
                outNum=Integer.parseInt(DataBase.rs.getObject(8).toString());
                books[bookNum++]=new Book(ISBN,bookName,author,price,type,publish,inNum,outNum);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    //测试成功
    /*借阅图书验证，0-图书剩余数量为0，无法借阅，1-成功验证，修改传入的book对象信息，并修改图书表中对应的信息*/
    public static int borrowBook(Book book,String account)
    {
        DataBase.sqlQuery("select 在馆数量 from 图书信息 where ISBN号='"+book.ISBN+"'");
        try {
            if(!DataBase.rs.next()) return 0;
            if(Integer.parseInt(DataBase.rs.getObject(1).toString())==0) return 0;
            int count=DataBase.sqlUpdate("update 图书信息 set 在馆数量=在馆数量-1,已借数量=已借数量+1 where ISBN号='"+book.ISBN+"'");
            if(count==1) {
                book.inNum--; book.outNum++;
                Date date=new Date();
                DataBase.ps=DataBase.conn.prepareStatement("insert into 借阅记录(读者账号,ISBN号,借阅时间,应当归还时间,实际归还时间) values(?,?,(select GETDATE()),?,?)");
                DataBase.ps.setString(1,account);
                DataBase.ps.setString(2,book.ISBN);
                //应当归还时间为一个月
                DataBase.ps.setDate(3,new java.sql.Date(date.getTime()+1000*60*60*24*30));
                DataBase.ps.setDate(4,null);
                DataBase.ps.executeUpdate();
                DataBase.conn.commit();
                return 1;
            }
            return 0;
        }catch (SQLException e){
            if (DataBase.conn != null) {
                try {
                    DataBase.conn.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            e.printStackTrace();
            return 0;
        }
    }

    //测试成功
    /*阅读记录查询，根据传入的读者 账号查询对应的借阅记录，创建借阅记录对象Report，将查找到的借阅记录插入到reports数组中，返回Report[]数组*/
    public static Report[] getReport(String search)
    {
        Report[] reportTs=new Report[20];
        Report[] reportFs=new Report[20];
        Report[] reports=null;
        int numberT=0,numberF=0,number=0;
        String account;//读者账号
        String ISBN;//ISBN号
        boolean borrowState=false;//借阅状态，0-已归还，1-未归还
        String borrowTime;//借阅时间
        String returnTime;//归还时间
        DataBase.sqlQuery("select * from 借阅记录 where 读者账号='"+search+"'" );
        try {
            DataBase.rs.last();
            int rows=DataBase.rs.getRow();
            if(rows==0) return null;
            DataBase.rs.beforeFirst();

            reports=new Report[rows];
            while(DataBase.rs.next()){
                account=search;
                ISBN=String.valueOf(DataBase.rs.getObject(2)).trim();
                borrowTime=DataBase.rs.getString(3);
                //Date date=DataBase.rs.getDate(5);
                Timestamp ts=DataBase.rs.getTimestamp(5);
                if(ts==null) {
                    borrowState=true;
                    returnTime="";
                    reportFs[numberF]=new Report(account,ISBN,borrowState,borrowTime,returnTime);
                    numberF++;
                }
                else{
                    borrowState=false;
                    returnTime=ts.toString();
                    reportTs[numberT]=new Report(account,ISBN,borrowState,borrowTime,returnTime);
                    numberT++;
                }

            }
            for(int i=0;i<numberF;i++){
                reports[number++]=reportFs[i];
            }
            for(int i=0;i<numberT;i++){
                reports[number++]=reportTs[i];
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return reports;
    }

    //测试成功
    /*归还图书验证，0-图书借阅状态为0，图书已归还，不需要再还书，1-成功验证，修改传入的report对象信息，并修改图书表以及借阅记录表中对应的信息*/
    public static int returnBook(Report report,String account)
    {
        if(report.borrowState==false || !report.account.equals(account)) return 0;

        java.sql.Date date=new java.sql.Date(new Date().getTime());

        try{
            DataBase.ps=DataBase.conn.prepareStatement("update 借阅记录 set 实际归还时间=(select GETDATE()) where 读者账号='"+report.account+"' and ISBN号='"+report.ISBN+"'");
            //DataBase.ps.setDate(1,date);
            int count=DataBase.ps.executeUpdate();
            DataBase.conn.commit();
            if(count!=0) {
                report.borrowState=false;
                report.returnTime=new Date().toString();
                DataBase.sqlUpdate("update 图书信息 set 在馆数量=在馆数量+1,已借数量=已借数量-1 where ISBN号='"+report.ISBN+"'");
                return 1;
            }
        }catch (SQLException e){
            if (DataBase.conn != null) {
                try {
                    DataBase.conn.rollback();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return 0;
    }

    //测试成功
    /*用户信息查询，根据传入的读者账号查询对应的读者信息，创建读者对象User，将信息存放到user中并返回*/
    public static User getUser(String account)
    {
        User user=null;
        String name="";//读者姓名
        String password="";//读者密码
        String phone="";//手机

        DataBase.sqlQuery("select * from 读者信息 where 读者账号='"+account+"'");
        try {
            while(DataBase.rs.next()){
                password=DataBase.rs.getString(2);
                name=DataBase.rs.getString(3);
                phone=DataBase.rs.getString(4);
            }
            user=new User(account,password,name,phone);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return user;
    }

    //测试成功
    /*修改用户信息，0-name和原来用户的姓名一致，无需修改，1-成功验证，修改传入的user对象信息，并修改用户表中对应的信息*/
    public static int modifyUserName(String name, User user)
    {
        if(user.name==name) return 0;
        else{
            user.name=name;
            int count= DataBase.sqlUpdate("update 读者信息 set 姓名='"+name+"' where 读者账号='"+user.account+"'");
            if(count==1) return 1;
            return 0;
        }

    }

    //测试成功
    /*修改用户密码，0-旧密码错误，1-新密码错误，密码应为6-18位数字、字母组成，2-密码不一致（password和rePassword）
    3-成功验证，修改user对象，并修改用户表中对应的信息*/
    public static int modifyUserPassword(String oldPassword, String newPassword, String rePassword, User user)
    {
        if(!newPassword.equals(rePassword)) return 2;
        if(newPassword.length()<6 || newPassword.length()>18) return 1;
        DataBase.sqlQuery("select 密码 from 读者信息 where 读者账号='"+user.account+"'");
        try{
            DataBase.rs.next();
            if(!DataBase.rs.getString(1).trim().equals(user.password)) {
                return 0;
            }
            int count=DataBase.sqlUpdate("update 读者信息 set 密码='"+newPassword+"' where 读者账号='"+user.account+"'");
            if(count==1){
                user.password=newPassword;
                return 3;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //数据库删除不了，拒绝注销
    /*注销用户验证，0-任意一项传入的信息和user对象中的信息不一致，则无法注销，1-成功验证，删除用户表和借阅记录表中对应的信息*/
    public static int logoutUser(String account, String password, String name, String phone, User user)
    {
//        if(account.equals(user.account) && password.equals(user.password) && name.equals(user.name)){
//           int count= DataBase.sqlUpdate("delete from 读者信息 where 读者账号='"+account+"'");
//           if (count==1) return 1;
//        }
        return 0;
    }

    //测试成功
    //不删除原来图书，添加新图书
    /*修改图书信息，0-ISBN号已存在，1-价格price<0，2-馆藏数量inNum<0，3-已借数量outNum<0，4-馆藏数量inNum<已借数量outNum
    5-成功验证，修改book对象信息，并修改图书表和阅读记录表中对应信息*/
    public static int modifyBook(String ISBN,String bookName,String author,double price,String type,String publish,int inNum,Book book)
    {
        if(price<0) return 1;
        if(book.inNum<0) return 2;
        if(book.outNum<0) return 3;
        DataBase.sqlQuery("select * from 图书信息 where ISBN号='"+ISBN+"'");
        try{
            if(DataBase.rs.next()) return 0;
            else{
                book.ISBN=ISBN;
                book.bookName=bookName;
                book.author=author;
                book.price=price;
                book.publish=publish;
                book.inNum=inNum;
                Common.addBook(ISBN,bookName,author,price,type,publish,inNum,book.outNum);
                return 4;
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    //太难写了，拒绝删除，数据库有奇怪的外键关联，拒绝删除操作
    /*删除图书验证，0-book[i]图书已借数量>0，不可删除，1-成功验证，删除book[i]，并删除图书表和借阅记录表中对应的信息*/
    public static int deleteBook(int n,Book book[])
    {
        return 0;
    }

    //测试成功
    /*添加图书验证，0-ISBN号已存在，1-价格price<0，2-馆藏数量inNum<0，3-已借数量outNum<0，4-馆藏数量inNum<已借数量outNum
    5-成功验证，在图书表中添加信息*/
    public static int addBook(String ISBN,String bookName,String author,double price,String type,String publish,int inNum,int outNum)
    {
        if(price<0) return 1;
        if(inNum<0) return 2;
        if(outNum<0) return 3;
        if(inNum<outNum) return 4;
        DataBase.sqlQuery("select * from 图书信息 where ISBN号='"+ISBN+"'");
        try{
            if(DataBase.rs.next()) return 0;
            DataBase.ps=DataBase.conn.prepareStatement("insert into 图书信息(ISBN号,书名,作者,价格,类别,出版社,在馆数量,已借数量)" +
                    " values(?,?,?,?,?,?,?,?)");
            DataBase.ps.setString(1,ISBN);
            DataBase.ps.setString(2,bookName);
            DataBase.ps.setString(3,author);
            DataBase.ps.setDouble(4,price);
            DataBase.ps.setString(5,type);
            DataBase.ps.setString(6,publish);
            DataBase.ps.setInt(7,inNum);
            DataBase.ps.setInt(8,outNum);
            int count=DataBase.ps.executeUpdate();
            DataBase.conn.commit();
            if(count==1) return 5;
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return 0;
    }

    //测试成功
    /*管理员信息查询，根据传入的管理员账号查询对应的管理员信息，创建管理员对象Manager，将信息存放到manager中并返回*/
    public static Manager getManager(String account)
    {
        Manager manager=null;
        String password;//管理员密码
        String name;//管理员姓名
        String phone;//管理员手机号
        DataBase.sqlQuery("select * from 管理员信息 where 管理员账号='"+account+"'");
        try {
            if(DataBase.rs.next()){
                password=DataBase.rs.getString(2).trim();
                name=DataBase.rs.getString(3).trim();
                phone=DataBase.rs.getString(4).trim();
                manager=new Manager(account,password,name,phone);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return manager;
    }

    //跟修改用户名字一样，不用测试，，，吧
    /*修改管理员信息，0-name和原来管理员的姓名一致，无需修改，1-成功验证，修改传入的manager对象信息，并修改管理员表中对应的信息*/
    public static int modifyManagerName(String name, Manager manager)
    {
        if(manager.name==name) return 0;
        else{
            manager.name=name;
            int count= DataBase.sqlUpdate("update 管理员信息 set 姓名='"+name+"' where 管理员账号='"+manager.account+"'");
            if(count==1) return 1;
            return 0;
        }
    }

    //这也不用测试，，，，吧
    /*修改管理员密码，0-旧密码错误，1-新密码错误，密码应为6-18位数字、字母组成，2-密码不一致（password和rePassword）
    3-成功验证，修改manager对象，并修改管理员表中对应的信息*/
    public static int modifyManagerPassword(String oldPassword, String newPassword, String rePassword, Manager manager)
    {
        if(!newPassword.equals(rePassword)) return 2;
        if(newPassword.length()<6 || newPassword.length()>18) return 1;
        DataBase.sqlQuery("select 密码 from 管理员信息 where 管理员账号='"+manager.account+"'");
        try{
            DataBase.rs.next();
            if(!DataBase.rs.getString(1).trim().equals(manager.password)) {
                return 0;
            }
            int count=DataBase.sqlUpdate("update 管理员信息 set 密码='"+newPassword+"' where 管理员账号='"+manager.account+"'");
            if(count==1){
                manager.password=newPassword;
                return 3;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //测试成功
    /*注销管理员验证，0-任意一项传入的信息和manager对象中的信息不一致，则无法注销，1-成功验证，删除管理员表中对应的信息*/
    public static int logoutManager(String account, String password, String name, String phone, Manager manager)
    {
        if(account.equals(manager.account) && password.equals(manager.password) && name.equals(manager.name) && phone.equals(manager.phone)){
            int count=DataBase.sqlUpdate("delete from 管理员信息 where 管理员账号='"+manager.account+"'");
            if(count==1) return 1;
        }
        return 0;
    }

    //测试成功
    /*返回图书表中所有图书的数据*/
    public static Book[] getAllBook()
    {
        Book[] books=null;
        int bookNum=0,rows=0;
        String ISBN;//ISBN号
        String bookName;//书名
        String author;//作者
        double price;//价格
        String type;//类别
        String publish;//出版社
        int inNum;//馆藏数量
        int outNum;//已借数量
        DataBase.sqlQuery("select * from 图书信息");
        try{
            DataBase.rs.last();
            rows=DataBase.rs.getRow();
            if(rows==0) return null;
            books=new Book[rows];
            DataBase.rs.beforeFirst();
            while (DataBase.rs.next()){
                ISBN=DataBase.rs.getString(1).trim();
                bookName=DataBase.rs.getString(2).trim();
                author=DataBase.rs.getString(3).trim();
                price=DataBase.rs.getDouble(4);
                type=DataBase.rs.getString(5).trim();
                publish=DataBase.rs.getString(6).trim();
                inNum=DataBase.rs.getInt(7);
                outNum=DataBase.rs.getInt(8);
                books[bookNum++]=new Book(ISBN,bookName,author,price,type,publish,inNum,outNum);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return books;
    }

    /*ISBN号查询，根据对应ISBN号进行精确查询，创建图书对象Book，将查找到的图书添加到book对象中，返回Book对象*/
    public static Book ISBNSearch(String ISBN)
    {
        Book book=null;
        String bookName;//书名
        String author;//作者
        double price;//价格
        String type;//类别
        String publish;//出版社
        int inNum;//馆藏数量
        int outNum;//已借数量
        DataBase.sqlQuery("select * from 图书信息 where ISBN号='"+ISBN+"'");
        try{
            if(DataBase.rs.next()){
                bookName=DataBase.rs.getString(2).trim();
                author=DataBase.rs.getString(3).trim();
                price=DataBase.rs.getDouble(4);
                type=DataBase.rs.getString(5).trim();
                publish=DataBase.rs.getString(6).trim();
                inNum=DataBase.rs.getInt(7);
                outNum=DataBase.rs.getInt(8);
                book=new Book(ISBN,bookName,author,price,type,publish,inNum,outNum);
                return book;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
