import java.util.*;
public class Board {
    private Square[][] board;
    private int dimension;

    private ArrayList<Piece> compPieces;
    private ArrayList<Piece> humanPieces;
    

    /**
     * This constructor isntantiates the board, which is the initial
     * set up of pieces in standard checkers on an 8 x 8 board.
     * 
     * @param dim The dimension of the board, usually 8 but can be changed
     *            for testing
     */
    public Board(int dim, ArrayList<Piece> computerPieces, ArrayList<Piece> humanPieces) {
        dimension = dim;
        board = new Square[dimension][dimension];
        PlayerSide cur_side = PlayerSide.COMPUTER;
        int hum_index = 0;
        int comp_index = 0;

        for(int i = 0; i < dimension; i++) {

            if(i > 2)
                cur_side = PlayerSide.HUMAN;

            for(int j = 0; j < dimension; j++) {

                if((i == 0 || i == 2 || i == 6) && j % 2 != 0) {

                    if(cur_side == PlayerSide.COMPUTER) {
                        computerPieces.add(new Piece(cur_side, i, j));
                        board[i][j] = new Square(i, j, computerPieces.get(comp_index));
                        comp_index++;
                    } else {
                        humanPieces.add(new Piece(cur_side, i, j));
                        board[i][j] = new Square(i, j, humanPieces.get(hum_index));
                        hum_index++;
                    }
                    
                    
                    
                    
                } else if ((i == 1 || i == 5 || i == 7) && j % 2 == 0) {
                
                    if(cur_side == PlayerSide.COMPUTER) {
                        computerPieces.add(new Piece(cur_side, i, j)) ;
                        board[i][j] = new Square(i, j, computerPieces.get(comp_index));
                        comp_index++;
                    } else {
                        humanPieces.add(new Piece(cur_side, i, j));
                        board[i][j] = new Square(i, j, humanPieces.get(hum_index));
                        hum_index++;
                    }
                    
                } else {
                    board[i][j] = new Square(i, j);
                }


            }
        }

    }

    /**
     * Instantiates a board given another one. Makes another copy, does not point to the same
     * reference.
     * 
     * @param copyBoard the board to be duplicated when this new board is intantiated
     */
    public Board(Board copyBoard) {
        board = new Square[8][8];
        dimension = 8;

        compPieces = new ArrayList<Piece>(copyBoard.getCompPieces());
        humanPieces = new ArrayList<Piece>(copyBoard.getHumPieces());

        for(int i = 0; i < 8; i++) {

            for(int j = 0; j < 8; j++) {
                if(copyBoard.getBoard()[i][j].getPiece() != null)
                    board[i][j] = new Square(copyBoard.getBoard()[i][j]);
                else board[i][j] = new Square(i, j);
            }
        }
    }

    /**
     * Reference to the pieces of the board. Changes done here will reflect in main reference
     * @param humanPieces the pieces of human
     * @param compPieces the ieces of AI
     */
    public void setPieces(ArrayList<Piece> humanPieces, ArrayList<Piece> compPieces) {
        this.humanPieces = humanPieces;
        this.compPieces = compPieces;
    }

    /**
     * This method returns the Square[][]
     * @return the 2D array that represents the board
     */
    public Square[][] getBoard() {
        return this.board;
    }

    /**
     * This method executes the standard move
     * @param cur_piece piece being moved
     * @param moveDirection direction
     */
    public void standardMove(Piece cur_piece, Direction moveDirection) {

        board[cur_piece.getRow()][cur_piece.getCol()].removePiece();
        switch(moveDirection) {
            case UPRIGHT:
                cur_piece.setPos(cur_piece.getRow() - 1, cur_piece.getCol() + 1);
                break;
            case UPLEFT:
                cur_piece.setPos(cur_piece.getRow() - 1, cur_piece.getCol() - 1);
                break;
            case DOWNLEFT:
                cur_piece.setPos(cur_piece.getRow() + 1, cur_piece.getCol() - 1);
                break;
            case DOWNRIGHT:
                cur_piece.setPos(cur_piece.getRow() + 1, cur_piece.getCol() + 1);
                break;         

        }

        //System.out.println("*CUR PIECE ROW COL" + cur_piece.getRow() + cur_piece.getCol());
        board[cur_piece.getRow()][cur_piece.getCol()].movePieceIn(cur_piece);

        //transform to king, if valid
        if(cur_piece.getSide() == PlayerSide.HUMAN && cur_piece.getRow() == 0) {
            cur_piece.setAsKing();
        } else if(cur_piece.getSide() == PlayerSide.COMPUTER && cur_piece.getRow() == 7) {
            cur_piece.setAsKing();
        }


    }

    /**
     * This method execute the jump move.
     * 
     * @param cur_piece jumping piece
     * @param moveDirection direction to jump to
     * @return jumping piece, for removing the reference in other places other than the board
     */
    public Piece jumpMove(Piece cur_piece, Direction moveDirection) {

        Piece jumpedPiece = new Piece();
        board[cur_piece.getRow()][cur_piece.getCol()].removePiece();

        switch(moveDirection) {
            case UPRIGHT:
                jumpedPiece = board[cur_piece.getRow() - 1][cur_piece.getCol() + 1].getPiece();
                board[cur_piece.getRow() - 1][cur_piece.getCol() + 1].removePiece();
                cur_piece.setPos(cur_piece.getRow() - 2, cur_piece.getCol() + 2);
                break;
            case UPLEFT:
                jumpedPiece = board[cur_piece.getRow() - 1][cur_piece.getCol() - 1].getPiece();
                board[cur_piece.getRow() - 1][cur_piece.getCol() - 1].removePiece();
                cur_piece.setPos(cur_piece.getRow() - 2, cur_piece.getCol() - 2);
                break;
            case DOWNLEFT:
                jumpedPiece = board[cur_piece.getRow() + 1][cur_piece.getCol() - 1].getPiece();
                board[cur_piece.getRow() + 1][cur_piece.getCol() - 1].removePiece();
                cur_piece.setPos(cur_piece.getRow() + 2, cur_piece.getCol() - 2);
                break;
            case DOWNRIGHT:
                jumpedPiece = board[cur_piece.getRow() + 1][cur_piece.getCol() + 1].getPiece();
                board[cur_piece.getRow() + 1][cur_piece.getCol() + 1].removePiece();
                cur_piece.setPos(cur_piece.getRow() + 2, cur_piece.getCol() + 2);
                break;   
                  

        }

        //
        //humanPieces.remove(jumpedPiece);
        //compPieces.remove(jumpedPiece);

        board[cur_piece.getRow()][cur_piece.getCol()].movePieceIn(cur_piece);
        //transform to king, if valid
        if(cur_piece.getSide() == PlayerSide.HUMAN && cur_piece.getRow() == 0) {
            cur_piece.setAsKing();
        } else if(cur_piece.getSide() == PlayerSide.COMPUTER && cur_piece.getRow() == 7) {
            cur_piece.setAsKing();
        }
        return jumpedPiece;

    }

    /**
     * This method checks if the piece can do a standard move given its current position
     * and the given direction.
     * 
     * @param cur_piece reference to the piece in the board
     * @param moveDirection the direction being checked
     * @return true if it can do a standard move, false otherwise
     */
    public boolean checkStandardMove(Piece cur_piece, Direction moveDirection) {

        switch(moveDirection) {
            case UPLEFT:
                if(cur_piece.getRow() - 1 >= 0 && cur_piece.getCol() - 1 >= 0
                && board[cur_piece.getRow() - 1][cur_piece.getCol() - 1].getPiece() == null)
                    return true;
                    break;
            case UPRIGHT:
                if(cur_piece.getRow() - 1 >= 0 && cur_piece.getCol() + 1 <= 7
                && board[cur_piece.getRow() - 1][cur_piece.getCol() + 1].getPiece() == null)
                    return true;
                    break;

            case DOWNRIGHT:
                if(cur_piece.getRow() + 1 <= 7 && cur_piece.getCol() + 1 <= 7
                && board[cur_piece.getRow() + 1][cur_piece.getCol() + 1].getPiece() == null)
                    return true;
                    break;

            case DOWNLEFT:
                if(cur_piece.getRow() + 1 <= 7 && cur_piece.getCol() - 1 >= 0
                && board[cur_piece.getRow() + 1][cur_piece.getCol() - 1].getPiece() == null)
                    return true;
                    break;
            
            default: return false;
                
        }

        return false;
    }

    /**
     * Checks if a jump move is possible given a piece and direction
     * 
     * @param cur_piece piece being checked
     * @param moveDirection direction
     * @return true if a jump move is possible, false otherwise
     */
    public boolean checkJumpMove(Piece cur_piece, Direction moveDirection) {

        /*If it is not out of bonds and if there is no piece and if the piece that is jumped over
          is an enemy piece, then a jump move on this piece is valid */
        switch(moveDirection) {
            case UPLEFT:
                if(cur_piece.getRow() - 2 >= 0 && cur_piece.getCol() - 2 >= 0
                && board[cur_piece.getRow() - 2][cur_piece.getCol() - 2].getPiece() == null
                && board[cur_piece.getRow() - 1][cur_piece.getCol() - 1].getPiece() != null
                && board[cur_piece.getRow() - 1][cur_piece.getCol() - 1].getPiece().getSide()
                != cur_piece.getSide())
                    return true;

            break;
            case UPRIGHT:
                if(cur_piece.getRow() - 2 >= 0 && cur_piece.getCol() + 2 <= 7
                && board[cur_piece.getRow() - 2][cur_piece.getCol() + 2].getPiece() == null
                && board[cur_piece.getRow() - 1][cur_piece.getCol() + 1].getPiece() != null
                && board[cur_piece.getRow() - 1][cur_piece.getCol() + 1].getPiece().getSide()
                != cur_piece.getSide())
                    return true;

            break;
            case DOWNRIGHT:
                if(cur_piece.getRow() + 2 <= 7 && cur_piece.getCol() + 2 <= 7
                && board[cur_piece.getRow() + 2][cur_piece.getCol() + 2].getPiece() == null
                && board[cur_piece.getRow() + 1][cur_piece.getCol() + 1].getPiece() != null
                && board[cur_piece.getRow() + 1][cur_piece.getCol() + 1].getPiece().getSide()
                != cur_piece.getSide())
                    return true;
            

            break;
            case DOWNLEFT:
                if(cur_piece.getRow() + 2 <= 7 && cur_piece.getCol() - 2 >= 0
                && board[cur_piece.getRow() + 2][cur_piece.getCol() - 2].getPiece() == null
                && board[cur_piece.getRow() + 1][cur_piece.getCol() - 1].getPiece() != null
                && board[cur_piece.getRow() + 1][cur_piece.getCol() - 1].getPiece().getSide()
                != cur_piece.getSide())
                    return true;

            break;
            default: return false;

        }

        return false;
    }
    
    /**
     * This method displays the board in the console.
     */
    public void display() {
        for(int i = 0; i < dimension; i++){
           
            for(int j =0; j < dimension; j++){
                System.out.print("----");
            }
            System.out.println("-");
            
            for(int j =0; j < dimension; j++){
                
                
                if(board[i][j].getPiece() != null){
                  System.out.print("| " + board[i][j].getPiece().getSymbol() + " ");
                }else{
                  System.out.print("|   ");   
                }

            }
            System.out.println("|");
        }
        for(int i =0; i < dimension; i++){       
            System.out.print("----");
        }
        System.out.print("-");
    }

    /**
     * Given a move, this method executes the move.
     * @param move the move to be executed on the board.
     */
    public void executeMove(Move move) {

        Piece jumpedPiece;
        Piece movingPiece;

        movingPiece = board[move.getPiece().getRow()][move.getPiece().getCol()].getPiece();

        
            if(move.getType() == MoveType.STANDARD) {
                standardMove(movingPiece, move.getDirections().get(0));

            } else {

                for(int i = 0; i < move.getDirections().size(); i++) {
                    jumpedPiece = jumpMove(movingPiece,move.getDirections().get(i));
                    System.out.println(jumpedPiece);
                    if(jumpedPiece.getSide() == PlayerSide.HUMAN) {
                        humanPieces.remove(jumpedPiece);
                    } else {
                        compPieces.remove(jumpedPiece);
                    }
                }

            
        }
    }

    public boolean isGameOver() {
        if(humanPieces.size() == 0 || compPieces.size() == 0)
            return true;
        else return false;

    }

    public ArrayList<Piece> getCompPieces() {
        return compPieces;
    }

    public ArrayList<Piece> getHumPieces() {
        return humanPieces;
    }



}
    
