import java.util.*;
public class Move {
    private ArrayList<Direction> directionMoves;
    private MoveType type;
    public Move(MoveType type, Direction d) {
        this.type = type;
        directionMoves = new ArrayList<Direction>();
        directionMoves.add(d);


    }

    public Move(MoveType type, ArrayList<Direction> directions) {
        this.type = type;
        //duplicate arraylist
        directionMoves = new ArrayList<Direction>(directions);

    }

    public MoveType getType() {
        return this.type;
    }
    
}
