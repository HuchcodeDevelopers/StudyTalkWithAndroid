package com.huchcode.train.android.sample.chap1;

import com.huchcode.train.android.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

public class ViewSampleActivity extends Activity {
	
	CheckBox mCbox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_sample_layout);
		
		mCbox = (CheckBox)findViewById(R.id.testCheckBox);
		mCbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					Toast.makeText(ViewSampleActivity.this, "Checked", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(ViewSampleActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
				
			}
		});
	}

}
