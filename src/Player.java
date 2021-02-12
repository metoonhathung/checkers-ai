import java.util.Scanner;

public class Player {
    int color;

    boolean play(Table table, boolean blackTurn, int depth) {
        System.out.print("Your move? ");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.next();
        if (color == 0) {
            if (!table.whiteMap.keySet().isEmpty()) {
                if (table.whiteMap.containsKey(s)) {
                    table.board = table.whiteMap.get(s);
                    System.out.println("WHITE chooses " + s);
                    return true;
                }
            }
        }
        if (color == 1) {
            if (!table.blackMap.keySet().isEmpty()) {
                if (table.blackMap.containsKey(s)) {
                    table.board = table.blackMap.get(s);
                    System.out.println("BLACK chooses " + s);
                    return true;
                }
            }
        }
        return false;
    }
}
