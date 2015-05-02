package edu.nyu.cs.pqs.ps1;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class EntryNoteTest {

  private EntryNote note;
  private EntryNote emptynote;
  private EntryNote nullnote;
  
  @Before
  public void executedBeforeEach() {
    note = new EntryNote("some text");
    emptynote = new EntryNote("");
    nullnote = new EntryNote(null);
  }
  
  @Test
  public void testGetText() {
    assertEquals("some text",note.getText());
    assertEquals("",emptynote.getText());
    assertEquals(null,nullnote.getText());
  }
  
  @Test
  public void testSetText() {
    //Test setting text to empty
    note.setText("");
    assertEquals("",note.getText());
    
    //Test setting text to null
    note.setText(null);
    assertEquals(null,note.getText());
  }
  
  @Test
  public void testEquals() {
    //Test with non-EntryNote object
    assertFalse(note.equals("a String"));
    assertFalse(nullnote.equals(null));
    //Test with default EntryNote objects
    assertTrue(note.equals(note));
    assertTrue(emptynote.equals(emptynote));
    assertFalse(note.equals(emptynote));
    //Test with different objects, same text
    assertTrue(emptynote.equals(new EntryNote("")));
  }
  
  @Test
  //BUG: unexpected behavior in equals when dealing with null text
  public void testEquals_NullText() {
    assertTrue(nullnote.equals(nullnote));
  }
  
  @Test
  public void testHashCode() {
    assertEquals(note.hashCode(),note.hashCode());
    assertEquals(emptynote.hashCode(),emptynote.hashCode());
    assertEquals(emptynote.hashCode(),(new EntryNote("")).hashCode());
  }
  
  @Test
  //BUG: unexpected behavior in hashcode when dealing with null text
  public void testHashCode_NullText() {
    assertEquals(nullnote.hashCode(),nullnote.hashCode());
  }
  
  @Test
  public void testToString() {
    assertTrue(note.toString().equals(note.toString()));
    assertTrue(emptynote.toString().equals(emptynote.toString()));
  }
  
  @Test
  //BUG: unexpected behavior in ToString when dealing with null text
  public void testToString_NullText() {
    assertTrue(nullnote.toString().equals(nullnote.toString()));
  }

}
