package pl.roksana;

import java.awt.*;
import java.util.ArrayList;

public class AISnake extends Snake{
    private final Snake player;
    private final Food food;
    private final Obstacles obstacles;
    private final Frog frog;

    public AISnake(Game game) {
        this.StartSpawn();
        this.player = game.getPlayer();
        this.food = game.getFood();
        this.obstacles = game.getObstacles();
        this.frog = game.getFrog();
        this.move = "UP";
    }

    private void StartSpawn(){
        this.snakesBody = new ArrayList<>();

        Rectangle temp = new Rectangle(d, d);
        temp.setLocation(w / 4 * d, h / 4 * d); //początkowa lokalizacja węża w połowie okna gry
        snakesBody.add(temp); //dodanie prostokąta do ciała węża

        temp = new Rectangle(d, d);
        temp.setLocation((w / 4 - 1) * d,(h / 4) * d); //drugi prostokąt tworzący ciało węża jest o 1 na lewo od poprzedniego
        snakesBody.add(temp);

        temp = new Rectangle(d, d);
        temp.setLocation((w / 4 - 2) * d,(h / 4) * d); //trzeci prostokąt tworzący ciało węża jest o 2 na lewo od poprzedniego
        snakesBody.add(temp);

        move = "NOTHING"; //kiedy wąż zostaje utworzony na początku gry zmienna move ma wartość "NOTHING"
    }

    public void AvoidObstacles(){
        if(move != "NOTHING") {//sprawdzanie bieżącego ruchu


            if(move == "UP" && CollisionUp()) {
                this.left();
            }else if(move == "LEFT" && CollisionLeft()){
                this.down();
            }else if(move == "DOWN" && CollisionDown()){
                this.right();
            }else if(move == "RIGHT" && CollisionRight()){
                this.up();
            }
        }
    }



    private boolean CollisionRight(){
        Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        tmp.setLocation((getX() + (2*Game.windowsDimension)), getY() );
        return CheckCollision(tmp);
    }

    private boolean CollisionLeft(){
        Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        tmp.setLocation((getX() - (2*Game.windowsDimension)), getY() );
        return CheckCollision(tmp);
    }

    private boolean CollisionDown(){
        Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        tmp.setLocation(getX(), (getY() + (2*Game.windowsDimension)));
        return CheckCollision(tmp);
    }

    private boolean CollisionUp(){
        Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        tmp.setLocation(getX(), (getY() - (2*Game.windowsDimension)));
        return CheckCollision(tmp);
    }


    private boolean CheckCollision(Rectangle Head){
        return food.LocationOnSnake(Head, player) ||
                obstacles.CheckIfTheresObstacle(Head, true) ||
                obstacles.CheckIfTheresObstacle(Head, false) ||
                food.LocationOnWall(Head) ||
                food.LocationOnFruit(Head);
    }

    @Override
    public void run() {
        this.move();
    }

}
