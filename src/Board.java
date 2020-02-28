import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener {

    Timer timer;
    ArrayList<Sprite> actors;
    int paddingNum = 25;
    Game game;
    long moment;
    long nextMoment;

    public Board(Game game){

        setPreferredSize(new Dimension(550, 750));
        setBackground(Color.BLACK);
        this.game = game;
        timer = new Timer(1000/60, this);
    }

    public void setup(){
        actors = new ArrayList<>();
        actors.add(new Player(Color.GREEN, getWidth()/2, getHeight()/2, 30, 30, this, game));

        for(int i = 1; i < STATS.getNumFood(); i++){
            actors.add(new Food(Color.ORANGE, (int)(Math.random()*(getWidth()-paddingNum)+paddingNum), (int)(Math.random()*(getHeight()-paddingNum)+paddingNum), 15, 15, this));
        }

        for(int i = 1; i < STATS.getNumEnemies(); i++){
            actors.add(new Enemy(Color.RED, (int)(Math.random()*(getWidth()-paddingNum)+paddingNum), (int)(Math.random()*(getHeight()-paddingNum)+paddingNum), 25, 25, this));
        }

        /*timer = new Timer(1000/60, this);
        timer.start();*/

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(Gamestates.isMENU()){
            g.setColor(Color.WHITE);
            g.setFont(new Font("Monospaced", Font.BOLD, 40));
            printSimpleString("DON'T FEED THE DOGS!", getWidth(), 0, 150, g);
            g.setFont(new Font("Monospaced", Font.ITALIC, 25));
            printSimpleString("Press ENTER to play!", getWidth(), 0, 225, g);
        }

        if(Gamestates.isPLAY() && !timer.isRunning()){
            timer.start();
        }

        if(Gamestates.isPLAY()) {
            for (Sprite thisGuy : actors) {
                thisGuy.paint(g);
            }
        }
    }

    private void printSimpleString(String s, int width, int XPos, int YPos, Graphics g){
        int stringLen = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
        int start = width/2 - stringLen/2;
        g.drawString(s, start + XPos, YPos);
    }

    public void checkCollisions(){
        for(int i = 1; i < actors.size(); i++){
            if(actors.get(0).collidesWith(actors.get(i))){

                if(actors.get(i) instanceof Enemy){
                    game.notClicked();
                }
                else{
                    actors.get(i).setRemove();
                }
            }
        }
        for(int i = actors.size() - 1; i >= 0; i--){
            if(actors.get(i).isRemove()){
                actors.remove(i);
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(Gamestates.isPLAY() && !Gamestates.isPAUSE()) {
            nextMoment = System.currentTimeMillis();
            if ((nextMoment - game.getMoment() >= 1500)) {
                checkCollisions();
            }
            //Gives 1.5 second invincibility at the start of game starting when first opened


            if (game.getIsClicked()) {
                for (Sprite thisGuy : actors) {
                    thisGuy.move();
                }
            }

            if (actors.size() < STATS.getNumEnemies() + 1) {
                System.out.println("Killed em all");
                game.notClicked();
            }
        }
        if(game.isEnterPressed()){
            Gamestates.setPLAY(true);
            Gamestates.setMENU(false);
        }

        if(game.ispPressed()){
            if(Gamestates.isPAUSE()){
                Gamestates.setPAUSE(false);
            }else
                Gamestates.setPAUSE(true);
        }

        repaint();
    }
}
//if lives = 0  set level to 1, call setup
//if player gets touched by enemy, lives = lives - 1 , call setup