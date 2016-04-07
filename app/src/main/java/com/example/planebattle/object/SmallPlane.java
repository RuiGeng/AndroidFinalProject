package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.planebattle.R;

import java.util.Random;

/**
 * Created by RuiGeng on 3/28/2016.
 */
public class SmallPlane extends EnemyPlane {
    private static int currentCount = 0;
    private Bitmap smallPlane;
    public static int sumCount = 8;

    public SmallPlane(Resources resources) {
        super(resources);
        this.score = 100;
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
        isAlive = true;
        bloodVolume = 1;
        blood = bloodVolume;
        Random ran = new Random();
        speed = ran.nextInt(8) + 8 * arg0;
        objectX = ran.nextInt((int) (screenWidth - objectWidth));
        objectY = -objectHeight * (currentCount * 2 + 1);
        currentCount++;
        if (currentCount >= sumCount) {
            currentCount = 0;
        }
    }

    @Override
    public void initBitmap() {
        smallPlane = BitmapFactory.decodeResource(resources, R.drawable.small);
        objectWidth = smallPlane.getWidth();
        objectHeight = smallPlane.getHeight() / 3;
    }

    @Override
    public void drawSelf(Canvas canvas) {
        if (isAlive) {

            if (!isExplosion) {
                if (isVisible) {
                    canvas.save();
                    canvas.clipRect(objectX, objectY, objectX + objectWidth, objectY + objectHeight);
                    canvas.drawBitmap(smallPlane, objectX, objectY, paint);
                    canvas.restore();
                }
                logic();
            } else {
                int y = (int) (currentView * objectHeight);
                canvas.save();
                canvas.clipRect(objectX, objectY, objectX + objectWidth, objectY + objectHeight);
                canvas.drawBitmap(smallPlane, objectX, objectY - y, paint);
                canvas.restore();
                currentView++;
                if (currentView >= 3) {
                    currentView = 0;
                    isExplosion = false;
                    isAlive = false;
                }
            }
        }
    }

    @Override
    public void release() {
        if (!smallPlane.isRecycled()) {
            smallPlane.recycle();
        }
    }
}


