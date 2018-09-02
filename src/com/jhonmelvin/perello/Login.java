package com.jhonmelvin.perello;

import java.util.ArrayList;
import java.util.List;

import sql.Me;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import dao.User;
import dao.UserAccess;

public class Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		AnimationDrawable ad = (AnimationDrawable) findViewById(R.id.pnl_login)
				.getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		addEventHandlers();

		count_users();
		check_iflogged();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(Me.getInstance().getAppContext(), Me.about_message,
					Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// ***************************************************************
	Button btn_register;
	Button btn_login;
	TextView lbl_forgot;

	public void addEventHandlers() {
		// set singleton
		Me me = Me.getInstance();
		me.setAppContext(getApplicationContext());

		btn_register = (Button) findViewById(R.id.btn_register);
		btn_login = (Button) findViewById(R.id.btn_login);
		lbl_forgot = (TextView) findViewById(R.id.lbl_forgot);

		lbl_forgot.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				forgot();
			}
		});

		btn_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				register();
			}
		});

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				login();
			}
		});

	}

	// ***************************************************************

	public void forgot() {
		EditText txt_user = (EditText) findViewById(R.id.txt_user);
		String user = txt_user.getText().toString();
		UserAccess acc = new UserAccess();
		if (user.isEmpty()) {
			Toast.makeText(getApplicationContext(),
					"Type your username to recover your password.",
					Toast.LENGTH_LONG).show();
		} else {
			if (acc.check_exist(user)) {
				List<User> u = new ArrayList<User>();
				u = ua.find("username", user);
				User me = u.get(0);

				try {
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(
							me.getRecovery_contact(),
							null,
							"You are registered as a Recovery Contact for a Instaiary account, "
									+ me.getName() + "'s password is : "
									+ me.getPassword() + " \nThank you.", null,
							null);
					smsManager.sendTextMessage(
							me.getRecovery_contact(),
							null,
							"You are registered as a Recovery Contact for a Instaiary account, "
									+ me.getName() + "'s password is : "
									+ me.getPassword() + " \nThank you.", null,
							null);

					//
					Toast.makeText(
							getApplicationContext(),
							"Your password has been sent to "
									+ me.getRecovery_contact(),
							Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(getApplicationContext(), "Error Sending Message.", Toast.LENGTH_LONG).show();
				}

			} else {
				Toast.makeText(getApplicationContext(),
						"Account is not existing.", Toast.LENGTH_LONG).show();
			}
		}
	}

	public void registrationPrompt() {
		Intent i = new Intent(getApplicationContext(), RegistrationPrompt.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(i);
	}

	public void register() {
		Intent i = new Intent(getApplicationContext(), Register.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(i);
	}

	public void count_users() {
		UserAccess ua = new UserAccess();
		List<User> users = new ArrayList<User>();
		users = ua.getAllUsers();

		if (users.size() == 0) {
			registrationPrompt();
		}
	}

	// ************
	UserAccess ua;

	public void login() {
		EditText txt_user = (EditText) findViewById(R.id.txt_user);
		EditText txt_pass = (EditText) findViewById(R.id.txt_pass);
		ua = new UserAccess();
		if (ua.check_exist(txt_user.getText().toString())) {
			List<User> u = new ArrayList<User>();
			u = ua.find("username", txt_user.getText().toString());

			if (txt_pass.getText().toString().equals(u.get(0).getPassword())) {
				// login code
				auth(txt_user.getText().toString(), u.get(0).getName());
				//
			} else {
				Toast.makeText(getApplicationContext(), "Wrong Password",
						Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(getApplicationContext(), "Invalid User",
					Toast.LENGTH_SHORT).show();
		}

	}

	public void auth(String user, String nickname) {
		Intent i = new Intent(getApplicationContext(), Main.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(i);
		Me.getInstance().getStoredValues().put("user", user);
		Me.getInstance().setUsername(user);
		Me.getInstance().setUsernickname(nickname);
		// login_info.put("user", user);
		// Me.getInstance().setStoredValues(login_info);
		ua.updateGlobal("logged", "0");
		ua.update("logged", "1", "username", user);
		String state = ua.find("username", user).get(0).getLogged().toString();
		// Toast.makeText(getApplicationContext(), state,
		// Toast.LENGTH_SHORT).show();

		finish();
	}

	public void check_iflogged() {
		ua = new UserAccess();
		List<User> u = new ArrayList<User>();
		u = ua.find("logged", "1");
		if (u.size() != 0) {
			auth(u.get(0).getUsername(), u.get(0).getName());
		}
	}
}
