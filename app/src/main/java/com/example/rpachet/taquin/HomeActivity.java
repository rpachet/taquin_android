package com.example.rpachet.taquin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Spinner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


/**
 * Created by rpachet on 04/06/18.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    ImageView image;
    Button play;
    GridView grid;
    Spinner gridWith;
    String item;

//    ArrayList<Bitmap> bitmapList;

    Integer[] imageList;

    public static final int PICK_IMAGE = 1;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE) {
            Uri get_image = data.getData();
            image.setImageURI(get_image);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        image = (ImageView) findViewById(R.id.image);
        play = (Button) findViewById(R.id.play);
        grid = (GridView) findViewById(R.id.gridview);
        gridWith = (Spinner) findViewById(R.id.gridWith);

        play.setOnClickListener(this);

//        bitmapList = new ArrayList<Bitmap>();

        // references to our images
        imageList =  new Integer[]{
                R.drawable.bodet, R.drawable.kremlin,
                R.drawable.pise, R.drawable.bodet,
                R.drawable.kremlin, R.drawable.pise,
                R.drawable.bodet, R.drawable.kremlin,
                R.drawable.pise, R.drawable.bodet,
        };

//        for (int x = 0;x < mThumbIds.length; x++){
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mThumbIds[x]);
//
//            bitmapList.add(bitmap);
//        }

        grid.setAdapter(new DrawableAdapter(this,imageList));
        grid.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.play:


                Intent activityMain = new Intent(HomeActivity.this,MainActivity.class);

                String width = String.valueOf(gridWith.getSelectedItem());

                activityMain.putExtra("size",width);
                activityMain.putExtra("image", item);

                startActivity(activityMain);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
//
        Toast.makeText(HomeActivity.this, "" + item ,
                Toast.LENGTH_SHORT).show();
    }
}
