package com.example.jeff.move4kassa.library;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Jeff on 7-1-2015.
 */
public class ImageSync {
    private String path;
    private Context mContext;
    private int productID;
 public ImageSync(int productid, Context c, String path )
 {
     this.path = path;
     this.mContext = c;
     this.productID = productid;
     Sync(productid,c);
 }

    public void Sync(int id , Context context)
    {
        ServerRequestHandler.SyncImage(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    Log.e("succes" ,jsonObject.getString("returnvalue") );
                    //byte[] decoded = Base64.decode(o.getString("image"), Base64.DEFAULT);
                    String image = jsonObject.getString("returnvalue");
                    byte[] decoded = Base64.decode(image, Base64.DEFAULT);
                    Bitmap bmp = BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
                    Log.d("Parsing","parsed image");
                    saveToInternalSorage(bmp, path,mContext);
                    Log.d("Parsing","saved");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                    Log.e("error" ,volleyError.toString() );
            }
        },id,context);
    }

    private void saveToInternalSorage(Bitmap bitmapImage , String filename,Context c){
        ContextWrapper cw = new ContextWrapper(c);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir we remove the images/ by usign substring
        File mypath=new File(directory,filename.substring(7));

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
