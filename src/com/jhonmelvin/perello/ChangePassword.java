package com.jhonmelvin.perello;

import java.util.List;

import sql.Me;

import android.app.Activity;
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

public class ChangePassword extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		
		
		AnimationDrawable ad = (AnimationDrawable) findViewById(R.id.pnl_change)
				.getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();
		
		init();
	}
	
	Button btn_change,btn_cancel;
	EditText txt_old, txt_new,txt_confirm;
	public void init(){
		btn_change = (Button) findViewById(R.id.btn_change);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		
		txt_old = (EditText) findViewById(R.id.txt_old);
		txt_new = (EditText) findViewById(R.id.txt_new);
		txt_confirm = (EditText) findViewById(R.id.txt_confirm);
		
		addEvent();
	}
	
	public void addEvent(){
		btn_change.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				change_password();
			}
		});
		
		btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	/******************************************************/
	/*functions*/
	public void change_password(){
		UserAccess ua = new UserAccess();
		List<User> u;
		u = ua.find("username", Me.getInstance().getUsername());
		User me = u.get(0);
		
		String _old = txt_old.getText().toString();
		String _new = txt_new.getText().toString();
		String _confirm = txt_confirm.getText().toString();
		
		if(_old.equals(me.getPassword())){
			if(_new.length() < 6){
				Toast.makeText(getApplicationContext(), "New Password should be atleast 6 characters.", Toast.LENGTH_SHORT).show();
			}else{
				if(_new.equals(_confirm)){
					ua.update("password", _new, "username", Me.getInstance().getUsername());
					Toast.makeText(getApplicationContext(), "Password successfully changed.", Toast.LENGTH_SHORT).show();
					finish();
					
				}else{
					Toast.makeText(getApplicationContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
				}
			}
		}else{
			Toast.makeText(getApplicationContext(), "Wrong Password.", Toast.LENGTH_SHORT).show();
		}
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
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
}
