package com.jhonmelvin.perello;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sql.Me;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;
import dao.Entry;
import dao.EntryAccess;

public class MyEntry extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_entry);
		init();
	}
	
	private String id;
	private String type;
	private String title;
	private String content;
	private Integer month;
	private Integer day;
	private Integer year;
	private Integer hours;
	private Integer seconds;
	private String color;
	private Uri fileUri; // path
	private String author;
	/**********************************************/
	private String mode = "Auto";
	ImageView img_display;
	VideoView vd_display;

	Button btn_post, btn_cancel;
	EditText txt_title, txt_content;

	private void init() {
		// TODO Auto-generated method stub
		AnimationDrawable ad = (AnimationDrawable) findViewById(
				R.id.pnl_myentry).getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		img_display = (ImageView) findViewById(R.id.img_display);
		vd_display = (VideoView) findViewById(R.id.vd_display);
		btn_post = (Button) findViewById(R.id.btn_post);
		btn_cancel = (Button) findViewById(R.id.btn_cancel);
		txt_title = (EditText) findViewById(R.id.txt_title);
		txt_content = (EditText) findViewById(R.id.txt_content);

		// add events
		addEventHandler();
		
		
		
		try {
			if(getIntent().getStringExtra("mode").equals("edit")){
				btn_post.setText("Save Changes");
				txt_title.setText(getIntent().getStringExtra("title"));
				txt_content.setText(getIntent().getStringExtra("content"));
				this.id = getIntent().getStringExtra("id");
				this.mode = "edit";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		this.type = getIntent().getStringExtra("type");
		try {
			if (type.equalsIgnoreCase("Text Entry")) {
				previewImage("text");
			} else if (type.equalsIgnoreCase("Photo Entry")) {
				this.fileUri = Uri.parse(getIntent().getStringExtra("uri"));
				previewImage("photo");
			} else if (type.equalsIgnoreCase("Video Entry")) {
				this.fileUri = Uri.parse(getIntent().getStringExtra("uri"));
				previewVideo();
			}
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), ex.toString(),
					Toast.LENGTH_LONG).show();
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		try {
			String confirm_active = Me.getInstance().getStoredValues()
					.get("confirm_active");
			if (confirm_active.equals("1")) {
				String confirm_value = Me.getInstance().getStoredValues()
						.get("confirm_value");
				if (confirm_value.equalsIgnoreCase("1")) {
					post();
					Toast.makeText(getApplicationContext(), "Posted",
							Toast.LENGTH_SHORT).show();
				} else {
					//finish();
				}
				Me.getInstance().getStoredValues().remove("confirm_value");
				Me.getInstance().getStoredValues().remove("confirm_active");
			}
		} catch (Exception e) {
			// Toast.makeText(getApplicationContext(), e.toString(),
			// Toast.LENGTH_LONG).show();
		}

	}

	public void _action(){
		if(this.mode.equals("edit")){
			
			String title = txt_title.getText().toString();
			String content = txt_content.getText().toString();
			if(title.isEmpty()){
				title = "No Title";
			}
			if(content.isEmpty()){
				content = "This entry is empty . . .";
			}
			EntryAccess ea = new EntryAccess();
			int i = ea.edit_entry(this.id, title, content);
			if(i==1){
				Toast.makeText(getApplicationContext(), "Changes Saved", Toast.LENGTH_SHORT).show();
			}
			finish();
			//
		}else{
			confirm_box();
		}
	}
	public void addEventHandler() {

		
		btn_post.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				_action();
				// post();
			}
		});

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				endActivity();
			}
		});
		vd_display.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.setLooping(true);
			}
		});

	}

	public void endActivity() {
		finish();
	}

	@SuppressWarnings("deprecation")
	public void post() {
		Calendar c = Calendar.getInstance();

		this.title = txt_title.getText().toString();
		if (title.isEmpty()) {
			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
					Locale.getDefault()).format(new Date());
			this.title = "No Title";
		}
		this.content = txt_content.getText().toString();
		if (this.content.isEmpty()) {
			this.content = "This entry is empty . . .";
		}

		this.month = c.getTime().getMonth();
		this.day = c.getTime().getDate();
		this.year = c.getTime().getYear();
		this.hours = c.getTime().getHours();
		this.seconds = c.getTime().getMinutes();
		this.author = Me.getInstance().getUsernickname();
		this.color = "default";

		Entry e = new Entry();
		e.setType(this.type);
		e.setTitle(this.title);
		e.setContent(this.content);
		e.setMonth(this.month);
		e.setDay(this.day);
		e.setYear(this.year);
		e.setHours(this.hours);
		e.setSeconds(this.seconds);
		e.setColor(this.color);
		if (!this.type.equalsIgnoreCase("Text Entry")) {
			e.setPath(this.fileUri.toString());
		}
		e.setAuthor(this.author);

		// Toast.makeText(getApplicationContext(), this.month.toString() + " - "
		// + this.day.toString() + " - " + this.year.toString() + " - " +
		// this.hours + " - " + this.seconds.toString(),
		// Toast.LENGTH_LONG).show();
		try {
			EntryAccess ea = new EntryAccess();
			Long l = ea.new_user(e);
			view_entry(l);
			// Toast.makeText(getApplicationContext(), l.toString(),
			// Toast.LENGTH_SHORT).show();
		} catch (Exception ex) {
			// Toast.makeText(getApplicationContext(), ex.toString(),
			// Toast.LENGTH_SHORT).show();
		} finally {
			finish();
		}
	}

	public void view_entry(Long entry_no) {
		Intent i = new Intent(getApplicationContext(), CreateEntry.class);
		// i.putExtra("id", entry_no);
		Me.getInstance().getStoredValues().put("view_id", entry_no.toString());
		this.startActivity(i);
	}

	public void confirm_box() {
		Intent i = new Intent(getApplicationContext(), ConfirmMessage.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(i);
	}

	void previewImage(String type) {
		try {
			vd_display.setVisibility(View.GONE);
			img_display.setVisibility(View.VISIBLE);

			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 4;
			final Bitmap b = BitmapFactory.decodeResource(
					getApplicationContext().getResources(),
					R.drawable.textentryimage, options);

			if (type.equalsIgnoreCase("text")) {
				img_display.setImageBitmap(b);
			} else if (type.equalsIgnoreCase("photo")) {
				final Bitmap bitmap = BitmapFactory.decodeFile(
						fileUri.getPath(), options);
				img_display.setImageBitmap(bitmap);
			}

			// Toast.makeText(getApplicationContext(), "Done",
			// Toast.LENGTH_LONG)
			// .show();
		} catch (NullPointerException e) {
			Toast.makeText(getApplicationContext(), fileUri.toString(),
					Toast.LENGTH_LONG).show();
		}
	}

	void previewVideo() {
		try {
			img_display.setVisibility(View.GONE);
			vd_display.setVisibility(View.VISIBLE);

			vd_display.setVideoPath(fileUri.getPath());
			vd_display.start();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), e.toString(),
					Toast.LENGTH_LONG).show();
		}
	}

	/* ??????????????????????????????????? */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_entry, menu);
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
