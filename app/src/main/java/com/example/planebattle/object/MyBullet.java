package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Bitmap;

/**
 * Created by RuiGeng on 3/26/2016.
 */
public class MyBullet extends Bullet {
    private Bitmap bullet;
    public MyBullet(Resources resources) {
        super(resources);
        this.harm = 1;
    }
}
