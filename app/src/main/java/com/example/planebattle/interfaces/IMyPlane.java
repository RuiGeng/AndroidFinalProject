package com.example.planebattle.interfaces;

import android.graphics.Canvas;

import com.example.planebattle.object.EnemyPlane;

import java.util.List;

/**
 * Created by RuiGeng on 3/25/2016.
 */
public interface IMyPlane {
    public float getMiddle_x();
    public void setMiddle_x(float middle_x);
    public float getMiddle_y();
    public void setMiddle_y(float middle_y);

    public void shoot(Canvas canvas,List<EnemyPlane> planes);

    public void initButtle();

    public void changeButtle();
}
