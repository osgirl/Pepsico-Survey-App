package limited.it.planet.visicoolertracking.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by User on 1/29/2018.
 */

public class LoginDB {
    //to login table
    public static final String KEY_ROWID = "id";
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_PASSWORD = "password";
    //to register table

    public static final String KEY_FULL_NAME = "full_name";
    public static final String KEY_ADDRESS = "address";


    // db version
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "planetit";
    private static final String DATABASE_TABLE_LOGIN = "table_login";
    private static final String DATABASE_TABLE_REGISTER = "table_register";

    private DBHelper dbhelper;
    private final Context context;
    private SQLiteDatabase database;

    private static class DBHelper extends SQLiteOpenHelper {

        @SuppressLint("NewApi")
        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // create table to store msgs
            db.execSQL("CREATE TABLE " + DATABASE_TABLE_LOGIN + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_USER_NAME + " TEXT, "
                    + KEY_PASSWORD + " TEXT );");

            db.execSQL("CREATE TABLE " + DATABASE_TABLE_REGISTER + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_FULL_NAME + " TEXT, "
                    + KEY_ADDRESS + " TEXT, "
                    + KEY_USER_NAME + " TEXT, "
                    + KEY_PASSWORD + " TEXT );");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LOGIN);
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_REGISTER);

            onCreate(db);
        }

    }
    // constructor
    public LoginDB(Context c) {
        context = c;
    }

    // open db
    public LoginDB open() {
        dbhelper = new DBHelper(context);
        database = dbhelper.getWritableDatabase();
        return this;
    }

    // close db
    public void close() {
        dbhelper.close();
    }

    // insert login information
    public long saveLoginEntry(String userName,String password){
        ContentValues cv = new ContentValues();

        cv.put(KEY_USER_NAME, userName);
        cv.put(KEY_PASSWORD,  password);

        return database.insert(DATABASE_TABLE_LOGIN, null, cv);
    }

    // insert register information
    public long saveRegisterEntry(String fullName,String address,String userName,String password){
        ContentValues cv = new ContentValues();
        cv.put(KEY_FULL_NAME, fullName);
        cv.put(KEY_ADDRESS, address);
        cv.put(KEY_USER_NAME, userName);
        cv.put(KEY_PASSWORD,  password);
        long dbInsert = database.insert(DATABASE_TABLE_REGISTER, null, cv);

        if(dbInsert != -1) {
            Toast.makeText(context, "New row added in local storage, row id: " + dbInsert, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
        }

        return dbInsert;
    }

    public boolean checkUser(String username, String passWord) throws SQLException
    {
        Cursor cursor = database.query(DATABASE_TABLE_LOGIN, null, " user_name=? and password=?",
                new String[] { username,passWord }, null, null, null);

        if (cursor != null) {
            if(cursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public void getAllData(){
        Cursor  cursor = database.rawQuery("select * from table_login",null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String name = cursor.getString(cursor.getColumnIndex("user_name"));


                cursor.moveToNext();
            }
        }
    }
}
