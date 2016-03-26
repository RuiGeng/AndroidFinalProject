package com.example.planebattle.object;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.planebattle.R;
import com.example.planebattle.interfaces.IMyPlane;
import com.example.planebattle.view.MainView;
import com.example.planebattle.factory.GameObjectFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RuiGeng on 3/25/2016.
 */
public class MyPlane extends GameObject implements IMyPlane {
    private float middle_x;
    private float middle_y;
    private long startTime;
    private long endTime;
    private Bitmap myplane;
    private Bitmap myplane2;
    private List<Bullet> bullets;
    private MainView mainView;
    private GameObjectFactory factory;

    public MyPlane(Resources resources) {
        super(resources);
        initBitmap();
        this.speed = 8;
        factory = new GameObjectFactory();
        bullets = new ArrayList<Bullet>();
        changeButtle();
    }
    public void setMainView(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void setScreenWH(float screen_width, float screen_height) {
        super.setScreenWH(screen_width, screen_height);
        object_x = screen_width/2 - object_width/2;
        object_y = screen_height - object_height;
        middle_x = object_x + object_width/2;
        middle_y = object_y + object_height/2;
    }

    @Override
    public void initBitmap() {
        myplane = BitmapFactory.decodeResource(resources, R.drawable.myplane);
        myplane2 = BitmapFactory.decodeResource(resources, R.drawable.myplaneexplosion);
        object_width = myplane.getWidth();
        object_height = myplane.getHeight();
    }

    @Override
    public void drawSelf(Canvas canvas) {
        // TODO Auto-generated method stub
        if(isAlive){
            int x = (int) (currentFrame * object_width);
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width, object_y + object_height);
            canvas.drawBitmap(myplane, object_x - x, object_y, paint);
            canvas.restore();
            currentFrame++;
            if (currentFrame >= 2) {
                currentFrame = 0;
            }
        }
        else{
            int x = (int) (currentFrame * object_width);
            canvas.save();
            canvas.clipRect(object_x, object_y, object_x + object_width, object_y
                    + object_height);
            canvas.drawBitmap(myplane2, object_x - x, object_y, paint);
            canvas.restore();
            currentFrame++;
            if (currentFrame >= 2) {
                currentFrame = 1;
            }
        }
    }

    @Override
    public void release() {
        for(Bullet obj:bullets){
            obj.release();
        }
        if(!myplane.isRecycled()){
            myplane.recycle();
        }
        if(!myplane2.isRecycled()){
            myplane2.recycle();
        }
    }

    @Override
    public void shoot(Canvas canvas,List<EnemyPlane> planes) {
        for(Bullet obj:bullets){
            if(obj.isAlive()){
                for(EnemyPlane pobj:planes){
                    if( pobj.isCanCollide()){
                        if(obj.isCollide((GameObject)pobj)){
                            pobj.attacked(obj.getHarm());
                            if(pobj.isExplosion()){
//                                mainView.addGameScore(pobj.getScore());
//                                if(pobj instanceof SmallPlane){
//                                }
//                                else if(pobj instanceof MiddlePlane){
//                                }
//                                else if(pobj instanceof BigPlane){
//                                }
//                                else{
//                                }
                            }
                            break;
                        }
                    }
                }
                obj.drawSelf(canvas);
            }
        }
    }

    @Override
    public void initButtle() {
        for(Bullet obj:bullets){
            if(!obj.isAlive()){
                obj.initial(0,middle_x, middle_y);
                break;
            }
        }
    }

   @Override
    public void changeButtle() {
        bullets.clear();
            for(int i = 0;i < 4;i++){
                MyBullet bullet = (MyBullet) factory.createMyBullet(resources);
                bullets.add(bullet);
            }
        }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public float getMiddle_x() {
        return middle_x;
    }
    @Override
    public void setMiddle_x(float middle_x) {
        this.middle_x = middle_x;
        this.object_x = middle_x - object_width/2;
    }
    @Override
    public float getMiddle_y() {
        return middle_y;
    }
    @Override
    public void setMiddle_y(float middle_y) {
        this.middle_y = middle_y;
        this.object_y = middle_y - object_height/2;
    }
}
