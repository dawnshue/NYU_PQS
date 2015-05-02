package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * The StopwatchFactory is a thread-safe factory class for IStopwatch objects.
 * It maintains references to all created IStopwatch objects and provides a
 * convenient method for getting a list of those objects.
 * @author Vangie Shue, Michael Schidlowsky
 * @see IStopwatch
 */
public class StopwatchFactory {
  
  /**
   * In order to store all the existing IStopwatch objects in the current execution, we use
   * a static list object.
   */
  private static List<IStopwatch> watches = new ArrayList<IStopwatch>();

	/**
	 * Creates and returns a new IStopwatch object. The object is also stored in watches.
	 * This method locks on the list of IStopwatches to prevent other threads from mutating it
	 * before it finishes adding the new IStopwatch. 
	 * @param id The identifier of the new object
	 * @return The new IStopwatch object
	 * @throws IllegalArgumentException if <code>id</code> is empty, null, or already
   *     taken.
	 */
	public static IStopwatch getStopwatch(String id) {
	  if (id==null || id.isEmpty()) {
	    throw new IllegalArgumentException("An IStopwatch id cannot be null or empty");
	  }
	  
	  /**
	   * In order to ensure all IStopwatches have a unique ID, we lock on our watches list to
	   * prevent another thread from mutating it midway through.
	   */
	  synchronized(watches) {
	    if(hasMatchingStopwatch(id)) {
	      throw new IllegalArgumentException("An IStopwatch with that id already exists");
	    }
	    IStopwatch newwatch = new BasicStopwatch(id);
	    watches.add(newwatch);
	    return newwatch;
	  }
	}
	
	/**
	 * This method checks if any existing IStopwatch objects have a matching ID with the input.
	 * We assume that any method calling this is locking on the "watches" list to ensure it does
	 * not mutate during the iteration.
	 * @param id The id we wish to compare with existing IStopwatch id's
	 * @return boolean true if an existing IStopwatch has the same id, else return false
	 */
	private static boolean hasMatchingStopwatch(String id) {
	  for(IStopwatch watch : watches) {
	    if(watch.getId().equals(id)) {
	      return true;
	    }
	  }
    return false;
	}

	/**
	 * Returns a list of all created stopwatches
	 * @return a List of all created IStopwatch objects.  Returns an empty
	 * list if no IStopwatches have been created.
	 */
	public static List<IStopwatch> getStopwatches() {
		return watches;
	}
}
