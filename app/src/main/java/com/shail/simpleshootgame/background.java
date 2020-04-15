package com.shail.simpleshootgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class background {
    int x=0, y=0;
    Bitmap background;

    background (int screenx, int screeny, Resources res)
    {
        background = BitmapFactory.decodeResource(res,R.drawable.background);
        background = Bitmap.createScaledBitmap(background, screenx, screeny, false);
    }
}
