public class Report//���ļ�¼����
{
    String account;//�����˺�
    String ISBN;//ISBN��
    boolean borrowState;//����״̬��0-�ѹ黹��1-δ�黹
    String borrowTime;//����ʱ��
    String returnTime;//�黹ʱ��
    Report(String account,String ISBN,boolean borrowState,String borrowTime,String returnTime)
    {
        this.account=account;
        this.ISBN=ISBN;
        this.borrowState=borrowState;
        this.borrowTime=borrowTime;
        this.returnTime=returnTime;
    }
}
