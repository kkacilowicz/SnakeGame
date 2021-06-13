package pl.roksana;

import java.text.SimpleDateFormat;
import java.util.Date;

/// <summary>
/// Class to manage format of score etc
/// </summary>
public class Score {
    private int Score;
    private String DateOfScore;

    public Score(int Score) {
        this.Score = Score;
        var Date = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.DateOfScore = formatter.format(Date);
    }
    public Score(int Score, String Date) {
        this.Score = Score;
        this.DateOfScore = Date;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getDateOfScore() {
        return DateOfScore;
    }

    public void setDateOfScore(String dateOfScore) {
        DateOfScore = dateOfScore;
    }
}
