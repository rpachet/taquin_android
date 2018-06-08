package com.example.rpachet.taquin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    GridView board;
    ArrayList<Bitmap> ImgList;
    Bitmap img;
    int m_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();

        String size = i.getStringExtra("size");
        int checkSize = (int) Math.pow(Double.parseDouble(size),2);
        String image = i.getStringExtra("image");

//        Drawable drawable = getResources().getDrawable(Integer.parseInt(image),null);

        ImgList = new ArrayList<Bitmap>();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), (Integer.parseInt(image)));

        splitImage(bitmap,checkSize);



        board = (GridView) findViewById(R.id.Board);
        board.setAdapter(new ImageAdapter(this,ImgList));

        Toast.makeText(MainActivity.this, "" + size + " " +image ,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void splitImage(Bitmap originImage, int chunkNumbers) {

        //For the number of rows and columns of the grid to be displayed
        int rows,cols;

        //For height and width of the small image chunks
        int chunkHeight,chunkWidth;

        Bitmap bitmap = originImage;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        rows = cols = (int) Math.sqrt(chunkNumbers);
        Toast.makeText(MainActivity.this, "sizeBox : " + rows,Toast.LENGTH_SHORT).show();
        chunkHeight = bitmap.getHeight() / rows;
        chunkWidth = bitmap.getWidth() / cols;

        //xCoord and yCoord are the pixel positions of the image chunks
        int yCoord = 0;
        for(int x = 0; x < rows; x++) {
            int xCoord = 0;
            for(int y = 0; y < cols; y++) {
                ImgList.add(Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, chunkWidth, chunkHeight));
                xCoord += chunkWidth;
            }
            yCoord += chunkHeight;
        }
    }

}
