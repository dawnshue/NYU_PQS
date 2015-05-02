/**
 * 
 */
package edu.nyu.cs.psq.ps4;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vangie
 *
 */
public class Connect4BoardTest {
  Connect4Board board;

  @Before
  public void executedBeforeEach() {
    //board is just a default 2 player, 6x7 board.
    board = new Connect4Board.BoardBuilder().build();
  }
  
  @Test
  public void testBoardBuilder() {
    //Test default board
    assertEquals(board.getRows(),6);
    assertEquals(board.getCols(),7);
    assertEquals(board.getNumPlayers(),2);
    int[][] boardboard = board.getBoard();
    for(int r=0; r<boardboard.length; r++) {
      for(int c=0; c<boardboard[0].length; c++) {
        assertEquals(boardboard[r][c],-1);
      }
    }
    
    //Test different builder options
    Connect4Board test;
    
    //Test setting numplayers
    test = new Connect4Board.BoardBuilder().numplayers(5).build();
    assertEquals(test.getNumPlayers(),5);
    //Test illegal numplayers
    try {
      test = new Connect4Board.BoardBuilder().numplayers(1).build();
      fail("Should not be able to create board with less than 2 players.");
    } catch (IllegalArgumentException e) { }
    
    //Test setting rows, cols should stay default value
    test = new Connect4Board.BoardBuilder().rows(5).build();
    assertEquals(test.getRows(),5);
    assertEquals(test.getCols(),7);
    //Test illegal rows
    try {
      test = new Connect4Board.BoardBuilder().rows(0).build();
      fail("Should not be able to create board with less than 1 row.");
    } catch (IllegalArgumentException e) { }
    
    //Test setting cols, rows should stay default value
    test = new Connect4Board.BoardBuilder().cols(5).build();
    assertEquals(test.getCols(),5);
    assertEquals(test.getRows(),6);
    //Test illegal cols
    try {
      test = new Connect4Board.BoardBuilder().cols(0).build();
      fail("Should not be able to create board with less than 1 column.");
    } catch (IllegalArgumentException e) { }
    
    //Test setting rows & cols
    test = new Connect4Board.BoardBuilder().rows(5).cols(10).build();
    assertEquals(test.getRows(),5);
    assertEquals(test.getCols(),10);
    //Test illegal rows
    try {
      test = new Connect4Board.BoardBuilder().rows(0).cols(10).build();
      fail("Should not be able to create board with less than 1 row.");
    } catch (IllegalArgumentException e) { }
    
    //Test setting a custom board
    int[][] goodboard = new int[5][8];
    for(int r=0; r<goodboard.length; r++) {
      for(int c=0; c<goodboard[0].length; c++) {
        goodboard[r][c] = -1;
      }
      goodboard[r][0] = r%2;
    }
    test = new Connect4Board.BoardBuilder().board(goodboard).rows(2).cols(1).build();
    assertEquals(test.getRows(),goodboard.length);
    assertEquals(test.getCols(),goodboard[0].length);
    int[][] testboard = test.getBoard();
    for(int r=0; r<goodboard.length; r++) {
      for(int c=0; c<goodboard[0].length; c++) {
        assertEquals(goodboard[r][c],testboard[r][c]);
      }
    }
  }
  
  @Test
  public void testBoardBuilderwithBadCustomBoard() {
    @SuppressWarnings("unused")
    Connect4Board test;
    //Test null board
    try {
      test = new Connect4Board.BoardBuilder().board(null).build();
      fail("Should not be able to create board with null board.");
    } catch (IllegalArgumentException e) { }
    
    //Test board with illegal positions
    int[][] badboard = new int[5][8];
    for(int r=0; r<badboard.length; r++) {
      for(int c=0; c<badboard[0].length; c++) {
        badboard[r][c] = -1;
      }
    }
    try {
      badboard[0][0] = -2;
      test = new Connect4Board.BoardBuilder().board(badboard).build();
      fail("Should not be able to create board with position value < -1.");
    } catch (IllegalArgumentException e) { }
    try {
      badboard[0][0] = 2;
      test = new Connect4Board.BoardBuilder().board(badboard).build();
      fail("Should not be able to create board with position value >= numplayers.");
    } catch (IllegalArgumentException e) { }
  }
  
  @Test
  public void testResetBoard() {
    int[][] goodboard = new int[6][7];
    for(int r=0; r<goodboard.length; r++) {
      for(int c=0; c<goodboard[0].length; c++) {
        goodboard[r][c] = -1;
      }
      goodboard[r][0] = r%2;
    }
    Connect4Board customboard = new Connect4Board.BoardBuilder().board(goodboard).build();
    
    customboard.resetBoard();
    int[][] board = customboard.getBoard();
    for(int r=0; r<board.length; r++) {
      for(int c=0; c<board[0].length; c++) {
        assertEquals(board[r][c],-1);
      }
    }
  }

  @Test
  public void testGetBestMove() {
    //First best move should be middle column (3 in 7 column default board)
    assertEquals(board.getBestMove(0),3);
    
    //Place 0 token at position 0,0 and 0,1
    board.resetBoard();
    board.makeMove(0,0);
    board.makeMove(0,1);
    assertEquals(board.getBestMove(0),2);
    
    //Place 0 token in single column, with one token horizontal (sequence lengths 3 vs 2)
    board.resetBoard();
    board.makeMove(0,0);
    board.makeMove(0,0);
    board.makeMove(0,0);
    board.makeMove(0,1);
    assertEquals(board.getBestMove(0),0);
    
    //Horizontal sequence with a gap
    board.resetBoard();
    board.makeMove(0,1);
    board.makeMove(0,2);
    board.makeMove(0,4);
    board.makeMove(0,5);
    assertEquals(board.getBestMove(0),3);
  }

  /**
   * Test method for {@link edu.nyu.cs.psq.ps4.Connect4Board#getLastPlay()}.
   */
  @Test
  public void testGetLastPlay() {
    board.makeMove(0,0);
    int[] expectedmove = {0,0};
    int[] movemade = board.getLastPlay();
    assertEquals(movemade[0], expectedmove[0]);
    assertEquals(movemade[1], expectedmove[1]);
  }

  @Test
  public void testMakeMovewithBadMove() {
    //makeMove should return false if row has filled up (play cannot be made)
    for(int i=0; i<board.getRows(); i++) {
      assertTrue(board.makeMove(0,0));
    }
    assertTrue(!board.makeMove(0,0));
  }

  /**
   * Test method for {@link edu.nyu.cs.psq.ps4.Connect4Board#hasConnect4()}.
   */
  @Test
  public void testHasConnect4() {
    board.makeMove(0,0);
    board.makeMove(1,1);
    board.makeMove(0,1);
    board.makeMove(1,2);
    board.makeMove(1,2);
    board.makeMove(0,2);
    board.makeMove(1,3);
    board.makeMove(1,3);
    board.makeMove(1,3);
    assertEquals(board.getBestMove(0),3);
    board.makeMove(0,3);
    assertTrue(board.hasConnect4());
  }

}
