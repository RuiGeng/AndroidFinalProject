package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.planebattle.R;

import java.util.Random;

/**
 * Created by RuiGeng 7128218 on 3/28/2016.
 */
public class SmallPlane extends EnemyPlane {
    private static int currentCount = 0;
    private Bitmap smallPlane;
    //total plane count
    public static int totalCount = 10;

    public SmallPlane(Resources resources) {
        super(resources);
        this.score = 100;
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
        isAlive = true;
        totalBlood = 1;
        currentBlood = totalBlood;
        Random ran = new Random();
        speed = ran.nextInt(10) + 10 * arg0;
        objectX = ran.nextInt((int) (screenWidth - objectWidth));
        objectY = -objectHeight * (currentCount * 2 + 1);
        currentCount++;
        if (currentCount >= totalCount) {
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
        //is alive
        if (isAlive) {
            //is not explosion
            if (!isExplosion) {
                if (isVisible) {
                    canvas.save();
                    canvas.clipRect(objectX, objectY, objectX + objectWidth, objectY + objectHeight);
                    canvas.drawBitmap(smallPlane, objectX, objectY, paint);
                    canvas.restore();
                }
                logic();
            } else {
                int y = (int) (currentPoint * objectHeight);
                canvas.save();
                canvas.clipRect(objectX, objectY, objectX + objectWidth, objectY + objectHeight);
                canvas.drawBitmap(smallPlane, objectX, objectY - y, paint);
                canvas.restore();
                currentPoint++;
                if (currentPoint >= 3) {
                    currentPoint = 0;
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


