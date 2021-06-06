package pl.roksana;

import java.awt.*;

public class Food {
    private int x; //współrzędne jedzenia na planszy
    private int y;
    private Obstacles obstacles;

    public Food(Snake player, Obstacles obstacles) {
        this.obstacles = obstacles;
        this.randomSpawn(player);
    }
    //funkcja generuje losowe współrzędne pojawiającego sie jedzenia i przypisuje je do x i y,
    // jednocześnie upewnia się że jedzenie nie wygeneruje się w miejscu w którym aktualnie na planszy znajduje się
    // ciało węża
    //funkcja dodatkowo unika powstawanie jedzenia na przeszkodach
    public void randomSpawn(Snake player){

        var LocationX = (int) (Math.random() * (Game.windowsWidth-5));
        var LocationY = (int) (Math.random() * (Game.windowsHeight-5));

        //zabezpieczenie przed tym, żeby przeszkoda nie powstała na snake'u i innych przeszkodach
        while (LocationOnSnake(LocationX, LocationY, player) ||
                obstacles.CheckIfTheresObstacle(LocationX, LocationY, true) ||
                obstacles.CheckIfTheresObstacle(LocationX, LocationY, false)){
            LocationX = (int) (Math.random() * (Game.windowsWidth-5));
            LocationY = (int) (Math.random() * (Game.windowsHeight-5));
        }

        x = LocationX;
        y = LocationY;

    }

    private boolean LocationOnSnake(int LocationX, int LocationY, Snake player){

        for(Rectangle r : player.getSnakesBody()){ //dla każdego prostokąta wchodzącego w skład ciała węża
            if(r.x == x && r.y == y) { //sprawdza czy jego współrzędne zgadzają się z współrzędnymi
                // generowanego jedzenia, jeśli tak to zmienna isOnSnake zmienia swoją wartość na true
                return true;
            }
        }
        return false;
    }
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
