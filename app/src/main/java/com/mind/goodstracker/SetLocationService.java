package com.mind.goodstracker;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class SetLocationService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
//        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
//        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startid) {
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
//        new Thread(){
//            public void run(){
//                while(true){
//                    try{
//
//                        Toast.makeText(SetLocationService.this, "1poewhfbpiyduiufbo", Toast.LENGTH_SHORT).show();
//                    }
//                    catch (Exception e){
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();

        // And From your main() method or any other method
        Timer timer = new Timer();
        timer.schedule(new SayHello(), 0, 5000);
    }

    class SayHello extends TimerTask {
        public void run() {

        }
    }
}
