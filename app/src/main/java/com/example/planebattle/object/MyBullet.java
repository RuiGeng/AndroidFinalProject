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
        object_x = arg1 - object_width / 2;
        object_y = arg2 - object_height;
    }

    @Override
    public void initBitmap() {
        bullet = BitmapFactory.decodeResource(resources, R.drawable.bullet);
        object_width = bullet.getWidth();
        object_height = bullet.getHeight();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
            canvas.drawBitmap(bullet, object_x, object_y, paint);
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
        if (object_y >= 0) {
            object_y -= speed;
        } else {
            isAlive = false;
        }
    }

    @Override
    public boolean isCollide(GameObject obj) {
        return super.isCollide(obj);
    }
}
