package com.jiangzhouq.FlashLight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by MyPC on 2014/5/27.
 */
public class RotateButton extends View {
    public float currentX = 40;
    public float currentY = 40;
    public RotateButton(Context context) {
        super(context);
    }
    public RotateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.argb(255,242,242,242));
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,p);
        p.setColor(Color.argb(255,255,254,238));
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 20, p);
        p.setColor(Color.argb(255,170,0,4));
        canvas.drawCircle((float)getCurPoint(getGrade())[0], (float)getCurPoint(getGrade())[1], 5,p);
    }
    public double[] getCurPoint(int i){
        double angleSin = Math.sin(30*Math.PI/180);
        double angleCos = Math.cos(30*Math.PI/180);
        Log.d("qiqi","angleSin:"+ angleSin + " grade:" + i + " currentX:" + currentX + " currentY:" + currentY);
        double[] point = new double[2];
        switch(i){
            case 2:
                point[0] = getWidth()/2 - angleCos * (getWidth()/2 - 10);
                point[1] = getWidth()/2 + angleSin * (getWidth()/2 - 10);
                break;
            case 3:
                point[0] = getWidth()/2;
                point[1] = 10;
                break;
            case 4:
                point[0] = getWidth()/2 + angleCos * (getWidth()/2 - 10);
                point[1] = getWidth()/2 - angleSin * (getWidth()/2 - 10);
                break;
            case 5:
                point[0] = getWidth()/2 + angleCos * (getWidth()/2 - 10);
                point[1] = getWidth()/2 + angleSin * (getWidth()/2 - 10);
                break;
            //case 1
            default:
                point[0] = getWidth()/2 - angleCos * (getWidth()/2 - 10);
                point[1] = getWidth()/2 - angleSin * (getWidth()/2 - 10);
                break;
        }
        return point;
    }
    public int getGrade(){
        double a = Math.atan2(currentX -getWidth()/2, currentY - getHeight()/2);
        double angle = 180*a /Math.PI;
        if(angle >= 0 && angle < 90){
           return 5;
        }else if( angle >= 90 && angle < 150){
            return 4;
        }else if(angle >= 150 || angle < -150){
            return 3;
        }else if ( angle >= -150 && angle > -90){
            return 2;
        }else if(angle > -90 && angle < 0){
            return 1;
        }
        return 1;
    }
    public double getAngle(){
        double a = Math.atan2(currentX -getWidth()/2, currentY - getHeight()/2);
        return 180*a /Math.PI;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
