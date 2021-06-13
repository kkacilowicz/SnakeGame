package pl.roksana;

import java.awt.*;
import java.util.ArrayList;

/// <summary>
/// Class to create obstacles
/// </summary>
public class Obstacles {

    private ArrayList<ArrayList<Rectangle>> ObstaclesBodies;
    private int LengthOfObstacles;
    private int NumberOfObstacles;


    public Obstacles() {
       this.GenerateObstacles();
    }

    /// <summary>
    /// Function to generate obstacles on proper locations
    /// </summary>
    public void GenerateObstacles(){

        ObstaclesBodies = new ArrayList<ArrayList<Rectangle>>();
        var Dimension = Game.windowsDimension;
        LengthOfObstacles = 20;
        NumberOfObstacles = 3;

        for(int j = 0; j < NumberOfObstacles; j++) {

            var OneObstacle = new ArrayList<Rectangle>();
            var IsHorizontal = (Math.random() < 0.5);    //to zwraca random true lub false
            var StartXLocation = (int) (Math.random() * (Game.windowsWidth-5));
            var StartYLocation = (int) (Math.random() * (Game.windowsHeight-5));

            //zabezpieczenie przed tym, żeby przeszkoda nie powstała na snake'u i innych przeszkodach
            if (LocationXOnSnake(StartXLocation) ||LocationYOnSnake(StartYLocation) ) {
                while (LocationXOnSnake(StartXLocation) ||LocationYOnSnake(StartYLocation) ||
                        CheckIfTheresObstacle(StartXLocation, StartYLocation, IsHorizontal) ||
                        TooCloseToWall(StartXLocation, StartYLocation)) {
                    StartXLocation = (int) (Math.random() * (Game.windowsWidth-5));
                    StartYLocation = (int) (Math.random() * (Game.windowsHeight-5));
                }
            }


            if (IsHorizontal) {
                for (int i = 0; i < LengthOfObstacles; i++) {
                    Rectangle tmp = new Rectangle(Dimension, Dimension);
                    tmp.setLocation((StartXLocation + i) * Dimension, StartYLocation * Dimension);
                    OneObstacle.add(tmp);
                }
            } else {
                for (int i = 0; i < LengthOfObstacles; i++) {
                    Rectangle tmp = new Rectangle(Dimension, Dimension);
                    tmp.setLocation(StartXLocation * Dimension, (StartYLocation + i) * Dimension);
                    OneObstacle.add(tmp);
                }
            }
        ObstaclesBodies.add(OneObstacle);
        }
    }

    /// <summary>
    /// Function to check obstacles if obstacle is not too close to wall
    /// </summary>
    private boolean TooCloseToWall(int LocationX, int LocationY){
        return LocationX <= LengthOfObstacles || LocationX >= Game.windowsWidth - (LengthOfObstacles +3)
                || LocationY <= LengthOfObstacles || LocationY >= Game.windowsHeight - (LengthOfObstacles +3);
    }

    /// <summary>
    /// Function to check obstacles if obstacle is not on snake
    /// </summary>
    private boolean LocationXOnSnake(int LocationX){
        return LocationX > Game.windowsWidth / 2 - 4 && LocationX < Game.windowsWidth / 2 + 3;
    }

    /// <summary>
    /// Function to check obstacles if obstacle is not on snake
    /// </summary>
    private boolean LocationYOnSnake(int LocationY){
        return LocationY > Game.windowsHeight / 2 - 4 && LocationY < Game.windowsWidth / 2 + 3;
    }


    /// <summary>
    /// Function to check obstacles if theres obstacle
    /// </summary>
    public boolean CheckIfTheresObstacle(int LocationX, int LocationY, boolean IsHorizontal){

        if(!IsHorizontal) {
            for (ArrayList<Rectangle> Obstacle : ObstaclesBodies) {
                for (Rectangle r : Obstacle) {
                    if ((LocationX * Game.windowsDimension <= r.x && (LocationX + LengthOfObstacles) * Game.windowsDimension >= r.x)
                            && ((LocationY - 1) * Game.windowsDimension <= r.y && (LocationY + 1) * Game.windowsDimension >= r.y))
                        return true;
                }
            }
        }else{
            for (ArrayList<Rectangle> Obstacle : ObstaclesBodies) {
                for (Rectangle r : Obstacle) {
                    if ((LocationY * Game.windowsDimension <= r.y && (LocationY + LengthOfObstacles) * Game.windowsDimension >= r.y)
                            && ((LocationX - 1) * Game.windowsDimension <= r.x && (LocationX + 1) * Game.windowsDimension >= r.x))
                        return true;
                }
            }
        }

        return false;
    }

    public boolean CheckIfTheresObstacle(Rectangle R, boolean IsHorizontal){

        if(!IsHorizontal) {
            for (ArrayList<Rectangle> Obstacle : ObstaclesBodies) {
                for (Rectangle r : Obstacle) {
                    if ((R.x <= r.x && (R.x + (LengthOfObstacles * Game.windowsDimension) >= r.x)
                            && (R.y -  Game.windowsDimension) <= r.y) && (R.y + Game.windowsDimension >= r.y))
                        return true;
                }
            }
        }else{
            for (ArrayList<Rectangle> Obstacle : ObstaclesBodies) {
                for (Rectangle r : Obstacle) {
                    if ((R.y <= r.y) && (R.y + (LengthOfObstacles * Game.windowsDimension) >= r.y)
                            && (R.x - Game.windowsDimension <= r.x && R.x + Game.windowsDimension >= r.x))
                        return true;
                }
            }
        }

        return false;
    }
    public ArrayList<ArrayList<Rectangle>> getObstaclesBodies() {
        return ObstaclesBodies;
    }

    public int getLengthOfObstacles() {
        return LengthOfObstacles;
    }

    public void setLengthOfObstacles(int lengthOfObstacles) {
        LengthOfObstacles = lengthOfObstacles;
    }

    public int getNumberOfObstacles() {
        return NumberOfObstacles;
    }

    public void setNumberOfObstacles(int numberOfObstacles) {
        NumberOfObstacles = numberOfObstacles;
    }
}
