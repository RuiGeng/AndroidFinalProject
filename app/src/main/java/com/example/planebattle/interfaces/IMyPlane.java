package com.example.planebattle.interfaces;

import android.graphics.Canvas;

import com.example.planebattle.object.EnemyPlane;

import java.util.List;

/**
 * Created by RuiGeng on 3/25/2016.
 */
public interface IMyPlane {
    //get the middle point x of the plane
    float getPlaneMiddleX();

    void setPlaneMiddleX(float middle_x);

    //get the middle point y of the plane
    float getPlaneMiddleY();

    void setPlaneMiddleY(float middle_y);

    //shoot bullet function
    void shoot(Canvas canvas, List<EnemyPlane> planes);

    //initial bullet function
    void initBullet();

    //change bullet function
    void changeBullet();
}
