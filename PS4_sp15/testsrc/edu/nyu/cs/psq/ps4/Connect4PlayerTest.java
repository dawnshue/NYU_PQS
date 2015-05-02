package edu.nyu.cs.psq.ps4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Connect4PlayerTest {

  private Connect4Player computer;
  private Connect4Player player1;
  private Connect4Player player2;
  
  @Before
  public void executedBeforeEach() {
    computer = Connect4Player.createComputerPlayer();
    player1 = new Connect4Player.PlayerBuilder().name("Player 1").build();
    player2 = new Connect4Player.PlayerBuilder().name("Player 2").build();
  }
  
  @Test
  public void testPlayerBuilder() {
    @SuppressWarnings("unused")
    Connect4Player test;
    //Test Empty Name
    try {
      test = new Connect4Player.PlayerBuilder().name("").build();
      fail("Should not be able to create player with empty name.");
    } catch (IllegalArgumentException e) { }
    //Test NULL Name
    try {
      test = new Connect4Player.PlayerBuilder().name(null).build();
      fail("Should not be able to create player with null name.");
    } catch (IllegalArgumentException e) { }
    //Test Negative Wins
    try {
      test = new Connect4Player.PlayerBuilder().name("X").wins(-1).build();
      fail("Should not be able to create player with negative wins.");
    } catch (IllegalArgumentException e) { }
    //Test Negative Losses
    try {
      test = new Connect4Player.PlayerBuilder().name("X").losses(-1).build();
      fail("Should not be able to create player with negative losses.");
    } catch (IllegalArgumentException e) { }
  }
  
  @Test
  public void testIsComputerPlayer() {
    assertTrue(computer.isComputerPlayer());
    assertTrue(!player1.isComputerPlayer());
    assertTrue(!player2.isComputerPlayer());
  }
  
  @Test
  public void testGetWinsAndLosses() {
    //All players should start with 0 wins, 0 losses
    assertEquals(player1.getWins(),0);
    assertEquals(player1.getLosses(),0);
    
    assertEquals(computer.getWins(),0);
    assertEquals(computer.getLosses(),0);
  }

  @Test
  public void testAddWin() {
    //Add win should increment wins by 1, losses should not change
    player1.addWin();
    assertEquals(player1.getWins(),1);
    assertEquals(player1.getLosses(),0);
  }

  @Test
  public void testAddLoss() {
    //Add loss should increment losses by 1, wins should not change
    player2.addLoss();
    assertEquals(player2.getWins(),0);
    assertEquals(player2.getLosses(),1);
  }

  @Test
  public void testGetName() {
    assertEquals(computer.getName(),"COMPUTER");
    assertEquals(player1.getName(),"Player 1");
    assertEquals(player2.getName(),"Player 2");
  }

}
