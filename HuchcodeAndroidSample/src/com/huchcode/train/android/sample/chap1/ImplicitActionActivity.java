package com.huchcode.train.android.sample.chap1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ImplicitActionActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("Activity example using implicit Intent");
		setContentView(tv);
	}

}
