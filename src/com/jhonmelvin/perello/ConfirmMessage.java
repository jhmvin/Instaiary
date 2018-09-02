package com.jhonmelvin.perello;

import sql.Me;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ConfirmMessage extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirm_message);

		AnimationDrawable ad = (AnimationDrawable) findViewById(
				R.id.pnl_confirm).getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		Me.getInstance().getStoredValues().put("confirm_active", "1");
		init();
	}

	Button btn_yes, btn_no;

	private void init() {
		// TODO Auto-generated method stub
		btn_yes = (Button) findViewById(R.id.btn_yes);
		btn_no = (Button) findViewById(R.id.btn_no);

		addEventHandlers();

	}

	private void addEventHandlers() {
		// TODO Auto-generated method stub
		btn_yes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Me.getInstance().getStoredValues().put("confirm_value", "1");
				finish();
			}
		});

		btn_no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Me.getInstance().getStoredValues().put("confirm_value", "0");
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.confirm_message, menu);
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
}
