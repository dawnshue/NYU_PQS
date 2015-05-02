package edu.nyu.pqs.stopwatch.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.nyu.pqs.stopwatch.api.IStopwatch;

/**
 * BasicStopwatch is a simple implementation of the IStopwatch interface.
 * It is written to be "unconditionally thread-safe". The objects can be managed with the
 * StopwatchFactory class.
 * @author Vangie Shue
 * @see IStopwatch
 * @see StopwatchFactory
 */
public class BasicStopwatch implements IStopwatch {
  
  private String id;
  private Long startTime = null;
  
  /**
   * The lap times are stored as NANOSECOND times to reduce time calculating conversions.
   */
  private List<Long> lapTimes = new ArrayList<Long>();
  private Object lock = new Object();
  
  /**
   * The constructor creates a new BasicStopwatch with the given String ID.
   * The unique ID therefore cannot be changed once the BasicStopwatch is constructed.
   */
  public BasicStopwatch(String newid) {
    if (newid==null || newid.isEmpty()) {
      throw new IllegalArgumentException("BasicStopwatch id cannot be null or empty");
    } else {
      this.id = newid;
    }
  }

  /**
   * Returns the object's id, which is a required field.
   * @return id of the object
   */
  @Override
  public String getId() {
    return id;
  }

  /**
   * Sets the start time. The method locks on the "lock" in order to prevent
   * multiple threads from attempting to set the start time.
   * @throws IllegalStateException if called when the BasicStopwatch is already running
   */
  @Override
  public void start() {
    synchronized(lock) {
      if(startTime != null) {
        throw new IllegalStateException("Cannot start() a BasicStopwatch that is running.");
      }
      startTime = System.nanoTime();
    }
  }

  /**
   * Stores the time elapsed since the last time lap() was called
   * or since start() was called if this is the first lap. The method will lock on the
   * "lock" object to prevent other threads from inserting a lap time while the current call
   * is calculating lap time based off old times.
   * @throws IllegalStateException if called when the BasicStopwatch isn't running
   */
  @Override
  public void lap() {
    synchronized(lock) {
      Long currentTime = System.nanoTime();
      if(startTime == null) {
        throw new IllegalStateException("Cannot lap() a BasicStopwatch that is not running.");
      }
      Long elapsedTime = currentTime - startTime;
      startTime = currentTime;
      lapTimes.add(elapsedTime);
    }
  }

  /**
   * Sets the stop time (and records one final lap). It locks on the "lock" to prevent
   * other threads from calling lap() or stop() while the current call is attempting to
   * stop the object.
   * @throws IllegalStateException if called when the BasicStopwatch isn't running
   */
  @Override
  public void stop() {
    synchronized(lock) {
      Long currentTime = System.nanoTime();
      if(startTime == null) {
        throw new IllegalStateException("Cannot stop() BasicStopwatch that is not running.");
      }
      Long elapsedTime = currentTime - startTime;
      startTime = null;
      lapTimes.add(elapsedTime);
    }
  }

  /**
   * Resets the BasicStopwatch. If it is running, this method stops the watch and resets it.
   * This also clears all recorded laps. Therefore it must lock the "lock" object.
   */
  @Override
  public void reset() {
    synchronized(lock) {
      startTime = null;
      lapTimes.clear();
    }
  }

  /**
   * Returns a list of lap times (in milliseconds).  This method can be called at
   * any time and will not throw an exception. It locks on the "lock" object to prevent
   * new times from being added while it is creating the copy to return.
   * @return a list of recorded lap times or an empty list if no times are recorded.
   */
  @Override
  public List<Long> getLapTimes() {
    synchronized(lock) {
      List<Long> lapTimesInMillis = new ArrayList<Long>();
      for(Long lap : lapTimes) {
        lapTimesInMillis.add(convertToMillis(lap));
      }
      return lapTimesInMillis;
    }
  }
  
  /**
   * This method simply converts the input nanosecond time to millisecond time.
   * @param nanotime a time interval in nanoseconds
   * @return millisecond equivalent of the nanotime
   */
  private static Long convertToMillis(Long nanotime) {
    return TimeUnit.MILLISECONDS.convert(nanotime, TimeUnit.NANOSECONDS);
  }
  
  /**
   * toString is subject to change, but should at least convey the unique id of the object.
   * @return String representing the BasicStopwatch
   */
  public String toString() {
    return "BasicStopwatch("+id+")";
  }
  
  /**
   * The BasicStopwatch need only compare its unique identifier to determine if the parameter
   * object "o" is equal to it.
   * @return boolean true if the object is a BasicStopwatch with the same unique ID.
   */
  public boolean equals(Object o) {
    if(!(o instanceof BasicStopwatch)) {
      return false;
    }
    BasicStopwatch watchcopy = (BasicStopwatch)o;
    if(id.equals(watchcopy.getId())) {
      return true;
    }
    return false;
  }

  /**
   * The BasicStopwatch hashCode is overwritten to adhere to the logic of equals()
   */
  public int hashCode() {
    return this.id.hashCode();
  }
}
