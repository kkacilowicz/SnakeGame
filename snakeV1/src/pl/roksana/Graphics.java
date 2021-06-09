package pl.roksana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Graphics
extends JPanel
implements ActionListener {

    private Timer t = new Timer(75, this); //utworzenie timera, co 75ms tło będzie rysowane
    public String gameState;

    private Snake s;
    private Food f;
    private Frog frog;
    private Game game;
    private Obstacles obstacles;
    private AISnake aiSnake;


    public Graphics(Game g) { //konstruktor klasy Graphics
        t.start();//uruchomienie Timera
        gameState = "START";

        game = g;
        s = g.getPlayer();
        obstacles = g.getObstacles();
        f = g.getFood();
        frog = g.getFrog();
        aiSnake = g.getAiSnake();


        this.addKeyListener(g); //dodanie keyListenera do obiektu klasy Game
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        Font fnt0 = new Font("arial", Font.BOLD, 60);
        Font fnt1 = new Font("arial",Font.CENTER_BASELINE, 25);

        Graphics2D g2d = (Graphics2D) g;

        //Image logo = new Image();

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, Game.windowsWidth * Game.windowsDimension + 5, Game.windowsHeight * Game.windowsDimension + 5);

        if(gameState == "START") {
            g2d.setColor(Color.white);
            g2d.setFont(fnt0);
            g2d.drawString("SNAKE GAME", Game.windowsWidth / 2 * Game.windowsDimension - 210, Game.windowsHeight / 2 * Game.windowsDimension - 20 );

            g2d.setColor(Color.yellow);
            g2d.setFont(fnt1);
            g2d.drawString("Press any key to start", Game.windowsWidth / 2 * Game.windowsDimension - 140, Game.windowsHeight / 2 * Game.windowsDimension + 50);





        } else if (gameState == "RUNNING") {

            g2d.setColor(Color.BLUE);  //kolor przeszkody

            for(ArrayList<Rectangle> Obstacle : obstacles.getObstaclesBodies()) { //wypełnienie każdego prostokąta stanowiącego ciało węża
                for (Rectangle r: Obstacle) {
                    g2d.fill(r);
                }
            }

            g2d.setColor(Color.RED); //kolor jedzenia
            for (Rectangle r: f.getFruits()) {
                g2d.fill(r);
            }

            g2d.setColor(Color.GREEN);
            g2d.fill(frog.getFrogBody());

            g2d.setColor(Color.YELLOW); //kolor węża
            for(Rectangle r : s.getSnakesBody()) { //wypełnienie każdego prostokąta stanowiącego ciało węża
                g2d.fill(r);
            }
            g2d.setColor(Color.PINK); //kolor węża
            for(Rectangle r : aiSnake.getSnakesBody()) { //wypełnienie każdego prostokąta stanowiącego ciało węża
                g2d.fill(r);
            }
        } else {
            g2d.setColor(Color.red);
            g2d.setFont(fnt0);
            g2d.drawString("GAME OVER", Game.windowsWidth / 2 * Game.windowsDimension - 200, Game.windowsHeight / 2 * Game.windowsDimension - 20 );


            g2d.setColor(Color.white);
            g2d.setFont(fnt1);
            g2d.drawString("Your Score: " + (s.getSnakesBody().size() - 3), Game.windowsWidth / 2 * Game.windowsDimension - 100, Game.windowsHeight / 2 * Game.windowsDimension + 50);
        }

    }


    //żeby ActionListener zadziałał musimy przeciążyć tę metodę
    @Override
    public void actionPerformed(ActionEvent e) { //metoda jest wywoływana co 100ms
        repaint(); //aktualizuje grafikę tła

        game.update();

    }
}
