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
    double angleSin = Math.sin(30*Math.PI/180);
    double angleCos = Math.cos(30*Math.PI/180);

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
        //Draw outside circle
        p.setColor(Color.argb(255,140,143,145));
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,p);
        //Draw inside circle
        p.setColor(Color.argb(255,28,30,32));
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() *2/ 5, p);
        //Draw a small ball point
        p.setColor(Color.argb(255,36,41,46));
        canvas.drawCircle((float)getCurPoint(getGrade())[0], (float)getCurPoint(getGrade())[1], getWidth()/40,p);
        //Draw grades
        p.setColor(Color.argb(255,140,143,145));
        p.setStrokeWidth((float)3);
        canvas.drawLine((float)(getWidth()/2 - angleCos * (getWidth()*2/5)),
                (float)(getWidth()/2 - angleSin * (getWidth()*2/5 )),
                (float)(getWidth()/2 - angleCos * (getWidth()*9/25)),
                (float)(getWidth()/2 - angleSin * (getWidth()*9/25)),
                p
                );
        canvas.drawLine((float)(getWidth()/2 - angleCos * (getWidth()*2/5)),
                (float)(getWidth()/2 + angleSin * (getWidth()*2/5 )),
                (float)(getWidth()/2 - angleCos * (getWidth()*9/25)),
                (float)(getWidth()/2 + angleSin * (getWidth()*9/25)),
                p
        );
        canvas.drawLine((float)(getWidth()/2),
                (float)(getWidth()/10),
                (float)(getWidth()/2),
                (float)(getWidth()*9/50),
                p
        );
        canvas.drawLine((float)(getWidth()/2 + angleCos * (getWidth()*2/5)),
                (float)(getWidth()/2 - angleSin * (getWidth()*2/5 )),
                (float)(getWidth()/2 + angleCos * (getWidth()*9/25)),
                (float)(getWidth()/2 - angleSin * (getWidth()*9/25)),
                p
        );
        canvas.drawLine((float)(getWidth()/2 + angleCos * (getWidth()*2/5)),
                (float)(getWidth()/2 + angleSin * (getWidth()*2/5 )),
                (float)(getWidth()/2 + angleCos * (getWidth()*9/25)),
                (float)(getWidth()/2 + angleSin * (getWidth()*9/25)),
                p
        );
        //Draw text
        p.setTextSize((float)24);
        canvas.drawText("1",(float)(getWidth()/2 - angleCos * (getWidth()*9/25)),(float)(getWidth()/2 - angleSin * (getWidth()*9/25)),p);
        canvas.drawText("2",(float)(getWidth()/2 - angleCos * (getWidth()*9/25)),(float)(getWidth()/2 + angleSin * (getWidth()*9/25)),p);
        canvas.drawText("4",(float)(getWidth()/2 + angleCos * (getWidth()*8/25)),(float)(getWidth()/2 - angleSin * (getWidth()*9/25)),p);
        canvas.drawText("5",(float)(getWidth()/2 + angleCos * (getWidth()*8/25)),(float)(getWidth()/2 + angleSin * (getWidth()*9/25)),p);
    }
    public double[] getCurPoint(int i){

        Log.d("qiqi","angleSin:"+ angleSin + " grade:" + i + " currentX:" + currentX + " currentY:" + currentY);
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
