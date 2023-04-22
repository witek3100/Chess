import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

abstract class Pawns {

    int player;
    int position_x;
    int position_y;
    BufferedImage image;
    Pawns(int player, int x, int y, String imgPath) throws IOException {
        this.position_x = x;
        this.position_y = y;
        this.player = player;
        this.image = ImageIO.read(new File(imgPath));
    }

    public void move(int x, int y){
        position_x = x;
        position_y = y;
    }

    public abstract List<List<Integer>> available_fields();
}

class Pawn extends Pawns {

    Pawn(int player, int x, int y, String imgPath) throws IOException {
        super(player, x, y, imgPath);
    }

    @Override
    public List<List<Integer>> available_fields() {
        if (player == 0){
            List<List<Integer>> avf = new ArrayList<List<Integer>>();
            avf.add(new ArrayList<Integer>());
            avf.get(0).addAll(Arrays.asList(position_x, position_y-1));
            if (position_y == 6) {
                avf.add(new ArrayList<Integer>());
                avf.get(1).addAll(Arrays.asList(position_x, position_y-2));
            }
            return avf;
        }
        else {
            List<List<Integer>> avf = new ArrayList<List<Integer>>();
            avf.add(new ArrayList<Integer>());
            avf.get(0).addAll(Arrays.asList(position_x, position_y+1));
            if (position_y == 1) {
                avf.add(new ArrayList<Integer>());
                avf.get(1).addAll(Arrays.asList(position_x, position_y+2));
            }
            return avf;
        }
    }
}


class Knight extends Pawns {

    Knight(int player, int x, int y, String imgPath) throws IOException {
        super(player, x, y, imgPath);
    }

    @Override
    public List<List<Integer>> available_fields() {
        List<List<Integer>> l = new ArrayList<>();
        return l;
    }
}

class Bishop extends Pawns {

    Bishop(int player, int x, int y, String imgPath) throws IOException {
        super(player, x, y, imgPath);
    }

    @Override
    public List<List<Integer>> available_fields() {
        List<List<Integer>> l = new ArrayList<>();
        return l;
    }
}


class Rook extends Pawns {

    Rook(int player, int x, int y, String imgPath) throws IOException {
        super(player, x, y, imgPath);
    }

    @Override
    public List<List<Integer>> available_fields() {
        List<List<Integer>> l = new ArrayList<>();
        return l;
    }
}


class Queen extends Pawns {

    Queen(int player, int x, int y, String imgPath) throws IOException {
        super(player, x, y, imgPath);
    }

    @Override
    public List<List<Integer>> available_fields() {
        List<List<Integer>> l = new ArrayList<>();
        return l;
    }
}


class King extends Pawns {

    King(int player, int x, int y, String imgPath) throws IOException {
        super(player, x, y, imgPath);
    }

    @Override
    public List<List<Integer>> available_fields() {
        List<List<Integer>> l = new ArrayList<>();
        return l;
    }
}