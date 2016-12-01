package org.cuatrovientos.sqlite.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Mikel on 1/12/16.
 */

public class SqLiteHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "contactlist.db";
    public static final int DB_VERSION = 1;
    public static final String CREATE_CONTACT_TABLE = "CREATE TABLE contacts " +
            " (_id INTEGER primary key autoincrement, " +
            "name TEXT, " +
            "phone TEXT, " +
            "mark INTEGER NOT NULL" +
            ");";

    public SqLiteHelper(Context contexto) {
        super(contexto, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        db.execSQL(CREATE_CONTACT_TABLE);
        Log.d("DEBUG", "Ok, database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SqLiteHelper", "Updating from version " + oldVersion
                + " to " + newVersion + ", data will be deleted.");

        db.execSQL("DROP TABLE IF EXISTS contacts");

        onCreate(db);
        Log.d("DEBUG", "Ok, database upgraded");
    }
}
