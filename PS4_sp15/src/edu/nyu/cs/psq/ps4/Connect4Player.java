package edu.nyu.cs.psq.ps4;

/**
 * This class represents a Connect4Player. It holds very basic information currently:
 * the player's name, number of wins, and number of losses.
 * It can also be used to represent a computer player.
 * @author Vangie
 * @see Connect4Model
 */
public class Connect4Player {

  private String name;
  private int wins;
  private int losses;
  private boolean isComputer = false;
  
  private Connect4Player(PlayerBuilder player) {
    this.name = player.name;
    this.wins = player.wins;
    this.losses = player.losses;
  }
  public boolean isComputerPlayer() {
    return isComputer;
  }
  public static Connect4Player createComputerPlayer() {
    Connect4Player player = new Connect4Player.PlayerBuilder().name("COMPUTER").build();
    player.isComputer = true;
    return player;
  }
  public static class PlayerBuilder {
    private String name = null;
    private int wins = 0;
    private int losses = 0;
    public PlayerBuilder name(String newname) {
      this.name = newname;
      return this;
    }
    public PlayerBuilder wins(int w) {
      this.wins = w;
      return this;
    }
    public PlayerBuilder losses(int l) {
      this.losses = l;
      return this;
    }
    public Connect4Player build() {
      if(name==null || name.equals("")) {
        throw new IllegalArgumentException("Player name cannot be null or empty.");
      }
      if(wins<0) {
        throw new IllegalArgumentException("Player wins cannot be negative.");
      }
      if(losses<0) {
        throw new IllegalArgumentException("Player losses cannot be negative.");
      }
      //How to deal with overflow wins and losses?
      return new Connect4Player(this);
    }
  }
  /**
   * This method will increment the player's wins by 1.
   * It does not account for overflow currently.
   */
  public void addWin() {
    this.wins++;
  }
  /**
   * This method will increment the player's losses by 1.
   * It does not account for overflow currently.
   */
  public void addLoss() {
    this.losses++;
  }
  public String getName() {
    return name;
  }
  public int getWins() {
    return wins;
  }
  public int getLosses() {
    return losses;
  }
  
}
