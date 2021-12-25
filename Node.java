import java.util.*;
public class Node {

    //deep copy of the configuaration of the board
    private Board board;

    //possible moves player can make
    private ArrayList<Move> moves;

    //the player on this state
    private PlayerSide player;

    private boolean isComp;

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
    public boolean isTerminal() {
        if(this.board.isGameOver() || moves.size() == 0)
            return true;
        return false;
    }

    public Move MinMaxSearch() {
        Move move;
        //System.out.println("**INSIDE MINMAX**");

        move = this.max_value(this);

        return move;

    }

    public Move max_value(Node newNode) {

        ArrayList<Board> copyBoards = new ArrayList<Board>();
        //negative infinity, lowest value imposssible 
        Move move = new Move(-100);
        //some arbitrary value
        Move move2 = new Move(0);

        newNode.generateMoves(newNode);
        

        System.out.println("**MAX B4 TERMINAL");
        System.out.println("TERMINAL?" + isTerminal());
        if(newNode.isTerminal())
            return newNode.utility();

        
        //System.out.println("**MAX");
        //System.out.println("AI MOVES");
        //System.out.println(newNode.getMoves());
        
        for(int i = 0; i < newNode.getMoves().size(); i++) {
            copyBoards.add(new Board(newNode.getBoard()));
            
            //System.out.println("**copy boards size**" + copyBoards.size() + "i: " + i);

            copyBoards.get(i).executeMove(newNode.getMoves().get(i));

            //System.out.println("**AFTER MOVE EXECUTION**");
            copyBoards.get(i).display();
            //System.out.println(copyBoards.get(i).getHumPieces());
            //System.out.println(copyBoards.get(i).getCompPieces());

            move2 = newNode.min_value(new Node(copyBoards.get(i), !isComp));

            if(move2.getValue() > move.getValue())
                move = move2;


        }

        return move;


    }

    public Move min_value(Node newNode) {
        
        ArrayList<Board> copyBoards = new ArrayList<Board>();
        //negative infinity, lowest value imposssible 
        Move move = new Move(100);
        //some arbitrary value
        Move move2 = new Move(0);

        newNode.generateMoves(newNode);
        
        System.out.println("**MIN V4 TERMINAL");
        System.out.println("TERMINAL?" + isTerminal());
        if(newNode.isTerminal())
            return newNode.utility();

        
        //System.out.println("**MIN");
        //System.out.println("AI MOVES");
        //System.out.println(newNode.getMoves());
        
        for(int i = 0; i < newNode.getMoves().size(); i++) {
            copyBoards.add(new Board(newNode.getBoard()));
            //System.out.println("**copy boards size**" + copyBoards.size() + "i: " + i);
            
            copyBoards.get(i).executeMove(newNode.getMoves().get(i));
            //System.out.println("**AFTER MOVE EXECUTION**");
            copyBoards.get(i).display();
            //System.out.println(copyBoards.get(i).getHumPieces());
            //System.out.println(copyBoards.get(i).getCompPieces());

            move2 = newNode.max_value(new Node(copyBoards.get(i), !isComp));

            if(move2.getValue() < move.getValue())
                move = move2;


        }

        return move;


    }

    public Move utility() {
        Move move = new Move(0);

        if(this.board.getHumPieces().size() == 0 || (player == PlayerSide.HUMAN && moves.size() == 0))
            move.setValue(1);
        else if(this.board.getCompPieces().size() == 0 || (player == PlayerSide.COMPUTER && moves.size() == 0))
            move.setValue(-1);
        return move;
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

        for(int i = 0; i < 8; i++) {

            for(int j = 0; j < 8; j++) {
                if(newNode.getBoard().getBoard()[i][j].getPiece() != null) {
                    
                    if(newNode.getBoard().getBoard()[i][j].getPiece().getSide() == PlayerSide.HUMAN)
                        humanPieces.add(newNode.getBoard().getBoard()[i][j].getPiece());
                    else compPieces.add(newNode.getBoard().getBoard()[i][j].getPiece());
                }

            }
        }

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

            

            if(player == PlayerSide.HUMAN) {

                
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
                if(player == PlayerSide.HUMAN) {

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

            //System.out.println("RESULTING MOVES: ");
            //System.out.println(newNode.getMoves());
    }

    public Board getBoard() {
        return this.board;
    }

    public ArrayList<Move> getMoves() {
        return this.moves;
    }

    

    
}
