package com.uzerjamal.cricketscoremanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    private int wickets;
    private int overs = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendWicketAndOver(View v){

        EditText getWickets = (EditText) findViewById(R.id.numberOfPlayers);
        wickets = Integer.parseInt(String.valueOf(getWickets.getText()));

        EditText getOvers = (EditText) findViewById(R.id.numberOfOvers);
        overs = Integer.parseInt(String.valueOf(getOvers.getText()));

        Intent startManager = new Intent(this, mainscreen.class);

        startManager.putExtra("Send Wickets", wickets);
        startManager.putExtra("Send Overs", overs);

        startActivity(startManager);
    }
}
