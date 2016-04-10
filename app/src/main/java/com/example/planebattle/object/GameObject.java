package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by RuiGeng 7128218 on 3/25/2016.
 */
abstract public class GameObject {
    //current object point
    protected int currentPoint;
    //object speed
    protected int speed;
    //object x  coordinate
    protected float objectX;
    //object y coordinate
    protected float objectY;
    //object width
    protected float objectWidth;
    //object height
    protected float objectHeight;
    //screen width
    protected float screenWidth;
    //screen height
    protected float screenHeight;
    //alive flag
    protected boolean isAlive;
    protected Paint paint;
    protected Resources resources;

    public GameObject(Resources resources) {
        this.resources = resources;
        this.paint = new Paint();
    }

    //set screen size
    public void setScreenWH(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    //initial function
    //arg0: gameSpeed
    //arg1: object middle x
    //arg2: object middle y
    public void initial(int arg0, float arg1, float arg2) {

    }

    //initial bit map
    protected abstract void initBitmap();

    //object draw it self function
    public abstract void drawSelf(Canvas canvas);

    //release object
    public abstract void release();

    //collide function
    public boolean isCollide(GameObject object) {
        return true;
    }

    //object logic function
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
