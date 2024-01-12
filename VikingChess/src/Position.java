public class Position {
    private int _x,_y;
    //_x=row,_y=col
    public Position(int x,int y){
        this._x=x;
        this._y=y;
    }
    public Position(Position p){
        this(p.GetX(),p.GetY());
    }
    public int GetX(){
        return _x;
    }
    public int GetY(){
        return _y;
    }
    public boolean Equalto(Position p){
        return (this._x==p._x && this._y==p._y);
    }

    @Override
    public String toString() {
        return "(" +
                _x +
                ", " + _y +
                ')';
    }
}
