package pl.roksana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Game
implements KeyListener{ //listener który będzie nasłuchiwał które klawisze są wciskane

    private Snake player; //obiekt player klasy Snake
    private Food food; //jedzenie
    private Graphics graphics;
    private Obstacles obstacles;
    private ProgramThreads threads;

    private JFrame window; //okno gry
    public static final int windowsWidth = 60;
    public static final int windowsHeight = 60;
    public static final int windowsDimension = 10;

        //konstruktor klasy Game
    public Game() {
        window = new JFrame(); //tworzę nowe okno
        player = new Snake();
        food = new Food(player);
        obstacles = new Obstacles();
        threads = new ProgramThreads(4);

        graphics = new Graphics(this);

        window.add(graphics);

        window.setTitle("Snake");
        window.setSize(windowsWidth * windowsDimension + 2, windowsHeight * windowsDimension + 4);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//okno zamknie się po naciśnięciu X w rogu ekranu

    }

    public void start() {
        graphics.gameState = "RUNNING";

    }

    public void update() {
        if (graphics.gameState == "RUNNING") {
            if (checkFoodCollision()) {
                player.grow();
                food.randomSpawn(player);
            }else if (checkWallCollision() || checkSelfCollision() || checkObstacleCollision()) {
                graphics.gameState = "END";
            } else {
                threads.runTask(player);
            }

        }
    }

    private boolean checkWallCollision() {
        if(player.getX() < 0 || player.getX() >= windowsDimension * windowsWidth || player.getY() < 0 || player.getY() >= windowsDimension * windowsHeight ) {
            return true;
        }
        return false;
    }

    private   boolean checkFoodCollision() {
        if(player.getX() == food.getX() * windowsDimension && player.getY() == food.getY() * windowsDimension) {
            return true;
        }
        return false;
    }

    private boolean checkSelfCollision() {
        for(int i = 1; i < player.getSnakesBody().size() ; i++) {
            if (player.getX() == player.getSnakesBody().get(i).x && player.getY() == player.getSnakesBody().get(i).y) {
                return true;
            }
        }
        return false;
    }

    private boolean checkObstacleCollision(){
        for (ArrayList<Rectangle> Obstacle: obstacles.getObstaclesBodies()) {
            for (Rectangle r: Obstacle) {
                if(player.getX() == r.x && player.getY() == r.y)
                return true;
            }
        }
        return false;
    }


    //aby KeyListener działał musieliśmy przeciążyć te 3 metody
    @Override
    public void keyTyped(KeyEvent e) {
    }

    //będziemy używać tej metody
    @Override
    public void keyPressed(KeyEvent e) { //za każdym razem gdy przycisk zostanie wciśnięty, ta funkcja zostanie uruchomiona
        int keyCode = e.getKeyCode(); //kiedy przycisk zostaje wciśnięty, jego kod zostaje pobrany i zapisany w zmiennej

        if (graphics.gameState == "RUNNING") {

            //odczytywanie wciśniętego przycisku
            if (keyCode == KeyEvent.VK_UP && player.getMove() != "UP") {
                player.up();

            } else if (keyCode == KeyEvent.VK_DOWN && player.getMove() != "DOWN") {
                player.down();

            } else if (keyCode == KeyEvent.VK_LEFT && player.getMove() != "LEFT") {
                player.left();

            } else if (keyCode == KeyEvent.VK_RIGHT && player.getMove() != "RIGHT") {
                player.right();
            }

        } else {
            this.start();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public Snake getPlayer() {
        return player;
    }

    public void setPlayer(Snake player) {
        this.player = player;
    }

    public Obstacles getObstacles() {
        return obstacles;
    }

    public void setObstacles(Obstacles obstacles) {
        this.obstacles = obstacles;
    }

    public JFrame getWindow() {
        return window;
    }

    public void setWindow(JFrame window) {
        this.window = window;
    }
}
