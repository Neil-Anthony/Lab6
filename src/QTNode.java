public class QTNode {
    String data;
    QTNode left, right;

    public QTNode(String data){
        this(data, null, null);
    }

    public QTNode(String data, QTNode left, QTNode right){
        this.data = data;
        this.left = left;
        this.right = right;
    }
}
