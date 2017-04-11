package tutorial.cs5551.com.homeapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ashweeza on 3/15/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "items.db";
    public static final String TABLE_NAME = "allitemstable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ITEMNAME";
    public static final String COL_3 = "ITEMBRAND";
    public static final String COL_4 = "BNAME";
    public static final String COL_5 = "BEMAIL";
    public static final String COL_6 = "DUEDATE";
    public static final String COL_7 = "COMPLETED";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ITEMNAME TEXT," +
                "ITEMBRAND TEXT,BNAME TEXT,BEMAIL TEXT,DUEDATE TEXT,COMPLETED BOOLEAN)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void createTable(String tablename) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table IF NOT EXISTS " + tablename + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ITEMNAME TEXT, " +
                "ITEMBRAND TEXT,BNAME TEXT,BEMAIL TEXT,DUEDATE TEXT)");
    }

    public boolean insertData(String tablename, String itemname, String itembrand, String bname, String bemail, String duedate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, itemname);
        values.put(COL_3, itembrand);
        values.put(COL_4, bname);
        values.put(COL_5, bemail);
        values.put(COL_6, duedate);
        long result = db.insert(tablename, null, values);
        if (result == -1) {
            Log.v("tag", "Returned false");
            return false;

        } else {
            Log.v("tag", "Returned true");
            return true;
        }
    }

    public Integer deleteData(String tablename, String personname, String itemname) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + tablename + "'");
        return db.delete(tablename, "BNAME =? AND ITEMNAME =?", new String[]{personname, itemname});
    }

    public boolean updateData(String tablename, String bname, String itemname, String bemail, String duedate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        /*values.put(COL_2, itemname);
        values.put(COL_3, itembrand);
        values.put(COL_4, bname);*/
        values.put(COL_5, bemail);
        values.put(COL_6, duedate);
        db.update(tablename, values, "BNAME = ? AND ITEMNAME =?", new String[]{bname, itemname});
        return true;
    }


    public Cursor getData(int iscomplete) {

        SQLiteDatabase db = this.getWritableDatabase();
        int iscompleted = iscomplete;
        String query = "select * from " + TABLE_NAME + " WHERE COMPLETED =" + iscompleted + ";";
        Log.v("tag", "Query is:" + query);
        Cursor data = db.rawQuery(query, null);
        if (data != null) {
            data.moveToFirst();
        }
        return data;
    }

    public void createAllItemsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("create table IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ITEMNAME TEXT," +
                "ITEMBRAND TEXT,BNAME TEXT,BEMAIL TEXT,DUEDATE TEXT,COMPLETED BOOLEAN)");
        // db.execSQL("create table IF NOT EXISTS "+TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,TRIPNAME TEXT,DAYNUM INTEGER)");
    }

    public int deletefromAllItemsTable(String bname, String itemname) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = 'allitemstable'");
        return db.delete("allitemstable", "BNAME = ? AND ITEMNAME =?", new String[]{bname, itemname});
    }

    public void dropAllItemsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("drop table if exists " + TABLE_NAME);
    }

    public boolean insertDataintoAllItemsTable(String itemname, String itembrand, String bname, String bemail, String duedate, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, itemname);
        values.put(COL_3, itembrand);
        values.put(COL_4, bname);
        values.put(COL_5, bemail);
        values.put(COL_6, duedate);
        values.put(COL_7, completed);
        long result = db.insert(TABLE_NAME, null, values);
        if (result == -1) {
            Log.v("tag", "Returned false");
            return false;

        } else {
            Log.v("tag", "Returned true");
            return true;
        }
    }

    public boolean updateDataofAllItemsTable(String bname, String itemname, String bemail, String duedate, boolean completed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        /*values.put(COL_2, itemname);
        values.put(COL_3, itembrand);
        values.put(COL_4, bname);*/
        values.put(COL_5, bemail);
        values.put(COL_6, duedate);
        values.put(COL_7, completed);
        db.update(TABLE_NAME, values, "BNAME = ? AND ITEMNAME =?", new String[]{bname, itemname});
        return true;
    }

    public Cursor getAllDataformAllItemsTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME + ";", null);
        Log.v("tag", "Query is:" + "select * from " + TABLE_NAME + ";");
        return res;
    }
}
