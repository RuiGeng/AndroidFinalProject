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
    private float middle_x;
    private float middle_y;
    private Bitmap myplane;
    private Bitmap myplane2;
    private List<Bullet> bullets;
    private MainView mainView;
    private GameObjectFactory factory;

    public MyPlane(Resources resources) {
        super(resources);
        initBitmap();
        this.speed = 8;
        factory = new GameObjectFactory();
        bullets = new ArrayList<Bullet>();
        changeButtle();
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void setScreenWH(float screenWidth, float screenHeight) {
        super.setScreenWH(screenWidth, screenHeight);
        objectX = screenWidth / 2 - objectWidth / 2;
        objectY = screenHeight - objectHeight;
        middle_x = objectX + objectWidth / 2;
        middle_y = objectY + objectHeight / 2;
    }

    @Override
    public void initBitmap() {
        myplane = BitmapFactory.decodeResource(resources, R.drawable.myplane);
        myplane2 = BitmapFactory.decodeResource(resources, R.drawable.myplaneexplosion);
        objectWidth = myplane.getWidth();
        objectHeight = myplane.getHeight();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {
            int x = (int) (currentView * objectWidth);
            canvas.save();
            canvas.clipRect(objectX, objectY, objectX + objectWidth, objectY + objectHeight);
            canvas.drawBitmap(myplane, objectX, objectY, paint);
            canvas.restore();
            currentView++;
            if (currentView >= 2) {
                currentView = 0;
            }
        } else {
            int x = (int) (currentView * objectWidth);
            canvas.save();
            canvas.clipRect(objectX, objectY, objectX + objectWidth, objectY
                    + objectHeight);
            canvas.drawBitmap(myplane2, objectX, objectY, paint);
            canvas.restore();
            currentView++;
            if (currentView >= 2) {
                currentView = 1;
            }
        }
    }

    @Override
    public void release() {
        for (Bullet obj : bullets) {
            obj.release();
        }
        if (!myplane.isRecycled()) {
            myplane.recycle();
        }
        if (!myplane2.isRecycled()) {
            myplane2.recycle();
        }
    }

    @Override
    public void shoot(Canvas canvas, List<EnemyPlane> planes) {
        for (Bullet obj : bullets) {
            if (obj.isAlive()) {
                for (EnemyPlane pobj : planes) {
                    if (pobj.isCanCollide()) {
                        if (obj.isCollide(pobj)) {
                            pobj.attacked(obj.getHarm());
                            if (pobj.isExplosion()) {
                                mainView.addGameScore(pobj.getScore());
                                if (pobj instanceof SmallPlane) {
                                } else {
                                }
                            }
                            break;
                        }
                    }
                }
                obj.drawSelf(canvas);
            }
        }
    }

    @Override
    public void initButtle() {
        for (Bullet obj : bullets) {
            if (!obj.isAlive()) {
                obj.initial(0, middle_x, middle_y);
                break;
            }
        }
    }

    @Override
    public void changeButtle() {
        bullets.clear();
        for (int i = 0; i < 4; i++) {
            MyBullet bullet = (MyBullet) factory.createMyBullet(resources);
            bullets.add(bullet);
        }
    }

    @Override
    public float getMiddle_x() {
        return middle_x;
    }

    @Override
    public void setMiddle_x(float middle_x) {
        this.middle_x = middle_x;
        this.objectX = middle_x - objectWidth / 2;
    }

    @Override
    public float getMiddle_y() {
        return middle_y;
    }

    @Override
    public void setMiddle_y(float middle_y) {
        this.middle_y = middle_y;
        this.objectY = middle_y - objectHeight / 2;
    }
}
