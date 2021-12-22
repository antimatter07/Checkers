public class Driver {

    public static void main(String[] args) {
        Piece[] compPieces = new Piece[12];
        Piece[] humanPieces = new Piece[12];
        Board board = new Board(8, compPieces, humanPieces);
        boolean isOver = false;
        PlayerSide turn = PlayerSide.HUMAN;
        
        board.display();
        board.standardMove(compPieces[9], Direction.DOWNLEFT);
        board.display();

        /*
        do {

            board.display();
        }while(isOver == false);
        */


    }
    
}
