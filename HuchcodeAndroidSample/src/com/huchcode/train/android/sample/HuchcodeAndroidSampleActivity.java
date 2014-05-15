package com.huchcode.train.android.sample;

import com.huchcode.train.android.sample.chap1.ImplicitActionSamplesActivity;
import com.huchcode.train.android.sample.chap1.MyForResultActivity;
import com.huchcode.train.android.sample.chap1.ViewGorupLinearLayoutActivity;
import com.huchcode.train.android.sample.chap2.BusinessCardActivity;
import com.huchcode.train.android.sample.chap2.ContentProviderBusinessCardListActivity;
import com.huchcode.train.android.sample.chap2.LocalDbBusinessCardActivity;
import com.huchcode.train.android.sample.chap2.SimpleListActivity;
import com.huchcode.train.android.sample.chap2.TwitListActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HuchcodeAndroidSampleActivity extends Activity {
	private Button mBtn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mBtn = (Button)findViewById(R.id.sample1);
        mBtn.setOnClickListener(mClichListener);
        
        mBtn = (Button)findViewById(R.id.sample2);
        mBtn.setOnClickListener(mClichListener);
        
        mBtn = (Button)findViewById(R.id.sample3);
        mBtn.setOnClickListener(mClichListener);
        
        mBtn = (Button)findViewById(R.id.sample4);
        mBtn.setOnClickListener(mClichListener);
        
        mBtn = (Button)findViewById(R.id.sample5);
        mBtn.setOnClickListener(mClichListener);
        
        mBtn = (Button)findViewById(R.id.sample6);
        mBtn.setOnClickListener(mClichListener);
        
        mBtn = (Button)findViewById(R.id.sample7);
        mBtn.setOnClickListener(mClichListener);
        
        mBtn = (Button)findViewById(R.id.sample8);
        mBtn.setOnClickListener(mClichListener);

        mBtn = (Button)findViewById(R.id.sample9);
        mBtn.setOnClickListener(mClichListener);
        
        mBtn = (Button)findViewById(R.id.sample10);
        mBtn.setOnClickListener(mClichListener);

    }
    
    
    
    View.OnClickListener mClichListener = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent intent = null;
			switch (v.getId()) {
			case R.id.sample1:
				intent = new Intent(HuchcodeAndroidSampleActivity.this, ViewGorupLinearLayoutActivity.class);
				startActivity(intent);
				break;
			case R.id.sample2:
				intent = new Intent(HuchcodeAndroidSampleActivity.this, ViewGorupLinearLayoutActivity.class);
				startActivity(intent);
				break;
			case R.id.sample3:
				intent = new Intent("com.huchcode.train.android.sample.TEST_ACTION");
				startActivity(intent);
				break;
			case R.id.sample4:
				intent = new Intent(v.getContext(), ImplicitActionSamplesActivity.class);
				startActivity(intent);
				break;
			case R.id.sample5:
				intent = new Intent(v.getContext(), MyForResultActivity.class);
				startActivity(intent);
				break;
			case R.id.sample6:
				intent = new Intent(v.getContext(), SimpleListActivity.class);
				startActivity(intent);
				break;
				
			case R.id.sample7:
				intent = new Intent(v.getContext(), TwitListActivity.class);
				startActivity(intent);
				break;

			case R.id.sample8:
				intent = new Intent(v.getContext(), BusinessCardActivity.class);
				startActivity(intent);
				break;

			case R.id.sample9:
				intent = new Intent(v.getContext(), LocalDbBusinessCardActivity.class);
				startActivity(intent);
				break;

			case R.id.sample10:
				intent = new Intent(v.getContext(), ContentProviderBusinessCardListActivity.class);
				startActivity(intent);
				break;

			default:
				break;
			}
		}
	};
}