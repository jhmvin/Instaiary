package com.jhonmelvin.perello;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import sql.Me;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera.PreviewCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

public class AddEntry extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_entry);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_entry, menu);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_PHOTO) {
			if (resultCode == RESULT_OK) {
				open_editor("Photo Entry");
			} else if (resultCode == RESULT_CANCELED) {
				// Toast.makeText(getApplicationContext(),
				// "User cancelled operation!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Cannot capture image.", Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == REQUEST_VIDEO) {
			if (resultCode == RESULT_OK) {
				open_editor("Video Entry");
			} else if (resultCode == RESULT_CANCELED) {
				// Toast.makeText(getApplicationContext(),
				// "User cancelled operation!", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Cannot capture video.", Toast.LENGTH_SHORT).show();
			}
		}
	}

	/******************************************/
	final static int REQUEST_PHOTO = 100;
	final static int REQUEST_VIDEO = 200;
	final static int MEDIA_IMAGE = 1;
	final static int MEDIA_VIDEO = 2;

	final static String IMAGE_DIRECTORY = "Entries";

	Uri fileUri;

	Button btn_text_entry, btn_photo_entry, btn_video_entry;
	ImageView imgView;
	VideoView vidView;

	public void init() {
		try {
			AnimationDrawable ad = (AnimationDrawable) findViewById(
					R.id.pnl_add).getBackground();
			ad.setExitFadeDuration(5000);
			ad.setExitFadeDuration(2000);
			ad.start();

			btn_text_entry = (Button) findViewById(R.id.btn_text_entry);
			btn_photo_entry = (Button) findViewById(R.id.btn_photo_entry);
			btn_video_entry = (Button) findViewById(R.id.btn_video_entry);

			addEventHandlers();
		} catch (Exception ex) {
			Toast.makeText(getApplicationContext(), ex.toString(),
					Toast.LENGTH_SHORT).show();
		}
	}

	public void open_editor(String type) {
		try {
			Intent i = new Intent(getApplicationContext(), MyEntry.class);

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

	private void addEventHandlers() {
		// TODO Auto-generated method stub
		btn_text_entry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				open_editor("Text Entry");
			}
		});

		btn_photo_entry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CaptureImage();
			}
		});

		btn_video_entry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CaptureVideo();
			}
		});

	}

	void CaptureVideo() {
		Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		fileUri = getOutputMediaFileUri(MEDIA_VIDEO);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		startActivityForResult(intent, REQUEST_VIDEO);
	}

	void CaptureImage() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = getOutputMediaFileUri(MEDIA_IMAGE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		startActivityForResult(intent, REQUEST_PHOTO);
	}

	boolean isDeviceSupportCamera() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA)) {
			return true;
		} else {
			return false;
		}
	}

	Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	private static File getOutputMediaFile(int type) {
		/*
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY);
				*/
		String FILE_DIR = "Instaiary";
		File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
	            + File.separator + FILE_DIR
	            + File.separator + IMAGE_DIRECTORY);

		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());

		File mediaFile;

		if (type == MEDIA_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}
		return mediaFile;
	}

}
