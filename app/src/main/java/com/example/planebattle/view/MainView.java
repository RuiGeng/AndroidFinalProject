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
 * Created by RuiGeng 7128218 on 3/25/2016.
 */
public class MainView extends BaseView {
    //total score
    private int totalScore;
    //current speed
    private int gameSpeed;
    //touched flag
    private boolean isTouched;
    //background img
    private Bitmap backgroundBitmap;
    //player plane
    private MyPlane myPlane;
    //enemy plane
    private List<EnemyPlane> enemyPlanes;
    private GameObjectFactory objectFactory;
    //play flag
    private boolean isPlaying;

    public MainView(Context context) {
        super(context);
        isPlaying = true;
        //start speed
        gameSpeed = 1;
        objectFactory = new GameObjectFactory();
        enemyPlanes = new ArrayList<EnemyPlane>();
        myPlane = (MyPlane) objectFactory.createMyPlane(getResources());
        myPlane.setMainView(this);

        //create small enemy plane
        for (int i = 0; i < SmallPlane.totalCount; i++) {

            SmallPlane smallPlane = (SmallPlane) objectFactory.createSmallPlane(getResources());
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
        for (GameObject gameObject : enemyPlanes) {
            gameObject.setScreenWH(screenWidth, screenHeight);
        }
        myPlane.setScreenWH(screenWidth, screenHeight);
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
            isTouched = false;
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            //player plane on touch
            if (x > myPlane.getObjectX() && x < myPlane.getObjectX() + myPlane.getObjectWidth()
                    && y > myPlane.getObjectY() && y < myPlane.getObjectY() + myPlane.getObjectHeight()) {
                if (isPlaying) {
                    isTouched = true;
                }
                return true;
            }
        }
        //move action
        else if (event.getAction() == MotionEvent.ACTION_MOVE && event.getPointerCount() == 1) {
            //touch point on player airplane
            if (isTouched) {
                float x = event.getX();
                float y = event.getY();
                if (x > myPlane.getPlaneMiddleX() + 20) {
                    if (myPlane.getPlaneMiddleX() + myPlane.getSpeed() <= screenWidth) {
                        myPlane.setPlaneMiddleX(myPlane.getPlaneMiddleX() + myPlane.getSpeed());
                    }
                } else if (x < myPlane.getPlaneMiddleX() - 20) {
                    if (myPlane.getPlaneMiddleX() - myPlane.getSpeed() >= 0) {
                        myPlane.setPlaneMiddleX(myPlane.getPlaneMiddleX() - myPlane.getSpeed());
                    }
                }
                if (y > myPlane.getPlaneMiddleY() + 20) {
                    if (myPlane.getPlaneMiddleY() + myPlane.getSpeed() <= screenHeight) {
                        myPlane.setPlaneMiddleY(myPlane.getPlaneMiddleY() + myPlane.getSpeed());
                    }
                } else if (y < myPlane.getPlaneMiddleY() - 20) {
                    if (myPlane.getPlaneMiddleY() - myPlane.getSpeed() >= 0) {
                        myPlane.setPlaneMiddleY(myPlane.getPlaneMiddleY() - myPlane.getSpeed());
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void initBitmap() {
        backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
        scaleX = screenWidth / backgroundBitmap.getWidth();
        scaleY = screenHeight / backgroundBitmap.getHeight();
    }


    public void initObject() {
        for (EnemyPlane enemyPlane : enemyPlanes) {

            if (enemyPlane instanceof SmallPlane) {
                if (!enemyPlane.isAlive()) {
                    enemyPlane.initial(gameSpeed, 0, 0);
                    break;
                }
            }

        }
        myPlane.initBullet();

        if (totalScore >= gameSpeed * 1000 && gameSpeed < 10) {
            gameSpeed++;
        }
    }

    @Override
    public void release() {
        for (GameObject gameObject : enemyPlanes) {
            gameObject.release();
        }
        myPlane.release();

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

            canvas.save();
            for (EnemyPlane enemyPlane : enemyPlanes) {
                if (enemyPlane.isAlive()) {
                    enemyPlane.drawSelf(canvas);

                    if (enemyPlane.isCanCollide() && myPlane.isAlive()) {
                        if (enemyPlane.isCollide(myPlane)) {
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
            canvas.drawText("Score :" + String.valueOf(totalScore), 30, 40, paint);
            canvas.drawText("Speed X " + String.valueOf(gameSpeed), screenWidth - 150, 40, paint);
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            if (canvas != null)
                surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void addGameScore(int score) {
        totalScore += score;
    }

    @Override
    public void run() {
        while (threadFlag) {
            long startTime = System.currentTimeMillis();
            initObject();
            drawSelf();
            long endTime = System.currentTimeMillis();
            if (!isPlaying) {
                synchronized (thread) {
                    try {
                        thread.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        message.arg1 = Integer.valueOf(totalScore);
        mainActivity.getHandler().sendMessage(message);
    }
}
