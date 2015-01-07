package com.example.jeff.move4kassa.library;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sander on 11-11-2014.
 */
public class ServerRequestHandler {
    ArrayList<User> list;



    ////////////////
    //REQUESTS
    ////////////////
    public static void getPresentUsers(Response.Listener<JSONArray> l, Response.ErrorListener el) {
        String URL = Config.GETPRESENTUSERS;

        JsonArrayRequest req = new JsonArrayRequest(URL, l, el);

        RequestController.getInstance().addToRequestQueue(req);
    }

    public static void SyncImage(Response.Listener<JSONObject> l, Response.ErrorListener el, final int id,Context c){
        String URL = Config.SYNCUSERIMAGE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("customerID", Integer.toString(id));
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params), l, el);
        //JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params), l, el);

        RequestController.getInstance().addToRequestQueue(request);
    }

    public static void getAllLikes(Response.Listener<JSONArray> l, Response.ErrorListener el) {
        String URL = Config.GETALLLIKES;

        JsonArrayRequest req = new JsonArrayRequest(URL, l, el);

        RequestController.getInstance().addToRequestQueue(req);
    }

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, 1);
    }

    public static void getUserImages(Response.Listener<JSONArray> l, Response.ErrorListener el) {
        String URL = Config.GETUSERIMAGES;

        JsonArrayRequest req = new JsonArrayRequest(URL, l, el);

        RequestController.getInstance().addToRequestQueue(req);
    }

    public void getUserImagesParsed(){
        getUserImages(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                Log.d("Images array", jsonArray.toString());
                //TODO: Parse hier je images uit de array en doe er iets mee
               /* bijvoorbeerd zo:
                byte[] decoded = Base64.decode(jsonArray.getJSONObject( int index).
                getString("profileImage").getBytes(), Base64.DEFAULT);
                String path = saveImage(decoded);

                laat deze methode dan bijvoorbeeld een array met paden terugsturen van elke image*/

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError.networkResponse != null)
                    Log.e("NETWORKERROR", volleyError.networkResponse.statusCode + " " + new String(volleyError.networkResponse.data));
                else {
                    if (volleyError.getMessage() == null)
                        Log.e("NETWORKERROR", "timeout");
                    else
                        Log.e("NETWORKERROR", volleyError.getMessage());
                }
            }
        });
    }




    public String saveImage(byte[] byteArray) {
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        File dir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + "/Move4Kassa");
        dir.mkdirs();

        File mypath = new File(dir, "ProfileImage.jpeg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            return mypath.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /////////////////////////////
    //IMPLEMENTED REQUESTS
    /////////////////////////////



}

