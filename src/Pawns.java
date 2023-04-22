import java.awt.*;

abstract class Pawns {

    int player;
    int position_x;
    int position_y;
    Pawns(int player, int x, int y){
        this.position_x = x;
        this.position_y = y;
        this.player = player;
    }

    public void move(int x, int y){
        position_x = x;
        position_y = y;
    }

    public abstract int[][] available_fields();
}

class Pawn extends Pawns {

    Pawn(int player, int x, int y){
        super(player, x, y);
    }

    @Override
    public int[][] available_fields() {
        if (player == 0){
            int[][] avf = {{position_x, position_y - 1}, {position_x, position_y - 2}};
            return avf;
        }
        else {
            int[][] avf = {{position_x, position_y + 1}, {position_x, position_y + 2}};
            return avf;
        }
    }
}


class Knight extends Pawns {

    Knight(int player, int x, int y) {
        super(player, x, y);
    }

    @Override
    public int[][] available_fields() {
        return new int[0][];
    }
}

class Bishop extends Pawns {

    Bishop(int player, int x, int y) {
        super(player, x, y);
    }

    @Override
    public int[][] available_fields() {
        return new int[0][];
    }
}


class Rook extends Pawns {

    Rook(int player, int x, int y) {
        super(player, x, y);
    }

    @Override
    public int[][] available_fields() {
        return new int[0][];
    }
}


class Queen extends Pawns {

    Queen(int player, int x, int y) {
        super(player, x, y);
    }

    @Override
    public int[][] available_fields() {
        return new int[0][];
    }
}


class King extends Pawns {

    King(int player, int x, int y) {
        super(player, x, y);
    }

    @Override
    public int[][] available_fields() {
        return new int[0][];
    }
}