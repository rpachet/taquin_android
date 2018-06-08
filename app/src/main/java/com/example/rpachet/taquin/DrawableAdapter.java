package com.example.rpachet.taquin;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by rpachet on 08/06/18.
 */
public class DrawableAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] mThumbIds;
    public DrawableAdapter(Context c,Integer[] listImage ) {
        mContext = c;
        mThumbIds =  listImage;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return mThumbIds[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }
}
