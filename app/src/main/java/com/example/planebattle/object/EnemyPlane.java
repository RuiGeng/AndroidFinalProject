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

        if (object_x <= obj.getObject_x()
                && object_x + object_width <= obj.getObject_x()) {
            return false;
        } else if (obj.getObject_x() <= object_x
                && obj.getObject_x() + obj.getObject_width() <= object_x) {
            return false;
        } else if (object_y <= obj.getObject_y()
                && object_y + object_height <= obj.getObject_y()) {
            return false;
        } else if (obj.getObject_y() <= object_y
                && obj.getObject_y() + obj.getObject_height() <= object_y) {
            return false;
        }
        return true;
    }

    public boolean isCanCollide() {
        return isAlive && !isExplosion && isVisible;
    }

    public void attacked(int harm) {
        blood -= harm;
        if (blood <= 0) {
            isExplosion = true;
        }
    }

    public boolean isExplosion() {
        return isExplosion;
    }

}
