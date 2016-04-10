package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Canvas;

/**
 * Created by RuiGeng 7128218 on 3/25/2016.
 */

//base class of enemy plane
public class EnemyPlane extends GameObject {
    //score of enemy plane
    protected int score;
    //current Blood
    protected int currentBlood;
    //total Blood
    protected int totalBlood;
    //explosion flag
    protected boolean isExplosion;
    //visible flag
    protected boolean isVisible;

    public EnemyPlane(Resources resources) {
        super(resources);
        initBitmap();
    }

    @Override
    public void initial(int arg0, float arg1, float arg2) {
    }

    @Override
    public void initBitmap() {
    }

    @Override
    public void drawSelf(Canvas canvas) {
    }

    @Override
    public void release() {
    }

    //logic function
    @Override
    public void logic() {
        if (objectY < screenHeight) {
            objectY += speed;
        } else {
            isAlive = false;
        }
        isVisible = objectY + objectHeight > 0;
    }

    //be attacked function
    public void attacked(int harm) {
        currentBlood -= harm;
        if (currentBlood <= 0) {
            isExplosion = true;
        }
    }

    @Override
    public boolean isCollide(GameObject gameObject) {

        //object at the game object left side
        if (objectX <= gameObject.getObjectX()
                && objectX + objectWidth <= gameObject.getObjectX()) {
            return false;
            //object at the game object right side
        } else if (gameObject.getObjectX() <= objectX
                && gameObject.getObjectX() + gameObject.getObjectWidth() <= objectX) {
            return false;
            //object at the game object up side
        } else if (objectY <= gameObject.getObjectY()
                && objectY + objectHeight <= gameObject.getObjectY()) {
            return false;
            //object at the game object down side
        } else if (gameObject.getObjectY() <= objectY
                && gameObject.getObjectY() + gameObject.getObjectHeight() <= objectY) {
            return false;
        }
        return true;
    }

    public boolean isCanCollide() {
        return isAlive && !isExplosion && isVisible;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCurrentBlood() {
        return currentBlood;
    }

    public void setCurrentBlood(int currentBlood) {
        this.currentBlood = currentBlood;
    }

    public int getTotalBlood() {
        return totalBlood;
    }

    public void setTotalBlood(int totalBlood) {
        this.totalBlood = totalBlood;
    }

    public boolean isExplosion() {
        return isExplosion;
    }
}
