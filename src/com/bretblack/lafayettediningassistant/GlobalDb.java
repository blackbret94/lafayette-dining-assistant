package com.bretblack.lafayettediningassistant;

import android.app.Application;
import android.content.res.Configuration;

public class GlobalDb extends Application {
	/** Singleton instance */
	private static GlobalDb singleton;
	/** Instance of the database adapter */
	private DbAdapter mDbHelper;
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
 
	@Override
	public void onCreate() {
		super.onCreate();
		singleton = this;
		mDbHelper = new DbAdapter(this);
	}
 
	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}
 
	@Override
	public void onTerminate() {
		super.onTerminate();
	}
	
	public GlobalDb getInstance(){
		return singleton;
	}
	
	public DbAdapter getDbAdapter(){
		return mDbHelper;
	}
	
}
