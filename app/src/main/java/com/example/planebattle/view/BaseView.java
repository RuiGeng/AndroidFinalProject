package com.example.planebattle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.planebattle.planebattle.MainActivity;

/**
 * Created by RuiGeng 7128218 on 3/25/2016.
 */
public class BaseView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    protected int currentView;
    protected float scaleX;
    protected float scaleY;
    protected float screenWidth;
    protected float screenHeight;
    protected boolean threadFlag;
    protected Paint paint;
    protected Canvas canvas;
    protected Thread thread;
    protected SurfaceHolder surfaceHolder;
    protected MainActivity mainActivity;

    public BaseView(Context context) {
        super(context);
        this.mainActivity = (MainActivity) context;
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        paint = new Paint();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenWidth = this.getWidth();
        screenHeight = this.getHeight();
        threadFlag = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        threadFlag = false;
    }

    @Override
    public void run() {

    }

    public void initBitmap() {
    }

    public void release() {
    }

    public void drawSelf() {
    }

    public void setThreadFlag(boolean threadFlag) {
        this.threadFlag = threadFlag;
    }
}
