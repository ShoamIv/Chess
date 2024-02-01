public class King extends ConcretePiece {
    private final static String t = "♔";
    public King(Player p, int index) {
        super((ConcretePlayer) p, index, t);
    }
    public King(King king){
        super(king.getOwner(),king.getId());
    }    @Override
    public String getType() {
        return t;
    }
}
