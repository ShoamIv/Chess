public class ConcretePlayer  implements Player {
    private boolean _playerOne;
    private int _win;
    private ConcretePiece[] _pieces;

    @Override
    public String toString() {
        if(_playerOne){
            return "1";
        }else return "2";
    }
    public void printPieces(){
        for (int i = 0; i<_pieces.length; i++){
            System.out.println("piece ID "+_pieces[i].getId() + "had ");
            _pieces[i].printPositions();
        }
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
    public void addConcretePiece(ConcretePiece concretePiece){
        if (concretePiece.getType().equals("♙")){
            addPawn((Pawn) concretePiece);
        }else {
            addKing((King) concretePiece);
        }
        System.out.println(concretePiece.toString() + "added successfully");
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
