package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EntryPhoneNumberTest {

  private EntryPhoneNumber phone;
  private EntryPhoneNumber emptyphone;
  private EntryPhoneNumber nullphone;

  @Before
  public void executedBeforeEach() {
    phone = new EntryPhoneNumber("000","111","2222");
    emptyphone = new EntryPhoneNumber("","","");
    nullphone = new EntryPhoneNumber(null,null,null);
  }
  
  @Test
  public void testGetAreaCode() {
    assertEquals("000",phone.getAreaCode());
    assertEquals("",emptyphone.getAreaCode());
    assertEquals(null,nullphone.getAreaCode());
  }

  @Test
  public void testSetAreaCode() {
    //Test with new String
    phone.setAreaCode("---");
    assertEquals("---",phone.getAreaCode());
    //Test with empty String
    phone.setAreaCode("");
    assertEquals("",phone.getAreaCode());
    //Test with null
    phone.setAreaCode(null);
    assertEquals(null,phone.getAreaCode());
  }
  
  @Test
  public void testGetPrefix() {
    assertEquals("111",phone.getPrefix());
    assertEquals("",emptyphone.getPrefix());
    assertEquals(null,nullphone.getPrefix());
  }
  
  @Test
  public void testSetPrefix() {
    //Test with new String
    phone.setPrefix("---");
    assertEquals("---",phone.getPrefix());
    //Test with empty String
    phone.setPrefix("");
    assertEquals("",phone.getPrefix());
    //Test with null
    phone.setPrefix(null);
    assertEquals(null,phone.getPrefix());
  }
  
  @Test
  public void testGetNumber() {
    assertEquals("2222",phone.getNumber());
    assertEquals("",emptyphone.getNumber());
    assertEquals(null,nullphone.getNumber());
  }
  
  @Test
  public void testSetNumber() {
    //Test with new String
    phone.setNumber("----");
    assertEquals("----",phone.getNumber());
    //Test with empty String
    phone.setNumber("");
    assertEquals("",phone.getNumber());
    //Test with null
    phone.setNumber(null);
    assertEquals(null,phone.getNumber());
  }
  
  @Test
  public void testEquals() {
    //Test on other Object
    assertFalse(phone.equals("a String"));
    assertFalse(nullphone.equals(null));
    //Test with default EntryNote objects
    assertTrue(phone.equals(phone));
    assertTrue(emptyphone.equals(emptyphone));
    assertFalse(emptyphone.equals(phone));
    //Test with different objects, same text
    assertTrue(emptyphone.equals(new EntryPhoneNumber("","","")));
    assertFalse(emptyphone.equals(new EntryPhoneNumber("0","","")));
    assertFalse(emptyphone.equals(new EntryPhoneNumber("","0","")));
    assertFalse(emptyphone.equals(new EntryPhoneNumber("","","0")));
  }
  
  @Test
  //BUG: Equals method has unexpected behavior if any field is null
  public void testEquals_NullFields() {
    assertTrue(nullphone.equals(nullphone));
    assertFalse(emptyphone.equals(nullphone));
  }
  
  @Test
  public void testHashCode() {
    assertEquals(phone.hashCode(),phone.hashCode());
    assertEquals(emptyphone.hashCode(),emptyphone.hashCode());
    assertEquals(emptyphone.hashCode(),(new EntryPhoneNumber("","","")).hashCode());
  }
  
  @Test
  //BUG: hashcode method has unexpected behavior if field is null
  public void testHashCode_NullFields() {
    assertEquals(nullphone.hashCode(),nullphone.hashCode());
  }
  
  @Test
  public void testToString() {
    assertTrue(phone.toString().equals(phone.toString()));
    assertTrue(phone.toString().equals("000-111-2222"));
    assertTrue(emptyphone.toString().equals(emptyphone.toString()));
    assertTrue(emptyphone.toString().equals("--"));
    assertTrue(nullphone.toString().equals(nullphone.toString()));
  }
  
  @Test
  //BUG: toString has unexpected behavior if field is null
  public void testToString_NullFields() {
    assertTrue(nullphone.toString().equals("--"));
  }

}
