package com.anjlu.weighttracker.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

import com.anjlu.weighttracker.provider.db.DatabaseHelper;

public class WeightProvider extends ContentProvider {

	private static final String TAG = WeightProvider.class.getSimpleName();

	private static final String AUTHORITY = "com.anjlu.weighttracker.provider";

	public static final Uri CONTENT_URI_WEIGHT = Uri.parse("content://"
			+ AUTHORITY + "/weight");

	private static final int URI_WEIGHT = 1;
	private static final int URI_WEIGHT_ITEM = 2;

	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, "weight", URI_WEIGHT);
		uriMatcher.addURI(AUTHORITY, "weight/#", URI_WEIGHT_ITEM);
	}

	private DatabaseHelper mDbHelper;

	public WeightProvider() {
	}

	protected void notifyChange(Uri uri) {
		getContext().getContentResolver().notifyChange(uri, null);
	}

	@Override
	public boolean onCreate() {
		mDbHelper = DatabaseHelper.getInstance(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = mDbHelper.getMyDataBase();

		SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
		String id = "";

		switch (uriMatcher.match(uri)) {
		case URI_WEIGHT:
			qBuilder.setTables(DatabaseHelper.WEIGHT);
			break;
		case URI_WEIGHT_ITEM:
			qBuilder.setTables(DatabaseHelper.WEIGHT);
			break;
		}

		Cursor c = qBuilder.query(db, projection, selection, selectionArgs,
				null, null, sortOrder);

		return c;
	}

	@Override
	public Uri insert(Uri uri, ContentValues cv) {

		SQLiteDatabase db = mDbHelper.getMyDataBase();
		long id;
		switch (uriMatcher.match(uri)) {
		case URI_WEIGHT:
			id = db.insert(DatabaseHelper.WEIGHT, null, cv);

			Uri result = null;

			if (id >= 0) {
				result = ContentUris.withAppendedId(CONTENT_URI_WEIGHT, id);
				notifyChange(result);
			}
			return result;
		}
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues cv, String selection,
			String[] selectionArgs) {
		return 0;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		return 0;
	}

	@Override
	public String getType(Uri uri) {

		int match = uriMatcher.match(uri);

		switch (match) {
		case URI_WEIGHT:
			return "vnd.android.cursor.dir/vnd.weighttracker.weight";
		default:
			Log.w(TAG, "Uri didn't match: " + uri);
			throw new IllegalArgumentException("Unknown URI: " + uri);
		}
	}

	/**
	 * Clase de constantes con la informacion de la base de datos
	 * 
	 */
	public class WeightTracker implements BaseColumns {

		public static final String WEIGHT = "WEIGHT";
		public static final String DATE_TRACK = "DATE_TRACK";
		public static final String SMILEY = "SMILEY";
	}

}
