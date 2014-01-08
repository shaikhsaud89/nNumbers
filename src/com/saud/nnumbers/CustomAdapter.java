package com.saud.nnumbers;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Item> {

	Context context;
	int layoutResourceId;
	ArrayList<Item> data = new ArrayList<Item>();

	public CustomAdapter(Context context, int layoutResourceId, ArrayList<Item> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ItemHolder holder = null;

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new ItemHolder();
			holder.txtValue = (TextView) row.findViewById(R.id.item_value);
			holder.txtNumber = (TextView) row.findViewById(R.id.item_number);
			row.setTag(holder);
		} else {
			holder = (ItemHolder) row.getTag();
		}

		Item item = data.get(position);
		holder.txtValue.setText(item.getValue());
		holder.txtNumber.setText(item.getNumber());
		return row;

	}

	static class ItemHolder {
		TextView txtValue;
		TextView txtNumber;
	}

}
