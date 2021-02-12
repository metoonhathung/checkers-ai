public class Snapshot {
    Checker[][] board;
    int count;
    String seq = "";

    public Snapshot(Checker[][] board, int count, String s) {
        this.board = board;
        this.count = count;
        this.seq += s;
    }
}
