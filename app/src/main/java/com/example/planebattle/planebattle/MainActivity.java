package com.example.planebattle.planebattle;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;

import com.example.planebattle.R;
import com.example.planebattle.constant.ConstantUtil;
import com.example.planebattle.view.EndView;
import com.example.planebattle.view.MainView;
import com.example.planebattle.view.ReadyView;


import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity {
    private EndView endView;
    private MainView mainView;
    private ReadyView readyView;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ConstantUtil.TO_MAIN_VIEW) {
                toMainView();
            } else if (msg.what == ConstantUtil.TO_END_VIEW) {
                toEndView(msg.arg1);
            } else if (msg.what == ConstantUtil.END_GAME) {
                endGame();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toMainView(){
    }

    public void toEndView(int score){
    }

    public void endGame(){
    }


    public Handler getHandler() {
        return handler;
    }
}