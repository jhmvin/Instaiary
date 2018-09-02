package dao;

import java.util.ArrayList;
import java.util.List;

import sql.Me;
import sql.MonoData;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserAccess {
	private Me me;
	private SQLiteDatabase dc;
	private MonoData md;

	public UserAccess() {
		this.me = Me.getInstance();
		this.md = me.initMono();
		this.dc = md.connect();
	}

	private String[] columns = { "id", "username", "password", "name", "pin",
			"logged", "recovery_contact" };

	public List<User> getAllUsers() {

		List<User> user = new ArrayList<User>();
		Cursor row = dc
				.query("tbl_user", columns, null, null, null, null, null);

		row.moveToFirst();
		while (!row.isAfterLast()) {
			User comment = rowToUser(row);// rowToUser(row);
			user.add(comment);
			row.moveToNext();
		}
		// make sure to close the cursor

		row.close();
		return user;
	}

	public List<User> find(String field, String value) {

		List<User> user = new ArrayList<User>();
		String[] parameters = { value };
		Cursor row = dc.rawQuery("SELECT * FROM tbl_user where " + field
				+ " = ?", parameters);

		row.moveToFirst();
		while (!row.isAfterLast()) {
			User comment = rowToUser(row);// rowToUser(row);
			user.add(comment);
			row.moveToNext();
		}
		// make sure to close the cursor

		row.close();
		return user;
	}

	public void updateGlobal(String key, String value) {
		String[] parameters = { value };
		dc.execSQL("UPDATE tbl_user SET " + key + " = ?", parameters);
	}

	public void update(String key, String value, String field,
			String field_value) {
		String[] parameters = { value, field_value };
		dc.execSQL("UPDATE tbl_user SET " + key + " = ? where " + field
				+ " = ?", parameters);
	}

	private User rowToUser(Cursor row) {
		User user = new User();
		user.setId((row.getInt(row.getColumnIndex("id"))));
		user.setLogged((row.getInt(row.getColumnIndex("logged"))));
		user.setUsername((row.getString(row.getColumnIndex("username"))));
		user.setPassword((row.getString(row.getColumnIndex("password"))));
		user.setName((row.getString(row.getColumnIndex("name"))));
		user.setPin((row.getString(row.getColumnIndex("pin"))));
		user.setRecovery_contact((row.getString(row.getColumnIndex("recovery_contact"))));
		return user;
	}

	public void new_user(User user) {
		ContentValues values = new ContentValues();
		values.put("username", user.getUsername());
		values.put("password", user.getPassword());
		values.put("name", user.getName());
		values.put("recovery_contact", user.getRecovery_contact());
		values.put("pin", user.getPin());
		values.put("logged", user.getLogged());

		dc.insert("tbl_user", null, values);
	}

	public Boolean check_exist(String username) {
		UserAccess ua = new UserAccess();
		List<User> users = new ArrayList<User>();
		users = ua.find("username", username);
		Boolean exist = false;

		for (int x = 0; x < users.size(); x++) {
			if (users.get(x).getUsername().equalsIgnoreCase(username)) {
				exist = true;
				break;
			} else {
				exist = false;
			}
		}

		return exist;
	}

}
