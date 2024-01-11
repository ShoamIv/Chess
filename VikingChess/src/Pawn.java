public class Pawn extends ConcretePiece {
    public Pawn(Player p) {
        super((ConcretePlayer)p,true);
    }

    @Override
    public String getType() {
        return "♙";
    }
}
