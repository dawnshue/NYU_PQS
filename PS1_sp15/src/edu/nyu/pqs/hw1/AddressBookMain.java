package edu.nyu.pqs.hw1;

import java.io.IOException;

public class AddressBookMain {

  public static void main(String[] args) throws Exception {
    AddressBook book = new AddressBook();
    AddressBookEntry first = new AddressBookEntry();
    first.setName("Vangie");
    first.setEmailAddress("vangie@gmail.com");
    first.setNote("HI");
    first.setPhoneNumber("703+577a");
    first.setPostalAddress("something");
    book.addEntry(first);
    book.saveAddressBookToFile("output.txt");
    for(AddressBookEntry e : book.searchEntries("v")) {
      System.out.println(e.getName());
      System.out.println(e.getEmailAddress());
      System.out.println(e.getEntryID());
      System.out.println(e.getPhoneNumber());
      System.out.println(e.getPostalAddress());
    }
    
    AddressBook input = AddressBook.readAddressBookFromFile("output.txt");
    for(AddressBookEntry e : input.searchEntries("v")) {
      System.out.println(e.getName());
      System.out.println(e.getEmailAddress());
      System.out.println(e.getEntryID());
      System.out.println(e.getPhoneNumber());
      System.out.println(e.getPostalAddress());
    }

  }

}
