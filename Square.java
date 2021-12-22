public class Square {
    Piece piece;
    int row;
    int col;

    public Square(int row, int col, Piece piece) {
        this.row = row;
        this.col = col;

        this.piece = piece;
    }

    public Square(int row, int col) {

        this.row = row;
        this.col = col;


    }

    public Piece getPiece() {
        return piece;
    }
    
}
