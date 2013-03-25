package com.example.rhamache_calcounter;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class CalorieLog implements Serializable {
	/* CalorieLog provides the necessary data structure to store multiple
	 * log entries
	 */
	
	// STATIC VARIABLES
	
	private static final long serialVersionUID = 1L;
	
	// INSTANCE VARIABLES
	
	private ArrayList<LogEntry> theLog;
	
	// CONSTRUCTORS
	
	public CalorieLog()
	{
		this.theLog = new ArrayList<LogEntry>();
	}
	
	/* addEntry
	 * ----------------
	 * Adds a entry to the calorie log
	 * 
	 * parameters: le -			The LogEntry to add
	 * 
	 * return values:			none
	 */	
	public void addEntry(LogEntry le)
	{
		theLog.add(le);
	}
	
	/* getArray
	 * ----------------
	 * Converts the calorie log to an array
	 * 
	 * parameters: 				none
	 * 
	 * return values:			An array of LogEntries containing every
	 * 							entry currently in the calorie log
	 */
	public LogEntry[] getArray()
	{
		return (LogEntry[]) theLog.toArray();
	}
	
	/* size
	 * ----------------
	 * Returns the size or number of entries in the calorie log
	 * 
	 * parameters: 				none
	 * 
	 * return values:			Number of entries currently in this log
	 */	
	public int size()
	{
		return theLog.size();
	}
	
	/* getLog
	 * ----------------
	 * Returns the LogEntry at the given index
	 * 
	 * parameters: i -			Index of the log entry to retrieve
	 * 
	 * return values:			The log entry at the given index
	 */	
	public LogEntry getLog(int i)
	{
		LogEntry l = theLog.get(i);
		return l;
	}
	
	/* getTotal
	 * ----------------
	 * Returns the total amount of calories from all log entries in the log
	 * 
	 * parameters: 				none
	 * 
	 * return values:			Total amount of calories from all entries
	 */
	public int getTotal()
	{
		int runningTotal = 0;
		
		for(int i = 0; i < theLog.size(); i++)
		{
			runningTotal += theLog.get(i).getTotal();			
		}
		return runningTotal;
	}

	/* getTrackingTime
	 * ----------------
	 * Returns the length of time (in days) that this log has been tracking over
	 * 
	 * parameters: 				none
	 * 
	 * return values:			Number of days that the log has been tracking. If
	 * 							the log is empty, returns 0.
	 */
	public int getTrackingTime()
	{
		if (theLog.isEmpty())
			return 0;
		else
		{
			Date earliest = theLog.get(0).getDate();
			Date latest = theLog.get(0).getDate();
			Date currDate = theLog.get(0).getDate();
		
			for(int i = 0; i < theLog.size(); i++)
			{
				currDate = theLog.get(i).getDate();
				if (earliest.compareTo(currDate) > 0)
					earliest = currDate;
				if (latest.compareTo(currDate) < 0)
					latest = currDate;
			}

			// diff will contain the difference in milliseconds
			long diff = latest.getTime() - earliest.getTime();
			
			// 86400000 is the number of milliseconds in a day. Add 1 to account for current day
			return (int) (diff / 86400000)+1;
		}
 	}
	
	/* averageCalories
	 * ----------------
	 * Converts this log entry to a string
	 * 
	 * parameters: 				none
	 * 
	 * return values:			A string representation of this LogEntry object
	 */
	public float averageCalories()
	{
		float total = (float) this.getTotal();
		int days = this.getTrackingTime();
		if (this.getTrackingTime() != 0)
			return (float) total/days;
		else
			return 0;
		
	}
	
	/* getEntryIndexByText
	 * ----------------
	 * Searches for a log entry that matches the given string
	 * 
	 * parameters: text - 		String the search for
	 * 
	 * return values:			the index of the log entry that corresponds to the
	 * 							given string
	 */	
	public int getEntryIndexByText(String text)
	{
		int index = -1;
		for(int i = 0; i < theLog.size(); i++)
		{
			if(text.equals(theLog.get(i).toString()))
				index = i;
		}
		return index;
	}
	
	/* removeEntry
	 * ----------------
	 * Removes the log entry at the given index
	 * 
	 * parameters: index -		index of log entry to remove
	 * 
	 * return values:			none
	 */	
	public void removeEntry(int index)
	{
		theLog.remove(index);
	}
	
	/* sortByDate
	 * ----------------
	 * Sorts the calorie log by date with later dates appearing first
	 * 
	 * parameters: 				none
	 * 
	 * return values:			none
	 */
	public void sortByDate()
	{
		Collections.sort(theLog);
	}
}
	
