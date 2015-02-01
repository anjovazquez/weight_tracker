package com.anjlu.weighttracker.provider.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = DatabaseHelper.class.getSimpleName();

	private static final int DATABASE_VERSION = 4;
	private static final String DATABASE_NAME = "weightdb.db";

	public static final String WEIGHT = "WEIGHT_TRACK";

	private static DatabaseHelper instance;
	private final Context myContext;
	private SQLiteDatabase database;

	public static DatabaseHelper getInstance(Context ctx) {
		if (instance == null) {
			instance = new DatabaseHelper(ctx);
		}
		return instance;
	}

	private DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
	}

	public SQLiteDatabase getMyDataBase() {
		if (database == null) {
			openDataBase();
		} else {
			if (!database.isOpen()) {
				openDataBase();
			}
		}
		return database;
	}

	public void openDataBase() throws SQLException {

		ContextWrapper cw = new ContextWrapper(myContext);

		String myPath = cw.getFilesDir().getAbsolutePath() + File.separator
				+ DATABASE_NAME;
		if (database != null && database.isOpen()) {
			database.close();
		}
		try {

			if (checkDataBase()) {

				database = SQLiteDatabase.openDatabase(myPath, null,
						SQLiteDatabase.OPEN_READWRITE);
				database.setVersion(DATABASE_VERSION);
			} else {
				try {
					createDataBase();
					database = SQLiteDatabase.openDatabase(myPath, null,
							SQLiteDatabase.OPEN_READWRITE);
					database.setVersion(DATABASE_VERSION);
				} catch (IOException e1) {
					Log.e(TAG, "Database can not be created");
				}
			}
		} catch (SQLiteException e) {
			try {
				createDataBase();
				openDataBase();
			} catch (IOException e1) {
				Log.e(TAG, "Database can not be created");
			}

		}

	}

	public void createDataBase() throws IOException {
		try {
			// create empty db
			getReadableDatabase();
			copyDataBase();
		} catch (IOException e) {
			Log.e(TAG, "Error copying database");
		}
	}

	private void copyDataBase() throws IOException {

		ContextWrapper cw = new ContextWrapper(myContext);

		InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
		// Path to the just created empty db
		String outFileName = cw.getFilesDir().getAbsolutePath()
				+ File.separator + DATABASE_NAME;

		// Open the empty db as the output stream
		OutputStream myOutput = new FileOutputStream(outFileName);
		// transfer bytes from the inputfile to the outputfile
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		// Close the streams
		myOutput.flush();
		myOutput.close();
		myInput.close();

		Log.i(TAG, "Copy database finish");

	}

	public boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		boolean isok = false;
		try {
			ContextWrapper cw = new ContextWrapper(myContext);
			String myPath = cw.getFilesDir().getAbsolutePath() + File.separator
					+ DATABASE_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLiteException e) {
			Log.i(TAG, "Database does not exists yet");
		}
		if (checkDB != null) {
			if (checkDB.getVersion() == DATABASE_VERSION) {
				isok = true;
			}
			checkDB.close();
		}
		return isok;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {

	}

}
