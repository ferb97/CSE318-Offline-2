public class Board {

    public static final int NUMBER_OF_PITS = 6;
    public static final int NUMBER_OF_BEADS = 4;

    private int[][] pits;
    private int[] storage;

    public Board(){
        storage = new int[2];
        pits = new int[2][NUMBER_OF_PITS];

        for(int i = 0; i < 2; i++){
            storage[i] = 0;
            for(int j = 0; j < NUMBER_OF_PITS; j++){
                pits[i][j] = NUMBER_OF_BEADS;
            }
        }
    }

    public int[][] getPits() {
        return pits;
    }

    public void setPits(int[][] pits) {
        this.pits = pits;
    }

    public int[] getStorage() {
        return storage;
    }

    public void setStorage(int[] storage) {
        this.storage = storage;
    }

    public void copyBoard(Board board){
        for(int i = 0; i < 2; i++){
            storage[i] = board.getStorage()[i];
            for(int j = 0; j < NUMBER_OF_PITS; j++){
                pits[i][j] = board.getPits()[i][j];
            }
        }
    }

    public int playMove(int playerId, int pitNumber){

        if(pitNumber < 0 || pitNumber >= NUMBER_OF_PITS || pits[playerId][pitNumber] == 0){
            return -1;
        }

        int remainingBeads = pits[playerId][pitNumber];
        pits[playerId][pitNumber] = 0;

        int opponent = 1 - playerId;
        int beadsAddition = remainingBeads / (NUMBER_OF_PITS * 2 + 1);
        int beadsLeft = remainingBeads % (NUMBER_OF_PITS * 2 + 1);

        for(int i = 0; i < NUMBER_OF_PITS; i++){
            pits[playerId][i] += beadsAddition;
            pits[opponent][i] += beadsAddition;
        }
        storage[playerId] += beadsAddition;

        if(remainingBeads == NUMBER_OF_PITS * 2 + 1){
           storage[playerId] += pits[opponent][NUMBER_OF_PITS - pitNumber - 1] + 1;
           pits[playerId][pitNumber] = 0;
           pits[opponent][NUMBER_OF_PITS - pitNumber - 1] = 0;
           return opponent;
        }

        for(int i = pitNumber + 1; i < NUMBER_OF_PITS; i++){
            pits[playerId][i]++;
            beadsLeft--;

            if(beadsLeft == 0){
                if(pits[playerId][i] == 1 && pits[opponent][NUMBER_OF_PITS - i - 1] > 0){
                    storage[playerId] += pits[opponent][NUMBER_OF_PITS - i - 1] + 1;
                    pits[opponent][NUMBER_OF_PITS - i - 1] = 0;
                    pits[playerId][i] = 0;
                }
                return opponent;
            }
        }

        storage[playerId]++;
        beadsLeft--;

        if(beadsLeft == 0){
            return playerId;
        }

        for(int i = 0; i < NUMBER_OF_PITS; i++){
            pits[opponent][i]++;
            beadsLeft--;

            if(beadsLeft  == 0){
               return opponent;
            }
        }

        for(int i = 0; i < pitNumber; i++){
            pits[playerId][i]++;
            beadsLeft--;

            if(beadsLeft == 0){
                if(pits[playerId][i] == 1 && pits[opponent][NUMBER_OF_PITS - i - 1] > 0){
                    storage[playerId] += pits[opponent][NUMBER_OF_PITS - i - 1] + 1;
                    pits[opponent][NUMBER_OF_PITS - i - 1] = 0;
                    pits[playerId][i] = 0;
                }
                return opponent;
            }
        }

        return opponent;
    }

    public boolean isGameOver(){
        if(storage[0] + storage[1] == 2 * NUMBER_OF_PITS * NUMBER_OF_BEADS){
           return true;
        }

        int beadsLeftPlayer0 = 0;
        int beadsLeftPlayer1 = 0;

        for(int i = 0; i < NUMBER_OF_PITS; i++){
            beadsLeftPlayer0 += pits[0][i];
            beadsLeftPlayer1 += pits[1][i];
        }

        if(beadsLeftPlayer0 == 0 || beadsLeftPlayer1 == 0){
           return true;
        }
        return false;
    }

    public int getWinner(){
        if(!isGameOver()){
           return -1;
        }

        int beadsPlayer0 = storage[0];
        int beadsPlayer1 = storage[1];

        for(int i = 0; i < NUMBER_OF_PITS; i++){
            beadsPlayer0 += pits[0][i];
            beadsPlayer1 += pits[1][i];
        }

        if(beadsPlayer0 > beadsPlayer1){
           return 0;
        }
        else if(beadsPlayer1 > beadsPlayer0){
           return 1;
        }
        else{
           return 2;
        }
    }

    public int getFinalStorage(int playerId){
        if(!isGameOver()){
           return -1;
        }

        int finalStorage = storage[playerId];

        for(int i = 0; i < NUMBER_OF_PITS; i++){
            finalStorage += pits[playerId][i];
        }
        return finalStorage;
    }

    public void printBoard(){
        //System.out.println("_________________________________________________________________________________________________");
        for(int i = 0; i < NUMBER_OF_PITS + 2; i++){
            System.out.print("------------");
        }
        System.out.println("-");
        System.out.print("|  Player1\t|");
        for(int i = NUMBER_OF_PITS - 1; i >= 0; i--){
            System.out.print("\tPit" + i + "\t|");
        }
        System.out.println("\t       \t|");

        System.out.print("|  Storage\t|");
        for(int i = 0; i < NUMBER_OF_PITS; i++){
            System.out.print("\t    \t|");
        }
        System.out.println("\t       \t|");

        System.out.print("|\t       \t|");
        for(int i = NUMBER_OF_PITS - 1; i >= 0; i--){
            System.out.print("\t  " + pits[1][i] + "  \t|");
        }
        System.out.println("\t       \t|");

        System.out.print("|\t       \t|");
        for(int i = 0; i < NUMBER_OF_PITS; i++){
            System.out.print("\t    \t|");
        }
        System.out.println("\t       \t|");

        System.out.print("|\t  " + storage[1] + "  \t|");
        for(int i = 0; i < NUMBER_OF_PITS; i++){
            System.out.print("-----------|");
        }
        System.out.println("\t  " + storage[0] + "  \t|");

        System.out.print("|\t       \t|");
        for(int i = 0; i < NUMBER_OF_PITS; i++){
            System.out.print("\t    \t|");
        }
        System.out.println("\t       \t|");

        System.out.print("|\t       \t|");
        for(int i = 0; i < NUMBER_OF_PITS; i++){
            System.out.print("\t  " + pits[0][i] + "  \t|");
        }
        System.out.println("\t       \t|");

        System.out.print("|\t      \t|");
        for(int i = 0; i < NUMBER_OF_PITS; i++){
            System.out.print("\t    \t|");
        }
        System.out.println("  Storage\t|");

        System.out.print("|\t      \t|");
        for(int i = 0; i < NUMBER_OF_PITS; i++){
            System.out.print("\tPit" + i + "\t|");
        }
        System.out.println("  Player0\t|");

        for(int i = 0; i < NUMBER_OF_PITS + 2; i++){
            System.out.print("------------");
        }
        System.out.println("-");
    }
}
