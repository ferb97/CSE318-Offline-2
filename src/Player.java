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
        System.out.print("Enter Move: ");
        int move = scn.nextInt();
        return move;
    }

    public int getPlayerMove(Board board){
        if(isHuman){
           return getHumanMove();
        }
        else{
           extraMoves = 0;
           return minimaxAlgortithmWithAlphaBetaPruning(board)
        }
    }
}
