package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Canvas;

/**
 * Created by RuiGeng on 3/26/2016.
 */
public class Bullet extends GameObject {
    protected int harm;

    public Bullet(Resources resources) {
        super(resources);
        initBitmap();
    }

    @Override
    protected void initBitmap() {
    }

    @Override
    public void drawSelf(Canvas canvas) {
    }

    @Override
    public void release() {
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
            if (obj instanceof SmallPlane) {
                if (objectY - speed < obj.getObjectY()) {
                    isAlive = false;
                    return true;
                }
            } else
                return false;
        }
        isAlive = false;
        return true;
    }

    public int getHarm() {
        return harm;
    }

    public void setHarm(int harm) {
        this.harm = harm;
    }
}
