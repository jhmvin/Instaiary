package sql;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import com.jhonmelvin.perello.CreateEntry;

public class Me {

	public final static String subscription = "PRO";
	public final static String about_message = "Instaiary v9.8.17 (C) 2017 Made with Love <3 jlnclsmrc. \nThank you for using "
			+ subscription + " version.";

	private static Me me;
	private Context appContext;
	private MonoData md;
	private HashMap<String, String> storedValues;

	private ArrayList<HashMap<String, String>> list_values;

	private String message;

	private String username;
	private String usernickname;

	private Me() {
		storedValues = new HashMap<String, String>();
		list_values = new ArrayList<HashMap<String, String>>();
		username = "";
		usernickname = "";
	}

	public void view_entry(Long entry_no) {
		try {
			HashMap<String, String> hm = this.list_values.get(Integer
					.parseInt(String.valueOf(entry_no)));

			Intent i = new Intent(this.appContext, CreateEntry.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// i.putExtra("id", entry_no);
			Me.getInstance().getStoredValues().put("view_id", hm.get("id"));
			this.appContext.startActivity(i);
		} catch (Exception ex) {
			Toast.makeText(this.appContext, ex.toString(), Toast.LENGTH_LONG)
					.show();
		}
	}

	public static Me getInstance() {
		if (me == null) {
			me = new Me();
		}
		return me;
	}

	public Context getAppContext() {
		return this.appContext;

	}

	public void setAppContext(Context appContext) {
		this.appContext = appContext;
	}

	public MonoData getMd() {
		return this.md;
	}

	public MonoData initMono() {
		this.md = new MonoData(this.appContext);
		return this.md;
	}

	public static Boolean isDigit(String str) {
		Boolean res = true;

		for (Character c : str.toCharArray()) {
			if (Character.isDigit(c)) {
				res = true;
			} else {
				res = false;
				break;
			}
		}
		return res;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HashMap<String, String> getStoredValues() {
		return storedValues;
	}

	public void setStoredValues(HashMap<String, String> storedValues) {
		this.storedValues = storedValues;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsernickname() {
		return usernickname;
	}

	public void setUsernickname(String usernickname) {
		this.usernickname = usernickname;
	}

	public ArrayList<HashMap<String, String>> getList_values() {
		return list_values;
	}

	public void setList_values(ArrayList<HashMap<String, String>> list_values) {
		this.list_values = list_values;
	}

}
