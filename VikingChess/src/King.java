public class King extends ConcretePiece {
    private final static String t = "♔";

    public King(Player p, int index) {
        super((ConcretePlayer) p, index, t);
    }

    @Override
    public String getType() {
        return t;
    }
}
