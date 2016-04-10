package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Canvas;

/**
 * Created by RuiGeng 7128218 on 3/26/2016.
 */

//base class of the bullet
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
            if (gameObject instanceof SmallPlane) {
                if (objectY - speed < gameObject.getObjectY()) {
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
