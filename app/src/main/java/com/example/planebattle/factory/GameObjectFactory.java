package com.example.planebattle.factory;

import android.content.res.Resources;

import com.example.planebattle.object.GameObject;
import com.example.planebattle.object.MyBullet;
import com.example.planebattle.object.MyPlane;
import com.example.planebattle.object.SmallPlane;

/**
 * Created by RuiGeng on 3/26/2016.
 */
public class GameObjectFactory {

    public GameObject createMyBullet(Resources resources) {
        return new MyBullet(resources);
    }

    public GameObject createSmallPlane(Resources resources) {
        return new SmallPlane(resources);
    }

    public GameObject createMyPlane(Resources resources) {
        return new MyPlane(resources);
    }
}
