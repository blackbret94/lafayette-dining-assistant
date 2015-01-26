package com.bretblack.lafayettediningassistant;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragment {
	private String filename = "conTextSMSBackup";
	private DbAdapter db;
	private Activity act;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // open database
        GlobalDb mApp = (GlobalDb) getActivity().getApplicationContext();
		db = mApp.getDbAdapter();
		
		// get activity
		 act = getActivity();

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        
        // set preference listener for export
        Preference pref = getPreferenceManager().findPreference("export");
        pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (preference.getKey().equals("export")) {
                    exportFavorites();
                    return true;
                }
                return false;
            }
        });
                
        // set preference listener for import
        pref = getPreferenceManager().findPreference("import");
        pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (preference.getKey().equals("import")) {
                	importFavorites();
                    return true;
                }
                return false;
            }
        });
        
     // set preference listener for delete all
        pref = getPreferenceManager().findPreference("delete_all");
        pref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                if (preference.getKey().equals("delete_all")) {
                	deleteAllFavorites();
                    return true;
                }
                return false;
            }
        });
    }
	
	/** Parses the database and saves it to a text file */
	public void exportFavorites(){
		db.open();
		
		// create and fill file
		try {
			 FileOutputStream fileout=act.openFileOutput(filename, Context.MODE_PRIVATE);
			 
			 // access database
			 Cursor c = db.fetchAllNotes();
			 c.moveToFirst();
			 
			 // write strings
			 do{
				 String s = c.getString(2)+"\n ";
				 fileout.write(s.getBytes());
				 c.moveToNext();
			 } while (!c.isLast());
			 fileout.close();
			 
			 //display file saved message
			 Toast.makeText(act.getBaseContext(), "File saved successfully!",
			 Toast.LENGTH_SHORT).show();
			 
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// close DB
		db.close();
	}
	
	/** Parses a text file and saves it to the database */
	public void importFavorites(){
		Calendar c = Calendar.getInstance();
		db.open();
		
		try {
		    BufferedReader inputReader = new BufferedReader(new InputStreamReader(
		            act.openFileInput(filename)));
		    String inputString;
		    StringBuffer stringBuffer = new StringBuffer();                
		    while ((inputString = inputReader.readLine()) != null) {
		    	if (!inputString.equals("")) db.createNote(c.getTime().toString(), inputString);
		    }
		    
		    // temp toast
		    Toast.makeText(act.getBaseContext(), "File successfully imported",
					 Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		// close DB
		db.close();
	}
	
	/** Creates a dialog box making sure the user really wants to delete their favorites */
	public void deleteAllFavorites(){
		AlertDialog.Builder builder = new AlertDialog.Builder(act);

	    builder.setTitle("Confirm");
	    builder.setMessage("Are you sure you would like to delete your favorites?");

	    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

	        public void onClick(DialogInterface dialog, int which) {
	            // Delete and close
	        	actionDeleteAll();
	            dialog.dismiss();
	        }

	    });

	    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            // Do nothing
	            dialog.dismiss();
	        }
	    });

	    AlertDialog alert = builder.create();
	    alert.show();
		
	}
	
	// deletes the favorites
	private void actionDeleteAll(){
		Activity act = getActivity();
		db.open();
		db.deleteTable();
		db.close();
		
		Toast.makeText(act.getBaseContext(), "Your favorited items have been removed",
				 Toast.LENGTH_SHORT).show();
	}
}
