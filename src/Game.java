import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class Game extends Frame implements KeyListener {

    private static final int WHITE = 0;
    private static final int BLACK = 1;
    private Pawns[] whitePlayerPawns;
    private Pawns[] blackPlayerPawns;
    private Pawns[] availablePawns;
    int player;
    int pawn_num;
    Game() {
        whitePlayerPawns = new Pawns[16];
        blackPlayerPawns = new Pawns[16];
        for (int i=0; i<8; i++){
            whitePlayerPawns[i] = new Pawn(WHITE, i, 6);
            blackPlayerPawns[i] = new Pawn(BLACK, i, 1);
        }

        setSize(700, 700);
        setTitle("CHESS");
        setLayout(null);
        setVisible(true);

        player = 0;
        pawn_num = 0;

        setBackground(Color.lightGray);

        this.addKeyListener(this);

        Pawns[][] wbpawns = {whitePlayerPawns, blackPlayerPawns};
        availablePawns = wbpawns[player];

    }

    public void paint(Graphics g){
        setBackground(Color.lightGray);

        for (int i=0; i<4; i++) {
            g.setColor(Color.black);
            g.fillRect(140 + i * 140, 70, 70, 70);
            g.fillRect(70 + i * 140, 140, 70, 70);
            g.fillRect(140 + i * 140, 210, 70, 70);
            g.fillRect(70 + i * 140, 280, 70, 70);
            g.fillRect(140 + i * 140, 350, 70, 70);
            g.fillRect(70 + i * 140, 420, 70, 70);
            g.fillRect(140 + i * 140, 490, 70, 70);
            g.fillRect(70 + i * 140, 560, 70, 70);

            g.setColor(Color.white);
            g.fillRect(70 + i * 140, 70, 70, 70);
            g.fillRect(140 + i * 140, 140, 70, 70);
            g.fillRect(70 + i * 140, 210, 70, 70);
            g.fillRect(140 + i * 140, 280, 70, 70);
            g.fillRect(70 + i * 140, 350, 70, 70);
            g.fillRect(140 + i * 140, 420, 70, 70);
            g.fillRect(70 + i * 140, 490, 70, 70);
            g.fillRect(140 + i * 140, 560, 70, 70);


        }

        for (int j=0; j<16; j++){
            g.setColor(Color.getHSBColor(10, 10, 10));
            if (whitePlayerPawns[j] != null){
                g.fillRect(87+whitePlayerPawns[j].position_x*70, 87+whitePlayerPawns[j].position_y*70, 40, 40);
            }
            g.setColor(Color.getHSBColor(40, 40, 40));
            if (blackPlayerPawns[j] != null){
                g.fillRect(87+blackPlayerPawns[j].position_x*70, 87+blackPlayerPawns[j].position_y*70, 40, 40);
            }
        }

        g.setColor(Color.cyan);
        g.fillRect( availablePawns[Math.abs(pawn_num)%16].position_x*70+70, (availablePawns[Math.abs(pawn_num)%16].position_y-2)*70+70, 70, 70);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 37){
            pawn_num--;
            repaint();
        }
        else if (e.getKeyCode() == 39){
            pawn_num++;
            repaint();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}
}
