package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class AddressBookTest {
  private AddressBook book;
  
  private AddressEntry entry;
  private AddressEntry copyentry;
  private AddressEntry nullentry;
  private AddressEntry emptyentry;
  
  private EntryName name;
  private EntryPhoneNumber phone;
  private Email email;
  private Address address;
  private EntryNote note;

  @Before
  public void executedBeforeEach() {
    //Creates a generic AddressBook
    book = new AddressBook();
    //Create a generic Entry
    name = new EntryName("V","X");
    phone = new EntryPhoneNumber("000","111","2222");
    email = new Email("v@nyu.edu");
    address = new Address("street","NYC","NY","10025","US");
    note = new EntryNote("note");
    entry = new AddressEntry(name, phone, email, address, note);
    //Create a copy object of the entry
    copyentry = new AddressEntry(name, phone, email, address, note);
    //Create entry with empty fields
    EntryName name2 = new EntryName("","");
    EntryPhoneNumber phone2 = new EntryPhoneNumber("","","");
    Email email2 = new Email("");
    Address address2 = new Address("","","","","");
    EntryNote note2 = new EntryNote("");
    emptyentry = new AddressEntry(name2, phone2, email2, address2, note2);
    //Create an Entry with null fields
    nullentry = new AddressEntry(null, null, null, null, null);
  }
  
  @Test
  public void testAddNewEntry() {
    assertTrue(book.addNewEntry(entry));
    assertTrue(book.addNewEntry(nullentry));
    //BUG? NOT SURE WHETHER THIS BEHAVIOR (return value) IS INTENDED OR NOT FOR SAME ENTRY RE-ADD
    assertTrue(book.addNewEntry(entry));
    //BUG? NOT SURE WHETHER THIS BEHAVIOR (return value) IS INTENDED OR NOT
    assertTrue(book.addNewEntry(null));
  }
  
  
  @Test
  public void testRemoveOldEntry() {
    //Test single entry insert
    assertTrue(book.addNewEntry(entry));
    assertTrue(book.removeOldEntry(entry));
    assertFalse(book.removeOldEntry(entry));
    assertFalse(book.removeOldEntry(nullentry));
    //Test duplicates
    assertTrue(book.addNewEntry(entry));
    assertTrue(book.addNewEntry(copyentry));
    assertTrue(book.removeOldEntry(entry));
    assertTrue(book.removeOldEntry(copyentry));
  }
  
  @Test
  //BUG? Unexpected behavior for AddressEntry with null fields
  public void testRemoveOldEntry_EntryWithNulls() {
    assertTrue(book.addNewEntry(nullentry));
    assertTrue(book.removeOldEntry(nullentry));
  }
  
  @Test
  public void testSearchEntries() {
    List<AddressEntry> list;
    //Test empty book
    list = book.search(new EntryName("V","X"));
    assertEquals(list.size(),0);
    //Test single entry
    book.addNewEntry(entry);
    list = book.search(new EntryName("V","X"));
    assertEquals(list.size(),1);
    list = book.search(new EntryName("Vangie"));
    assertEquals(list.size(),0);
    list = book.search(emptyentry);
    assertEquals(list.size(),0);
    //Test with a null
    list = book.search(null);
    assertEquals(list.size(),0);
    //Test with non-valid Object
    list = book.search("a plain string");
    assertEquals(list.size(),0);
    //Test 2 copy entry & 1 different entry
    book.addNewEntry(copyentry);
    book.addNewEntry(emptyentry);
    list = book.search(emptyentry);
    assertEquals(list.size(),1);
    //Test Phone search
    list = book.search(new EntryPhoneNumber("000","111","2222"));
    assertEquals(list.size(),2);
    //Test Email search
    list = book.search(new Email("vvvv@nyu.edu"));
    assertEquals(list.size(),0);
    list = book.search(email);
    assertEquals(list.size(),2);
    //Test Address search
    list = book.search(address);
    assertEquals(list.size(),2);
    //Test EntryNote search
    list = book.search(new EntryNote("huzzah"));
    assertEquals(list.size(),0);
    list = book.search(note);
    assertEquals(list.size(),2);
  }
  
  @Test
  //BUG: unexpected behavior if any fields are null
  public void testSearchEntries_NullFields() {
    List<AddressEntry> list;
    book.addNewEntry(entry);
    book.addNewEntry(copyentry);
    //An AddressBook-related object with a null field fails
    list = book.search(new EntryNote(null));
    assertEquals(list.size(),0);
    //Nullentry will cause any search in "book" to fail
    book.addNewEntry(nullentry);
    //Cannot search for its nullentry
    list = book.search(nullentry);
    //Cannot search for a field that exists in the "valid" entries
    list = book.search(note);
    assertEquals(list.size(),2);
    
  }
  
  @Test
  public void testToString() {
    //Blank book
    assertEquals(book.toString(),book.toString());
    //Book with 1 entry
    assertTrue(book.addNewEntry(entry));
    assertEquals(book.toString(),book.toString());
    //Book with 2 entries
    assertTrue(book.addNewEntry(copyentry));
    assertEquals(book.toString(),book.toString());
  }
  
  @Test
  //BUG? Unexpected behavior in toString after Entry with nulls added
  public void testToString_EntryWithNulls() {
    //Add entry with nulls to Book
    assertTrue(book.addNewEntry(entry));
    assertTrue(book.addNewEntry(nullentry));
    assertEquals(book.toString(),book.toString());
  }
  
  @Test
  //BUG? Cannot read AddressBook back from the file
  public void testSaveReadAddressBook() {
    assertTrue(book.addNewEntry(entry));
    File f = new File(System.getProperty("user.dir") + "AddressBook.txt");
    try {
      f = book.saveAddressBookToFile();
    } catch (IOException e) {
      fail("IOException on saveAddressBookToFile.");
    }
    
    AddressBook book2 = new AddressBook();
    //NoSuchElementException Error occurs with readAddressBookFromFile
    try {
      book2 = book2.readAddressBookFromFile(f);
    } catch (IOException e) {
      fail("Should not have an IO Exception for this file.");
    }
    assertTrue(book.equals(book2));
  }
}
