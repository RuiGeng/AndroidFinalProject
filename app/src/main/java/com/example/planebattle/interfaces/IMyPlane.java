package com.example.planebattle.interfaces;

import android.graphics.Canvas;

import com.example.planebattle.object.EnemyPlane;

import java.util.List;

/**
 * Created by RuiGeng on 3/25/2016.
 */
public interface IMyPlane {
    float getMiddle_x();
    void setMiddle_x(float middle_x);
    float getMiddle_y();
    void setMiddle_y(float middle_y);

    void shoot(Canvas canvas, List<EnemyPlane> planes);

    void initButtle();

    void changeButtle();
}
