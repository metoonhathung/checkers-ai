import java.util.ArrayList;
import java.util.Random;

public class RandomBot extends Player {

    @Override
    boolean play(Table table, boolean blackTurn, int depth) {
        ArrayList<String> arr = new ArrayList<>();
        Random random = new Random();
        if (color == 0) {
            if (!table.whiteMap.keySet().isEmpty()) {
                arr.addAll(table.whiteMap.keySet());
                String s = arr.get(random.nextInt(arr.size()));
                table.board = table.whiteMap.get(s);
                System.out.println("WHITE chooses " + s);
                return true;
            }
        }
        if (color == 1) {
            if (!table.blackMap.keySet().isEmpty()) {
                arr.addAll(table.blackMap.keySet());
                String s = arr.get(random.nextInt(arr.size()));
                table.board = table.blackMap.get(s);
                System.out.println("BLACK chooses " + s);
                return true;
            }
        }
        return false;
    }
}
