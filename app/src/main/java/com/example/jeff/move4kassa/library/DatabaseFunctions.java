/*
package com.example.jeff.move4kassa.library;

*/
/**
 * Created by Sander on 8-10-2014.
 *//*


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFunctions extends SQLiteOpenHelper {


    //region Table and column names
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ibeacon";
    // Table names
    private static final String TABLE_USER = "user";
    private static final String TABLE_CATEGORY = "category";
    private static final String TABLE_BEACONS = "beacons";
    private static final String TABLE_OFFERS = "offers";
    private static final String TABLE_PRODUCTS = "products";
    // User Table Column names
    private static final String KEY_USERID = "id";
    private static final String KEY_FIRSTNAME = "fname";
    private static final String KEY_LASTNAME = "lname";
    private static final String KEY_STREET = "street";
    private static final String KEY_HOUSENUMBER = "houseNumber";
    private static final String KEY_POSTALCODE = "postalCode";
    private static final String KEY_CITY = "city";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_FILEPATH = "filePath";

    //Category Table Column names
    private static final String KEY_CATEGORYID = "id";
    private static final String KEY_CATEGORYNAME = "categoryName";
    private static final String KEY_CATEGORYLIKED = "liked";
    //Offers Table Column names
    private static final String KEY_ID_OFFER = "id_offer";
    private static final String KEY_OFFERCATEGORY = "offer_category";
    private static final String KEY_OFFERDESCRIPTION = "offer_description";
    private static final String KEY_OFFERIMAGE = "offer_image";
    //Product Table Column names
    private static final String KEY_PRODUCT_ID = "productID";
    private static final String KEY_PRODUCT_NAME = "name";
    private static final String KEY_PRODUCT_CATEGORYID = "categoryID";
    private static final String KEY_PRODUCT_IMAGE = "image";
    private static final String KEY_PRODUCT_DESCRIPTION = "description";
    //Beacon Table Column names
    private static final String KEY_BEACON_ID = "id_beacon";
    private static final String KEY_BEACON_PRODUCTID = "beaccon_product";
    private static final String KEY_BEACON_OFFERID = "beacon_offer";
    private static final String KEY_BEACON_MAJOR = "beacon_major";
    private static final String KEY_BEACON_MINOR = "beacon_minor";
    //endregion



    public DatabaseFunctions(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
       createTables(db);
    }

    // Upgrading database
    @Override
    //TODO Moeten hier nog mee tabellen bij?
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        // Create tables again
        onCreate(db);
    }

    */
/**
     * Category functions
     * *//*

    public void addCategory(int id, String categoryName, int liked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORYID, id); //
        values.put(KEY_CATEGORYNAME, categoryName); //
        values.put(KEY_CATEGORYLIKED, liked); //
        // Inserting Row
        db.insert(TABLE_CATEGORY, null, values);
        db.close(); // Closing database connection
    }
    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> list = new ArrayList<Category>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String category = cursor.getString(1);
                int liked = cursor.getInt(2);
                Category c = new Category(id, category, liked);
                list.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return category list
        return list;
    }
    public ArrayList<Category> getLikedCategories(){
        ArrayList<Category> list = new ArrayList<Category>();
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY + " WHERE " + KEY_CATEGORYLIKED + " =  1" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String category = cursor.getString(1);
                int liked = cursor.getInt(2);
                Category c = new Category(id, category, liked);
                list.add(c);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return category list
        return list;
    }
    public void saveLikedCategories (List<Category> list){

    }

    */
/**
     * User functions
     * *//*

    public void addUser(int userID, String fname, String lname, String email, String filePath) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERID, userID); // User ID
        values.put(KEY_FIRSTNAME, fname); // FirstName
        values.put(KEY_LASTNAME, lname); // LastName
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_FILEPATH, filePath); // filePath
        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection
    }
    public User getUser(){
        User user = new User();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.setUserID(cursor.getInt(0));
            user.setName(cursor.getString(1));
            user.setLastName(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setFilePath(cursor.getString(4));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }

    */
/**
     * Offer functions
     * *//*

    public void addOffer(int id, int categoryID, String description, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID_OFFER, id); // OfferID
        values.put(KEY_OFFERCATEGORY, categoryID); // Category ID
        values.put(KEY_OFFERDESCRIPTION, description); // Offer Description
        values.put(KEY_OFFERIMAGE, image); // Offer Image
        // Inserting Row
        db.insert(TABLE_OFFERS, null, values);
        db.close(); // Closing database connection
    }
    public ArrayList<Offer> getAllOffers(){
        ArrayList<Offer> list = new ArrayList<Offer>();
        String selectQuery = "SELECT  * FROM " + TABLE_OFFERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Offer o;
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                int id = cursor.getInt(0);
                int categoryID= cursor.getInt(1);
                String description = cursor.getString(2);
                String image = cursor.getString(3);
                o = new Offer(id, categoryID,description,image);
                list.add(o);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return category list
        return list;
    }
    public Offer getOfferByID(int offerID){
        Offer o = null;
        String selectQuery = "SELECT  * FROM " + TABLE_OFFERS + " WHERE " + KEY_ID_OFFER + " = " + offerID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            int id = cursor.getInt(0);
            int categoryID= cursor.getInt(1);
            String description = cursor.getString(2);
            String image = cursor.getString(3);
            o = new Offer(id, categoryID,description,image);
        }
        cursor.close();
        db.close();
        // return category list
        return o;
    }
    public ArrayList<Offer> getOffersByLikedCategories(){
        ArrayList<Offer> list = new ArrayList<Offer>();
        String selectQuery = "SELECT  * FROM " + TABLE_OFFERS +
                             " WHERE " + KEY_OFFERCATEGORY + " IN (" +
                             " SELECT " + KEY_CATEGORYID +
                             " FROM " + TABLE_CATEGORY +
                             " WHERE " + KEY_CATEGORYLIKED + " = 1 )";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Offer o;
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                int id = cursor.getInt(0);
                int categoryID= cursor.getInt(1);
                String description = cursor.getString(2);
                String image = cursor.getString(3);
                o = new Offer(id, categoryID,description,image);
                list.add(o);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return category list
        return list;
    }

    */
/**
     * Product functions
     * *//*

    public void addProduct(int id, String name, int categoryID, String image, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_PRODUCT_ID, id); //
        values.put(KEY_PRODUCT_NAME, name); //
        values.put(KEY_PRODUCT_CATEGORYID, categoryID); //
        values.put(KEY_PRODUCT_IMAGE, image); //
        values.put(KEY_PRODUCT_DESCRIPTION, description); //
        // Inserting Row
        db.insert(TABLE_PRODUCTS, null, values);
        db.close(); // Closing database connection
    }
    public ArrayList<Product> getAllProducts(){
        ArrayList<Product> list = new ArrayList<Product>();
        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                int categoryID = Integer.parseInt(cursor.getString(2));
                String image = cursor.getString(3);
                String description = cursor.getString(4);

                Product p = new Product(id, name, categoryID, image, description);
                list.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return category list
        return list;
    }
    public Product getProductByID(int id){
        Product p = null;
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS +
                             " WHERE " + KEY_PRODUCT_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                int pid = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                int categoryID = Integer.parseInt(cursor.getString(2));
                String image = cursor.getString(3);
                String description = cursor.getString(4);

                p = new Product(pid, name, categoryID, image, description);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return category list
        return p;
    }

    */
/**
     * Beacon functions
     * *//*

    public void addBeacon(int beaconID, int productID, int offerID, int minor, int major) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_BEACON_ID, beaconID); // BEACON ID
        values.put(KEY_BEACON_MAJOR, major); // MAJOR
        values.put(KEY_BEACON_MINOR, minor); // MINOR
        values.put(KEY_BEACON_OFFERID, offerID); // OFFERID
        values.put(KEY_BEACON_PRODUCTID, productID); // PRODUCTID

        // Inserting Row
        db.insert(TABLE_BEACONS, null, values);
        db.close(); // Closing database connection
    }
    public ArrayList<ibeacon> getAllBeacons(){
        ArrayList<ibeacon> list = new ArrayList<ibeacon>();
        String selectQuery = "SELECT  * FROM " + TABLE_BEACONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                int id = cursor.getInt(0);
                int productID= cursor.getInt(1);
                int offerID= cursor.getInt(2);
                int major= cursor.getInt(3);
                int minor= cursor.getInt(4);
                ibeacon b = new ibeacon(id, offerID,productID,major,minor);
                list.add(b);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return category list
        return list;
    }
    public ibeacon getBeaconByMinor(int minorID){
        ibeacon b = null;
        String selectQuery = "SELECT  * FROM " + TABLE_BEACONS + " WHERE " + KEY_BEACON_MINOR + " = " + minorID;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){

                int id = cursor.getInt(1);
                int productID= cursor.getInt(2);
                int offerID= cursor.getInt(3);
                int major= cursor.getInt(4);
                int minor= cursor.getInt(5);
                b = new ibeacon(id, offerID,productID,major,minor);


        }
        cursor.close();
        db.close();
        // return category list
        return b;
    }

    */
/**
     * Empty tables
     * *//*

    public void resetUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();
    }
    public void resetBeaconTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_BEACONS, null, null);
        db.close();
    }
    public void resetOfferTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_OFFERS, null, null);
        db.close();
    }
    public void resetProductsTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_PRODUCTS, null, null);
        db.close();
    }
    public void resetCategory(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_CATEGORY, null, null);
        db.close();
    }

    */
/**
     * Create tables
     * *//*

    public void createTables(SQLiteDatabase db){
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_USERID + " INTEGER PRIMARY KEY,"
                + KEY_FIRSTNAME + " TEXT,"
                + KEY_LASTNAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_FILEPATH + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "("
                + KEY_CATEGORYID + " INTEGER PRIMARY KEY,"
                + KEY_CATEGORYNAME + " TEXT,"
                + KEY_CATEGORYLIKED + " INTEGER"+ ")";
        db.execSQL(CREATE_CATEGORY_TABLE);
        String CREATE_OFFERS_TABLE = "CREATE TABLE " + TABLE_OFFERS + "("
                + KEY_ID_OFFER + " INTEGER PRIMARY KEY,"
                + KEY_OFFERCATEGORY + " TEXT,"
                + KEY_OFFERDESCRIPTION + " TEXT,"
                + KEY_OFFERIMAGE + " TEXT" + ")";
        db.execSQL(CREATE_OFFERS_TABLE);
        String CREATE_BEACONS_TABLE = "CREATE TABLE " + TABLE_BEACONS + "("
                + KEY_BEACON_ID + " INTEGER PRIMARY KEY,"
                + KEY_BEACON_PRODUCTID + " INTEGER,"
                + KEY_BEACON_OFFERID + " INTEGER,"
                + KEY_BEACON_MAJOR + " INTEGER,"
                + KEY_BEACON_MINOR + " INTEGER" + ")";
        db.execSQL(CREATE_BEACONS_TABLE);
        String CREATE_PRODUCTS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
                + KEY_PRODUCT_ID + " INTEGER PRIMARY KEY,"
                + KEY_PRODUCT_NAME + " TEXT, "
                + KEY_PRODUCT_CATEGORYID + " INTEGER,"
                + KEY_PRODUCT_IMAGE + " TEXT,"
                + KEY_PRODUCT_DESCRIPTION + " TEXT" + ")";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }



}*/
