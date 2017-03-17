package com.shinhan.serviceexam;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

public class MyService extends Service {
    private  static final  String TAG =  "MyService";
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate()--------------!!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartComend()--------------!!");
        if (intent == null) { /// 인텐트작가 넬인 경우 자동재시작
            return Service.START_STICKY;

        } else { // 인텐트가 정상인 경우
            String command = intent.getStringExtra("command");
            String name = intent.getStringExtra("name");
            Log.d(TAG, "command:"+command+"name:"+name);
            ////////////////////////////////////////////////
            Intent showIntent = new Intent(getApplicationContext(), MainActivity.class);
            showIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                 Intent.FLAG_ACTIVITY_SINGLE_TOP |
                                 Intent.FLAG_ACTIVITY_CLEAR_TOP);
            showIntent.putExtra("command", command);
            showIntent.putExtra("name", name);
            startActivity(showIntent);

        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
