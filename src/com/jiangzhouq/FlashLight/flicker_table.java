package com.jiangzhouq.FlashLight;

import com.tclmid.app.FlshLight.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.view.View;
import android.view.View.OnClickListener;
public class flicker_table extends TableLayout implements OnClickListener{
	public flicker_table(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public flicker_table(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.flicker_grade_1);
		layout1.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.flicker_grade_1:
			break;
		}
	}
}
