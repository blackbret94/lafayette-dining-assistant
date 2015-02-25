package com.bretblack.mealplanassistant;

import com.bretblack.mealplanassistant.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment {
	private String filename = "conTextSMSBackup";
	private DbAdapter db;
	private Activity act;
	private SharedPreferences sharedPreferences;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		// get activity
		 act = getActivity();

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        //sharedPreferences = act.getSharedPreferences("Pref", Context.MODE_PRIVATE);
		 sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        
        // set preference listener for reset meals
        Preference pref = getPreferenceManager().findPreference("reset_meals");
        pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (preference.getKey().equals("reset_meals")) {
                    resetMeals();
                    return true;
                }
                return false;
            }
        });
        
        // set preference listener for reset records
        /*pref = getPreferenceManager().findPreference("reset_records");
        pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (preference.getKey().equals("reset_records")){
                	resetVenue();
                	return true;
                }
                return false;
            }
        });*/
    }
	
	/** Gets the current meal plan value */
	public int getMealPlanValue(){
		// get value
		int val = Integer.parseInt(sharedPreferences.getString("meal_plan_options", "20"));
		
		// return
		return val;
	}
	
	public void resetMeals(){
		if (sharedPreferences.contains(HomeFragment.MEALKEY)){
			// reset the meals
			Editor editor = sharedPreferences.edit();
			editor.putInt(HomeFragment.MEALKEY, getMealPlanValue());
		    editor.commit();
		    
		    // toast a message
		    Toast.makeText(getActivity().getApplicationContext(), "Your meals have been reset",
		    		   Toast.LENGTH_SHORT).show();
		}
    }
	
	public void resetVenue(){
		Toast.makeText(getActivity().getApplicationContext(), "This feature is under construction",
	    		   Toast.LENGTH_SHORT).show();
	}
}
