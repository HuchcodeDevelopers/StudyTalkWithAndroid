package com.huchcode.train.android.sample.chap1;

import com.huchcode.train.android.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyForResultActivity extends Activity {
	
	Button mBtn;
	TextView mTxtView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_result_activity);
		
		mBtn = (Button)findViewById(R.id.button);
		mBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), OtherForResultActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		mTxtView = (TextView)findViewById(R.id.return_value);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			switch (requestCode) {
			case 1:
				String value = data.getStringExtra("returnValue");
				mTxtView.setText(value);
				break;

			default:
				break;
			}
		}
	}

}
