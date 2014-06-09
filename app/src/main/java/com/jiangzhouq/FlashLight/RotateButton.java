package com.jiangzhouq.FlashLight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by MyPC on 2014/5/27.
 */
public class RotateButton extends View {
    public final static int TOTAL_ANGLE_180 = 0;
    public final static int TOTAL_ANGLE_240 = 1;
    public final static int TOTAL_ANGLE_300 = 2;
    private int mTotalAngle = 180;
    private int mGradeCount = 5;
    public float currentX = 40;
    public float currentY = 40;
    private int oldGrade = 0;
    private boolean forRunOnce = true;
    private int newGrade;
    public RotateButton(Context context) {
        super(context);
    }
    public RotateButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setTotalAngle(int q){
        switch(q){
            case 0:
                mTotalAngle = 180;
                break;
            case 1:
                mTotalAngle = 240;
                break;
            case 2:
                mTotalAngle = 300;
                break;
            default:
                mTotalAngle = 180;
                break;
        }
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    Runnable drawRun = new Runnable() {
        @Override
        public void run() {
            for(int i = 0 ; i < 10 ; i ++){
                invalidate();
                Log.d("qiqi", " invalidate:" + i);
                if(i == 9 ){
                    oldGrade = newGrade;
                    forRunOnce = true;
                }
            }
            mHandler.postDelayed(drawRun,1000);
        }
    };
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint p = new Paint();
        p.setColor(Color.argb(255,140,143,145));
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,p);
        p.setColor(Color.argb(255,28,30,32));
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() *2/ 5, p);
        p.setColor(Color.argb(255,36,41,46));
        newGrade = getGrade();
        if(newGrade == oldGrade){
            canvas.drawCircle((float)getCurPoint(newGrade)[0], (float)getCurPoint(newGrade)[1], getWidth()/40,p);
        }else{
            float x = (float)getCurPoint(newGrade)[0] + ((float)getCurPoint(newGrade)[0] - (float)getCurPoint(oldGrade)[0])/10;
            float y = (float)getCurPoint(newGrade)[1] + ((float)getCurPoint(newGrade)[1] - (float)getCurPoint(oldGrade)[1])/10;
            Log.d("qiqi", " draw x:" + x + " y:" + y);
            canvas.drawCircle(x, y, getWidth() / 40, p);
            if(forRunOnce) {
                mHandler.post(drawRun);
                forRunOnce = false;
            }
        }
    }
    public double[] getCurPoint(int i){
        double angleSin = Math.sin(30*Math.PI/180);
        double angleCos = Math.cos(30*Math.PI/180);
        double[] point = new double[2];
        switch(i){
            case 2:
                point[0] = getWidth()/2 - angleCos * (getWidth()*7/16);
                point[1] = getWidth()/2 + angleSin * (getWidth()*7/16);
                break;
            case 3:
                point[0] = getWidth()/2;
                point[1] = getWidth()/16;
                break;
            case 4:
                point[0] = getWidth()/2 + angleCos * (getWidth()*7/16);
                point[1] = getWidth()/2 - angleSin * (getWidth()*7/16);
                break;
            case 5:
                point[0] = getWidth()/2 + angleCos * (getWidth()*7/16);
                point[1] = getWidth()/2 + angleSin * (getWidth()*7/16);
                break;
            //case 1
            default:
                point[0] = getWidth()/2 - angleCos * (getWidth()*7/16);
                point[1] = getWidth()/2 - angleSin * (getWidth()*7/16 );
                break;
        }
        return point;
    }
    public int getGrade(){
        int mPerAngle = 300/(mGradeCount - 1);
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
