import java.util.Arrays;

public class GameLogic implements PlayableLogic {
    ConcretePiece[][] _board = new ConcretePiece[getBoardSize()][getBoardSize()];
    private final Position Corner1 = new Position(0, 0);
    private final Position Corner2 = new Position(0, 10);
    private final Position Corner3 = new Position(10, 0);
    private final Position Corner4 = new Position(10, 10);

    private int turns;
    private boolean gameover;
    private final ConcretePlayer player1 = new ConcretePlayer(true);
    private final ConcretePlayer player2 = new ConcretePlayer(false);
    private Position[][] positions = new Position[11][11];

    /**
     * Simple Constructor.
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
    /**
     * Initiating the game:
     * False init to false
     * Set Pieces on the board as required
     */
    public void init() {
        resetBoard();
        setPieces();
        setPositions();
    }

    private void resetBoard() {         //first,Set pieces to null and game-state to false.
        turns = 0;
        gameover = false;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                _board[i][j] = null;
            }
        }
    }

    private void setPositions() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                positions[i][j] = new Position(i, j);
            }
        }
        /*
        for (int i = 3; i < 8; i++) {
            //p2
            positions[i][0] = new Position(i, 0);
            positions[i][0] = new Position(i, 10);
            if (i < 5) {
                if (i == 3) {
                    positions[i + 2][i] = new Position(i + 2, i);
                    positions[i + 2][i + 4] = new Position(i + 2, i + 4);
                }
                if (i == 4) {
                    for (int j = 2; j < 5; j++) {
                        positions[j + 2][i] = new Position(j + 2, i);
                        positions[j + 2][i + 2] = new Position(j + 2, i + 2);
                    }
                }
                positions[0][i] = new Position(0, i);
                positions[10][i] = new Position(10, i);
            }
            if (i == 5) {
                for (int j = 5; j < 10; j++) {
                    if (j != 7) {
                        positions[j - 2][i] = new Position(j - 2, i);
                    } else {
                        positions[j - 2][i] = new Position(j - 2, i);
                    }
                }
                positions[i][1] = new Position(i, 1);
                positions[i][9] = new Position(i, 9);
                for (int j = 0; j < 2; j++) {
                    positions[j][i] = new Position(j, i);
                    positions[j + 9][i] = new Position(j + 9, i);
                }
            }
            if (i > 5) {
                positions[0][i] = new Position(0, i);
                positions[10][i] = new Position(10, i);
            }
        }
    */
    }

    private void setPieces() { // sets ID and owner for every ConcretePiece on the board
        for (int i = 3; i < 8; i++) {
            //p2
            _board[i][0] = new Pawn(player2, (i - 2), new Position(i, 0)); // p2 1-5
            _board[i][0] = new Pawn(player2, (i + 17), new Position(i, 10)); // p2 20-24
            if (i < 5) {
                if (i == 3) {
                    _board[i + 2][i] = new Pawn(player1, (i - 2), new Position(i + 2, i));// p1 1
                    _board[i + 2][i + 4] = new Pawn(player1, (i + 10), new Position(i + 2, i + 4));// p1 13
                }
                if (i == 4) {
                    for (int j = 2; j < 5; j++) {
                        _board[j + 2][i] = new Pawn(player1, (j), new Position(j + 2, i));// p1 2-4
                        _board[j + 2][i + 2] = new Pawn(player1, (j + 8), new Position(j + 2, i + 2));// p1 10-12
                    }
                }
                _board[0][i] = new Pawn(player2, (2 * i) + 1, new Position(0, i));// p2 7, 9
                _board[10][i] = new Pawn(player2, (2 * i) + 2, new Position(10, i));// p2 8,10
            }
            if (i == 5) {
                for (int j = 5; j < 10; j++) {
                    if (j != 7) {// p1 5-9
                        _board[j - 2][i] = new Pawn(player1, j, new Position(j - 2, i));
                    } else {
                        _board[j - 2][i] = new King(player1, j, new Position(j - 2, i));
                    }
                }
                _board[i][1] = new Pawn(player2, i + 1, new Position(i, 1));// p2 6
                _board[i][9] = new Pawn(player2, i + 4, new Position(i, 9));// p2 19
                for (int j = 0; j < 2; j++) {
                    _board[j][i] = new Pawn(player2, j + 11, new Position(j, i));// p2 11, 12
                    _board[j + 9][i] = new Pawn(player2, j + 13, new Position(j + 9, i));// p2 13, 14
                }
            }
            if (i > 5) {
                _board[0][i] = new Pawn(player2, (2 * i) + 3, new Position(0, i));// p2 15, 17
                _board[10][i] = new Pawn(player2, (2 * i) + 4, new Position(10, i));// p2 16, 18
            }
        }
    }

    /**
     * The main function of GameLogic.
     * Use variety of functions to maintain OPP principles.
     * First, move Checks if the move from position a to position b is legal.
     * Move checks if it is the player(i) turn (i=1/2).
     * If all condition to move is legal, move change the position of the piece.
     * Move summon CheckSurrounding and IsCorner to execute "kiils" if reuqired.
     *
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
        System.out.println(_board[a.GetX()][a.GetY()].getOwner().toString() + ":(" + b.GetX() + "," + b.GetY() + ")");
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
        addPosition(b, (ConcretePiece) piece);
    }

    private void addPosition(Position p, ConcretePiece concretePiece) {
        concretePiece.addPosition(p);
        positions[p.GetX()][p.GetY()].set_pieces(concretePiece.getId());
    }

    private boolean ValidPath(Position a, Position b) {
        if (_board[a.GetX()][a.GetY()].isKing()) {
            if (b.Equalto(Corner1)) return false;
            if (b.Equalto(Corner2)) return false;
            if (b.Equalto(Corner3)) return false;
            if (b.Equalto(Corner4)) return false;
        } else {
            gameover = ValidCornerKing(a, b);
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
        if (_board[a.GetX()][a.GetY()].isKing()) {
            if (b.Equalto(Corner1)) return true;
            if (b.Equalto(Corner2)) return true;
            if (b.Equalto(Corner3)) return true;
            return b.Equalto(Corner4);
        }
        return false;
    }

    private void addEat(Position a) {
        ((Pawn) _board[a.GetX()][a.GetY()]).kill();
    }

    private void CheckSurrounding(Position a) {
        Player p = _board[a.GetX()][a.GetY()].getOwner();
        //Cannot Eat with King.
        if (_board[a.GetX()][a.GetY()].isKing()) return;
        else if (CheckEast(a, p)) {
            if (_board[a.GetX() + 1][a.GetY()] != null) {
                if (_board[a.GetX() + 1][a.GetY()].isKing()) {
                    gameover = EatKing(new Position(a.GetX() + 1, a.GetY()), p);
                    addEat(a);
                } else if (_board[a.GetX() + 1][a.GetY()].isPawn()) {
                    _board[a.GetX() + 1][a.GetY()] = null;
                    addEat(a);
                }
            }
        }
        if (CheckWest(a, p)) {
            if (_board[a.GetX() - 1][a.GetY()] != null) {
                if (_board[a.GetX() - 1][a.GetY()].isKing()) {
                    gameover = EatKing(new Position(a.GetX() - 1, a.GetY()), p);
                    addEat(a);
                } else if (_board[a.GetX() - 1][a.GetY()].isPawn()) {
                    _board[a.GetX() - 1][a.GetY()] = null;
                    addEat(a);
                }
            }
        }
        if (CheckNorth(a, p)) {
            if (_board[a.GetX()][a.GetY() - 1] != null) {
                if (_board[a.GetX()][a.GetY() - 1].isKing()) {
                    gameover = EatKing(new Position(a.GetX(), a.GetY() - 1), p);
                    addEat(a);
                } else if (_board[a.GetX()][a.GetY() - 1].isPawn()) {
                    _board[a.GetX()][a.GetY() - 1] = null;
                    addEat(a);
                }
            }
        }
        if (CheckSouth(a, p)) {
            if (_board[a.GetX()][a.GetY() + 1] != null) {
                if (_board[a.GetX()][a.GetY() + 1].isKing()) {
                    gameover = EatKing(new Position(a.GetX(), a.GetY() + 1), p);
                    addEat(a);
                } else if (_board[a.GetX()][a.GetY() + 1].isPawn()) {
                    _board[a.GetX()][a.GetY() + 1] = null;
                    addEat(a);
                }
            }
        }
    }

    //Checking if Eatable, East side.
    private boolean CheckEast(Position a, Player p) {
        if (a.GetX() == 10) return false;
        else if (_board[a.GetX() + 1][a.GetY()] != null && a.GetX() + 1 <= 10) {
            if (_board[a.GetX() + 1][a.GetY()].getOwner() != p) {
                if (a.GetX() == 9) {
                    if (_board[a.GetX() + 1][a.GetY()].isKing())
                        return EatEdgeKing(new Position(a.GetX() + 1, a.GetY()), p);
                    else {
                        return true;
                    }
                } else return _board[a.GetX() + 2][a.GetY()] != null && _board[a.GetX() + 2][a.GetY()].getOwner() == p;
            }
        }
        return false;
    }

    //Checking if Eatable, West side.
    private boolean CheckWest(Position a, Player p) {
        if (a.GetX() == 0) return false;
        else if (_board[a.GetX() - 1][a.GetY()] != null && a.GetX() - 1 >= 0) {
            if (_board[a.GetX() - 1][a.GetY()].getOwner() != p) {
                if (a.GetX() == 1) {
                    if (_board[a.GetX() - 1][a.GetY()].isKing())
                        return EatEdgeKing(new Position(a.GetX() - 1, a.GetY()), p);
                    else {
                        return true;
                    }
                } else return _board[a.GetX() - 2][a.GetY()] != null && _board[a.GetX() - 2][a.GetY()].getOwner() == p;
            }
        }
        return false;
    }

    //Checking if Eatable, North side.
    private boolean CheckNorth(Position a, Player p) {
        if (a.GetY() == 0) return false;
        else if (_board[a.GetX()][a.GetY() - 1] != null && a.GetY() - 1 >= 0) {
            if (_board[a.GetX()][a.GetY() - 1].getOwner() != p) {
                if (a.GetY() == 1) {
                    if (_board[a.GetX()][a.GetY() - 1].isKing())
                        return EatEdgeKing(new Position(a.GetX(), a.GetY() - 1), p);
                    else {
                        return true;
                    }
                } else {
                    return _board[a.GetX()][a.GetY() - 2] != null && (_board[a.GetX()][a.GetY() - 2].getOwner() == p);
                }
            }
        }
        return false;
    }

    //Checking if Eatable, South side.
    private boolean CheckSouth(Position a, Player p) {
        if (a.GetY() == 10) return false;
        else if (_board[a.GetX()][a.GetY() + 1] != null) {
            if (_board[a.GetX()][a.GetY() + 1].getOwner() != p) {
                if (a.GetY() == 9) {
                    if (_board[a.GetX()][a.GetY() + 1].isKing())
                        return EatEdgeKing(new Position(a.GetX(), a.GetY()), p);
                    else {
                        return true;
                    }
                } else
                    return _board[a.GetX()][a.GetY() + 2] != null && _board[a.GetX()][a.GetY() + 2].getOwner() == p;
            }
        }
        return false;
    }

    private void IsCorner(Position a) {
        if (_board[a.GetX()][a.GetY()].isKing()) return;
        Player p = _board[a.GetX()][a.GetY()].getOwner();
        if ((a.GetX() == 10 && a.GetY() == 2) || (a.GetX() == 8 && a.GetY() == 0)) {
            NorthEastCorner(a, p);
            return;
        }
        if ((a.GetX() == 0 && a.GetY() == 2) || (a.GetX() == 2 && a.GetY() == 0)) {
            NorthWestCorner(a, p);
            return;
        }
        if ((a.GetX() == 8 && a.GetY() == 10) || (a.GetX() == 10 && a.GetY() == 8)) {
            SouthEastCorner(a, p);
            return;
        }
        if ((a.GetX() == 0 && a.GetY() == 8) || (a.GetX() == 2 && a.GetY() == 10)) {
            SouthWestCorner(a, p);
        }
    }

    private void NorthEastCorner(Position a, Player p) {
        if (a.GetY() == 2) {
            if (_board[a.GetX()][a.GetY() - 1] != null) {
                if (_board[a.GetX()][a.GetY() - 1].getOwner() != p) {
                    if (_board[a.GetX()][a.GetY() - 1].isKing()) return;
                    _board[a.GetX()][a.GetY() - 1] = null;
                }
            }
        } else if (_board[a.GetX() + 1][a.GetY()] != null) {
            if (_board[a.GetX() + 1][a.GetY()].getOwner() != p) {
                if (_board[a.GetX() + 1][a.GetY()].isKing()) return;
                _board[a.GetX() + 1][a.GetY()] = null;
            }
        }
    }

    private void NorthWestCorner(Position a, Player p) {
        if (a.GetY() == 2) {
            if (_board[a.GetX()][a.GetY() - 1] != null) {
                if (_board[a.GetX()][a.GetY() - 1].getOwner() != p) {
                    if (_board[a.GetX()][a.GetY() - 1].isKing()) return;
                    _board[a.GetX()][a.GetY() - 1] = null;
                }
            }
        } else if (_board[a.GetX() - 1][a.GetY()] != null) {
            if (_board[a.GetX() - 1][a.GetY()].getOwner() != p) {
                if (_board[a.GetX() - 1][a.GetY()].isKing()) return;
                _board[a.GetX() - 1][a.GetY()] = null;
            }
        }
    }

    private void SouthEastCorner(Position a, Player p) {
        if (a.GetY() == 8) {
            if (_board[a.GetX()][a.GetY() + 1] != null) {
                if (_board[a.GetX()][a.GetY() + 1].getOwner() != p) {
                    if (_board[a.GetX()][a.GetY() + 1].isKing()) return;
                    _board[a.GetX()][a.GetY() + 1] = null;
                }
            }
        } else if (_board[a.GetX() + 1][a.GetY()] != null) {
            if (_board[a.GetX() + 1][a.GetY()].getOwner() != p) {
                if (_board[a.GetX() + 1][a.GetY()].isKing()) return;
                _board[a.GetX() + 1][a.GetY()] = null;
            }
        }
    }

    private void SouthWestCorner(Position a, Player p) {
        if (a.GetX() == 0) {
            if (_board[a.GetX()][a.GetY() + 1] != null) {
                if (_board[a.GetX()][a.GetY() + 1].getOwner() != p) {
                    if (_board[a.GetX()][a.GetY() + 1].isKing()) return;
                    _board[a.GetX()][a.GetY() + 1] = null;
                }
            }
        } else if (_board[a.GetX() - 1][a.GetY()] != null) {
            if (_board[a.GetX() - 1][a.GetY()].getOwner() != p) {
                if (_board[a.GetX() - 1][a.GetY()].isKing()) return;
                _board[a.GetX() - 1][a.GetY()] = null;
            }
        }
    }

    private boolean EatKing(Position King, Player p) {
        if (King.GetX() == 0 || King.GetX() == 10 || King.GetY() == 0 || King.GetY() == 10) {
            return EatEdgeKing(King, p);
        }
        //First Check that There are 4 Pawn Circling the King.
        if (_board[King.GetX() + 1][King.GetY()] != null && _board[King.GetX() - 1][King.GetY()] != null && _board[King.GetX()][King.GetY() + 1] != null && _board[King.GetX()][King.GetY() - 1] != null) {
            if (_board[King.GetX() + 1][King.GetY()].getOwner() == p && _board[King.GetX() - 1][King.GetY()].getOwner() == p && _board[King.GetX()][King.GetY() + 1].getOwner() == p && _board[King.GetX()][King.GetY() - 1].getOwner() == p) {
                _board[King.GetX()][King.GetY()] = null;
            }
            gameover = true;
            return true;
        } else {
            return false;
        }
    }

    private boolean EatEdgeKing(Position King, Player p) {
        if (King.GetX() == 0 && _board[King.GetX() + 1][King.GetY()] != null && _board[King.GetX()][King.GetY() + 1] != null && _board[King.GetX()][King.GetY() - 1] != null) {
            if (King.GetX() == 0 && _board[King.GetX() + 1][King.GetY()].getOwner() == p && _board[King.GetX()][King.GetY() + 1].getOwner() == p && _board[King.GetX()][King.GetY() - 1].getOwner() == p) {
                _board[King.GetX()][King.GetY()] = null;
                gameover = true;
                return true;
            }
        }
        if (King.GetX() == 10 && _board[King.GetX() - 1][King.GetY()] != null && _board[King.GetX()][King.GetY() + 1] != null && _board[King.GetX()][King.GetY() - 1] != null) {
            if (_board[King.GetX() - 1][King.GetY()].getOwner() == p && _board[King.GetX()][King.GetY() + 1].getOwner() == p && _board[King.GetX()][King.GetY() - 1].getOwner() == p) {
                _board[King.GetX()][King.GetY()] = null;
                gameover = true;
                return true;
            }
        }
        if (King.GetY() == 0 && _board[King.GetX() + 1][King.GetY()] != null && _board[King.GetX() - 1][King.GetY()] != null && _board[King.GetX()][King.GetY() + 1] != null) {
            if (_board[King.GetX() + 1][King.GetY()].getOwner() == p && _board[King.GetX() - 1][King.GetY()].getOwner() == p && _board[King.GetX()][King.GetY() + 1].getOwner() == p) {
                _board[King.GetX()][King.GetY()] = null;
                gameover = true;
                return true;
            }
        }
        if (King.GetY() == 10 && _board[King.GetX() + 1][King.GetY()] != null && _board[King.GetX() - 1][King.GetY()] != null && _board[King.GetX()][King.GetY() - 1] != null) {
            if (_board[King.GetX() + 1][King.GetY()].getOwner() == p && _board[King.GetX() - 1][King.GetY()].getOwner() == p && _board[King.GetX()][King.GetY() - 1].getOwner() == p) {
                _board[King.GetX()][King.GetY()] = null;
                gameover = true;
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
        if (gameover) {
            if (isSecondPlayerTurn()) {
                player1.IncreaseWins();
                printStats(player1, player2);
                return gameover;
            }
            player2.IncreaseWins();
            printStats(player2, player1);
            return gameover;
        }
        return false;
    }

    @Override
    public boolean isSecondPlayerTurn() {
        return turns % 2 == 0;
    }

    @Override
    public void reset() {
        turns = 0;
        init();
    }

    @Override
    public void undoLastMove() {

    }

    @Override
    public int getBoardSize() {
        return 11;
    }

    private void printStats(Player w, Player l) {
        printStatsBySteps(w, l);
        printStatsByKills();
        printStatsBySquares();
        printStatsByPieces();
    }

    // private void statsBy
    private void printStatsBySteps(Player w, Player l) {
        Arrays.sort(((ConcretePlayer) w).get_pieces(), new SortBySteps());
        if (w.isPlayerOne()) {
            for (int i = 0; i < 13; i++) {
                System.out.println(((ConcretePlayer) w).get_pieces()[i].toString());
            }
        }
    }

    private void printStatsByKills() {
        return;
    }

    private void printStatsBySquares() {
        return;
    }

    private void printStatsByPieces() {
        return;
    }
}
