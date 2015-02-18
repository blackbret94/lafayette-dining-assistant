package com.bretblack.lafayettediningassistant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class VenueFragment extends ListFragment {
	/** The fragment argument representing the section number for this
	 * fragment. */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/** Returns a new instance of this fragment for the given section number.*/
	public static VenueFragment newInstance(int sectionNumber) {
		VenueFragment fragment = new VenueFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_venue, container,
				false);
		
		// get hashmap
		HashMap<String,Integer> venueMap = ((MainActivity)getActivity()).getVenueMap();
		
		// create array of strings
		ArrayList<String> al = new ArrayList<String>(venueMap.size());
		
		// iterate
		Iterator it = venueMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        al.add(pair.getKey()+": " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	    
	    // sort the array list here later, potentially
	    
		// set up adaptor
		ArrayAdapter adaptor = new ArrayAdapter(getActivity(),R.layout.venue_list_item,al);
	    //new SimpleAdapter(getActivity(), venueMap, R.layout.fragment_venue, from, to);
		setListAdapter(adaptor);

		return rootView;
	}

}
