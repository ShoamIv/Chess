public class King extends ConcretePiece {
    public King(Player p) {
        super((ConcretePlayer)p,false);
    }
    @Override
    public String getType() {
        return "♔";
    }
}
