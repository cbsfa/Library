public class Book//图书对象
{
    String ISBN;//ISBN号
    String bookName;//书名
    String author;//作者
    double price;//价格
    String type;//类别
    String publish;//出版社
    int inNum;//馆藏数量
    int outNum;//已借数量
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
