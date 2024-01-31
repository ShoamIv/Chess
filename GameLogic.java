import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GameLogic implements PlayableLogic {
    ConcretePiece[][] _board = new ConcretePiece[getBoardSize()][getBoardSize()];
    private final Position Corner1 = new Position(0, 0);
    private final Position Corner2 = new Position(0, 10);
    private final Position Corner3 = new Position(10, 0);
    private final Position Corner4 = new Position(10, 10);
    private int turns;
    private boolean gameover;
    private int stats = 0;
    private final ConcretePlayer player1 = new ConcretePlayer(true);
    private final ConcretePlayer player2 = new ConcretePlayer(false);
    private Position[][] positions = new Position[11][11];
    private ArrayList<Position>[] positionsArrayList = new ArrayList[37];

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
        initArrayList();
        setPositionsArrayList();
        setPositions();
        setBoard2DArr();
        stats = 0;
    }

    private void initArrayList() {
        for (int i = 0; i < 37; i++) {
            this.positionsArrayList[i] = new ArrayList<Position>();
        }
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
    }

    private void setPositionsArrayList() {
        for (int i = 3; i < 8; i++) {
            // p2 1-5
            positionsArrayList[i + 10].add(new Position(i, 0));
            // p2 20-24
            positionsArrayList[i + 29].add(new Position(i, 10));
            if (i < 5) {
                if (i == 3) {
// p1 1
                    positionsArrayList[i - 3].add(new Position(i + 2, i));
                    // p1 13
                    positionsArrayList[i + 9].add(new Position(i + 2, i + 4));
                }
                if (i == 4) {
                    for (int j = 2; j < 5; j++) {
                        // p1 2-4
                        positionsArrayList[j - 1].add(new Position(j + 2, i));
                        // p1 10-12
                        positionsArrayList[j + 7].add(new Position(j + 2, i + 2));
                    }
                }
// p2 7, 9
                positionsArrayList[(2 * i) + 13].add(new Position(0, i));
// p2 8,10
                positionsArrayList[(2 * i) + 14].add(new Position(10, i));
            }
            if (i == 5) {
                for (int j = 5; j < 10; j++) {
                    if (j != 7) {// p1 5-9

                        positionsArrayList[j - 1].add(new Position(j - 2, i));
                    } else {
                        positionsArrayList[j - 1].add(new Position(j - 2, i));
                    }
                }
                // p2 6
                positionsArrayList[i + 13].add(new Position(i, 1));
                // p2 19
                positionsArrayList[i + 26].add(new Position(i, 9));
                for (int j = 0; j < 2; j++) {
                    // p2 11, 12
                    positionsArrayList[j + 23].add(new Position(j, i));
                    // p2 13, 14
                    positionsArrayList[j + 25].add(new Position(j + 9, i));
                }
            }
            if (i > 5) {
                // p2 15, 17
                positionsArrayList[(2 * i) + 15].add(new Position(0, i));
                // p2 16, 18
                positionsArrayList[(2 * i) + 16].add(new Position(10, i));
            }
        }
    }

    private void setBoard2DArr() { // sets ID and owner for every ConcretePiece on the board
        for (int i = 3; i < 8; i++) {
            //p2
            _board[i][0] = new Pawn(player2, (i - 2)); // p2 1-5
            positions[i][0].set_pieces(i + 11);
            _board[i][10] = new Pawn(player2, (i + 17)); // p2 20-24
            positions[i][10].set_pieces(i + 30);
            if (i < 5) {
                if (i == 3) {
                    _board[i + 2][i] = new Pawn(player1, (i - 2));// p1 1
                    positions[i + 2][i].set_pieces(i - 2);
                    _board[i + 2][i + 4] = new Pawn(player1, (i + 10));// p1 13
                    positions[i + 2][i + 4].set_pieces(i + 23);
                }
                if (i == 4) {
                    for (int j = 2; j < 5; j++) {
                        _board[j + 2][i] = new Pawn(player1, (j));// p1 2-4
                        positions[j + 2][i].set_pieces(j);
                        _board[j + 2][i + 2] = new Pawn(player1, (j + 8));// p1 10-12
                        positions[j + 2][i + 2].set_pieces(j + 8);
                    }
                }
                _board[0][i] = new Pawn(player2, (2 * i) + 1);// p2 7, 9
                positions[0][i].set_pieces(2 * i + 14);
                _board[10][i] = new Pawn(player2, (2 * i) + 2);// p2 8,10
                positions[10][i].set_pieces(2 * i + 15);
            }
            if (i == 5) {
                for (int j = 5; j < 10; j++) {
                    if (j != 7) {// p1 5-9
                        _board[j - 2][i] = new Pawn(player1, j);
                        positions[j - 2][i].set_pieces(j);
                    } else {
                        _board[j - 2][i] = new King(player1, j);
                        positions[j - 2][i].set_pieces(j);
                    }
                }
                _board[i][1] = new Pawn(player2, i + 1);// p2 6
                positions[i][1].set_pieces(i + 14);
                _board[i][9] = new Pawn(player2, i + 14);// p2 19
                positions[i][9].set_pieces(i + 27);
                for (int j = 0; j < 2; j++) {
                    _board[j][i] = new Pawn(player2, j + 11);// p2 11, 12
                    positions[j][i].set_pieces(j + 24);
                    _board[j + 9][i] = new Pawn(player2, j + 13);// p2 13, 14
                    positions[j + 9][i].set_pieces(j + 26);
                }
            }
            if (i > 5) {
                _board[0][i] = new Pawn(player2, (2 * i) + 3);// p2 15, 17
                positions[0][i].set_pieces(2 * i + 16);
                _board[10][i] = new Pawn(player2, (2 * i) + 4);// p2 16, 18
                positions[10][i].set_pieces(2 * i + 17);
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
        if ((isSecondPlayerTurn() && getPieceAtPosition(a) == null) || (isSecondPlayerTurn() && getPieceAtPosition(a).getOwner().isPlayerOne())) {
            return false;
        }
        if (!isSecondPlayerTurn() && !getPieceAtPosition(a).getOwner().isPlayerOne()) {
            return false;
        }
        Piece piece = getPieceAtPosition(a);
        ChangePosition(piece, a, b);
        IsCorner(b);
        CheckSurrounding(b);
        turns++;
        isGameFinished();
        return true;
    }

    private void ChangePosition(Piece piece, Position a, Position b) {
        addPosition(b, (ConcretePiece) piece);
        _board[b.GetX()][b.GetY()] = (ConcretePiece) piece;
        _board[a.GetX()][a.GetY()] = null;
    }

    private void addPositionToArrayList(Position p, int id) {
        positionsArrayList[id].add(p);
    }

    private void addPosition(Position p, ConcretePiece concretePiece) {
        if (concretePiece.getOwner().isPlayerOne()) {
            addSquares(concretePiece, calcSquares(positionsArrayList[concretePiece.getId() - 1].getLast(), p));
            addPositionToArrayList(p, concretePiece.getId() - 1);
            positions[p.GetX()][p.GetY()].set_pieces(concretePiece.getId());
        } else {
            addSquares(concretePiece, calcSquares(positionsArrayList[concretePiece.getId() + 12].getLast(), p));
            addPositionToArrayList(p, concretePiece.getId() + 12);
            positions[p.GetX()][p.GetY()].set_pieces(concretePiece.getId() + 13);
        }
    }

    private void addSquares(ConcretePiece concretePiece, int squares) {
        concretePiece.add_squares(squares);
    }

    private int calcSquares(Position src, Position dst) {
        int x_diff = Math.abs(src.GetX() - dst.GetX());
        int y_diff = Math.abs(src.GetY() - dst.GetY());
        return (x_diff + y_diff);
    }

    private ArrayList<Position> steppedPositions() {
        ArrayList<Position> steppedPositionsArrayList = new ArrayList<Position>();
        for (int i = 0; i < getBoardSize(); i++) {
            for (int j = 0; j < getBoardSize(); j++) {
                if (positions[i][j].get_pieces() > 1) {
                    steppedPositionsArrayList.add(positions[i][j]);
                }
            }
        }
        return steppedPositionsArrayList;
    }

    private boolean ValidPath(Position a, Position b) {
        if (!_board[a.GetX()][a.GetY()].isKing()) {
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
        ((ConcretePlayer) ((Pawn) _board[a.GetX()][a.GetY()]).getOwner()).addConcretePiece(_board[a.GetX()][a.GetY()]);
    }

    private void CheckSurrounding(Position a) {
        Player p = _board[a.GetX()][a.GetY()].getOwner();
        //Cannot Eat with King.
        if (_board[a.GetX()][a.GetY()].isKing()) return;
        else if (CheckEast(a, p)) {
            if (_board[a.GetX() + 1][a.GetY()] != null) {
                if (_board[a.GetX() + 1][a.GetY()].isKing()) {
                    gameover = EatKing(new Position(a.GetX() + 1, a.GetY()), p);
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
                if (stats == 0) {
                    printStats(player1, player2);
                    stats++;
                }
                return gameover;
            } else {
                player2.IncreaseWins();
                if (stats == 0) {
                    printStats(player2, player1);
                    stats++;
                }
                return gameover;
            }
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

    private void printStars() {
        System.out.println("***************************************************************************");
    }

    private void printStatsBySteps(Player w, Player l) {
        ConcretePiece[] copyW = copyArrayOfConcretePiece(((ConcretePlayer) w).get_pieces());
        Arrays.sort(copyW, new SortBySteps());
        ConcretePiece[] copyL = copyArrayOfConcretePiece(((ConcretePlayer) l).get_pieces());
        Arrays.sort(copyL, new SortBySteps());
        if (w.isPlayerOne()) {
            for (int i = 0; i < 13; i++) {
                if (copyW[i].getSteps() >= 1) {
                    if (copyW[i].isKing()) {
                        System.out.println("K" + copyW[i].toString() + positionsArrayList[copyW[i].getId() - 1].toString());
                    } else {
                        System.out.println("D" + copyW[i].toString() + positionsArrayList[copyW[i].getId() - 1].toString());
                    }
                }

            }
            for (int i = 0; i < 24; i++) {
                if (copyL[i].getSteps() >= 1) {
                    System.out.println("A" + copyL[i].toString() + positionsArrayList[copyL[i].getId() + 12].toString());
                }
            }
        } else {
            for (int i = 0; i < 24; i++) {
                if (copyW[i].getSteps() >= 1) {
                    System.out.println("A" + copyW[i].toString() + positionsArrayList[copyW[i].getId() + 12].toString());
                }
            }
            for (int i = 0; i < 13; i++) {
                if (copyL[i].getSteps() >= 1) {
                    if (copyL[i].isKing()) {
                        System.out.println("K" + copyL[i].toString() + positionsArrayList[copyL[i].getId() - 1].toString());
                    } else {
                        System.out.println("D" + copyL[i].toString() + positionsArrayList[copyL[i].getId() - 1].toString());
                    }
                }
            }
        }
        printStars();
    }

    private ConcretePiece[] copyArrayOfConcretePiece(ConcretePiece[] concretePieces) {
        int l = concretePieces.length;
        ConcretePiece[] copy = new ConcretePiece[l];
        for (int i = 0; i < l; i++) {
            copy[i] = concretePieces[i];
        }
        return copy;
    }

    private void printStatsByKills() {
        Pawn[] arrOfPawns = getArrayOfPawns(player1.get_pieces(), player2.get_pieces());
        Arrays.sort(arrOfPawns, new SortByKills());
        for (int i = 0; i < 36; i++) {
            if (arrOfPawns[i].getKills() > 0) {
                System.out.println(arrOfPawns[i].getOwner().toString() + arrOfPawns[i].getId() + ": " + arrOfPawns[i].getKills() + " kills");
            }
        }
        printStars();
    }

    private Pawn[] getArrayOfPawns(ConcretePiece[] player1, ConcretePiece[] player2) {
        Pawn[] arr = new Pawn[36];
        for (int i = 0; i < 13; i++) {
            if (i != 6) {
                arr[i] = (Pawn) player1[i];
            } else {
                arr[i] = (Pawn) player2[0];
            }
        }
        for (int i = 1; i < 24; i++) {
            arr[i + 12] = (Pawn) player2[i];
        }
        return arr;
    }

    private void printStatsBySquares() {
        ConcretePiece[] arrayOfConcretePieces = getArrayOfConcretePieces(player1.get_pieces(), player2.get_pieces());
        Arrays.sort(arrayOfConcretePieces, new SortBySquares());
        for (int i = 0; i <= 36; i++) {
            if (arrayOfConcretePieces[i].isKing()) {
                System.out.println("K" + arrayOfConcretePieces[i].getId() + ": " + arrayOfConcretePieces[i].getSquares() + " squares");
            } else {
                if (arrayOfConcretePieces[i].getSquares() != 0) {
                    System.out.println(arrayOfConcretePieces[i].getOwner().toString() + arrayOfConcretePieces[i].getId() + ": " + arrayOfConcretePieces[i].getSquares() + " squares");
                }
            }
        }
        printStars();
    }

    private ConcretePiece[] getArrayOfConcretePieces(ConcretePiece[] player1, ConcretePiece[] player2) {
        ConcretePiece[] arr = new ConcretePiece[37];
        for (int i = 0; i < 13; i++) {
            arr[i] = player1[i];
        }
        for (int i = 13; i < 37; i++) {
            arr[i] = player2[i - 13];
        }
        return arr;
    }

    private void printStatsByPieces() {
        ArrayList<Position> steppedPositionsArrayList = steppedPositions();
        steppedPositionsArrayList.sort(new SortByPieces());
        Iterator<Position> iterator = steppedPositionsArrayList.iterator();
        while (iterator.hasNext()) {
            Position position = iterator.next();
            System.out.println(position.toString() + position.get_pieces() + " pieces");
        }
        printStars();
    }
}