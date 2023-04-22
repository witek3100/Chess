import java.awt.*;

abstract class Pawns {

    int player;
    int position_x;
    int position_y;
    public abstract void move(Graphics g);
    Pawns(int player, int x, int y){
        this.position_x = x;
        this.position_y = y;
        this.player = player;
    }
}

class Pawn extends Pawns {
    @Override
    public void move(Graphics g) {

    }

    Pawn(int player, int x, int y){
        super(player, x, y);
    }

}
