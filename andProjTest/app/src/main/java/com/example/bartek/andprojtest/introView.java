package com.example.bartek.andprojtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Bartek on 2015-05-30.
 */
public class introView extends View{

    protected Drawable ballSprite;
    protected int ballHeigth = 40;
    protected int ballWidth = 40;
    protected int speed = 20;
    protected Point ballPos = new Point(50,50);
    protected Point ballDir = new Point(1,1);

    protected int heigth = 100;
    protected int width = 500;

    public introView(Context context) {
        super(context);
        this.setBackgroundColor(Color.WHITE);
        this.ballSprite = this.getResources().getDrawable(R.drawable.send);
    }

    public introView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(Color.WHITE);
        this.ballSprite = this.getResources().getDrawable(R.drawable.send);
    }

    public introView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setBackgroundColor(Color.WHITE);
        this.ballSprite = this.getResources().getDrawable(R.drawable.send);
    }

    //logika odbijajacej sie pileczki
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        ballSprite.setBounds(ballPos.x,ballPos.y,ballPos.x + ballWidth, ballPos.y + ballHeigth);

        //zmiana kierunku
        if(ballPos.x > width)           ballDir.x = -1;
        else if(ballPos.x < 0)          ballDir.x = 1;
        if(ballPos.y > heigth)          ballDir.y = -1;
        else if(ballPos.y < 0)          ballDir.y = 1;

        //zmiana pozycji
        ballPos.x += speed*ballDir.x;
        ballPos.y += speed*ballDir.y;

        //rysowanie
        this.ballSprite.draw(canvas);
    }
}
