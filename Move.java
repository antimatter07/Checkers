import java.util.*;
public class Move {
    private ArrayList<Direction> directionMoves;
    private MoveType type;
    private Piece movePiece;
    private double value;
    private Move parent;

    private ArrayList<Move> destMoves;

    public Move(MoveType type, Direction d, Piece movePiece) {
        this.type = type;
        directionMoves = new ArrayList<Direction>();
        directionMoves.add(d);
        this.movePiece = movePiece;



    }

    public Move(Move dupMove) {

        if(dupMove.getType() != null)
            this.type = dupMove.getType();

        this.movePiece = dupMove.getPiece();

        if(dupMove.getDirections() != null)
            directionMoves = new ArrayList<Direction>(dupMove.getDirections());
        this.value = dupMove.getValue();
        this.setParent(dupMove.getParent());
    }

    public Move(MoveType type, ArrayList<Direction> directions, Piece movePiece) {
        this.type = type;
        //duplicate arraylist
        directionMoves = new ArrayList<Direction>(directions);
        this.movePiece = movePiece;

    }

    public Move(int value) {
        setValue(value);
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

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue() {
        return this.value;
    }

    public void setParent(Move move) {
        parent = move;
        
    }

    public Move getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return movePiece.getSide() + " on pos " + movePiece.getRow() + 
        " " + movePiece.getCol() + directionMoves.toString() +" UTILITY: " + this.value; 
    }

    @Override
    public boolean equals(Object obj) {
        Move move = (Move) obj;
        boolean equal = false;

        if(move.getPiece().getRow() == this.getPiece().getRow() && move.getPiece().getCol() == this.getPiece().getRow()
        && this.type == move.getType() && this.sameDirections(move) == true)
            equal = true;

        return equal;
    }

    public boolean sameDirections(Move move) {
        boolean sameDirections = false;

        if(this.directionMoves.size() == move.getDirections().size()) {
            sameDirections = true;
            for(int i = 0; i < directionMoves.size(); i++) {

                if(this.directionMoves.get(i) != move.getDirections().get(i)) {
                    sameDirections = false;
                    break;
                }

            }
        }

        return sameDirections;
    }

    public ArrayList<Move> getDestMoves() {
        return destMoves;
    }
    
}
