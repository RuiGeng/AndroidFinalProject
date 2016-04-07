package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Canvas;

/**
 * Created by RuiGeng on 3/25/2016.
 */
public class EnemyPlane extends GameObject {
    protected int score;
    protected int blood;
    protected int bloodVolume;
    protected boolean isExplosion;
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

    @Override
    public void logic() {
        if (objectY < screenHeight) {
            objectY += speed;
        } else {
            isAlive = false;
        }
        isVisible = objectY + objectHeight > 0;
    }

    public void attacked(int harm) {
        blood -= harm;
        if (blood <= 0) {
            isExplosion = true;
        }
    }

    @Override
    public boolean isCollide(GameObject obj) {

        if (objectX <= obj.getObjectX()
                && objectX + objectWidth <= obj.getObjectX()) {
            return false;
        } else if (obj.getObjectX() <= objectX
                && obj.getObjectX() + obj.getObjectWidth() <= objectX) {
            return false;
        } else if (objectY <= obj.getObjectY()
                && objectY + objectHeight <= obj.getObjectY()) {
            return false;
        } else if (obj.getObjectY() <= objectY
                && obj.getObjectY() + obj.getObjectHeight() <= objectY) {
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

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public int getBloodVolume() {
        return bloodVolume;
    }

    public void setBloodVolume(int bloodVolume) {
        this.bloodVolume = bloodVolume;
    }

    public boolean isExplosion() {
        return isExplosion;
    }
}
