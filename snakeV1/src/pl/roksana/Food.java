package pl.roksana;

import java.awt.*;

public class Food {
    private int x; //współrzędne jedzenia na planszy
    private int y;

    public Food(Snake player) {
        this.randomSpawn(player);
    }
    //funkcja generuje losowe współrzędne pojawiającego sie jedzenia i przypisuje je do x i y,
    // jednocześnie upewnia się że jedzenie nie wygeneruje się w miejscu w którym aktualnie na planszy znajduje się
    // ciało węża
    public void randomSpawn(Snake player){
        boolean isOnSnake = true;

        while(isOnSnake) {
            isOnSnake = false;

            x = (int) (Math.random() * Game.windowsWidth);
            y = (int) (Math.random() * Game.windowsHeight);

            for(Rectangle r : player.getSnakesBody()){ //dla każdego prostokąta wchodzącego w skład ciała węża
                if(r.x == x && r.y == y) { //sprawdza czy jego współrzędne zgadzają się z współrzędnymi
                    // generowanego jedzenia, jeśli tak to zmienna isOnSnake zmienia swoją wartość na true
                    isOnSnake = true;
                }
            }
        }
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
