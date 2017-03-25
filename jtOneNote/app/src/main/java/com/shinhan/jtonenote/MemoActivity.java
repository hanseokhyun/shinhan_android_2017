package com.shinhan.jtonenote;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MemoActivity extends AppCompatActivity {
    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        Log.i("id",id+"");
        if (id != -1) {
            readDatabase(id);
        }
    }
    public void onCloseButton1Clicked(View view) {
        EditText word = (EditText)findViewById(R.id.word);
        EditText definition = (EditText)findViewById(R.id.definition);
        String wordString = word.getText().toString();
        String definitionString = definition.getText().toString();
        if (!wordString.isEmpty() || !definitionString.isEmpty()) {
            writeDatabase(wordString, definitionString );
            setResult(RESULT_OK);
        }
        finish();
    }
    public void onDelButtonClicked(View view) {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);
        alt_bld.setMessage("진실로 진실로 삭제 하시겠습니까?").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        deleteDatabase();
                        finish();
                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        AlertDialog alert = alt_bld.create();
        alert.setTitle("주의");
        alert.show();
    }
    public void onCloseButton2Clicked(View view) {
        finish();
    }
    private void deleteDatabase() {
        Dictionary dictionary = new Dictionary(MemoActivity.this);
        SQLiteDatabase database = dictionary.getWritableDatabase();
        if (id == -1) {
        } else {
            database.delete(Dictionary.TABLE_NAME,"_id = " + id, null);
            setResult(RESULT_OK);
        }

    }
    private void writeDatabase(String word, String definition) {
        Dictionary dictionary = new Dictionary(MemoActivity.this);
        SQLiteDatabase database = dictionary.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", word);
        values.put("definition", definition);
        if (id == -1) {
            database.insert(Dictionary.TABLE_NAME,null,values);
        } else {
            database.update(Dictionary.TABLE_NAME,values, "_id = " + id, null);
        }

    }
    private void readDatabase(int id) {
        Dictionary dictionary = new Dictionary(MemoActivity.this);
        SQLiteDatabase database = dictionary.getReadableDatabase();
        Cursor cursor =
       //         database.rawQuery("select * from "+Dictionary.TABLE_NAME, null);
               database.rawQuery("select * from "+Dictionary.TABLE_NAME+" where _id = "+id, null);
        String[] words = new String[cursor.getCount()];
        cursor.moveToNext();
        EditText editTitle = (EditText)findViewById(R.id.word);
        editTitle.setText(cursor.getString(1));
        EditText editDefinition = (EditText)findViewById(R.id.definition);
        editDefinition.setText(cursor.getString(2));
    }

    public void onEditTitleClicked(View view) {
        EditText editTitle = (EditText)findViewById(R.id.word);

        if (editTitle.getText().toString().equals("제목")) {
            editTitle.setText("");
        }
    }

    public void onEditDefinitionClicked(View view) {
        EditText editDefinition = (EditText)findViewById(R.id.definition);
        if (editDefinition.getText().toString().equals("내용입력하세요")) {
            editDefinition.setText("");
        }
    }

}
