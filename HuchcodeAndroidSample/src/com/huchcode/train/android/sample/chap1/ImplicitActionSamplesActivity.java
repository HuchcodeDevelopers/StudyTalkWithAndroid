package com.huchcode.train.android.sample.chap1;

import com.huchcode.train.android.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ImplicitActionSamplesActivity extends Activity {
	
	private Button mBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.implicit_samples);
		
		mBtn = (Button)findViewById(R.id.btn_call);
		mBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:010-2019-1470"));
				startActivity(intent);
			}
		});
		
		mBtn = (Button)findViewById(R.id.btn_mail);
		mBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:dohkim@nextree.co.kr"));
				startActivity(intent);
			}
		});
	}

}
