public abstract class ConcretePiece  implements Piece  {
    private Player owner;
    private boolean Capture;
    public ConcretePiece(Player p,boolean cap){
    owner=p;
    Capture=cap;
    }
    @Override
    public Player getOwner() {
        return owner;
    }

}
