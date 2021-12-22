import java.util.*;
public class Driver {

    public static void main(String[] args) {
        ArrayList<Piece> compPieces = new ArrayList<Piece>(12);
        ArrayList<Piece> humanPieces = new ArrayList<Piece>(12);
        Board board = new Board(8, compPieces, humanPieces);
        boolean isOver = false;
        PlayerSide turn = PlayerSide.HUMAN;
        
        board.display();
        board.standardMove(compPieces.get(9), Direction.DOWNLEFT);
        board.display();

        /*
        do {

            board.display();
        }while(isOver == false);
        */


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
