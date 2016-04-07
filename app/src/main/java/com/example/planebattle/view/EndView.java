package com.example.planebattle.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.planebattle.R;
import com.example.planebattle.constant.ConstantUtil;
import com.example.planebattle.planebattle.MainActivity;

/**
 * Created by RuiGeng on 3/25/2016.
 */
public class EndView extends BaseView {
    private int score;
    private float buttonMainX;
    private float buttonMainY;
    private float buttonEndY;
    private float textWidth;
    private float textHeight;
    private boolean isMainButtonPressed;
    private boolean isEndButtonPressed;
    private String startGameText = "Start Game";
    private String exitGameText = "Exit Game";
    private Bitmap buttonBitmap1;
    private Bitmap buttonBitmap2;
    private Bitmap backgroundBitmap;
    private Rect rect;
    private MainActivity mainActivity;

    public EndView(Context context) {
        super(context);

        this.mainActivity = (MainActivity) context;
        rect = new Rect();
        thread = new Thread(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.surfaceChanged(holder, format, width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);

        initBitmap();
        if (thread.isAlive()) {
            thread.start();
        } else {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        super.surfaceDestroyed(holder);
        release();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1) {
            float x = event.getX();
            float y = event.getY();

            if (x > buttonMainX && x < buttonMainX + buttonBitmap1.getWidth()
                    && y > buttonMainY && y < buttonMainY + buttonBitmap1.getHeight()) {
                isMainButtonPressed = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
            } else if (x > buttonMainX && x < buttonMainX + buttonBitmap1.getWidth()
                    && y > buttonEndY && y < buttonEndY + buttonBitmap1.getHeight()) {
                isEndButtonPressed = true;
                drawSelf();
                threadFlag = false;
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float x = event.getX();
            float y = event.getY();
            isMainButtonPressed = x > buttonMainX && x < buttonMainX + buttonBitmap1.getWidth()
                    && y > buttonMainY && y < buttonMainY + buttonBitmap1.getHeight();
            isEndButtonPressed = x > buttonMainX && x < buttonMainX + buttonBitmap1.getWidth()
                    && y > buttonEndY && y < buttonEndY + buttonBitmap1.getHeight();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isMainButtonPressed = false;
            isEndButtonPressed = false;
            return true;
        }
        return false;
    }

    @Override
    public void initBitmap() {
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
        buttonBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.button);
        buttonBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.button2);
        scaleX = screenWidth / backgroundBitmap.getWidth();
        scaleY = screenHeight / backgroundBitmap.getHeight();
        buttonMainX = screenWidth / 2 - buttonBitmap1.getWidth() / 2;
        buttonMainY = screenHeight / 2 + buttonBitmap1.getHeight();
        buttonEndY = buttonMainY + buttonBitmap1.getHeight() + 40;

        paint.setTextSize(40);
        paint.getTextBounds(startGameText, 0, startGameText.length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();
    }

    @Override
    public void release() {

        if (!buttonBitmap1.isRecycled()) {
            buttonBitmap1.recycle();
        }
        if (!buttonBitmap2.isRecycled()) {
            buttonBitmap2.recycle();
        }
        if (!backgroundBitmap.isRecycled()) {
            backgroundBitmap.recycle();
        }
    }

    @Override
    public void drawSelf() {
        try {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.save();
            canvas.scale(scaleX, scaleY, 0, 0);
            canvas.drawBitmap(backgroundBitmap, 0, 0, paint);
            canvas.restore();

            if (isMainButtonPressed) {
                canvas.drawBitmap(buttonBitmap2, buttonMainX, buttonMainY, paint);
            } else {
                canvas.drawBitmap(buttonBitmap1, buttonMainX, buttonMainY, paint);
            }
            if (isEndButtonPressed) {
                canvas.drawBitmap(buttonBitmap2, buttonMainX, buttonEndY, paint);
            } else {
                canvas.drawBitmap(buttonBitmap1, buttonMainX, buttonEndY, paint);
            }
            paint.setTextSize(40);

            paint.getTextBounds(startGameText, 0, startGameText.length(), rect);
            canvas.drawText(startGameText, screenWidth / 2 - textWidth / 2, buttonMainY + buttonBitmap1.getHeight() / 2 + textHeight / 2, paint);
            canvas.drawText(exitGameText, screenWidth / 2 - textWidth / 2, buttonEndY + buttonBitmap1.getHeight() / 2 + textHeight / 2, paint);
            paint.setTextSize(60);
            float scoreTextSize;
            scoreTextSize = paint.measureText("Score:" + String.valueOf(score));
            canvas.drawText("Score:" + String.valueOf(score), screenWidth / 2 - scoreTextSize / 2, screenHeight / 2 - 100, paint);
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public void run() {
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            drawSelf();
            long endTime = System.currentTimeMillis();
            try {
                if (endTime - startTime < 400)
                    Thread.sleep(400 - (endTime - startTime));
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        }
    }

    public void setScore(int score) {
        this.score = score;
    }
}
