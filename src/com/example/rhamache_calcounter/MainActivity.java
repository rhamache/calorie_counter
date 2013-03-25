package com.example.rhamache_calcounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
	
	// STATIC VARIABLES
	
	public static CalorieLog calorieLog;
	private static final String FILENAME = "calorielog.sav";
	
	// INSTANCE METHODS
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calorieLog = new CalorieLog();
        try
		{
			MainActivity.load(this);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
    }
    
    /* newEntry
     * ------------------
     * Called when 'new entry' is clicked from the main menu screen. Starts
     * NewEntryActivity.java.
     * 
     * parameters: view - 	a view object that references the button that was
     * 						pressed
     * 
     * return values: 		none
     */ 
    public void newEntry(View view){
    	Intent intent = new Intent(this, NewEntryActivity.class);
    	startActivity(intent);
    }
    
    /* viewLog
     * ------------------
     * Called when 'view log' is clicked from the main menu screen. Starts
     * DisplayLogActivity.java.
     * 
     * parameters: view - 	a view object that references the button that was
     * 						pressed
     * 
     * return values: 		none
     */     
    public void viewLog(View view)
    {
    	Intent intent = new Intent(this, DisplayLogActivity.class);
    	startActivity(intent);
    }
    
    /* deleteMode
     * ------------------
     * Called when 'delete entries' is clicked from the main menu screen. Starts
     * DeleteEntriesActivity.java.
     * 
     * parameters: view - 	a view object that references the button that was
     * 						pressed
     * 
     * return values: 		none
     */        
    public void deleteMode(View view)
    {
    	Intent intent = new Intent(this, DeleteEntriesActivity.class);
    	startActivity(intent);
    }
    
    /* aboutMessage
     * ------------------
     * Called when 'about' is clicked from the main menu screen. Displays an info
     * message
     * 
     * parameters: view - 	a view object that references the button that was
     * 						pressed
     * 
     * return values: 		none
     */      
    public void aboutMessage(View view)
    {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		
		adb.setTitle("About");
		
		adb.setMessage("Created in 2013 by Ryan Hamacher");
		
		adb.setNeutralButton("Okay",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int id) {
				// if this button is clicked, just close
				// the dialog box and do nothing
				dialog.cancel();
			}
		});

		// create alert dialog
		AlertDialog alertDialog = adb.create();

		// show it
		alertDialog.show();
    }
    
    // STATIC METHODS
    
    /* load
     * ------------------
     * Loads the calorie log from a file on disk
     * 
     * parameters: c -		the context that called this method
     * 
     * return values:		none
     * 
     * throws:				Exception
     */
	public static void load(Context c) throws Exception
	{
		File file = c.getFileStreamPath(FILENAME);
		if(file.exists())
		{
			FileInputStream fis = c.openFileInput(FILENAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			MainActivity.calorieLog = (CalorieLog) is.readObject();
			is.close();
			
			// Make sure we are not loading a null object
			
			if(MainActivity.calorieLog == null)
				MainActivity.calorieLog = new CalorieLog();
		}
	}
	
    /* save
     * ------------------
     * Saves the calorie log to a file on disk
     * 
     * parameters: c -		the context that called this method
     * 
     * return values:		none
     * 
     * throws:				Exception
     */
	public static void save(Context c) throws Exception
	{
		FileOutputStream fos = c.openFileOutput(FILENAME, Context.MODE_PRIVATE);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(MainActivity.calorieLog);
		os.flush();
		os.close();
	}
}
