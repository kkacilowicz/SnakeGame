package pl.roksana;

import java.awt.*;
import java.util.ArrayList;

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
    //funkcja generuje losowe współrzędne pojawiającego sie jedzenia i przypisuje je do x i y,
    // jednocześnie upewnia się że jedzenie nie wygeneruje się w miejscu w którym aktualnie na planszy znajduje się
    // ciało węża
    //funkcja dodatkowo unika powstawanie jedzenia na przeszkodach
    public void randomSpawn(){

        while (Fruits.size() < NumberOfFruits) {
            var LocationX = (int) (Math.random() * (Game.windowsWidth - 5));
            var LocationY = (int) (Math.random() * (Game.windowsHeight - 5));

            //zabezpieczenie przed tym, żeby przeszkoda nie powstała na snake'u i innych przeszkodach
            while (LocationOnSnake(LocationX, LocationY, player) ||
                    obstacles.CheckIfTheresObstacle(LocationX, LocationY, true) ||
                    obstacles.CheckIfTheresObstacle(LocationX, LocationY, false)) {
                LocationX = (int) (Math.random() * (Game.windowsWidth - 5));
                LocationY = (int) (Math.random() * (Game.windowsHeight - 5));
            }

            Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
            tmp.setLocation(LocationX * Game.windowsDimension, LocationY * Game.windowsDimension);
            Fruits.add(tmp);
        }
    }

    public void FruitEaten(){
        for (int i = 0; i <Fruits.size() ; i++) {
            if(player.getX() + Game.windowsDimension >= Fruits.get(i).x &&
                    player.getX() - Game.windowsDimension <= Fruits.get(i).x &&
                    player.getY() + Game.windowsDimension >= Fruits.get(i).y &&
                    player.getY() - Game.windowsDimension <= Fruits.get(i).y)
                Fruits.remove(i);
        }

    }

    private boolean LocationOnSnake(int LocationX, int LocationY, Snake player){

        for(Rectangle r : player.getSnakesBody()){ //dla każdego prostokąta wchodzącego w skład ciała węża
            if(r.x == LocationX && r.y == LocationY) { //sprawdza czy jego współrzędne zgadzają się z współrzędnymi
                // generowanego jedzenia, jeśli tak to zmienna isOnSnake zmienia swoją wartość na true
                return true;
            }
        }
        return false;
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
        this.FruitEaten();
        this.randomSpawn();
    }
}
