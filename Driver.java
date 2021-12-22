public class Driver {

    public static void main(String[] args) {
        Piece[] compPieces = new Piece[12];
        Piece[] humanPieces = new Piece[12];

        Board board = new Board(8, compPieces, humanPieces);

        board.display();

    }
    
}
