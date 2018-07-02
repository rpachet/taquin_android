package com.example.rpachet.taquin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.Chronometer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    GridView board;
    ArrayList<Bitmap> ImgList;
    ImageAdapter imageAdapter;
    Animation animation;
    Button btnRejouer;
    Button score;
    Chronometer timer;
    String time;
    JSONArray scoresJson;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();

        String size = i.getStringExtra("size");
        int level = Integer.parseInt(size);

        String image = i.getStringExtra("image");
        Uri imageURL = i.getParcelableExtra("imageURL");
        String TypeImage = i.getStringExtra("TypeImage");

        Toast.makeText(this, TypeImage, Toast.LENGTH_SHORT).show();
//
        ImgList = new ArrayList<Bitmap>();
        bitmap = null;

        if (TypeImage.equals("URI")){
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),imageURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            bitmap = BitmapFactory.decodeResource(getResources(), (Integer.parseInt(image)));
        }


        Display d = getWindowManager().getDefaultDisplay();
        DisplayMetrics m = new DisplayMetrics();
        d.getMetrics(m);

        board = (GridView) findViewById(R.id.Board);
        imageAdapter = new ImageAdapter(this, level, bitmap, m.widthPixels, m.heightPixels);
        board.setAdapter(imageAdapter);
        // On set le nombre de colonne selon le niveau
        board.setNumColumns(level);
        board.setOnItemClickListener(this);

        btnRejouer = (Button) findViewById(R.id.btnRejouer);
        btnRejouer.setOnClickListener(this);
        btnRejouer.setVisibility(View.INVISIBLE); // le bouton pour revenir est invisible

        score = (Button) findViewById(R.id.score);
        score.setOnClickListener(this);
        score.setVisibility(View.INVISIBLE); // Bouton score invisible

        timer = (Chronometer) findViewById(R.id.timer); // Initialisation du chrono

        timer.start();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

        // Permutation des pièces de l'image
        animation = imageAdapter.deplacement(position);
        View view = board.getChildAt(position);
        animation.setDuration(300); // time de l'animation


        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Rafraichissement après permutation
                board.invalidateViews();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation); // démarrage de l'animation
        if(imageAdapter.bonOrdre()){

            // Affichage du message de victoire
            Toast toast = Toast.makeText(MainActivity.this, "Gagné ! Le jeu est terminé", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            timer.stop();
            board.setOnItemClickListener(null); // la partie est terminée, on stop le listner pour ne plus donner la possibilité de déplacer les cases
            btnRejouer.setVisibility(View.VISIBLE); // le bouton pour revenir à l'accueil s'affiche
            score.setVisibility(View.VISIBLE); // le bouton pour le score s'affiche

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


            time =  timer.getText().toString();
            JSONObject score = new JSONObject();

            try {
                score.put("time",time);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            scoresJson.put(score);


            sharedPreferences = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE);

            sharedPreferences.edit()
                    .putString("score",scoresJson.toString())
                    .apply();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnRejouer:


                Intent activityHome= new Intent(MainActivity.this,HomeActivity.class);


                startActivity(activityHome);
                break;

            case R.id.score:
                Intent activityScore= new Intent(MainActivity.this,ScoreActivity.class);

                activityScore.putExtra("time",time);

                startActivity(activityScore);
        }
    }
}
