public class HMinimaxBot extends ABMinimaxBot {

    @Override
    /**
     * alpha beta pruning
     **/
    void abMinimax(Node root, boolean blackTurn, int depth, int alpha, int beta) {
        root.table.allActions();
        if (depth == 0) {
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