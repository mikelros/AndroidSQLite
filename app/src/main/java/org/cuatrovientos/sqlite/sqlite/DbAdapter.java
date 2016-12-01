package org.cuatrovientos.sqlite.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Mikel on 1/12/16.
 */

public class DbAdapter {
    private SQLiteDatabase db;
    private SqLiteHelper dbHelper;
    private final Context context;

    public DbAdapter(Context context) {
        this.context = context;
    }

    public SQLiteDatabase open() throws SQLException {
        dbHelper = new SqLiteHelper(context);

        db = dbHelper.getWritableDatabase();

        Log.d("DEBUG", "got database: " + db.toString());

        return db;
    }

    public void close() {
        dbHelper.close();
    }

    public long insertContact(String name, String phone, String mark) {
        ContentValues register = new ContentValues();

        register.put("name", name);
        register.put("phone", phone);
        register.put("mark", mark);

        return db.insert("contacts", null, register);
    }

    public int deleteContact(long idRegister) {
        return db.delete("contacts", "_id = "
                + idRegister, null);
    }

    public Cursor getContacts() {
        return db.query("contacts", new String[]{"_id", "name", "phone", "mark"}, null, null, null, null, null);
    }

    public Cursor getContact(long idRegister) throws SQLException {
        Cursor register = db.query(true, "contacts", new String[]{"_id", "name", "phone", "mark"},
                "_id =" + idRegister, null, null, null, null, null);

        if (register != null) {
            register.moveToFirst();
        }
        return register;
    }

    public int updateContact(long idRegister, String name, String phone, Integer mark) {
        ContentValues register = new ContentValues();

        register.put("name", name);
        register.put("phone", phone);
        register.put("mark", mark);

        return db.update("contacts",register,
                "_id=" + idRegister, null);
    }
}
