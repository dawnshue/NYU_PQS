/**
 * Copyright (c) 2015, Vangie Shue
 */

package edu.nyu.pqs.hw1;

import java.io.Serializable;

/**
 * EmailAddress is a protected class that conveys a valid email address.
 * A valid email address is considered a String with an "@" symbol
 * and at least one "." symbol after the "@" sign.
 * Otherwise it must be an empty String.
 * @author Vangie Shue
 * @see AddressBookEntry
 */
class EmailAddress implements Serializable {
  private String email;

  EmailAddress() {
    email = "";
  }

  /**
   * Constructing an EmailAddress object with a String will invoke the
   * setEmailAddress() method to ensure that the String is a valid email address.
   * @param s the String which to set the email as.
   * @throws Exception is thrown if the String is not a valid email address.
   */
  EmailAddress(String s) throws Exception {
    setEmailAddress(s);
  }
  
  /**
   * setEmailAddress determines whether or not the input String is a valid email address.
   * If it is valid, the email field will be set to the String.
   * @param s the String which to set the email as.
   * @throws Exception thrown if the String is not a valid email address.
   */
  void setEmailAddress(String s) throws Exception {
    if(validateEmailAddress(s)) {
      email = s;
    } else {
      throw new Exception("Invalid email address.");
    }
  }
  
  /**
   * validateEmailAddress tests to see if the input String is valid.
   * If the String is not an empty String, it must contain an "@" symbol and
   * at least one "." after the "@" sign.
   * @param s String to validate.
   * @return boolean indicating whether or not the String is a valid email.
   */
  private boolean validateEmailAddress(String s) {
    if(s.equals("")) {
      return true;
    } else if(s.indexOf("@")>0 && s.indexOf(".")>s.indexOf("@")) {
      return true;
    } else {
      return false;
    }
  }
  
  /**
   * Returns the string representation of the email address.
   * @return String the email address as a String.
   */
  String getEmailAddress() {
    return email;
  }

}
