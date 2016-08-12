import java.util.ArrayList;

/**
 * Created by CptAmerica on 8/11/16.
 */
public class Player {

    private int playerToken;
    private int opponentToken;
    private int[][] board;
    private boolean hasInitPos;
    private int initI;
    private int initJ;
    private boolean isFirstMove;
    private int moves;



    //Constructors & Initializer Functions:
    //---------------------------------------------------------------------------------------------------

    /**
     * This is the Player Constructor.
     * @param token the token that will be representing this Player on the game board.
     * @param optoken the token that will be representing the Opposing Player on the game board.
     * @param hasInitPos this boolean determines if this Player has an initial Position ability.
     */
    public Player(int token, int optoken, boolean hasInitPos){
        this.playerToken = token;
        this.opponentToken = optoken;
        this.hasInitPos = hasInitPos;
        this.isFirstMove = true;
        this.initI = 1;
        this.initJ = 1;
        this.moves=0;
    }

    /**
     * This Can only be used if the Player has an initial Position ability.
     * @param i the ith row of the game board (a 3x3 Matrix).
     * @param j the jth column of the game board (a 3x3 Matrix).
     */
    public void setInitMove(int i, int j){
        if(this.hasInitPos) {
            this.initI = i;
            this.initJ = j;
        }
    }
    //---------------------------------------------------------------------------------------------------



    //Player Logic Algorithm(s):
    //---------------------------------------------------------------------------------------------------
    /**
     * The Function that leverages the minmaxPredictor function, along with some additional logic
     * to determine the best possible move for this player. The Goal is to Either Win or Draw, but
     * never to fail/lose.
     * @param board the current state of the game board (a 3x3 integer Matrix)
     */
    public void playerMove(int[][] board){

        //This is for All cases testing purposes
        if((this.hasInitPos) && (board[this.initI][this.initJ] == -1)){
            if(this.isFirstMove) {
                board[this.initI][this.initJ] = this.getPlayerToken();
                this.isFirstMove = false;
                return;
            }
        }
        //If Next move is win, take it
        //Else if Next opponent move is win, steal it
        //else, determine next best move:
        ArrayList<MovePoint> mps = new ArrayList<MovePoint>();
        for(int i=0; i < board.length; i++){
            for(int j=0; j < board[0].length; j++){
                if(board[i][j] == -1){
                    board[i][j] = this.playerToken;
                    if(this.isWin(board,this.playerToken)){
                        this.moves++;
                        return;
                    }else{
                        board[i][j] = this.opponentToken;
                        if(this.isWin(board,this.opponentToken)){
                            board[i][j] = this.playerToken;
                            this.moves++;
                            return;
                        }else{
                            board[i][j] = -1;
                        }
                    }
                    MovePoint mp = new MovePoint(i,j,this.minmaxPredictor(board,0,true));
                    mps.add(mp);
                }
            }
        }

        //Tic Tac Toe Techniques:

        //Special Case 1: Inital Move, all possible cells have same winning potential at this point.
        if((board[1][2]==this.getOpponentToken())&&(this.moves==0)&&(board[0][2]==-1)){
            this.moves++;
            board[0][2] = this.playerToken;
            return;
        }

        //Attempt to take control of Board:
        if(board[1][1] == -1){
            board[1][1] = this.getPlayerToken();
            this.moves++;
            return;
        }

        //Otherwise Choose the best potential point on the board to win as soon as possible:
        MovePoint best = mps.get(0);
        for(int i=0; i < mps.size(); i++ ){
            if(mps.get(i).getValue() > best.getValue()){
                best = mps.get(i);
            }
        }

        this.moves++;//Increment Moves
        board[best.getI()][best.getJ()] = this.playerToken;
    }

    /**
     * Recursive thinking algorithm (based on minMax concept):
     * @param board The current state Game Board (3x3 matrix)
     * @param depth The current Recursion Depth
     * @param isPlayerTurn The Hypothetical Player Turn
     * @return The value of this move
     */
    public int minmaxPredictor(int[][] board, int depth, boolean isPlayerTurn){
        if(this.isWin(board,this.opponentToken)){
            return depth -10;//Lost
        }
        else if(!this.isWin(board,this.playerToken)){
            return 10 - depth;
        }else{
            if(isFull(board)){
                return 0;
            }else{
                int returner = 0;
                for(int i=0; i < board.length;i++){
                    for(int j=0; j < board[0].length; j++){
                        if(board[i][j] == -1){
                            int val;
                            if(isPlayerTurn){
                                board[i][j] = this.getPlayerToken();
                                val = this.minmaxPredictor(board,depth++,false);
                            }else{
                                board[i][j] = this.getOpponentToken();
                                val = this.minmaxPredictor(board,depth++,false);
                            }
                            board[i][j] = -1;
                            return (val > returner) ? val : returner;
                        }
                    }
                }
            }
        }
        return 0;
    }
    //---------------------------------------------------------------------------------------------------



    //Checking Functions:
    //---------------------------------------------------------------------------------------------------
    /**
     * Determine whether a given player wins on a given game Board (3x3 matrix)
     * @param board the current state of the game board (3x3 matrix)
     * @param plyr the integer token the player is represented by
     * @return boolean whether the given player has won on the given game board (3x3 matrix)
     */
    public boolean isWin(int[][] board, int plyr){
        if( ((board[0][0]==plyr)&&(board[0][1]==plyr)&&(board[0][2]==plyr))
        || ((board[1][0]==plyr)&&(board[1][1]==plyr)&&(board[1][2]==plyr))
        || ((board[2][0]==plyr)&&(board[2][1]==plyr)&&(board[2][2]==plyr)))
            return true;

        if( ((board[0][0]==plyr)&&(board[1][0]==plyr)&&(board[2][0]==plyr))
                || ((board[0][1]==plyr)&&(board[1][1]==plyr)&&(board[2][1]==plyr))
                || ((board[0][2]==plyr)&&(board[1][2]==plyr)&&(board[2][2]==plyr)))
            return true;

        if( ((board[0][0]==plyr)&&(board[1][1]==plyr)&&(board[2][2]==plyr))
                || ((board[2][0]==plyr)&&(board[1][1]==plyr)&&(board[0][2]==plyr)))
            return true;
        return false;
    }

    /**
     * Determines if the GameBoard is completed (all cells, board positions are occupied by players)
     * @param board the Current state of the game board (3x3 matrix)
     * @return boolean whether the current state of the matrix is full.
     */
    private boolean isFull(int[][] board){
        for(int i=0; i < board.length; i++){
            for(int j=0; j < board.length; j++){
                if(board[i][j] == -1)
                    return false;
            }
        }
        return true;
    }

    /**
     * Gets the integer token that represents this Player.
     * @return the integer token that represents this Player.
     */
    public int getPlayerToken() {
        return playerToken;
    }

    /**
     * Gets the integer token that represents the Opposing Player.
     * @return the integer token that represents the Opposing Player.
     */
    public int getOpponentToken() {
        return opponentToken;
    }
    //---------------------------------------------------------------------------------------------------
}
