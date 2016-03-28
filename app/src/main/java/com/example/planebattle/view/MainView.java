package com.example.planebattle.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.os.Message;

import com.example.planebattle.R;
import com.example.planebattle.constant.ConstantUtil;
import com.example.planebattle.factory.GameObjectFactory;
import com.example.planebattle.object.EnemyPlane;
import com.example.planebattle.object.GameObject;
import com.example.planebattle.object.MyPlane;
import com.example.planebattle.object.SmallPlane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RuiGeng on 3/25/2016.
 */
public class MainView extends BaseView {
    private int bulletScore;
    private int sumScore;
    private int speedTime;
    private float bg_y;
    private float bg_y2;
    private float play_bt_w;
    private float play_bt_h;
    private boolean isTouchPlane;
    private Bitmap background;
    private Bitmap background2;
    private MyPlane myPlane;
    private List<EnemyPlane> enemyPlanes;
    private GameObjectFactory factory;

    public MainView(Context context) {
        super(context);
        speedTime = 1;
        factory = new GameObjectFactory();
        enemyPlanes = new ArrayList<EnemyPlane>();
        myPlane = (MyPlane) factory.createMyPlane(getResources());
        myPlane.setMainView(this);

        for (int i = 0; i < SmallPlane.sumCount; i++) {

            SmallPlane smallPlane = (SmallPlane) factory.createSmallPlane(getResources());
            enemyPlanes.add(smallPlane);

        }
        thread = new Thread(this);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        super.surfaceChanged(holder, format, width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        super.surfaceCreated(holder);

        super.surfaceCreated(holder);
        initBitmap();
        for (GameObject obj : enemyPlanes) {
            obj.setScreenWH(screen_width, screen_height);
        }
        myPlane.setScreenWH(screen_width, screen_height);
        myPlane.setAlive(true);
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
        if (event.getAction() == MotionEvent.ACTION_UP) {
            isTouchPlane = false;
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            if (x > myPlane.getObject_x() && x < myPlane.getObject_x() + myPlane.getObject_width()
                    && y > myPlane.getObject_y() && y < myPlane.getObject_y() + myPlane.getObject_height()) {
                isTouchPlane = true;

                return true;
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1) {

            if (isTouchPlane) {
                float x = event.getX();
                float y = event.getY();
                if (x > myPlane.getMiddle_x() + 20) {
                    if (myPlane.getMiddle_x() + myPlane.getSpeed() <= screen_width) {
                        myPlane.setMiddle_x(myPlane.getMiddle_x() + myPlane.getSpeed());
                    }
                } else if (x < myPlane.getMiddle_x() - 20) {
                    if (myPlane.getMiddle_x() - myPlane.getSpeed() >= 0) {
                        myPlane.setMiddle_x(myPlane.getMiddle_x() - myPlane.getSpeed());
                    }
                }
                if (y > myPlane.getMiddle_y() + 20) {
                    if (myPlane.getMiddle_y() + myPlane.getSpeed() <= screen_height) {
                        myPlane.setMiddle_y(myPlane.getMiddle_y() + myPlane.getSpeed());
                    }
                } else if (y < myPlane.getMiddle_y() - 20) {
                    if (myPlane.getMiddle_y() - myPlane.getSpeed() >= 0) {
                        myPlane.setMiddle_y(myPlane.getMiddle_y() - myPlane.getSpeed());
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void initBitmap() {
        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
        background2 = BitmapFactory.decodeResource(getResources(), R.drawable.bg_02);
        scalex = screen_width / background.getWidth();
        scaley = screen_height / background.getHeight();
        bg_y = 0;
        bg_y2 = bg_y - screen_height;
    }


    public void initObject() {
        for (EnemyPlane obj : enemyPlanes) {

            if (obj instanceof SmallPlane) {
                if (!obj.isAlive()) {
                    obj.initial(speedTime, 0, 0);
                    break;
                }
            }

        }

        myPlane.initButtle();

        if (sumScore >= speedTime * 10000 && speedTime < 6) {
            speedTime++;
        }
    }

    @Override
    public void release() {
        for (GameObject obj : enemyPlanes) {
            obj.release();
        }
        myPlane.release();

        if (!background.isRecycled()) {
            background.recycle();
        }
        if (!background2.isRecycled()) {
            background2.recycle();
        }
    }

    @Override
    public void drawSelf() {
        try {
            canvas = sfh.lockCanvas();
            canvas.drawColor(Color.BLACK);
            canvas.save();

            canvas.scale(scalex, scaley, 0, 0);
            canvas.drawBitmap(background, 0, bg_y, paint);
            canvas.drawBitmap(background2, 0, bg_y2, paint);
            canvas.restore();


            for (EnemyPlane obj : enemyPlanes) {
                if (obj.isAlive()) {
                    obj.drawSelf(canvas);

                    if (obj.isCanCollide() && myPlane.isAlive()) {
                        if (obj.isCollide(myPlane)) {
                            myPlane.setAlive(false);
                        }
                    }
                }
            }
            if (!myPlane.isAlive()) {
                threadFlag = false;
            }
            myPlane.drawSelf(canvas);
            myPlane.shoot(canvas, enemyPlanes);


            paint.setTextSize(30);
            paint.setColor(Color.rgb(235, 161, 1));
            canvas.drawText("Score :" + String.valueOf(sumScore), 30, 40, paint);
            canvas.drawText("Speed X " + String.valueOf(speedTime), screen_width - 150, 40, paint);
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }

    public void viewLogic() {
        if (bg_y > bg_y2) {
            bg_y += 10;
            bg_y2 = bg_y - background.getHeight();
        } else {
            bg_y2 += 10;
            bg_y = bg_y2 - background.getHeight();
        }
        if (bg_y >= background.getHeight()) {
            bg_y = bg_y2 - background.getHeight();
        } else if (bg_y2 >= background.getHeight()) {
            bg_y2 = bg_y - background.getHeight();
        }
    }

    public void addGameScore(int score) {
        bulletScore += score;
        sumScore += score;
    }

    @Override
    public void run() {
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            initObject();
            drawSelf();
            viewLogic();
            long endTime = System.currentTimeMillis();
            synchronized (thread) {
                try {
                    thread.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (endTime - startTime < 100)
                    Thread.sleep(100 - (endTime - startTime));
            } catch (InterruptedException err) {
                err.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Message message = new Message();
        message.what = ConstantUtil.TO_END_VIEW;
        message.arg1 = Integer.valueOf(sumScore);
        mainActivity.getHandler().sendMessage(message);
    }
}
