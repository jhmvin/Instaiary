package sql;

import java.util.ArrayList;
import java.util.HashMap;

import com.jhonmelvin.perello.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.list_row, null);
		}

		TextView title = (TextView) vi.findViewById(R.id.txt_title); // title
		TextView date = (TextView) vi.findViewById(R.id.txt_date); // artist
		TextView time = (TextView) vi.findViewById(R.id.txt_time); // artist
		TextView type = (TextView) vi.findViewById(R.id.txt_type); // duration
		ImageView thumb_image = (ImageView) vi.findViewById(R.id.img_thumb); // thumb

		HashMap<String,String> song = new HashMap<String, String>();
        song = data.get(position);
		
		title.setText(song.get("title"));
		date.setText(song.get("date"));
		time.setText(song.get("time"));
		type.setText(song.get("type"));
		if(song.get("type").equalsIgnoreCase("Text Entry")){
			thumb_image.setImageResource(R.drawable.text_entry);
		}else if (song.get("type").equalsIgnoreCase("Photo Entry")){
			thumb_image.setImageResource(R.drawable.photo_entry);
		}else if (song.get("type").equalsIgnoreCase("Video Entry")){
			thumb_image.setImageResource(R.drawable.video_entry);
		}else{
			thumb_image.setImageResource(R.drawable.default_entry);
		}
		

		return vi;
	}

	// **************
	private Activity activity;
	private ArrayList<HashMap<String, String>> data;
	private static LayoutInflater inflater = null;

	public LazyAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

}
