package org.cuatrovientos.sqlite.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private DbAdapter db;
    private EditText newContact;
    private TextView textViewSelected;
    private ListView dataList;
    private Cursor cursor = null;
    private int selected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newContact = (EditText) findViewById(R.id.editTextNewContact);
        textViewSelected = (TextView) findViewById(R.id.textViewSelected);
        dataList = (ListView) findViewById(R.id.data_list);

        db = new DbAdapter(this);
        db.open();


        this.setupListView();
    }

    public void setupListView() {
        cursor = db.getContacts();

        startManagingCursor(cursor);

        String[] fields = new String[]{"_id", "name", "phone", "mark"};

        int[] whereToShow = new int[]{R.id.item_id, R.id.item_name, R.id.item_phone, R.id.item_mark};

        SimpleCursorAdapter contacts = new SimpleCursorAdapter(this, R.layout.item, cursor, fields, whereToShow);

        dataList.setAdapter(contacts);

        dataList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Cursor selectedElement = (Cursor) arg0.getItemAtPosition(arg2);

                selected = selectedElement.getInt(0);

                textViewSelected.setText("You've chosen: " + selected);

                Log.d("DEBUG", "Clicked element with id: " + selected);
            }
        });

        Log.d("DEBUG", "List filled by db");
    }

    public void insertContact(View v) {
        String text = newContact.getText().toString();
        String[] data = text.split(",");
        db.insertContact(data[0], data[1], data[2]);

        updateList();

        Toast.makeText(getApplicationContext(), "Inserted: " + text, Toast.LENGTH_SHORT).show();

        newContact.setText("");
    }

    public void deleteContact(View v) {

        if (selected == -1) {
            return;
        }

        db.deleteContact(selected);

        selected = -1;

        textViewSelected.setText("Element was deleted");

        updateList();

        Toast.makeText(getApplicationContext(), "Contact deleted: " + selected, Toast.LENGTH_SHORT).show();

    }

    public void updateContact(View v) {
        if (selected == -1) {
            return;
        }

        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
        intent.putExtra("id", Long.valueOf(selected));
        startActivity(intent);
        selected = -1;

    }

    private void updateList() {
        cursor.requery();
    }
}
