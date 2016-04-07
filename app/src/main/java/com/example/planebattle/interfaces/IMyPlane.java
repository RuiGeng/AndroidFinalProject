package com.example.planebattle.interfaces;

import android.graphics.Canvas;

import com.example.planebattle.object.EnemyPlane;

import java.util.List;

/**
 * Created by RuiGeng on 3/25/2016.
 */
public interface IMyPlane {
    float getPlaneMiddleX();

    void setPlaneMiddleX(float middle_x);

    float getPlaneMiddleY();

    void setPlaneMiddleY(float middle_y);

    void shoot(Canvas canvas, List<EnemyPlane> planes);

    void initBullet();

    void changeBullet();
}
