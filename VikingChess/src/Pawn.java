public class Pawn extends ConcretePiece {
    private static String t = "♙";
    private int kills = 0;
    public Pawn(Player p) {
        super((ConcretePlayer)p);
    }
    public Pawn(Player p, int index){
        super((ConcretePlayer)p, index);
    }
    public Pawn(Player p, int index, Position position){
        super((ConcretePlayer)p, index, position,t);
    }
    @Override
    public String getType() {
        return t;
    }
    public void kill(){
        kills++;
        System.out.println("kills: "+kills);
    }
}

