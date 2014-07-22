package com.jiangzhouq.FlashLight;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
	
	private int[] flicker_buttons = new int[]{
			R.id.flicker_grade_1,
			R.id.flicker_grade_2,
			R.id.flicker_grade_3,
			R.id.flicker_grade_4,
			R.id.flicker_grade_5};
	private int[] rhythm_buttons = new int[]{
			R.id.rhythm_grade_1,
			R.id.rhythm_grade_2,
			R.id.rhythm_grade_3,
			R.id.rhythm_grade_4,
			R.id.rhythm_grade_5,
			R.id.rhythm_grade_6,
			R.id.rhythm_grade_7,
			R.id.rhythm_grade_8,
			R.id.rhythm_grade_9,
			R.id.rhythm_grade_10,
			R.id.rhythm_grade_11,
			R.id.rhythm_grade_12
	};
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
    private int defaultRate[] = new int[]{1000,200,1000,200,400,200,400,200,1000,200};
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
            	//music1
                case 1:
                    defaultRate = new int[]{100,100,100,1000,100,1000};
                    break;
                case 2:
                    defaultRate = new int[]{100,100,100,800,100,800};
                    break;
                case 3:
                    defaultRate = new int[]{100,100,100,500,100,500};
                    break;
                case 4:
                    defaultRate = new int[]{100,100,100,300,100,300};
                    break;
                //music2
                case 5:
                    defaultRate = new int[]{100,1000,100,1000,100,300,100,300,100,1000};
                    break;
                case 6:
                    defaultRate = new int[]{100,800,100,800,100,200,100,200,100,800};
                    break;
                case 7:
                    defaultRate = new int[]{100,500,100,500,100,100,100,100,100,500};
                    break;
                case 8:
                    defaultRate = new int[]{100,300,100,300,100,100,100,100,100,300};
                    break;
                //music3
                case 9:
                    defaultRate = new int[]{100,200,100,100,100,100,100,200,100,200,100,100,100,100,100,100,100,100,100,200};
                    break;
                case 10:
                    defaultRate = new int[]{800,200,800,200,300,200,300,200,800,200};
                    break;
                case 11:
                    defaultRate = new int[]{600,150,600,150,300,150,300,150,600,150};
                    break;
                case 12:
                    defaultRate = new int[]{400,150,400,150,200,150,200,150,400,150};
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
			mHandler.postDelayed(flickerRun,500);
			break;
		case 2:
			mHandler.postDelayed(rhythmRun,500);
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
            	setFlickerRate(0);
            	break;
            case R.id.flicker_grade_2:
            	setFlickerRate(1);
            	break;
            case R.id.flicker_grade_3:
            	setFlickerRate(2);
            	break;
            case R.id.flicker_grade_4:
            	setFlickerRate(3);
            	break;
            case R.id.flicker_grade_5:
            	setFlickerRate(4);
            	break;
            case R.id.rhythm_grade_1:
            	setRhythmRate(0);
            	break;
            case R.id.rhythm_grade_2:
            	setRhythmRate(1);
            	break;
            case R.id.rhythm_grade_3:
            	setRhythmRate(2);
            	break;
            case R.id.rhythm_grade_4:
            	setRhythmRate(3);
            	break;
            case R.id.rhythm_grade_5:
            	setRhythmRate(4);
            	break;
            case R.id.rhythm_grade_6:
            	setRhythmRate(5);
            	break;
            case R.id.rhythm_grade_7:
            	setRhythmRate(6);
            	break;
            case R.id.rhythm_grade_8:
            	setRhythmRate(7);
            	break;
            case R.id.rhythm_grade_9:
            	setRhythmRate(8);
            	break;
            case R.id.rhythm_grade_10:
            	setRhythmRate(9);
            	break;
            case R.id.rhythm_grade_11:
            	setRhythmRate(10);
            	break;
            case R.id.rhythm_grade_12:
            	setRhythmRate(11);
            	break;
            	
		}
	}
	private void setFlickerRate(int rate){
		stopFlash();
		flickerRun.setRate(rate + 1);
		recoverFlickerButtons();
    	((RelativeLayout) findViewById(flicker_buttons[rate])).setBackgroundResource(R.drawable.mode_button_rhythm_pressed);
    	((TextView)((RelativeLayout) findViewById(flicker_buttons[rate])).getChildAt(0)).setTextColor(getResources().getColor(R.color.my_blue));
		SharedPreferences sharedPreferences = getSharedPreferences("qjizho", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt("flicker_grade", rate);
		editor.commit();
		startFlash(1);
	}
	private void setRhythmRate(int rate){
		stopFlash();
		rhythmRun.setRate(rate + 1);
		recoverRhythmButtons();
		((RelativeLayout) findViewById(rhythm_buttons[rate])).setBackgroundResource(R.drawable.mode_button_rhythm_pressed);
		((TextView)((RelativeLayout) findViewById(rhythm_buttons[rate])).getChildAt(0)).setTextColor(getResources().getColor(R.color.my_blue));
		SharedPreferences sharedPreferences = getSharedPreferences("qjizho", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();
		editor.putInt("rhythm_grade", rate);
		editor.commit();
		startFlash(2);
	}
	private void recoverFlickerButtons(){
		for(int i : flicker_buttons){
    		RelativeLayout relative = (RelativeLayout) findViewById(i);
    		relative.setBackgroundResource(R.drawable.mode_button_rhythm);
    		TextView text = (TextView) relative.getChildAt(0);
    		text.setTextColor(getResources().getColor(R.color.my_dark));
    	}
	}
	private void recoverRhythmButtons(){
		for(int i : rhythm_buttons){
			RelativeLayout relative = (RelativeLayout)findViewById(i);
			relative.setBackgroundResource(R.drawable.mode_button_rhythm);
			TextView text = (TextView) relative.getChildAt(0);
			text.setTextColor(getResources().getColor(R.color.my_dark));
 		}
	}
    private void changeState(int state){
        recoverState();
        SharedPreferences sharedPreferences = getSharedPreferences("qjizho", Context.MODE_PRIVATE);
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
                mButton_flicker.setBackgroundResource(R.drawable.mode_button_middle_pressed);
                bottom_relative.removeAllViews();
                View.inflate(this,R.layout.flicker_table,bottom_relative);
                top_image.setImageResource(R.drawable.top_bg_3);
                ImageButton mFlickerButton = (ImageButton) findViewById(R.id.flicker);
                mFlickerButton.setImageResource(R.drawable.bt_bg_4);
                setFlickerListener();
                int flicker_grade = sharedPreferences.getInt("flicker_grade", 0);
                setFlickerRate(flicker_grade);
                break;
            case mState_rhythm:
                mButton_rhythm.setBackgroundResource(R.drawable.mode_button_middle_pressed);
                bottom_relative.removeAllViews();
                View.inflate(this,R.layout.rhythm_table,bottom_relative);
                top_image.setImageResource(R.drawable.top_bg_4);
                ImageButton mRhythmButton = (ImageButton) findViewById(R.id.rhythm);
                mRhythmButton.setImageResource(R.drawable.bt_bg_6);
                setRhythmListener();
                int rhythm_grade = sharedPreferences.getInt("rhythm_grade", 0);
                setRhythmRate(rhythm_grade);
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
	private void setRhythmListener(){
		RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.rhythm_grade_1);
		layout1.setOnClickListener(this);
		RelativeLayout layout2 = (RelativeLayout) findViewById(R.id.rhythm_grade_2);
		layout2.setOnClickListener(this);
		RelativeLayout layout3 = (RelativeLayout) findViewById(R.id.rhythm_grade_3);
		layout3.setOnClickListener(this);
		RelativeLayout layout4 = (RelativeLayout) findViewById(R.id.rhythm_grade_4);
		layout4.setOnClickListener(this);
		RelativeLayout layout5 = (RelativeLayout) findViewById(R.id.rhythm_grade_5);
		layout5.setOnClickListener(this);
		RelativeLayout layout6 = (RelativeLayout) findViewById(R.id.rhythm_grade_6);
		layout6.setOnClickListener(this);
		RelativeLayout layout7 = (RelativeLayout) findViewById(R.id.rhythm_grade_7);
		layout7.setOnClickListener(this);
		RelativeLayout layout8 = (RelativeLayout) findViewById(R.id.rhythm_grade_8);
		layout8.setOnClickListener(this);
		RelativeLayout layout9 = (RelativeLayout) findViewById(R.id.rhythm_grade_9);
		layout9.setOnClickListener(this);
		RelativeLayout layout10 = (RelativeLayout) findViewById(R.id.rhythm_grade_10);
		layout10.setOnClickListener(this);
		RelativeLayout layout11 = (RelativeLayout) findViewById(R.id.rhythm_grade_11);
		layout11.setOnClickListener(this);
		RelativeLayout layout12 = (RelativeLayout) findViewById(R.id.rhythm_grade_12);
		layout12.setOnClickListener(this);
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
