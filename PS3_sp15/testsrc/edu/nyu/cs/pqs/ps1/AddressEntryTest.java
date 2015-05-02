package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AddressEntryTest {
  private AddressEntry entry;
  private AddressEntry nullentry;

  @Before
  public void executedBeforeEach() {
    EntryName name = new EntryName("V","X");
    EntryPhoneNumber phone = new EntryPhoneNumber("000","111","2222");
    Email email = new Email("v@nyu.edu");
    Address address = new Address("street","NYC","NY","10025","US");
    EntryNote note = new EntryNote("note");
    entry = new AddressEntry(name, phone, email, address, note);
    
    nullentry = new AddressEntry(null, null, null, null, null);
  }

  @Test
  public void testGetSetEntryName() {
    //Test default EntryName
    assertEquals(entry.getEntryName(),new EntryName("V","X"));
    //Test empty EntryName
    entry.setEntryName(new EntryName(""));
    assertEquals(entry.getEntryName(),new EntryName(""));
    //Test null EntryName
    entry.setEntryName(null);
    assertEquals(entry.getEntryName(),null);
  }
  
  @Test
  //BUG: unexpected behavior if EntryName field set to null
  public void testSetEntryName_NullName() {
    //Test null name EntryName
    entry.setEntryName(new EntryName(null));
    assertEquals(entry.getEntryName(),new EntryName(null));    
  }
  
  @Test
  public void testGetSetEntryPhoneNumber() {
    //Test default EntryPhoneNumber
    assertEquals(entry.getEntryPhoneNumber(),new EntryPhoneNumber("000","111","2222"));
    //Test empty EntryPhoneNumber
    entry.setEntryPhoneNumber(new EntryPhoneNumber("","",""));
    assertEquals(entry.getEntryPhoneNumber(),new EntryPhoneNumber("","",""));
    //Test null EntryPhoneNumber
    entry.setEntryPhoneNumber(null);
    assertEquals(entry.getEntryPhoneNumber(),null);

  }
  
  @Test
  //BUG: unexpected behavior in EntryPhoneNumber with null fields
  public void testSetEntryPhoneNumber_NullFields() {
    //Test EntryPhoneNumber with null fields
    entry.setEntryPhoneNumber(new EntryPhoneNumber(null,null,null));
    assertEquals(entry.getEntryPhoneNumber(),new EntryPhoneNumber(null,null,null));
  }
  
  @Test
  public void testGetSetEmail() {
    //Test default Email
    assertEquals(entry.getEmail(),new Email("v@nyu.edu"));
    //Test empty EntryPhoneNumber
    entry.setEmail(new Email(""));
    assertEquals(entry.getEmail(),new Email(""));
    //Test null EntryPhoneNumber
    entry.setEmail(null);
    assertEquals(entry.getEmail(),null);
  }
  
  @Test
  //BUG: unexpected behavior in Email has null email field
  public void testSetEmail_NullField() {
    //Test Email with null field
    entry.setEmail(new Email(null));
    assertEquals(entry.getEmail(),new Email(null));
  }
  
  @Test
  public void testGetSetAddress() {
    //Test default Address
    assertEquals(entry.getAddress(),new Address("street","NYC","NY","10025","US"));
    //Test empty Address
    entry.setAddress(new Address("","","","",""));
    assertEquals(entry.getAddress(), new Address("","","","",""));
    //Test null Address
    entry.setAddress(null);
    assertEquals(entry.getAddress(),null);
  }
  
  @Test
  //BUG: unexpected behavior if Address has null fields
  public void testSetAddress_NullField() {
    //Test Email with null field
    entry.setAddress(new Address(null, null, null, null, null));
    assertEquals(entry.getEmail(),new Address(null, null, null, null, null));
  }
  
  @Test
  public void testGetSetEntryNote() {
    //Test default Address
    assertEquals(entry.getNote(),new EntryNote("note"));
    //Test empty Address
    entry.setEntryNote(new EntryNote(""));
    assertEquals(entry.getNote(), new EntryNote(""));
    //Test null Address
    entry.setEntryNote(null);
    assertEquals(entry.getNote(),null);
  }
  
  @Test
  //BUG: unexpected behavior if EntryNote has null fields
  public void testSetEntryNote_NullField() {
    //Test Email with null field
    entry.setEntryNote(new EntryNote(null));
    assertEquals(entry.getNote(),new EntryNote(null));
  }
  
  @Test
  public void testEquals() {
    //Test with non-AddressEntry object
    assertFalse(entry.equals("a String"));
    //Test with a null
    assertFalse(entry.equals(null));
    //Test with itself
    assertTrue(entry.equals(entry));
    //Test with a copy
    EntryName name = new EntryName("V","X");
    EntryPhoneNumber phone = new EntryPhoneNumber("000","111","2222");
    Email email = new Email("v@nyu.edu");
    Address address = new Address("street","NYC","NY","10025","US");
    EntryNote note = new EntryNote("note");
    AddressEntry entry2 = new AddressEntry(name, phone, email, address, note);
    assertTrue(entry.equals(entry2));
    //Test with a AddressEntry with different lines
    entry.setEntryNote(new EntryNote(""));
    assertFalse(entry.equals(entry2));
    entry.setAddress(new Address("","","","",""));
    assertFalse(entry.equals(entry2));
    entry.setEmail(new Email(""));
    assertFalse(entry.equals(entry2));
    entry.setEntryPhoneNumber(new EntryPhoneNumber("","",""));
    assertFalse(entry.equals(entry2));
    entry.setEntryName(new EntryName(""));
    assertFalse(entry.equals(entry2));
  }
  
  @Test
  //BUG: Equals behaves unexpectedly if a field is null
  public void testEquals_NullField() {
    assertTrue(nullentry.equals(nullentry));
    assertFalse(entry.equals(nullentry));
  }
  
  @Test
  public void testHashCode() {
    //Test itself
    assertEquals(entry.hashCode(),entry.hashCode());
    //Test copied object
    EntryName name = new EntryName("V","X");
    EntryPhoneNumber phone = new EntryPhoneNumber("000","111","2222");
    Email email = new Email("v@nyu.edu");
    Address address = new Address("street","NYC","NY","10025","US");
    EntryNote note = new EntryNote("note");
    AddressEntry entry2 = new AddressEntry(name, phone, email, address, note);
    assertEquals(entry.hashCode(),entry2.hashCode());
  }
  
  @Test
  //BUG: hashCode behaves unexpectedly if a field is null
  public void testHashCode_NullField() {
    assertEquals(nullentry.hashCode(),nullentry.hashCode());
    assertFalse(entry.hashCode()==nullentry.hashCode());
  }
  
  @Test
  public void testToString() {
    //Test itself
    assertEquals(entry.toString(),entry.toString());
    //Test a copy
    EntryName name = new EntryName("V","X");
    EntryPhoneNumber phone = new EntryPhoneNumber("000","111","2222");
    Email email = new Email("v@nyu.edu");
    Address address = new Address("street","NYC","NY","10025","US");
    EntryNote note = new EntryNote("note");
    AddressEntry entry2 = new AddressEntry(name, phone, email, address, note);
    assertEquals(entry.toString(),entry2.toString());
    //Test a different AddressEntry
    entry2.setEmail(new Email(""));
    assertFalse(entry.toString().equals(entry2.toString()));
  }
  
  @Test
  //BUG: toString behaves unexpectedly if a field is null
  public void testToString_NullFields() {
    assertEquals(nullentry.toString(),nullentry.toString());
    assertFalse(entry.toString()==nullentry.toString());
  }

}