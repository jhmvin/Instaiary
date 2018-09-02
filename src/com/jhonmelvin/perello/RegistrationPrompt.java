package com.jhonmelvin.perello;

import sql.Me;

import com.jhonmelvin.perello.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class RegistrationPrompt extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration_prompt);

		AnimationDrawable ad = (AnimationDrawable) findViewById(
				R.id.pnl_regprompt).getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		addEventHandlers();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration_prompt, menu);
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

	// ***************************************************************
	Button btn_reg;
	Button btn_later;

	public void addEventHandlers() {
		btn_reg = (Button) findViewById(R.id.btn_reg);
		btn_reg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				register();
			}
		});

		btn_later = (Button) findViewById(R.id.btn_later);
		btn_later.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				later();
			}
		});
	}

	// **************************************************************
	public void register() {
		Intent i = new Intent(getApplicationContext(), Register.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(i);
	}

	public void later() {
		
		Intent i = new Intent(getApplicationContext(), Login.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(i);
	

	}
}
