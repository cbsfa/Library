public class Book//ͼ�����
{
    String ISBN;//ISBN��
    String bookName;//����
    String author;//����
    double price;//�۸�
    String type;//���
    String publish;//������
    int inNum;//�ݲ�����
    int outNum;//�ѽ�����
    Book(String ISBN,String bookName,String author,double price,String type,String publish,int inNum,int outNum)
    {
        this.ISBN=ISBN;
        this.bookName=bookName;
        this.author=author;
        this.price=price;
        this.type=type;
        this.publish=publish;
        this.inNum=inNum;
        this.outNum=outNum;
    }
}
