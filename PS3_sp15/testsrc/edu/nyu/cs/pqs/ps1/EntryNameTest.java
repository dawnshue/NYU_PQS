package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EntryNameTest {
  
  private EntryName firstonly;
  private EntryName fullname;
  private EntryName emptyname;
  private EntryName nullname;

  @Before
  public void executedBeforeEach() {
    firstonly = new EntryName("First");
    fullname = new EntryName("First","Last");
    emptyname = new EntryName("","");
    nullname = new EntryName(null, null);
  }

  @Test
  public void testSetFirstName() {
    //Test method for EntryName with only a firstname
    firstonly.setFirstName("First2");
    assertEquals(firstonly.getFirstName(),"First2");
    
    //Test method for an EntryName with a fullname
    fullname.setFirstName("First2");
    assertEquals(fullname.getFirstName(),"First2");
    
    //Test method for empty string
    fullname.setFirstName("");
    assertEquals(fullname.getFirstName(),"");
    
    //Test method for null
    fullname.setFirstName(null);
    assertEquals(null,fullname.getFirstName());
  }
  
  @Test
  public void testGetFirstName_withoutSetting() {
    //Test method for EntryName with only a firstname
    assertEquals(firstonly.getFirstName(),"First");
    
    //Test method for an EntryName with a fullname
    assertEquals(fullname.getFirstName(),"First");
    
    //Test method for empty name
    assertEquals("",emptyname.getFirstName());
    
    //Test method for a null name
    assertEquals(null,nullname.getFirstName());
  }
  
  @Test
  public void testSetLastName() {
    //Test method for EntryName with only a firstname
    firstonly.setLastName("Last2");
    assertEquals(firstonly.getLastName(),"Last2");
    
    //Test method for an EntryName with a fullname
    fullname.setLastName("Last2");
    assertEquals(fullname.getLastName(),"Last2");
    
    //Test method for empty string
    fullname.setLastName("");
    assertEquals("",fullname.getLastName());
    
    //Test method for null
    fullname.setLastName(null);
    assertEquals(null,fullname.getLastName());
  }
  
  @Test
  public void testGetLastName_withSetting() {
    //Test method for EntryName with only a firstname
    assertEquals(null,firstonly.getLastName());
    
    //Test method for an EntryName with a fullname
    assertEquals("Last",fullname.getLastName());
    
    //Test method for empty name
    assertEquals("",emptyname.getLastName());
    
    //Test method for a null name
    assertEquals(null,nullname.getLastName());
  }
  
  @Test
  public void testEquals() {
    //Test method for a different object (a String)
    assertFalse(firstonly.equals("First"));
    
    //Test method for same objects with the different defaults
    assertTrue(firstonly.equals(firstonly));
    
    assertTrue(fullname.equals(fullname));
    
    assertTrue(emptyname.equals(emptyname));
    
    EntryName nullfirst = new EntryName(null,"Last");
    assertTrue(nullfirst.equals(nullfirst));
    
    //Test method for an EntryName with same First but not Last
    assertFalse(firstonly.equals(fullname));
    
    assertFalse(firstonly.equals(new EntryName("First","")));
    
    //Test where object2 has null first name and different last name
    assertFalse(nullfirst.equals(new EntryName(null,"Last2")));
    
    //Test where object2 has null last name & different first name
    assertFalse(firstonly.equals(new EntryName("First2",null)));
    
    //Test where objects are fullnames but different values
    assertTrue(fullname.equals(new EntryName("First", "Last")));
    assertFalse(fullname.equals(new EntryName("First2", "Last")));
    assertFalse(fullname.equals(new EntryName("First", "Last2")));
  }
  
  @Test
  //BUG: behavior is unexpected for null first name
  public void testEquals_NullFirstNameOnly() {
    //Compare names with same last name, but different firstname
    //where object2 has a nullfirst name
    EntryName nullfirst = new EntryName(null,"Last");
    assertFalse(fullname.equals(nullfirst));
  }
  
  @Test
  //BUG: behavior is unexpected for null name
  public void testEquals_NullFirstAndLastNames() {
    //Compare same object but have null fields
    assertTrue(nullname.equals(nullname));
  }
  
  @Test
  public void testHashCode() {
    //Test for same object, different params
    assertEquals(fullname.hashCode(),fullname.hashCode());
    assertEquals(emptyname.hashCode(),emptyname.hashCode());
    //Note: current implementation of hashCode will have two EntryNames
    //with swapped first & last names be equal, this is not necessarily
    //"wrong" but it is less effective
  }
  
  @Test
  //BUG: behavior unexpected if EntryName has any null fields
  public void testHashCode_NullFirstAndLast() {
    assertEquals(nullname.hashCode(),nullname.hashCode());
  }
  
  @Test
  //BUG: behavior unexpected if EntryName has any null fields
  public void testHashCode_NullFirstOnly() {
    assertEquals(firstonly.hashCode(),firstonly.hashCode());
  }
  
  @Test
  //BUG: behavior unexpected if EntryName has any null fields
  public void testHashCode_NullLastOnly() {
    EntryName lastonly = new EntryName(null,"Last");
    assertEquals(lastonly.hashCode(),lastonly.hashCode());
  }
  
  @Test
  public void testToString() {
    //Test for default EntryNames
    assertEquals(firstonly.toString(),firstonly.toString());
    assertEquals(fullname.toString(),fullname.toString());
    assertEquals(emptyname.toString(),emptyname.toString());
    assertEquals(nullname.toString(),nullname.toString());
  }

}
