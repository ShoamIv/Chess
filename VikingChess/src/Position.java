import java.util.Comparator;

public class Position {
    private int _x, _y;
    private int[] _pieces = new int[38]; //_pieces[0] will store the amount of pieces

    public void set_pieces(int id) {
        if (isFirstAppearance(id)) {
            this._pieces[id]++;
            this._pieces[0]++;
        }
        return;
    }

    public boolean isFirstAppearance(int id) {
        return !(this._pieces[id] > 0);
    }


    public Position(int x, int y) {
        this._x = x;
        this._y = y;
    }

    public Position(Position p) {
        this(p.GetX(), p.GetY());
    }

    public int GetX() {
        return _x;
    }

    public int GetY() {
        return _y;
    }

    public boolean Equalto(Position p) {
        return (this._x == p._x && this._y == p._y);
    }

    public int get_pieces() {
        return _pieces[0];
    }

    @Override
    public String toString() {
        return "(" +
                _x +
                ", " + _y +
                ')';
    }
}

class SortByPieces implements Comparator<Position> {

    /**
     * Compares its two arguments for order.  Returns a negative integer,
     * zero, or a positive integer as the first argument is less than, equal
     * to, or greater than the second.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as the
     * first argument is less than, equal to, or greater than the
     * second.
     */
    @Override
    public int compare(Position p1, Position p2) {
        if (p1.get_pieces() > p2.get_pieces()) {
            return 1;
        } else {
            if (p1.get_pieces() == p2.get_pieces()) {
                if (p1.GetX() > p2.GetX()) {
                    return 1;
                } else {
                    if (p1.GetX() == p2.GetX()) {
                        if (p1.GetY() > p2.GetY()) {
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