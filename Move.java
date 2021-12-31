import java.util.*;
/**
 * This class represents a move that can be done on the board that will change the state.
 * @author Matthew James D. Villarica
 */
public class Move {

    //directions this move contains, can contain UPRIGHT, UPLEFT, DOWNLEFT, DOWNRIGHT
    private ArrayList<Direction> directionMoves;

    //type of move represented as an enum that can be JUMP (includes MULTIPLE JUMP) or STANDARD
    private MoveType type;

    //reference to the piece being moved
    private Piece movePiece;

    //value if it is evaluated by the utility function
    private double value;

    //parent move to get to this move
    private Move parent;

    //true if this will lead to the piece transforming to a king, false otherwise
    private boolean transformToKing = false;

    

    public Move(MoveType type, Direction d, Piece movePiece) {
        this.type = type;
        directionMoves = new ArrayList<Direction>();
        directionMoves.add(d);
        this.movePiece = movePiece;

        checkKingTransformation();

    }

    public Move(Move dupMove) {

        if(dupMove.getType() != null)
            this.type = dupMove.getType();

        this.movePiece = dupMove.getPiece();

        if(dupMove.getDirections() != null)
            directionMoves = new ArrayList<Direction>(dupMove.getDirections());
        this.value = dupMove.getValue();
        this.setParent(dupMove.getParent());
        this.transformToKing = dupMove.isKingTransform();
    }

    public Move(MoveType type, ArrayList<Direction> directions, Piece movePiece) {
        this.type = type;
        //duplicate arraylist
        directionMoves = new ArrayList<Direction>(directions);
        this.movePiece = movePiece;

        checkKingTransformation();

    }

    public boolean isKingTransform() {
        return transformToKing;
    }

    public void checkKingTransformation() {
        int row = movePiece.getRow();
        


        if(this.type == MoveType.JUMP) {

            if(movePiece.getSide() == PlayerSide.COMPUTER) {
                for(int i = 0; i < directionMoves.size(); i++) {
                    switch(directionMoves.get(i)) {
                        case UPRIGHT: row += 2;
                        break;

                        case UPLEFT: row += 2;
                        break;

                        case DOWNLEFT: row -= 2;
                        break;

                        case DOWNRIGHT: row -= 2;

                    }
                }

                if(row == 7)
                    this.transformToKing = true;

            } else {

                for(int i = 0; i < directionMoves.size(); i++) {
                    switch(directionMoves.get(i)) {
                        case UPRIGHT: row -= 2;
                        break;

                        case UPLEFT: row -= 2;
                        break;

                        case DOWNLEFT: row += 2;
                        break;

                        case DOWNRIGHT: row += 2;

                    }
                }

                if(row == 0)
                    this.transformToKing = true;

            }


        } else {

            switch(directionMoves.get(0)) {
                case UPRIGHT: row -= 1;
                break;

                case UPLEFT: row -= 1;
                break;

                case DOWNLEFT: row += 1;
                break;

                case DOWNRIGHT: row += 1;

            }

            if(this.movePiece.getSide() == PlayerSide.COMPUTER && row == 7)
                this.transformToKing = true;
            else if(this.movePiece.getSide() == PlayerSide.HUMAN && row == 0)
                this.transformToKing = true;


        }
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

    private ArrayList<Move> destMoves;
    
}
