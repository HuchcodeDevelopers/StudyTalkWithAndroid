package com.huchcode.train.android.sample.chap1;

import com.huchcode.train.android.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OtherForResultActivity extends Activity {
	
	Button mBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_activity);
		
		mBtn = (Button)findViewById(R.id.returnBtn);
		mBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra("returnValue", "This is the return value from OtherForResultActivity");
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

}
