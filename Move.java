import java.util.*;
public class Move {
    private ArrayList<Direction> directionMoves;
    private MoveType type;
    private Piece movePiece;

    public Move(MoveType type, Direction d, Piece movePiece) {
        this.type = type;
        directionMoves = new ArrayList<Direction>();
        directionMoves.add(d);
        this.movePiece = movePiece;


    }

    public Move(MoveType type, ArrayList<Direction> directions, Piece movePiece) {
        this.type = type;
        //duplicate arraylist
        directionMoves = new ArrayList<Direction>(directions);
        this.movePiece = movePiece;

    }

    public MoveType getType() {
        return this.type;
    }

    public ArrayList<Direction> getDirections() {
        return directionMoves;
    }

    public Piece getPiece() {
        return movePiece;
    }

    @Override
    public String toString() {
        return movePiece.getSide() + " on pos " + movePiece.getRow() + 
        " " + movePiece.getCol() + directionMoves.toString(); 
    }
    
}
