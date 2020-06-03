package com.ril.digitalwardrobeAI;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ril.digitalwardrobeAI.Model.WardrobeItems;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName.db";
    public static final String IMAGE_TABLE_NAME = "images";
    public static final String IMAGE_ID = "id";
    public static final String IMAGE_URL = "image_url";
    public static final String TYPE="type";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table images " +
                        "(id integer primary key, image_url text,type text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS images");
        onCreate(db);
    }
    public boolean insertImageUrl(String image_url,String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(IMAGE_URL, image_url);
        contentValues.put(TYPE,type);
        db.insert(IMAGE_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from images where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, IMAGE_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public void delete () {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ IMAGE_TABLE_NAME);

    }

    public ArrayList<WardrobeItems> getWardrobe() {
        ArrayList<WardrobeItems> array_list = new ArrayList<WardrobeItems>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from images", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(new WardrobeItems(res.getString(res.getColumnIndex(IMAGE_URL)),res.getString(res.getColumnIndex(TYPE))));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<String> getImages() {
        ArrayList<String> array_list = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select image_url from images", null );
        res.moveToFirst();
        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(IMAGE_URL)));
            res.moveToNext();
        }
        return array_list;
    }
    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }
}