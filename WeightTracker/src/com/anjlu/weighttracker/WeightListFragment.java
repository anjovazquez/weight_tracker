package com.anjlu.weighttracker;

import java.util.Calendar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.anjlu.weighttracker.provider.WeightProvider;
import com.anjlu.weighttracker.util.Utils;

/**
 * A dummy fragment representing a section of the app, but that simply displays
 * dummy text.
 */
public class WeightListFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */

	private ImageView ivAddWeight;
	private WeightAdapter adapter;

	public WeightListFragment() {
	}

	@Override
	public void onResume() {
		super.onResume();
		adapter.changeCursor(getActivity().getContentResolver().query(
				WeightProvider.CONTENT_URI_WEIGHT,
				null,
				WeightProvider.WeightTracker.DATE_TRACK + " <= ?",
				new String[] { Utils.formatDate(Calendar.getInstance()
						.getTime()) }, WeightProvider.WeightTracker._ID));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.weights_list, container,
				false);
		ivAddWeight = (ImageView) rootView
				.findViewById(R.id.weight_list_add_weight);
		ivAddWeight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						WeightAddActivity.class);
				startActivity(intent);

			}
		});
		// Obtenemos los datos de la base de datos
		Cursor c = getActivity().getContentResolver().query(
				WeightProvider.CONTENT_URI_WEIGHT,
				null,
				WeightProvider.WeightTracker.DATE_TRACK + " <= ?",
				new String[] { Utils.formatDate(Calendar.getInstance()
						.getTime()) }, WeightProvider.WeightTracker._ID);

		adapter = new WeightAdapter(getActivity(),
				android.R.layout.simple_list_item_2, c, true);

		((ListView) rootView.findViewById(android.R.id.list))
				.setAdapter(adapter);
		return rootView;
	}

	private class WeightAdapter extends ResourceCursorAdapter {

		private LayoutInflater mInflater;

		public WeightAdapter(Context context, int layout, Cursor c,
				boolean autoRequery) {
			super(context, layout, c, autoRequery);
			mInflater = LayoutInflater.from(context);
		}

		public WeightAdapter(Context context, int layout, Cursor c, int flags) {
			super(context, layout, c, flags);
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = mInflater.inflate(android.R.layout.simple_list_item_2,
					parent, false);
			ViewHolder holder = new ViewHolder();
			TextView text1 = (TextView) view.findViewById(android.R.id.text1);
			TextView text2 = (TextView) view.findViewById(android.R.id.text2);
			holder.text1 = text1;
			holder.text2 = text2;
			view.setTag(holder);
			return view;
		}

		@Override
		public void bindView(View v, Context context, Cursor cursor) {

			ViewHolder holder = (ViewHolder) v.getTag();

			holder.text1.setText(cursor.getString(cursor
					.getColumnIndex(WeightProvider.WeightTracker.WEIGHT)));
			holder.text2.setText(cursor.getString(cursor
					.getColumnIndex(WeightProvider.WeightTracker.DATE_TRACK)));
		}

	}

	static class ViewHolder {
		TextView text1;
		TextView text2;
	}
}