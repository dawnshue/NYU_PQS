package edu.nyu.cs.psq.ps4;

/**
 * Connect4App creates a model and listener
 * @author Vangie
 *
 */
public class Connect4App {

  private void go() {
    Connect4Model model = new Connect4Model();
    Connect4View view = new Connect4View(model);
  }
  
  public static void main(String[] args) {
    new Connect4App().go();
  }
}
