package com.huchcode.train.android.sample.chap2;

import java.util.List;

import com.huchcode.train.android.sample.R;
import com.huchcode.train.android.sample.chap2.domain.BusinessCard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

// Custom Adapter
public class BusinessCardAdapter extends BaseAdapter {
    private Context context;
    private int layoutId;
    private List<BusinessCard> items;
    private LayoutInflater inflater;

    // Constructor
    public BusinessCardAdapter(Context context, int layoutId, List<BusinessCard> cards) {

        this.context = context;
        this.layoutId = layoutId;
        this.items = cards;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
            convertView = inflater.inflate(layoutId, parent, false);
        }

        ImageView imgPic = (ImageView) convertView.findViewById(R.id.imgPic);
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvCompany = (TextView) convertView.findViewById(R.id.tvCompany);
        TextView tvPhoneNo = (TextView) convertView.findViewById(R.id.tvPhoneNo);
        TextView tvEmail = (TextView) convertView.findViewById(R.id.tvEmail);

        BusinessCard card = items.get(position);
        imgPic.setImageResource(R.drawable.icon1); // Configure the default image
        tvName.setText(card.getName());
        tvCompany.setText(card.getCompany());
        tvPhoneNo.setText(card.getPhoneNo());
        tvEmail.setText(card.getEmail());

        return convertView;
    }

    // Add the new name card
    public void add(BusinessCard card) {
        this.items.add(card);
        this.notifyDataSetChanged();
    }

    public void setItems(List<BusinessCard> cards) {
        this.items = cards;
    }

}
