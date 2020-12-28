public class Report//借阅记录对象
{
    String account;//读者账号
    String ISBN;//ISBN号
    boolean borrowState;//借阅状态，0-已归还，1-未归还
    String borrowTime;//借阅时间
    String returnTime;//归还时间
    Report(String account,String ISBN,boolean borrowState,String borrowTime,String returnTime)
    {
        this.account=account;
        this.ISBN=ISBN;
        this.borrowState=borrowState;
        this.borrowTime=borrowTime;
        this.returnTime=returnTime;
    }
}
