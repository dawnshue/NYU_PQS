package edu.nyu.cs.psq.ps4;

/**
 * @author Vangie
 *
 */
public interface Connect4Listener {
  public void gameStart();
  public void gameReset();
  public void updatePlayers(Connect4Player[] players);
  public void makeMove(int playerTurn, int[] pos);
  public void notify(String message);
}
