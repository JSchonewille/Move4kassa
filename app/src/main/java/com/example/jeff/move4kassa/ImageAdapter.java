package com.example.jeff.move4kassa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.jeff.move4kassa.library.ImageSync;
import com.example.jeff.move4kassa.library.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Jeff on 21-11-2014.
 */
public class ImageAdapter extends BaseAdapter {

    int scale = 3;
    private Context mContext;
    private User[] input;
    private String savedPath;


    public ImageAdapter(Context c, User[] input, String savedpath) {
        this.input = input;
        mContext = c;
        this.savedPath = savedpath;

    }

    @Override
    public int getCount() {
        return input.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(350, 350));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        try {
            File f = new File(savedPath, input[position].getFilePath().substring(7));
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            b = Bitmap.createScaledBitmap(b, 350, 350, true);


            Bitmap img = addtext(b, input[position].getName());
            imageView.setImageBitmap(img);
            imageView.setLayoutParams(new GridView.LayoutParams(img.getWidth(), img.getHeight()));
        } catch (Exception e) {
            ImageSync s = new  ImageSync(input[position].getUserID(),mContext,input[position].getFilePath());
            Bitmap noimg = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.nopic);
            noimg = Bitmap.createScaledBitmap(noimg, 350, 350, true);

            Bitmap img = addtext(noimg, input[position].getName());
            imageView.setImageBitmap(addtext(noimg, input[position].getName()));
            imageView.setLayoutParams(new GridView.LayoutParams(img.getWidth(), img.getHeight()));

        }


        return imageView;
    }


    public Bitmap addtext(Bitmap b, String text) {
        Bitmap b2 = Bitmap.createBitmap(b.getWidth(), (b.getHeight() + 100), b.getConfig());

        Canvas canvas = new Canvas(b2);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        // text size in pixels
        paint.setTextSize((int) (14 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (b2.getWidth() - bounds.width()) / 2;
        int y = (b2.getHeight() + bounds.height()) / 2;


        if(text.length() > 13)
        {

           text = text.substring(0,15);
           text += "...";
           x +=60;
        }

        //canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(b, 0, 0, null);
        canvas.drawText(text, x, b2.getHeight() - 50, paint);
        return b2;
    }

}
