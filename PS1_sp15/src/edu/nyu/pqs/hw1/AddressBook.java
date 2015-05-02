/**
 * Copyright (c) 2015, Vangie Shue
 */

package edu.nyu.pqs.hw1;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.TreeSet;

/**
 * AddressBook is a service that allows the user to store contacts 
 * by means of AddressBookEntry objects.
 * @author Vangie Shue
 * @see AddressBookEntry
 */
public class AddressBook implements Serializable {

  /**
   * AddressBooks use TreeSets in order to maintain an organized set of 
   * AddressBookEntry objects for more efficient searching. 
   */
  private TreeSet<AddressBookEntry> entries;

  public AddressBook() {
    entries = new TreeSet<AddressBookEntry>();
  }
  
  /**
   * Any valid (non-null) AddressBookEntry object.
   * @param e the AddressBookEntry object to be added to the AddressBook.
   * @return a boolean indicating whether the AddressBook was able to add the contact.
   */
  public boolean addEntry(AddressBookEntry e) {
    if (e == null) {
      return false;
    }
    return entries.add(e);
  }

  /**
   * Removes the passed entry object from the AddressBook.
   * @param e the AddressBookEntry object to be removed from the AddressBook
   * @return a boolean indicating whether or not the entry was removed.
   */
  public boolean removeEntry(AddressBookEntry e) {
    return entries.remove(e);
  }
  
  /**
   * This method will search all fields of all AddressBookEntry objects in entries. 
   * Any entries that have a parameter containing the string will be returned together as a set.
   * @param s the String which we will search every Entry field for match. It can be a regex.
   * @return TreeSet<Entry> the set of all entries that matched the pattern string.
   */
  public TreeSet<AddressBookEntry> searchEntries(String regex) {
    TreeSet<AddressBookEntry> matches = new TreeSet<AddressBookEntry>();
    for (AddressBookEntry e : entries) {
      if(e.hasFieldMatch(regex)) {
        matches.add(e);
      }
    }
    return matches;
  }
  
  /**
   * Saves the AddressBook object to the file given by the input parameter, path.
   * @param path the file which the AddressBook object will be saved at.
   * @throws IOException if the path is invalid or the file cannot be written to.
   */
  public void saveAddressBookToFile(String path) throws IOException {
    FileOutputStream saveFile = new FileOutputStream(path);
    ObjectOutputStream save = new ObjectOutputStream(saveFile);
    save.writeObject(this);
    save.close();
  }
  
  /**
   * Reads and returns the AddressBook object from the file indicated by the parameter, path.
   * @param path the file which we will try to read the AddressBook object from.
   * @return AddressBook as read by the Object InputStream.
   * @throws IOException if the input path is invalid or the file cannot be read.
   * @throws ClassNotFoundException if the object read is not a valid AddressBook object.
   */
  public static AddressBook readAddressBookFromFile(String path) throws IOException, ClassNotFoundException {
    FileInputStream inputFile = new FileInputStream(path);
    ObjectInputStream input = new ObjectInputStream(inputFile);
    AddressBook fileAddressBook = (AddressBook)input.readObject();
    input.close();
    return fileAddressBook;
  }

}
