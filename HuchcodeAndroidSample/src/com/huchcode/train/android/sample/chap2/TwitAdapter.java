package com.huchcode.train.android.sample.chap2;

import java.util.ArrayList;

import com.huchcode.train.android.sample.R;
import com.huchcode.train.android.sample.chap2.domain.Twit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Park Yonggyu
 */
public class TwitAdapter extends BaseAdapter {
	private Context context;
	private int layoutId;
	private ArrayList<Twit> items;
	private LayoutInflater inflater;

	public TwitAdapter(Context context, int layoutId, ArrayList<Twit> list) {
		this.context = context;
		this.layoutId = layoutId;
		this.items = list;
		inflater = (LayoutInflater) this.context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return items.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(this.layoutId, parent, false);
		}

		Twit twit = items.get(position);

		ImageView imgView = (ImageView) convertView.findViewById(R.id.img);
		TextView txtView1 = (TextView) convertView.findViewById(R.id.text1);
		TextView txtView2 = (TextView) convertView.findViewById(R.id.text2);

		imgView.setImageDrawable(this.context.getResources().getDrawable(
				R.drawable.icon));
		txtView1.setText(twit.getContents());
		txtView2.setText(twit.getAuthorId() + " " + twit.getPublished());

		return convertView;
	}

}