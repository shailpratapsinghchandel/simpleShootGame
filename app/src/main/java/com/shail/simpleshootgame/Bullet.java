package com.shail.simpleshootgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.shail.simpleshootgame.gameview.screenRatiox;
import static com.shail.simpleshootgame.gameview.screenRatioy;

public class Bullet {
    int x,y;
    Bitmap bullet;
    int width;
    int height;
    Bullet(Resources res)
    {
        bullet= BitmapFactory.decodeResource(res,R.drawable.bullet);

        width=bullet.getWidth();
         height=bullet.getHeight();

        width /=4;
        height /=4;

        width *= (int)screenRatiox;
        height *= (int)screenRatioy;

        bullet=Bitmap.createScaledBitmap(bullet,width,height,false);

    }
    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }
}
