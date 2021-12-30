import java.util.*;
public class Driver {
    private static final Direction[] HUMAN_DIRECTIONS = new Direction[2];
    private static final Direction[] COMP_DIRECTIONS = new Direction[2];
    private static final Direction[] KING_DIRECTIONS = new Direction[4];


    public static void main(String[] args) {
        boolean canJump = false;
        boolean jumpPossible = false;
        Piece jumpingPiece;
        int choice;
        ArrayList<Piece> compPieces = new ArrayList<Piece>(12);
        ArrayList<Piece> humanPieces = new ArrayList<Piece>(12);

        ArrayList<Move> compMoves = new ArrayList<Move>();
        ArrayList<Move> humanMoves = new ArrayList<Move>();
 
        ArrayList<Direction> jumpDirections = new ArrayList<Direction>();
        
        Board board = new Board(8, compPieces, humanPieces);
        boolean isOver = false;
        PlayerSide turn = PlayerSide.HUMAN;

        setDirections();
        board.setPieces(humanPieces, compPieces);

        //generate valid human moves in this state
        while(isOver == false) {
            jumpPossible = false;

            

            if(turn == PlayerSide.HUMAN) {

                
                for(int i = 0; i < humanPieces.size(); i++) {

                    if(humanPieces.get(i).isKing() == false) {

                        for(int j = 0; j < HUMAN_DIRECTIONS.length; j++) {
                            if(board.checkStandardMove(humanPieces.get(i), HUMAN_DIRECTIONS[j]))
                                humanMoves.add(new Move(MoveType.STANDARD, HUMAN_DIRECTIONS[j], humanPieces.get(i)));

                            else if(board.checkJumpMove(humanPieces.get(i), HUMAN_DIRECTIONS[j])) {
                                canJump = true;
                                jumpPossible = true;

                                jumpDirections.add(HUMAN_DIRECTIONS[j]);

                                Board boardCopy = new Board(board);

                                //keep a reference to jumping piece in copy boared for easy access
                                jumpingPiece = boardCopy.getBoard()[humanPieces.get(i).getRow()][humanPieces.get(i).getCol()].getPiece(); 

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

                                humanMoves.add(new Move(MoveType.JUMP, jumpDirections, humanPieces.get(i)));

                                jumpDirections.clear();



                            }

                            
                            

                        }
                        
                    } else {
                        //for king
                        for(int j = 0; j < KING_DIRECTIONS.length; j++) {
                            if(board.checkStandardMove(humanPieces.get(i), KING_DIRECTIONS[j]))
                                humanMoves.add(new Move(MoveType.STANDARD, KING_DIRECTIONS[j], humanPieces.get(i)));

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

                                humanMoves.add(new Move(MoveType.JUMP, jumpDirections, humanPieces.get(i)));

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
                                compMoves.add(new Move(MoveType.STANDARD, COMP_DIRECTIONS[j], compPieces.get(i)));

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

                                compMoves.add(new Move(MoveType.JUMP, jumpDirections, compPieces.get(i)));

                                jumpDirections.clear();



                            }

                            
                            

                        }
                        
                    } else {

                        //for king
                        for(int j = 0; j < KING_DIRECTIONS.length; j++) {
                            if(board.checkStandardMove(compPieces.get(i), KING_DIRECTIONS[j]))
                                compMoves.add(new Move(MoveType.STANDARD, KING_DIRECTIONS[j], compPieces.get(i)));

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

                                compMoves.add(new Move(MoveType.JUMP, jumpDirections, compPieces.get(i)));

                                jumpDirections.clear();



                            }

                            
                            

                        }
                        
                    }
                    
                }

            }
            
            //display and pick moves
            board.display();
            System.out.println();

            /*Remove all non jumps, because a jump MUST be made if at least one jump is possible */
            if(jumpPossible == true) {

                /*System.out.println("*IM INSIDE JUMPOS*");
                System.out.println("SIZE OF ARRAYLISTS:" + humanMoves.size() + compMoves.size());
                */
                if(turn == PlayerSide.HUMAN) {

                    for(int h = 0; h < humanMoves.size(); h++) {
                        if(humanMoves.get(h).getType() == MoveType.STANDARD) {
                            humanMoves.remove(h);
                            --h;
                        }
                    }

                    

                } else {

                    for(int h = 0; h < compMoves.size(); h++) {
                        if(compMoves.get(h).getType() == MoveType.STANDARD) {
                            compMoves.remove(h);
                            --h;
                        }
                    }


                }

            }

            //if it is either player's turn and there are legal moves that can be made, ask for a choice
            //otherwise, player who can't make a legal move loses and theg ame is over
            if((turn == PlayerSide.COMPUTER && compMoves.size() > 0) || (turn == PlayerSide.HUMAN && humanMoves.size() > 0)) {

                choice = 0;
                if(turn == PlayerSide.HUMAN) {
                    sortMoves(humanMoves);
                    
                    choice = displayMoves(turn, humanMoves, compMoves);
                }

                //old condition: if((turn == PlayerSide.COMPUTER && compMoves.size() > 0) || (turn == PlayerSide.HUMAN && humanMoves.size() > 0))
                //execute move
                //int times = 0;
                if(turn == PlayerSide.COMPUTER) {
                    Move comp;

                    Node root = new Node(board, true);
                    comp = root.MinMaxSearch();

                    while(comp.getParent() != null) {
                        //System.out.println("*CHILD: " + comp);
                        //System.out.println("VALUE! : " + comp.getValue());
                        comp = comp.getParent();
                        //System.out.println("*PARENT: " + comp);
                       
                    
                    }
                    //System.out.println("times looped: "+ times);
                    System.out.println("*AI's choice: " + comp+ "UTIL: " + comp.getValue());
                    
                    
                    board.executeMove(comp);

                    if(board.isGameOver() || (turn == PlayerSide.COMPUTER && compMoves.size() == 0) ||
                    (turn == PlayerSide.HUMAN && humanMoves.size() == 0))
                        isOver = true;

                    turn = PlayerSide.HUMAN;
                    
                } else {

                    board.executeMove(humanMoves.get(choice));
                    if(board.isGameOver() || (turn == PlayerSide.COMPUTER && compMoves.size() == 0) ||
                    (turn == PlayerSide.HUMAN && humanMoves.size() == 0))
                        isOver = true;

                    turn = PlayerSide.COMPUTER;
                    

                }
            } else isOver = true;

            
            

            humanMoves.clear();
            compMoves.clear();
            
            

            
          

        

        }

        


    }
    /**
     * This methods sets the CONSTANT directions of each player, and the king piece.
     */
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
    /**
     * This method displays the move and asks the user for their choice in terms of array indexing
     * 
     * @param turn current turn, HUMAN or COMPUTER
     * @param humanMoves list of possible moves
     * @param compMoves list of possible moves
     * @return index of choice in list of moves
     */
    public static int displayMoves(PlayerSide turn, ArrayList<Move> humanMoves, ArrayList<Move> compMoves) {
        Scanner sc = new Scanner(System.in);
        int choice;
        
        if(turn == PlayerSide.HUMAN) {
            for(int i = 0; i < humanMoves.size(); i++) {
                System.out.println("[" + i + "]" + humanMoves.get(i).toString());
            }

            do {
                System.out.println("> Please enter your move of choice: ");
                choice = sc.nextInt();

            } while(!(choice >= 0 && choice < humanMoves.size()));
        } else {

            for(int i = 0; i < compMoves.size(); i++) {
                System.out.println("[" + i + "]" + compMoves.get(i).toString());
            }

            do {
                System.out.println("> Please enter your move of choice: ");
                choice = sc.nextInt();

            } while(!(choice >= 0 && choice < compMoves.size()));

        }

        //sc.close();


        return choice;
    }

    public static void sortMoves(ArrayList<Move> movesToSort) {

        int availIndex = 0;

        if(movesToSort.get(0).getType() == MoveType.JUMP) {
            for(int i = 1; i < movesToSort.size(); i++) {

                if(movesToSort.get(i).getDirections().size() > movesToSort.get(availIndex).getDirections().size()) {
                    Collections.swap(movesToSort, availIndex, i);
                    availIndex++;
                } else if(movesToSort.get(i).isKingTransform()) {
                    Collections.swap(movesToSort, availIndex, i);
                    availIndex++;
                }
                
            }
        } else {

            for(int i = 1; i < movesToSort.size(); i++) {

                if(movesToSort.get(i).isKingTransform()) {
                    Collections.swap(movesToSort, availIndex, i);
                    availIndex++;
                }
                
            }


        }
        

    }

    
    /*
    for each piece in the players arsenal that is still alive
        if if it is a normal piece
            check if it can do standard move in the 2 valid directions 
            (THIS IS DOWN LEFT DOWN RIGHT FOR COMPUTER, UPRIGHT UPLEFT FOR HUMAN)
                add that to array of moves
                
                if it can not do a standard move, check if it can do
                a jump move(including multiple jump moves)
                    if it can, keep checking if it can capture another with other jump moves
                    until it can not jump anymore (ONLY IN THE 2 VALID DIRECTIONS)
                        add this to the array of moves with the direction/s of the jump
        else if it is a king
            do same as above, but consider ALL 4 POSSIBLE DIRECTIONS
    
    
    
    
    
    
    */
    
}
