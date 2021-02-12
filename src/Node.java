import java.util.ArrayList;

public class Node {
    Table table;
    int point;
    ArrayList<Node> child;
    String seq;

    public Node(Table table, String seq) {
        this.table = table;
        this.child = new ArrayList<>();
        this.seq = seq;
    }
}
