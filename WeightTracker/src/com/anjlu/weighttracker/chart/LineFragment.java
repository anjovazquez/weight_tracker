/*
 * 	   Created by Daniel Nadeau
 * 	   daniel.nadeau01@gmail.com
 * 	   danielnadeau.blogspot.com
 * 
 * 	   Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.anjlu.weighttracker.chart;

import java.util.Calendar;
import java.util.Vector;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.anjlu.weighttracker.R;
import com.anjlu.weighttracker.provider.WeightProvider;
import com.anjlu.weighttracker.util.Utils;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

public class LineFragment extends Fragment {

	@Override
	public void onResume() {
		super.onResume();
	}

	// protected void composeGraphic(View v) {
	// Cursor cursor = getActivity().getContentResolver().query(
	// WeightProvider.CONTENT_URI_WEIGHT,
	// null,
	// WeightProvider.WeightTracker.DATE_TRACK + " <= ?",
	// new String[] { Utils.formatDate(Calendar.getInstance()
	// .getTime()) }, WeightProvider.WeightTracker._ID);
	// int i = 0;
	// final Resources resources = getResources();
	// Line l = new Line();
	// l.setUsingDips(true);
	// while (cursor.moveToNext()) {
	// String dateTrack = cursor.getString(cursor
	// .getColumnIndex(WeightProvider.WeightTracker.DATE_TRACK));
	// String weight = cursor.getString(cursor
	// .getColumnIndex(WeightProvider.WeightTracker.WEIGHT));
	// Log.i(LineFragment.class.getCanonicalName(), "Date: " + dateTrack
	// + "Weight: " + weight);
	//
	// LinePoint p = new LinePoint();
	// p.setX(i++);
	// p.setY(Float.valueOf(weight));
	// p.setColor(resources.getColor(R.color.red));
	// p.setSelectedColor(resources.getColor(R.color.transparent_blue));
	// l.addPoint(p);
	// }
	// LineGraph li = (LineGraph) v.findViewById(R.id.linegraph);
	// li.setUsingDips(true);
	// li.addLine(l);
	// li.setRangeY(0, 150);
	// li.setLineToFill(0);
	//
	// li.setOnPointClickedListener(new OnPointClickedListener() {
	//
	// @Override
	// public void onClick(int lineIndex, int pointIndex) {
	// Toast.makeText(
	// getActivity(),
	// "Line " + lineIndex + " / Point " + pointIndex
	// + " clicked", Toast.LENGTH_SHORT).show();
	// }
	// });
	// }

	// private void graphicsExample(View v) {
	// final Resources resources = getResources();
	// Line l = new Line();
	// l.setUsingDips(true);
	// LinePoint p = new LinePoint();
	// p.setX(0);
	// p.setY(5);
	// p.setColor(resources.getColor(R.color.red));
	// p.setSelectedColor(resources.getColor(R.color.transparent_blue));
	// l.addPoint(p);
	// p = new LinePoint();
	// p.setX(8);
	// p.setY(8);
	// p.setColor(resources.getColor(R.color.blue));
	// l.addPoint(p);
	// p = new LinePoint();
	// p.setX(10);
	// p.setY(4);
	// l.addPoint(p);
	// p.setColor(resources.getColor(R.color.green));
	// l.setColor(resources.getColor(R.color.orange));
	//
	// LineGraph li = (LineGraph) v.findViewById(R.id.linegraph);
	// li.setUsingDips(true);
	// li.addLine(l);
	// li.setRangeY(0, 10);
	// li.setLineToFill(0);
	//
	// li.setOnPointClickedListener(new OnPointClickedListener() {
	//
	// @Override
	// public void onClick(int lineIndex, int pointIndex) {
	// Toast.makeText(
	// getActivity(),
	// "Line " + lineIndex + " / Point " + pointIndex
	// + " clicked", Toast.LENGTH_SHORT).show();
	// }
	// });
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.fragment_linegraph, container,
				false);
		// graphicsExample(v);
		// composeGraphic(v);
		compose(v);
		return v;
	}

	private void compose(View v) {
		// init example series data

		Cursor cursor = getActivity().getContentResolver().query(
				WeightProvider.CONTENT_URI_WEIGHT,
				null,
				WeightProvider.WeightTracker.DATE_TRACK + " <= ?",
				new String[] { Utils.formatDate(Calendar.getInstance()
						.getTime()) }, WeightProvider.WeightTracker._ID);
		int i = 0;
		Vector<GraphViewData> values = new Vector<GraphViewData>();
		while (cursor.moveToNext()) {
			String dateTrack = cursor.getString(cursor
					.getColumnIndex(WeightProvider.WeightTracker.DATE_TRACK));
			String weight = cursor.getString(cursor
					.getColumnIndex(WeightProvider.WeightTracker.WEIGHT));
			Log.i(LineFragment.class.getCanonicalName(), "Date: " + dateTrack
					+ "Weight: " + weight);
			values.add( new GraphViewData(i++, Double.parseDouble(weight)));
			
		}

		GraphViewData[] graphs = {};
		graphs = values.toArray((GraphViewData[]) graphs);
		GraphViewSeries exampleSeries = new GraphViewSeries(graphs);

		GraphView graphView = new LineGraphView(getActivity() // context
				, "GraphViewDemo" // heading
		);
		graphView.getGraphViewStyle().setNumHorizontalLabels(graphs.length);
		graphView.getGraphViewStyle().setGridColor(Color.WHITE);
		graphView.addSeries(exampleSeries); // data

		LinearLayout layout = (LinearLayout) v.findViewById(R.id.graph1);
		layout.addView(graphView);
	}
}
