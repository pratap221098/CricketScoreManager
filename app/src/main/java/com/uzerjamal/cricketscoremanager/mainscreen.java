package com.uzerjamal.cricketscoremanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import static android.R.attr.y;
import static com.uzerjamal.cricketscoremanager.R.id.ballsAndOvers;
import static com.uzerjamal.cricketscoremanager.R.id.currentRunRate;
import static com.uzerjamal.cricketscoremanager.R.id.score;
import static com.uzerjamal.cricketscoremanager.R.id.teamLabel;

public class mainscreen extends AppCompatActivity {

    private int totalOvers;
    private int oversLeft;
    private int totalWickets;
    private int run=0;
    private int wicket=0;
    private int overs=0;
    private int balls=0;
    private int gameOver=0;
    private int teamARuns=0;
    private int teamBstart=0;
    private float runsRequired=0;
    private int ballsRequired=0;
    private float runRate=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainscreen);

        Intent dataFromMainActivity = getIntent();
        totalOvers = dataFromMainActivity.getExtras().getInt("Send Overs");
        totalWickets = dataFromMainActivity.getExtras().getInt("Send Wickets");

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-3940256099942544/6300978111");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextView scoreView = (TextView) findViewById(score);
        scoreView.setTextSize(16 * getResources().getDisplayMetrics().density);
    }

    //Displays the score.
    public void displayScore() {
        TextView scoreView = (TextView) findViewById(score);
        scoreView.setText(run + "/" + wicket);
    }

    //Displays Overs and Balls
    public void displayOver(){
        if(balls==6){
            balls=0;
            overs+=1;
        }

        TextView ballsView = (TextView) findViewById(ballsAndOvers);
        ballsView.setText("Overs: " + overs + "        "  + "Balls: " + balls);

        oversLeft();
    }

    //Displays the Run Rate
    public void displayRunRate(){
        TextView runRateView = (TextView) findViewById(currentRunRate);
            runRateView.setText("Current RR: " + String.format("%.1f", runRate));
    }


    //Adds to the score

    public void addZero(View v){
        balls +=1;
        startSecondTeamBatting();
        displayScore();
        displayOver();
        runRateCalculator();
    }


    public void addOne(View v){
        balls +=1;
        run+=1;
        startSecondTeamBatting();
        displayScore();
        displayOver();
        runRateCalculator();
    }

    public void addTwo(View v){
        balls +=1;
        run+=2;
        startSecondTeamBatting();
        displayScore();
        displayOver();
        runRateCalculator();
    }

    public void addThree(View v){
        balls +=1;
        run+=3;
        startSecondTeamBatting();
        displayScore();
        displayOver();
        runRateCalculator();
    }

    public void addFour(View v){
        balls +=1;
        run+=4;
        startSecondTeamBatting();
        displayScore();
        displayOver();
        runRateCalculator();
    }

    public void addSix(View v){
        balls +=1;
        run+=6;
        startSecondTeamBatting();
        displayScore();
        displayOver();
        runRateCalculator();

    }

    //Adds to the wicket

    public void addWicket(View v){
        balls +=1;
        wicket +=1;
        displayScore();
        displayOver();
        runRateCalculator();
        startSecondTeamBatting();

        if(totalWickets - wicket == 0 && teamBstart==0){
            TextView gameover = (TextView) findViewById(R.id.gameOver);
            gameover.setText("ALL PLAYERS OUT! PRESS ANY BUTTON TO START SECOND TEAM'S BATTING");
            gameOver=1;
        }

        if(totalWickets - wicket == 0 && teamBstart==1){
            TextView gameover = (TextView) findViewById(R.id.gameOver);
            gameover.setText("TEAM  A WINS!!");
            gameover.setTextColor(Color.parseColor("#00E676"));
        }
    }

    //Adds a Wide Ball
    public void addWide(View v){
        run+=1;
        displayScore();
        displayOver();
        runRateCalculator();
        startSecondTeamBatting();
    }

    //Adds a No Ball
    public void addNoBall(View v){
        balls-=1;
        run+=1;
        displayScore();
        displayOver();
        runRateCalculator();
        startSecondTeamBatting();
    }

    //Calculates the run rate
    public void runRateCalculator() {
        if (overs == 0) {
            if(balls==0){
                runRate = run*6;
            }
            else
            {
                runRate = (run / balls) * 6;
            }
        }
        else
        {
            if (balls == 0) {
                runRate = run / overs;
            }
            else
            {
                float divider = overs * 6 + balls;
                float multiplier = run * 6;

                runRate = multiplier/divider;
            }
        }
        displayRunRate();
    }

    //Checks how many overs are left
    private void oversLeft(){
        TextView remainingOvers = (TextView) findViewById(R.id.oversLeftView);

        if(teamBstart==1){
            oversLeft = totalOvers - overs;
            if(oversLeft == totalOvers){
                remainingOvers.setText((oversLeft-1) + "." + (6 - balls) + " overs left" + "          Runs left to win: "+ (teamARuns - run));
            }
            else {
                remainingOvers.setText((oversLeft - 1) + "." + (6 - balls) + " overs left" + "          Runs left to win: "+ (teamARuns - run));
            }

            if((teamARuns-run)<=0){
                TextView gameover = (TextView) findViewById(R.id.gameOver);
                gameover.setText("TEAM B WINS!");
                gameover.setTextColor(Color.parseColor("#00E676"));
            }
        }
        else{
            oversLeft = totalOvers - overs;
            if(oversLeft == totalOvers){
                remainingOvers.setText((oversLeft-1) + "." + (6 - balls) + " overs left");
            }
            else {
                remainingOvers.setText((oversLeft - 1) + "." + (6 - balls) + " overs left");
            }
        }

        //End from overs
        if(oversLeft==0 && balls == 0 && teamBstart==0){
            remainingOvers.setText("0 overs left");
            TextView gameover = (TextView) findViewById(R.id.gameOver);
            gameover.setText("OVERS COMPLETE, PRESS ANY BUTTON TO START SECOND TEAM'S BATTING");
            gameOver=1;
        }

        if(oversLeft==0 && balls == 0 && teamBstart==1){
            remainingOvers.setText("0 overs left" + "          Runs left to win: "+ (teamARuns - run));
            TextView gameover = (TextView) findViewById(R.id.gameOver);
            gameover.setText("TEAM  A WINS!!");
            gameover.setTextColor(Color.parseColor("#00E676"));
        }
    }


    //Start's second team's battling
    private void startSecondTeamBatting(){
        if(gameOver==1){
            teamARuns = run;
            wicket = 0;
            run = 0;
            overs = 0;
            balls = 0;
            runRate = 0;
            gameOver=0;
            ballsRequired = totalOvers * 6;
            teamBstart=1;
            displayRunRate();
            displayScore();
            displayOver();

            TextView gameover = (TextView) findViewById(R.id.gameOver);
            gameover.setText(" ");
            TextView teamlabel = (TextView) findViewById(R.id.teamLabel);
            teamlabel.setText("TEAM B RUNS");
        }
    }

    //Resets all variables
    public void restart(View v){

        Intent startManager = new Intent(this, MainActivity.class);
        startActivity(startManager);

        /*wicket = 0;
        run = 0;
        overs = 0;
        balls = 0;
        runRate = 0;
        teamARuns = 0;
        gameOver = 0;
        displayRunRate();
        displayScore();
        displayOver();

        TextView gameover = (TextView) findViewById(R.id.gameOver);
        gameover.setText(" ");
        */
    }

}
