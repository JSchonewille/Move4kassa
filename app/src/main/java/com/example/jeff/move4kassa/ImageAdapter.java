package com.example.jeff.move4kassa;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.jeff.move4kassa.library.ServerRequestHandler;
import com.example.jeff.move4kassa.library.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Jeff on 21-11-2014.
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private User[] input;
    private String savedPath;
    int scale = 3;


    public ImageAdapter(Context c , User[] input, String savedpath) {
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
            imageView.setLayoutParams(new GridView.LayoutParams(400, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        try {
            File f = new File(savedPath, input[position].getFilePath());
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            b = Bitmap.createScaledBitmap(b, 300, 400, true);


            Bitmap img = addtext(b,input[position].getName());
            imageView.setImageBitmap(img);
            imageView.setLayoutParams(new GridView.LayoutParams(img.getWidth(), img.getHeight()));
        }
        catch (FileNotFoundException e)
        {
            Bitmap noimg = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.nopic);
            noimg =  Bitmap.createScaledBitmap(noimg, 300, 400, true);

            Bitmap img = addtext(noimg,input[position].getName());
            imageView.setImageBitmap(addtext(noimg,input[position].getName()));
            imageView.setLayoutParams(new GridView.LayoutParams(img.getWidth(), img.getHeight()));

        }

        return imageView;
    }


    public Bitmap addtext(Bitmap b , String text)
    {
        Bitmap b2 = Bitmap.createBitmap(b.getWidth(),(b.getHeight()+ 100 ),b.getConfig());

        Canvas canvas = new Canvas(b2);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize((int) (14 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        int x = (b.getWidth() - bounds.width())/2;
        int y = (b.getHeight() + bounds.height())/2;

        canvas.drawText(text, x, b.getHeight(), paint);
        canvas.drawColor(Color.BLUE);
        canvas.drawBitmap(b,0,0,null);
        return b2;
    }

}
