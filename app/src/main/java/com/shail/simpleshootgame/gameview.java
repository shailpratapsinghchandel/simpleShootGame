package com.shail.simpleshootgame;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.shail.simpleshootgame.background;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class gameview extends SurfaceView implements Runnable{
    private Thread thread;
    private boolean isPlaying,isGameOver=false;
    private int screenx,screeny,score=0;
    private background background1,background2;
    private Paint paint;
    private List<Bullet> bullets;
    public static float screenRatiox,  screenRatioy;
    private flight f;
    private Bird[] birds;
    private Random random;

    public gameview(Context context, int screenx, int screeny) {
        super(context);
        this.screenx=screenx;
        this.screeny=screeny;
        screenRatiox=1920f/screenx;
        screenRatioy=1080f/screeny;
        background1 =new background(screenx,screeny,getResources());
        background2 =new background(screenx, screeny,getResources());

        f = new flight( this,screeny, getResources());

        bullets = new ArrayList<>();
        background2.x=screenx;
        paint =new Paint();
        paint.setTextSize(120);
        paint.setColor(Color.BLACK);

        birds=new Bird[4];
        for(int i=0;i<4;i++)
        {
            Bird bird=new Bird(getResources());
            birds[i]=bird;
        }
        random=new Random();
    }

    @Override
    public void run() {
        while (isPlaying){
            update ();
            draw ();
            sleep ();

        }


    }
    private void update()
    {
        background1.x -=10*screenRatiox;
        background2.x -=10*screenRatiox;

        if(background1.x + background1.background.getWidth() <0)
        {
            background1.x=screenx;
        }
        if(background2.x + background2.background.getWidth() <0)
        {
            background2.x=screenx;
        }
        if(f.isGoingUp)
            f.y-= 30*screenRatioy;
        else
            f.y+= 30*screenRatioy;
        if(f.y<0)
            f.y=0;
        if(f.y > screeny-f.height)
            f.y=screeny-f.height;

        List<Bullet> trash=new ArrayList<>();

        for(Bullet bullet : bullets){
            if(bullet.x > screenx)
                trash.add(bullet);
            bullet.x +=50*screenRatiox;

            for(Bird bird : birds){
                if(Rect.intersects(bird.getCollisionShape(),bullet.getCollisionShape())) {

                    score++;
                    bird.x = -500;
                    bullet.x = screenx + 500;
                    bird.wasShot=true;
                }
            }
        }
        for(Bullet bullet: trash)
            bullets.remove(bullet);

        for(Bird bird:birds){
            bird.x -= bird.speed;
            if(bird.x+bird.width<0){

                if(!bird.wasShot){
                    isGameOver=true;
                    return;
                }

                int bound= (int) (30*screenRatiox);
                bird.speed = random.nextInt(bound);

                if(bird.speed<10*screenRatiox)
                    bird.speed= (int) (10*screenRatiox);

                bird.x=screenx;
                bird.y=random.nextInt(screeny-bird.height);

                bird.wasShot=false;

            }

            if(Rect.intersects(bird.getCollisionShape(),f.getCollisionShape())){
                isGameOver=true;
                return;
            }
        }

    }
    private void draw()
    {
        if(getHolder().getSurface().isValid())
        {
            Canvas canvas=getHolder().lockCanvas();
            canvas.drawBitmap(background1.background,background1.x,background1.y,paint);
            canvas.drawBitmap(background2.background,background2.x,background2.y,paint);

            for(Bird bird:birds){
                canvas.drawBitmap(bird.getBird(),bird.x,bird.y,paint);
            }
            canvas.drawText(score+" ",screenx/2f,164,paint);

            if(isGameOver)
            {
                isPlaying=false;
                canvas.drawBitmap(f.getDead(),f.x,f.y,paint);
                getHolder().unlockCanvasAndPost(canvas);
                return;
            }


            canvas.drawBitmap(f.getFlight(), f.x,f.y,paint);

            for(Bullet bullet:bullets)
            {
                canvas.drawBitmap(bullet.bullet,bullet.x,bullet.y,paint);
            }

            getHolder().unlockCanvasAndPost(canvas);
        }

    }
    private void sleep()
    {
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void resume(){
        thread = new Thread(this);
        thread.start();
        isPlaying=true;

    }
    public void pause(){
        try {
            isPlaying=false;
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            if(event.getX() < screenx/2){
                f.isGoingUp=true;
            }
            break;
            case MotionEvent.ACTION_UP:
                f.isGoingUp=false;
                if(event.getX() > screenx/2)
                    f.toShoot++;
            break;
        }
        return true;
    }

    public void newBullet() {
        Bullet bullet=new Bullet(getResources());
        bullet.x=f.x+f.width;
        bullet.y=f.y+(f.height/2);
        bullets.add(bullet);

    }
}

