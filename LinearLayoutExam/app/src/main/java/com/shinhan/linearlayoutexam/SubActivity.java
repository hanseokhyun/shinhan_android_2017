package com.shinhan.linearlayoutexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
    }
    public void onButtonClicked(View view) {
        ImageView imageView1 = (ImageView) findViewById(R.id.imageview1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageview2);
        Button button = (Button) view;
        if (button.getText().toString().equals("BUTTON1")) { //첫번재버튼클릭시
            imageView1.setBackgroundResource(R.drawable.s0001);
            imageView2.setBackgroundResource(R.drawable.s0003);
        } else { //두번째클릭시
            imageView1.setBackgroundResource(R.drawable.s0003);
            imageView2.setBackgroundResource(R.drawable.s0002);
        }
    }

}
