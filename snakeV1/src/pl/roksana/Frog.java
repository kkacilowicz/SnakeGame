package pl.roksana;

import java.awt.*;
import java.util.ArrayList;

public class Frog implements Runnable {

    private Snake snake;
    private Obstacles obstacles;
    private Rectangle FrogBody;
    private Food food;
    private String move;
    private boolean IsFrogEaten;    //flaga, czy została żabka zjedzona, jeśli tak to nowy spawn

    public Frog(Snake snake, Obstacles obstacles, Food food) {
        this.obstacles = obstacles;
        this.snake = snake;
        this.FrogBody = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        this.food = food;
        move = "UP";
        this.IsFrogEaten =true;      //zakładamy, że jet pierwotnie zjedzona (nie ma jej)
        this.FrogSpawn();
    }

    public void FrogSpawn(){

        while (IsFrogEaten) {

            IsFrogEaten = false;

            var LocationX = (int) (Math.random() * (Game.windowsWidth - 5));
            var LocationY = (int) (Math.random() * (Game.windowsHeight - 5));

            //zabezpieczenie przed tym, żeby przeszkoda nie powstała na snake'u i innych przeszkodach
            while (CheckCollision(LocationX, LocationY)) {
                LocationX = (int) (Math.random() * (Game.windowsWidth - 5));
                LocationY = (int) (Math.random() * (Game.windowsHeight - 5));
            }

            FrogBody.setLocation(LocationX * Game.windowsDimension, LocationY * Game.windowsDimension);
        }
    }

    //porusza wężem poprzez dodawanie prostokąta z przodu ciała węża i usuwanie prostokąta na końcu ciała węża
    public void move(){
        if(move != "NOTHING") {//sprawdzanie bieżącego ruchu

            Rectangle temp = FrogBody;

            if (move == "UP") { //jeżeli wąż porusza się w górę to  umieszczamy kwadrat nad pierwszym prostokątem ciała węża
                FrogBody.setLocation(temp.x, temp.y - Game.windowsDimension); //współrzędna y o jeden mniejsza bo prostokąt
                // ma pojawić się nad pierwszym prostokątem ciała węża
            } else if (move == "DOWN") { //jeżeli wąż porusza się w dół to  umieszczamy kwadrat pod pierwszym prostokątem ciała węża
                FrogBody.setLocation(temp.x, temp.y + Game.windowsDimension); //współrzędna y o jeden większa bo prostokąt
                // ma pojawić się pod pierwszym prostokątem ciała węża
            } else if (move == "LEFT") { //jeżeli wąż porusza się w lewo to  umieszczamy kwadrat po lewej od pierwszego prostokąta ciała węża
                FrogBody.setLocation(temp.x - Game.windowsDimension, temp.y); //współrzędna x o jeden mniejsza bo prostokąt
                // ma pojawić się po lewej od pierwszego prostokąta ciała węża
            } else if (move == "RIGHT") { //jeżeli wąż porusza się w prawo to  umieszczamy kwadrat po prawej od pierwszego prostokąta ciała węża
                FrogBody.setLocation(temp.x + Game.windowsDimension, temp.y); //współrzędna x o jeden większa bo prostokąt
                // ma pojawić się po prawej od pierwszego prostokąta ciała węża
            }

        }

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
        tmp.setLocation((FrogBody.x + (2*Game.windowsDimension)), FrogBody.y );
        return CheckCollision(tmp);
    }

    private boolean CollisionLeft(){
        Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        tmp.setLocation((FrogBody.x - (2*Game.windowsDimension)), FrogBody.y );
        return CheckCollision(tmp);
    }

    private boolean CollisionDown(){
        Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        tmp.setLocation(FrogBody.x, (FrogBody.y + (2*Game.windowsDimension)));
        return CheckCollision(tmp);
    }

    private boolean CollisionUp(){
        Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        tmp.setLocation(FrogBody.x, (FrogBody.y - (2*Game.windowsDimension)));
        return CheckCollision(tmp);
    }


    private boolean CheckCollision(Rectangle Frog){
        return food.LocationOnSnake(Frog, snake) ||
                CheckObstacleCollision(Frog) ||
                food.LocationOnWall(Frog) ||
                food.LocationOnFruit(Frog);
    }

    private boolean CheckCollision(int LocationX, int LocationY){
        return food.LocationOnSnake(LocationX, LocationY, snake) ||
                obstacles.CheckIfTheresObstacle(LocationX, LocationY, true) ||
                obstacles.CheckIfTheresObstacle(LocationX, LocationY, false) ||
                food.LocationOnWall(LocationX, LocationY) ||
                food.LocationOnFruit(LocationX, LocationY);
    }

    public boolean CheckObstacleCollision(Rectangle Frog){
        for (ArrayList<Rectangle> Obstacle: obstacles.getObstaclesBodies()) {
            for (Rectangle R: Obstacle) {
                if(R.x == Frog.x && R.y == Frog.y)
                    return true;
            }

        }
        return false;
    }


    public void FrogEaten(){
        IsFrogEaten = true;
    }

    @Override
    public void run() {

        if(IsFrogEaten){
            this.FrogSpawn();
        }else{
            this.AvoidObstacles();
            this.move();
        }

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

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public Obstacles getObstacles() {
        return obstacles;
    }

    public void setObstacles(Obstacles obstacles) {
        this.obstacles = obstacles;
    }

    public Rectangle getFrogBody() {
        return FrogBody;
    }

    public void setFrogBody(Rectangle frogBody) {
        FrogBody = frogBody;
    }
}
