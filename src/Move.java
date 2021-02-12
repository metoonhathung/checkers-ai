import java.util.ArrayList;

public class Move {
    ArrayList<Snapshot> snapshots;

    public Move() {
        snapshots = new ArrayList<>();
    }

    void add(Snapshot snapshot) {
        snapshots.add(snapshot);
    }
}
