package pl.roksana;

import java.awt.*;
import java.util.ArrayList;


public class Obstacles {

    private ArrayList<Rectangle> ObstaclesBodies;

    public Obstacles() {
       this.GenerateObstacles();
    }

    public void GenerateObstacles(){
        ObstaclesBodies = new ArrayList<Rectangle>();
        var Dimension = Game.windowsDimension;
        var LengthOfObstacles = 10;

        var StartXLocation = (int) (Math.random() * Game.windowsWidth);
        var StartYLocation = (int) (Math.random() * Game.windowsHeight);

        //zabezpieczenie przed tym, żeby przeszkoda powstała na snake'u
        if((StartXLocation > Game.windowsWidth/2 -4 && StartXLocation < Game.windowsWidth/2 +3)
                || (StartYLocation > Game.windowsHeight/2 -4 && StartYLocation < Game.windowsWidth/2 + 3))
        {
            while ((StartXLocation > Game.windowsWidth/2 -4 && StartXLocation < Game.windowsWidth/2 +3)
                    || (StartYLocation > Game.windowsHeight/2 -4 && StartYLocation < Game.windowsWidth/2 + 3)){
                StartXLocation = (int) (Math.random() * Game.windowsWidth);
                StartYLocation = (int) (Math.random() * Game.windowsHeight);
            }
        }

        var VerticalOrHorizontal = (Math.random() <0.5);    //to zwraca random true lub false

        if(VerticalOrHorizontal) {
            for (int i = 0; i < LengthOfObstacles; i++) {
                Rectangle tmp = new Rectangle(Dimension, Dimension);
                tmp.setLocation((StartXLocation + i) * Dimension, StartYLocation * Dimension);
                ObstaclesBodies.add(tmp);
            }
        }
        else {
            for (int i = 0; i < LengthOfObstacles; i++) {
                Rectangle tmp = new Rectangle(Dimension, Dimension);
                tmp.setLocation(StartXLocation * Dimension, (StartYLocation-i) * Dimension);
                ObstaclesBodies.add(tmp);
            }
        }
    }

    public ArrayList<Rectangle> getObstaclesBodies() {
        return ObstaclesBodies;
    }
}
