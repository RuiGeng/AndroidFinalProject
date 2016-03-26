package com.example.planebattle.factory;

import android.content.res.Resources;

import com.example.planebattle.object.GameObject;
import com.example.planebattle.object.MyBullet;

/**
 * Created by RuiGeng on 3/26/2016.
 */
public class GameObjectFactory {
    public GameObject createMyBullet(Resources resources){
        return new MyBullet(resources);
    }
}
