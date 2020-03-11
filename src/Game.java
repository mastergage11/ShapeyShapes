import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.event.KeyListener;

public class Game extends JFrame implements KeyListener {
    Board board;
    int positionX, positionY;
    long moment;
    boolean mouseClicked = false;
    boolean pPressed;

    public Game(){
        setTitle("Don't feed the dog!");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        board = new Board(this);
        add(board);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                setCursor(getToolkit().createCustomCursor(new BufferedImage(3, 3, 2), new Point(0, 0), "null"));
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                positionX = e.getX();
                positionY = e.getY();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mouseClicked = true;
                moment = System.currentTimeMillis();

            }
        });

        pack();
        board.setup();
        setLocationRelativeTo(null);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_P){
            pPressed = true;
            System.out.println("ssssssssssssssssssss");
        }
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_P){
            pPressed = false;
            System.out.println("SSSSSSSSSSSSSSSSSSSS");
        }
    }

    public boolean ispPressed(){
        return pPressed;
    }

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }

    public boolean getIsClicked(){
        return mouseClicked;
    }

    public void notClicked(){
        mouseClicked = false;
    }

    public long getMoment(){
        return moment;
    }

    public static void main(String[] args){

        Game game = new Game();
        game.board.setup();

    }

}



