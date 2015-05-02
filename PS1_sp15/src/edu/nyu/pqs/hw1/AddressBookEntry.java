/**
 * Copyright (c) 2015, Vangie Shue
 */

package edu.nyu.pqs.hw1;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AddressBookEntry objects are used to store a single contact's information.
 * They can be saved together in an AddressBook. The class implements Comparable
 * so that AddressBook can compare two AddressBookEntry objects.
 * @author Vangie Shue
 * @see AddressBook
 * @see EmailAddress
 * @see PhoneNumber
 */
public class AddressBookEntry implements Comparable, Serializable {
  
  /**
   * AddressBookEntry objects can store the contact information in a name, postalAddress,
   * phoneNumber, emailAddress, or note field. While there are no limitations on the String
   * used for the contact's name, postal address or note, they are kept private in case
   * future updates will set more rigorous standards.
   */
  private String name;
  private String postalAddress;
  private PhoneNumber phoneNumber;
  private EmailAddress emailAddress;
  private String note;

  /**
   * In order to distinguish AddressBookEntry objects that have the exact same fields above,
   * we use a static counter to determine each contact's unique ID.
   */
  private static AtomicInteger nextId = new AtomicInteger();
  private final int uniqueID;

  public AddressBookEntry() {
    uniqueID = nextId.incrementAndGet();
    name = "";
    postalAddress = "";
    phoneNumber = new PhoneNumber("");
    emailAddress = new EmailAddress();
    note = "";
  }

  /**
   * Any valid String is currently accepted as a name field.
   * @param s the String which to set the name as.
   */
  public void setName(String s) {
    name = s;
  }

  /**
   * Any valid String is currently accepted as the postalAddress field.
   * @param s the String which to set the contact's postal address as.
   */
  public void setPostalAddress(String s) {
    postalAddress = s;
  }

  /**
   * The String input will be corrected to adhere to the PhoneNumber object requirements.
   * @param s the String which to set the contact's phone number as.
   */
  public void setPhoneNumber(String s) {
    phoneNumber.setPhoneNumber(s);
  }

  /**
   * The String input will not be accepted if it is an invalid emailAddress string.
   * @param s the String which to set the contact' email address as.
   * @throws Exception if the input string is not a valid email address string.
   */
  public void setEmailAddress(String s) throws Exception {
    emailAddress.setEmailAddress(s);
  }

  /**
   * Any valid String is currently accepted for the note field.
   * @param s the String which to set the contact's note as.
   */
  public void setNote(String s) {
    note = s;
  }

  /**
   * The uniqueID field is only viewable by package members in order to allow for ID checking
   * in classes that utilize the AddressBookEntry object.
   * @return int the unique ID of the AddressBookEntry object.
   */
  int getEntryID() {
    return uniqueID;
  }

  public String getName() {
    return name;
  }
  
  /**
   * Because PostalAddress is a protected class, we return the string representation.
   * @return String the postalAddress field as a String.
   */
  public String getPostalAddress() {
    return postalAddress.toString();
  }

  /**
   * Because PhoneNumber is a protected class, we return the string representation.
   * @return String the phoneNumber field as a String.
   */
  public String getPhoneNumber() {
    return phoneNumber.getPhoneNumber();
  }

  /**
   * Because EmailAddress is a protected class, we return the string representation.
   * @return String the emailAddress field as a String.
   */
  public String getEmailAddress() {
    return emailAddress.getEmailAddress();
  }

  public String getNote() {
    return note;
  }

  /**
   * This method checks all the fields of the AddressBookEntry object.
   * If any fields have a match it will return true, otherwise false.
   * @param s the String pattern to search for in the fields.
   * @return boolean indicating whether or not a pattern match was found.
   */
  public boolean hasFieldMatch(String s) {
    if (name.indexOf(s) >= 0 || postalAddress.indexOf(s) >= 0 
        || phoneNumber.getPhoneNumber().indexOf(s) >= 0
        || emailAddress.getEmailAddress().indexOf(s) >= 0 || note.indexOf(s) >= 0) {
      return true;
    }
    return false;
  }
  
  /**
   * We compare the unique IDs of the AddressBookEntry objects as the means
   * of comparing two AddressBookEntry objects.
   */
  @Override
  public int compareTo(Object o) {
    AddressBookEntry input = (AddressBookEntry)o;
    return Integer.compare(this.getEntryID(), input.getEntryID());
  }
}
