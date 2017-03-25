package com.shinhan.jtonenote;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    int[] ids;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        readDatabase();
    }
    public void onMemoButton1Clicked(View view) {
        Intent intent = new Intent(MainActivity.this, MemoActivity.class);
        startActivityForResult(intent, 1);
    }
    public void onCloseButton2Clicked(View view) {
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }
    private void readDatabase() {
        Dictionary dictionary = new Dictionary(MainActivity.this);
        SQLiteDatabase database = dictionary.getReadableDatabase();
        Cursor cursor =
                database.rawQuery("select * from "+Dictionary.TABLE_NAME+" order by _id desc " , null);
        Log.i("qwert count", cursor.getCount()+"");
        String[] words = new String[cursor.getCount()];
        ids = new int[cursor.getCount()];
        for (int i = 0; i < cursor.getCount(); i++ ) {
            cursor.moveToNext();
            ids[i] = cursor.getInt(0);
            words[i] = cursor.getString(1)+"\n("+cursor.getString(2)+")";
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, words);
        ListView listview = (ListView)findViewById(R.id.listview);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                intent.putExtra("id", ids[position]);
                startActivityForResult(intent, 2);
                Log.i("zzzzz ids",ids[position]+"");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("aaaaaaaaaaaaaaaaaa onActivityResult",requestCode+"");
        if ((requestCode == 1 || requestCode == 2)  && resultCode == RESULT_OK) {
            Log.i("onActivityResult",resultCode+"");
            readDatabase();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
