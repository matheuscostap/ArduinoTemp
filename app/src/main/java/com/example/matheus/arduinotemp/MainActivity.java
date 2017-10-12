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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String LTAG = getClass().getSimpleName();
    private TextView tvTemperatura;
    private Thread refreshThread;
    private int temp;
    private RelativeLayout background;
    private ImageButton btnSettings;
    private ImageButton btnPause;
    private boolean flagThread;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        tvTemperatura = (TextView) findViewById(R.id.tvTemperatura);
        background = (RelativeLayout) findViewById(R.id.relativeBackground);
        (btnPause = (ImageButton) findViewById(R.id.btnPause)).setOnClickListener(this);
        (btnSettings = (ImageButton) findViewById(R.id.btnSettings)).setOnClickListener(this);
    }



    @Override
    protected void onStart() {
        super.onStart();
        //startThread();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnPause:
                if (flagThread){ //Clicando para pausar
                    btnPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow_black_24dp));
                    stopThread();
                }else{ //Clicando para iniciar
                    btnPause.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_black_24dp));
                    startThread();
                }
                break;

            case R.id.btnSettings:
                CustomDialog.getInstance().showInputDialog(MainActivity.this,getString(R.string.enter_ip_address));
                break;

        }
    }



    private void startThread(){
        flagThread = true;
        refreshThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i(LTAG,"startThread() THREAD INICIADA");
                while (flagThread){
                    TempService.getInstance().requisicaoTemperature(MainActivity.this,Preferences.getInstance().retrieve(MainActivity.this));
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



    private void stopThread(){
        flagThread = false;
        Log.i(LTAG,"stopThread() THREAD PAUSADA");
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
