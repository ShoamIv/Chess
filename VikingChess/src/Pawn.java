import java.util.Comparator;

public class Pawn extends ConcretePiece {
    private static final String t = "♙";
    private int kills = 0;
    public Pawn(Player p, int index) {
        super((ConcretePlayer) p, index);
    }

    @Override
    public String getType() {
        return t;
    }

    public int getKills() {
        return kills;
    }

    public void kill() {
        kills++;
    }
}

class SortByKills implements Comparator<Pawn> {

    /**
     * This comparator is unique to Pawn since it's the only ConcretePiece that kills.
     * Compares its two arguments for order.  Returns a negative integer,
     *  or a positive integer as the first argument is less than
     *  or greater than the second.
     *
     * @param p1 the first Pawn to be compared.
     * @param p2 the second Pawn to be compared.
     * @return a negative integer or a positive integer as the
     * first argument is less than or greater than the
     * second according to the requirements given in the exercise
     */
    @Override
    public int compare(Pawn p1, Pawn p2) {
        if (p1.getKills() > p2.getKills()) {
            return -1;
        } else {
            if (p1.getKills() == p2.getKills()) {
                if (p1.getId() > p2.getId()) {
                    return 1;
                } else {
                    if (p1.getId() == p2.getId()) {
                        if (p1.getWins() > p2.getWins()) {
                            return -1;
                        }
                        return 1;
                    }
                    return -1;
                }

            }
            return 1;
        }
    }
}