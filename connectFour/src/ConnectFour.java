/**
 * Author: Umid Muzrapov. ConnectFour The program allows two users to play ConnectFour game. The
 * target values can be from 3 to 6 for win. The outcome of the game can be draw or win. The user
 * can play game again if they want.
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class ConnectFour {
  public static Scanner input = new Scanner(System.in);

  /**
   * main is main logic of the game. It takes user information, switches the users, checks for the
   * wins,and repeats the game if prompted.
   *
   * @param args its value is not used here.
   */
  public static void main(String[] args) {
    int winningTokenNumber = getTargetValue();
    String[] userInput = takeUserInformation();
    String[] player1 = new String[] {userInput[0], "R", "Red"};
    String[] player2 = new String[] {userInput[1], "Y", "Yellow"};
    String[] currentPlayer = player1;
    boolean someoneWon = false;

    // Build the grid and print it.
    String[][] grid = new String[6][7];
    buildGrid(grid);
    printGrid(grid);

    while (!someoneWon && !isGridFull(grid)) // Play the game
    {
      someoneWon = playGame(grid, currentPlayer, winningTokenNumber);

      if (!someoneWon) {
        currentPlayer = switchPlayers(player1, player2, currentPlayer);
      }
    }

    playAgain(args);
  }

  /**
   * getTargetValue method asks the user for the winning token number and then it verifies the
   * input.
   *
   * @return integer value for the winning token number.
   */
  static int getTargetValue() {
    System.out.print("How many tokens should match to win(3-6)? ");
    String winningTokenNumberString = input.nextLine().trim();
    ArrayList<String> numbers = new ArrayList<>(Arrays.asList("3", "4", "5", "6"));

    while (!numbers.contains(winningTokenNumberString)) {
      System.out.println(
          "Sorry,"
              + " it seems like you have entered the wrong winning"
              + "token value.\nPlease enter a value between 3 and 6.  ");
      winningTokenNumberString = input.nextLine().trim();
    }

    int winningTokenNumber = Integer.parseInt(winningTokenNumberString);
    return winningTokenNumber;
  }

  /**
   * takeUserInformation takes in the user information, including their names and color of choice.
   *
   * @return the array with strings
   */
  static String[] takeUserInformation() {
    System.out.print("What's the name of player 1? ");
    String person1 = input.nextLine();
    System.out.print("Please choose choose 'R'(red) or 'Y'{yellow) ");
    String colorPerson1 = input.nextLine().trim().toUpperCase();

    // Validate the color
    while (!(colorMatches(colorPerson1))) {
      System.out.print(
          "It seems like"
              + " you have entered an invalid color. \n"
              + "Please enter either 'R' for red or 'Y' for yellow: ");
      colorPerson1 = input.nextLine().trim().toUpperCase();
    }

    System.out.print("What's the name of player 2? ");
    String person2 = input.nextLine();

    if (colorPerson1.equals("R")) {
      System.out.println("Your color is yellow since player 1 chose red.");
      return new String[] {person1, person2};
    } else {
      System.out.println("Your color is red since player 1 chose yellow.");
      return new String[] {person2, person1};
    }
  }

  /**
   * colorMatches makes sure that the used has chosen one of the available colors.
   *
   * @return true if the valid color is chosen.
   */
  static boolean colorMatches(String colorPerson1) {
    ArrayList<String> colors = new ArrayList<>(Arrays.asList("Y", "R"));

    if (colors.contains(colorPerson1.toUpperCase())) {
      return true;
    }

    return false;
  }

  /**
   * buildGrid replaces null values in the original 2-d string to empty spaces.
   *
   * @param grid 2-d with the strings
   */
  static void buildGrid(String[][] grid) {
    int row = 6;
    int column = 7;

    for (int rowNumber = 0; rowNumber < row; rowNumber++) {
      for (int columnNumber = 0; columnNumber < column; columnNumber++) {
        grid[rowNumber][columnNumber] = " ";
      }
    }
  }

  /**
   * printGrid prints out the grid for the user
   *
   * @param grid 2-d with the strings
   */
  static void printGrid(String[][] grid) {
    int row = 6;
    int column = 7;
    System.out.println("-".repeat(5) + "GRID" + "-".repeat(5));

    for (int rowNumber = 0; rowNumber < row; rowNumber++) {
      for (int columnNumber = 0; columnNumber < column; columnNumber++) {
        System.out.print("|" + grid[rowNumber][columnNumber]);
      }
      System.out.println("|");
    }
    System.out.println("_".repeat(15) + '\n');
  }

  /**
   * isGridFull checks if the grid is full or not.
   *
   * @param grid 2-d array with the strings.
   * @return only true if the grid is full.
   */
  static boolean isGridFull(String[][] grid) {
    int row = 6;
    int column = 7;

    for (int rowNumber = 0; rowNumber < row; rowNumber++) {
      for (int columnNumber = 0; columnNumber < column; columnNumber++) {
        if (grid[rowNumber][columnNumber].equals(" ")) {
          return false;
        }
      }
    }

    System.out.println("The grid is full. It is a draw. ");
    return true;
  }

  /**
   * playGame asks for the column to move, processes the move checks for the win.
   *
   * @param grid 2-d array with the strings.
   * @param currentPlayer a string array that represents the current player
   * @param winningTokenNumber integer that is the target value
   * @return only true if there is a winner.
   */
  static boolean playGame(String[][] grid, String[] currentPlayer, int winningTokenNumber) {
    int columnChosen = getColumnNumber(currentPlayer, grid);
    processMove(grid, currentPlayer, columnChosen);
    printGrid(grid);
    boolean someoneWon = checkWin(currentPlayer, grid, winningTokenNumber);

    if (someoneWon) {
      System.out.println(currentPlayer[0] + " won. Congratulations.");
    }

    return someoneWon;
  }

  /**
   * getColumnNumber gets the valid column number
   *
   * @param currentPlayer a string array that represents the current player
   * @param grid 2-d array with the strings.
   * @return integer, valid column number
   */
  static int getColumnNumber(String[] currentPlayer, String[][] grid) {
    System.out.println(currentPlayer[2] + " to play. Pick a column (1-7): ");
    String columnChosenString = input.nextLine().trim();

    // make sure that the valid number is given.
    while (!columnChosenValid(columnChosenString)) {
      System.out.println("Your input is invalid. \nPlease enter a valid input: ");
      columnChosenString = input.nextLine().trim();
    }

    int columnChosen = Integer.parseInt(columnChosenString) - 1;

    while (!columnFree(grid, columnChosen)) {
      System.out.println("The column is full.\n");
      // Need this recursion to check the validity of the colNumb again.
      columnChosen = getColumnNumber(currentPlayer, grid);
    }

    return columnChosen;
  }

  /**
   * columnChosenValid checks if the entered value is in valid and in the right range.
   *
   * @param columnChosenString String that is the chosen column
   * @return only true if the input is valid.
   */
  static boolean columnChosenValid(String columnChosenString) {
    ArrayList<String> numbers = new ArrayList<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7"));

    if (numbers.contains(columnChosenString)) return true;

    return false;
  }

  /**
   * columnFree checks if the given column is free.
   *
   * @param grid 2-d array with the strings.
   * @param columnChosen integer that is the column chosen.
   * @return only true if the column is free.
   */
  static boolean columnFree(String[][] grid, int columnChosen) {
    int rowNumber = 0;

    while (rowNumber < 6 && columnChosen < 7 && columnChosen >= 0) {
      if (grid[rowNumber][columnChosen].equals(" ")) {
        return true;
      }
      rowNumber++;
    }

    return false;
  }

  /**
   * processMove processes the move by changing the grid.
   *
   * @param grid 2-d array with the strings.
   * @param currentPlayer string array that represents the current player
   * @param columnChosen integer that is the chosen column.
   */
  static void processMove(String[][] grid, String[] currentPlayer, int columnChosen) {
    int row = 6;

    for (int rowNumber = 0; rowNumber < row; rowNumber++) {
      if (!grid[rowNumber][columnChosen].equals(" ")) {
        grid[rowNumber - 1][columnChosen] = currentPlayer[1];
        return;
      }
    }
    grid[5][columnChosen] = currentPlayer[1];
  }

  /**
   * checkWin checks the input horizontally, vertically, in both diagonals only in the locations
   * where the player appears.
   *
   * @param currentPlayer string array that represents the current player
   * @param grid 2-d array with the strings.
   * @param winningTokenNumber integer that is the target value
   * @return only true if the win is found.
   */
  static boolean checkWin(String[] currentPlayer, String[][] grid, int winningTokenNumber) {
    ArrayList<int[]> locationsToSearch = getLocations(currentPlayer, grid);

    for (int[] location : locationsToSearch) {
      // Checks sides and diagonals.
      if (checkVertical(location, grid, winningTokenNumber, currentPlayer)
          || checkHorizontal(location, grid, winningTokenNumber, currentPlayer)
          || checkDiagonalSouthEast(location, grid, winningTokenNumber, currentPlayer)
          || checkDiagonalNorthWest(location, grid, winningTokenNumber, currentPlayer)) {
        return true;
      }
    }

    return false;
  }

  /**
   * getLocations finds the locations of the current player to check for the win.
   *
   * @param currentPlayer string array that represents the current player
   * @param grid 2-d array with the strings.
   * @return ArrayList of integer arrays to represent r,c location.
   */
  static ArrayList<int[]> getLocations(String[] currentPlayer, String[][] grid) {
    ArrayList<int[]> locationsToSearch = new ArrayList<int[]>();
    String searchedPlayer = currentPlayer[1];
    int row = 6;
    int column = 7;

    for (int rowNumber = 0; rowNumber < row; rowNumber++) {
      for (int columnNumber = 0; columnNumber < column; columnNumber++) {
        if (grid[rowNumber][columnNumber].equals(currentPlayer[1])) {
          locationsToSearch.add(new int[] {rowNumber, columnNumber});
        }
      }
    }

    return locationsToSearch;
  }

  /**
   * checkHorizontal checks for the location horizontally.
   *
   * @param location the integer string that shows the r,w where the player appears
   * @param grid 2-d array with the strings.
   * @param winningTokenNumber integer that is the target value.
   * @param currentPlayer string array that represents the current player
   * @return only true if the win is found.
   */
  static boolean checkHorizontal(
      int[] location, String[][] grid, int winningTokenNumber, String[] currentPlayer) {
    int rowStart = location[0];
    int columnStart = location[1];

    if (columnStart + winningTokenNumber <= 7) {
      for (int columnNumber = 0; columnNumber < winningTokenNumber; columnNumber++) {
        if (!(grid[rowStart][columnStart + columnNumber].equals(currentPlayer[1]))) {
          return false;
        }
      }
      return true;
    }

    return false;
  }

  /**
   * checkVertical checks for the location vertically.
   *
   * @param location the integer string that shows the r,w where the player appears
   * @param grid 2-d array with the strings.
   * @param winningTokenNumber integer that is the target value.
   * @param currentPlayer string array that represents the current player
   * @return only true if the win is found.
   */
  static boolean checkVertical(
      int[] location, String[][] grid, int winningTokenNumber, String[] currentPlayer) {
    int columnStart = location[1];
    int rowStart = location[0];
    if (rowStart + winningTokenNumber <= 6) {
      for (int rowNumber = 0; rowNumber < winningTokenNumber; rowNumber++) {
        if (!(grid[rowStart + rowNumber][columnStart].equals(currentPlayer[1]))) {
          return false;
        }
      }
      return true;
    }

    return false;
  }

  /**
   * cgeckDiagonalSouthEast checks for the location south-east.
   *
   * @param location the integer string that shows the r,w where the player appears
   * @param grid 2-d array with the strings.
   * @param winningTokenNumber integer that is the target value.
   * @param currentPlayer string array that represents the current player
   * @return only true if the win is found.
   */
  static boolean checkDiagonalSouthEast(
      int[] location, String[][] grid, int winningTokenNumber, String[] currentPlayer) {
    int columnStart = location[1];
    int rowStart = location[0];

    if (rowStart + winningTokenNumber <= 6 && columnStart + winningTokenNumber <= 7) {
      for (int increment = 0; increment < winningTokenNumber; increment++) {
        if (!grid[rowStart + increment][columnStart + increment].equals(currentPlayer[1])) {
          return false;
        }
      }
      return true;
    }

    return false;
  }

  /**
   * checkDiagonalNorthWest checks for the location north-west.
   *
   * @param location the integer string that shows the r,w where the player appears
   * @param grid 2-d array with the strings.
   * @param winningTokenNumber integer that is the target value.
   * @param currentPlayer string array that represents the current player
   * @return only true if the win is found.
   */
  static boolean checkDiagonalNorthWest(
      int[] location, String[][] grid, int winningTokenNumber, String[] currentPlayer) {
    int columnStart = location[1];
    int rowStart = location[0];

    if (rowStart + winningTokenNumber <= 6 && columnStart - winningTokenNumber >= -1) {
      for (int increment = 0; increment < winningTokenNumber; increment++) {
        if (!(grid[rowStart + increment][columnStart - increment].equals(currentPlayer[1]))) {
          return false;
        }
      }
      return true;
    }

    return false;
  }

  /**
   * switchPlayers switches the players.
   *
   * @param player1 string array that represents player 1
   * @param player2 string array that represents player 2
   * @param currentPlayer string array that represents player
   * @return string array that is a player.
   */
  static String[] switchPlayers(String[] player1, String[] player2, String[] currentPlayer) {
    if (player1 == currentPlayer) return player2;
    else return player1;
  }

  /**
   * playAgain asks the user if they want to play again. Based on their response, it restarts the
   * new game, or exits.
   *
   * @param args not used here
   */
  static void playAgain(String[] args) {
    System.out.println("Do you want to play again (Y='yes'): ");
    String playAgain = input.nextLine().trim().toUpperCase();

    if (playAgain.trim().toUpperCase().equals("Y")) {
      System.out.println("\n ------New Game------");
      main(args);
    } else System.out.println("The game is over. Thank you for playing.");
  }
}
