package com.example.planebattle.planebattle;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

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
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        readyView = new ReadyView(this);
        setContentView(readyView);
    }

    public void toMainView() {
    }

    public void toEndView(int score) {
        if (endView == null) {
            endView = new EndView(this);
            endView.setScore(score);
        }
        setContentView(endView);
        mainView = null;
    }

    public void endGame() {
        if (readyView != null) {
            readyView.setThreadFlag(false);
        } else if (mainView != null) {
            mainView.setThreadFlag(false);
        } else if (endView != null) {
            endView.setThreadFlag(false);
        }
        this.finish();
    }


    public Handler getHandler() {
        return handler;
    }
}