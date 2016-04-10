package com.example.planebattle.factory;

import android.content.res.Resources;

import com.example.planebattle.object.GameObject;
import com.example.planebattle.object.MyBullet;
import com.example.planebattle.object.MyPlane;
import com.example.planebattle.object.SmallPlane;

/**
 * Created by RuiGeng 7128218 on 3/26/2016.
 */
public class GameObjectFactory {

    //create player bullet
    public GameObject createMyBullet(Resources resources) {
        return new MyBullet(resources);
    }

    //create enemy airplane
    public GameObject createSmallPlane(Resources resources) {
        return new SmallPlane(resources);
    }

    //create player airplane
    public GameObject createMyPlane(Resources resources) {
        return new MyPlane(resources);
    }
}
