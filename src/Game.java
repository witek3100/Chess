import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


public class Game extends Frame implements KeyListener {

    public static final int WHITE = 0;
    public static final int BLACK = 1;
    public final Pawns[] whitePlayerPawns;
    public final Pawns[] blackPlayerPawns;
    public Pawns[] availablePawns;
    List<List<Integer>> availableFields;
    int player;
    int pawn_num;
    int field_num;
    Game() throws IOException {

        // tworzenie pionkow dla obu graczy
        whitePlayerPawns = new Pawns[16];
        blackPlayerPawns = new Pawns[16];
        for (int i=0; i<8; i++){
            whitePlayerPawns[i] = new Pawn(WHITE, i, 6, "static/whitepawn.png", this);
            blackPlayerPawns[i] = new Pawn(BLACK, i, 1, "static/blackpawn.png", this);
        }
        whitePlayerPawns[8] = new Knight(WHITE, 1, 7, "static/whiteknight.png", this);
        whitePlayerPawns[9] = new Knight(WHITE, 6, 7, "static/whiteknight.png", this);
        whitePlayerPawns[10] = new Rook(WHITE, 0, 7, "static/whiterook.png", this);
        whitePlayerPawns[11] = new Rook(WHITE, 7, 7, "static/whiterook.png", this);
        whitePlayerPawns[12] = new Bishop(WHITE, 2, 7, "static/whitebishop.png", this);
        whitePlayerPawns[13] = new Bishop(WHITE, 5, 7, "static/whitebishop.png", this);
        whitePlayerPawns[14] = new Queen(WHITE, 3, 7, "static/whitequeen.png", this);
        whitePlayerPawns[15] = new King(WHITE, 4, 7, "static/whiteking.png", this);

        blackPlayerPawns[8] = new Knight(BLACK, 1, 0, "static/blackknight.png", this);
        blackPlayerPawns[9] = new Knight(BLACK, 6, 0, "static/blackknight.png", this);
        blackPlayerPawns[10] = new Rook(BLACK, 0, 0, "static/blackrook.png", this);
        blackPlayerPawns[11] = new Rook(BLACK, 7, 0, "static/blackrook.png", this);
        blackPlayerPawns[12] = new Bishop(BLACK, 2, 0, "static/blackbishop.png", this);
        blackPlayerPawns[13] = new Bishop(BLACK, 5, 0, "static/blackbishop.png", this);
        blackPlayerPawns[14] = new Queen(BLACK, 4, 0, "static/blackqueen.png", this);
        blackPlayerPawns[15] = new King(BLACK, 3, 0, "static/blackking.png", this);

        // ustawienia okna wyswietlania
        setSize(700, 700);
        setTitle("CHESS");
        setLayout(null);
        setVisible(true);

        // zmienne sterujace gra
        player = 0; // gracz wykonujacy ruch  0 - bialy   1 - czarny
        pawn_num = 0; // aktualnie wybrany pionek
        field_num = 0; // aktualnie wybrane pole

        setBackground(Color.lightGray);

        this.addKeyListener(this);

        // tworzenie listy dostepnych pionkow dla aktualnego gracza
        Pawns[][] wbpawns = {whitePlayerPawns, blackPlayerPawns};
        availablePawns = Arrays.stream(wbpawns[player]).filter(x -> x.active).toArray(Pawns[]::new);

        getBoard();
    }

    public void paint(Graphics g){
        /*
        Rysowanie planszy
        */
        setBackground(Color.lightGray);

        // pola planszy
        for (int i=0; i<4; i++) {
            g.setColor(Color.getHSBColor(0, 0.3F, 0.3F));
            g.fillRect(140 + i * 140, 70, 70, 70);
            g.fillRect(70 + i * 140, 140, 70, 70);
            g.fillRect(140 + i * 140, 210, 70, 70);
            g.fillRect(70 + i * 140, 280, 70, 70);
            g.fillRect(140 + i * 140, 350, 70, 70);
            g.fillRect(70 + i * 140, 420, 70, 70);
            g.fillRect(140 + i * 140, 490, 70, 70);
            g.fillRect(70 + i * 140, 560, 70, 70);

            g.setColor(Color.getHSBColor(1, 0.1F, 0.95F));
            g.fillRect(70 + i * 140, 70, 70, 70);
            g.fillRect(140 + i * 140, 140, 70, 70);
            g.fillRect(70 + i * 140, 210, 70, 70);
            g.fillRect(140 + i * 140, 280, 70, 70);
            g.fillRect(70 + i * 140, 350, 70, 70);
            g.fillRect(140 + i * 140, 420, 70, 70);
            g.fillRect(70 + i * 140, 490, 70, 70);
            g.fillRect(140 + i * 140, 560, 70, 70);
        }

        // dostepne ruchu dla wybranego pionka
        availableFields = availablePawns[Math.abs(pawn_num)%availablePawns.length].available_fields();
        for (int i=0; i<availableFields.size(); i++){
            g.setColor(Color.cyan);
            g.fillRect( availableFields.get(i).get(0)*70+70, availableFields.get(i).get(1)*70+70, 70, 70);
        }
        g.setColor(Color.ORANGE);
        g.fillRect(70+availablePawns[Math.abs(pawn_num)%availablePawns.length].position_x*70, 70+availablePawns[Math.abs(pawn_num)%16].position_y*70, 70, 70);

        // aktualnie wybrany ruch
        g.setColor(Color.BLUE);
        if (!(availableFields.size() == 0)){
            g.fillRect(availableFields.get(Math.abs(field_num)%availableFields.size()).get(0)*70+70, availableFields.get(Math.abs(field_num)%availableFields.size()).get(1)*70+70, 70, 70);
        }

        // pionki
        for (int j=0; j<16; j++){
            if (whitePlayerPawns[j].active) {
                g.drawImage(whitePlayerPawns[j].image, 82 + whitePlayerPawns[j].position_x * 70, 82 + whitePlayerPawns[j].position_y * 70, 50, 50, null);
            }
            if (blackPlayerPawns[j].active) {
                g.drawImage(blackPlayerPawns[j].image, 82 + blackPlayerPawns[j].position_x * 70, 82 + blackPlayerPawns[j].position_y * 70, 50, 50, null);
            }
        }
    }

    public int[][] getBoard() {
        /*
        Zwraca aktualny stan planszy, gdzie 2 - puste pole, 0 - bialy pionek, 1 - czarny pionek
        Wykorzystywana do szukania dostepnych ruchow
        */
        int[][] board = new int[8][8];
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                board[i][j] = 2;
            }
        }
        for (Pawns p : blackPlayerPawns){
            if (p.active) {
                board[p.position_y][p.position_x] = 1;
            }
        }
        for (Pawns p : whitePlayerPawns){
            if (p.active) {
                board[p.position_y][p.position_x] = 0;
            }
        }
        return board;
    }

    
    // metody interfejsu KeyListener do obsługi działań gracza
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        /*
            metoda wykonująca działania w zależności od wciśniętego przycisku
            37, 39 - kody strzałek - zmiana pionka
            68, 65 - kody liter A i D - zmiana pola na które przemiieszczamy pionek
            10 - enter - przemieszcza pionek na zadane pole
        */
        if (e.getKeyCode() == 37){
            pawn_num--;
        }
        else if (e.getKeyCode() == 39){
            pawn_num++;
        }
        else if (e.getKeyCode() == 68){
            field_num++;
        }
        else if (e.getKeyCode() == 65){
            field_num--;
        }
        else if (e.getKeyCode() == 10){
            availablePawns[Math.abs(pawn_num)%16].move(availableFields.get(Math.abs(field_num)%availableFields.size()).get(0), availableFields.get(Math.abs(field_num)%availableFields.size()).get(1));
            player = Math.abs(player-1);
            System.out.println(player);
            Pawns[][] wbpawns = {whitePlayerPawns, blackPlayerPawns};
            availablePawns = Arrays.stream(wbpawns[player]).filter(x -> x.active).toArray(Pawns[]::new);
        }
        repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}



