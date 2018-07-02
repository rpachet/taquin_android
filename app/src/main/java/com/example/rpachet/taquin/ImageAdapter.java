package com.example.rpachet.taquin;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by rpachet on 04/06/18.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Bitmap vide;
    private Bitmap videToAdd;
    private int level;
    private ArrayList<Bitmap> imgPieces;
    private ArrayList<Bitmap> ordrePieces;
    private Animation animation;

    public ImageAdapter(Context c, int l, Bitmap b, int w, int h) {
        mContext = c;
        level = l;
        decoupe(b, w, h);
        melange();
    }

    @Override
    public int getCount() {
        return level*level;
    }

    @Override
    public Object getItem(int position) {
        return imgPieces.get(position);
    }


    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        //Placement des pieces dans grille
        imageView.setImageBitmap(imgPieces.get(position));
        imageView.setAdjustViewBounds(true);
        return imageView;
    }


    private void decoupe(Bitmap img, int w, int h) {
        imgPieces = new ArrayList<>(); // stockage des pieces dans une ArrayList
        ordrePieces = new ArrayList<>(); // Tableau des pieces dans l'ordre

        // Récupération des tailles de la bitmap
        int width = img.getWidth();
        int height = img.getHeight();

        // Découpe de l'image
        for(int i=0; i<level; i++){
            for(int j=0; j<level; j++){
                if(i == level-1 && j == level-1){
                    vide = Bitmap.createBitmap(width/level, height/level, Bitmap.Config.ALPHA_8);
                    Bitmap piece = vide;
                    imgPieces.add(piece);
                    ordrePieces.add(piece);
                    videToAdd =  Bitmap.createBitmap(img, j*width/level, i*height/level, width/level, height/level);

                }else{
                    Bitmap piece = Bitmap.createBitmap(img, j*width/level, i*height/level, width/level, height/level);
                    imgPieces.add(piece);
                    ordrePieces.add(piece);
                }
            }
        }
    }

    // Deplacement pieces - position de la piece en paramètre
    public  Animation deplacement(int position){
        int caseVide = imgPieces.indexOf(vide); // récupération de la position de la piece vide
        int[] testCase = {0,0,0,0};
        // Initialisation des valeurs de destinations de l'animation
        float destinationX = 0;
        float destinationY = 0;

        if(position >=0 && position < imgPieces.size()){
            // on rempli le tableau de cases à tester
            if(position+1 != 0){
                testCase[0] = position+1;
            }
            if(position+level != 0){
                testCase[1] = position+level;
            }
            if(position-1 != 0){
                testCase[2] = position-1;
            }
            if(position-level != 0){
                testCase[3] = position-level;
            }
            // On test les 4 cotes de la piece
            for(int i=0; i<4; i++){
                if(testCase[i] == caseVide){

                    // vers la droite
                    if(i==0){
                        destinationX = 1;
                    }else if(i==1){ // vers le bas
                        destinationY = 1;
                    }else if(i==2){ // vers la gauche
                        destinationX = -1;
                    }else if(i==3){ // vers le haut
                        destinationY = -1;
                    }

                    // si case vide

                    Bitmap temp = imgPieces.get(position);

                    imgPieces.set(position, vide);
                    imgPieces.set(caseVide, temp);
                }
            }
        }
        // on créé l'animation
        animation = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_SELF, (float)0, TranslateAnimation.RELATIVE_TO_SELF, destinationX,
                TranslateAnimation.RELATIVE_TO_SELF, (float)0, TranslateAnimation.RELATIVE_TO_SELF, destinationY);
        return animation;
    }

    //melange aléatoire 1000 fois
    public void melange(){
        for(int i=0; i<1000; i++){ // Nb de coup joué
            int currentPosition = imgPieces.indexOf(vide); //position  de la case vide
            int random = (int) (Math.random()*level); // random en fonction du nb case du jeu

            // déplacement en fonction du random
            if(random == 0){
                deplacement(currentPosition+1);
            }
            if(random == 1){
                deplacement(currentPosition+level);
            }
            if(random == 2){
                deplacement(currentPosition-1);
            }
            if(random == 3){
                deplacement(currentPosition-level);
            }
        }
    }

    // Test si le jeu est terminé
    public boolean bonOrdre(){
        // pour chaque piece de l'image
        for(int i=0; i<ordrePieces.size(); i++){
            // si la piece n'est pas la piece vide
            if(ordrePieces.get(i) != null){
                // si les deux pieces sont différent on retourne false, le jeu n'est pas terminé
                if(!ordrePieces.get(i).sameAs(imgPieces.get(i))){
                    return false;
                }
            }
        }
        imgPieces.set(imgPieces.indexOf(vide), videToAdd);
        return true;
    }
}