package com.shail.simpleshootgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.shail.simpleshootgame.gameview.screenRatiox;
import static com.shail.simpleshootgame.gameview.screenRatioy;

public class flight {


    public int toShoot =0 ,shootCounter=1;

    boolean isGoingUp=false;
    int x,y,width,height,wingCounter=0;
    Bitmap flight1,flight2,flight3,flight4,shoot1,shoot2,shoot3,shoot4,shoot5,dead;

    private gameview gameView;

    flight( gameview gameView,int screeny, Resources res)
    {
        this.gameView=gameView;
        flight1= BitmapFactory.decodeResource(res,R.drawable.fly1);
        flight2= BitmapFactory.decodeResource(res,R.drawable.fly2);
       /* flight3= BitmapFactory.decodeResource(res,R.drawable.eagle3);
        flight4= BitmapFactory.decodeResource(res,R.drawable.eagle4);*/
        width=flight1.getWidth();
        height=flight1.getHeight();

        width /=4;
        height /=4;

        width*=(int)screenRatiox;
        height*=(int)screenRatioy;

        flight1=Bitmap.createScaledBitmap(flight1,width,height,false);
        flight2=Bitmap.createScaledBitmap(flight2,width,height,false);
       /* flight3=Bitmap.createScaledBitmap(flight3,width,height,false);
        flight4=Bitmap.createScaledBitmap(flight4,width,height,false);*/

        shoot1=BitmapFactory.decodeResource(res, R.drawable.shoot1);
        shoot2=BitmapFactory.decodeResource(res, R.drawable.shoot2);
        shoot3=BitmapFactory.decodeResource(res, R.drawable.shoot3);
        shoot4=BitmapFactory.decodeResource(res, R.drawable.shoot4);
        shoot5=BitmapFactory.decodeResource(res, R.drawable.shoot5);

        shoot1 = Bitmap.createScaledBitmap(shoot1,width,height,false);
        shoot2 = Bitmap.createScaledBitmap(shoot2,width,height,false);
        shoot3 = Bitmap.createScaledBitmap(shoot3,width,height,false);
        shoot4 = Bitmap.createScaledBitmap(shoot4,width,height,false);
        shoot5 = Bitmap.createScaledBitmap(shoot4,width,height,false);

        dead=BitmapFactory.decodeResource(res, R.drawable.dead);
        dead=Bitmap.createScaledBitmap(dead,width,height,false);

        y=screeny/2;
        x=(int)(64*screenRatiox);

    }

    Bitmap getFlight()
    {
        if(toShoot!=0)
        {
         if(shootCounter == 1) {
            shootCounter++;
            return shoot1;
        }
            if(shootCounter == 2) {
                shootCounter++;
                return shoot2;
            }
            if(shootCounter == 3) {
                shootCounter++;
                return shoot3;
            }
            if(shootCounter == 4) {
                shootCounter++;
                return shoot4;
            }
            shootCounter=1;
            toShoot++;
            gameView.newBullet();
            return shoot5;
        }

        if(wingCounter == 0)
        {
            wingCounter++;
            return flight1;
        }
      /* else if(wingCounter == 1)
        {
            wingCounter++;
            return flight2;
        }
       else if(wingCounter == 2)
        {
            wingCounter++;
            return flight3;
        }*/
       else{
           wingCounter=0;
           return flight2;
        }


    }
    Rect getCollisionShape(){
        return new Rect(x,y,x+width,y+height);
    }
    Bitmap getDead(){
        return dead;
    }
}
