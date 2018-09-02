package com.jhonmelvin.perello;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import sql.FloatingActionButton;
import sql.LazyAdapter;
import sql.Me;
import sql.StampFormatter;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import dao.Entry;
import dao.EntryAccess;
import dao.UserAccess;

public class Main extends Activity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Me.getInstance().getStoredValues().put("arrange", "recent");
		mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));

		AnimationDrawable ad = (AnimationDrawable) findViewById(
				R.id.drawer_layout).getBackground();
		ad.setExitFadeDuration(5000);
		ad.setExitFadeDuration(2000);
		ad.start();

		addEvents();
		Me.getInstance().getStoredValues().put("filter", "photo");
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();

	}

	UserAccess ua;

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = "Memories";
			Me.getInstance().getStoredValues().put("filter", "all");
			break;
		case 2:
			mTitle = "Text";
			Me.getInstance().getStoredValues().put("filter", "text");
			break;
		case 3:
			mTitle = "Photos";
			Me.getInstance().getStoredValues().put("filter", "photo");
			break;
		case 4:
			mTitle = "Videos";
			Me.getInstance().getStoredValues().put("filter", "video");
			break;
		case 5:
			// change password
			Intent i = new Intent(getApplicationContext(), ChangePassword.class);
			i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			this.startActivity(i);
			break;
		case 6:
			logout();
			break;
		}

	}

	public void view_entry(int id) {

	}

	// ********************************************

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		onNavigationDrawerItemSelected(0);
		mTitle = "Memories";
	}

	public void logout() {
		ua.updateGlobal("logged", "0");
		Intent i = new Intent(getApplicationContext(), Login.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(i);
		finish();
	}

	FloatingActionButton fabButton;

	public void addEvents() {
		ua = new UserAccess();

		fabButton = new FloatingActionButton.Builder(this)
				.withDrawable(getResources().getDrawable(R.drawable.pencil3))
				.withButtonColor(Color.rgb(219, 60, 50))
				.withGravity(Gravity.BOTTOM | Gravity.RIGHT)
				.withMargins(0, 0, 15, 15).create();

		fabButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// onNavigationDrawerItemSelected(0);

				counter_check();
			}
		});

	}

	public Integer counter_check() {
		Integer r = -1;
		try {
			Calendar c = Calendar.getInstance();
			EntryAccess ea = new EntryAccess();
			String month = String.valueOf(c.getTime().getMonth());
			String day = String.valueOf(c.getTime().getDate());
			String year = String.valueOf(c.getTime().getYear());
			List<Entry> checksize = ea.counter_check(Me.getInstance()
					.getUsernickname(), month, day, year);
			r = checksize.size();
			// Toast.makeText(getApplicationContext(),String.valueOf(checksize.size()),
			// Toast.LENGTH_SHORT).show();
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(getApplicationContext(), e.toString(),
					Toast.LENGTH_SHORT).show();
		}
		if (Me.getInstance().subscription.equalsIgnoreCase("FREE")) {
			if (r > 0) {
				Toast.makeText(
						getApplicationContext(),
						"FREE Version only allows one post per day please buy the PRO version for unlimitted posts.",
						Toast.LENGTH_SHORT).show();
			} else if (r == 0) {
				add_entry();
			}
		}else{
			add_entry();
		}

		return r;
	}

	/********************************************************/
	public void add_entry() {
		Intent i = new Intent(getApplicationContext(), AddEntry.class);
		i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		this.startActivity(i);
	}

	/********************************************************/

	public static ArrayList<HashMap<String, String>> populate(String param,
			String order) {
		ArrayList<HashMap<String, String>> entryList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> entry = new HashMap<String, String>();

		/*******************************************/
		/* Load list from database */
		EntryAccess ea = new EntryAccess();
		List<Entry> all_entries = ea.find("author", Me.getInstance()
				.getUsernickname());
		if (order.equals("recent")) {
			for (int x = (all_entries.size() - 1); x >= 0; x--) {
				Entry current_entry = all_entries.get(x);
				String date_string = StampFormatter.getInstance().format_month(
						current_entry.getMonth())
						+ " "
						+ StampFormatter.getInstance().format_day(
								current_entry.getDay())
						+ ", "
						+ StampFormatter.getInstance().format_year(
								current_entry.getYear());

				entry = new HashMap<String, String>();
				entry.put("id", current_entry.getEntry_no().toString());
				entry.put("title", current_entry.getTitle());
				entry.put("date", date_string);
				entry.put(
						"time",
						StampFormatter.getInstance().format_time(
								current_entry.getHours(),
								current_entry.getSeconds()));
				entry.put("type", current_entry.getType());
				entryList.add(entry);

			}
		} else {
			for (int x = 0; x < all_entries.size(); x++) {
				Entry current_entry = all_entries.get(x);
				String date_string = StampFormatter.getInstance().format_month(
						current_entry.getMonth())
						+ " "
						+ StampFormatter.getInstance().format_day(
								current_entry.getDay())
						+ ", "
						+ StampFormatter.getInstance().format_year(
								current_entry.getYear());

				entry = new HashMap<String, String>();
				entry.put("id", current_entry.getEntry_no().toString());
				entry.put("title", current_entry.getTitle());
				entry.put("date", date_string);
				entry.put(
						"time",
						StampFormatter.getInstance().format_time(
								current_entry.getHours(),
								current_entry.getSeconds()));
				entry.put("type", current_entry.getType());
				entryList.add(entry);

			}
		}

		if (param.equalsIgnoreCase("ALL")) {
			return entryList;
		} else if (param.equalsIgnoreCase("photo")) {
			ArrayList<HashMap<String, String>> photoEntry = new ArrayList<HashMap<String, String>>();
			for (int x = 0; x < entryList.size(); x++) {
				if (entryList.get(x).get("type")
						.equalsIgnoreCase("Photo Entry")) {
					photoEntry.add(entryList.get(x));
				}
			}
			return photoEntry;
		} else if (param.equalsIgnoreCase("text")) {
			ArrayList<HashMap<String, String>> textEntry = new ArrayList<HashMap<String, String>>();
			for (int x = 0; x < entryList.size(); x++) {
				if (entryList.get(x).get("type").equalsIgnoreCase("Text Entry")) {
					textEntry.add(entryList.get(x));
				}
			}
			return textEntry;
		} else if (param.equalsIgnoreCase("video")) {
			ArrayList<HashMap<String, String>> videoEntry = new ArrayList<HashMap<String, String>>();
			for (int x = 0; x < entryList.size(); x++) {
				if (entryList.get(x).get("type")
						.equalsIgnoreCase("Video Entry")) {
					videoEntry.add(entryList.get(x));
				}
			}
			return videoEntry;
		}

		return null;

	}

	// *******************************************

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.

			getMenuInflater().inflate(R.menu.main, menu);

			restoreActionBar();

			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(getApplicationContext(), Me.about_message,
					Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		LazyAdapter adapter;
		ListView list;
		Activity act;

		Button btn_search, btn_recent, btn_older;
		EditText txt_search;
		private String arrange = "recent";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			this.list = (ListView) rootView.findViewById(R.id.list);
			this.btn_older = (Button) rootView.findViewById(R.id.btn_older);
			this.btn_recent = (Button) rootView.findViewById(R.id.btn_recent);
			this.btn_search = (Button) rootView.findViewById(R.id.btn_search);
			this.txt_search = (EditText) rootView.findViewById(R.id.txt_search);

			/*
			 * btn_older.setVisibility(View.GONE);
			 * btn_recent.setVisibility(View.GONE);
			 */
			btn_search.setVisibility(View.GONE);
			txt_search.setVisibility(View.GONE);

			this.act = this.getActivity();
			this.arrange = Me.getInstance().getStoredValues().get("arrange");
			Me.getInstance().setList_values(
					populate(Me.getInstance().getStoredValues().get("filter"),
							this.arrange));

			adapter = new LazyAdapter(this.act, Me.getInstance()
					.getList_values());

			setAdapater(this.list, adapter);
			addEvents();
			return rootView;
		}

		public void addEvents() {
			btn_older.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					set_arrange("older");
					Me.getInstance().getStoredValues().put("arrange", "older");
				}
			});

			btn_recent.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					set_arrange("recent");
					Me.getInstance().getStoredValues().put("arrange", "recent");
				}
			});
		}

		public void set_arrange(String arr) {
			this.arrange = arr;
			((Main) this.act).onNavigationDrawerItemSelected(99);
		}

		// LIST CLICK HERE
		public void setAdapater(ListView lv, LazyAdapter adapter) {
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					// Toast.makeText(getActivity(), Integer.toString(position),
					// Toast.LENGTH_SHORT).show();
					Me.getInstance().view_entry((long) position);
				}
			});

		}

		// LIST CLICK HERE

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((Main) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));

		}
	}

}
