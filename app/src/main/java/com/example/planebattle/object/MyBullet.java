package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.planebattle.R;

/**
 * Created by RuiGeng on 3/26/2016.
 */
public class MyBullet extends Bullet {
    private Bitmap bullet;

    public MyBullet(Resources resources) {
        super(resources);
        this.harm = 1;
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
        isAlive = true;
        speed = 100;
        objectX = arg1 - objectWidth / 2;
        objectY = arg2 - objectHeight;
    }

    @Override
    public void initBitmap() {
        bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);
        objectWidth = bullet.getWidth();
        objectHeight = bullet.getHeight();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {
            canvas.save();
            canvas.clipRect(objectX, objectY, objectX + objectWidth, objectY + objectHeight);
            canvas.drawBitmap(bullet, objectX, objectY, paint);
            canvas.restore();
            logic();
        }
    }

    @Override
    public void release() {
        if (!bullet.isRecycled()) {
            bullet.recycle();
        }
    }

    @Override
    public void logic() {
        if (objectY >= 0) {
            objectY -= speed;
        } else {
            isAlive = false;
        }
    }

    @Override
    public boolean isCollide(GameObject obj) {
        return super.isCollide(obj);
    }
}
