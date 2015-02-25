package com.bretblack.lafayettediningassistant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
//import com.bretblack.lafayettediningassistant.R;
//import com.bretblack.lafayettediningassistant.*;

public class MainActivity extends Activity {
	private SettingsFragment settingsFragment;
	private HashMap<String, Integer> venueMap;
	private HomeFragment hf;

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	// SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	// ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// set up fragments
		if (savedInstanceState == null) {
			// create fragment
			hf = new HomeFragment();

			// add
			getFragmentManager().beginTransaction().add(R.id.container, hf)
					.commit();
		}

		// create preferences
		PreferenceManager.setDefaultValues(this, R.xml.preferences, true);
		settingsFragment = new SettingsFragment();

		// Set up the action bar.
		// final ActionBar actionBar = getActionBar();
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		/*
		 * REMOVED TABS // set up hashmap venueMap = createVenueMap();
		 * loadVenueMap();
		 * 
		 * // Create the adapter that will return a fragment for each of the
		 * three // primary sections of the activity. mSectionsPagerAdapter =
		 * new SectionsPagerAdapter(getFragmentManager());
		 * 
		 * // Set up the ViewPager with the sections adapter. mViewPager =
		 * (ViewPager) findViewById(R.id.pager);
		 * mViewPager.setAdapter(mSectionsPagerAdapter);
		 * 
		 * // When swiping between different sections, select the corresponding
		 * // tab. We can also use ActionBar.Tab#select() to do this if we have
		 * // a reference to the Tab. mViewPager .setOnPageChangeListener(new
		 * ViewPager.SimpleOnPageChangeListener() {
		 * 
		 * @Override public void onPageSelected(int position) {
		 * actionBar.setSelectedNavigationItem(position); } });
		 * 
		 * // For each of the sections in the app, add a tab to the action bar.
		 * for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) { //
		 * Create a tab with text corresponding to the page title defined by //
		 * the adapter. Also specify this Activity object, which implements //
		 * the TabListener interface, as the callback (listener) for when //
		 * this tab is selected. actionBar.addTab(actionBar.newTab()
		 * .setText(mSectionsPagerAdapter.getPageTitle(i))
		 * .setTabListener(this)); }
		 */
	}

	/** Uses a meal, calling the method in the associated fragment */
	public void useAMeal(View v) {
		// get adapter and get fragment
		// SectionsPagerAdapter sectionsAdapter =
		// (SectionsPagerAdapter)mViewPager.getAdapter();
		// HomeFragment hf = (HomeFragment)sectionsAdapter.getItem(0);
		hf.useAMeal(v);

	}

	/** Create the empty hashmap */
	public HashMap<String, Integer> createVenueMap() {
		HashMap<String, Integer> vm = new HashMap<String, Integer>();
		vm.put("Marquis Hall", 0);
		vm.put("Upper Farinon", 0);
		vm.put("Lower Farinon", 0);
		vm.put("Gilbert\'s", 0);
		vm.put("Simon\'s", 0);
		vm.put("Skillman Cafe", 0);
		return vm;
	}

	/** Load venue statistics from file */
	public void loadVenueMap() {
		try {
			File file = new File(getDir("data", MODE_PRIVATE), "map");
			ObjectInputStream inputStream = new ObjectInputStream(
					new FileInputStream(file));
			venueMap = (HashMap<String, Integer>) inputStream.readObject();
			inputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			saveVenueMap();
		}
	}

	/** Save venue statistics to file */
	public void saveVenueMap() {
		try {
			File file = new File(getDir("data", MODE_PRIVATE), "map");
			ObjectOutputStream outputStream = new ObjectOutputStream(
					new FileOutputStream(file));
			outputStream.writeObject(venueMap);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent intent = new Intent(this, SettingsActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	// Override
	/*
	 * public void onTabSelected(ActionBar.Tab tab, FragmentTransaction
	 * fragmentTransaction) { // When the given tab is selected, switch to the
	 * corresponding page in // the ViewPager.
	 * mViewPager.setCurrentItem(tab.getPosition()); }
	 * 
	 * @Override public void onTabUnselected(ActionBar.Tab tab,
	 * FragmentTransaction fragmentTransaction) { }
	 * 
	 * @Override public void onTabReselected(ActionBar.Tab tab,
	 * FragmentTransaction fragmentTransaction) { }
	 */

	/**
	 * Gets the venue map
	 * 
	 * @return venueMap
	 */
	public HashMap<String, Integer> getVenueMap() {
		return venueMap;
	}

	/**
	 * Gets the settings fragment
	 * 
	 * @return Instance of settings fragment
	 */
	public SettingsFragment getSettingsFragment() {
		return settingsFragment;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	/*
	 * public class SectionsPagerAdapter extends FragmentPagerAdapter {
	 * 
	 * public SectionsPagerAdapter(FragmentManager fm) { super(fm); }
	 * 
	 * @Override public Fragment getItem(int position) { // getItem is called to
	 * instantiate the fragment for the given page. // Return a
	 * PlaceholderFragment (defined as a static inner class // below).
	 * switch(position){ case 0: return HomeFragment.newInstance(position + 1);
	 * case 1: return VenueFragment.newInstance(position + 1); } return
	 * InfoFragment.newInstance(position + 1); }
	 * 
	 * @Override public int getCount() { // Show 3 total pages. return 3; }
	 * 
	 * @Override public CharSequence getPageTitle(int position) { Locale l =
	 * Locale.getDefault(); switch (position) { case 0: return
	 * getString(R.string.home_section_title).toUpperCase(l); case 1: return
	 * getString(R.string.venue_section_title).toUpperCase(l); case 2: return
	 * getString(R.string.info_section_title).toUpperCase(l); } return null; } }
	 */

}
