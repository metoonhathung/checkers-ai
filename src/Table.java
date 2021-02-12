import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Table implements StateSpaceModel {
    int n;
    Checker[][] board;
    static ArrayList<Character> convert = new ArrayList<>();
    Character[] alphabet = {' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    ArrayList<Move> blackActions = new ArrayList<>();
    ArrayList<Move> whiteActions = new ArrayList<>();
    ArrayList<Snapshot> blackSnapshots = new ArrayList<>();
    ArrayList<Snapshot> whiteSnapshots = new ArrayList<>();
    Map<String, Checker[][]> blackMap = new HashMap<>();
    Map<String, Checker[][]> whiteMap = new HashMap<>();

    public Table(int n) {
        this.n = n;
        this.board = new Checker[n + 1][n + 1];
    }

    Checker[][] copy(Checker[][] orig) {
        Checker[][] copy = new Checker[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (orig[i][j] != null) {
                    copy[i][j] = new Checker(orig[i][j].color, i, j, orig[i][j].isKing);
                }
            }
        }
        return copy;
    }

    void retrieve(Checker[][] orig) {
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (orig[i][j] != null) {
                    board[i][j] = new Checker(orig[i][j].color, i, j, orig[i][j].isKing);
                }
            }
        }
    }


    void initiate() {
        convert.addAll(Arrays.asList(alphabet));
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (((j % 2 == 0) && (i % 2 == 1)) || ((j % 2 == 1) && (i % 2 == 0))) {
                    if (i < (n / 2)) {
                        board[i][j] = new Checker(1, i, j, false);
                    } else if (i > (n / 2 + 1)) {
                        board[i][j] = new Checker(0, i, j, false);
                    }
                }
            }
        }
    }

    boolean isKing(Checker c) {
        if (c.color == 0 && c.i == 1) {
            c.isKing = true;
            return true;
        } else if (c.color == 1 && c.i == n) {
            c.isKing = true;
            return true;
        } else return false;
    }

    boolean isInTable(int i, int j) {
        if (i >= 1 && i <= n && j >= 1 && j <= n) {
            return true;
        } else return false;
    }

    boolean isEmpty(int i, int j, Checker[][] copy) {
        if (!(isInTable(i, j))) {
            return false;
        }
        if (copy[i][j] == null) {
            return true;
        } else return false;
    }

    void possibleMove(Checker c, Checker[][] copy, Move move) {
        /** up left **/
        if (isEmpty(c.i - 1, c.j - 1, copy) && (c.color == 0 || c.isKing)) {
            Checker[][] tmp = copy(copy);
            copy[c.i - 1][c.j - 1] = new Checker(c.color, c.i - 1, c.j - 1, c.isKing);
            copy[c.i][c.j] = null;
            isKing(copy[c.i - 1][c.j - 1]);
            move.add(new Snapshot(copy, 0, convert.get(c.i) + Integer.toString(c.j) + "-" + convert.get(c.i - 1) + (c.j - 1)));
            copy = tmp;
        }
        /** up right **/
        if (isEmpty(c.i - 1, c.j + 1, copy) && (c.color == 0 || c.isKing)) {
            Checker[][] tmp = copy(copy);
            copy[c.i - 1][c.j + 1] = new Checker(c.color, c.i - 1, c.j + 1, c.isKing);
            copy[c.i][c.j] = null;
            isKing(copy[c.i - 1][c.j + 1]);
            move.add(new Snapshot(copy, 0, convert.get(c.i) + Integer.toString(c.j) + "-" + convert.get(c.i - 1) + (c.j + 1)));
            copy = tmp;
        }
        /** down left **/
        if (isEmpty(c.i + 1, c.j - 1, copy) && (c.color == 1 || c.isKing)) {
            Checker[][] tmp = copy(copy);
            copy[c.i + 1][c.j - 1] = new Checker(c.color, c.i + 1, c.j - 1, c.isKing);
            copy[c.i][c.j] = null;
            isKing(copy[c.i + 1][c.j - 1]);
            move.add(new Snapshot(copy, 0, convert.get(c.i) + Integer.toString(c.j) + "-" + convert.get(c.i + 1) + (c.j - 1)));
            copy = tmp;
        }
        /** down right **/
        if (isEmpty(c.i + 1, c.j + 1, copy) && (c.color == 1 || c.isKing)) {
            Checker[][] tmp = copy(copy);
            copy[c.i + 1][c.j + 1] = new Checker(c.color, c.i + 1, c.j + 1, c.isKing);
            copy[c.i][c.j] = null;
            isKing(copy[c.i + 1][c.j + 1]);
            move.add(new Snapshot(copy, 0, convert.get(c.i) + Integer.toString(c.j) + "-" + convert.get(c.i + 1) + (c.j + 1)));
            copy = tmp;
        }
    }

    void possibleCapture(Checker c, Checker[][] copy, Move move, int count, String s) {
        /** base case **/
        if (
                !(isInTable(c.i - 1, c.j - 1) && !isEmpty(c.i - 1, c.j - 1, copy) && copy[c.i - 1][c.j - 1].color != c.color && isEmpty(c.i - 2, c.j - 2, copy) && (c.color == 0 || c.isKing))
                        && !(isInTable(c.i - 1, c.j + 1) && !isEmpty(c.i - 1, c.j + 1, copy) && copy[c.i - 1][c.j + 1].color != c.color && isEmpty(c.i - 2, c.j + 2, copy) && (c.color == 0 || c.isKing))
                        && !(isInTable(c.i + 1, c.j - 1) && !isEmpty(c.i + 1, c.j - 1, copy) && copy[c.i + 1][c.j - 1].color != c.color && isEmpty(c.i + 2, c.j - 2, copy) && (c.color == 1 || c.isKing))
                        && !(isInTable(c.i + 1, c.j + 1) && !isEmpty(c.i + 1, c.j + 1, copy) && copy[c.i + 1][c.j + 1].color != c.color && isEmpty(c.i + 2, c.j + 2, copy) && (c.color == 1 || c.isKing))
        ) {
            move.add(new Snapshot(copy, count, s));
        }
        count++;
        String s0 = s;
        Checker[][] tmp = copy(copy);
        /** up left **/
        if (isInTable(c.i - 1, c.j - 1) && !isEmpty(c.i - 1, c.j - 1, copy) && copy[c.i - 1][c.j - 1].color != c.color && isEmpty(c.i - 2, c.j - 2, copy) && (c.color == 0 || c.isKing)) {
            copy[c.i - 2][c.j - 2] = new Checker(c.color, c.i - 2, c.j - 2, c.isKing);
            copy[c.i - 1][c.j - 1] = null;
            Checker temp = new Checker(c.color, c.i, c.j, c.isKing);
            copy[c.i][c.j] = null;
            s += "x" + convert.get(c.i - 2) + (c.j - 2);
            if (isKing(copy[c.i - 2][c.j - 2]) && !temp.isKing) {
                move.add(new Snapshot(copy, count, s));
            } else possibleCapture(copy[c.i - 2][c.j - 2], copy, move, count, s);
            copy = tmp;
            s = s0;
        }
        /** up right **/
        if (isInTable(c.i - 1, c.j + 1) && !isEmpty(c.i - 1, c.j + 1, copy) && copy[c.i - 1][c.j + 1].color != c.color && isEmpty(c.i - 2, c.j + 2, copy) && (c.color == 0 || c.isKing)) {
            copy[c.i - 2][c.j + 2] = new Checker(c.color, c.i - 2, c.j + 2, c.isKing);
            copy[c.i - 1][c.j + 1] = null;
            Checker temp = new Checker(c.color, c.i, c.j, c.isKing);
            copy[c.i][c.j] = null;
            s += "x" + convert.get(c.i - 2) + (c.j + 2);
            if (isKing(copy[c.i - 2][c.j + 2]) && !temp.isKing) {
                move.add(new Snapshot(copy, count, s));
            } else possibleCapture(copy[c.i - 2][c.j + 2], copy, move, count, s);
            copy = tmp;
            s = s0;
        }
        /** down left **/
        if (isInTable(c.i + 1, c.j - 1) && !isEmpty(c.i + 1, c.j - 1, copy) && copy[c.i + 1][c.j - 1].color != c.color && isEmpty(c.i + 2, c.j - 2, copy) && (c.color == 1 || c.isKing)) {
            copy[c.i + 2][c.j - 2] = new Checker(c.color, c.i + 2, c.j - 2, c.isKing);
            copy[c.i + 1][c.j - 1] = null;
            Checker temp = new Checker(c.color, c.i, c.j, c.isKing);
            copy[c.i][c.j] = null;
            s += "x" + convert.get(c.i + 2) + (c.j - 2);
            if (isKing(copy[c.i + 2][c.j - 2]) && !temp.isKing) {
                move.add(new Snapshot(copy, count, s));
            } else possibleCapture(copy[c.i + 2][c.j - 2], copy, move, count, s);
            copy = tmp;
            s = s0;
        }
        /** down right **/
        if (isInTable(c.i + 1, c.j + 1) && !isEmpty(c.i + 1, c.j + 1, copy) && copy[c.i + 1][c.j + 1].color != c.color && isEmpty(c.i + 2, c.j + 2, copy) && (c.color == 1 || c.isKing)) {
            copy[c.i + 2][c.j + 2] = new Checker(c.color, c.i + 2, c.j + 2, c.isKing);
            copy[c.i + 1][c.j + 1] = null;
            Checker temp = new Checker(c.color, c.i, c.j, c.isKing);
            copy[c.i][c.j] = null;
            s += "x" + convert.get(c.i + 2) + (c.j + 2);
            if (isKing(copy[c.i + 2][c.j + 2]) && !temp.isKing) {
                move.add(new Snapshot(copy, count, s));
            } else possibleCapture(copy[c.i + 2][c.j + 2], copy, move, count, s);
            copy = tmp;
            s = s0;
        }
    }

    public Move possibleAction(Checker c) {
        Move move = new Move();
        move.add(new Snapshot(copy(board), 0, ""));
        possibleCapture(c, copy(board), move, 0, convert.get(c.i) + Integer.toString(c.j));
        int max = 0;
        for (Snapshot s : move.snapshots) {
            if (s.count > max) {
                max = s.count;
            }
        }
        ArrayList<Snapshot> temp = new ArrayList<>();
        for (Snapshot s : move.snapshots) {
            if (s.count == max) {
                temp.add(s);
            }
        }
        move.snapshots.clear();
        for (Snapshot s : temp) {
            if (s.count > 0) {
                move.add(s);
            }
        }
        if (max == 0) {
            possibleMove(c, copy(board), move);
        }
        return move;
    }

    public void allActions() {
        whiteActions.clear();
        blackActions.clear();
        whiteSnapshots.clear();
        blackSnapshots.clear();
        whiteMap.clear();
        blackMap.clear();
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].color == 0) {
                        Move temp = possibleAction(board[i][j]);
                        if (temp.snapshots.size() != 0) {
                            whiteActions.add(temp);
                        }
                    } else if (board[i][j].color == 1) {
                        Move temp = possibleAction(board[i][j]);
                        if (temp.snapshots.size() != 0) {
                            blackActions.add(temp);
                        }
                    }
                }
            }
        }
        int maxWhite = 0;
        for (Move m : whiteActions) {
            for (Snapshot s : m.snapshots) {
                if (s.count > maxWhite) {
                    maxWhite = s.count;
                }
            }
        }
        for (Move m : whiteActions) {
            for (Snapshot s : m.snapshots) {
                if (s.count == maxWhite) {
                    whiteSnapshots.add(s);
                }
            }
        }
        for (Snapshot s : whiteSnapshots) {
            whiteMap.put(s.seq, s.board);
        }
        int maxBlack = 0;
        for (Move m : blackActions) {
            for (Snapshot s : m.snapshots) {
                if (s.count > maxBlack) {
                    maxBlack = s.count;
                }
            }
        }
        for (Move m : blackActions) {
            for (Snapshot s : m.snapshots) {
                if (s.count == maxBlack) {
                    blackSnapshots.add(s);
                }
            }
        }
        for (Snapshot s : blackSnapshots) {
            blackMap.put(s.seq, s.board);
        }
    }

    public Object action(Object o) {
        return null;
    }

    public Object result() {
        return null;
    }

    public Checker[][] state() {
        return board;
    }

    public int cost() {
        return 1;
    }

    public boolean goal() {
        if (whiteSnapshots.size() == 0 || blackSnapshots.size() == 0) {
            return true;
        }
        return false;
    }

    void print() {
        for (int i = 0; i <= board.length - 1; i++) {
            for (int j = 0; j <= board[i].length - 1; j++) {
                if (i == 0 && j == 0) System.out.print("  ");
                if (i == 0 && j > 0) System.out.print(j + " ");
                if (j == 0 && i > 0) System.out.print(convert.get(i) + "|");
                if (board[i][j] == null) {
                    if (i > 0 && j > 0) {
                        System.out.print(" |");
                    }
                } else if (board[i][j].color == 0) {
                    if (!board[i][j].isKing) System.out.print("w|");
                    else System.out.print("W|");
                } else if (board[i][j].color == 1) {
                    if (!board[i][j].isKing) System.out.print("b|");
                    else System.out.print("B|");
                }
            }
            System.out.println();
            for (int x = 0; x < board.length - 1; x++) {
                if (x == 0) System.out.print(" +");
                System.out.print("-+");
            }
            System.out.println();
        }
    }
}
