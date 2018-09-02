package dao;

import java.util.ArrayList;
import java.util.List;

import sql.Me;
import sql.MonoData;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class EntryAccess {

	private Me me;
	private SQLiteDatabase dc;
	private MonoData md;

	public EntryAccess() {
		this.me = Me.getInstance();
		this.md = me.initMono();
		this.dc = md.connect();
	}

	private String[] columns = { "entry_no", "type", "title", "content",
			"month", "day", "year", "hours", "seconds", "color", "path",
			"author" };

	public List<Entry> getAllEntries() {

		List<Entry> entry = new ArrayList<Entry>();
		Cursor row = dc.query("tbl_entry", columns, null, null, null, null,
				null);

		row.moveToFirst();
		while (!row.isAfterLast()) {
			Entry comment = rowToEntry(row);// rowToUser(row);
			entry.add(comment);
			row.moveToNext();
		}
		// make sure to close the cursor

		row.close();
		return entry;
	}

	public List<Entry> find(String field, String value) {
		List<Entry> entry = new ArrayList<Entry>();
		try {
			String[] parameters = { value };
			Cursor row = dc.rawQuery("SELECT * FROM tbl_entry where " + field
					+ " = ?", parameters);

			row.moveToFirst();
			while (!row.isAfterLast()) {
				Entry comment = rowToEntry(row);// rowToUser(row);
				entry.add(comment);
				row.moveToNext();
			}
			// make sure to close the cursor

			row.close();

		} catch (Exception ex) {
			Toast.makeText(Me.getInstance().getAppContext(), ex.toString(),
					Toast.LENGTH_SHORT).show();
		}
		return entry;
	}

	public Integer edit_entry(String id, String title, String content) {
		ContentValues values = new ContentValues();
		String[] parameters = { id };
		values.put("title", title);
		values.put("content", content);
		return dc.update("tbl_entry", values, "entry_no = ?", parameters);
	}

	public List<Entry> counter_check(String author, String month, String day,
			String year) {
		
		List<Entry> entry = new ArrayList<Entry>();
		try {
			String[] parameters = { author, month, day, year };
			Cursor row = dc
					.rawQuery(
							"SELECT * FROM tbl_entry where author = ? and month = ? and day = ? and year =?",
							parameters);
			/**/
			row.moveToFirst();
			while (!row.isAfterLast()) {
				Entry comment = rowToEntry(row);// rowToUser(row);
				entry.add(comment);
				row.moveToNext();
			}
			// make sure to close the cursor

			row.close();

		} catch (Exception ex) {
			Toast.makeText(Me.getInstance().getAppContext(), ex.toString(),
					Toast.LENGTH_SHORT).show();
		}
		return entry;
	}

	public Long new_user(Entry entry) {
		ContentValues values = new ContentValues();
		values.put("type", entry.getType());
		values.put("title", entry.getTitle());
		values.put("content", entry.getContent());
		values.put("month", entry.getMonth());
		values.put("day", entry.getDay());
		values.put("year", entry.getYear());
		values.put("hours", entry.getHours());
		values.put("seconds", entry.getSeconds());
		values.put("color", entry.getColor());
		values.put("path", entry.getPath());
		values.put("author", entry.getAuthor());

		return dc.insert("tbl_entry", null, values);
	}

	private Entry rowToEntry(Cursor row) {
		Entry entry = new Entry();
		entry.setEntry_no((row.getInt(row.getColumnIndex("entry_no"))));
		entry.setType((row.getString(row.getColumnIndex("type"))));
		entry.setTitle((row.getString(row.getColumnIndex("title"))));
		entry.setContent((row.getString(row.getColumnIndex("content"))));
		entry.setMonth((row.getInt(row.getColumnIndex("month"))));
		entry.setDay((row.getInt(row.getColumnIndex("day"))));
		entry.setYear((row.getInt(row.getColumnIndex("year"))));
		entry.setHours((row.getInt(row.getColumnIndex("hours"))));
		entry.setSeconds((row.getInt(row.getColumnIndex("seconds"))));
		entry.setColor((row.getString(row.getColumnIndex("color"))));
		entry.setPath((row.getString(row.getColumnIndex("path"))));
		entry.setAuthor((row.getString(row.getColumnIndex("author"))));
		return entry;
	}

}
