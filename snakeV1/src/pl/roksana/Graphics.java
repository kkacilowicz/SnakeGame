package pl.roksana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Graphics
extends JPanel
implements ActionListener {

    private Timer t = new Timer(75, this); //utworzenie timera, co 100ms tło będzie rysowane
    public String gameState;

    private Snake s;
    private Food f;
    private Game game;
    private Obstacles obstacles;


    public Graphics(Game g) { //konstruktor klasy Graphics
        t.start();//uruchomienie Timera
        gameState = "START";

        game = g;
        s = g.getPlayer();
        f = g.getFood();
        obstacles = g.getObstacles();


        this.addKeyListener(g); //dodanie keyListenera do obiektu klasy Game
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
    }

    public void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, Game.windowsWidth * Game.windowsDimension + 5, Game.windowsHeight * Game.windowsDimension + 5);

        if(gameState == "START") {
            g2d.setColor(Color.white);
            g2d.drawString("Press Any Key To Start", Game.windowsWidth / 2 * Game.windowsDimension - 40, Game.windowsHeight / 2 * Game.windowsDimension - 20 );

        } else if (gameState == "RUNNING") {

            g2d.setColor(Color.BLUE);  //kolor przeszkody

            for(ArrayList<Rectangle> Obstacle : obstacles.getObstaclesBodies()) { //wypełnienie każdego prostokąta stanowiącego ciało węża
                for (Rectangle r: Obstacle) {
                    g2d.fill(r);
                }
            }

            g2d.setColor(Color.RED); //kolor jedzenia
            g2d.fillRect(f.getX() * Game.windowsDimension, f.getY() * Game.windowsDimension, Game.windowsDimension, Game.windowsDimension);

            g2d.setColor(Color.YELLOW); //kolor węża
            for(Rectangle r : s.getSnakesBody()) { //wypełnienie każdego prostokąta stanowiącego ciało węża
                g2d.fill(r);
            }
        } else {
            g2d.setColor(Color.white);
            g2d.drawString("Your Score: " + (s.getSnakesBody().size() - 3), Game.windowsWidth / 2 * Game.windowsDimension - 40, Game.windowsHeight / 2 * Game.windowsDimension - 20 );
        }

    }


    //żeby ActionListener zadziałał musimy przeciążyć tę metodę
    @Override
    public void actionPerformed(ActionEvent e) { //metoda jest wywoływana co 100ms
        repaint(); //aktualizuje grafikę tła

        game.update();

    }
}
