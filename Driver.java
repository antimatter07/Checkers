import java.util.*;
public class Driver {
    private static final Direction[] HUMAN_DIRECTIONS = new Direction[2];
    private static final Direction[] COMP_DIRECTIONS = new Direction[2];
    private static final Direction[] KING_DIRECTIONS = new Direction[4];


    public static void main(String[] args) {
        boolean canJump = false;
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

            

            if(turn == PlayerSide.HUMAN) {

                
                for(int i = 0; i < humanPieces.size(); i++) {

                    if(humanPieces.get(i).isKing() == false) {

                        for(int j = 0; j < HUMAN_DIRECTIONS.length; j++) {
                            if(board.checkStandardMove(humanPieces.get(i), HUMAN_DIRECTIONS[j]))
                                humanMoves.add(new Move(MoveType.STANDARD, HUMAN_DIRECTIONS[j], humanPieces.get(i)));

                            else if(board.checkJumpMove(humanPieces.get(i), HUMAN_DIRECTIONS[j])) {
                                canJump = true;
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
                    }
                    
                }

            }
            
            //display and pick moves
            board.display();
            System.out.println();
            choice = displayMoves(turn, humanMoves, compMoves);
            
            //execute move
            if(turn == PlayerSide.COMPUTER) {
                board.executeMove(compMoves.get(choice));
                turn = PlayerSide.HUMAN;
                compMoves.clear();
            } else {
                board.executeMove(humanMoves.get(choice));
                turn = PlayerSide.COMPUTER;
                humanMoves.clear();

            } 

            //check if game is over
          

        

        }

        


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


        return choice;
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
