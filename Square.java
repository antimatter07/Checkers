public class Square {
    //if there is a piece, a reference to the Piece on the board
    private Piece piece;
    //row position on board
    private int row;
    //column position on board
    private int col;

    public Square(int row, int col, Piece piece) {
        this.row = row;
        this.col = col;

        this.piece = piece;
    }

    public Square(int row, int col) {

        this.row = row;
        this.col = col;
    }

    public Square(Square copySquare) {
        this.row = copySquare.getRow();
        this.col = copySquare.getCol();

        this.piece = new Piece(copySquare.getPiece().getSide(), copySquare.getRow(), copySquare.getCol());
        if(copySquare.getPiece().isKing())
            this.piece.setAsKing();


    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    public Piece getPiece() {
        return piece;
    }

    public void removePiece() {
        piece = null;
    }

    public void movePieceIn(Piece p) {
        this.piece = p;
    }
    
}
