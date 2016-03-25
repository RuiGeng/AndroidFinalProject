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
public class EndView extends BaseView{
    private int score;
    private float button_x;
    private float button_y;
    private float button_y2;
    private float strwid;
    private float strhei;
    private boolean isBtChange;
    private boolean isBtChange2;
    private String startGame = "Start Game";
    private String exitGame = "Exit Game";
    private Bitmap button;
    private Bitmap button2;
    private Bitmap background;
    private Rect rect;
    private MainActivity mainActivity;

    public EndView(Context context) {
        super(context);

        this.mainActivity = (MainActivity)context;
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
        if(thread.isAlive()){
            thread.start();
        }
        else{
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
        if(event.getAction() == MotionEvent.ACTION_DOWN && event.getPointerCount() == 1){
            float x = event.getX();
            float y = event.getY();

            if(x > button_x && x < button_x + button.getWidth()
                    && y > button_y && y < button_y + button.getHeight())
            {
                isBtChange = true;
                drawSelf();
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
            }

            else if(x > button_x && x < button_x + button.getWidth()
                    && y > button_y2 && y < button_y2 + button.getHeight())
            {
                isBtChange2 = true;
                drawSelf();
                threadFlag = false;
                mainActivity.getHandler().sendEmptyMessage(ConstantUtil.END_GAME);
            }
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_MOVE){
            float x = event.getX();
            float y = event.getY();
            if(x > button_x && x < button_x + button.getWidth()
                    && y > button_y && y < button_y + button.getHeight())
            {
                isBtChange = true;
            }
            else{
                isBtChange = false;
            }
            if(x > button_x && x < button_x + button.getWidth()
                    && y > button_y2 && y < button_y2 + button.getHeight())
            {
                isBtChange2 = true;
            }
            else{
                isBtChange2 = false;
            }
            return true;
        }
        else if(event.getAction() == MotionEvent.ACTION_UP){
            isBtChange = false;
            isBtChange2 = false;
            return true;
        }
        return false;
    }

    @Override
    public void initBitmap() {
        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
        button = BitmapFactory.decodeResource(getResources(), R.drawable.button);
        button2 = BitmapFactory.decodeResource(getResources(),R.drawable.button2);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        button_x = screen_width / 2 - button.getWidth() / 2;
        button_y = screen_height / 2 + button.getHeight();
        button_y2 = button_y + button.getHeight() + 40;

        paint.setTextSize(40);
        paint.getTextBounds(startGame, 0, startGame.length(), rect);
        strwid = rect.width();
        strhei = rect.height();
    }

    @Override
    public void release() {

        if (!button.isRecycled()) {
            button.recycle();
        }
        if (!button2.isRecycled()) {
            button2.recycle();
        }
        if (!background.isRecycled()) {
            background.recycle();
        }
    }

    @Override
    public void drawSelf() {
        // TODO Auto-generated method stub
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.save();
            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(background, 0, 0, paint);
            canvas.restore();

            if (isBtChange) {
                canvas.drawBitmap(button2, button_x, button_y, paint);
            }
            else {
                canvas.drawBitmap(button, button_x, button_y, paint);
            }
            if (isBtChange2) {
                canvas.drawBitmap(button2, button_x, button_y2, paint);
            }
            else {
                canvas.drawBitmap(button, button_x, button_y2, paint);
            }
            paint.setTextSize(40);

            paint.getTextBounds(startGame, 0, startGame.length(), rect);
            canvas.drawText(startGame, screen_width/2 - strwid/2, button_y + button.getHeight()/2 + strhei/2, paint);
            canvas.drawText(exitGame, screen_width/2 - strwid/2, button_y2 + button.getHeight()/2 + strhei/2, paint);
            paint.setTextSize(60);
            float textlong = paint.measureText("Score:"+String.valueOf(score));
            canvas.drawText("Score:"+String.valueOf(score), screen_width/2 - textlong/2, screen_height/2 - 100, paint);
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
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