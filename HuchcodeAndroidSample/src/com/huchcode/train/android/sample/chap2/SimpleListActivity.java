package com.huchcode.train.android.sample.chap2;

import java.util.ArrayList;

import com.huchcode.train.android.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SimpleListActivity extends Activity {
	private ListView listView;
	private EditText etName;
	private ArrayAdapter<String> adapter;
	private Button btnAdd;
	
	private ArrayList<String> list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_list_layout);
		
		listView = (ListView) findViewById(R.id.listview);
		adapter = new ArrayAdapter<String>(//
				getBaseContext(), //
				android.R.layout.simple_list_item_1,//
				findNames()
				);
		listView.setAdapter(adapter);
		
		etName = (EditText) findViewById(R.id.etName);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		btnAdd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = etName.getText().toString();
				list.add(name);
				adapter.notifyDataSetChanged();
			}
		});
		
	}

	private ArrayList<String> findNames() {
		list =new ArrayList<String>();
		list.add("I love Mark!!!");
		list.add("I like Jerick!!");
		list.add("I respect Ronald!");
		
		return list;
	}
	
	

}
