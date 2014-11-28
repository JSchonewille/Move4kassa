
package com.example.jeff.move4kassa.library;




import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFunctions {
    private static DatabaseFunctions _instance = null;

    //region Table and column names
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ibeaconkassa";
    // Table names
    private static final String TABLE_USERLIKES = " userlikes ";
    private SQLiteOpenHelper sqLiteOpenHelper;
    // User Table Column names
    private static final String KEY_USERID = "id";
    private static final String KEY_CATEGORYNAME = "categoryname";
    //endregion

    //create instance
    private synchronized static void createInstance (final Context context) {
        if (_instance == null) _instance = new DatabaseFunctions(context);

    }

    //get instance
    public static DatabaseFunctions getInstance (final Context context) {
        if (_instance == null) createInstance (context);
        return _instance;
    }

    private DatabaseFunctions(Context context ) {
        sqLiteOpenHelper = new SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                //createTables(db);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// Drop older table if existed
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERLIKES);
                // Create tables again
                onCreate(db);
            }
        };
       sqLiteOpenHelper.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_USERLIKES);
       createTables(sqLiteOpenHelper.getWritableDatabase());

    }


    public void addUserLikes(int id, String categoryName) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERID, id); //
        values.put(KEY_CATEGORYNAME, categoryName); //
        db.insert(TABLE_USERLIKES, null, values);
        db.close(); // Closing database connection
    }

    public ArrayList<UserLike> getUserLikes(){
        ArrayList<UserLike> userlikes = new ArrayList<UserLike>();

        String selectQuery = "SELECT * FROM " + TABLE_USERLIKES;
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        boolean empty = true;
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String like = cursor.getString(1);

                for (UserLike u : userlikes)
                {
                    if (u.getUserID() == id)
                    {
                        u.addLikes(like);
                        empty = false;
                        break;
                    }
                    else {
                        empty = true;
                    }
                }
                if (empty)
                {
                    UserLike ul = new UserLike(id);
                    ul.addLikes(like);
                    userlikes.add(ul);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        // return category list
        return userlikes;
    }



/**
     * Empty tables
     * */

    public void resetUserLikes(){
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USERLIKES, null, null);
        db.close();
    }

/**
     * Create tables
     * */

    public void createTables(SQLiteDatabase db){
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERLIKES + "("
                + KEY_USERID + " INTEGER, "
                + KEY_CATEGORYNAME + " TEXT " +")";
        db.execSQL(CREATE_USER_TABLE);

    }
}
