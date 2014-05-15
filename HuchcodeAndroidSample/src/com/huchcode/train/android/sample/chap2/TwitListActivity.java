package com.huchcode.train.android.sample.chap2;

import java.util.ArrayList;

import com.huchcode.train.android.sample.R;
import com.huchcode.train.android.sample.chap2.domain.Twit;
import com.huchcode.train.android.sample.chap2.provider.TwitProvider;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class TwitListActivity extends Activity {
	private final static int MENU_ADD = 1;

	private ListView listView;
	private ArrayList<Twit> list;
	private TwitAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twit_list_layout);

		list = TwitProvider.getInstance().findTwits();

		listView = (ListView) findViewById(R.id.listview);

		adapter = new TwitAdapter(getBaseContext(),
				R.layout.twit_list_layout_row, list);
		listView.setAdapter(adapter);
	}

	/**
	 * Create Menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, MENU_ADD, 0, "Add").setIcon(android.R.drawable.ic_menu_add);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case MENU_ADD:
			Toast.makeText(getBaseContext(), "Chosen the add button", Toast.LENGTH_SHORT).show();
			break;

		default:
			break;
		}
		
		return true;
	}

}
