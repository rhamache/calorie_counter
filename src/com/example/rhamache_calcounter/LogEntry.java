package com.example.rhamache_calcounter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogEntry implements Serializable, Comparable<LogEntry> {
	
	/* LogEntry is used to represent one entry in the calorie log.
	 */
	
	// STATIC VARIABLES
	
	private static final long serialVersionUID = 835205398887845166L;
	
	// INSTANCE VARIABLES

	private Date entryDate;
	private int totalCalories;
	private float caloriesPerServing;
	private float servingSize;
	private float totalAmount;
	private String description;
	private String unit;
	private Boolean totalCalWasGiven;
	
	// CONSTRUCTORs
	
	public LogEntry()
	{
		this.entryDate = new Date();
		this.caloriesPerServing = 0f;
		this.servingSize = 0f;
		this.totalAmount = 0f;
		this.description = "null";
		this.unit = "null";
		this.totalCalories = 0;
		this.totalCalWasGiven = false;
	}
	
	public LogEntry(Date date, String caloriesPerServ, String servSize, String amount, String desc, String unit)
	{
		this.entryDate = date;
		this.caloriesPerServing = Float.valueOf(caloriesPerServ);
		this.servingSize = Float.valueOf(servSize);
		this.totalAmount = Float.valueOf(amount);
		this.description = desc;
		this.unit = unit;
		this.totalCalWasGiven = false;
		this.totalCalories = (int) (this.totalAmount * (this.caloriesPerServing/this.servingSize));
	}
	
	public LogEntry(Date date, String desc, String totalCal)
	{
		this.entryDate = date;
		this.description = desc;
		this.totalCalories = Integer.valueOf(totalCal);
		this.totalCalWasGiven = true;		
	}
	
	// INSTANCE METHODS
	
	/* toString
	 * ----------------
	 * Converts this log entry to a string
	 * 
	 * parameters: 				none
	 * 
	 * return values:			A string representation of this LogEntry object
	 */
	@Override
	public String toString()
	{
		String nl = System.getProperty("line.separator");
		String string;
		
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String formattedDate = f.format(this.entryDate);

        if (totalCalWasGiven)
        	string = formattedDate+nl+description+nl+"Consumed "+totalCalories+" calories.";
        else
        	string = formattedDate+nl+description+nl+"Consumed "+totalAmount+unit+" at "+caloriesPerServing+
					" calories per "+servingSize+unit+" for a total of "+totalCalories+" calories.";
		return string;
	}
	
	/* getTotal
	 * ----------------
	 * Returns the total calories that were consumed in this log entry
	 * 
	 * parameters:				none
	 * 
	 * return values:			The total calories in this LogEntry
	 */
	public int getTotal()
	{
		return this.totalCalories;
	}
	
	/* getDate
	 * ----------------
	 * Returns the date of this log entry
	 * 
	 * parameters:				none
	 * 
	 * return values:			The date of this LogEntry
	 */	
	public Date getDate()
	{
		return this.entryDate;
	}

	/* compareTo
	 * ----------------
	 * Compares two log entries. Entries are compared by date with the latest
	 * dates considered greater than earlier dates.
	 * 
	 * parameters:				none
	 * 
	 * return values:			a negative integer, zero, or a positive integer 
	 * 							as this object is less than, equal to, or greater 
	 * 							than the specified object. 
	 */
	@Override
	public int compareTo(LogEntry another)
	{
		return another.entryDate.compareTo(this.entryDate);
	}
	
}