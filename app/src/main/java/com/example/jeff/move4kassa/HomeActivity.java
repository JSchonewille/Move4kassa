package com.example.jeff.move4kassa;

import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.jeff.move4kassa.library.DatabaseFunctions;
import com.example.jeff.move4kassa.library.ServerRequestHandler;
import com.example.jeff.move4kassa.library.User;
import com.example.jeff.move4kassa.library.UserLike;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class HomeActivity extends FragmentActivity implements personInfo.OnFragmentInteractionListener {

    GridView gridView;
    ArrayList<User> list;
    Fragment userInfo;
    ImageAdapter adapter;
    String filePath = "";
    ArrayList<UserLike> userLikes = new ArrayList<UserLike>();
    DatabaseFunctions dbf;
    final static String TAG_FRAGMENT = "INFOFRAGMENT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        gridView = (GridView) findViewById(R.id.gridView);

        dbf = DatabaseFunctions.getInstance(getApplicationContext());

        userLikes = dbf.getUserLikes();
        Userrefesh();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                GridOnClick(parent, v, position, id);
            }
        });

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

    public void setadapter(ArrayList<User> input) {
        User[] test = new User[input.size()];
        test = input.toArray(test);

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        filePath = directory.getAbsolutePath().toString();
        adapter = new ImageAdapter(this, test, filePath);
        gridView.setAdapter(null);
        gridView.setAdapter(adapter);
        gridView.invalidate();

    }

    public ArrayList<User> getPresentUsers() {
        ServerRequestHandler.getPresentUsers(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    JSONObject o = jsonArray.getJSONObject(0);
                    if (!o.has("returnvalue")) {
                        Log.e("henk",o.toString());
                        list.clear();
                        list = User.fromJSON(jsonArray);
                        setadapter(list);
                    }
                } catch (JSONException e) {
                    Log.e("presentusererror", e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError.networkResponse != null) {
                    Log.e("NETWORKERROR", volleyError.networkResponse.statusCode + " " + new String(volleyError.networkResponse.data));
                    list = null;
                } else {
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

    public void Userrefesh() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            getPresentUsers();
                        } catch (Exception e) {
                            Log.e("henk", e.toString());
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 2 * 1000);
    }

    public void GridOnClick(AdapterView<?> parent, View v,
                            int position, long id) {
        User u = list.get(position);

        ArrayList<String> likes = new ArrayList<String>();
        String img = "";
        if (filePath.length() > 8 && u.getFilePath().length() > 8) {
            String f = filePath;
            String f2 = u.getFilePath().substring(7);
            img = f + "/" + f2;
        }
        for (UserLike ul : userLikes) {
            if (ul.getUserID() == u.getUserID()) {
                likes = ul.getLikes();
                break;
            }
        }

        personInfo p = new personInfo().newInstance(u.getName(), u.getLastName(), u.getEmail(), likes, img);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (gridView.getNumColumns() != 6) {
            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        }
        transaction.replace(R.id.infoLayout, p,TAG_FRAGMENT).addToBackStack(TAG_FRAGMENT);
        transaction.commit();
        gridView.setNumColumns(5);
    }

    @Override
    public void onFragmentInteraction() {
        gridView.setNumColumns(7);

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        final personInfo fragment = (personInfo) getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        fragment.close();
    }

}
