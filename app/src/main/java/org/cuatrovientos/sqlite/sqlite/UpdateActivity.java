package org.cuatrovientos.sqlite.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    private String[] values = {"","","",""};
    private EditText editTextName;
    private EditText editTextPhone;
    private EditText editTextMark;
    private DbAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        db = new DbAdapter(this);
        db.open();
        Long id = getIntent().getLongExtra("id", -1L);
        values[0] = String.valueOf(id);
        Cursor cursor = db.getContact(id);
        try {
            values[1] = cursor.getString(cursor.getColumnIndex("name"));
            values[2] = cursor.getString(cursor.getColumnIndex("phone"));
            values[3] = cursor.getString(cursor.getColumnIndex("mark"));
        } finally {
            cursor.close();
        }

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextMark = (EditText) findViewById(R.id.editTextMark);

        editTextName.setText(values[1]);
        editTextPhone.setText(values[2]);
        editTextMark.setText(values[3]);
    }

    public void updateContact(View v) {
        db.open();
        db.updateContact(Long.valueOf(values[0]), editTextName.getText().toString(), editTextPhone.getText().toString(), Integer.valueOf(editTextMark.getText().toString()));
        Toast.makeText(getApplicationContext(), "Contact updated: " + values[0], Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
