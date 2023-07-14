import java.util.Scanner;

public class Main {

    public static final int NUMBER_OF_PITS = 6;
    public static final int NUMBER_OF_BEADS = 4;

    public static void main(String[] args) {
        Board board = new Board();
        System.out.println("Initial Board: ");
        board.printBoard();

        int playerTurn = 0;
        Scanner scn = new Scanner(System.in);

        while(!board.isGameOver()){
            System.out.println("Player" + playerTurn + "'s Turn");
            System.out.print("Enter Pit Number: ");
            int pitNumber = scn.nextInt();
            int nextTurn = board.playMove(playerTurn, pitNumber);
            if(nextTurn == -1){
                System.out.println("Invalid Move. Try Again.");
                continue;
            }
            playerTurn = nextTurn;
            board.printBoard();
        }

        System.out.println("Game Finished");
        System.out.println("Player0's Storage: " + board.getFinalStorage(0));
        System.out.println("Player1's Storage: " + board.getFinalStorage(1));
        if(board.getWinner() == 2){
            System.out.println("Match Drawn");
        }
        else{
            System.out.println("Winner: Player" + board.getWinner());
        }
    }
}
