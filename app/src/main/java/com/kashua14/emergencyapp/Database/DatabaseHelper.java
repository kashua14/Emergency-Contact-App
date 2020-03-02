package com.kashua14.emergencyapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.kashua14.emergencyapp.Adaptors.ListData;
import com.kashua14.emergencyapp.Database.ContactReaderContract.FeedEntry;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_NAME + " TEXT," +
                    FeedEntry.COLUMN_NAME_PHONE+ " TEXT," +
                    FeedEntry.COLUMN_NAME_RELATIONSHIP+ " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

    private static final String DATABASE_NAME = "ContactsActivity.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    //    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(SQL_CREATE_ENTRIES);
//    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean addTupple(String name, String phone, String relationship) {

        // Gets the data repository in write mode
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_NAME, name);
        values.put(FeedEntry.COLUMN_NAME_PHONE, phone);
        values.put(FeedEntry.COLUMN_NAME_RELATIONSHIP, relationship);

        // Insert the new row, returning the primary key value of the new row
        sqLiteDatabase.insert(FeedEntry.TABLE_NAME, null, values);
        return true;
    }

    public List<ListData> getAllContacts() {
        // Gets the data repository in write mode
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        List<ListData> contacts = new ArrayList<>();

        String[] projection = {
                BaseColumns._ID,
                ContactReaderContract.FeedEntry.COLUMN_NAME_NAME,
                ContactReaderContract.FeedEntry.COLUMN_NAME_PHONE,
                ContactReaderContract.FeedEntry.COLUMN_NAME_RELATIONSHIP
        };

//        Cursor cursor = db.rawQuery("select * from "+ ContactReaderContract.FeedEntry.TABLE_NAME, null);
//        String sortOrder =
//                ContactReaderContract.FeedEntry.COLUMN_NAME_NAME + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                ContactReaderContract.FeedEntry.TABLE_NAME, projection,
                null, null, null, null, null);

        String name, phone, relationship;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            name = cursor.getString(cursor.getColumnIndex(ContactReaderContract.FeedEntry.COLUMN_NAME_NAME));
            phone = cursor.getString(cursor.getColumnIndex(ContactReaderContract.FeedEntry.COLUMN_NAME_PHONE));
            relationship = cursor.getString(cursor.getColumnIndex(ContactReaderContract.FeedEntry.COLUMN_NAME_RELATIONSHIP));

            ListData listData = new ListData(name, phone, relationship);
            contacts.add(listData);
            cursor.moveToNext();
        }
        cursor.close();

        return contacts;
    }
}
