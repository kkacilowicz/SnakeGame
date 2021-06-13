package pl.roksana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Game
implements KeyListener{ //listener który będzie nasłuchiwał które klawisze są wciskane

    private Snake player; //obiekt player klasy Snake
    private Food food; //jedzenie
    private Frog frog;
    private Graphics graphics;
    private Obstacles obstacles;
    private AISnake aiSnake;
    private ProgramThreads threads;
    private int MoveAISlower;     //o ile wolniej ma się ruszać żabka od węża

    private JFrame window; //okno gry
    public static final int windowsWidth = 60;
    public static final int windowsHeight = 60;
    public static final int windowsDimension = 10;

        //konstruktor klasy Game
    public Game() throws FileNotFoundException {
        window = new JFrame(); //tworzę nowe okno
        player = new Snake();
        obstacles = new Obstacles();
        food = new Food(player, obstacles);
        frog = new Frog(player, obstacles, food);
        aiSnake = new AISnake(this);

        threads = new ProgramThreads(4);

        graphics = new Graphics(this);
        window.add(graphics);

        window.setTitle("Snake");
        window.setSize(windowsWidth * windowsDimension + 2, windowsHeight * windowsDimension + 4);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//okno zamknie się po naciśnięciu X w rogu ekranu

        MoveAISlower =0;

    }
    /// <summary>
    /// Function sets game state as running
    /// </summary>
    public void start() {
        graphics.gameState = "RUNNING";

    }

    public void update() {
        if (graphics.gameState == "RUNNING") {
            if (checkFoodCollision(player)) {
                player.grow();
                food.FruitEaten(player);
                threads.runTask(food);
            }else if(checkFrogCollision(player)){
                frog.FrogEaten();
                threads.runTask(frog);
                player.grow();
            }else if (checkWallCollision() || checkSelfCollision() || checkObstacleCollision() || checkSnakesCollision()) {
                graphics.gameState = "END";
            } else {
                if(MoveAISlower >=4) {    //rzadziej się ta funkcja wywołuje o tyle razy ile jest MoveFrogSlower
                    MoveAISlower =0;
                    if(checkFoodCollision(aiSnake)){
                        food.FruitEaten(aiSnake);
                        aiSnake.grow();
                        threads.runTask(food);
                    }else if(checkFrogCollision(aiSnake)){
                        frog.FrogEaten();
                        threads.runTask(frog);
                        aiSnake.grow();
                    }
                    threads.runTask(aiSnake);
                    threads.runTask(frog);
                }
                threads.runTask(player);

            }
            MoveAISlower++;

        }
    }
    /// <summary>
    /// Function checks if snake runs into AI snake
    /// </summary>
    private boolean checkSnakesCollision(){
        for (Rectangle r: aiSnake.getSnakesBody()) {
            if(player.getX() == r.x && player.getY() == r.y)
                return true;
        }
        return false;
    }

    /// <summary>
    /// Function checks if snake runs into wall
    /// </summary>
    private boolean checkWallCollision() {
        if(player.getX() <= 0 || player.getX() >= windowsDimension * windowsWidth || player.getY() <= 0 || player.getY() >= windowsDimension * windowsHeight ) {
            return true;
        }
        return false;
    }

    /// <summary>
    /// Function checks if snake runs into food
    /// </summary>
    private   boolean checkFoodCollision(Snake snake) {
        for (Rectangle R: food.getFruits()) {
            if (snake.getX() == R.x && snake.getY() == R.y) {
                return true;
            }
        }
        return false;
    }


    /// <summary>
    /// Function checks if snake runs into himself
    /// </summary>
    private boolean checkSelfCollision() {
        for(int i = 1; i < player.getSnakesBody().size() ; i++) {
            if (player.getX() == player.getSnakesBody().get(i).x && player.getY() == player.getSnakesBody().get(i).y) {
                return true;
            }
        }
        return false;
    }


    /// <summary>
    /// Function checks if snake runs into obstacle
    /// </summary>
    private boolean checkObstacleCollision(){
        for (ArrayList<Rectangle> Obstacle: obstacles.getObstaclesBodies()) {
            for (Rectangle r: Obstacle) {
                if(player.getX() == r.x && player.getY() == r.y)
                return true;
            }
        }
        return false;
    }

    private boolean checkFrogCollision(Snake snake){
        return snake.getX() == frog.getFrogBody().x && snake.getY() == frog.getFrogBody().y;
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

        } else if (graphics.gameState == "START") {
            this.start();
        } else {
            if (keyCode == KeyEvent.VK_SPACE)
            {
                //Score.score = 0;
                //player.move = "NOTHING";
                graphics.gameState = "RUNNING";


            }
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

    public Frog getFrog() {
        return frog;
    }

    public void setFrog(Frog frog) {
        this.frog = frog;
    }

    public AISnake getAiSnake() {
        return aiSnake;
    }

    public void setAiSnake(AISnake aiSnake) {
        this.aiSnake = aiSnake;
    }
}
