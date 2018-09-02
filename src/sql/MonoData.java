package sql;

import java.io.File;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class MonoData extends SQLiteOpenHelper {
	// SmsManager sms = SmsManager.getDefault();

	private static final String db_name = "commments.db";
	private static final int db_version = 1;
	private static final String FILE_DIR = "Instaiary";

	/*---------------------SQL STATEMENTS-----------------------*/
	// create table entry
	private static final String tbl_entry = "CREATE TABLE IF NOT EXISTS tbl_entry ("
			+ "entry_no integer primary key autoincrement not null,"
			+ "type text,"
			+ "title text,"
			+ "content text,"
			+ "month integer,"
			+ "day integer,"
			+ "year integer,"
			+ "hours integer,"
			+ "seconds integer,"
			+ "color text,"
			+ "path text,"
			+ "author text"
			+ ");";

	private static final String tbl_user = "CREATE TABLE IF NOT EXISTS tbl_user ("
			+ "id integer primary key autoincrement not null,"
			+ "username text,"
			+ "password text,"
			+ "name text,"
			+ "pin text,"
			+ "logged integer," + "recovery_contact text" + ");";

	/*---------------------SQL STATEMENTS-----------------------*/

	// constructor
	public MonoData(Context context) {
		super(context, Environment.getExternalStorageDirectory()
	            + File.separator + FILE_DIR
	            + File.separator + db_name, null, db_version);

	}

	// connect function
	@SuppressWarnings("finally")
	public SQLiteDatabase connect() {
		SQLiteDatabase dc = null;
		try {
			dc = this.getWritableDatabase();
		} catch (SQLException e) {
			Log.w(MonoData.class.getName(), "Database Connection Error: " + e);
		} finally {
			return dc;
		}
		
	}

	// close function
	public void close() {
		this.close();
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// db.execSQL("DROP TABLE IF EXISTS tbl_user");
		//db.execSQL("DROP TABLE IF EXISTS tbl_entry");
		db.execSQL(tbl_user);
		db.execSQL(tbl_entry);
		
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MonoData.class.getName(), "Current Version: " + oldVersion
				+ "\n Upgrade To Version: " + newVersion
				+ "\n WARNING: THIS WILL DELETE ALL YOUR DATA . . .");
		//db.execSQL("DROP TABLE IF EXISTS tbl_user");
		onOpen(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

}
