package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.planebattle.R;
import com.example.planebattle.interfaces.IMyPlane;
import com.example.planebattle.view.MainView;
import com.example.planebattle.factory.GameObjectFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RuiGeng on 3/25/2016.
 */
public class MyPlane extends GameObject implements IMyPlane {
    private float planeMiddleX;
    private float planeMiddleY;
    private Bitmap myPlaneBitmap1;
    private Bitmap myPlaneBitmap2;
    private List<Bullet> bullets;
    private MainView mainView;
    private GameObjectFactory objectFactory;

    public MyPlane(Resources resources) {
        super(resources);
        initBitmap();
        this.speed = 8;
        objectFactory = new GameObjectFactory();
        bullets = new ArrayList<Bullet>();
        changeBullet();
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void setScreenWH(float screenWidth, float screenHeight) {
        super.setScreenWH(screenWidth, screenHeight);
        objectX = screenWidth / 2 - objectWidth / 2;
        objectY = screenHeight - objectHeight;
        planeMiddleX = objectX + objectWidth / 2;
        planeMiddleY = objectY + objectHeight / 2;
    }

    @Override
    public void initBitmap() {
        myPlaneBitmap1 = BitmapFactory.decodeResource(resources, R.drawable.myplane);
        myPlaneBitmap2 = BitmapFactory.decodeResource(resources, R.drawable.myplaneexplosion);
        objectWidth = myPlaneBitmap1.getWidth();
        objectHeight = myPlaneBitmap1.getHeight();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {
            canvas.save();
            canvas.clipRect(objectX, objectY, objectX + objectWidth, objectY + objectHeight);
            canvas.drawBitmap(myPlaneBitmap1, objectX, objectY, paint);
            canvas.restore();
            currentView++;
            if (currentView >= 2) {
                currentView = 0;
            }
        } else {
            canvas.save();
            canvas.clipRect(objectX, objectY, objectX + objectWidth, objectY
                    + objectHeight);
            canvas.drawBitmap(myPlaneBitmap2, objectX, objectY, paint);
            canvas.restore();
            currentView++;
            if (currentView >= 2) {
                currentView = 1;
            }
        }
    }

    @Override
    public void release() {
        for (Bullet bullet : bullets) {
            bullet.release();
        }
        if (!myPlaneBitmap1.isRecycled()) {
            myPlaneBitmap1.recycle();
        }
        if (!myPlaneBitmap2.isRecycled()) {
            myPlaneBitmap2.recycle();
        }
    }

    @Override
    public void shoot(Canvas canvas, List<EnemyPlane> planes) {
        for (Bullet bullet : bullets) {
            if (bullet.isAlive()) {
                for (EnemyPlane enemyPlane : planes) {
                    if (enemyPlane.isCanCollide()) {
                        if (bullet.isCollide(enemyPlane)) {
                            enemyPlane.attacked(bullet.getHarm());
                            if (enemyPlane.isExplosion()) {
                                mainView.addGameScore(enemyPlane.getScore());
                            }
                            break;
                        }
                    }
                }
                bullet.drawSelf(canvas);
            }
        }
    }

    @Override
    public void initBullet() {
        for (Bullet bullet : bullets) {
            if (!bullet.isAlive()) {
                bullet.initial(0, planeMiddleX, planeMiddleY);
                break;
            }
        }
    }

    @Override
    public void changeBullet() {
        bullets.clear();
        for (int i = 0; i < 4; i++) {
            MyBullet bullet = (MyBullet) objectFactory.createMyBullet(resources);
            bullets.add(bullet);
        }
    }

    @Override
    public float getPlaneMiddleX() {
        return planeMiddleX;
    }

    @Override
    public void setPlaneMiddleX(float planeMiddleX) {
        this.planeMiddleX = planeMiddleX;
        this.objectX = planeMiddleX - objectWidth / 2;
    }

    @Override
    public float getPlaneMiddleY() {
        return planeMiddleY;
    }

    @Override
    public void setPlaneMiddleY(float planeMiddleY) {
        this.planeMiddleY = planeMiddleY;
        this.objectY = planeMiddleY - objectHeight / 2;
    }
}
