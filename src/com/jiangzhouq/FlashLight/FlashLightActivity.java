package com.jiangzhouq.FlashLight;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.tclmid.app.FlshLight.R;


public class FlashLightActivity extends Activity implements OnClickListener, View.OnTouchListener{
	private FlashLightSurface mSurface;
    //Buttons
    private ImageButton mButton_constant;
    private ImageButton mButton_flicker;
    private ImageButton mButton_rhythm;
    private ImageButton mButton_slow;
    //State
    private final int mState_off = 0;
    private final int mState_constant = 1;
    private final int mState_flicker = 2;
    private final int mState_rhythm = 3;
    private final int mState_slow = 4;
    private int mState = mState_off;

    private RotateButton mRotate;
	private boolean isFlashOn = false;
	Handler mHandler = new  Handler(){
	};
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mSurface = new FlashLightSurface(this);
		RelativeLayout relative = (RelativeLayout) findViewById(R.id.main_layout);
		relative.addView(mSurface, 0);
        mButton_constant = (ImageButton) findViewById(R.id.constant);
        mButton_constant.setOnClickListener(this);
        mButton_flicker = (ImageButton) findViewById(R.id.flicker);
        mButton_flicker.setOnClickListener(this);
        mButton_rhythm = (ImageButton) findViewById(R.id.rhythm);
        mButton_rhythm.setOnClickListener(this);
        mButton_slow = (ImageButton) findViewById(R.id.slow);
        mButton_slow.setOnClickListener(this);
	}
    class FlickerRun implements Runnable{
        private int defaultRate = 500;
        @Override
        public void run() {
            mSurface.setFlashlightSwitch(!mSurface.isFlashLightOn());
            mHandler.postDelayed(flickerRun, defaultRate);
        }
        public void setRate(int rate){
            switch(rate){
                case 1:
                    defaultRate = 1000;
                    break;
                case 2:
                    defaultRate = 700;
                    break;
                case 3:
                    defaultRate = 500;
                    break;
                case 4:
                    defaultRate = 200;
                    break;
                case 5:
                    defaultRate = 50;
                    break;
            }
        }
    };
    FlickerRun flickerRun = new FlickerRun() {
	};
	private void startFlash(boolean flick){
		if(flick){
			mHandler.post(flickerRun);
//			mImageButton.setImageResource(R.drawable.switch_on);
		}else{
			mHandler.removeCallbacks(flickerRun);
			if(!mSurface.isFlashLightOn()){
				mSurface.setFlashlightSwitch(true);
//				mImageButton.setImageResource(R.drawable.switch_on);
			}
		}
	}
	
	private void stopFlash(){
		mHandler.removeCallbacks(flickerRun);
		if(mSurface.isFlashLightOn()){
			mSurface.setFlashlightSwitch(false);
		}
//		mImageButton.setImageResource(R.drawable.switch_off);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()){
		    case R.id.constant:
                if(mState == mState_constant){
                    recoverState(mState_constant);
                }else{
                    changeState(mState_constant);
                }
                break;
            case R.id.flicker:
                if(mState == mState_flicker){
                    recoverState(mState_flicker);
                }else{
                    changeState(mState_flicker);
                }
                break;
            case R.id.rhythm:
                if(mState == mState_rhythm){
                    recoverState(mState_rhythm);
                }else{
                    changeState(mState_rhythm);
                }
                break;
            case R.id.slow:
                if(mState == mState_slow){
                    recoverState(mState_slow);
                }else{
                    changeState(mState_slow);
                }
                break;
//			if(isFlashOn){
//            stopFlash();
//            isFlashOn = false;
//			}else{
//				startFlash(!mSwitch.isChecked());
//				isFlashOn = true;
//			}
		}
	}

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch(view.getId()){
            case R.id.rotate:
                mRotate.currentX = motionEvent.getX();
                mRotate.currentY = motionEvent.getY();
                mRotate.invalidate();
                adjustLightFlicker(mRotate.getAngle());
                break;
        }
        return true;
    }
    private void adjustLightFlicker(double light){
        if(light >= 0 && light < 90){
            flickerRun.setRate(5);
        }else if( light >= 90 && light < 150){
            flickerRun.setRate(4);
        }else if(light >= 150 || light < -150){
            flickerRun.setRate(3);
        }else if ( light >= -150 && light > -90){
            flickerRun.setRate(2);
        }else if(light > -90 && light < 0){
            flickerRun.setRate(1);
        }
    }
    private void changeState(int state){
        recoverState();
        RelativeLayout relative = (RelativeLayout)findViewById(R.id.container);
        switch(state){
            case mState_constant:
                startFlash(false);
                mButton_constant.setBackgroundResource(R.drawable.mode_button_left_pressed);
                
                relative.removeAllViews();
                relative.addView(View.inflate(this,R.layout.constant_detail,null));
                break;
            case mState_flicker:
                startFlash(true);
                mButton_flicker.setBackgroundResource(R.drawable.mode_button_middle_pressed);
                
                relative.removeAllViews();
                relative.addView(View.inflate(this,R.layout.flicker_and_slow_button,null));
                mRotate = (RotateButton) findViewById(R.id.rotate);
                mRotate.setOnTouchListener(this);
                break;
            case mState_rhythm:
                mButton_rhythm.setBackgroundResource(R.drawable.mode_button_middle_pressed);
                relative.removeAllViews();
                relative.addView(View.inflate(this,R.layout.rhythm_table,null));
                break;
            case mState_slow:
                mButton_slow.setBackgroundResource(R.drawable.mode_button_right_pressed);
                relative.removeAllViews();
                relative.addView(View.inflate(this,R.layout.flicker_and_slow_button,null));
                mRotate = (RotateButton) findViewById(R.id.rotate);
                mRotate.setOnTouchListener(this);
                break;
        }
        mState = state;
    }
    private void recoverState(int nowState){
        switch(nowState){
            case mState_constant:
                mButton_constant.setBackgroundResource(R.drawable.mode_button_left);
                break;
            case mState_flicker:
                mButton_flicker.setBackgroundResource(R.drawable.mode_button_middle);
                break;
            case mState_rhythm:
                mButton_rhythm.setBackgroundResource(R.drawable.mode_button_middle);
                break;
            case mState_slow:
                mButton_slow.setBackgroundResource(R.drawable.mode_button_right);
                break;
        }
        mState = mState_off;
        stopFlash();
    }
	private void recoverState(){
        switch(mState){
            case mState_constant:
                mButton_constant.setBackgroundResource(R.drawable.mode_button_left);
                break;
            case mState_flicker:
                mButton_flicker.setBackgroundResource(R.drawable.mode_button_middle);
                break;
            case mState_rhythm:
                mButton_rhythm.setBackgroundResource(R.drawable.mode_button_middle);
                break;
            case mState_slow:
                mButton_slow.setBackgroundResource(R.drawable.mode_button_right);
                break;
        }
        mState = mState_off;
        stopFlash();
    }
	@Override
	protected void onDestroy() {
		super.onDestroy();
        stopFlash();
        isFlashOn = false;
		mSurface.releaseCamera();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
//		stopFlash();
//		isFlashOn = false;
	}
}
