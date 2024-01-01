/**
 * @author: Umid Muzrapov PA3Main reads a file from the console, processes the commands and performs
 *     them. The main purpose of the code is to simulate a garden.
 */
import java.io.*;
import java.util.*;

public class PA3Main {
  // To avoid threading issues, there is only one Scanner object.
  static Scanner keyboard = new Scanner(System.in);

  /**
   * main is the program's starting point.
   *
   * @param args String[].
   */
  public static void main(String[] args) {
    // Get the information from the file.
    ArrayList<String[]> inputInformation = getInformation(args[0].trim());
    ArrayList<String> rawData = getRawData(args[0]);

    // Initialize the garden
    Garden garden = buildGarden(inputInformation);
    performCommands(inputInformation, rawData, garden);
  }

  ///// Methods to get and format data from inputFile here/////

  /**
   * getInformation reads the information from the file, parses it and formats it before adding it
   * inputInformation ArrayList.
   *
   * @param fileName String
   * @return inputInformation zArrayList<String[]> >
   */
  static ArrayList<String[]> getInformation(String fileName) {
    ArrayList<String[]> inputInformation = new ArrayList<String[]>();
    boolean fileFound = false;

    while (!fileFound) {
      try {
        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);
        fileFound = true;
        getSize(inputFile, inputInformation);
        getCommands(inputFile, inputInformation);
      } catch (FileNotFoundException | NoSuchElementException fileNotFoundOrEmpty) {
        System.out.println("The file you types does not exist. Try again: ");
        fileName = keyboard.nextLine().trim();
      }
    }

    return inputInformation;
  }

  /**
   * getRawData reads the file and creates an ArrayList of String containing unedited commands.
   *
   * @param fileName String
   * @return rawData ArrayList<String>
   */
  static ArrayList<String> getRawData(String fileName) {
    ArrayList<String> rawData = new ArrayList<String>();
    boolean fileFound = false;

    while (!fileFound) {
      try {
        File file = new File(fileName);
        Scanner inputFile = new Scanner(file);

        // Get rid of the first three lines.
        inputFile.nextLine();
        inputFile.nextLine();
        inputFile.nextLine();
        rawData.add("test");
        fileFound = true;

        while (inputFile.hasNext()) {
          String[] command = inputFile.nextLine().split(" ");
          command[0] = command[0].toUpperCase();
          rawData.add(String.join(" ", command));
        }
      } catch (FileNotFoundException | NoSuchElementException fileNotFoundOrEmpty) {
        System.out.println("The file you types does not exist. Try again: ");
        fileName = keyboard.nextLine();
      }
    }
    return rawData;
  }

  /**
   * getSize gets the size of the garden.
   *
   * @param inputFile String
   * @param inputInformation ArrayList<String[]>
   */
  static void getSize(Scanner inputFile, ArrayList<String[]> inputInformation) {
    String[] size = new String[2];
    String[] line1 = inputFile.nextLine().split(" ");
    String[] line2 = inputFile.nextLine().split(" ");

    // choose the size of column and row.
    if (line1[0].toLowerCase().startsWith("row")) {
      size[0] = line1[1];
      size[1] = line2[1];
    } else {
      size[0] = line2[1];
      size[1] = line1[1];
    }

    inputInformation.add(size);
  }

  /**
   * getCommands is the command-parsing center. Based on the length of the command, it calls the
   * right command-parsing method.
   *
   * @param inputFile String
   * @param inputInformation ArrayList<String[]>
   */
  static void getCommands(Scanner inputFile, ArrayList<String[]> inputInformation) {
    inputFile.nextLine();

    while (inputFile.hasNext()) {
      String[] command = inputFile.nextLine().split(" ");

      command[0] = command[0].toUpperCase();

      int commandLength = command.length;

      switch (commandLength) {
        case 1:
          inputInformation.add(new String[] {command[0]});
          break;
        case 2:
          command = processLengthTwoCommand(command);
          inputInformation.add(command);
          break;

        case 3:
          command = processLengthThreeCommand(command);
          inputInformation.add(command);
          break;
        default:
          System.out.println(
              String.join(" ", command) + "is in the wrong format or does not exist.");
      }
    }
  }

  /**
   * processLengthTwoCommand parses commands of length two.
   *
   * @param command String[]
   * @return newCommand String[]
   */
  static String[] processLengthTwoCommand(String[] command) {
    String secondCommand = command[1];
    String[] newCommand = new String[3];
    if (isNumeric(secondCommand)) {
      return command;
    } else {
      if (isAlpha(secondCommand)) {
        return command;
      } else {
        newCommand[0] = command[0];
        newCommand[1] = Character.toString(secondCommand.charAt(1));
        newCommand[2] = Character.toString(secondCommand.charAt(3));
        return newCommand;
      }
    }
  }

  /**
   * processLengthTwoCommand parses commands of length three.
   *
   * @param command String[]
   * @return newCommand String[]
   */
  static String[] processLengthThreeCommand(String[] command) {
    String thirdCommand = command[2];
    String secondCommand = command[1];

    if (isAlpha(thirdCommand) && isNumeric(secondCommand)) {
      return command;
    } else {
      String[] newCommand = new String[4];
      if (isNumeric(secondCommand)) {
        newCommand[0] = command[0];
        newCommand[1] = command[1];
        newCommand[2] = Character.toString(thirdCommand.charAt(1));
        newCommand[3] = Character.toString(thirdCommand.charAt(3));
      } else {
        newCommand[0] = command[0];
        newCommand[3] = command[2];
        newCommand[1] = Character.toString(secondCommand.charAt(1));
        newCommand[2] = Character.toString(secondCommand.charAt(3));
      }

      return newCommand;
    }
  }

  ///// Methods to Perform Commands Here/////

  /**
   * performCommands performs the valid commands. If the command is not supported, it says so.
   *
   * @param inputInformation ArrayList<String[]>
   * @param rawData ArrayList<String>
   * @param garden Garden
   */
  static void performCommands(
      ArrayList<String[]> inputInformation, ArrayList<String> rawData, Garden garden) {
    ArrayList<String> possibleCommands = getPossibleCommands();

    for (int commandIndex = 1; commandIndex < inputInformation.size(); commandIndex++) {
      String[] command = inputInformation.get(commandIndex);
      String actionVerb = command[0];

      if (!command[0].equals("PLANT")) {
        System.out.println("> " + rawData.get(commandIndex));
      }

      if (!command[0].equals("PLANT") && !command[0].equals("PRINT")) {
        System.out.println("  ");
      }

      // Here we call the right method to perform the command.
      if (possibleCommands.contains(actionVerb)) {
        callRightPerformCommand(garden, command);
      } else {
        System.out.println("Sorry I don't how to " + actionVerb + "\n");
      }
    }
  }

  /**
   * callRightPerformCommand calls the right method to perform the command.
   *
   * @param garden Garden
   * @param command String[]
   */
  private static void callRightPerformCommand(Garden garden, String[] command) {
    switch (command.length) {
      case 1:
        performLengthOneCommand(command, garden);
        break;
      case 2:
        performLengthTwoCommand(command, garden);
        break;
      case 3:
        performLengthThreeCommand(command, garden);
        break;
      case 4:
        performLengthFourCommand(command, garden);
        break;
      default:
        System.out.println("Sorry, you command is too long.");
    }
  }

  /**
   * performLengthOneCommand performs the commands with a single argument, the verb.
   *
   * @param command String[]
   * @param garden Garden
   */
  static void performLengthOneCommand(String[] command, Garden garden) {
    String commandAction = command[0];
    HashMap<String, Plant> removeMap = getRemoveMap();

    if (commandAction.equals("PRINT")) {
      garden.showPlants();
    } else {
      garden.remove(removeMap.get(commandAction));
    }
  }

  /**
   * performLengthOneCommand performs the commands that have 2 arguments.
   *
   * @param command String[]
   * @param garden Garden
   */
  static void performLengthTwoCommand(String[] command, Garden garden) {
    ArrayList<String> removeList = getRemoveList();
    String commandAction = command[0];

    if (commandAction.equals("GROW") && isNumeric(command[1])) {
      garden.growMyGarden(Integer.parseInt(command[1]));
    } else if (removeList.contains(commandAction) && isAlpha(command[1])) {
      garden.remove(command[1].toLowerCase());
    } else {
      System.out.println("The command is in the wrong format. \n");
    }
  }

  /**
   * performLengthOneCommand performs the commands that have three arguments.
   *
   * @param command String[]
   * @param garden Garden
   */
  static void performLengthThreeCommand(String[] command, Garden garden) {
    String commandAction = command[0];
    HashMap<String, Plant> removeMap = getRemoveMap();

    if (commandAction.equals("GROW")) {
      String plantType = command[2].toLowerCase();
      garden.growMyPlantType(Integer.parseInt(command[1]), plantType);
    } else {
      garden.remove(
          removeMap.get(commandAction), Integer.parseInt(command[1]), Integer.parseInt(command[2]));
    }
  }

  /**
   * performLengthOneCommand performs the commands that have four arguments.
   *
   * @param command String[]
   * @param garden Garden
   */
  static void performLengthFourCommand(String[] command, Garden garden) {
    String commandAction = command[0];

    if (commandAction.equals("GROW")) {
      garden.growPlantInPlot(
          Integer.parseInt(command[1]), Integer.parseInt(command[2]), Integer.parseInt(command[3]));
    } else {
      garden.plantInGarden(Integer.parseInt(command[1]), Integer.parseInt(command[2]), command[3]);
    }
  }

  ///// Helper Methods/////

  /**
   * isAlpah checks if all characters in String are letters or symbols.
   *
   * @param stringToCheck String
   * @return boolean true if none of the characters is not a digit.
   */
  static boolean isAlpha(String stringToCheck) {
    for (char character : stringToCheck.toCharArray()) {
      try {
        Double.parseDouble(Character.toString(character));
        return false;
      } catch (NumberFormatException isNotNumber) {
        // It is a letter
      }
    }
    return true;
  }

  /**
   * isNumeric checks if all characters in string are digits.
   *
   * @param stringToCheck String
   * @return boolean true if each character is a digit.
   */
  static boolean isNumeric(String stringToCheck) {
    for (char character : stringToCheck.toCharArray()) {
      try {
        Double.parseDouble(Character.toString(character));
        // it is a digit
      } catch (NumberFormatException isNotNumber) {
        // It is a letter
        return false;
      }
    }
    return true;
  }

  /**
   * checkNumColumns makes sure that a valid value for the column size is given.
   *
   * @param numColumnsString String
   * @return numColumn int
   */
  static int checkNumColumns(String numColumnsString) {
    if (isNumeric(numColumnsString)) {
      int numColumns = Integer.parseInt(numColumnsString);
      if (numColumns > 16) {
        System.out.println(
            "The system allows up 16 columns.\n "
                + "Your garden width is adjusted to maximum 16 plots.");
        return 16;
      } else return numColumns;
    } else {
      System.out.println(
          "The value for column is not numeric or negative.\n" + "Enter numeric value:");
      numColumnsString = keyboard.nextLine();
      return checkNumColumns(numColumnsString);
    }
  }

  /**
   * checkNumRow makes sure that the valid value is given for the size of the garden raw.
   *
   * @param numRowsString String
   * @return numRows int
   */
  static int checkNumRows(String numRowsString) {

    if (isNumeric(numRowsString)) {
      int numRows = Integer.parseInt(numRowsString);
      return numRows;
    } else {
      System.out.println(
          "The value for row is not numeric or negative.\n" + "Enter numeric value:");
      numRowsString = keyboard.nextLine();
      return checkNumRows(numRowsString);
    }
  }

  /**
   * getRemoveList gives a list of remove methods -- Harvest, Cut, Pick.
   *
   * @return remove Commands ArrayList<String>
   */
  static ArrayList<String> getRemoveList() {
    ArrayList<String> removeCommands = new ArrayList<String>();
    removeCommands.add("HARVEST");
    removeCommands.add("CUT");
    removeCommands.add("PICK");
    return removeCommands;
  }

  /**
   * getRemoveMap maps the remove verb to plant type.
   *
   * @return removeMap HashMap<String, Plant>
   */
  static HashMap<String, Plant> getRemoveMap() {
    Vegetable testVegetable = new Vegetable("Vegetable");
    Tree testTree = new Tree("Tree");
    Flower testFlower = new Flower("Flower");
    HashMap<String, Plant> removeMap = new HashMap<String, Plant>();
    removeMap.put("HARVEST", testVegetable);
    removeMap.put("PICK", testFlower);
    removeMap.put("CUT", testTree);

    return removeMap;
  }

  /**
   * getPossibleCommands gives possible commands this code can do.
   *
   * @return possibleCOmmands ArrayList<String>
   */
  static ArrayList<String> getPossibleCommands() {
    ArrayList<String> possibleCommands = new ArrayList<String>();
    possibleCommands.add("PRINT");
    possibleCommands.add("PLANT");
    possibleCommands.add("GROW");
    possibleCommands.add("HARVEST");
    possibleCommands.add("PICK");
    possibleCommands.add("CUT");

    return possibleCommands;
  }

  /**
   * buildGarden builds a garden after validating the given size of it.
   *
   * @param inputInformation ArrayList<String[]>
   * @return garden Garden
   */
  private static Garden buildGarden(ArrayList<String[]> inputInformation) {
    String[] size = inputInformation.get(0);
    int numColumns = checkNumColumns(size[1]);
    int numRows = checkNumRows(size[0]);

    Garden garden = new Garden(numRows, numColumns);
    return garden;
  }
}
