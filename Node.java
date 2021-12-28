import java.util.*;
public class Node {

    //deep copy of the configuaration of the board
    private Board board;

    //possible moves player can make
    private ArrayList<Move> moves;

    //the player on this state
    private PlayerSide player;

    //the value of a cut-off node, higher utility favors COMPUTER
    private double utility;

    //move to get to this state
    private Move move;

    
    //is this node the COMPUTER or HUMAN
    private boolean isComp;

    //cut-off search at this depth
    private final int max_depth = 5;
    //normal piece weight in eval function
    private final double normalW = 1.3;
    //king weight
    private final double kingW = 2;

    

    private static final Direction[] HUMAN_DIRECTIONS = new Direction[2];
    private static final Direction[] COMP_DIRECTIONS = new Direction[2];
    private static final Direction[] KING_DIRECTIONS = new Direction[4];

    
    /**
     * Makes a new node given the config of the board, list of moves, and player
     * @param board deep copy of the config of the board
     * @param moves list of moves 
     * @param isComp if this is true, player is the computer, flase otherwise
     */
    /*
    public Node(Board board, ArrayList<Move> moves, boolean isComp) {
        //make a deep copy

        this.moves = new ArrayList<Move>(moves);
        this.board = new Board(board);

        this.isComp = isComp;
        if(isComp)
            player = PlayerSide.COMPUTER;
        else player = PlayerSide.HUMAN;

    }
    */

    public Node(Board board, boolean isComp, Move move) {
        this.board = new Board(board);
        this.moves = new ArrayList<Move>();
        this.move = new Move(move);
        this.isComp = isComp;

        if(isComp)
            player = PlayerSide.COMPUTER;
        else player = PlayerSide.HUMAN;

    }

    public Node(Board board, boolean isComp) {
        this.board = new Board(board);
        this.moves = new ArrayList<Move>();
     
        this.isComp = isComp;
        

        if(isComp)
            player = PlayerSide.COMPUTER;
        else player = PlayerSide.HUMAN;

    }

    /**
     * Determines if the state is a terminal state.
     * @return true if it is a terminal node (game is over), false otherwise.
     */
    public boolean isCutOff(Node newNode, int depth) {
        if(newNode.getBoard().isGameOver() || newNode.getMoves().size() == 0 || depth >= max_depth)
            return true;
    

        return false;
    }

    public Move MinMaxSearch() {
        Move move;
        double alpha = -10000;
        double beta = 10000;
        int depth = 0;
        //System.out.println("**INSIDE MINMAX**");
        
        move = this.max_value(this, alpha, beta, depth);

        return move;

    }

    public Move max_value(Node newNode, double alpha, double beta, int depth) {

        ArrayList<Board> copyBoards = new ArrayList<Board>();
        //negative infinity, a very low value that the utility function will never generate 
        Move move = new Move(-1000000);
        //some arbitrary value
        Move move2 = new Move(0);
        System.out.println("**MOVE TO GET TO THIS NODE: " + newNode.getDestMove());

        depth++;

        this.isComp = !isComp;

        newNode.generateMoves(newNode);
        
        

        System.out.println("**MAX B4 TERMINAL");
        System.out.println("TERMINAL?" + isCutOff(newNode, depth));
        System.out.println("**CUR DEPTH" + depth);
        //System.out.println("RESULTING MOVES: ");
        //System.out.println(newNode.getMoves());
        

        if(newNode.isCutOff(newNode, depth)) {
            newNode.setUtility(newNode.utility(newNode));
            newNode.getDestMove().setValue(newNode.getUtility());
           
            return newNode.getDestMove();

        }

        
        //System.out.println("**MAX");
        //System.out.println("AI MOVES");
        //System.out.println(newNode.getMoves());
        System.out.println("*DEST MOVE: " + newNode.getDestMove());
        for(int i = 0; i < newNode.getMoves().size(); i++) {
            copyBoards.add(new Board(newNode.getBoard()));

            System.out.println("**COPY BOARD AT DEPTH " + depth +" BEFORE EXECUTION OF MOVE***");
            copyBoards.get(i).display();

            System.out.println("**VALID MOVES: " + newNode.getMoves());
            System.out.println("**PIECES OF THIS BOARD:" + newNode.getBoard().getHumPieces() + newNode.getBoard().getCompPieces());
            
            

            //System.out.println("**copy boards size**" + copyBoards.size() + "i: " + i);

            copyBoards.get(i).executeMove(newNode.getMoves().get(i));
            
            System.out.println("**AFTER MOVE EXECUTION**");
            copyBoards.get(i).display();
            //System.out.println(copyBoards.get(i).getHumPieces());
            //System.out.println(copyBoards.get(i).getCompPieces());
            System.out.println("** CUR MOV BEING PASSED TO CONST (move executed): " + newNode.getMoves().get(i));

            if(newNode.getDestMove() != null)
                newNode.getMoves().get(i).setParent(newNode.getDestMove());

            move2 = newNode.min_value(new Node(copyBoards.get(i), isComp, newNode.getMoves().get(i)), alpha, beta, depth);

            if(move2.getValue() > move.getValue()) {
                move = new Move(move2);

                if(move.getValue() > alpha)
                    alpha = move.getValue();
            }

            if(move.getValue() >= beta)
                return move;


        }

        return move;


    }

    public Move min_value(Node newNode, double alpha, double beta, int depth) {
        
        ArrayList<Board> copyBoards = new ArrayList<Board>();
        //positive infinity , a big value that will never be generated by evaluation function
        Move move = new Move(1000000);
        //some arbitrary value
        Move move2 = new Move(0);
        System.out.println("**MOVE TO GET TO THIS NODE: " + newNode.getDestMove());

        newNode.generateMoves(newNode);

        depth++;
        this.isComp = !isComp;

        System.out.println("**MIN V4 TERMINAL");
        System.out.println("TERMINAL?" + isCutOff(newNode, depth));
        System.out.println("**CUR DEPTH" + depth);
        //System.out.println("RESULTING MOVES: ");
        //System.out.println(newNode.getMoves());

        if(newNode.isCutOff(newNode, depth)) {
            newNode.setUtility(newNode.utility(newNode));
            newNode.getDestMove().setValue(newNode.getUtility());
          
            return newNode.getDestMove();

        }

        
        //System.out.println("**MIN");
        //System.out.println("AI MOVES");
        //System.out.println(newNode.getMoves());
        System.out.println("*DEST MOVE: " + newNode.getDestMove());
        for(int i = 0; i < newNode.getMoves().size(); i++) {
            copyBoards.add(new Board(newNode.getBoard()));
            //System.out.println("**copy boards size**" + copyBoards.size() + "i: " + i);
            System.out.println("**COPY BOARD AT DEPTH " + depth +" BEFORE EXECUTION OF MOVE***");
            copyBoards.get(i).display();
            System.out.println("**VALID MOVES: " + newNode.getMoves());
            System.out.println("**PIECES OF THIS BOARD:" + newNode.getBoard().getHumPieces() + newNode.getBoard().getCompPieces());

            

            copyBoards.get(i).executeMove(newNode.getMoves().get(i));
            System.out.println("**AFTER MOVE EXECUTION**");
            copyBoards.get(i).display();
            //System.out.println(copyBoards.get(i).getHumPieces());
            //System.out.println(copyBoards.get(i).getCompPieces());
            
            System.out.println("** CUR MOV BEING PASSED TO CONST (executed): " + newNode.getMoves().get(i));

            if(newNode.getDestMove() != null)
                newNode.getMoves().get(i).setParent(newNode.getDestMove());
                
            move2 = newNode.max_value(new Node(copyBoards.get(i), isComp, newNode.getMoves().get(i)), alpha, beta, depth);
            //System.out.println("MOVE2: " + move2);
            if(move2.getValue() < move.getValue()) {
                move = new Move(move2);

                if(move.getValue() < beta)
                    beta = move.getValue();
            }

            if(move.getValue() <= alpha)
                return move;


        }

        return move;


    }

    public double utility(Node newNode) {
        double utility;
        double pieceDifference;

        //newNode.generateMoves(newNode);

        /*First, check if node is a terminal state where either HUMAN or COMPUTER wins. */
        if(newNode.getBoard().getHumPieces().size() == 0 || (player == PlayerSide.HUMAN && newNode.getMoves().size() == 0))
            utility = 90;
        else if(newNode.getBoard().getCompPieces().size() == 0 || (player == PlayerSide.COMPUTER && moves.size() == 0))
            utility = -90;
        else {

            /*Evaluate if it is not a terminal node with the heuristic */
            int compPieceScore = 0;
            int humanPieceScore = 0;

            for(int i = 0; i < newNode.getBoard().getCompPieces().size(); i++) {
                if(newNode.getBoard().getCompPieces().get(i).isKing())
                    compPieceScore += kingW;
                else compPieceScore += normalW;

            }

            for(int i = 0; i < newNode.getBoard().getHumPieces().size(); i++) {
                if(newNode.getBoard().getHumPieces().get(i).isKing())
                    humanPieceScore += kingW;
                else humanPieceScore += normalW;

            }
            //more pieces/kings puts you at an advantageous position
            pieceDifference = compPieceScore - humanPieceScore;



            for(int i = 3; i <= 4; i++) {
                for(int j = 0; j < 8; j++) {
                    
                }
            }


            utility = pieceDifference;
            System.out.println("**UTILITY: " + utility);



        }
        return utility;
    }

    public static void setDirections() {
        HUMAN_DIRECTIONS[0] = Direction.UPLEFT;
        HUMAN_DIRECTIONS[1] = Direction.UPRIGHT;

        COMP_DIRECTIONS[0] = Direction.DOWNLEFT;
        COMP_DIRECTIONS[1] = Direction.DOWNRIGHT;

        KING_DIRECTIONS[0] = Direction.UPLEFT;
        KING_DIRECTIONS[1] = Direction.UPRIGHT;
        KING_DIRECTIONS[2] = Direction.DOWNLEFT;
        KING_DIRECTIONS[3] = Direction.DOWNRIGHT;


    }

    public void initPieces(ArrayList<Piece> humanPieces, ArrayList<Piece> compPieces,Node newNode) {
        for(int i = 0; i < 8; i++) {

            for(int j = 0; j < 8; j++) {
              if(newNode.getBoard().getBoard()[i][j].getPiece() != null) { 
                if(newNode.getBoard().getBoard()[i][j].getPiece().getSide()== PlayerSide.HUMAN)
                    humanPieces.add(newNode.getBoard().getBoard()[i][j].getPiece());
                else compPieces.add(newNode.getBoard().getBoard()[i][j].getPiece());
              } 
            
            }
        }
    }
    public void generateMoves(Node newNode) {
        boolean canJump = false;
        boolean jumpPossible = false;
        Piece jumpingPiece;
        ArrayList<Piece> compPieces = new ArrayList<Piece>();
        ArrayList<Piece> humanPieces = new ArrayList<Piece>();
        int ind, ind2;

        ArrayList<Direction> jumpDirections = new ArrayList<Direction>();

        //System.out.println("**BOARD IN GENERATE MOVES NODE METHOD**");
        //newNode.getBoard().display();

        //System.out.println(compPieces);
        //System.out.println(humanPieces);
       
        //System.out.println(newNode.getMoves());
    

        setDirections();
        //System.out.println("BOARD BEFORE GENERATING MOVES: " );
        //newNode.getBoard().display();

        newNode.initPieces(humanPieces, compPieces, newNode);

        newNode.getBoard().setPieces(humanPieces, compPieces);

        jumpPossible = false;

        //double check if list of pieces is CORRECT.

        //System.out.println("**PIECES IN ACTUAL BOARD OBJECT**");
        for(int i = 0; i < 8; i++) {

            for(int j = 0; j < 8; j++) {
                if(newNode.getBoard().getBoard()[i][j].getPiece() != null) {
                    //System.out.println(newNode.getBoard().getBoard()[i][j].getPiece());

                    ind = humanPieces.indexOf(newNode.getBoard().getBoard()[i][j].getPiece());
                    ind2 = compPieces.indexOf(newNode.getBoard().getBoard()[i][j].getPiece());

                    //System.out.println("VALUE OF INDICES: " + ind + ind2);
                    if(ind == -1 && ind2 == -1) {
                        humanPieces.remove(newNode.getBoard().getBoard()[i][j].getPiece());
                    }
                }

            }
        }

        //System.out.println(compPieces);
        //System.out.println(humanPieces);

            

            if(newNode.player == PlayerSide.HUMAN) {

                
                for(int i = 0; i < humanPieces.size(); i++) {

                    if(humanPieces.get(i).isKing() == false) {

                        for(int j = 0; j < HUMAN_DIRECTIONS.length; j++) {
                            if(board.checkStandardMove(humanPieces.get(i), HUMAN_DIRECTIONS[j]))
                                newNode.getMoves().add(new Move(MoveType.STANDARD, HUMAN_DIRECTIONS[j], humanPieces.get(i)));

                            else if(board.checkJumpMove(humanPieces.get(i), HUMAN_DIRECTIONS[j])) {
                                canJump = true;
                                jumpPossible = true;

                                jumpDirections.add(HUMAN_DIRECTIONS[j]);

                                Board boardCopy = new Board(board);


                                //System.out.println("*BOARD IN JUMPING PIECE*");
                               // boardCopy.display();

                                //keep a reference to jumping piece in copy boared for easy access
                                jumpingPiece = boardCopy.getBoard()[humanPieces.get(i).getRow()][humanPieces.get(i).getCol()].getPiece(); 
                                //System.out.println("**JUMPING PIECE!" + humanPieces.get(i));
                                boardCopy.jumpMove(jumpingPiece, HUMAN_DIRECTIONS[j]);

                                while(canJump) {
                                    int k;

                                    canJump = false;

                                    for(k = 0;k < HUMAN_DIRECTIONS.length; k++) {
                                        //for now assume that there is only 1 path
                                        if(boardCopy.checkJumpMove(jumpingPiece, HUMAN_DIRECTIONS[k])) {
                                            canJump = true;
                                            jumpDirections.add(HUMAN_DIRECTIONS[k]);
                                            break;
                                        }
                                    }

                                    if(canJump) {
                                        boardCopy.jumpMove(jumpingPiece, HUMAN_DIRECTIONS[k]);
                                    }


                                }

                                newNode.getMoves().add(new Move(MoveType.JUMP, jumpDirections, humanPieces.get(i)));

                                jumpDirections.clear();



                            }

                            
                            

                        }
                        
                    } else {
                        //for king
                        for(int j = 0; j < KING_DIRECTIONS.length; j++) {
                            if(board.checkStandardMove(humanPieces.get(i), KING_DIRECTIONS[j]))
                                newNode.getMoves().add(new Move(MoveType.STANDARD, KING_DIRECTIONS[j], humanPieces.get(i)));

                            else if(board.checkJumpMove(humanPieces.get(i), KING_DIRECTIONS[j])) {
                                canJump = true;
                                jumpPossible = true;
                                jumpDirections.add(KING_DIRECTIONS[j]);

                                Board boardCopy = new Board(board);

                                //keep a reference to jumping piece in copy boared for easy access
                                jumpingPiece = boardCopy.getBoard()[humanPieces.get(i).getRow()][humanPieces.get(i).getCol()].getPiece(); 

                                boardCopy.jumpMove(jumpingPiece, KING_DIRECTIONS[j]);

                                while(canJump) {
                                    int k;

                                    canJump = false;

                                    for(k = 0;k < KING_DIRECTIONS.length; k++) {
                                        //for now assume that there is only 1 path
                                        if(boardCopy.checkJumpMove(jumpingPiece, KING_DIRECTIONS[k])) {
                                            canJump = true;
                                            jumpDirections.add(KING_DIRECTIONS[k]);
                                            break;
                                        }
                                    }

                                    if(canJump) {
                                        boardCopy.jumpMove(jumpingPiece, KING_DIRECTIONS[k]);
                                    }


                                }

                                newNode.getMoves().add(new Move(MoveType.JUMP, jumpDirections, humanPieces.get(i)));

                                jumpDirections.clear();



                            }

                            
                            

                        }

                        

                    }
                    
                }
            } else {
            //generate valid moves for computer    
                for(int i = 0; i < compPieces.size(); i++) {

                    if(compPieces.get(i).isKing() == false) {

                        for(int j = 0; j < COMP_DIRECTIONS.length; j++) {
                            if(board.checkStandardMove(compPieces.get(i), COMP_DIRECTIONS[j]))
                                newNode.getMoves().add(new Move(MoveType.STANDARD, COMP_DIRECTIONS[j], compPieces.get(i)));

                            else if(board.checkJumpMove(compPieces.get(i), COMP_DIRECTIONS[j])) {
                                canJump = true;
                                jumpPossible = true;

                                jumpDirections.add(COMP_DIRECTIONS[j]);

                                Board boardCopy = new Board(board);

                                //keep a reference to jumping piece in copy boared for easy access
                                jumpingPiece = boardCopy.getBoard()[compPieces.get(i).getRow()][compPieces.get(i).getCol()].getPiece(); 

                                boardCopy.jumpMove(jumpingPiece, COMP_DIRECTIONS[j]);

                                while(canJump) {
                                    int k;

                                    canJump = false;

                                    for(k = 0;k < COMP_DIRECTIONS.length; k++) {
                                        //for now assume that there is only 1 path
                                        if(boardCopy.checkJumpMove(jumpingPiece, COMP_DIRECTIONS[k])) {
                                            canJump = true;
                                            jumpDirections.add(COMP_DIRECTIONS[k]);
                                            break;
                                        }
                                    }

                                    if(canJump) {
                                        boardCopy.jumpMove(jumpingPiece, COMP_DIRECTIONS[k]);
                                    }


                                }

                                newNode.getMoves().add(new Move(MoveType.JUMP, jumpDirections, compPieces.get(i)));

                                jumpDirections.clear();



                            }

                            
                            

                        }
                        
                    } else {

                        //for king
                        for(int j = 0; j < KING_DIRECTIONS.length; j++) {
                            if(board.checkStandardMove(compPieces.get(i), KING_DIRECTIONS[j]))
                                newNode.getMoves().add(new Move(MoveType.STANDARD, KING_DIRECTIONS[j], compPieces.get(i)));

                            else if(board.checkJumpMove(compPieces.get(i), KING_DIRECTIONS[j])) {
                                canJump = true;
                                jumpPossible = true;
                                jumpDirections.add(KING_DIRECTIONS[j]);

                                Board boardCopy = new Board(board);

                                //keep a reference to jumping piece in copy boared for easy access
                                jumpingPiece = boardCopy.getBoard()[compPieces.get(i).getRow()][compPieces.get(i).getCol()].getPiece(); 

                                boardCopy.jumpMove(jumpingPiece, KING_DIRECTIONS[j]);

                                while(canJump) {
                                    int k;

                                    canJump = false;

                                    for(k = 0;k < KING_DIRECTIONS.length; k++) {
                                        //for now assume that there is only 1 path
                                        if(boardCopy.checkJumpMove(jumpingPiece, KING_DIRECTIONS[k])) {
                                            canJump = true;
                                            jumpDirections.add(KING_DIRECTIONS[k]);
                                            break;
                                        }
                                    }

                                    if(canJump) {
                                        boardCopy.jumpMove(jumpingPiece, KING_DIRECTIONS[k]);
                                    }


                                }

                                newNode.getMoves().add(new Move(MoveType.JUMP, jumpDirections, compPieces.get(i)));

                                jumpDirections.clear();



                            }

                            
                            

                        }
                        
                    }
                    
                }

            }

            /*Remove all non jumps, because a jump MUST be made if at least one jump is possible */
            if(jumpPossible == true) {

                /*System.out.println("*IM INSIDE JUMPOS*");
                System.out.println("SIZE OF ARRAYLISTS:" + humanMoves.size() + compMoves.size());
                */
                if(newNode.player == PlayerSide.HUMAN) {

                    for(int h = 0; h < moves.size(); h++) {
                        if(newNode.getMoves().get(h).getType() == MoveType.STANDARD) {
                            newNode.getMoves().remove(h);
                            --h;
                        }
                    }

                } else {

                    for(int h = 0; h < moves.size(); h++) {
                        if(newNode.getMoves().get(h).getType() == MoveType.STANDARD) {
                            newNode.getMoves().remove(h);
                            --h;
                        }
                    }

                }

            }

            //System.out.println("BOARD AFTER!! GENERATING MOVES: " );
            //newNode.getBoard().display();

            //System.out.println("RESULTING MOVES: ");
            //System.out.println(newNode.getMoves());
    }

    public Board getBoard() {
        return this.board;
    }

    public ArrayList<Move> getMoves() {
        return this.moves;
    }

    public PlayerSide getTurn() {
        return this.player;
    }

    public double getUtility() {
        return this.utility;
    }

    public void setUtility(double utility) {
        this.utility = utility;
    }

    public Move getDestMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    

    
}
