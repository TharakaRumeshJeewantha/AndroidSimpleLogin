package com.example.tharaka.simplelogin;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "test.db";
    private static final int DATABASE_VERSION = 1;
    private final Context context;
    SQLiteDatabase db;

    private static final String DATABASE_PATH = "/data/data/com.example.tharaka.simplelogin/databases/";

    /**
     * Database name Changed
     */
    private static final String USER_TABLE = "loginuser";

    /**
     * Create Query added
     */

    private static final String TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT, " +
                    "username TEXT, " +
                    "password TEX " + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        createDb();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void createDb() {
        boolean dbExist = checkDbExist();

        if (!dbExist) {
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private boolean checkDbExist() {
        SQLiteDatabase sqLiteDatabase = null;

        try {

            String path = DATABASE_PATH + DATABASE_NAME;
            sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

        } catch (Exception ex) {

        }

        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
            return true;
        }
        return false;
    }

    private void copyDatabase() {
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);

            String outFileName = DATABASE_PATH + DATABASE_NAME;

            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] b = new byte[1024];
            int length;

            while ((length = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private SQLiteDatabase openDatabase() {
        String path = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }


    /**
     * This Part is Added Newly
     *
     */


    public boolean checkUserExist(String username, String password) {
        boolean isTrue = false;
        if (!isTableExists(USER_TABLE)) {
            db = openDatabase();
            db.execSQL(TABLE_CREATE);

        }
        String[] columns = {"username"};
        db = openDatabase();
        String selection = "username = ? and password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        close();

        if (count > 0) {
            isTrue = true;
        } else {
            isTrue = false;
        }

        return isTrue;
    }


    /**
     * insert a new User when registering
     * @param name
     * @param username
     * @param password
     */
    public void insertNewUser(String name,String username, String password) {

        db = openDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("username", username);
        values.put("password", password);

        db.insert(USER_TABLE, null, values);
        db.close();

    }


    /**
     * search username is already taken before registering
     * @param username
     * @return
     */
    public boolean searchUser(String username)
    {
        if (!isTableExists(USER_TABLE)) {
            try{
                db = openDatabase();
                db.execSQL(TABLE_CREATE);
                db.close();
            }catch (Exception e){
                System.out.println("Exception : "+e.getMessage());
                e.printStackTrace();
            }


        }
        boolean isUserAvailable=true;
        db=openDatabase();
        String sql="select username,name from "+USER_TABLE;
        Cursor cursor=db.rawQuery(sql,null);
        String returnedUserName;

        if(cursor.moveToFirst()){
            do {
                returnedUserName=cursor.getString(cursor.getColumnIndex("username"));
                if (returnedUserName.equals(username)) {
                    isUserAvailable=false;
                    break;
                }
            }while (cursor.moveToNext());

        }
        cursor.close();
        db.close();
        return isUserAvailable;

    }

    /**
     * if table does not exist,Create
     * @param tableName
     * @return
     */

    public boolean isTableExists(String tableName) {
        boolean isTrue;
        db = openDatabase();
        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"
                + tableName + "'", null);
        ;
        int table = cursor.getCount();

        if (table > 0) {
            isTrue = true;
        } else {
            isTrue = false;
        }
        return isTrue;
    }

}

