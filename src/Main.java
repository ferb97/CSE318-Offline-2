import java.util.Scanner;

public class Main {

    public static final int NUMBER_OF_PITS = 6;
    public static final int NUMBER_OF_BEADS = 4;
    public static final int PLAYER0_DEPTH = 6;
    public static final int PLAYER1_DEPTH = 6;
    public static final int PLAYER0_HEURISTIC = 4;
    public static final int PLAYER1_HEURISTIC = 4;

    public static void main(String[] args) {
        Board board = new Board();
        System.out.println("Initial Board: ");
        board.printBoard();

        int playerTurn = 0, option;
        Scanner scn = new Scanner(System.in);

        System.out.println("1 - Human vs Human");
        System.out.println("2 - AI vs AI");
        System.out.println("3 - Human vs AI");
        System.out.println("4 - AI vs Human");
        while(true){
            System.out.print("Enter an option: ");
            try{
               option = scn.nextInt();
               if(option == 1 || option == 2 || option == 3 || option == 4)
                 break;
               else{
                   System.out.println("Invalid Input");
               }
            }
            catch(Exception e){
                System.out.println("Invalid Input");
            }
        }

        Player player0 = new Player(false, 0, PLAYER0_HEURISTIC, PLAYER0_DEPTH, 0);
        Player player1 = new Player(false, 1, PLAYER1_HEURISTIC, PLAYER1_DEPTH, 0);

        if(option == 1){
           player0.setHuman(true);
           player1.setHuman(true);
        }
        else if(option == 3){
           player0.setHuman(true);
        }
        else if(option == 4){
           player1.setHuman(true);
        }

        Board prevBoard = new Board();
        prevBoard.copyBoard(board);
        while(!board.isGameOver()){
            System.out.println("Player" + playerTurn + "'s Turn");
            System.out.print("Enter Pit Number: ");
            int pitNumber;
            if(playerTurn == 0){
               pitNumber = player0.getPlayerMove(board, prevBoard);
            }
            else{
               pitNumber = player1.getPlayerMove(board, prevBoard);
            }
            //int pitNumber = scn.nextInt();
            int nextTurn = board.playMove(playerTurn, pitNumber);
            if(nextTurn == -1){
                System.out.println("Invalid Move. Try Again.");
                continue;
            }
            System.out.println("Player" + playerTurn + " will make his move on pit number: " + pitNumber);
            playerTurn = nextTurn;
            board.printBoard();
            prevBoard.copyBoard(board);
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
