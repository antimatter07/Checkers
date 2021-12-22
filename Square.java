public class Square {
    private Piece piece;
    private int row;
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
