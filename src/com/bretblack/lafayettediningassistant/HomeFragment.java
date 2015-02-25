package com.bretblack.lafayettediningassistant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment{
	private MainActivity act;
	private TextView mealCountText;
	private TextView lastMealText;
	private int mealCount;
	private Editor editor;
	private SharedPreferences sharedPreferences;
	private String lastMeal;
	private HashMap<String,Integer> venueMap;
	public static final String MEALKEY = "mealKey";
	public static final String LASTMEALKEY = "lastMealKey";
	
	/** The fragment argument representing the section number for this
	 * fragment. */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/** Returns a new instance of this fragment for the given section number.*/
	public static HomeFragment newInstance(int sectionNumber) {
		HomeFragment fragment = new HomeFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);
		
		// save activity
		act = (MainActivity)getActivity();
		
		// set up shared preferences
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
		editor = sharedPreferences.edit();
		mealCountText = (TextView) rootView.findViewById(R.id.meal_count_text);
		lastMealText = (TextView) rootView.findViewById(R.id.last_meal_text);
		updateMealText();
		
		// set up button
		Button button = (Button) rootView.findViewById(R.id.use_a_meal_btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	//inflateVenueMenu();
            	useAMeal(view);
            	((MainActivity)getActivity()).saveVenueMap();
            }
        });
        
        // get hashmap
        venueMap = ((MainActivity)getActivity()).getVenueMap();
        
		return rootView;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		updateMealText();
	}
	
	/** Respond to use a meal button click */
	public void useAMeal(View v){
		if (mealCount > 0) {
			mealCount--;
			editor.putInt(HomeFragment.MEALKEY, mealCount);
		    editor.commit();
		    editor.putString(HomeFragment.LASTMEALKEY, getTime());
		    
		    // toast
		    Toast.makeText(getActivity().getApplicationContext(), "You have used a meal",
		    		   Toast.LENGTH_SHORT).show();
			
		}
		updateMealText();
	}
	
	/** Updates the meal count TextView */
	public void updateMealText(){
		// update meal count
		if (sharedPreferences.contains(MEALKEY)){
			Log.v("Preference found","Preference found");
			 mealCount = sharedPreferences.getInt(MEALKEY, 20);
		} else {
			// put in value
			editor.putInt(HomeFragment.MEALKEY, 20);
		    editor.commit();
		}
		
		// update time of last meal
		if (sharedPreferences.contains(LASTMEALKEY)){
			Log.v("Preference found","Preference found");
			lastMeal = sharedPreferences.getString(LASTMEALKEY, getTime());
		} else {
			// put in value
			editor.putString(HomeFragment.LASTMEALKEY, getTime());
		    editor.commit();
		}
		
		mealCountText.setText("You have " + mealCount + " meals remaining");
		lastMealText.setText("Your last meal was used " + lastMeal +".");
	}
	
	
	/** Inflates the venue menu */
	public void inflateVenueMenu(){
		// get list of venue titles
		venueMap = ((MainActivity)getActivity()).getVenueMap();
		final CharSequence[] items = generateVenueNames(venueMap);
		
		// instantiate
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		// build alert
		builder.setTitle("Pick a Venue");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	// get prev value
		    	int v = venueMap.get(items[item]);
		    	
		    	// increment the value
		    	v++;
		    	
		    	// update value
		    	venueMap.put((String)items[item],v);
		    }
		});
		
		// create
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	/** Gets the names of venues */
	public CharSequence[] generateVenueNames(HashMap<String,Integer> map){
		// create array of strings
		ArrayList<String> al = new ArrayList<String>(venueMap.size());
		
		// iterate
		Iterator it = venueMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        al.add((String)pair.getKey());
	        Log.v("Value", "The value is: " + pair.getValue());
	    }
	    
	    // convert to charsequence
	    CharSequence[] items = al.toArray(new CharSequence[al.size()]);

	    // return
		return items;
	}
	
	/** Gets the current time and date in a user-friendly format */
	public String getTime(){
		return java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
	}
}
