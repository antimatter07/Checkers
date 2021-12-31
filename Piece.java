public class Piece {

    //allegiance of piece, either COMPUTER or HUMAN
    private PlayerSide side;
    //row position on board
    private int row;
    //col position on board
    private int col;
    //print this symbol for representing the piece on the board
    private String symbol;
    //false if this piece is not a king, true otherwise
    private boolean isKing;


    
    /**
     * This constructor instantiates a piece given it's position in the board
     * @param side the allegiance of the piece (human or AI)
     * @param row row position
     * @param col col position (in array indexing)
     */
    public Piece(PlayerSide side, int row, int col) {
        this.side = side;
        this.row = row;
        this.col = col;
        isKing = false;

        /*C for computer, H for human */

        if(side == PlayerSide.COMPUTER) {
            this.symbol = "C";
        } else {
            this.symbol = "H";
        }
    }

    public Piece() {
        
    }

    public void setPos(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public void setAsKing() {
        this.isKing = true;
        if(side == PlayerSide.COMPUTER) {
            symbol = "KC";
        } else {
            symbol = "KH";
        }
    }

    public Piece(PlayerSide side) {
        this.side = side;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public PlayerSide getSide() {
        return this.side;
    }

    public boolean isKing() {
        return isKing;
    } 
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String toString() {
        return this.side + "ROW: " + row + " & COL: " + col;
    }
    
}
