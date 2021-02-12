import java.util.ArrayList;
import java.util.Random;

public class MinimaxBot extends Player {

    @Override
    boolean play(Table table, boolean blackTurn, int depth) {
        Node root = new Node(table, "");
        minimax(root, blackTurn, depth);
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

    void ultility(Node node) {
        if (node.table.whiteSnapshots.size() == 0) {
            if (color == 1) {
                node.point = 1;
            } else if (color == 0) {
                node.point = -1;
            }
        } else if (node.table.blackSnapshots.size() == 0) {
            if (color == 1) {
                node.point = -1;
            } else if (color == 0) {
                node.point = 1;
            }
        }
    }

    /**
     * heuristic: calculate the difference between sums of checkers.
     **/
    void heuristic(Node node) {
        int sumWhite = 0;
        int sumBlack = 0;
        for (int i = 0; i < node.table.board.length; i++) {
            for (int j = 0; j < node.table.board[i].length; j++) {
                if (node.table.board[i][j] != null) {
                    if (node.table.board[i][j].color == 0) {
                        if (node.table.board[i][j].isKing) sumWhite += 5;
                        else sumWhite += 1;
                    }
                    if (node.table.board[i][j].color == 1) {
                        if (node.table.board[i][j].isKing) sumBlack += 5;
                        else sumBlack += 1;
                    }
                }
            }
        }
        if (color == 0) {
            node.point = sumWhite - sumBlack;
        } else if (color == 1) {
            node.point = sumBlack - sumWhite;
        }
    }

    void minimax(Node root, boolean blackTurn, int depth) {
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
                        minimax(child, !blackTurn, depth);
                        root.point = Math.min(root.point, child.point);
                    }
                    /** max **/
                    else if (color == 0) {
                        root.point = -999;
                        Node child = new Node(white, s);
                        root.child.add(child);
                        minimax(child, !blackTurn, depth);
                        root.point = Math.max(root.point, child.point);
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
                        minimax(child, !blackTurn, depth);
                        root.point = Math.max(root.point, child.point);
                    }
                    /** min **/
                    else if (color == 0) {
                        root.point = 999;
                        Node child = new Node(black, s);
                        root.child.add(child);
                        minimax(child, !blackTurn, depth);
                        root.point = Math.min(root.point, child.point);
                    }
                }
            }
        }
    }
}