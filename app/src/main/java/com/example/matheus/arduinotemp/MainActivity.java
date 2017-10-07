package com.example.matheus.arduinotemp;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvTemperatura;
    private Thread refreshThread;
    private int temp;
    private RelativeLayout background;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        tvTemperatura = (TextView) findViewById(R.id.tvTemperatura);
        background = (RelativeLayout) findViewById(R.id.relativeBackground);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final boolean flagThread = true;


        refreshThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (flagThread){
                    TempService.getInstance().requisicaoTemperature(MainActivity.this,"http://192.168.1.12/");
                    try {Thread.sleep(2000);} catch (Throwable t) {t.printStackTrace();}
                    temp = (int) TempService.getInstance().getTemperature();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tvTemperatura.setText(String.valueOf(temp)+"Cº");
                            updateBackgroundColor();
                        }
                    });
                }
            }
        });
        refreshThread.start();
    }



    private void updateBackgroundColor(){
        if (temp >= 45){
            background.setBackgroundColor(getResources().getColor(R.color.deepOrange700));
        }else if (temp >= 40){
            background.setBackgroundColor(getResources().getColor(R.color.deepOrange500));
        }else if (temp >= 35){
            background.setBackgroundColor(getResources().getColor(R.color.deepOrange300));
        }else if (temp >= 30){
            background.setBackgroundColor(getResources().getColor(R.color.amber700));
        }else if (temp >= 25){
            background.setBackgroundColor(getResources().getColor(R.color.amber500));
        }else if (temp >= 20){
            background.setBackgroundColor(getResources().getColor(R.color.amber300));
        }else if (temp >= 15){
            background.setBackgroundColor(getResources().getColor(R.color.amber100));
        }else if (temp >= 10){
            background.setBackgroundColor(getResources().getColor(R.color.lightBlue100));
        }else if (temp >= 5){
            background.setBackgroundColor(getResources().getColor(R.color.lightBlue300));
        }else if (temp >= 0){
            background.setBackgroundColor(getResources().getColor(R.color.lightBlue500));
        }else if (temp >= -5){
            background.setBackgroundColor(getResources().getColor(R.color.lightBlue700));
        }else{
            background.setBackgroundColor(getResources().getColor(R.color.blue800));
        }
/*
        switch (temp){
            case -10: background.setBackgroundColor(getResources().getColor(R.color.blue800));break;
            case -5: background.setBackgroundColor(getResources().getColor(R.color.lightBlue700));break;
            case 0: background.setBackgroundColor(getResources().getColor(R.color.lightBlue500));break;
            case 5: background.setBackgroundColor(getResources().getColor(R.color.lightBlue300));break;
            case 10: background.setBackgroundColor(getResources().getColor(R.color.lightBlue100));break;
            case 15: background.setBackgroundColor(getResources().getColor(R.color.amber100));break;
            case 20: background.setBackgroundColor(getResources().getColor(R.color.amber300));break;
            case 25: background.setBackgroundColor(getResources().getColor(R.color.amber500));break;
            case 30: background.setBackgroundColor(getResources().getColor(R.color.amber700));break;
            case 35: background.setBackgroundColor(getResources().getColor(R.color.deepOrange300));break;
            case 40: background.setBackgroundColor(getResources().getColor(R.color.deepOrange500));break;
            case 45: background.setBackgroundColor(getResources().getColor(R.color.deepOrange700));break;
        }*/
    }
}
