package com.example.rpachet.taquin;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


/**
 * Created by rpachet on 04/06/18.
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    ImageView image;
    Button play;
    Button add;

    GridView grid;
    Spinner gridWith;
    String item;
    Uri selectedUri;
    String TypeImage;
//    ArrayList<Bitmap> bitmapList;

    Integer[] imageList;

    public static final int PICK_IMAGE = 1;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        image = (ImageView) findViewById(R.id.image);
        play = (Button) findViewById(R.id.play);
        add = (Button) findViewById(R.id.add);

        grid = (GridView) findViewById(R.id.gridview);

        gridWith = (Spinner) findViewById(R.id.gridWith);

        play.setOnClickListener(this);
        add.setOnClickListener(this);


        // references to our images
        imageList =  new Integer[]{
                R.drawable.panda,  R.drawable.what,   R.drawable.tigrou,
                R.drawable.shiba,  R.drawable.loutre, R.drawable.shiba2,
                R.drawable.girafe, R.drawable.renard, R.drawable.chaton
        };


        grid.setAdapter(new DrawableAdapter(this,imageList));
        grid.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.add:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"select Picture"),PICK_IMAGE);

                break;
            case R.id.play:


                Intent activityMain = new Intent(HomeActivity.this,MainActivity.class);

                String width = String.valueOf(gridWith.getSelectedItem());
                activityMain.putExtra("size",width);

                activityMain.putExtra("imageURL",selectedUri);
                activityMain.putExtra("image", item);
                activityMain.putExtra("TypeImage",TypeImage);

                startActivity(activityMain);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        item = parent.getItemAtPosition(position).toString();
        TypeImage = "Bitmap";
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        if (requestCode == PICK_IMAGE) {
            selectedUri = data.getData();
            TypeImage = "URI";
        }
    }
    }

