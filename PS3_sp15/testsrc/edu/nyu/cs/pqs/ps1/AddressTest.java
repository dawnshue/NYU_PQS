package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class AddressTest {
  private Address address;
  private Address address2;

  @Before
  public void executedBeforeEach() {
    //Creates an Address to be used by any Test for Address
    address = new Address("some street","NYC","NY","10025","US");
    //Creates a second Address with the same parameters
    address2 = new Address("some street","NYC","NY","10025","US");
  }
  
  @Test
  public void testGetStreet() {
    assertEquals("some street", address.getStreet());
  }
  
  @Test
  public void testSetStreet() {
    address.setStreet("new street");
    assertEquals("new street", address.getStreet());
    //Test Empty String
    address.setStreet("");
    assertEquals("", address.getStreet());
    //Test null Field
    address.setStreet(null);
    assertEquals(null, address.getStreet());
  }
  
  @Test
  public void testGetCity() {
    assertEquals("NYC", address.getCity());
  }
  
  @Test
  public void testSetString() {
    //method name should probably be setCity instead of setString (too late now)
    address.setString("New York");
    assertEquals("New York", address.getCity());
    //Test Empty String
    address.setString("");
    assertEquals("", address.getCity());
    //Test null Field
    address.setString(null);
    assertEquals(null, address.getCity());
  }
  
  @Test
  public void testGetState() {
    assertEquals("NY", address.getState());
  }
  
  @Test
  public void testSetState() {
    address.setState("NY2");
    assertEquals("NY2", address.getState());
    //Test Empty String
    address.setState("");
    assertEquals("", address.getState());
    //Test null Field
    address.setState(null);
    assertEquals(null, address.getState());
  }
  
  @Test
  public void testGetZip() {
    assertEquals("10025", address.getZip());
  }
  
  @Test
  public void testSetZip() {
    address.setZip("10031");
    assertEquals("10031", address.getZip());
    //Test Empty String
    address.setZip("");
    assertEquals("", address.getZip());
    //Test null Field
    address.setZip(null);
    assertEquals(null, address.getZip());
  }
  
  @Test
  public void testGetCountry() {
    assertEquals("US", address.getCountry());
  }
  
  @Test
  public void testSetCountry() {
    address.setCountry("USA");
    assertEquals("USA", address.getCountry());
    //Test Empty String
    address.setCountry("");
    assertEquals("", address.getCountry());
    //Test null Field
    address.setCountry(null);
    assertEquals(null, address.getCountry());
  }
  
  @Test
  public void testEquals() {
    //Compare initial addresses, which were created with the same constructor
    assertTrue(address.equals(address2));
    
    //Compare non-Address object
    assertFalse(address.equals("a string"));
    
    //Compare an address2 with a different parameter
    address2.setCountry("USA");
    assertFalse(address.equals(address2));
    address2 = new Address("some street","NYC","NY","10025","US");
    address2.setZip("0");
    assertFalse(address.equals(address2));
    address2 = new Address("some street","NYC","NY","10025","US");
    address2.setState("VA");
    assertFalse(address.equals(address2));
    address2 = new Address("some street","NYC","NY","10025","US");
    address2.setString("Albany");
    assertFalse(address.equals(address2));
    
    //Compare a new address constructed with empty strings
    Address address4 = new Address("","","","","");
    assertFalse(address.equals(address4));
  }
  
  @Test
  // BUG: Equals cannot handle Address containing null fields
  public void testEquals_NullFields() {
    //Compare a new address constructed with nulls
    Address address3 = new Address(null, null, null, null, null);
    assertFalse(address.equals(address3));
  }
  
  @Test
  public void testHashCode() {
    //Compare hash codes of initial addresses, which are identical
    assertEquals(address.hashCode(), address2.hashCode());
    
    //Test if a field is an empty string (addresses are different)
    address2.setStreet("");
    assertFalse(address.hashCode()==address2.hashCode());
  }
  
  @Test
  // BUG: hashCode cannot handle null fields
  public void testHashCode_NullFields() {
    //Test behavior if address has a field that is null
    Address address3 = new Address(null, null, null, null, null);
    assertFalse(address.hashCode()==address3.hashCode());
  }
  
  @Test
  public void testToString() {
    //Test behavior with initial addresses (same constructor)
    assertEquals(address.toString(),address2.toString());
    //Test behavior if field changed to a different String
    address2.setCountry("");
    assertFalse(address.toString().equals(address2.toString()));
    //Test behavior with a null field
    address2.setCountry(null);
    assertFalse(address.toString().equals(address2.toString()));
  }

}
