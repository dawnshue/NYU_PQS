package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EmailTest {
  private Email email, copyemail, bademail, emptyemail, nullemail;

  @Before
  public void executedBeforeEach() {
    email = new Email("vds229@nyu.edu");
    copyemail = new Email("vds229@nyu.edu");
    bademail = new Email("vds229");
    emptyemail = new Email("");
    nullemail = new Email(null);
  }
  
  @Test
  public void testGetEmail() {
    //Test for a good email
    assertEquals("vds229@nyu.edu",email.getEmail());
    //Test for a "bad" email
    assertEquals("vds229",bademail.getEmail());
    //Test for an empty string email
    assertEquals("",emptyemail.getEmail());
    //Test for a null email
    assertEquals(null,nullemail.getEmail());
  }
  
  @Test
  public void testSetEmail() {
    //Test a new (good) email
    email.setEmail("vds2107@columbia.edu");
    assertEquals("vds2107@columbia.edu",email.getEmail());
    //Test a new (bad) email
    email.setEmail("vds229");
    assertEquals("vds229",email.getEmail());
    //Test an empty email
    email.setEmail("");
    assertEquals("",email.getEmail());
    //Test a null email
    email.setEmail(null);
    assertEquals(null,email.getEmail());
  }
  
  @Test
  public void testEquals() {
    //Test with a different Object
    assertFalse(email.equals("a string"));
    //Test with itself
    assertTrue(email.equals(email));
    //Test email with the same email String
    assertTrue(email.equals(copyemail));
    //Test with itself after changing its email
    email.setEmail("lalala");
    assertTrue(email.equals(email));
    //Test with copy that changed
    copyemail.setEmail("nanana");
    assertFalse(email.equals(copyemail));
    //Test with a different email address
    assertFalse(email.equals(bademail));
    //Test with empty email address
    assertFalse(email.equals(emptyemail));
  }
  
  @Test
  //BUG: equals method throws NullPointerException (not documented behavior)
  public void testEquals_NullEmail() {
    assertFalse(email.equals(nullemail));
  }
  
  @Test
  public void testHashCode() {
    //Test with itself
    assertEquals(email.hashCode(), email.hashCode());
    //Test email with the same email String
    assertEquals(email.hashCode(), copyemail.hashCode());
    //Test with a different email address
    assertFalse(email.hashCode()==bademail.hashCode());
    //Test with empty email address
    assertFalse(email.hashCode()==emptyemail.hashCode());
    //Test with copy that changed
    copyemail.setEmail("nanana");
    assertFalse(email.hashCode()==copyemail.hashCode());
    //Test with itself after changing its email
    email.setEmail("lalala");
    assertEquals(email.hashCode(), email.hashCode());
  }
  
  @Test
  //BUG: hashCode throws NullPointerException (not documented behavior)
  public void testHashCode_NullEmail() {
    //Test with a null email address
    assertFalse(email.hashCode()==nullemail.hashCode());
  }
  
  @Test
  public void testToString() {
    //Test with itself
    assertEquals(email.toString(),email.toString());
    //Test email with the same email String
    assertEquals(email.toString(),copyemail.toString());
    //Test with a different email address
    assertFalse(email.toString().equals(bademail.toString()));
    //Test with empty email address
    assertFalse(email.toString().equals(emptyemail.toString()));
    //Test with null email address
    assertFalse(email.toString().equals(nullemail.toString()));
    //Test with copy that changed
    copyemail.setEmail("nanana");
    assertFalse(email.toString().equals(copyemail.toString()));
    //Test with itself after changing its email
    email.setEmail("lalala");
    assertEquals(email.toString(), email.toString());
  }

}
