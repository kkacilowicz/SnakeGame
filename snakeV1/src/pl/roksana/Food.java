package pl.roksana;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;


/// <summary>
/// Class to service food generation
/// </summary>
public class Food implements Runnable {
    private ArrayList<Rectangle> Fruits;
    private int NumberOfFruits;
    private final Obstacles obstacles;
    private Snake player;


    public Food(Snake player, Obstacles obstacles) {
        Fruits = new ArrayList<Rectangle>();
        NumberOfFruits = 3;
        this.obstacles = obstacles;
        this.player = player;
        this.randomSpawn();
    }

    /// <summary>
    /// Function generates random coordinates of appearing food and assigns it to variables x and y,
    ///checks if food doesn't generate in the same place as snake's body or obstacles occurs
    /// </summary>
    public void randomSpawn(){

        while (Fruits.size() < NumberOfFruits) {
            var LocationX = (int) (Math.random() * (Game.windowsWidth - 5));
            var LocationY = (int) (Math.random() * (Game.windowsHeight - 5));

            //zabezpieczenie przed tym, żeby przeszkoda nie powstała na snake'u i innych przeszkodach
            while (NotPossibleLocation(LocationX, LocationY)) {
                LocationX = (int) (Math.random() * (Game.windowsWidth - 5));
                LocationY = (int) (Math.random() * (Game.windowsHeight - 5));
            }

            Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
            tmp.setLocation(LocationX * Game.windowsDimension, LocationY * Game.windowsDimension);
            Fruits.add(tmp);
        }
    }

    /// <summary>
    /// Function to remove from list if fruit is eaten
    /// </summary>
    public void FruitEaten(Snake snake){
        for (int i = 0; i <Fruits.size() ; i++) {
            if(snake.getX() + Game.windowsDimension >= Fruits.get(i).x &&
                    snake.getX() - Game.windowsDimension <= Fruits.get(i).x &&
                    snake.getY() + Game.windowsDimension >= Fruits.get(i).y &&
                    snake.getY() - Game.windowsDimension <= Fruits.get(i).y)
                Fruits.remove(i);
        }

    }

    private boolean NotPossibleLocation(int LocationX, int LocationY){
        return LocationOnSnake(LocationX, LocationY, player) ||
                obstacles.CheckIfTheresObstacle(LocationX, LocationY, true) ||
                obstacles.CheckIfTheresObstacle(LocationX, LocationY, false) ||
                LocationOnWall(LocationX, LocationY) ||
                LocationOnFruit(LocationX, LocationY);
    }

    public boolean LocationOnFruit(int LocationX, int LocationY){

        var PositionX = LocationX * Game.windowsDimension;
        var PositionY = LocationY * Game.windowsDimension;
        for (Rectangle fruit: Fruits) {
            if(PositionX + Game.windowsDimension >= fruit.x &&
                    PositionX - Game.windowsDimension <= fruit.x &&
                    PositionY + Game.windowsDimension >= fruit.y &&
                    PositionY - Game.windowsDimension <= fruit.y)
                return true;
        }

        return false;
    }

    public boolean LocationOnFruit(Rectangle R){

        for (Rectangle fruit: Fruits) {
            if(R.x + Game.windowsDimension >= fruit.x &&
                    R.x - Game.windowsDimension <= fruit.x &&
                    R.y + Game.windowsDimension >= fruit.y &&
                    R.y - Game.windowsDimension <= fruit.y)
                return true;
        }

        return false;
    }
    synchronized public boolean LocationOnSnake(int LocationX, int LocationY, Snake player){

        for(Rectangle r : player.getSnakesBody()){ //dla każdego prostokąta wchodzącego w skład ciała węża
            if(r.x == LocationX && r.y == LocationY) { //sprawdza czy jego współrzędne zgadzają się z współrzędnymi
                // generowanego jedzenia, jeśli tak to zmienna isOnSnake zmienia swoją wartość na true
                return true;
            }
        }
        return false;
    }

    synchronized public boolean LocationOnSnake(Rectangle R, Snake player){

        var SnakesBody = new ArrayList<Rectangle>(player.getSnakesBody());

        for(Rectangle r : SnakesBody){ //dla każdego prostokąta wchodzącego w skład ciała węża
            if(r==null) return false;
            if(r.x == R.x && r.y == R.y) { //sprawdza czy jego współrzędne zgadzają się z współrzędnymi
                // generowanego jedzenia, jeśli tak to zmienna isOnSnake zmienia swoją wartość na true
                return true;
            }
        }
        return false;
    }

    public boolean LocationOnWall(int LocationX, int LocationY) {
        return LocationX < 0 || LocationX >= (Game.windowsWidth-2) || LocationY < 0 || LocationY >= (Game.windowsHeight-4);
    }

    public boolean LocationOnWall(Rectangle R) {
        return R.x < 0 || R.x >= (Game.windowsWidth-2)*Game.windowsDimension || R.y < 0 || R.y >= (Game.windowsHeight-4)*Game.windowsDimension;
    }

    public ArrayList<Rectangle> getFruits() {
        return Fruits;
    }

    public void setFruits(ArrayList<Rectangle> fruits) {
        Fruits = fruits;
    }

    public int getNumberOfFruits() {
        return NumberOfFruits;
    }

    public void setNumberOfFruits(int numberOfFruits) {
        NumberOfFruits = numberOfFruits;
    }

    @Override
    public void run() {

        this.randomSpawn();

    }
}
