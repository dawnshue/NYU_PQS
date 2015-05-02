package edu.nyu.cs.psq.ps4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Connect4ModelTest {

  private Connect4ListenerTest listener;
  private Connect4Model model;
  private Connect4Board board;
  
  @Before
  public void executedBeforeEach() {
    model = new Connect4Model();
    listener = new Connect4ListenerTest();
    model.addListener(listener);
    board = new Connect4Board.BoardBuilder().build();
  }
  
  @Test
  public void testAddListener() {
    assertTrue(!model.addListener(null));
  }
  @Test
  public void testGetBoardCols() {
    //The model uses a default board, so its dimensions should match a default board
    assertEquals(model.getBoardCols(),board.getCols());
  }

  @Test
  public void testGetBoardRows() {
    //The model uses a default board, so the row dimension is expected to be the default
    assertEquals(model.getBoardRows(),board.getRows());
  }

  @Test
  public void testGetNumPlayers() {
    //Default Model expected to use only 2 players
    assertEquals(model.getNumPlayers(), 2);
  }

  @Test
  public void testGameStart() {
    //Should not be able to start game until players are registered
    try {
      model.gameStart();
      fail("Should not be able to reach here.");
    } catch(UnsupportedOperationException e) { }
    
    //Valid gameStart
    model.addPlayer("Hi");
    model.addPlayer("Bye");
    listener.resetFlags();
    model.gameStart();
    assertTrue(!listener.triggerGameReset);
    assertTrue(!listener.triggerUpdatePlayers);
    assertTrue(listener.triggerNotify);
    assertTrue(listener.triggerGameStart);
    assertTrue(!listener.triggerMakeMove);
  }

  @Test
  public void testGameReset() {
    //Calling gameReset should call listener's gameReset, updatePlayers (resets them) and notify
    model.gameReset();
    assertTrue(listener.triggerGameReset);
    assertTrue(listener.triggerUpdatePlayers);
    assertTrue(listener.triggerNotify);
    assertTrue(!listener.triggerGameStart);
    assertTrue(!listener.triggerMakeMove);
  }

  @Test
  public void testAddPlayer() {
    //Adding a player should trigger a player update and a notify
    model.addPlayer("Hi");
    assertTrue(!listener.triggerGameReset);
    assertTrue(listener.triggerUpdatePlayers);
    assertTrue(listener.triggerNotify);
    assertTrue(!listener.triggerGameStart);
    assertTrue(!listener.triggerMakeMove);
    
    //Test adding too many players
    model.gameReset();
    model.addPlayer("Hi");
    model.addPlayer("Bye");
    try {
      model.addPlayer("Too many");
      fail("Should not be able to add extra players");
    } catch(UnsupportedOperationException e) { }
  }

  @Test
  public void testAddComputerPlayer() {
    //Cannot add a computer player without an existing human player
    try {
      model.addComputerPlayer();
      fail("Should not be able to reach here.");
    } catch(UnsupportedOperationException e) { }
    
    //Legally add computer player
    model.addPlayer("First");
    listener.resetFlags();
    model.addComputerPlayer();
    assertTrue(!listener.triggerGameReset);
    assertTrue(listener.triggerUpdatePlayers);
    assertTrue(listener.triggerNotify);
    assertTrue(!listener.triggerGameStart);
    assertTrue(!listener.triggerMakeMove);
    
    //Cannot add computer player if sufficient players already added
    model.gameReset();
    model.addPlayer("First");
    model.addPlayer("Second");
    try {
      model.addComputerPlayer();
      fail("Should not be able to add extra players");
    } catch(UnsupportedOperationException e) { }
  }

  @Test
  public void testMakeMove() {
    model.addPlayer("first");
    model.addPlayer("second");
    
    //Should not be able to makeMove until a gameStart called
    try {
      model.makeMove(0);
      fail("-1 should be an invalid column to makeMove");
    } catch(UnsupportedOperationException e) { }
    
    //First move should only trigger notify and makemove
    model.gameStart();
    listener.resetFlags();
    model.makeMove(3);
    assertTrue(!listener.triggerGameReset);
    assertTrue(!listener.triggerUpdatePlayers);
    assertTrue(listener.triggerNotify);
    assertTrue(!listener.triggerGameStart);
    assertTrue(listener.triggerMakeMove);
    
    //winning move should trigger updateplayers in addition the others
    model.gameStart();
    for(int i=0; i<3; i++) {
      model.makeMove(3);
      model.makeMove(1);
    }
    listener.resetFlags();
    model.makeMove(3);
    assertTrue(!listener.triggerGameReset);
    assertTrue(listener.triggerUpdatePlayers);
    assertTrue(listener.triggerNotify);
    assertTrue(!listener.triggerGameStart);
    assertTrue(listener.triggerMakeMove);
    
    //Trying to place a move in invalid column should throw exception
    model.gameStart();
    try {
      model.makeMove(-1);
      fail("-1 should be an invalid column to makeMove");
    } catch(UnsupportedOperationException e) { }
    
    //Trying to overfill a col should throw an exception
    model.gameStart();
    for(int i=0; i<6; i++) {
      model.makeMove(0);
    }
    try {
      model.makeMove(0);
      fail("Should not be able to overfill a column.");
    } catch(UnsupportedOperationException e) { }
  }

}
