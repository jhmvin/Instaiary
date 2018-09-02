package com.jhonmelvin.perello;

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
import android.widget.TextView;
import android.widget.Toast;

public class Message extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);

		AnimationDrawable ad = (AnimationDrawable) findViewById(R.id.pnl_message)
				.getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		addEventHandlers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.message, menu);
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
	
	protected void onResume() {
		super.onResume();
		String message = Me.getInstance().getMessage();
		txt_message.setText(message);
	};

	// ***************************************************************
	Button btn_continue;
	TextView txt_message;

	public void addEventHandlers() {
		// set singleton
		btn_continue = (Button) findViewById(R.id.btn_continue);
		txt_message = (TextView) findViewById(R.id.txt_message);
		
		String message = Me.getInstance().getMessage();
		txt_message.setText(message);
		
		btn_continue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				register();
			}
		});

	}


	// ***************************************************************
	public void register() {
		Intent i = new Intent(getApplicationContext(), Register.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(i);
	}




}
