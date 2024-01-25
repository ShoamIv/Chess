import java.util.ArrayList;
import java.util.Comparator;

public abstract class ConcretePiece implements Piece {
    private Player owner;
    String type;
    private ArrayList<Position> positionsArrayList = new ArrayList<Position>(); //should be depreciated
    private int steps = 0;
    private int squares = 0;
    private int id; // save id of piece according to the figure in the assignment

    public ConcretePiece(Player p) {
        owner = p;
        ((ConcretePlayer) p).addConcretePiece(this);
    }

    public ConcretePiece(Player p, int index) {
        owner = p;
        id = index;
        ((ConcretePlayer) p).addConcretePiece(this);
    }

    public ConcretePiece(Player p, int index, String t) {
        owner = p;
        id = index;
        type = t;
        ((ConcretePlayer) p).addConcretePiece(this);
    }

    public ConcretePiece(Player p, int index, Position pos, String t) {
        owner = p;
        id = index;
        positionsArrayList.add(pos);
        type = t;
        ((ConcretePlayer) p).addConcretePiece(this);
    }

    public ArrayList<Position> getPositionsArrayList() {
        return positionsArrayList;
    }


    @Override
    public Player getOwner() {
        return owner;
    }

    public void add_squares(int append) {
        squares += append;
        steps++;
    }

    public void addPosition(Position p) {
        calcSquares(p);
    }

    private void add_position_to_list(Position p) {
        positionsArrayList.add(p);
    }

    private void calcSquares(Position p) {
        int x_diff = Math.abs(p.GetX() - positionsArrayList.getLast().GetX());
        int y_diff = Math.abs(p.GetY() - positionsArrayList.getLast().GetY());
        add_position_to_list(p);
        add_squares(x_diff + y_diff);
        //  printPositions();
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + ": ";
    }

    public boolean isPawn() {
        return this instanceof Pawn;
    }

    public boolean isKing() {
        return this instanceof King;
    }

    public int getSteps() {
        return steps;
    }

    public int getSquares() {
        return squares;
    }

    public int getWins() {
        return this.owner.getWins();
    }
}

class SortBySquares implements Comparator<ConcretePiece> {

    /**
     * Returns a string representation of the object.
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.<p>
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     */
    @Override
    public int compare(ConcretePiece p1, ConcretePiece p2) {
        if (p1.getSquares() > p2.getSquares()) {
            return 1;
        } else {
            if (p1.getSquares() == p2.getSquares()) {
                if (p1.getId() < p2.getId()) {
                    return 1;
                } else {
                    if (p1.getId() == p2.getId()) {
                        if (p1.getWins() > p2.getWins()) {
                            return 1;
                        }
                        return -1;
                    }
                    return -1;
                }

            }
            return -1;
        }

    }
}

class SortBySteps implements Comparator<ConcretePiece> {

    @Override
    public String toString() {
        return super.toString();
    }


    @Override
    public int compare(ConcretePiece p1, ConcretePiece p2) {
        if (p1.getSteps() > p2.getSteps()) {
            return 1;
        } else {
            if (p1.getSteps() == p2.getSteps()) {
                if (p1.getId() > p2.getId()) {
                    return 1;
                } else {
                    return -1;
                }

            }
        }
        return -1;
    }
}
