package com.jhonmelvin.perello;

import java.util.List;

import sql.Me;
import sql.StampFormatter;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import dao.Entry;
import dao.EntryAccess;

public class CreateEntry extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_entry);
		init();
	}

	ImageView img_display;
	VideoView vd_display;
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

	TextView lbl_day, lbl_month, lbl_year, lbl_title, lbl_author, lbl_time,
			lbl_content, lbl_type;
	Button btn_back, btn_edit;

	private void init() {
		// TODO Auto-generated method stub

		AnimationDrawable ad = (AnimationDrawable) findViewById(R.id.pnl_view)
				.getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		lbl_day = (TextView) findViewById(R.id.lbl_day);
		lbl_month = (TextView) findViewById(R.id.lbl_month);
		lbl_year = (TextView) findViewById(R.id.lbl_year);
		lbl_title = (TextView) findViewById(R.id.lbl_title);
		lbl_author = (TextView) findViewById(R.id.lbl_author);
		lbl_time = (TextView) findViewById(R.id.lbl_time);
		lbl_content = (TextView) findViewById(R.id.lbl_content);
		lbl_type = (TextView) findViewById(R.id.lbl_type);

		img_display = (ImageView) findViewById(R.id.img_display);
		vd_display = (VideoView) findViewById(R.id.vd_display);

		btn_back = (Button) findViewById(R.id.btn_back);
		btn_edit = (Button) findViewById(R.id.btn_edit);

		// add events

		try {
			EntryAccess ea = new EntryAccess();

			// Toast.makeText(getApplicationContext(),
			// getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();
			List<Entry> entry = ea.find("entry_no", Me.getInstance()
					.getStoredValues().get("view_id"));
			Entry my_entry = entry.get(0);
			this.id = my_entry.getEntry_no().toString();
			this.type = my_entry.getType();
			this.title = my_entry.getTitle();
			this.content = my_entry.getContent();
			this.month = my_entry.getMonth();
			this.day = my_entry.getDay();
			this.year = my_entry.getYear();
			this.hours = my_entry.getHours();
			this.seconds = my_entry.getSeconds();
			this.color = my_entry.getColor();
			if (!this.type.equalsIgnoreCase("Text Entry")) {
				this.fileUri = Uri.parse(my_entry.getPath());
			}

			this.author = my_entry.getAuthor();

			lbl_day.setText(StampFormatter.getInstance().format_day(this.day));
			lbl_month.setText(StampFormatter.getInstance().format_month(
					this.month));
			lbl_year.setText(StampFormatter.getInstance()
					.format_year(this.year));
			lbl_title.setText(this.title);
			lbl_author.setText(this.author);
			lbl_time.setText(StampFormatter.getInstance().format_time(
					this.hours, this.seconds));
			lbl_content.setText(this.content);
			lbl_type.setText(this.type);

			if (this.type.equalsIgnoreCase("Photo Entry")) {
				previewImage("photo");
			} else if (this.type.equalsIgnoreCase("Text Entry")) {
				previewImage("text");
			} else if (this.type.equalsIgnoreCase("Video Entry")) {
				previewVideo();
			}

		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), ex.toString(),
					Toast.LENGTH_LONG).show();
		}
		addEventHandler();
	}

	public void open_editor() {
		String type = this.type;
		try {
			Intent i = new Intent(getApplicationContext(), MyEntry.class);
			i.putExtra("mode", "edit");
			i.putExtra("title", this.title);
			i.putExtra("content", this.content);
			i.putExtra("id", this.id);
			if (!type.equalsIgnoreCase("Text Entry")) {
				i.putExtra("uri", fileUri.toString());
			}

			i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			i.putExtra("type", type);
			this.startActivity(i);
			finish();
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), ex.toString(),
					Toast.LENGTH_SHORT).show();
		}
	}

	/***************************************************/

	public void addEventHandler() {
		btn_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				open_editor();
			}
		});
		btn_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
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

	// camera function

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

	// -------------------------------------------

	/***************************************************/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_entry, menu);
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
