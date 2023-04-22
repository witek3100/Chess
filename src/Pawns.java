import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

abstract class Pawns {

    /* klasa abstrakcyjna reprezantujaca pionek */

    int player;
    int position_x;
    int position_y;
    BufferedImage image;
    Game game;
    Boolean active;
    Pawns(int player, int x, int y, String imgPath, Game game) throws IOException {
        this.position_x = x;
        this.position_y = y;
        this.player = player;
        this.image = ImageIO.read(new File(imgPath));
        this.game = game;
        this.active = true;
    }

    public void move(int x, int y){
        /* metoda przemeiszczajaca pionek */
        position_x = x;
        position_y = y;

        // w razie potrzeby usuwa pionek przeciwnika z pola na ktorym staje
        for (int i=0; i<16; i++){
            Pawns[] enemyPawns;
            if (player == 0){
                enemyPawns = game.blackPlayerPawns;
            } else {
                enemyPawns = game.whitePlayerPawns;
            }
            if (enemyPawns[i].position_x == position_x && enemyPawns[i].position_y == position_y){
                enemyPawns[i].active = false;
            }
        }
    }

    // metoda znajdujaca dostepne ruchy dla kazdego pionka - implementacja zalezna od ruchow jakie moze wykonac pionek
    public abstract List<List<Integer>> available_fields();
}

class Pawn extends Pawns {

    Pawn(int player, int x, int y, String imgPath, Game game) throws IOException {
        super(player, x, y, imgPath, game);
    }

    @Override
    public List<List<Integer>> available_fields() {
        if (player == 0){
            List<List<Integer>> avf = new ArrayList<>();
            if (position_y - 1 >= 0) {
                avf.add(new ArrayList<>());
                avf.get(0).addAll(Arrays.asList(position_x, position_y - 1));
            }
            if (position_y == 6) {
                avf.add(new ArrayList<>());
                avf.get(1).addAll(Arrays.asList(position_x, position_y-2));
            }
            for (Pawns p : game.blackPlayerPawns){
                if (p.active){
                    if (p.position_x == position_x && p.position_y == position_y-1){
                        avf.remove(0);
                    }
                    if (p.position_x == position_x-1 && p.position_y == position_y-1){
                        avf.add(new ArrayList<>());
                        avf.get(avf.size()-1).addAll(Arrays.asList(position_x-1, position_y-1));
                    }
                    if (p.position_x == position_x+1 && p.position_y == position_y-1){
                        avf.add(new ArrayList<>());
                        avf.get(avf.size()-1).addAll(Arrays.asList(position_x+1, position_y-1));
                    }
                }
            }
            return avf;
        }
        else {
            List<List<Integer>> avf = new ArrayList<>();
            avf.add(new ArrayList<>());
            avf.get(0).addAll(Arrays.asList(position_x, position_y+1));
            if (position_y == 1) {
                avf.add(new ArrayList<>());
                avf.get(1).addAll(Arrays.asList(position_x, position_y+2));
            }
            return avf;
        }
    }
}


class Knight extends Pawns {

    Knight(int player, int x, int y, String imgPath, Game game) throws IOException {
        super(player, x, y, imgPath, game);
    }

    @Override
    public List<List<Integer>> available_fields() {
        List<List<Integer>> avf = new ArrayList<>();
        int[][] boardState = game.getBoard();
        if (0 <= position_x-1 && 0 <= position_y-2) {
            if (boardState[position_y - 2][position_x - 1] != player) {
                avf.add(new ArrayList<>());
                avf.get(0).addAll(Arrays.asList(position_x - 1, position_y - 2));
            }
        }
        if (0 <= position_x-2 && 0 <= position_y-1){
            if (boardState[position_y-1][position_x-2] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size()-1).addAll(Arrays.asList(position_x-2, position_y-1));
            }
        }
        if (0 <= position_x-2 && position_y+1 < 8) {
            if (boardState[position_y + 1][position_x - 2] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size() - 1).addAll(Arrays.asList(position_x - 2, position_y + 1));
            }
        }
        if (0 <= position_x-1 && position_y+2 < 8) {
            if (boardState[position_y + 2][position_x - 1] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size() - 1).addAll(Arrays.asList(position_x - 1, position_y + 2));
            }
        }
        if (position_x+1 < 8 && position_y+2 < 8) {
            if (boardState[position_y + 2][position_x + 1] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size() - 1).addAll(Arrays.asList(position_x + 1, position_y + 2));
            }
        }
        if (position_x+2 < 8 && position_y+1 < 8){
            if (boardState[position_y+1][position_x+2] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size()-1).addAll(Arrays.asList(position_x+2, position_y+1));
            }
        }
        if (position_x+2 < 8 && 0 <= position_y-1){
            if (boardState[position_y-1][position_x+2] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size()-1).addAll(Arrays.asList(position_x+2, position_y-1));
            }
        }
        if (position_x+1 < 8 && 0 <= position_y-2) {
            if (boardState[position_y - 2][position_x + 1] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size() - 1).addAll(Arrays.asList(position_x + 1, position_y - 2));
            }
        }
        return avf;
    }
}

class Bishop extends Pawns {

    Bishop(int player, int x, int y, String imgPath, Game game) throws IOException {
        super(player, x, y, imgPath, game);
    }

    @Override
    public List<List<Integer>> available_fields() {
        List<List<Integer>> avf = new ArrayList<>();
        int[][] boardState = game.getBoard();
        for (int j=1; j<8; j++){
            if (position_x-j>=0 && position_y-j>=0){
                if (boardState[position_y-j][position_x-j] == Math.abs(player-1))
                {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x-j, position_y-j));
                    break;
                }
                else if (boardState[position_y-j][position_x-j] == player){
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x-j, position_y-j));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_x+j<8 && position_y+j<8) {
                if (boardState[position_y + j][position_x + j] == Math.abs(player - 1)) {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x + j, position_y + j));
                    break;
                } else if (boardState[position_y + j][position_x + j] == player) {
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x + j, position_y + j));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_y-j >= 0 && position_x+j < 8) {
                if (boardState[position_y-j][position_x+j] == Math.abs(player-1))
                {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x+j, position_y-j));
                    break;
                }
                else if (boardState[position_y-j][position_x+j] == player){
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x+j, position_y-j));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_x-j >= 0 && position_y+j < 8) {
                if (boardState[position_y+j][position_x-j] == Math.abs(player - 1)) {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x - j, position_y + j));
                    break;
                } else if (boardState[position_y + j][position_x - j] == player) {
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x - j, position_y + j));
                }
            }
        }
        return avf;
    }
}


class Rook extends Pawns {

    Rook(int player, int x, int y, String imgPath, Game game) throws IOException {
        super(player, x, y, imgPath, game);
    }

    @Override
    public List<List<Integer>> available_fields() {
        List<List<Integer>> avf = new ArrayList<>();
        int[][] boardState = game.getBoard();
        for (int j=1; j<8; j++){
            if (position_x-j>=0){
                if (boardState[position_y][position_x-j] == Math.abs(player-1))
                {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x-j, position_y));
                    break;
                }
                else if (boardState[position_y][position_x-j] == player){
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x-j, position_y));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_x+j<8) {
                if (boardState[position_y][position_x + j] == Math.abs(player - 1)) {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x + j, position_y));
                    break;
                } else if (boardState[position_y][position_x + j] == player) {
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x + j, position_y));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_y-j>=0){
                if (boardState[position_y-j][position_x] == Math.abs(player-1))
                {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x, position_y-j));
                    break;
                }
                else if (boardState[position_y-j][position_x] == player){
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x, position_y-j));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_y+j<8) {
                if (boardState[position_y + j][position_x] == Math.abs(player - 1)) {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x, position_y + j));
                    break;
                } else if (boardState[position_y + j][position_x] == player) {
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x, position_y + j));
                }
            }
        }
        return avf;
    }
}


class Queen extends Pawns {

    Queen(int player, int x, int y, String imgPath, Game game) throws IOException {
        super(player, x, y, imgPath, game);
    }

    @Override
    public List<List<Integer>> available_fields() {
        List<List<Integer>> avf = new ArrayList<>();
        int[][] boardState = game.getBoard();
        for (int j=1; j<8; j++){
            if (position_x-j>=0 && position_y-j>=0){
                if (boardState[position_y-j][position_x-j] == Math.abs(player-1))
                {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x-j, position_y-j));
                    break;
                }
                else if (boardState[position_y-j][position_x-j] == player){
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x-j, position_y-j));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_x+j<8 && position_y+j<8) {
                if (boardState[position_y + j][position_x + j] == Math.abs(player - 1)) {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x + j, position_y + j));
                    break;
                } else if (boardState[position_y + j][position_x + j] == player) {
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x + j, position_y + j));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_y-j >= 0 && position_x+j < 8) {
                if (boardState[position_y-j][position_x+j] == Math.abs(player-1))
                {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x+j, position_y-j));
                    break;
                }
                else if (boardState[position_y-j][position_x+j] == player){
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x+j, position_y-j));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_x-j >= 0 && position_y+j < 8) {
                if (boardState[position_y+j][position_x-j] == Math.abs(player - 1)) {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x - j, position_y + j));
                    break;
                } else if (boardState[position_y + j][position_x - j] == player) {
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x - j, position_y + j));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_x-j>=0){
                if (boardState[position_y][position_x-j] == Math.abs(player-1))
                {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x-j, position_y));
                    break;
                }
                else if (boardState[position_y][position_x-j] == player){
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x-j, position_y));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_x+j<8) {
                if (boardState[position_y][position_x + j] == Math.abs(player - 1)) {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x + j, position_y));
                    break;
                } else if (boardState[position_y][position_x + j] == player) {
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x + j, position_y));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_y-j>=0){
                if (boardState[position_y-j][position_x] == Math.abs(player-1))
                {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x, position_y-j));
                    break;
                }
                else if (boardState[position_y-j][position_x] == player){
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size()-1).addAll(Arrays.asList(position_x, position_y-j));
                }
            }
        }
        for (int j=1; j<8; j++){
            if (position_y+j<8) {
                if (boardState[position_y + j][position_x] == Math.abs(player - 1)) {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x, position_y + j));
                    break;
                } else if (boardState[position_y + j][position_x] == player) {
                    break;
                } else {
                    avf.add(new ArrayList<>());
                    avf.get(avf.size() - 1).addAll(Arrays.asList(position_x, position_y + j));
                }
            }
        }
        return avf;
    }
}


class King extends Pawns {

    King(int player, int x, int y, String imgPath, Game game) throws IOException {
        super(player, x, y, imgPath, game);
    }

    @Override
    public List<List<Integer>> available_fields() {
        List<List<Integer>> avf = new ArrayList<>();
        int[][] boardState = game.getBoard();
        if (0 <= position_x-1 && 0 <= position_y-1) {
            if (boardState[position_y - 1][position_x - 1] != player) {
                avf.add(new ArrayList<>());
                avf.get(0).addAll(Arrays.asList(position_x - 1, position_y - 1));
            }
        }
        if (0 <= position_x-1){
            if (boardState[position_y][position_x-1] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size()-1).addAll(Arrays.asList(position_x-1, position_y));
            }
        }
        if (0 <= position_x-1 && position_y+1 < 8) {
            if (boardState[position_y + 1][position_x - 1] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size() - 1).addAll(Arrays.asList(position_x - 1, position_y + 1));
            }
        }
        if (position_y+1 < 8) {
            if (boardState[position_y + 1][position_x] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size() - 1).addAll(Arrays.asList(position_x, position_y + 1));
            }
        }
        if (position_x+1 < 8 && position_y+1 < 8) {
            if (boardState[position_y + 1][position_x + 1] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size() - 1).addAll(Arrays.asList(position_x + 1, position_y + 1));
            }
        }
        if (position_x+1 < 8){
            if (boardState[position_y][position_x+1] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size()-1).addAll(Arrays.asList(position_x+1, position_y));
            }
        }
        if (position_x+1 < 8 && 0 <= position_y-1){
            if (boardState[position_y-1][position_x+1] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size()-1).addAll(Arrays.asList(position_x+1, position_y-1));
            }
        }
        if (0 <= position_y-1) {
            if (boardState[position_y - 1][position_x] != player) {
                avf.add(new ArrayList<>());
                avf.get(avf.size() - 1).addAll(Arrays.asList(position_x, position_y - 1));
            }
        }
        return avf;
    }
}