package com.example.rhamache_calcounter;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewEntryActivity extends Activity {
	
	// STATIC VARIABLES
	
	// use when one or more entry fields are left empty	
	private static final int EMPTYFIELD = 0;
	// use when description field is left empty
	private static final int NODESCRIPTION = 1;
	// use when invalid date is entered
	private static final int DATEINVALID = 2;
	
	// INSTANCE VARIABLES
	
	private EditText dateEdit;
	private EditText caloriesPerServEdit;
	private EditText servSizeEdit;
	private EditText totalAmountEdit;
	private EditText descEdit;
	private EditText unitEdit;
	private EditText totalCalEdit;
	
	// INSTANCE METHODS
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);
        
        // Ensure 'date' field is filled in with
        // today's date by default
        
        /* Motiejus Osipovas was consulted to write the code snippet below
         * (Up to and including the line dateEdit.setText(formattedDate))  */
        Calendar c = Calendar.getInstance();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = f.format(c.getTime());
        
        dateEdit = (EditText) findViewById(R.id.edit_date);
        dateEdit.setText(formattedDate);
    }

    /* addEntry
     * ------------------
     * Called when the 'done' button is pressed from the 'New Entry' screen.
     * Checks the user's input for errors and then attempts to create a new log
     * entry and add it to the log. Uses String.isEmpty() and thus requires
     * API level 9.
     * 
     * parameters: view - 	a view object that references the button that was pressed
     * 
     * return values: 		none
     */  
	@TargetApi(9)
	public void addEntry(View view)
	{
		Boolean goodEntry = true;
		Boolean totalEntered = false;
		Intent intent = new Intent(this, DisplayLogActivity.class);
		dateEdit = (EditText) findViewById(R.id.edit_date);
		caloriesPerServEdit = (EditText) findViewById(R.id.edit_cal);
		servSizeEdit = (EditText) findViewById(R.id.edit_serv);
		totalAmountEdit = (EditText) findViewById(R.id.edit_total);
		descEdit = (EditText) findViewById(R.id.edit_desc);
		unitEdit = (EditText) findViewById(R.id.edit_units);
		totalCalEdit = (EditText) findViewById(R.id.edit_caltotal);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
		Date date = new Date();

		df.setLenient(false);
		date = df.parse(dateEdit.getText().toString(), new ParsePosition(0));
		if (date == null)
		{
			entryError(DATEINVALID);
			goodEntry = false;
		}

		String caloriesPerServ = caloriesPerServEdit.getText().toString();
		String servSize = servSizeEdit.getText().toString();
		String totalAmount = totalAmountEdit.getText().toString();
		String desc = descEdit.getText().toString();
		String unit = unitEdit.getText().toString();
		String totalCal = totalCalEdit.getText().toString();
		
		// make sure all fields are filled in
		if((caloriesPerServ.isEmpty() || servSize.isEmpty() || totalAmount.isEmpty() 
			 || unit.isEmpty()) && totalCal.isEmpty())
		{
			entryError(EMPTYFIELD);
			goodEntry = false;
		}
		
		if (desc.isEmpty())
		{
			entryError(NODESCRIPTION);
			goodEntry = false;
		}
		
		if(!totalCal.isEmpty())
		{
			totalEntered = true;
		}
		
		if(goodEntry)
		{
			LogEntry newEntry;
			// create the entry and add it to the log
			if(totalEntered)
				newEntry = new LogEntry(date, desc, totalCal);
			else
				newEntry = new LogEntry(date, caloriesPerServ, servSize, totalAmount, desc, unit);
			MainActivity.calorieLog.addEntry(newEntry);
			MainActivity.calorieLog.sortByDate();
		
			// now save the log
			try
			{
				MainActivity.save(this);
			} catch(Exception e)
			{
				e.printStackTrace();
			}
		
			// view the log
			startActivity(intent);
			finish();
			}
		
	}

	/* entryError
	 * ------------------
	 * Called when an error in user input occurs. Displays a pop up alert
	 * dialog that provides a textual description of the error.
	 * 
	 * parameters: errorcode - 	an integer that corresponds to a specific error
	 * 							message to display. Go to the top of this class
	 * 							file to view all available errorcodes
	 * 
	 * return values:			none
	 */
	private void entryError(int errorcode)
	{
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		
		adb.setTitle("Entry Error");
		switch (errorcode) {
			case EMPTYFIELD:	adb.setMessage("One or more necessary fields were left empty");
								break;
			case NODESCRIPTION: adb.setMessage("A description must be provided");
								break;
			case DATEINVALID: adb.setMessage("Date must be provide in format yyyy-MM-dd");
		}
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
}
