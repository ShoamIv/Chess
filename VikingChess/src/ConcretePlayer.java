public class ConcretePlayer  implements Player {
    private boolean _playerOne;
    private int _win;

    private ConcretePiece[] _pieces;
    /**
     * Returns a string representation of the Concrete player of the format D/A according to the player's role.
     *
     * @return a string representation of the ConcretePiece.
     */
    @Override
    public String toString() {
        if(_playerOne){
            return "D";
        }else return "A";
    }
    /**
     *  returns an array of the pieces aggregated by this Player.
     *
     * @return an array of ConcretePiece.
     */
    public ConcretePiece[] get_pieces() {
        return _pieces;
    }

    public ConcretePlayer(boolean playerOne){
        this._playerOne=playerOne;
        this._win=0;
        if (playerOne) {
            _pieces = new ConcretePiece[13];
        }else {
            _pieces = new ConcretePiece[24];
        }
    }
    /**
     *  defines the pieces in _pieces explicitly using helper functions addPawn(), addKing()
     */
    public void addConcretePiece(ConcretePiece concretePiece){
        if (concretePiece.isPawn()){
            addPawn((Pawn) concretePiece);
        }else {
            addKing((King) concretePiece);
        }
    }
    private void addPawn(Pawn pawn){
        _pieces[pawn.getId() - 1] = pawn;
    }
    private void addKing(King king){
        _pieces[king.getId() - 1] = king;
    }
    @Override
    public boolean isPlayerOne() {
        return this._playerOne;
    }
    @Override
    public int getWins() {
        return _win;
    }
    public int IncreaseWins(){return _win++;}
}
