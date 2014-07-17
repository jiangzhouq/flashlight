package com.jiangzhouq.FlashLight;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
	private RelativeLayout bottom_relative;
	private ImageView top_image;
	
	private int[] rhythm_buttons = new int[]{R.id.flicker_grade_1,
			R.id.flicker_grade_2,
			R.id.flicker_grade_3,
			R.id.flicker_grade_4,
			R.id.flicker_grade_5};
	
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
        bottom_relative = (RelativeLayout)findViewById(R.id.container);
        top_image = (ImageView)findViewById(R.id.top_image);
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
                    defaultRate = 1500;
                    break;
                case 2:
                    defaultRate = 1000;
                    break;
                case 3:
                    defaultRate = 600;
                    break;
                case 4:
                    defaultRate = 300;
                    break;
                case 5:
                    defaultRate = 150;
                    break;
            }
        }
    };
    private int defaultRate[] = new int[]{800,200,800,200,300,200,300,200,300,1000};
    private int rate_num = 0;
    
    class RhythmRun implements Runnable{
        
        @Override
        public void run() {
            mSurface.setFlashlightSwitch(!mSurface.isFlashLightOn());
            Log.d("qiqi", "postdelay:" + rate_num + " " + defaultRate[rate_num]);
        	mHandler.postDelayed(rhythmRun, defaultRate[rate_num]);
        	rate_num = rate_num + 1;
        	if(rate_num >= defaultRate.length){
        		rate_num = 0;
        	}
        }
        public void setRate(int rate){
            switch(rate){
                case 1:
                    defaultRate = new int[]{300,300,150,150,150};
                    break;
                case 2:
                    defaultRate = new int[]{300,300,150,150,150};
                    break;
                case 3:
                    defaultRate = new int[]{300,300,150,150,150};
                    break;
                case 4:
                    defaultRate = new int[]{300,300,150,150,150};
                    break;
                case 5:
                    defaultRate = new int[]{300,300,150,150,150};
                    break;
            }
        }
    };
    RhythmRun rhythmRun = new RhythmRun() {
    };
    FlickerRun flickerRun = new FlickerRun() {
	};
	private void startFlash(int mode){
		//0 for constant
		//1 for flicker
		//2 for rhythm 
		//3 for slow
		switch(mode){
		case 0:
			mHandler.removeCallbacks(flickerRun);
			if(!mSurface.isFlashLightOn()){
				mSurface.setFlashlightSwitch(true);
//				mImageButton.setImageResource(R.drawable.switch_on);
			}
			break;
		case 1:
			mHandler.post(flickerRun);
			break;
		case 2:
			mHandler.post(rhythmRun);
			break;
		case 3:
			break;
		}
	}
	
	private void stopFlash(){
		mHandler.removeCallbacks(flickerRun);
		mHandler.removeCallbacks(rhythmRun);
		mSurface.setFlashlightSwitch(false);
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
            case R.id.flicker_grade_1:
            	flickerRun.setRate(1);
            	recoverFlickerButtons();
            	((RelativeLayout) findViewById(R.id.flicker_grade_1)).setBackgroundResource(R.drawable.mode_button_rhythm_pressed);
            	((TextView)((RelativeLayout) findViewById(R.id.flicker_grade_1)).getChildAt(0)).setTextColor(getResources().getColor(R.color.my_blue));
            	break;
            case R.id.flicker_grade_2:
            	flickerRun.setRate(2);
            	recoverFlickerButtons();
            	((RelativeLayout) findViewById(R.id.flicker_grade_2)).setBackgroundResource(R.drawable.mode_button_rhythm_pressed);
            	((TextView)((RelativeLayout) findViewById(R.id.flicker_grade_2)).getChildAt(0)).setTextColor(getResources().getColor(R.color.my_blue));
            	break;
            case R.id.flicker_grade_3:
            	flickerRun.setRate(3);
            	recoverFlickerButtons();
            	((RelativeLayout) findViewById(R.id.flicker_grade_3)).setBackgroundResource(R.drawable.mode_button_rhythm_pressed);
            	((TextView)((RelativeLayout) findViewById(R.id.flicker_grade_3)).getChildAt(0)).setTextColor(getResources().getColor(R.color.my_blue));
            	break;
            case R.id.flicker_grade_4:
            	flickerRun.setRate(4);
            	recoverFlickerButtons();
            	((RelativeLayout) findViewById(R.id.flicker_grade_4)).setBackgroundResource(R.drawable.mode_button_rhythm_pressed);
            	((TextView)((RelativeLayout) findViewById(R.id.flicker_grade_4)).getChildAt(0)).setTextColor(getResources().getColor(R.color.my_blue));
            	break;
            case R.id.flicker_grade_5:
            	flickerRun.setRate(5);
            	recoverFlickerButtons();
            	((RelativeLayout) findViewById(R.id.flicker_grade_5)).setBackgroundResource(R.drawable.mode_button_rhythm_pressed);
            	((TextView)((RelativeLayout) findViewById(R.id.flicker_grade_5)).getChildAt(0)).setTextColor(getResources().getColor(R.color.my_blue));
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
	private void recoverFlickerButtons(){
		for(int i : rhythm_buttons){
    		RelativeLayout relative = (RelativeLayout) findViewById(i);
    		relative.setBackgroundResource(R.drawable.mode_button_rhythm);
    		TextView text = (TextView) relative.getChildAt(0);
    		text.setTextColor(getResources().getColor(R.color.my_dark));
    	}
	}
    private void changeState(int state){
        recoverState();
        
        switch(state){
            case mState_constant:
                startFlash(0);
                mButton_constant.setBackgroundResource(R.drawable.mode_button_left_pressed);
                bottom_relative.removeAllViews();
                View.inflate(this,R.layout.constant_detail,bottom_relative);
                top_image.setImageResource(R.drawable.top_bg_2);
                ImageButton mConstantsButton = (ImageButton) findViewById(R.id.constant);
                mConstantsButton.setImageResource(R.drawable.bt_bg_2);
                break;
            case mState_flicker:
                startFlash(1);
                mButton_flicker.setBackgroundResource(R.drawable.mode_button_middle_pressed);
                bottom_relative.removeAllViews();
                View.inflate(this,R.layout.flicker_table,bottom_relative);
                top_image.setImageResource(R.drawable.top_bg_3);
                ImageButton mFlickerButton = (ImageButton) findViewById(R.id.flicker);
                mFlickerButton.setImageResource(R.drawable.bt_bg_4);
                setFlickerListener();
                break;
            case mState_rhythm:
            	startFlash(2);
                mButton_rhythm.setBackgroundResource(R.drawable.mode_button_middle_pressed);
                bottom_relative.removeAllViews();
                View.inflate(this,R.layout.rhythm_table,bottom_relative);
                top_image.setImageResource(R.drawable.top_bg_4);
                ImageButton mRhythmButton = (ImageButton) findViewById(R.id.rhythm);
                mRhythmButton.setImageResource(R.drawable.bt_bg_6);
                break;
            case mState_slow:
                mButton_slow.setBackgroundResource(R.drawable.mode_button_right_pressed);
                bottom_relative.removeAllViews();
                View.inflate(this,R.layout.flicker_table,bottom_relative);
                top_image.setImageResource(R.drawable.top_bg_5);
                ImageButton mSlowButton = (ImageButton) findViewById(R.id.slow);
                mSlowButton.setImageResource(R.drawable.bt_bg_8);
                break;
        }
        mState = state;
    }
    
    private void recoverState(int nowState){
    	bottom_relative.removeAllViews();
    	View.inflate(this,R.layout.constant_detail,bottom_relative);
        top_image.setImageResource(R.drawable.top_bg_1);
        switch(nowState){
            case mState_constant:
                mButton_constant.setBackgroundResource(R.drawable.mode_button_left);
                ImageButton mConstantsButton = (ImageButton) findViewById(R.id.constant);
                mConstantsButton.setImageResource(R.drawable.bt_bg_1);
                break;
            case mState_flicker:
                mButton_flicker.setBackgroundResource(R.drawable.mode_button_middle);
                ImageButton mFlickerButton = (ImageButton) findViewById(R.id.flicker);
                mFlickerButton.setImageResource(R.drawable.bt_bg_3);
                break;
            case mState_rhythm:
                mButton_rhythm.setBackgroundResource(R.drawable.mode_button_middle);
                ImageButton mRhythmButton = (ImageButton) findViewById(R.id.rhythm);
                mRhythmButton.setImageResource(R.drawable.bt_bg_5);
                break;
            case mState_slow:
                mButton_slow.setBackgroundResource(R.drawable.mode_button_right);
                ImageButton mSlowButton = (ImageButton) findViewById(R.id.slow);
                mSlowButton.setImageResource(R.drawable.bt_bg_7);
                break;
        }
        mState = mState_off;
        stopFlash();
    }
	private void recoverState(){
		 ImageButton mConstantsButton = (ImageButton) findViewById(R.id.constant);
         mConstantsButton.setImageResource(R.drawable.bt_bg_1);
         ImageButton mFlickerButton = (ImageButton) findViewById(R.id.flicker);
         mFlickerButton.setImageResource(R.drawable.bt_bg_3);
         ImageButton mRhythmButton = (ImageButton) findViewById(R.id.rhythm);
         mRhythmButton.setImageResource(R.drawable.bt_bg_5);
         ImageButton mSlowButton = (ImageButton) findViewById(R.id.slow);
         mSlowButton.setImageResource(R.drawable.bt_bg_7);
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
	
	private void setFlickerListener(){
		RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.flicker_grade_1);
		layout1.setOnClickListener(this);
		RelativeLayout layout2 = (RelativeLayout) findViewById(R.id.flicker_grade_2);
		layout2.setOnClickListener(this);
		RelativeLayout layout3 = (RelativeLayout) findViewById(R.id.flicker_grade_3);
		layout3.setOnClickListener(this);
		RelativeLayout layout4 = (RelativeLayout) findViewById(R.id.flicker_grade_4);
		layout4.setOnClickListener(this);
		RelativeLayout layout5 = (RelativeLayout) findViewById(R.id.flicker_grade_5);
		layout5.setOnClickListener(this);
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
