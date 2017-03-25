package com.shinhan.dictionaryexam;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readDatabase();
    }

    public void onButtonClicked(View view) {
        EditText word = (EditText)findViewById(R.id.word);
        EditText definition = (EditText)findViewById(R.id.definition);
        String wordString = word.getText().toString();
        String definitionString = definition.getText().toString();
        if (!wordString.isEmpty() && !definitionString.isEmpty()) {
            writeDatabase(wordString, definitionString );
            readDatabase();
        }
    }

    private void writeDatabase(String word, String definition) {
        Dictionary dictionary = new Dictionary(MainActivity.this);
        SQLiteDatabase database = dictionary.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", word);
        values.put("definition", definition);
        database.insert(Dictionary.TABLE_NAME,null,values);
    }
    private void readDatabase() {
        Dictionary dictionary = new Dictionary(MainActivity.this);
        SQLiteDatabase database = dictionary.getReadableDatabase();
        Cursor cursor =
                database.rawQuery("select * from "+Dictionary.TABLE_NAME, null);
        Log.i("count", cursor.getCount()+"");
        String[] words = new String[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++ ) {
            cursor.moveToNext();
            words[i] = cursor.getString(1)+" ("+cursor.getString(2)+")";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, words);
        ListView listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(adapter);
    }
}
