/**
 * Copyright (c) 2015, Vangie Shue
 */

package edu.nyu.pqs.hw1;
import java.io.Serializable;
import java.util.Arrays;

/**
 * PhoneNumber is a protected class for representing phone numbers as needed by AddressBookEntry.
 * PhoneNumber is represented as a String of digits or chars given by the acceptedChars field.
 * We consider any combination of accepted characters as a valid phoneNumber.
 * @author Vangie
 * @see AddressBookEntry
 */
class PhoneNumber implements Serializable {
  private String phoneNumber;
  //Since Apple considers the following valid in a phone number, we will too.
  private static final Character[] acceptedChars = {';',',','+','*','#'};

  PhoneNumber() {
    phoneNumber = "";
  }
  
  /**
   * If calling the constructor with a String parameter, the constructor will call the
   * setPhoneNumber method in order to validate the string input first.
   * @param s the String which to set the phoneNumber field to.
   */
  PhoneNumber(String s) {
    setPhoneNumber(s);
  }
  
  /**
   * Calls the convertToPhoneNumber() method to correct the input String
   * before setting the phoneNumber.
   * @param s String which to set the phoneNumber field as.
   */
  void setPhoneNumber(String s) {
    phoneNumber = convertToPhoneNumber(s);
  }

  String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * This method iterates through the input String and removes any invalid characters.
   * @param s the String in which to set the phoneNumber field to.
   * @return String a valid phoneNumber String.
   */
  private static String convertToPhoneNumber(String s) {
    StringBuilder phoneNumber = new StringBuilder(s);
    int index = 0;
    Character temp;
    while (index < phoneNumber.length()) {
      temp = phoneNumber.charAt(index);
      if (!Character.isDigit(temp) && !Arrays.asList(acceptedChars).contains(temp)) {
        phoneNumber.deleteCharAt(index);
      } else {
        index++;
      }
    }
    return phoneNumber.toString();
  }

}
