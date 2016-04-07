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

/**
 * Created by RuiGeng on 3/25/2016.
 */
public class ReadyView extends BaseView {
    private float startButtonX;
    private float startButtonY;
    private float exitButtonY;
    private float textX;
    private float textY;
    private float textWidth;
    private float textHeight;
    private boolean isStartButtonPressed;
    private boolean isExitButtonPressed;
    private String startGame = "Start Game";
    private String exitGame = "Exit Game";
    private Bitmap buttonBitmap1;
    private Bitmap buttonBitmap2;
    private Bitmap backgroundBitmap;
    private Bitmap titleText;
    private Rect rect;

    public ReadyView(Context context) {
        super(context);

        paint.setTextSize(40);
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
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && event.getPointerCount() == 1) {
            float touchX = event.getX();
            float touchY = event.getY();

            if (touchX > startButtonX && touchX < startButtonX + buttonBitmap1.getWidth()
                    && touchY > startButtonY && touchY < startButtonY + buttonBitmap1.getHeight()) {
                isStartButtonPressed = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
            } else if (touchX > startButtonX && touchX < startButtonX + buttonBitmap1.getWidth()
                    && touchY > exitButtonY && touchY < exitButtonY + buttonBitmap1.getHeight()) {
                isExitButtonPressed = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
            }
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float touchX = event.getX();
            float touchY = event.getY();
            isStartButtonPressed = touchX > startButtonX && touchX < startButtonX + buttonBitmap1.getWidth()
                    && touchY > startButtonY && touchY < startButtonY + buttonBitmap1.getHeight();
            isExitButtonPressed = touchX > startButtonX && touchX < startButtonX + buttonBitmap1.getWidth()
                    && touchY > exitButtonY && touchY < exitButtonY + buttonBitmap1.getHeight();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            isStartButtonPressed = false;
            isExitButtonPressed = false;
            return true;
        }
        return false;
    }

    @Override
    public void initBitmap() {
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
        buttonBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.button);
        buttonBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.button2);
        titleText = BitmapFactory.decodeResource(getResources(), R.drawable.text);
        scaleX = screenWidth / backgroundBitmap.getWidth();
        scaleY = screenHeight / backgroundBitmap.getHeight();
        startButtonX = screenWidth / 2 - buttonBitmap1.getWidth() / 2;
        startButtonY = screenHeight / 2 + buttonBitmap1.getHeight();
        exitButtonY = startButtonY + buttonBitmap1.getHeight() + 40;
        textX = screenWidth / 2 - titleText.getWidth() / 2;
        textY = screenHeight / 2 - titleText.getHeight();

        paint.getTextBounds(startGame, 0, startGame.length(), rect);
        textWidth = rect.width();
        textHeight = rect.height();
    }

    @Override
    public void release() {
        if (!titleText.isRecycled()) {
            titleText.recycle();
        }
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
            canvas.drawBitmap(titleText, textX, textY, paint);

            if (isStartButtonPressed) {
                canvas.drawBitmap(buttonBitmap2, startButtonX, startButtonY, paint);
            } else {
                canvas.drawBitmap(buttonBitmap1, startButtonX, startButtonY, paint);
            }
            if (isExitButtonPressed) {
                canvas.drawBitmap(buttonBitmap2, startButtonX, exitButtonY, paint);
            } else {
                canvas.drawBitmap(buttonBitmap1, startButtonX, exitButtonY, paint);
            }

            canvas.drawText(startGame, screenWidth / 2 - textWidth / 2, startButtonY
                    + buttonBitmap1.getHeight() / 2 + textHeight / 2, paint);

            canvas.drawText(exitGame, screenWidth / 2 - textWidth / 2, exitButtonY
                    + buttonBitmap1.getHeight() / 2 + textHeight / 2, paint);

            canvas.save();
            currentView++;
            if (currentView >= 3) {
                currentView = 0;
            }
            canvas.restore();
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
}

