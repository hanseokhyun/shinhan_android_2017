package com.shinhan.jtonenote;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
    public void onCloseButton1Clicked(View view) {
        Toast.makeText(SettingActivity.this, "[로그인] 기능 설계 중 입니다. \n버젼 2.0 에서 더욱 좋은 모습 \n보여 드리겠습니다. ", Toast.LENGTH_SHORT).show();
    }
    public void onCloseButton2Clicked(View view) {
        Toast.makeText(SettingActivity.this, "[계정] 기능 개발 중 입니다. \n버젼 2.0 에서 더욱 좋은 모습 \n보여 드리겠습니다. ", Toast.LENGTH_SHORT).show();
    }
    public void onCloseButton3Clicked(View view) {
        Toast.makeText(SettingActivity.this, "[도움말및지원] 기능 구현 중 입니다. \n버젼 2.0 에서 더욱 좋은 모습 \n보여 드리겠습니다. ", Toast.LENGTH_SHORT).show();
    }
    public void onCloseButton4Clicked(View view) {
        Toast.makeText(SettingActivity.this, "짝퉁OneNote 버젼 1.0 ... \n 최신 버젼을 사용하고 있습니다.", Toast.LENGTH_SHORT).show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 3000);
    }


}
