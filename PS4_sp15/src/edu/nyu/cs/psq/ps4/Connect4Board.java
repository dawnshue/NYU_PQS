package edu.nyu.cs.psq.ps4;

/**
 * A Connect4Board represents a board that understands Connect4.
 * It can recognize more than 2 players if constructed as such.
 * It requires that the dimensions be greater than 1x1.
 * @author Vangie
 * @see Connect4Model
 */
public class Connect4Board {
  
  private final int ROWS;
  private final int COLS;
  private final int NUMPLAYERS;
  //lastPlay is the last position that was filled.
  private int[] lastPlay = {-1,-1};
  /* 
   * The board matrix is set such that the [0,0] position would represent the
   * bottom left-most position (where a piece would fall if put in the first column).
   * The positions are represented by the player number (eg. If the number of players is "N", 
   * then the board will recognize tokens 0 to "N-1").
   * Empty positions are represented by -1.
   */
  private int[][] board;
  
  private Connect4Board(BoardBuilder b) {
    this.NUMPLAYERS = b.numplayers;
    this.ROWS = b.rows;
    this.COLS = b.cols;
    this.board = b.board;
  }
  
  /**
   * By default, the board is set for 2 players on a 6x7 board.
   * The player count, dimensions (rows and cols) can be set with the builder.
   */
  public static class BoardBuilder {
    private int rows = 6;
    private int cols = 7;
    private int numplayers = 2;
    private int[][] board;
    private boolean customboard = false;
    public BoardBuilder numplayers(int n) {
      this.numplayers = n;
      return this;
    }
    public BoardBuilder rows(int r) {
      if(!customboard) {
        this.rows = r;
      }
      return this;
    }
    public BoardBuilder cols(int c) {
      if(!customboard) {
        this.cols = c;
      }
      return this;
    }
    /**
     * Setting the board takes precedence over setting the rows and cols individually.
     * @param board, an int matrix representing a Connect4 board
     */
    public BoardBuilder board(int[][] board) {
      if(board == null) {
        throw new IllegalArgumentException("Board cannot be created with a null board.");
      }
      customboard = true;
      rows = board.length;
      cols = board[0].length;
      this.board = board;
      return this;
    }
    public Connect4Board build() {
      if(rows<1) {
        throw new IllegalArgumentException("At least 1 row is required");
      }
      if(cols<1) {
        throw new IllegalArgumentException("At least 1 column is required");
      }
      if(numplayers<2) {
        throw new IllegalArgumentException("At least 2 players are required");
      }
      //If board was not set, then build needs to initialize it.
      if(!customboard) {
        board = new int[rows][cols];
        for(int r=0; r<rows; r++) {
          for(int c=0; c<cols; c++) {
            board[r][c] = -1;
          }
        }
      } else {
        //Verify that the given board is legal by checking all position values
        for(int r=0; r<rows; r++) {
          for(int c=0; c<cols; c++) {
            if(board[r][c] <-1 || board[r][c] >= numplayers) {
              throw new IllegalArgumentException("Board contained an invalid token at "+
                                                  "("+r+","+c+").");
            }
          }
        }
      }
      return new Connect4Board(this);
    }
  }
  
  /**
   * This method returns a copy of the board represented as an int matrix.
   * A value -1 in the matrix represents an empty space whereas a value >= 0 represents
   * a player turn starting from 0.
   * @return int[][] representation of board
   */
  public int[][] getBoard() {
    int[][] boardcopy = new int[ROWS][COLS];
    for(int r=0; r<ROWS; r++) {
      for(int c=0; c<COLS; c++) {
        boardcopy[r][c] = board[r][c];
      }
    }
    return boardcopy;
  }
  
  /*
   * Resetting the board sets all positions to -1.
   */
  public void resetBoard() {
    for(int r=0; r<ROWS; r++) {
      for(int c=0; c<COLS; c++) {
        board[r][c] = -1;
      }
    }
  }
  
  /**
   * @return the number of rows in the board
   */
  public int getRows() {
    return ROWS;
  }
  /**
   * @return the number of columns in the board
   */
  public int getCols() {
    return COLS;
  }
  /**
   * @return the number of recognized players in the board
   */
  public int getNumPlayers() {
    return NUMPLAYERS;
  }

  /**
   * Returns the column to use to make the best move. It iterates through the available
   * positions (lowest unfilled row in each column) and gets the longest possible sequence.
   * @param playerTurn
   * @return
   */
  public int getBestMove(int turn) {
    int bestCol = 0;
    int longest = 0;
    //If game just started, start playing from the middle
    if(lastPlay[0] == -1) {
      return COLS/2;
    }
    
    for(int c=0; c<COLS; c++) {
      int r = -1;
      //Get the available row for that column
      while(++r < ROWS) {
        if(board[r][c] == -1) {
          if(getHorizontalSequenceLength(turn,r,c)>longest) {
            bestCol = c;
            longest = getHorizontalSequenceLength(turn,r,c);
          }
          if(getVerticalSequenceLength(turn,r,c)>longest) {
            bestCol = c;
            longest = getVerticalSequenceLength(turn,r,c);
          }
          if(getLeftDiagSequenceLength(turn,r,c)>longest) {
            bestCol = c;
            longest = getLeftDiagSequenceLength(turn,r,c);
          }
          if(getRightDiagSequenceLength(turn,r,c)>longest) {
            bestCol = c;
            longest = getRightDiagSequenceLength(turn,r,c);
          }
          break;
        }
      }
    }
    
    return bestCol;
  }

  /*
   * This method returns the length of the horizontal sequence containing the given position.
   * The sequence contains the same pieces as the given position.
   */
  private int getHorizontalSequenceLength(int playerTurn, int row, int col) {
    int len = 1;
    int turn = playerTurn;
    int c = col;
    while(++c < COLS) {
      if(board[row][c]!=turn) {
        break;
      }
      len++;
    }
    c = col;
    while(--c >= 0) {
      if(board[row][c]!=turn) {
        break;
      }
      len++;
    }
    return len;
  }
  
  /*
   * This method returns the length of the vertical sequence containing the given position.
   * The sequence contains the same pieces as the given position.
   */
  private int getVerticalSequenceLength(int playerTurn, int row, int col) {
    int len = 1;
    int turn = playerTurn;
    int r = row;
    while(++r < ROWS) {
      if(board[r][col]!=turn) {
        break;
      }
      len++;
    }
    r = row;
    while(--r >= 0) {
      if(board[r][col]!=turn) {
        break;
      }
      len++;
    }
    return len;
  }
  
  /*
   * This method returns the length of the diagonal sequence running upward from [0,COLS-1]
   * to [ROW-1,0] and containing the given position.
   * The sequence contains the same pieces as the given position.
   */
  private int getLeftDiagSequenceLength(int playerTurn, int row, int col) {
    int len = 1;
    int turn = playerTurn;
    int r = row;
    int c = col;
    while(++r < ROWS && --c >= 0) {
      if(board[r][c]!=turn) {
        break;
      }
      len++;
    }
    r = row;
    c = col;
    while(--r >= 0 && ++c < COLS) {
      if(board[r][c]!=turn) {
        break;
      }
      len++;
    }
    return len;
  }
  
  /*
   * This method returns the length of the diagonal sequence running upward from [0,0] to
   * [ROWS-1,COLS-1] and containing the given position.
   * The sequence contains the same pieces as the given position.
   */
  private int getRightDiagSequenceLength(int playerTurn, int row, int col) {
    int len = 1;
    int turn = playerTurn;
    int r = row;
    int c = col;
    while(++r < ROWS && ++c < COLS) {
      if(board[r][c]!=turn) {
        break;
      }
      len++;
    }
    r = row;
    c = col;
    while(--r >= 0 && --c >= 0) {
      if(board[r][c]!=turn) {
        break;
      }
      len++;
    }
    return len;
  }

  /**
   * getLastPlay() gets the coordinates of the last move made.
   * @return int[] representing the coordinates of the last move as [row, col]
   */
  public int[] getLastPlay() {
    return lastPlay;
  }
  
  /***
   * Puts a piece for that player in the lowest possible row for the given column.
   * Sets the lastPlay variable to the position the token lands in.
   * @param playerTurn, the turn of the current player (where 0 is the first player)
   * @param col, the column to place the token
   * @return boolean true if the move was successfully made, else false
   */
  public boolean makeMove(int playerTurn, int col) {
    //Check col validity
    if(col<0 || col>=COLS) {
      return false;
    }
    int r = 0;
    while(r < ROWS) {
      if(board[r][col] < 0) {
        board[r][col] = playerTurn;
        //Update lastPlay values
        lastPlay[0] = r;
        lastPlay[1] = col;
        return true;
      } else {
        r++;
      }
    }
    return false;
  }
  
  /**
   * hasConnect4() uses the last move to check if it resulted in a Connect4.
   * @return boolean true if there exists a connect4, false otherwise.
   */
  public boolean hasConnect4() {
    int lastRow = lastPlay[0];
    int lastCol = lastPlay[1];
    int turn = board[lastRow][lastCol];
    if(getHorizontalSequenceLength(turn, lastRow, lastCol) == 4) {
      return true;
    }
    if(getVerticalSequenceLength(turn, lastRow, lastCol) == 4) {
      return true;
    }
    if(getLeftDiagSequenceLength(turn, lastRow, lastCol) == 4) {
      return true;
    }
    if(getRightDiagSequenceLength(turn, lastRow, lastCol) == 4) {
      return true;
    }
    return false;
  }
}
