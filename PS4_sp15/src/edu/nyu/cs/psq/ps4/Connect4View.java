package edu.nyu.cs.psq.ps4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * This is a GUI implementation of Connect4Listener to play Connect4.
 * @author Vangie
 * @see Connect4Listener
 * @see Connect4Model
 */
public class Connect4View implements Connect4Listener {

  private Connect4Model model;
  
  private JFrame frame = new JFrame();
  private int frameWidth, frameHeight, topPHeight, bottomPHeight;
  private JTextArea gamelog = new JTextArea();
  private JLabel[] playerLabels;
  private JLabel[] scoreLabels;
  private JTextField playerNameField = new JTextField("Enter player name");
  private JButton addPlayerButton = new JButton("Add Player");
  private JButton playComputerButton = new JButton("Play against Computer");
  private JButton startButton = new JButton("START NEW GAME");
  private JButton resetButton = new JButton("RESET ALL");
  
  private int boardRows, boardCols, numPlayers;
  //Colors only set for 2 players, need to account for dynamic # players in future
  private Color[] playerColors = {Color.YELLOW,Color.RED};
  private Color defaultColor = Color.BLACK;
  /* boardButtons represents the Connect4 board and is set such that the [0,0] button
   * would represent the bottom, leftmost position.
   */
  private JButton[][] boardButtons;
  
  public Connect4View(Connect4Model model) {
    this.model = model;
    model.addListener(this);
    this.setGameVariables();
    this.determineUIsizes();
    this.setButtonActions();
    
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    
    //Top panel contains all UI for adding and showing player information
    JPanel topPanel = this.getPlayerPanel();
    //Center panel contains all UI representing Connect4Board (basically just buttons)
    JPanel centerPanel = this.getBoardPanel();
    //Bottom panel handles gameStart and gameReset and logging actions made.
    JPanel bottomPanel = this.getBottomPanel();
    
    panel.add(topPanel, BorderLayout.NORTH);
    panel.add(centerPanel, BorderLayout.CENTER);
    panel.add(bottomPanel, BorderLayout.SOUTH);
    
    frame.setTitle("Connect 4");
    frame.getContentPane().setLayout(new BorderLayout());
    frame.getContentPane().add(panel, BorderLayout.CENTER);
    frame.setSize(frameWidth,frameHeight);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /*
   * This method manages added all JButton ActionListeners for this class
   */
  private void setButtonActions() {
    startButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        requestGameStart();
      }
    });
    resetButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        requestGameReset();
      }
    });
    addPlayerButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        addPlayer();
      }
    });
    playComputerButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        addComputerOpponent();
      }
    });
    //Add ActionListeners for board buttons
    for(int r=boardRows-1; r>=0; r--) {
      for(int c=0; c<boardCols; c++) {
        boardButtons[r][c] = new JButton("");
        boardButtons[r][c].setForeground(defaultColor);
        //Need to set the column property of the button to be used by ActionListener
        boardButtons[r][c].putClientProperty("column", c);
        boardButtons[r][c].addActionListener(new BoardButtonActionListener());
      }
    }
  }
  
  /*
   * Pulls the column value using the button's property and calls model's makeMove.
   */
  private class BoardButtonActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      JButton btn = (JButton) e.getSource();
      int c = (int) btn.getClientProperty("column");
      gamelog.append("Requested to add token to column "+c+".\n");
      model.makeMove(c);
    }
  }
  
  private JPanel getPlayerPanel() {
    JPanel headerPanel = new JPanel(new GridLayout(2,numPlayers));
    //Play information labels (name and wins)
    playerLabels = new JLabel[numPlayers];
    scoreLabels = new JLabel[numPlayers];
    for(int p=0; p<numPlayers; p++) {
      playerLabels[p] = new JLabel("<Player "+(p+1)+">",SwingConstants.RIGHT);
      scoreLabels[p] = new JLabel(": 0 (wins)",SwingConstants.LEFT);
      headerPanel.add(playerLabels[p]);
      headerPanel.add(scoreLabels[p]);
    }
    
    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setPreferredSize(new Dimension(frameWidth, topPHeight));
    topPanel.add(headerPanel, BorderLayout.NORTH);
    //Field for user to enter Player Name and click button to add it as a Connect4Player
    topPanel.add(playerNameField, BorderLayout.CENTER);
    topPanel.add(addPlayerButton, BorderLayout.EAST);
    //Button to request a computer player
    topPanel.add(playComputerButton, BorderLayout.SOUTH);
    return topPanel;
  }
  
  /*
   * Creates a panel of an array of JButtons representing the Connect4Board
   */
  private JPanel getBoardPanel() {
    JPanel boardPanel = new JPanel(new GridLayout(boardRows,boardCols));
    for(int r=boardRows-1; r>=0; r--) {
      for(int c=0; c<boardCols; c++) {
        boardPanel.add(boardButtons[r][c]);
      }
    }
    return boardPanel;
  }
  
  private JPanel getBottomPanel() {
    startButton.setForeground(Color.BLUE);
    resetButton.setForeground(Color.RED);
    JPanel bottomPanel = new JPanel(new BorderLayout());
    bottomPanel.setPreferredSize(new Dimension(frameWidth, bottomPHeight));
    bottomPanel.add(startButton, BorderLayout.NORTH);
    //Scrollable panel that records all actions
    bottomPanel.add(new JScrollPane(gamelog),BorderLayout.CENTER);
    bottomPanel.add(resetButton, BorderLayout.SOUTH);
    return bottomPanel;
  }
  
  /*
   * Pulls game variables from the model, who calls the appropriate methods of 
   * Connect4Board and Connect4Player as necessary.
   */
  private void setGameVariables() {
    boardRows = model.getBoardRows();
    boardCols = model.getBoardCols();
    numPlayers = model.getNumPlayers();
    boardButtons = new JButton[boardRows][boardCols];
  }
  
  //Default UI sizes
  private void determineUIsizes() {
    topPHeight = 100;
    bottomPHeight = 150;
    frameWidth = boardCols*50;
    frameHeight = boardRows*50+topPHeight+bottomPHeight;
  }
  
  //Action performed by the START button
  private void requestGameStart() {
    gamelog.append("Game start requested.\n");
    model.gameStart();
  }
  
  //Action performed by the RESET button
  private void requestGameReset() {
    gamelog.append("Game reset requested.\n");
    model.gameReset();
  }
  
  @Override
  public void gameStart() {
    //Reset board colors with gameStart
    for(JButton[] boardRow : boardButtons) {
      for(JButton b : boardRow) {
        b.setForeground(defaultColor);
        b.setText("");
      }
    }
  }
  
  @Override
  public void gameReset() {
    this.gameStart();
    for(int i=0; i<playerLabels.length; i++) {
      playerLabels[i].setText("<Player "+(i+1)+">");
      scoreLabels[i].setText(": "+0+" (wins)");
    }
  }
  
  /**
   * Updates the Name and Score labels for all the players passed in order of the array.
   */
  @Override
  public void updatePlayers(Connect4Player[] players) {
    for(int i=0; i<players.length; i++) {
      playerLabels[i].setText(players[i].getName());
      scoreLabels[i].setText(": "+players[i].getWins()+" (wins)");
    }
  }
  
  /*
   * Action performed by ADD PLAYER button.
   */
  private void addPlayer() {
    String name = playerNameField.getText();
    gamelog.append("Requested to add player, '"+name+"'\n");
    model.addPlayer(name);
  }

  /*
   * Action performed by ADD COMPUTER button.
   */
  private void addComputerOpponent() {
    gamelog.append("Requested to add a computer player.\n");
    model.addComputerPlayer();
  }
  
  /**
   * The View will update the appropriate Board Button for the player at the given position.
   */
  @Override
  public void makeMove(int playerTurn, int[] pos) {
    int row = pos[0];
    int col = pos[1];
    boardButtons[row][col].setText("O");
    boardButtons[row][col].setForeground(playerColors[playerTurn]);
  }
  
  /**
   * Prints the notification to the scrollable gamelog UI.
   */
  @Override
  public void notify(String message) {
    gamelog.append(message);
  }
  
}


