package com.huchcode.train.android.sample.chap1;

import com.huchcode.train.android.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ViewGorupLinearLayoutActivity extends Activity {
	private Button mBtn;
	private TextView mTextView;
	private ImageButton mImgButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.view_group_linear_layout);
		
		mImgButton = (ImageButton)findViewById(R.id.top_left_button);
		mImgButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		}); 
		
		mBtn = (Button)findViewById(R.id.top_right_button);
		mBtn.setText("Close");
		mBtn.setVisibility(View.VISIBLE);
		
		mTextView = (TextView)findViewById(R.id.top_middle_txt);
		mTextView.setText("Sample of Linearlayout");
		
	}

}
