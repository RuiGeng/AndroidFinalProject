package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by RuiGeng on 3/25/2016.
 */
abstract public class GameObject {
    protected int currentView;
    protected int speed;
    protected float objectX;
    protected float objectY;
    protected float objectWidth;
    protected float objectHeight;
    protected float screenWidth;
    protected float screenHeight;
    protected boolean isAlive;
    protected Paint paint;
    protected Resources resources;

    public GameObject(Resources resources) {
        this.resources = resources;
        this.paint = new Paint();
    }

    public void setScreenWH(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public void initial(int arg0, float arg1, float arg2) {

    }

    protected abstract void initBitmap();

    public abstract void drawSelf(Canvas canvas);

    public abstract void release();

    public boolean isCollide(GameObject object) {
        return true;
    }

    public void logic() {
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public float getObjectX() {
        return objectX;
    }

    public void setObjectX(float objectX) {
        this.objectX = objectX;
    }

    public float getObjectY() {
        return objectY;
    }

    public void setObjectY(float objectY) {
        this.objectY = objectY;
    }

    public float getObjectWidth() {
        return objectWidth;
    }

    public void setObjectWidth(float objectWidth) {
        this.objectWidth = objectWidth;
    }

    public float getObjectHeight() {
        return objectHeight;
    }

    public void setObjectHeight(float objectHeight) {
        this.objectHeight = objectHeight;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
}
