import java.lang.invoke.ConstantCallSite;
public class ConcretePlayer  implements Player {
     private boolean _white;
     private int _win;

public ConcretePlayer(boolean white){
    this._white=white;
    this._win=0;
}
    @Override
    public boolean isPlayerOne() {
   return this._white;
    }
    @Override
    public int getWins() {
        return _win;
    }
    public int IncreaseWins(){return _win++;}
}
