package com.example.jeff.move4kassa;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.jeff.move4kassa.library.ServerRequestHandler;
import com.example.jeff.move4kassa.library.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.channels.UnresolvedAddressException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class HomeActivity extends Activity {

    GridView gridView;
    ArrayList<User> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
       gridView =(GridView) findViewById(R.id.gridView);
        Userrefesh();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setadapter(ArrayList<User> input)
    {
        User[] test = new User[input.size()];
        test = input.toArray(test);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        ImageAdapter a = new ImageAdapter(this,test,directory.getAbsolutePath().toString());
        gridView.setAdapter(a);
        gridView.invalidate();
        Log.e("succes", "wooot woot");
    }

    public ArrayList<User> getPresentUsers(){
        ServerRequestHandler.getPresentUsers(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    JSONObject o = jsonArray.getJSONObject(0);
                    if(!o.has("returnvalue")) {
                        list = User.fromJSON(jsonArray);
                        setadapter(list);
                    }
                } catch (JSONException e) {
                    Log.e("presentusererror",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError.networkResponse != null) {
                    Log.e("NETWORKERROR", volleyError.networkResponse.statusCode + " " + new String(volleyError.networkResponse.data));
                    list = null;
                }
                else {
                    if (volleyError.getMessage() == null)
                        Log.e("NETWORKERROR", "timeout");
                    else
                        Log.e("NETWORKERROR", volleyError.getMessage());

                    list = null;
                }
            }
        });

        return list;
    }

    public void Userrefesh()
    {
        final Handler handler = new Handler();
        Timer  timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @SuppressWarnings("unchecked")
                    public void run() {
                        try {
                            getPresentUsers();
                        }
                        catch (Exception e) {
                            Log.e("henk",e.toString());
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 60* 1000);
    }

}
