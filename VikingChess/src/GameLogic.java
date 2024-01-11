public class GameLogic implements PlayableLogic {
    ConcretePiece[][] _board = new ConcretePiece[11][11];
    private final Position Corner1 = new Position(0, 0);
    private final Position Corner2 = new Position(0, 10);
    private final Position Corner3 = new Position(10, 0);
    private final Position Corner4 = new Position(10, 10);

    private int turns;
    private boolean Gameover;
    private final ConcretePlayer player1 = new ConcretePlayer(true);
    private final ConcretePlayer player2 = new ConcretePlayer(false);
    /**
    Simple Constructor.
     */
    public GameLogic() {
        turns = 0;
        init();
    }
    /**
    Initiating the game:
    *False init to false
    *Set Pieces on the board as required
     */
    public void init() {
        Gameover = false;
        //first,Set all the pieces on the board to null.
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                _board[i][j] = null;
            }
        }
        //Player2 Pieces
        for (int i = 3; i < 8; i++) {
            _board[i][0] = new Pawn(player2);
            _board[i][10] = new Pawn(player2);
            _board[0][i] = new Pawn(player2);
            _board[10][i] = new Pawn(player2);
        }
        _board[9][5] = new Pawn(player2);
        _board[1][5] = new Pawn(player2);
        _board[5][9] = new Pawn(player2);
        _board[5][1] = new Pawn(player2);
        //Player 1 Pieces:
        _board[5][3] = new Pawn(player1);
        _board[5][7] = new Pawn(player1);
        for (int i = 4; i < 7; i++) {
            _board[i][6] = new Pawn(player1);
        }
        for (int i = 3; i < 5; i++) {
            _board[i][5] = new Pawn(player1);
        }
        _board[5][5] = new King(player1);
        for (int i = 6; i < 8; i++) {
            _board[i][5] = new Pawn(player1);
        }
        for (int i = 4; i < 7; i++) {
            _board[i][4] = new Pawn(player1);
        }
    }

    /**
     * The main function of GameLogic.
     * Use variety of functions to maintain OPP principles.
     *First, move Checks if the move from position a to position b is legal.
     * Move checks if it is the player(i) turn (i=1/2).
     * If all condition to move is legal, move change the position of the piece.
     * Move summon CheckSurrounding and IsCorner to execute "kiils" if reuqired.
     * @param a The starting position of the piece.
     * @param b The destination position for the piece.
     * @return if the piece has moved return true, else false.
     */
    @Override
    public boolean move(Position a, Position b) {
        if (a.GetX() != b.GetX() && a.GetY() != b.GetY()) {
            return false;
        }
        if (b.GetX() > getBoardSize() || b.GetY() > getBoardSize()) return false;
        if (_board[b.GetX()][b.GetY()] != null) {
            return false;
        }
        if (!ValidPath(a, b)) return false;
        if (isSecondPlayerTurn() && getPieceAtPosition(a).getOwner().isPlayerOne()) {
            return false;
        }
        if (!isSecondPlayerTurn() && !getPieceAtPosition(a).getOwner().isPlayerOne()) {
            return false;
        }
        System.out.println(_board[a.GetX()][a.GetY()].getOwner().toString()+":("+b.GetX()+","+b.GetY()+")");
        Piece piece = getPieceAtPosition(a);
        ChangePosition(piece, a, b);
        IsCorner(b);
        CheckSurrounding(b);
        turns++;
        return true;
    }

    private void ChangePosition(Piece piece, Position a, Position b) {
        _board[b.GetX()][b.GetY()] = (ConcretePiece) piece;
        _board[a.GetX()][a.GetY()] = null;
    }
    private boolean ValidPath(Position a, Position b) {
        if (_board[a.GetX()][a.GetY()].getType().equals("♙")) {
            if (b.Equalto(Corner1)) return false;
            if (b.Equalto(Corner2)) return false;
            if (b.Equalto(Corner3)) return false;
            if (b.Equalto(Corner4)) return false;
        }else {
            Gameover = ValidCornerKing(a,b);
        }
        if (a.GetX() == b.GetX()) {
            if (a.GetY() < b.GetY()) {
                for (int i = a.GetY() + 1; i < b.GetY(); i++) {
                    if (_board[a.GetX()][i] != null) {
                        return false;
                    }
                }
            }
            if (a.GetY() > b.GetY()) {
                for (int i = b.GetY() + 1; i < a.GetY(); i++) {
                    if (_board[a.GetX()][i] != null) {
                        return false;
                    }
                }
            }
        }
        if (a.GetY() == b.GetY()) {
            if (a.GetX() < b.GetX()) {
                for (int i = a.GetX() + 1; i < b.GetX(); i++) {
                    if (_board[i][a.GetY()] != null) {
                        return false;
                    }
                }
            }
            if (a.GetX() > b.GetX()) {
                for (int i = b.GetX() + 1; i < a.GetX(); i++) {
                    if (_board[i][a.GetY()] != null) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean ValidCornerKing(Position a, Position b) {
        if (_board[a.GetX()][a.GetY()].getType().equals("♔")) {
            if (b.Equalto(Corner1)) return true;
            if (b.Equalto(Corner2)) return true;
            if (b.Equalto(Corner3)) return true;
            return b.Equalto(Corner4);
        }
        return false;
    }

    private void CheckSurrounding(Position a){
         Player p=_board[a.GetX()][a.GetY()].getOwner();
         //Cannot Eat with King.
         if(_board[a.GetX()][a.GetY()].getType().equals("♔"))return;else
    if(CheckEast(a,p)){
        if (_board[a.GetX()+1][a.GetY()]!=null) {
            if (_board[a.GetX() + 1][a.GetY()].getType().equals("♔")) {
                Gameover = EatKing(new Position(a.GetX() + 1, a.GetY()), p);
            } else if (_board[a.GetX() + 1][a.GetY()].getType().equals("♙")) _board[a.GetX() + 1][a.GetY()] = null;
        }
    }
        if(CheckWest(a,p)){
            if (_board[a.GetX()-1][a.GetY()]!=null) {
                if (_board[a.GetX() - 1][a.GetY()].getType().equals("♔")) {
                    Gameover = EatKing(new Position(a.GetX() - 1, a.GetY()), p);
                } else if (_board[a.GetX() - 1][a.GetY()].getType().equals("♙")) _board[a.GetX() - 1][a.GetY()] = null;
            }
        }
        if(CheckNorth(a, p)){
            if (_board[a.GetX()][a.GetY()-1]!=null) {
                if (_board[a.GetX()][a.GetY() - 1].getType().equals("♔")) {
                    Gameover = EatKing(new Position(a.GetX(), a.GetY() - 1), p);
                } else if (_board[a.GetX()][a.GetY() - 1].getType().equals("♙")) _board[a.GetX()][a.GetY() - 1] = null;
            }
        }
        if(CheckSouth(a,p)){
            if (_board[a.GetX()][a.GetY()+1]!=null) {
                if (_board[a.GetX()][a.GetY() + 1].getType().equals("♔")) {
                    Gameover = EatKing(new Position(a.GetX(), a.GetY() + 1), p);
                } else if (_board[a.GetX()][a.GetY() + 1].getType().equals("♙")) _board[a.GetX()][a.GetY() + 1] = null;
            }
        }
    }
    //Checking if Eatable, East side.
    private boolean CheckEast(Position a,Player p) {
         if (a.GetX()==10)return false; else
        if (_board[a.GetX()+1][a.GetY()]!=null && a.GetX()+1<=10){
            if(_board[a.GetX() + 1][a.GetY()].getOwner()!=p){
                if(a.GetX()==9){
                    if(_board[a.GetX()+1][a.GetY() ].getType().equals("♔")) return EatEdgeKing(new Position(a.GetX()+1,a.GetY()),p);
                    else {
                        return true;
                    }
                }
                else return _board[a.GetX() + 2][a.GetY()] != null && _board[a.GetX() + 2][a.GetY()].getOwner()==p;
            }
        }
        return false;
    }
    //Checking if Eatable, West side.
    private boolean CheckWest(Position a,Player p) {
         if(a.GetX()==0)return false;else
        if (_board[a.GetX()-1][a.GetY()] != null && a.GetX()-1>=0) {
            if (_board[a.GetX()-1][a.GetY()].getOwner()!=p) {
                if (a.GetX()==1){
                    if(_board[a.GetX()-1][a.GetY()].getType().equals("♔")) return EatEdgeKing(new Position(a.GetX()-1,a.GetY()),p);
                    else {
                        return true;
                    }
                }
                else return _board[a.GetX() - 2][a.GetY()] != null && _board[a.GetX() - 2][a.GetY()].getOwner() == p ;
            }
        }
        return false;
    }
    //Checking if Eatable, North side.
    private boolean CheckNorth(Position a,Player p) {
         if (a.GetY()==0)return false; else
        if (_board[a.GetX()][a.GetY() - 1] != null && a.GetY()-1>=0) {
            if (_board[a.GetX()][a.GetY() - 1].getOwner()!=p) {
                if (a.GetY()==1){
                    if(_board[a.GetX()][a.GetY() - 1].getType().equals("♔"))
                        return EatEdgeKing(new Position(a.GetX(),a.GetY()-1),p);
                    else {
                        return true;
                    }
                }else{
                return _board[a.GetX()][a.GetY() - 2] != null && (_board[a.GetX()][a.GetY() - 2].getOwner() == p);
                }
            }
        }
        return false;
    }
    //Checking if Eatable, South side.
    private boolean CheckSouth(Position a,Player p){
         if (a.GetY()==10)return false;else
                 if (_board[a.GetX()][a.GetY()+1] != null) {
                     if (_board[a.GetX()][a.GetY() +1].getOwner() != p) {
                         if (a.GetY()==9){
                             if(_board[a.GetX()][a.GetY() +1].getType().equals("♔")) return EatEdgeKing(new Position(a.GetX(),a.GetY()),p);
                             else {
                                 return true;
                             }
                         }
                         else
                             return _board[a.GetX()][a.GetY() + 2] != null && _board[a.GetX()][a.GetY() + 2].getOwner() == p;
                     }
             }
        return false;
    }
    private void IsCorner(Position a){
         if (_board[a.GetX()][a.GetY()].getType().equals("♔"))return;
         Player p=_board[a.GetX()][a.GetY()].getOwner();
         if((a.GetX()==10 &&a.GetY()==2) || (a.GetX()==8 && a.GetY()==0)) {
             NorthEastCorner(a,p);
             return;
         }
        if((a.GetX()==0 &&a.GetY()==2) || (a.GetX()==2 && a.GetY()==0)) {
            NorthWestCorner(a, p);
            return;
        }
        if((a.GetX()==8 &&a.GetY()==10) || (a.GetX()==10 && a.GetY()==8)) {
            SouthEastCorner(a, p);
            return;
        }
        if((a.GetX()==0 &&a.GetY()==8) || (a.GetX()==2 && a.GetY()==10)) {
            SouthWestCorner(a, p);
        }
    }
    private void NorthEastCorner(Position a, Player p){
         if(a.GetY()==2) {
             if (_board[a.GetX()][a.GetY() - 1] != null) {
                 if (_board[a.GetX()][a.GetY() - 1].getOwner() != p) {
                     if(_board[a.GetX()][a.GetY() - 1].getType().equals("♔"))return;
                     _board[a.GetX()][a.GetY() - 1] = null;
                 }
             }
         }else
             if(_board[a.GetX()+1][a.GetY()]!=null){
                 if(_board[a.GetX()+1][a.GetY()].getOwner()!=p) {
                     if(_board[a.GetX()+1][a.GetY()].getType().equals("♔")) return;
                     _board[a.GetX()+1][a.GetY()] = null;
                 }
             }
    }
    private void NorthWestCorner(Position a, Player p){
         if(a.GetY()==2){
        if(_board[a.GetX()][a.GetY()-1]!=null){
            if(_board[a.GetX()][a.GetY()-1].getOwner()!=p) {
                if(_board[a.GetX()][a.GetY() -1].getType().equals("♔")) return;
                _board[a.GetX()][a.GetY() - 1] = null;
            }
        }}else
        if(_board[a.GetX()-1][a.GetY()]!=null){
            if(_board[a.GetX()-1][a.GetY()].getOwner()!=p) {
                if(_board[a.GetX()-1][a.GetY()].getType().equals("♔")) return;
                _board[a.GetX()-1][a.GetY()] = null;
            }
        }
    }
    private void SouthEastCorner(Position a, Player p) {
        if (a.GetY() == 8){
            if (_board[a.GetX()][a.GetY() + 1] != null) {
                if (_board[a.GetX()][a.GetY() + 1].getOwner() != p) {
                    if(_board[a.GetX()][a.GetY() + 1].getType().equals("♔")) return;
                    _board[a.GetX()][a.GetY() + 1] = null;
                }
            }
    }else
        if (_board[a.GetX()+1][a.GetY()] != null) {
            if (_board[a.GetX()+1][a.GetY()].getOwner() != p) {
                if(_board[a.GetX()+1][a.GetY()].getType().equals("♔")) return;
                _board[a.GetX()+1][a.GetY()] = null;
            }
        }
    }
    private void SouthWestCorner(Position a, Player p){
         if(a.GetX()==0){
        if(_board[a.GetX()][a.GetY()+1]!=null){
            if(_board[a.GetX()][a.GetY()+1].getOwner()!=p) {
                if(_board[a.GetX()][a.GetY()+1].getType().equals("♔")) return;
                _board[a.GetX()][a.GetY()+1] = null;
            }
        }}else
        if(_board[a.GetX()-1][a.GetY()]!=null){
            if(_board[a.GetX()-1][a.GetY()].getOwner()!=p) {
                if(_board[a.GetX()-1][a.GetY()].getType().equals("♔")) return;
                _board[a.GetX()-1][a.GetY()] = null;
            }
        }
    }

    private boolean EatKing(Position King,Player p){
        if(King.GetX()==0 ||King.GetX()==10 || King.GetY()==0 || King.GetY()==10 ){ return EatEdgeKing(King,p);}
         //First Check that There are 4 Pawn Circling the King.
         if(_board[King.GetX()+1][King.GetY()]!=null && _board[King.GetX()-1][King.GetY()]!=null && _board[King.GetX()][King.GetY()+1]!=null && _board[King.GetX()][King.GetY()-1]!=null ) {
             if (_board[King.GetX()+1][King.GetY()].getOwner()==p && _board[King.GetX()-1][King.GetY()].getOwner()==p && _board[King.GetX()][King.GetY()+1].getOwner()==p && _board[King.GetX()][King.GetY()-1].getOwner()==p) {
                 _board[King.GetX()][King.GetY()]=null;}
             Gameover=true;
             return true;
         }else {
             return false;}
    }
private boolean EatEdgeKing(Position King,Player p){
        if(King.GetX()==0 && _board[King.GetX()+1][King.GetY()]!=null && _board[King.GetX()][King.GetY()+1]!=null &&_board[King.GetX()][King.GetY()-1 ]!=null){
           if(King.GetX()==0 && _board[King.GetX()+1][King.GetY()].getOwner()==p && _board[King.GetX()][King.GetY()+1].getOwner()==p &&_board[King.GetX()][King.GetY()-1].getOwner()==p){
           _board[King.GetX()][King.GetY()]=null;
           Gameover=true;
           return true;
           }
        }
        if(King.GetX()==10 && _board[King.GetX()-1][King.GetY()]!=null && _board[King.GetX()][King.GetY()+1]!=null &&_board[King.GetX()][King.GetY()-1 ]!=null){
            if(_board[King.GetX()-1][King.GetY()].getOwner()==p && _board[King.GetX()][King.GetY()+1].getOwner()==p &&_board[King.GetX()][King.GetY()-1].getOwner()==p) {
                _board[King.GetX()][King.GetY()] = null;
                Gameover=true;
                return true;
             }
            }
            if(King.GetY()==0 && _board[King.GetX()+1][King.GetY()]!=null && _board[King.GetX()-1][King.GetY()]!=null &&_board[King.GetX()][King.GetY()+1]!=null){
                if(_board[King.GetX()+1][King.GetY()].getOwner()==p && _board[King.GetX()-1][King.GetY()].getOwner()==p &&_board[King.GetX()][King.GetY()+1].getOwner()==p) {
                    _board[King.GetX()][King.GetY()] = null;
                    Gameover=true;
                    return true;
                 }
                }
                if(King.GetY()==10 && _board[King.GetX()+1][King.GetY()]!=null && _board[King.GetX()-1][King.GetY()]!=null &&_board[King.GetX()][King.GetY()-1 ]!=null){
                    if( _board[King.GetX()+1][King.GetY()].getOwner()==p && _board[King.GetX()-1][King.GetY()].getOwner()==p &&_board[King.GetX()][King.GetY()-1].getOwner()==p){
                        _board[King.GetX()][King.GetY()]=null;
                        Gameover=true;
                        return true;

                    }
                }
        return false;
    }
        @Override
    public Piece getPieceAtPosition(Position position) {
      return _board[position.GetX()][position.GetY()];
    }

    @Override
    public Player getFirstPlayer() {
        return player1;
    }

    @Override
    public Player getSecondPlayer() {
        return player2;
    }

    @Override
    public boolean isGameFinished() {
        if (Gameover) {
            if (isSecondPlayerTurn()) {
                player1.IncreaseWins();
                return Gameover;
            }
                player2.IncreaseWins();
                return Gameover;
            }
        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return turns%2==0;
    }

    @Override
    public void reset() {
   turns=0;
   init();
    }

    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return 11;
    }
}
