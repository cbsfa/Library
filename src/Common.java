import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;


public class Common
{

    /*��¼��֤��0-�˺Ŵ���1-�������2-�ɹ������û����棬3-�ɹ��������Ա����*/
    //���Գɹ�
    public static int login(String account,String password)
    {
        DataBase.sqlQuery("select �����˺�,���� from ������Ϣ where �����˺�='"+account+"'");
        try{
            if(DataBase.rs.next()){
                if(DataBase.rs.getString(2).trim().equals(password)) return 2;
                else return 1;
            }else{
                DataBase.sqlQuery("select ����Ա�˺�,���� from ����Ա��Ϣ where ����Ա�˺�="+account);
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

    //���Գɹ�
    /*ע����֤��0-�˺��Ѵ��ڣ�1-����ӦΪ6-18λ���֡���ĸ��ɣ�2-���벻һ�£�password��rePassword����3-�ֻ���ӦΪ11λ����
    4-�ֻ����Ѵ��ڣ�5-�ɹ���֤����������û���Ϣ���뵽�û�����*/
    public static int register(String account,String password,String rePassword,String name,String phone)
    {
        if(!password.equals(rePassword)) return 2;

        //����ʧ�ܣ��������������⹦���۲�Ҫ�ˣ�
        //java������ʽ��copy���ģ���������
        //String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$";
        //if(!password.matches(regex)) return 1;
        //regex="^-?[0-9]+";
        //if(!phone.matches(regex) || phone.length()!=11) return 3 ;
        if(password.length()<6 || password.length()>18) return 1;
        if(phone.length()!=11) return 3;

        DataBase.sqlQuery("select �����˺� from ������Ϣ where �����˺�='"+account+"'");
        try{
            if(DataBase.rs.next()) return 0;
            else{
                DataBase.sqlQuery("select �ֻ����� from ������Ϣ where �ֻ�����='"+phone+"'");
                if(DataBase.rs.next()) return 4;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        try{
            DataBase.ps=DataBase.conn.prepareStatement("insert into ������Ϣ(�����˺�,����,����,�ֻ�����) values(?,?,?,?)");
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

    //���Գɹ�
    /*�һ�������֤��0-�˺Ŵ���1-�ֻ��Ŵ���2-����ӦΪ6-18λ���֡���ĸ��ɣ�3-���벻һ�£�password��rePassword��
    4-�ɹ���֤���޸��û�����߹���Ա���ж�Ӧ��ֵ*/
    public static int retrieve(String account,String password,String rePassword,String phone)
    {
        if(!password.equals(rePassword)) return 3;
        if(password.length()<6 || password.length()>18) return 2;
        DataBase.sqlQuery("select �ֻ����� from ������Ϣ where �����˺�='"+account+"'");
        try{
            if(DataBase.rs.next() && DataBase.rs.getString(1).trim().equals(phone)){
                int count=DataBase.sqlUpdate("update ������Ϣ set ����='"+password+"' where �����˺�='"+account+"'");
                if(count==1) return 4;
            }else return 1;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //���Գɹ�
    /*������ѯ�����ݶ�Ӧ��������ģ����ѯ������ͼ�����Book�������ҵ���ͼ����뵽books�����У�����Book[]����*/
    public static Book[] bookNameSearch(String searchName)//����
    {
        Book[] books=null;
        int bookNum=0;
        String ISBN;//ISBN��
        String bookName;//����
        String author;//����
        double price;//�۸�
        String type;//���
        String publish;//������
        int inNum;//�ݲ�����
        int outNum;//�ѽ�����

        DataBase.sqlQuery("select * from ͼ����Ϣ where ���� like '%"+searchName+"%'");
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

    //���Գɹ�
    /*���߲�ѯ�����ݶ�Ӧ���߽��о�ȷ��ѯ������ͼ�����Book�������ҵ���ͼ����뵽books�����У�����Book[]����*/
    public static Book[] authorSearch(String searchName)//������
    {
        Book[] books=null;
        int bookNum=0;
        String ISBN;//ISBN��
        String bookName;//����
        String author;//����
        double price;//�۸�
        String type;//���
        String publish;//������
        int inNum;//�ݲ�����
        int outNum;//�ѽ�����

        DataBase.sqlQuery("select * from ͼ����Ϣ where ���� like '%"+searchName+"%'");
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

    //���Գɹ�
    /*����ͼ����֤��0-ͼ��ʣ������Ϊ0���޷����ģ�1-�ɹ���֤���޸Ĵ����book������Ϣ�����޸�ͼ����ж�Ӧ����Ϣ*/
    public static int borrowBook(Book book,String account)
    {
        DataBase.sqlQuery("select �ڹ����� from ͼ����Ϣ where ISBN��='"+book.ISBN+"'");
        try {
            if(!DataBase.rs.next()) return 0;
            if(Integer.parseInt(DataBase.rs.getObject(1).toString())==0) return 0;
            int count=DataBase.sqlUpdate("update ͼ����Ϣ set �ڹ�����=�ڹ�����-1,�ѽ�����=�ѽ�����+1 where ISBN��='"+book.ISBN+"'");
            if(count==1) {
                book.inNum--; book.outNum++;
                Date date=new Date();
                DataBase.ps=DataBase.conn.prepareStatement("insert into ���ļ�¼(�����˺�,ISBN��,����ʱ��,Ӧ���黹ʱ��,ʵ�ʹ黹ʱ��) values(?,?,(select GETDATE()),?,?)");
                DataBase.ps.setString(1,account);
                DataBase.ps.setString(2,book.ISBN);
                //Ӧ���黹ʱ��Ϊһ����
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

    //���Գɹ�
    /*�Ķ���¼��ѯ�����ݴ���Ķ��� �˺Ų�ѯ��Ӧ�Ľ��ļ�¼���������ļ�¼����Report�������ҵ��Ľ��ļ�¼���뵽reports�����У�����Report[]����*/
    public static Report[] getReport(String search)
    {
        Report[] reportTs=new Report[20];
        Report[] reportFs=new Report[20];
        Report[] reports=null;
        int numberT=0,numberF=0,number=0;
        String account;//�����˺�
        String ISBN;//ISBN��
        boolean borrowState=false;//����״̬��0-�ѹ黹��1-δ�黹
        String borrowTime;//����ʱ��
        String returnTime;//�黹ʱ��
        DataBase.sqlQuery("select * from ���ļ�¼ where �����˺�='"+search+"'" );
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

    //���Գɹ�
    /*�黹ͼ����֤��0-ͼ�����״̬Ϊ0��ͼ���ѹ黹������Ҫ�ٻ��飬1-�ɹ���֤���޸Ĵ����report������Ϣ�����޸�ͼ����Լ����ļ�¼���ж�Ӧ����Ϣ*/
    public static int returnBook(Report report,String account)
    {
        if(report.borrowState==false || !report.account.equals(account)) return 0;

        java.sql.Date date=new java.sql.Date(new Date().getTime());

        try{
            DataBase.ps=DataBase.conn.prepareStatement("update ���ļ�¼ set ʵ�ʹ黹ʱ��=(select GETDATE()) where �����˺�='"+report.account+"' and ISBN��='"+report.ISBN+"'");
            //DataBase.ps.setDate(1,date);
            int count=DataBase.ps.executeUpdate();
            DataBase.conn.commit();
            if(count!=0) {
                report.borrowState=false;
                report.returnTime=new Date().toString();
                DataBase.sqlUpdate("update ͼ����Ϣ set �ڹ�����=�ڹ�����+1,�ѽ�����=�ѽ�����-1 where ISBN��='"+report.ISBN+"'");
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

    //���Գɹ�
    /*�û���Ϣ��ѯ�����ݴ���Ķ����˺Ų�ѯ��Ӧ�Ķ�����Ϣ���������߶���User������Ϣ��ŵ�user�в�����*/
    public static User getUser(String account)
    {
        User user=null;
        String name="";//��������
        String password="";//��������
        String phone="";//�ֻ�

        DataBase.sqlQuery("select * from ������Ϣ where �����˺�='"+account+"'");
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

    //���Գɹ�
    /*�޸��û���Ϣ��0-name��ԭ���û�������һ�£������޸ģ�1-�ɹ���֤���޸Ĵ����user������Ϣ�����޸��û����ж�Ӧ����Ϣ*/
    public static int modifyUserName(String name, User user)
    {
        if(user.name==name) return 0;
        else{
            user.name=name;
            int count= DataBase.sqlUpdate("update ������Ϣ set ����='"+name+"' where �����˺�='"+user.account+"'");
            if(count==1) return 1;
            return 0;
        }

    }

    //���Գɹ�
    /*�޸��û����룬0-���������1-�������������ӦΪ6-18λ���֡���ĸ��ɣ�2-���벻һ�£�password��rePassword��
    3-�ɹ���֤���޸�user���󣬲��޸��û����ж�Ӧ����Ϣ*/
    public static int modifyUserPassword(String oldPassword, String newPassword, String rePassword, User user)
    {
        if(!newPassword.equals(rePassword)) return 2;
        if(newPassword.length()<6 || newPassword.length()>18) return 1;
        DataBase.sqlQuery("select ���� from ������Ϣ where �����˺�='"+user.account+"'");
        try{
            DataBase.rs.next();
            if(!DataBase.rs.getString(1).trim().equals(user.password)) {
                return 0;
            }
            int count=DataBase.sqlUpdate("update ������Ϣ set ����='"+newPassword+"' where �����˺�='"+user.account+"'");
            if(count==1){
                user.password=newPassword;
                return 3;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //���ݿ�ɾ�����ˣ��ܾ�ע��
    /*ע���û���֤��0-����һ������Ϣ��user�����е���Ϣ��һ�£����޷�ע����1-�ɹ���֤��ɾ���û���ͽ��ļ�¼���ж�Ӧ����Ϣ*/
    public static int logoutUser(String account, String password, String name, String phone, User user)
    {
//        if(account.equals(user.account) && password.equals(user.password) && name.equals(user.name)){
//           int count= DataBase.sqlUpdate("delete from ������Ϣ where �����˺�='"+account+"'");
//           if (count==1) return 1;
//        }
        return 0;
    }

    //���Գɹ�
    //��ɾ��ԭ��ͼ�飬�����ͼ��
    /*�޸�ͼ����Ϣ��0-ISBN���Ѵ��ڣ�1-�۸�price<0��2-�ݲ�����inNum<0��3-�ѽ�����outNum<0��4-�ݲ�����inNum<�ѽ�����outNum
    5-�ɹ���֤���޸�book������Ϣ�����޸�ͼ�����Ķ���¼���ж�Ӧ��Ϣ*/
    public static int modifyBook(String ISBN,String bookName,String author,double price,String type,String publish,int inNum,Book book)
    {
        if(price<0) return 1;
        if(book.inNum<0) return 2;
        if(book.outNum<0) return 3;
        DataBase.sqlQuery("select * from ͼ����Ϣ where ISBN��='"+ISBN+"'");
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

    //̫��д�ˣ��ܾ�ɾ�������ݿ�����ֵ�����������ܾ�ɾ������
    /*ɾ��ͼ����֤��0-book[i]ͼ���ѽ�����>0������ɾ����1-�ɹ���֤��ɾ��book[i]����ɾ��ͼ���ͽ��ļ�¼���ж�Ӧ����Ϣ*/
    public static int deleteBook(int n,Book book[])
    {
        return 0;
    }

    //���Գɹ�
    /*���ͼ����֤��0-ISBN���Ѵ��ڣ�1-�۸�price<0��2-�ݲ�����inNum<0��3-�ѽ�����outNum<0��4-�ݲ�����inNum<�ѽ�����outNum
    5-�ɹ���֤����ͼ����������Ϣ*/
    public static int addBook(String ISBN,String bookName,String author,double price,String type,String publish,int inNum,int outNum)
    {
        if(price<0) return 1;
        if(inNum<0) return 2;
        if(outNum<0) return 3;
        if(inNum<outNum) return 4;
        DataBase.sqlQuery("select * from ͼ����Ϣ where ISBN��='"+ISBN+"'");
        try{
            if(DataBase.rs.next()) return 0;
            DataBase.ps=DataBase.conn.prepareStatement("insert into ͼ����Ϣ(ISBN��,����,����,�۸�,���,������,�ڹ�����,�ѽ�����)" +
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

    //���Գɹ�
    /*����Ա��Ϣ��ѯ�����ݴ���Ĺ���Ա�˺Ų�ѯ��Ӧ�Ĺ���Ա��Ϣ����������Ա����Manager������Ϣ��ŵ�manager�в�����*/
    public static Manager getManager(String account)
    {
        Manager manager=null;
        String password;//����Ա����
        String name;//����Ա����
        String phone;//����Ա�ֻ���
        DataBase.sqlQuery("select * from ����Ա��Ϣ where ����Ա�˺�='"+account+"'");
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

    //���޸��û�����һ�������ò��ԣ�������
    /*�޸Ĺ���Ա��Ϣ��0-name��ԭ������Ա������һ�£������޸ģ�1-�ɹ���֤���޸Ĵ����manager������Ϣ�����޸Ĺ���Ա���ж�Ӧ����Ϣ*/
    public static int modifyManagerName(String name, Manager manager)
    {
        if(manager.name==name) return 0;
        else{
            manager.name=name;
            int count= DataBase.sqlUpdate("update ����Ա��Ϣ set ����='"+name+"' where ����Ա�˺�='"+manager.account+"'");
            if(count==1) return 1;
            return 0;
        }
    }

    //��Ҳ���ò��ԣ���������
    /*�޸Ĺ���Ա���룬0-���������1-�������������ӦΪ6-18λ���֡���ĸ��ɣ�2-���벻һ�£�password��rePassword��
    3-�ɹ���֤���޸�manager���󣬲��޸Ĺ���Ա���ж�Ӧ����Ϣ*/
    public static int modifyManagerPassword(String oldPassword, String newPassword, String rePassword, Manager manager)
    {
        if(!newPassword.equals(rePassword)) return 2;
        if(newPassword.length()<6 || newPassword.length()>18) return 1;
        DataBase.sqlQuery("select ���� from ����Ա��Ϣ where ����Ա�˺�='"+manager.account+"'");
        try{
            DataBase.rs.next();
            if(!DataBase.rs.getString(1).trim().equals(manager.password)) {
                return 0;
            }
            int count=DataBase.sqlUpdate("update ����Ա��Ϣ set ����='"+newPassword+"' where ����Ա�˺�='"+manager.account+"'");
            if(count==1){
                manager.password=newPassword;
                return 3;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

    //���Գɹ�
    /*ע������Ա��֤��0-����һ������Ϣ��manager�����е���Ϣ��һ�£����޷�ע����1-�ɹ���֤��ɾ������Ա���ж�Ӧ����Ϣ*/
    public static int logoutManager(String account, String password, String name, String phone, Manager manager)
    {
        if(account.equals(manager.account) && password.equals(manager.password) && name.equals(manager.name) && phone.equals(manager.phone)){
            int count=DataBase.sqlUpdate("delete from ����Ա��Ϣ where ����Ա�˺�='"+manager.account+"'");
            if(count==1) return 1;
        }
        return 0;
    }

    //���Գɹ�
    /*����ͼ���������ͼ�������*/
    public static Book[] getAllBook()
    {
        Book[] books=null;
        int bookNum=0,rows=0;
        String ISBN;//ISBN��
        String bookName;//����
        String author;//����
        double price;//�۸�
        String type;//���
        String publish;//������
        int inNum;//�ݲ�����
        int outNum;//�ѽ�����
        DataBase.sqlQuery("select * from ͼ����Ϣ");
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

    /*ISBN�Ų�ѯ�����ݶ�ӦISBN�Ž��о�ȷ��ѯ������ͼ�����Book�������ҵ���ͼ����ӵ�book�����У�����Book����*/
    public static Book ISBNSearch(String ISBN)
    {
        Book book=null;
        String bookName;//����
        String author;//����
        double price;//�۸�
        String type;//���
        String publish;//������
        int inNum;//�ݲ�����
        int outNum;//�ѽ�����
        DataBase.sqlQuery("select * from ͼ����Ϣ where ISBN��='"+ISBN+"'");
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
