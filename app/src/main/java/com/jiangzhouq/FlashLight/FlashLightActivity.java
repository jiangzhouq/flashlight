package com.jiangzhouq.FlashLight;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import com.tclmid.app.FlshLight.R;


public class FlashLightActivity extends Activity implements OnClickListener{
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
	Runnable run = new Runnable() {
		@Override
		public void run() {
			mSurface.setFlashlightSwitch(!mSurface.isFlashLightOn());
			mHandler.postDelayed(run, 500);
		}
	};
	private void startFlash(boolean flick){
		if(flick){
			mHandler.post(run);
//			mImageButton.setImageResource(R.drawable.switch_on);
		}else{
			mHandler.removeCallbacks(run);
			if(!mSurface.isFlashLightOn()){
				mSurface.setFlashlightSwitch(true);
//				mImageButton.setImageResource(R.drawable.switch_on);
			}
		}
	}
	
	private void stopFlash(){
		mHandler.removeCallbacks(run);
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
    private void changeState(int state){
        recoverState();
        switch(state){
            case mState_constant:
                startFlash(false);
                mButton_constant.setBackgroundResource(R.drawable.mode_button_left_pressed);
                break;
            case mState_flicker:
                startFlash(true);
                mButton_flicker.setBackgroundResource(R.drawable.mode_button_middle_pressed);
                break;
            case mState_rhythm:
                mButton_rhythm.setBackgroundResource(R.drawable.mode_button_middle_pressed);
                break;
            case mState_slow:
                mButton_slow.setBackgroundResource(R.drawable.mode_button_right_pressed);
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
