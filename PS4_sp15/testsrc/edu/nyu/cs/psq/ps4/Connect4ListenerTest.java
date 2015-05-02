package edu.nyu.cs.psq.ps4;

/**
 * This is a simple class that implements Connect4Listener and changes flags if certain 
 * methods are called.
 * @author Vangie
 */
public class Connect4ListenerTest implements Connect4Listener {
  public boolean triggerGameStart;
  public boolean triggerGameReset;
  public boolean triggerUpdatePlayers; 
  public boolean triggerMakeMove;
  public boolean triggerNotify;

  public void resetFlags() {
    triggerGameStart = false;
    triggerGameReset = false;
    triggerUpdatePlayers = false;
    triggerMakeMove = false;
    triggerNotify = false;
  }
  
  @Override
  public void gameStart() {
    triggerGameStart = true;
  }

  @Override
  public void gameReset() {
    triggerGameReset = true;
  }

  @Override
  public void updatePlayers(Connect4Player[] players) {
    triggerUpdatePlayers = true;
  }

  @Override
  public void makeMove(int playerTurn, int[] pos) {
    triggerMakeMove = true;
  }

  @Override
  public void notify(String message) {
    triggerNotify = true;
  }

}
