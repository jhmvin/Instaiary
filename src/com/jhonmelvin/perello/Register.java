package com.jhonmelvin.perello;

import java.util.Date;

import com.jhonmelvin.perello.R;

import sql.Me;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import dao.User;
import dao.UserAccess;

public class Register extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		AnimationDrawable ad = (AnimationDrawable) findViewById(
				R.id.pnl_register).getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		addEventHandlers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(Me.getInstance().getAppContext(), Me.about_message, Toast.LENGTH_SHORT)
			.show();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	// ****************************************************
	Button btn_register;
	EditText txt_user;
	EditText txt_name;
	EditText txt_pass1;
	EditText txt_pass2;
	EditText txt_recovery;
	UserAccess ua;
	Button btn_cancel;

	public void addEventHandlers() {
		// bind variables
		this.ua = new UserAccess();
		btn_register = (Button) findViewById(R.id.btn_register);
		txt_user = (EditText) findViewById(R.id.txt_user);
		txt_pass1 = (EditText) findViewById(R.id.txt_pass1);
		txt_pass2 = (EditText) findViewById(R.id.txt_pass2);
		txt_recovery = (EditText) findViewById(R.id.txt_recovery);
		txt_name = (EditText) findViewById(R.id.txt_name);

		
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		
		btn_register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				register();
			}
		});
		
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				show_login();
			}
		});
	}

	// ****************************************************
	public void register() {
		String username = this.txt_user.getText().toString();
		String message = "";
		
		//check  name;
		if(this.txt_name.getText().toString().isEmpty()){
			message += " >> Please enter a Name or Nickname\n";
		}
		
		//check user
		if (this.ua.check_exist(username) == true) {
			message += " >> Your username is already taken.\n";
		}
		
		if(username.length() < 6){
			message += " >> Username should be 6 characters or more.\n";
		}

		//check pass
		if (this.txt_pass1.length() < 6) {
			message += " >> Passwords should be 6 characters or more.\n";
		} else {
			if (this.txt_pass1.equals(this.txt_pass2)) {
				message += " >> Passwords do not match.\n";
			}
		}
		
		//check recovery contact
		if(this.txt_recovery.length() != 11){
			message += " >> Contact should be 11 Digits(PH)\n";
		}else{
			if(!Me.isDigit(this.txt_recovery.getText().toString())){
				message += " >> Contact should containe digits only.\n";
			}
		}

		if (!message.isEmpty()) {
			showMessage(message);
		}else{
			User u = new User();
			//
			u.setUsername(txt_user.getText().toString());
			u.setPassword(txt_pass1.getText().toString());
			u.setRecovery_contact(txt_recovery.getText().toString());
			u.setName(txt_name.getText().toString());
			u.setPin("REQUEST");
			u.setLogged(0);
			//
			ua.new_user(u);
			show_welcome();
		}

	}

	public void showMessage(String message) {
		Intent i = new Intent(getApplicationContext(), Message.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		Me.getInstance().setMessage(message);
		this.startActivity(i);
	}
	
	public void show_welcome(){
		Intent i = new Intent(getApplicationContext(), Welcome.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(i);
		finish();
	}
	
	public void show_login(){
		Intent i = new Intent(getApplicationContext(), Login.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(i);
		finish();
	}

}
