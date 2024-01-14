import java.util.ArrayList;
import java.util.Iterator;

public abstract class ConcretePiece  implements Piece  {
    private Player owner;
    String type;
    private boolean Capture; //ask shoam
    private ArrayList<Position> positionsArrayList = new ArrayList<Position>();
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
        ((ConcretePlayer) p).addConcretePiece(this);
        type = t;
        System.out.println("id: " + id + " player: " + p.toString());
    }
    public ConcretePiece(Player p,boolean cap){ //ask shoam
    owner=p;
    Capture=cap;
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
    public boolean isPawn(){
        return this.type.equals("♙");
    }
    public boolean isKing(){
        return this.type.equals("♔");
    }
}
