import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public abstract class ConcretePiece implements Piece {
    private Player owner;
    String type;
    private ArrayList<Position> positionsArrayList = new ArrayList<Position>();
    private int steps = 0;
    private int squares = 0;
    private int id; // save id of piece according to the figure in the assignment

    public ConcretePiece(Player p) {
        owner = p;
    }

    public ConcretePiece(Player p, int index) {
        owner = p;
        id = index;

    }

    public ConcretePiece(Player p, int index, Position pos) {
        owner = p;
        id = index;
        positionsArrayList.add(pos);
        ((ConcretePlayer) p).addConcretePiece(this);
        System.out.println("id: " + id + " player: " + p.toString());
    }

    public ConcretePiece(Player p, int index, Position pos, String t) {
        owner = p;
        id = index;
        positionsArrayList.add(pos);
        type = t;
        ((ConcretePlayer) p).addConcretePiece(this);
        System.out.println("id: " + id + " player: " + p.toString());
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    private void add_squares(int append) {
        squares += append;
    }

    public void addPosition(Position p) {
        calcSquares(p);
        //  System.out.println("id: " + id + " squares: " + squares);
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

    public void setId(int index) {
        id = index;
    }

    public void printPositions() {
        Iterator<Position> iterator = positionsArrayList.iterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next().toString() + ",, ");
        }
    }

    @Override
    public String toString() {
        printPositions();
        return "id: " + id + " squares: " + squares;
    }

    public boolean isPawn() {
        return this.type.equals("♙");
    }

    public boolean isKing() {
        return this.type.equals("♔");
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getSquares() {
        return squares;
    }

    public void setSquares(int squares) {
        this.squares = squares;
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
