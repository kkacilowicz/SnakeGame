package pl.roksana;

import java.awt.*;

public class Frog implements Runnable {

    private Snake snake;
    private Obstacles obstacles;
    private Rectangle FrogBody;
    private Food food;

    public Frog(Snake snake, Obstacles obstacles, Food food) {
        this.obstacles = obstacles;
        this.snake = snake;
        this.FrogBody = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        this.food = food;
        this.FrogSpawn();
    }

    public void FrogSpawn(){

        var LocationX = (int) (Math.random() * (Game.windowsWidth - 5));
        var LocationY = (int) (Math.random() * (Game.windowsHeight - 5));

        //zabezpieczenie przed tym, żeby przeszkoda nie powstała na snake'u i innych przeszkodach
        while (CheckCollision(LocationX,LocationY)) {
            LocationX = (int) (Math.random() * (Game.windowsWidth - 5));
            LocationY = (int) (Math.random() * (Game.windowsHeight - 5));
        }

        FrogBody.setLocation(LocationX * Game.windowsDimension, LocationY * Game.windowsDimension);
    }



    private boolean CheckCollision(int LocationX, int LocationY){
        return food.LocationOnSnake(LocationX, LocationY, snake) ||
                obstacles.CheckIfTheresObstacle(LocationX, LocationY, true) ||
                obstacles.CheckIfTheresObstacle(LocationX, LocationY, false) ||
                food.LocationOnWall(LocationX, LocationY) ||
                food.LocationOnFruit(LocationX, LocationY);
    }

    @Override
    public void run() {

    }

    private void MoveUp() {
        var tmp = FrogBody;
        FrogBody.setLocation(tmp.x, tmp.y - Game.windowsDimension);
    }
    private void MoveLeft() {
        var tmp = FrogBody;
        FrogBody.setLocation(tmp.x - Game.windowsDimension, tmp.y);
    }
    private void MoveRight() {
        var tmp = FrogBody;
        FrogBody.setLocation(tmp.x + Game.windowsDimension, tmp.y);
    }
    private void MoveDown() {
        var tmp = FrogBody;
        FrogBody.setLocation(tmp.x, tmp.y + Game.windowsDimension);
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
