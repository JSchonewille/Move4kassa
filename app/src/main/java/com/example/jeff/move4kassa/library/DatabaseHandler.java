/*
package com.example.jeff.move4kassa.library;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.jeff.move4klant.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by Sander on 7-11-2014.
 *//*

public class DatabaseHandler {
    private static DatabaseHandler _instance = null;
    private static DatabaseFunctions db;

    private boolean checkinstatus;

   public DatabaseHandler() {

    }

    //create instance
    private synchronized static void createInstance (final Context context) {
        if (_instance == null) _instance = new DatabaseHandler();
        db  = new DatabaseFunctions(context);
    }

    //get instance
    public static DatabaseHandler getInstance (final Context context) {
        if (_instance == null) createInstance (context);
        return _instance;
    }

    //CATEGORIES FUNCTIONS
    public void updateCategories(){
        ServerRequestHandler.getAllCategories(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                List<Category> catList = db.getLikedCategories();
                db.resetCategory();
                List<Category> list = Category.fromJSON(jsonArray);

                Log.d("Categorie Update", jsonArray.toString());
                for (Category cat : list) {
                    try {
                        int liked = 0;
                        for (Category c : catList) {
                            if (c.getName().equals(cat.getName()))
                            {liked = 1;}
                        }
                        db.addCategory(cat.getID(), cat.getName(), liked);

                    } catch (Exception e) {
                        Log.d("exception", e.toString());
                    }
                    Log.d("Categorie Update", "SUCCES");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
    }
    public ArrayList<Category> getAllCategories(){
        return db.getAllCategories();
    }
    public List<Category> getLikedCategories(){
        return db.getLikedCategories();
    }
    public void saveLikedCategories(int userID, List<Category> likes){

        List<Category> list = db.getAllCategories();
        Integer[] categoryids = new Integer[likes.size()];
        db.resetCategory();
        int i =0;
        for ( Category l : likes) {
            categoryids[i]=l.getID();
            i++;
        }
        for (Category cat : list) {

            try {

                i++;
                int liked = 0;
                for (Category c : likes) {
                    if (c.getName().equals(cat.getName()))
                    {liked = 1;}
                }
                db.addCategory(cat.getID(), cat.getName(), liked);

            } catch (Exception e) {
                Log.d("exception", e.toString());
            }


            Log.d("Like Update", "SUCCES");
        }

        ServerRequestHandler.uploadLikes(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonArray) {
                Log.d("Likes Upload", jsonArray.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(volleyError.networkResponse != null)
                    Log.e("NETWORKERROR", volleyError.networkResponse.statusCode + " " + new String(volleyError.networkResponse.data));
                else
                    Log.e("NETWORKERROR", volleyError.getMessage());
            }
        }, userID, categoryids);
    }

    //OFFER FUNCTIONS
    public void updateOffers(){
        ServerRequestHandler.getAllOffers(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                db.resetOfferTable();
                List<Offer> list = Offer.fromJSON(jsonArray);

                Log.d("Offer Update", jsonArray.toString());
                for (int i = 0; i<list.size();i++ ){
                    try {
                        db.addOffer(list.get(i).getID(), list.get(i).getCategoryID(), list.get(i).getDescription(), list.get(i).getImage());
                        Log.d("Offer Update", "SUCCES");
                    }
                    catch (Exception e){
                        Log.d("exception", e.toString());
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
    }
    public ArrayList<Offer> getAllOffers(){
        return db.getAllOffers();
    }
    public Offer getOfferById(int offerID){return db.getOfferByID(offerID);}
    public ArrayList<Offer> getOfferByLikedCategories (){return db.getOffersByLikedCategories();}

    //PRODUCTS FUNCTIONS
    public ArrayList<Product> getAllProducts(){return db.getAllProducts();}
    public Product getProductByID(int id){return db.getProductByID(id);}
    public void updateProducts(){
        ServerRequestHandler.getAllProducts(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                db.resetProductsTable();
                List<Product> list = Product.fromJSON(jsonArray);

                Log.d("Products Update", jsonArray.toString());
                for (int i = 0; i < list.size(); i++) {
                    try {
                        db.addProduct(list.get(i).getProductID(), list.get(i).getName(), list.get(i).getCategoryID(), list.get(i).getImage(), list.get(i).getDescription());
                        Log.d("Product Update", "SUCCES");
                    } catch (Exception e) {
                        Log.d("exception", e.toString());
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
    }

    //BEACON FUNCTIONS
    public void updateBeacons(){
        ServerRequestHandler.getAllBeacons(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                db.resetBeaconTable();
                Log.d("Beacon UPdate", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        int id = (Integer) jsonArray.getJSONObject(i).getInt("beaconID");
                        int product = (Integer) jsonArray.getJSONObject(i).getInt("productID");
                        int offer = (Integer) jsonArray.getJSONObject(i).getInt("offerID");
                        int minor = (Integer) jsonArray.getJSONObject(i).getInt("minor");
                        int major = (Integer) jsonArray.getJSONObject(i).getInt("major");
                        db.addBeacon(id, product, offer, minor, major);
                        Log.d("Beacon Update", "SUCCES");
                    } catch (Exception e) {
                        Log.d("exception", e.toString());
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
    }
    public ArrayList<ibeacon> getAllBeacons(){
        return db.getAllBeacons();
    }
    public ibeacon getBeaconByMinor(int minorID){
      return db.getBeaconByMinor(minorID);
    }

    //USER FUNCTIONS
    public void checkinout(int userid){
        ServerRequestHandler.checkinout(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonArray) {
                Log.d("CHECK-in/out", jsonArray.toString());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError.networkResponse != null)
                    Log.e("NETWORKERROR", volleyError.networkResponse.statusCode + " " + new String(volleyError.networkResponse.data));
                else {
                    if(volleyError.getMessage() == null)
                        Log.e("NETWORKERROR", "timeout");
                    else
                    Log.e("NETWORKERROR", volleyError.getMessage());
                }
            }
        }, userid);
    }
    public boolean checkinstatus(int userID){

        ServerRequestHandler.checkinstatus(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonArray) {
                Log.d("CHECKIN STATUS", jsonArray.toString());
                try {
                    checkinstatus = jsonArray.getBoolean("returnvalue");
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError.networkResponse != null)
                    Log.e("NETWORKERROR", volleyError.networkResponse.statusCode + " " + new String(volleyError.networkResponse.data));
                else
                    Log.e("NETWORKERROR", volleyError.getMessage());
            }
        }, userID);

        return checkinstatus;
    }

    public void setLocalCheckinStatus(Context context, boolean check){
        PrefUtils.saveToPrefs(context, context.getString(R.string.PREFS_CHECKEDIN), check);
    }
    public boolean getLocalCheckinStatus(Context context, boolean defaultvalue){
        Boolean checkedin = PrefUtils.getFromPrefs(context, context.getString(R.string.PREFS_AUTO_LOGIN_KEY),  defaultvalue) ;
        return checkedin;
    }
    public void uploadUserImage(int userID, byte[] image){
        ServerRequestHandler.uploadUserImage(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonArray) {
                Log.d("Image Upload", jsonArray.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(volleyError.networkResponse != null)
               Log.e("NETWORKERROR", volleyError.networkResponse.statusCode + " " + new String(volleyError.networkResponse.data));
                else
                {
                    if(volleyError.getMessage() == null)
                        Log.e("NETWORKERROR", "timeout");
                    else
                        Log.e("NETWORKERROR", volleyError.getMessage());
                }
            }
        }, userID, image);
    }
    public User getUser(){return db.getUser();}
    public void addUser(int id, String fname, String lname, String email, String filePath){
        db.resetUser();
        db.addUser(id, fname,  lname,  email, filePath);
    }
    public void resetUser(){db.resetUser();}


    //OTHER FUNCTIONS
    public void updateAll(){
        updateProducts();
        updateCategories();
        updateBeacons();
        updateOffers();
    }

}
*/
