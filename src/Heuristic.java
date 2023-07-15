public class Heuristic {

    public static final int HEURISTIC_1 = 1;
    public static final int HEURISTIC_2 = 2;
    public static final int HEURISTIC_3 = 3;
    public static final int HEURISTIC_4 = 4;
    public static final int W1 = 5;
    public static final int W2 = 3;
    public static final int W3 = 6;
    public static final int W4 = 8;

    public static int heuristicStorage(int playerId, Board board){
        return board.getStorage()[playerId] - board.getStorage()[1 - playerId];
    }

    public static int heuristicStoragePits(int playerId, Board board){
        int playerPit = 0, opponentPit = 0;

        for(int i = 0; i < Board.NUMBER_OF_PITS; i++){
            playerPit += board.getPits()[playerId][i];
            opponentPit += board.getPits()[1 - playerId][i];
        }

        return W1 * heuristicStorage(playerId, board) + W2 * (playerPit - opponentPit);
    }

    public static int heuristicStoragePitsExtra(int playerId, int extraMoves, Board board){
        return heuristicStoragePits(playerId, board) + W3 * extraMoves;
    }

    public static int heuristicStoragePitsExtraCaptured(int playerId, int extraMoves, Board board, Board prevBoard){
        int capturedBeads = 0;

        for(int i = 0; i < Board.NUMBER_OF_PITS; i++){
            if(board.getPits()[playerId][i] == 0 && board.getPits()[1 - playerId][Board.NUMBER_OF_PITS - i - 1] == 0){
               if((prevBoard.getPits()[playerId][i] == 0 || prevBoard.getPits()[playerId][i] == 2 * Board.NUMBER_OF_PITS + 1) && prevBoard.getPits()[1 - playerId][Board.NUMBER_OF_PITS - i - 1] > 0){
                  capturedBeads += prevBoard.getPits()[1 - playerId][Board.NUMBER_OF_PITS - i - 1];
               }
            }
        }

        return heuristicStoragePitsExtra(playerId, extraMoves, board) + W4 * capturedBeads;
    }
}
