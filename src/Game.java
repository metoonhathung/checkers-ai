import java.util.Scanner;

public class Game {
    int n, size, user, agent, depth, side;
    boolean blackTurn;
    Table table;
    Player player;
    Player bot;

    void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Checkers by Hung Tran" +
                "\nChoose your game:" +
                "\n1. Small 4x4 Checkers" +
                "\n2. Standard 8x8 Checkers" +
                "\n3. Custom nxn Checkers");
        do {
            System.out.print("Your choice? ");
            size = scanner.nextInt();
        } while (size != 1 && size != 2 && size != 3);
        if (size == 3) {
            do {
                System.out.print("Choose n even > 2: ");
                n = scanner.nextInt();
            } while (n <= 2 || n % 2 == 1);
        }
        System.out.println("Choose your PLAYER 1:" +
                "\n0. Human player" +
                "\n1. An agent that plays randomly" +
                "\n2. An agent that uses MINIMAX" +
                "\n3. An agent that uses MINIMAX with alpha-beta pruning" +
                "\n4. An agent that uses H-MINIMAX with a fixed depth cutoff");
        do {
            System.out.print("Your choice? ");
            user = scanner.nextInt();
        } while (user != 0 && user != 1 && user != 2 && user != 3 && user != 4);
        System.out.println("Choose your PLAYER 2:" +
                "\n0. Human player" +
                "\n1. An agent that plays randomly" +
                "\n2. An agent that uses MINIMAX" +
                "\n3. An agent that uses MINIMAX with alpha-beta pruning" +
                "\n4. An agent that uses H-MINIMAX with a fixed depth cutoff");
        do {
            System.out.print("Your choice? ");
            agent = scanner.nextInt();
        } while (agent != 0 && agent != 1 && agent != 2 && agent != 3 && agent != 4);
        System.out.println("Choose your depth limit:");
        do {
            System.out.print("Your choice? ");
            depth = scanner.nextInt();
        } while (depth <= 0);
        System.out.println("Choose side:" +
                "\n1. Player 1 - BLACK | Player 2 - WHITE" +
                "\n2. Player 1 - WHITE | Player 2 - BLACK");
        do {
            System.out.print("Your choice? ");
            side = scanner.nextInt();
        } while (side != 1 && side != 2);
        if (user == 0) player = new Player();
        else if (user == 1) player = new RandomBot();
        else if (user == 2) player = new MinimaxBot();
        else if (user == 3) player = new ABMinimaxBot();
        else if (user == 4) player = new HMinimaxBot();
        if (agent == 0) bot = new Player();
        else if (agent == 1) bot = new RandomBot();
        else if (agent == 2) bot = new MinimaxBot();
        else if (agent == 3) bot = new ABMinimaxBot();
        else if (agent == 4) bot = new HMinimaxBot();
        if (side == 1) {
            player.color = 1;
            bot.color = 0;
        } else if (side == 2) {
            player.color = 0;
            bot.color = 1;
        }
        blackTurn = true;
        if (size == 1) {
            n = 4;
        } else if (size == 2) {
            n = 8;
        }
        table = new Table(n);
        table.initiate();
        table.print();
    }

    void gameLoop() {
        table.allActions();
        while (true) {
            double start = System.currentTimeMillis();
            /** black turn **/
            if (blackTurn) {
                System.out.println("Next to play: BLACK");
                System.out.println("BLACK available: " + table.blackMap.keySet());
                if (player.color == 1) {
                    if (!player.play(table, blackTurn, depth)) continue;
                } else {
                    if (!bot.play(table, blackTurn, depth)) continue;
                }
            }
            /** white turn **/
            if (!blackTurn) {
                System.out.println("Next to play: WHITE");
                System.out.println("WHITE available: " + table.whiteMap.keySet());
                if (player.color == 0) {
                    if (!player.play(table, blackTurn, depth)) continue;
                } else {
                    if (!bot.play(table, blackTurn, depth)) continue;
                }
            }
            double finish = System.currentTimeMillis();
            double elapsed = (finish - start) / 1000;
            System.out.println("Elapsed time: " + elapsed + " secs");
            table.print();
            blackTurn = !blackTurn;
            table.allActions();
            if (table.goal()) {
                System.out.print("GAME OVER. ");
                if (table.whiteSnapshots.size() == 0) {
                    System.out.println("BLACK WINS.");
                } else if (table.blackSnapshots.size() == 0) {
                    System.out.println("WHITE WINS.");
                }
                blackTurn = true;
                break;
            }
        }
    }
}
