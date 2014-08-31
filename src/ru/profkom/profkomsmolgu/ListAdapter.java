package ru.profkom.profkomsmolgu;

import java.util.List;

import android.content.ClipData.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ListAdapter extends ArrayAdapter<Item> {

	public ListAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
	}

	private List<Item> items;

	public ListAdapter(Context context, int resource, List<Item> items) {

		super(context, resource, items);

		this.items = items;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {

			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.discont_main, null);

		}

		Item p = items.get(position);
		return v;

	}
}
