package com.example.rpachet.taquin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.ListViewCompat;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by rpachet on 04/06/18.
 */

public class ScoreActivity extends AppCompatActivity {
    ArrayList<String> scoresList;
    String newScore;
    JSONArray scoresJson;
    ListView list;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        Intent i = getIntent();

        SharedPreferences sharedPreferences = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        String scores = sharedPreferences.getString("score",null);


        if(scores !=  null && scores != "" ){
            try {
                scoresJson = new JSONArray(scores);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            scoresJson = new JSONArray();
        }

        scoresList = new ArrayList<String>();
        for (int j = 0; j< scoresJson.length(); j++){
            JSONObject score = null;
            try {
                score = scoresJson.getJSONObject(j);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                scoresList.add(score.getString("time"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        list = (ListView) findViewById(R.id.scores);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ScoreActivity.this,
                android.R.layout.simple_list_item_1, scoresList);
        list.setAdapter(adapter);
    }
}