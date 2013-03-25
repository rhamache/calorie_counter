package com.example.rhamache_calcounter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class DeleteEntriesActivity extends Activity {

	private ListView logEntries;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_entries);
        
        // make logEntries refer to the list view on the display so we can
        // refer to it
        logEntries = (ListView) findViewById(R.id.LogEntryList);
    }

	@Override
	protected void onStart() {
		super.onStart();
				
		
    	try {
			MainActivity.save(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
		
		/* Set up a listener to respond to clicks of elements of the list
		 * Deletes whatever entry was clicked.
		 */
		logEntries.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				String selectedEntry_Text = ((TextView)view).getText().toString();
				int index = MainActivity.calorieLog.getEntryIndexByText(selectedEntry_Text);
				MainActivity.calorieLog.removeEntry(index);
				MainActivity.calorieLog.sortByDate();
				onStart();
			}
		});
		
	}
	
    /* goBack
     * ------------------
     * Called when 'done' is clicked from the delete entries screen. Starts
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
