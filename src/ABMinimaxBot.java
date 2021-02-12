import java.util.ArrayList;
import java.util.Random;

public class ABMinimaxBot extends MinimaxBot {

    @Override
    boolean play(Table table, boolean blackTurn, int depth) {
        Node root = new Node(table, "");
        abMinimax(root, blackTurn, depth, -999, 999);
        int max = -999;
        for (Node child : root.child) {
            if (child.point > max) {
                max = child.point;
            }
        }
        ArrayList<Node> temp = new ArrayList<>();
        for (Node child : root.child) {
            if (child.point == max) {
                temp.add(child);
            }
        }
        Random random = new Random();
        Node choice = temp.get(random.nextInt(temp.size()));
        table.board = choice.table.board;
        if (color == 0) {
            System.out.print("WHITE ");
        } else if (color == 1) {
            System.out.print("BLACK ");
        }
        System.out.println("chooses " + choice.seq);
        return true;
    }

    /**
     * alpha beta pruning
     **/
    void abMinimax(Node root, boolean blackTurn, int depth, int alpha, int beta) {
        root.table.allActions();
        if (root.table.goal()) {
            ultility(root);
        } else if (depth == 0) {
            heuristic(root);
        } else {
            depth--;
            /** white turn **/
            if (!blackTurn) {
                for (String s : root.table.whiteMap.keySet()) {
                    Table white = new Table(root.table.n);
                    white.retrieve(root.table.whiteMap.get(s));
                    /** min **/
                    if (color == 1) {
                        root.point = 999;
                        Node child = new Node(white, s);
                        root.child.add(child);
                        abMinimax(child, !blackTurn, depth, alpha, beta);
                        root.point = Math.min(root.point, child.point);
                        beta = Math.min(beta, root.point);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                    /** max **/
                    else if (color == 0) {
                        root.point = -999;
                        Node child = new Node(white, s);
                        root.child.add(child);
                        abMinimax(child, !blackTurn, depth, alpha, beta);
                        root.point = Math.max(root.point, child.point);
                        alpha = Math.max(alpha, root.point);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            /** black turn **/
            if (blackTurn) {
                for (String s : root.table.blackMap.keySet()) {
                    Table black = new Table(root.table.n);
                    black.retrieve(root.table.blackMap.get(s));
                    /** max **/
                    if (color == 1) {
                        root.point = -999;
                        Node child = new Node(black, s);
                        root.child.add(child);
                        abMinimax(child, !blackTurn, depth, alpha, beta);
                        root.point = Math.max(root.point, child.point);
                        alpha = Math.max(alpha, root.point);
                        if (beta < alpha) {
                            break;
                        }
                    }
                    /** min **/
                    else if (color == 0) {
                        root.point = 999;
                        Node child = new Node(black, s);
                        root.child.add(child);
                        abMinimax(child, !blackTurn, depth, alpha, beta);
                        root.point = Math.min(root.point, child.point);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
        }
    }
}