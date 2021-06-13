package pl.roksana;

import java.awt.*;
import java.util.ArrayList;

import static java.lang.Math.*;
/// <summary>
/// Class which services the AI Snake
/// </summary>
public class AISnake extends Snake{
    private final Snake player;
    private final Food food;
    private final Obstacles obstacles;

    /// <summary>
    /// AI Snake gets info from game to know what to search for, avoid etc.
    /// </summary>
    /// <param name="game"> - game and its properties</param>
    public AISnake(Game game) {
        this.StartSpawn();
        this.player = game.getPlayer();
        this.food = game.getFood();
        this.obstacles = game.getObstacles();
        this.move = "UP";
    }
    /// <summary>
    /// Function to spawn AI snake
    /// </summary>
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

    /// <summary>
    /// Function that helps with getting directions of AI snake so as to eat food and avoid obstacles etc.
    /// </summary>
    public void LookForFood(){

        var Target = FindNearestFood();

        if(Target != null) {


            if (move == "UP" ){
                if(CollisionUp() ||
                        (Target.y == getY() && Target.x != getX())){

                    if(Target.x >= getX())
                        this.right();
                    else if(Target.x < getX())
                        this.left();
                    }

            }else if (move == "LEFT") {

                if(CollisionLeft() ||
                        (Target.x == getX() && Target.y != getY())){

                    if(Target.y > getY())
                        this.down();
                    else if(Target.y < getY())
                        this.up();
                    }
            }else if(move == "RIGHT"){

                if(CollisionRight() ||
                        (Target.x == getX() && Target.y != getY())){

                    if(Target.y >= getY())
                        this.down();
                    else if(Target.y < getY())
                        this.up();
                }
            }else if(move == "DOWN"){
                if(CollisionDown() ||
                        (Target.y == getY() && Target.x != getX())){

                    if(Target.x >= getX())
                        this.right();
                    else if(Target.x < getX())
                        this.left();
                }
            }
        }


    }

    /// <summary>
    /// Function that helps finding food to go to
    /// </summary>
    synchronized private Rectangle FindNearestFood(){
        var Fruits = food.getFruits();

        if(Fruits ==null)
            return null;

        var NearestFood = Fruits.get(0);
        if(NearestFood == null)
            return null;

        var Length = 100000;

        for (Rectangle Food: Fruits ) {
            var TmpLength = LengthToObject(Food);
            if(TmpLength<Length)
                NearestFood = Food;
        }
        return NearestFood;
    }

    /// <summary>
    /// Computing length between AI snake and this object
    /// </summary>
    /// <param name="rect"> - rectangle to be considered</param>
    private int LengthToObject(Rectangle Rect){
        if(Rect ==null)
            return 10000000;

        var ValueX = abs(getX() - Rect.x);           //w sumie nie postrzebne jest tutaj abs()
        var ValueY = abs(getY() -  Rect.y);
        return (int) sqrt(pow(ValueX,2)+pow(ValueY,2));
    }

    /// <summary>
    /// Checking collision from right
    /// </summary>
    private boolean CollisionRight(){
        Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        tmp.setLocation((getX() + (2*Game.windowsDimension)), getY() );
        return CheckCollision(tmp);
    }

    /// <summary>
    /// Checking collision from left
    /// </summary>
    private boolean CollisionLeft(){
        Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        tmp.setLocation((getX() - (2*Game.windowsDimension)), getY() );
        return CheckCollision(tmp);
    }

    /// <summary>
    /// Checking collision from down
    /// </summary>
    private boolean CollisionDown(){
        Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        tmp.setLocation(getX(), (getY() + (2*Game.windowsDimension)));
        return CheckCollision(tmp);
    }

    /// <summary>
    /// Checking collision from up
    /// </summary>
    private boolean CollisionUp(){
        Rectangle tmp = new Rectangle(Game.windowsDimension, Game.windowsDimension);
        tmp.setLocation(getX(), (getY() - (2*Game.windowsDimension)));
        return CheckCollision(tmp);
    }

    /// <summary>
    /// Checking collision with obstacle
    /// </summary>
    public boolean CheckObstacleCollision(Rectangle Head){
        for (ArrayList<Rectangle> Obstacle: obstacles.getObstaclesBodies()) {
            for (Rectangle R: Obstacle) {
                if(R.x == Head.x && R.y == Head.y)
                    return true;
            }

        }
        return false;
    }

    /// <summary>
    /// Checking collision
    /// </summary>
    private boolean CheckCollision(Rectangle Head){
        return food.LocationOnSnake(Head, player) ||
                CheckObstacleCollision(Head) ||
                food.LocationOnWall(Head);
    }

    /// <summary>
    /// Functions to be run by thread
    /// </summary>
    @Override
    public void run() {
        this.LookForFood();
        this.move();
    }

}
