package pl.roksana;

import javax.xml.transform.Result;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/// <summary>
/// Class which services saving Top10 results to file and reading from this file
/// </summary>
public class Leaderboard {
    private ArrayList<Score> Results;
    private boolean isSaved;
    private boolean isDownloaded;
    private String Filename;
    public Leaderboard(String filename) throws FileNotFoundException {
        Results = new ArrayList<Score>();
        isSaved = false;
        isDownloaded = false;
        this.Filename = filename;
        this.DownloadResults();
    }

    /// <summary>
    /// Function to save results to file
    /// </summary>
    public void SaveResults(){
        if(!isSaved) {
            StoreOnlyTop10();
            try (var filewriter = new FileWriter(this.Filename)){
                for (Score result: Results) {
                    filewriter.write(result.getDateOfScore());
                    filewriter.write("\t\t" + result.getScore() + " \n");
                }
                filewriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /// <summary>
    /// Function to remove additional results
    /// </summary>
    private void StoreOnlyTop10(){

        Results.sort(Comparator.comparing(Score::getScore));
        Collections.reverse(Results);
        if(Results.size()>10)
            Results.remove(10);

    }

    /// <summary>
    /// Function to download results from file so as to be printed
    /// </summary>
    public void DownloadResults() throws FileNotFoundException {
        if(!isDownloaded) {
            File FileWithResults = new File(Filename);
            Scanner Reader = new Scanner(FileWithResults);
            while (Reader.hasNextLine()) {
                String data = Reader.nextLine();
                String DateOfResult = data.substring(0,19);
                String ExtractScore = data.substring(20,data.length()-1);
                ExtractScore = ExtractScore.replaceAll("\\D+","");
                var score = Integer.parseInt(ExtractScore);
                var Result = new Score(score, DateOfResult);
                this.Results.add(Result);
                System.out.println(data);
            }
        }
    }

    public void ResultsAreNotDownloaded(){
        isDownloaded = false;
    }

    public void ResultsAreDownloaded(){
        isDownloaded = true;
    }

    public void ScoreIsNotSaved(){
        isSaved = false;
    }

    public void ScoreIsSaved(){
        isSaved = true;
    }

    /// <summary>
    /// Function to add results
    /// </summary>
    public void AddResult(Score result){
        if(result == null)
            return;
        Results.add(result);
    }
    public ArrayList<Score> getResults() {
        return Results;
    }

    public void setResults(ArrayList<Score> results) {
        Results = results;
    }
}
