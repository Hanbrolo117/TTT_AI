/**
 * Created by CptAmerica on 8/11/16.
 */
public class TicTacToeTester {

    /**
     * Main Function for testing the Players Perfection...?
     * @param args cmd arguments
     */
    public static void main(String[] args){
        int p1win = 0;
        int p2win = 0;
        for(int i=0; i < 3; i++){
            for(int j=0; j < 3; j++){
                Player p1 = new Player(0,1,true);
                p1.setInitMove(i,j);
                Player p2 = new Player(1,0,false);
                int[][] board = {{-1,-1,-1,},{-1,-1,-1},{-1,-1,-1}};
                int turn = 1;
                boolean player1Win = false;
                boolean player2Win = false;
                while((!isFull(board)) && (!player1Win) && (!player2Win) ) {
                    if (turn == 1) {
                        p1.playerMove(board);
                        turn = 2;
                    }else{
                        p2.playerMove(board);
                        turn = 1;
                    }

                    if(p1.isWin(board,p1.getPlayerToken())){
                        p1win++;
                        player1Win = true;
                    }else if(p2.isWin(board,p2.getPlayerToken())){
                        p2win++;
                        player2Win = true;
                    }
                }
                System.out.println();
                System.out.println("-------------------------------");
                if(player1Win){
                    System.out.println("Player 1 Wins");
                }
                else if(player2Win){
                    System.out.println("Player 2 Wins");
                }
                else {
                    System.out.println("Player 1 and 2 Draw!");
                }
                printXOBoard(board);
                System.out.println();
                System.out.println("-------------------------------");
            }
        }
        if((p1win==0)&&(p2win==0)){
            System.out.println("Neither Player 1 or 2 won in any game of all possible starting points. Thus they played as perfectly as they could.");
            System.out.println("Since Player1 is equivalent to Player 2, We only needed to test Player 1 in each of all possible starting positions. As the Player that\n" +
                    "goes next must make their move based off the player who went first.");
        }
    }

    /**
     * Checks if all the positions on the Current Game Board are taken.
     * @param board the current Game Board (Should be a 3x3 Matrix).
     * @return boolean True if the game board is indeed "Full", False otherwise.
     */
    public static boolean isFull(int[][] board){
        for(int i=0; i < board.length; i++){
            for(int j=0; j < board.length; j++){
                if(board[i][j] == -1)
                    return false;
            }
        }
        return true;
    }

    /**
     * Print the Current Game Board with proper Tic Tac Toe Symbols.
     * @param board the current state of the game Board. (Should be a 3x3 Matrix).
     */
    public static void printXOBoard(int[][] board){
        for(int i=0; i < board.length; i++){
            for(int j=0; j < board.length; j++){
                if(board[i][j]==1){
                    System.out.print("X\t");
                }
                else if(board[i][j]==0){
                    System.out.print("O\t");
                }else{
                    System.out.print("-\t");
                }
            }
            System.out.println();
        }
    }

}
