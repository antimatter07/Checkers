public class Piece {

    
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

        /*C for computer, H for human */

        if(side == PlayerSide.COMPUTER) {
            this.symbol = "C";
        } else {
            this.symbol = "H";
        }
    }

    public String getSymbol() {
        return this.symbol;
    }

    public PlayerSide getSide() {
        return this.side;
    }
    
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


    private PlayerSide side;
    private int row;
    private int col;
    private String symbol;

    
}
