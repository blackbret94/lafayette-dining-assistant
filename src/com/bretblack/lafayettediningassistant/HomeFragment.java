package com.bretblack.lafayettediningassistant;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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

public class HomeFragment extends Fragment{
	private MainActivity act;
	private TextView mealCountText;
	private int mealCount;
	private Editor editor;
	private SharedPreferences sharedPreferences;
	public static final String MEALKEY = "mealKey";
	
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
		sharedPreferences = act.getSharedPreferences("Pref", Context.MODE_PRIVATE);
		editor = sharedPreferences.edit();
		mealCountText = (TextView) rootView.findViewById(R.id.meal_count_text);
		updateMealText();
		
		// set up button
		Button button = (Button) rootView.findViewById(R.id.use_a_meal_btn);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            	useAMeal(view);
                //Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

		return rootView;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		updateMealText();
	}
	
	/** Create menu of dining halls */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getActivity().getMenuInflater();
	    inflater.inflate(R.menu.venue_selection_menu, menu);
	}
	
	/** Handle clicks on the menu */
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        /*case R.id.edit:
	            //editNote(info.id);
	            return true;
	        case R.id.delete:
	            //deleteNote(info.id);
	            return true;*/
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	/** Respond to use a meal button click */
	public void useAMeal(View v){
		if (mealCount > 0) {
			mealCount--;
			editor.putInt(HomeFragment.MEALKEY, mealCount);
		    editor.commit();
			
		}
		updateMealText();
		// INFLATE MENU OR DIALOG
		//MenuInflater inflater = act.getMenuInflater();
		//inflater.inflate(R.menu.venue_selection_menu, menu);
	}
	
	/** Updates the meal count TextView */
	public void updateMealText(){
		
		if (sharedPreferences.contains(MEALKEY)){
			Log.v("Preference found","Preference found");
			 mealCount = sharedPreferences.getInt(MEALKEY, 20);
		} else {
			// put in value
			editor.putInt(HomeFragment.MEALKEY, 20);
		    editor.commit();
		}
		
		mealCountText.setText("You have " + mealCount + " meals remaining");
	}
}
