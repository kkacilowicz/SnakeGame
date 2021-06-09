package pl.roksana;

import java.awt.*;
import java.util.ArrayList;

public class Snake implements Runnable
{

    protected ArrayList<Rectangle> snakesBody; //ciało węża będzie składać się z prostokątów
    protected int w = Game.windowsWidth;
    protected int h = Game.windowsHeight;
    protected int d = Game.windowsDimension;

    protected String move; //domyślna wartość NOTHING, wartości ustalone poprzez wciśnięcie przycisków: UP, DOWN, LEFT, RIGHT

    public Snake() {
        snakesBody = new ArrayList<>();

        Rectangle temp = new Rectangle(d, d);
        temp.setLocation(w / 2 * d, h / 2 * d); //początkowa lokalizacja węża w połowie okna gry
        snakesBody.add(temp); //dodanie prostokąta do ciała węża

        temp = new Rectangle(d, d);
        temp.setLocation((w / 2 - 1) * d,(h / 2) * d); //drugi prostokąt tworzący ciało węża jest o 1 na lewo od poprzedniego
        snakesBody.add(temp);

        temp = new Rectangle(d, d);
        temp.setLocation((w / 2 - 2) * d,(h / 2) * d); //trzeci prostokąt tworzący ciało węża jest o 2 na lewo od poprzedniego
        snakesBody.add(temp);

        move = "NOTHING"; //kiedy wąż zostaje utworzony na początku gry zmienna move ma wartość "NOTHING"

    }

    //porusza wężem poprzez dodawanie prostokąta z przodu ciała węża i usuwanie prostokąta na końcu ciała węża
    synchronized public void move(){
        if(move != "NOTHING") {//sprawdzanie bieżącego ruchu
            Rectangle first = snakesBody.get(0); //pierwszy element ciała węża

            Rectangle temp = new Rectangle(Game.windowsDimension, Game.windowsDimension); //tworzymy nowy prostokąt

            if (move == "UP") { //jeżeli wąż porusza się w górę to  umieszczamy kwadrat nad pierwszym prostokątem ciała węża
                temp.setLocation(first.x, first.y - Game.windowsDimension); //współrzędna y o jeden mniejsza bo prostokąt
                // ma pojawić się nad pierwszym prostokątem ciała węża
            } else if (move == "DOWN") { //jeżeli wąż porusza się w dół to  umieszczamy kwadrat pod pierwszym prostokątem ciała węża
                temp.setLocation(first.x, first.y + Game.windowsDimension); //współrzędna y o jeden większa bo prostokąt
                // ma pojawić się pod pierwszym prostokątem ciała węża
            } else if (move == "LEFT") { //jeżeli wąż porusza się w lewo to  umieszczamy kwadrat po lewej od pierwszego prostokąta ciała węża
                temp.setLocation(first.x - Game.windowsDimension, first.y); //współrzędna x o jeden mniejsza bo prostokąt
                // ma pojawić się po lewej od pierwszego prostokąta ciała węża
            } else if (move == "RIGHT") { //jeżeli wąż porusza się w prawo to  umieszczamy kwadrat po prawej od pierwszego prostokąta ciała węża
                temp.setLocation(first.x + Game.windowsDimension, first.y); //współrzędna x o jeden większa bo prostokąt
                // ma pojawić się po prawej od pierwszego prostokąta ciała węża
            }

            snakesBody.add(0, temp);
            snakesBody.remove(snakesBody.size() - 1); //usunięcie ostatniego elementu ciała węża
        }

    }

    //funkcja dodająca
    public void grow() { //to samo co w move, tylko że nie usuwamy prostokąta Z końca ciała węża
        Rectangle first = snakesBody.get(0); //pierwszy element ciała węża

        Rectangle temp = new Rectangle(Game.windowsDimension, Game.windowsDimension); //tworzymy nowy prostokąt

        if (move == "UP") { //jeżeli wąż porusza się w górę to  umieszczamy kwadrat nad pierwszym prostokątem ciała węża
            temp.setLocation(first.x, first.y - Game.windowsDimension); //współrzędna y o jeden mniejsza bo prostokąt
            // ma pojawić się nad pierwszym prostokątem ciała węża
        } else if (move == "DOWN") { //jeżeli wąż porusza się w dół to  umieszczamy kwadrat pod pierwszym prostokątem ciała węża
            temp.setLocation(first.x, first.y + Game.windowsDimension); //współrzędna y o jeden większa bo prostokąt
            // ma pojawić się pod pierwszym prostokątem ciała węża
        } else if (move == "LEFT") { //jeżeli wąż porusza się w lewo to  umieszczamy kwadrat po lewej od pierwszego prostokąta ciała węża
            temp.setLocation(first.x - Game.windowsDimension, first.y); //współrzędna x o jeden mniejsza bo prostokąt
            // ma pojawić się po lewej od pierwszego prostokąta ciała węża
        } else if (move == "RIGHT") { //jeżeli wąż porusza się w prawo to  umieszczamy kwadrat po prawej od pierwszego prostokąta ciała węża
            temp.setLocation(first.x + Game.windowsDimension, first.y); //współrzędna x o jeden większa bo prostokąt
            // ma pojawić się po prawej od pierwszego prostokąta ciała węża
        }

        snakesBody.add(0, temp);

    }

    synchronized public ArrayList<Rectangle> getSnakesBody() {
        return snakesBody;
    }

    public void setSnakesBody(ArrayList<Rectangle> snakesBody) {
        this.snakesBody = snakesBody;
    }

    //kolizja zachodzi kiedy wąż dotknie ściany pierwszym prostokątem swojego ciała
    public int getX() {
        return snakesBody.get(0).x;
    }

    public int getY() {
        return snakesBody.get(0).y;
    }

    public String getMove() {
        return move;
    }

    //funkcje zmieniające kierunek ruchu węża
    public void up(){
        move = "UP";
    }

    public void down(){
        move = "DOWN";
    }

    public void left(){
        move = "LEFT";
    }

    public void right(){
        move = "RIGHT";
    }

    @Override
    public void run() {
        this.move();
    }
}
