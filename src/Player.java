import java.util.Scanner;

public class Player {

    private boolean isHuman;
    private int id;
    private int heuristic;
    private int searchDepth;
    private int extraMoves;

    public Player(boolean isHuman, int id, int heuristic, int searchDepth, int extraMoves) {
        this.isHuman = isHuman;
        this.id = id;
        this.heuristic = heuristic;
        this.searchDepth = searchDepth;
        this.extraMoves = extraMoves;
    }

    public boolean isHuman() {
        return isHuman;
    }

    public void setHuman(boolean human) {
        isHuman = human;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    public int getSearchDepth() {
        return searchDepth;
    }

    public void setSearchDepth(int searchDepth) {
        this.searchDepth = searchDepth;
    }

    public int getExtraMoves() {
        return extraMoves;
    }

    public void setExtraMoves(int extraMoves) {
        this.extraMoves = extraMoves;
    }

    public int getHeuristicCost(Board board, Board prevBoard){
        if(heuristic == Heuristic.HEURISTIC_1){
           return Heuristic.heuristicStorage(id, board);
        }
        else if(heuristic == Heuristic.HEURISTIC_2){
            return Heuristic.heuristicStoragePits(id, board);
        }
        else if(heuristic == Heuristic.HEURISTIC_3){
            return Heuristic.heuristicStoragePitsExtra(id, extraMoves, board);
        }
        else if(heuristic == Heuristic.HEURISTIC_4){
            return Heuristic.heuristicStoragePitsExtraCaptured(id, extraMoves, board, prevBoard);
        }
        else{
            return 0;
        }
    }

    public int getHumanMove(){
        Scanner scn = new Scanner(System.in);
        int move;
        while(true) {
            try {
                System.out.print("Enter Pit Number: ");
                move = scn.nextInt();
                break;
            }
            catch(Exception e){
                System.out.println("Invalid Move. Try Again.");
                scn.nextLine();
            }
        }
        return move;
    }

    public int minimaxAlgorithmWithAlphaBetaPruning(Board board, Board prevBoard, boolean isMaxMode, int searchDepthRemaining, int alpha, int beta){

        if(board.isGameOver()){
           if(board.getWinner() == id){
              return Integer.MAX_VALUE / 2;
           }
           else if(board.getWinner() == 1 - id){
              return Integer.MIN_VALUE / 2;
           }
           else{
              return getHeuristicCost(board, prevBoard);
           }
        }

        if(searchDepthRemaining == 0){
           return getHeuristicCost(board, prevBoard);
        }

        Board oldBoard = new Board();
        oldBoard.copyBoard(board);

        if(isMaxMode){
           int bestMove = -1, maxValue = Integer.MIN_VALUE;

           for(int i = 0; i < Board.NUMBER_OF_PITS; i++){
               if(board.getPits()[id][i] > 0){
                  int nextPlayer = board.playMove(id, i);
                  int currentValue;

                  if(nextPlayer == id){
                     extraMoves++;
                     currentValue = minimaxAlgorithmWithAlphaBetaPruning(board, oldBoard, true, searchDepthRemaining - 1, alpha, beta);
                     extraMoves--;
                  }
                  else{
                      currentValue = minimaxAlgorithmWithAlphaBetaPruning(board, oldBoard, false, searchDepthRemaining - 1, alpha, beta);
                  }

                  if(currentValue > maxValue){
                     maxValue = currentValue;
                     bestMove = i;
                  }

                  if(maxValue > alpha){
                     alpha = maxValue;
                  }
                  if(beta <= alpha){
                     break;
                  }
                  board.copyBoard(oldBoard);
               }
           }

           if(searchDepthRemaining == searchDepth){
              return bestMove;
           }
           else{
              return maxValue;
           }
        }

        else{
            int minValue = Integer.MAX_VALUE;

            for(int i = 0; i < Board.NUMBER_OF_PITS; i++){
                if(board.getPits()[1 - id][i] > 0){
                    int nextPlayer = board.playMove(1 - id, i);
                    int currentValue;

                    if(nextPlayer == 1 - id){
                        extraMoves--;
                        currentValue = minimaxAlgorithmWithAlphaBetaPruning(board, oldBoard, false, searchDepthRemaining - 1, alpha, beta);
                        extraMoves++;
                    }
                    else{
                        currentValue = minimaxAlgorithmWithAlphaBetaPruning(board, oldBoard, true, searchDepthRemaining - 1, alpha, beta);
                    }

                    if(currentValue < minValue){
                        minValue = currentValue;
                    }

                    if(minValue < beta){
                        beta = minValue;
                    }
                    if(beta <= alpha){
                        break;
                    }
                    board.copyBoard(oldBoard);
                }
            }

            return minValue;
        }
    }

    public int getPlayerMove(Board board, Board prevBoard){
        if(isHuman){
           return getHumanMove();
        }
        else{
           extraMoves = 0;
           return minimaxAlgorithmWithAlphaBetaPruning(board, prevBoard, true, searchDepth, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
    }
}
