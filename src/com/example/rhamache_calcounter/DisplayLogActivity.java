    package com.example.rhamache_calcounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class DisplayLogActivity extends Activity {

	// INSTANCE VARIABLES
	
	private ListView logEntries;
	
	// INSTANCE METHODS
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_log);
        
        // make logEntries refer to the list view on the display so we can
        // refer to it
        logEntries = (ListView) findViewById(R.id.LogEntryList);
        
        // Prints some stats on the lower part of the screen
        TextView logInfo = (TextView) findViewById(R.id.textView_log_info);
        logInfo.setText("Total Calories: "+MainActivity.calorieLog.getTotal()+" calories. Overall consumption time: "+
        				MainActivity.calorieLog.getTrackingTime()+" days. Average calories per day: "+
        				MainActivity.calorieLog.averageCalories());
    }
    
	@Override
	protected void onStart() {
		super.onStart();
		
		/* Adapts the log entry for the list view object so it can be displayed
		 * on screen
		 */
		int logs = MainActivity.calorieLog.size();
		LogEntry[] entries = new LogEntry[logs];
		for (int i = 0; i < logs; i++)
		{
			entries[i] = MainActivity.calorieLog.getLog(i);
		}
		final ArrayAdapter<LogEntry> adapter = new ArrayAdapter<LogEntry>(this, R.layout.list_item, entries);
		logEntries.setAdapter(adapter);
	}
	
    /* goBack
     * ------------------
     * Called when 'done' is clicked from the view log screen. Starts
     * MainActivity.java.
     * 
     * parameters: view - 	a view object that references the button that was
     * 						pressed
     * 
     * return values: 		none
     */        	
	public void goBack(View view)
	{
    	Intent intent = new Intent(this,MainActivity.class);
    	startActivity(intent);
    	finish();
	}
}
