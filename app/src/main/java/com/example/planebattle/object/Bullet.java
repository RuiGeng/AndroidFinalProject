package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Canvas;

/**
 * Created by RuiGeng on 3/26/2016.
 */
public class Bullet  extends  GameObject {
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
        return true;
    }

    public int getHarm() {
        return harm;
    }
}
