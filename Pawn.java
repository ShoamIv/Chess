import java.util.Comparator;

public class Pawn extends ConcretePiece {
    private static String t = "♙";
    private int kills = 0;

//    @Override
//    public String toString() {
//        if (getOwner().isPlayerOne()) {
//            return getId() +
//                    ": " + kills +
//                    "kills";
//        }
//        return getId() +
//                ": " + kills +
//                "kills";
//    }

    public Pawn(Player p) {
        super((ConcretePlayer) p);
    }

    public Pawn(Player p, int index) {
        super((ConcretePlayer) p, index);
    }

    public Pawn(Player p, int index, Position position) {
        super((ConcretePlayer) p, index, position, t);
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
        //    System.out.println("kills: " + kills);
    }
}

class SortByKills implements Comparator<Pawn> {

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
    public int compare(Pawn p1, Pawn p2) {
        if (p1.getKills() > p2.getKills()) {
            return 1;
        } else {
            if (p1.getKills() == p2.getKills()) {
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

