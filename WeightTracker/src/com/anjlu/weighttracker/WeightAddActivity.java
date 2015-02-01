package com.anjlu.weighttracker;

import java.util.Date;

import com.anjlu.weighttracker.provider.WeightProvider;
import com.anjlu.weighttracker.util.Utils;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WeightAddActivity extends FragmentActivity {

	private Button bAddWeight;
	private Button bCancelAddWeight;

	private EditText etWeight;
	private EditText etWeightDecimal;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_weight);
		initView();
	}

	private void initView() {
		initAddWeightButton();
		initCancelAddWeightButton();
		etWeight = (EditText) findViewById(R.id.add_weight_kg_et);
		etWeightDecimal = (EditText) findViewById(R.id.add_weight_kg_decimal_et);

	}

	private void initAddWeightButton() {
		bAddWeight = (Button) findViewById(R.id.bAddWeght);
		bAddWeight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ContentValues cv = new ContentValues();
				Double weight = Double.parseDouble(etWeight.getText()
						.toString());
				Double weightDecimal = Double.parseDouble(etWeightDecimal
						.getText().toString());

				Double w = weight + (weightDecimal / 100);
				cv.put(WeightProvider.WeightTracker.WEIGHT, w.toString());
				// TODO
				cv.put(WeightProvider.WeightTracker.DATE_TRACK,
						Utils.formatDate(new Date()));

				getContentResolver().insert(WeightProvider.CONTENT_URI_WEIGHT,
						cv);
				

				WeightAddActivity.this.finish();


			}
		});
	}

	private void initCancelAddWeightButton() {
		bCancelAddWeight = (Button) findViewById(R.id.bCancelAddWeght);
		bCancelAddWeight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WeightAddActivity.this.finish();
			}
		});
	}

}
