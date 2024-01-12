public class King extends ConcretePiece {
    private static String t = "♔";
    public King(Player p) {
        super((ConcretePlayer)p);
    }
    public King(Player p, int index, Position position){
        super((ConcretePlayer)p, index, position, t);
    }
    @Override
    public String getType() {
        return t;
    }
}
