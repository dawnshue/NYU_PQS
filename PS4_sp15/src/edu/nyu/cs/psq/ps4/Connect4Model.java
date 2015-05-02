package edu.nyu.cs.psq.ps4;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

/**
 * The Connect4Model manages the Connect4 game play by controlling the interaction between
 * the Connect4Player and Connect4Board. Clients can interact with the model by using the
 * objects that implement the Connect4Listener.
 * The model uses a default Connect4Board, which means it presumes 2 players playing the game
 * on a 6x7 board.
 * @author Vangie
 * @see Connect4Model
 * @see Connect4Player
 * @see Connect4Listener
 */
public class Connect4Model {
  
  private final int NUMPLAYERS = 2;
  //The Connect4Model uses a default Connect4Board (for now)
  private Connect4Board board = new Connect4Board.BoardBuilder().numplayers(NUMPLAYERS).build();
  private Connect4Player[] players = new Connect4Player[NUMPLAYERS];
  //Determines which player's turn it is, where 0 represents the first player.
  private int playerTurn = 0;
  //Keeps track of how many players were added to the game
  private int playersAdded = 0;
  //Keeps track of whether a game is currently being played (thereby allowing moves)
  private boolean gameActive = false;
  
  private Random randomNumber = new Random();
  private List<Connect4Listener> listeners = new ArrayList<Connect4Listener>();
  
  /**
   * Allows clients to add Connect4Listener objects to listen to the model
   * @param Connect4Listener listener
   * @return boolean true if the listener was successfully added, else false
   */
  public boolean addListener(Connect4Listener listener) {
    if(listener instanceof Connect4Listener) {
      return listeners.add(listener);
    }
    return false;
  }
  
  /**
   * Allows clients to remove a previously added Connect4Listener
   * @param Connect4Listener listener to be removed
   * @return boolean true if the listener was successfully removed, else false
   */
  public boolean removeListener(Connect4Listener listener) {
    return listeners.remove(listener);
  }
  
  /**
   * Returns the column dimension of the Connect4Board used so that listeners can
   * correctly display the board.
   * @return int value of the number of columns
   */
  public int getBoardCols() {
    return board.getCols();
  }
  
  /**
   * Returns the row dimension of the Connect4Board used so that listeners can
   * correctly display the board.
   * @return int value of the number of rows
   */
  public int getBoardRows() {
    return board.getRows();
  }
  
  /**
   * Returns the number of players allowed to play the Connect4 game.
   * @return int value of the number of allowed players
   */
  public int getNumPlayers() {
    return NUMPLAYERS;
  }
  
  /**
   * This method should be called by a listener after the players have been added to the model.
   * The Connect4Model will reset the Connect4Board and validate that the players are legitimate.
   * It will notify all listeners that a game is starting (so they can reset their board UIs).
   * It will notify all listeners who the first player, chosen at random, is.
   * If the first player is a COMPUTER, it will go ahead and make a move for the computer.
   * @throws UnsupportedOperationException if called when players are not ready/valid
   */
  public void gameStart() {
    //Reset the board
    board.resetBoard();
    //Validate that all players are set
    if(playersAdded!=NUMPLAYERS) {
      String errormsg = "Game cannot start until players are set.";
      this.fireNotifyEvent("ERROR: "+errormsg+"\n");
      throw new UnsupportedOperationException(errormsg);
    } else {
      //Else determine the first player
      this.fireGameStartEvent();
      playerTurn = randomNumber.nextInt(players.length);
      gameActive = true;
      this.fireNotifyEvent("New game started. "+players[playerTurn].getName()+"'s turn.\n");
      this.makeComputerMove();
    }
  }
  
  /*
   * Calls the gameStart() method on all listeners.
   */
  private void fireGameStartEvent() {
    for(Connect4Listener l : listeners) {
      l.gameStart();
    }
  }
  
  /*
   * Attempts to make the best move according to the Connect4Board's getBestMove().
   */
  private boolean makeComputerMove() {
    if(gameActive && players[playerTurn].isComputerPlayer()) {
      int col = board.getBestMove(playerTurn);
      this.makeMove(col);
      return true;
    }
    return false;
  }

  /*
   * Called the notify() method on all listeners, passing the message along.
   */
  private void fireNotifyEvent(String message) {
    for(Connect4Listener l : listeners) {
      l.notify(message);
    }
  }
  
  /**
   * gameReset() will wipe both the board and the players.
   * It will call the gameReset(), updatePlayers(), and notify() method on all listeners.
   */
  public void gameReset() {
    board.resetBoard();
    players = new Connect4Player[NUMPLAYERS];
    playersAdded = 0;
    gameActive = false;
    this.fireGameResetEvent();
    this.fireUpdatePlayersEvent();
    this.fireNotifyEvent("Game reset.\n");
  }
  
  /*
   * Calls the gameReset() method on all listeners.
   */
  private void fireGameResetEvent() {
    for(Connect4Listener l : listeners) {
      l.gameReset();
    }
  }
  
  /*
   * Calls the updatePlayers() method on all listeners.
   * It passes only the valid players (as a copy) to the listeners.
   */
  private void fireUpdatePlayersEvent() {
    Connect4Player[] newPlayers = new Connect4Player[playersAdded];
    for(int i=0; i<playersAdded; i++) {
      newPlayers[i] = new Connect4Player.PlayerBuilder().name(players[i].getName())
          .wins(players[i].getWins()).losses(players[i].getLosses()).build();
    }
    for(Connect4Listener l : listeners) {
      l.updatePlayers(newPlayers);
    }
  }
  
  /**
   * addPlayer creates a Connect4Player with the given name.
   * It will notify all listeners of the added player.
   * @param name of the new player to be added
   * @throws UnsupportedOperationException if the method is called for too many players
   */
  public void addPlayer(String name) {
    if(playersAdded == NUMPLAYERS) {
      String errormsg = "Cannot add more than "+NUMPLAYERS+" players.";
      this.fireNotifyEvent("ERROR: "+errormsg+"\n");
      throw new UnsupportedOperationException(errormsg);
    } else {
      players[playersAdded] = new Connect4Player.PlayerBuilder().name(name).build();
      playersAdded++;
      this.fireNotifyEvent("Added player, "+name+".\n");
      this.fireUpdatePlayersEvent();
    }
  }
  
  /**
   * Adds a computer player who automatically makes a move when it is its turn.
   * Method will throw an exception if at least one human player is not present.
   * Method will throw an exception if you try to add a computer player exceeding NUMPLAYERS.
   * It will send a notification if player was added (as well as if it wasn't).
   * It will call all listener's updatePlayers() method.
   * @throws UnsupportedOperationException if the method is called illegally
   */
  public void addComputerPlayer() {
    if(playersAdded==0) {
      String errormsg = "Must have at least one non-computer player.";
      this.fireNotifyEvent("ERROR: "+errormsg+"\n");
      throw new UnsupportedOperationException(errormsg);
    } else if(playersAdded==NUMPLAYERS) {
      String errormsg = "Cannot add more than "+NUMPLAYERS+" players.";
      this.fireNotifyEvent("ERROR: "+errormsg+"\n");
      throw new UnsupportedOperationException(errormsg);
    } else {
      players[playersAdded] = Connect4Player.createComputerPlayer();
      playersAdded++;
      this.fireUpdatePlayersEvent();
      this.fireNotifyEvent("Computer player added.\n");
    }
  }
  
  /*
   * calls makeMove() on all listeners.
   */
  private void fireMakeMoveEvent(int[] pos) {
    for(Connect4Listener l : listeners) {
      l.makeMove(playerTurn, pos);
    }
  }
  
  /**
   * makeMove() is called to drop a token into next available row for the given column.
   * It will throw an UnsupportedOperationException if the game has already end.
   * It will throw an UnsupportedOperationException is a column is filled or an otherwise
   * invalid column index is passed.
   * @param col, the column to drop the current player's token
   * @throws UnsupportedOperationException if the game is not active or an invalid col was passed
   */
  public void makeMove(int col) {
    if(gameActive) {
      if(board.makeMove(playerTurn, col)) {
        //If board makes move successfully, then proceed
        this.fireNotifyEvent(players[playerTurn].getName()+" made move in column "+col+".\n");
        int[] pos = board.getLastPlay();
        this.fireMakeMoveEvent(pos);
        this.gameOver();
        this.incrementPlayerTurn();
        this.makeComputerMove();
      } else {
        String errormsg = "Invalid move requested.";
        this.fireNotifyEvent("ERROR: "+errormsg+"\n");
        throw new UnsupportedOperationException(errormsg);
      }
    } else {
      String errormsg = "Cannot play until game started.";
      this.fireNotifyEvent("ERROR: "+errormsg+"\n");
      throw new UnsupportedOperationException(errormsg);
    }
  }
  
  /*
   * Simple method that manages incrementing the playerTurn, and changing it to 0 once the
   * last player's turn has passed
   */
  private void incrementPlayerTurn() {
    playerTurn++;
    if(playerTurn>=NUMPLAYERS) {
      playerTurn = 0;
    }
  }
  
  /*
   * Checks if the game is over by checking if a winning sequence is played on the board.
   * Increments win for the winning player and losses for all other players.
   * Returns true if the game is over, false otherwise.
   */
  private boolean gameOver() {
    if(gameActive && board.hasConnect4()) {
      this.adjustScores();
      this.fireNotifyEvent("Game over! "+players[playerTurn].getName()+" won!\n");
      this.fireUpdatePlayersEvent();
      gameActive = false;
      return true;
    }
    return false;
  }
  
  /*
   * Increments the win of the current player at playerTurn.
   * Increments the losses of the remaining players.
   */
  private void adjustScores() {
    for(int i=0; i<players.length; i++) {
      if(i==playerTurn) {
        players[i].addWin();
      } else {
        players[i].addLoss();
      }
    }
  }

}
