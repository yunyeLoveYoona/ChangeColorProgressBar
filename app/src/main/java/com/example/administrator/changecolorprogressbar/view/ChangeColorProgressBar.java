package com.example.administrator.changecolorprogressbar.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ViewAnimator;

/**
 * Created by Administrator on 15-6-19.
 */
public class ChangeColorProgressBar extends View{
    private static final  int[] COLORS =new int[]{Color.RED,Color.BLUE,Color.YELLOW};
    private int angle = 10;
    private Paint paint;
    private RectF rectF;
    private static final int DIAMETER = 100;
    private int  start_angle = 180;
    private static final int STROKE_WIDTH = 5;
    private int color_option = 0;
    private ValueAnimator valueAnimator;
    private boolean isChangeColor =false;
    public ChangeColorProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setColor(COLORS[color_option]);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        rectF = new RectF();
        rectF.top = (getHeight() - DIAMETER)/2;
        rectF.bottom = (getHeight() - DIAMETER)/2 + DIAMETER;
        rectF.left = (getWidth() - DIAMETER)/2;
        rectF.right = (getWidth() - DIAMETER)/2 + DIAMETER;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(rectF,start_angle,angle,false,paint);
    }
    public void show(){
        valueAnimator =ValueAnimator.ofInt(10,330);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                if((int) animation.getAnimatedValue() < 300){
                    angle = (int) animation.getAnimatedValue();
                    isChangeColor = true;
                }else if(isChangeColor){
                    start_angle = start_angle+300>360?start_angle+300:start_angle+300-360;
                    color_option = color_option+1>=COLORS.length?0:color_option+1;
                    paint.setColor(COLORS[color_option]);
                    isChangeColor =false;
                }else if((int) animation.getAnimatedValue()<329){
                    angle = 30;
                    start_angle = start_angle +5 <360?start_angle+5:start_angle-5-360;
                }
                invalidate();
            }
        });
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(-1);
        valueAnimator.start();
    }
    public void dismiss(){
        valueAnimator.cancel();
        setVisibility(GONE);
    }
}
