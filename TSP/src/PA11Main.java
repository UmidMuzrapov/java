import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The main class reads the filename and command, processes the input file and creates a graph.
 * Based on the command, it solves traveling salesperson problem, using one of the 4 methods.
 *
 * @author Umid Muzrapov.
 */
public class PA11Main {
  /**
   * This where the program begins. If the file is not found, the program says so and stops.
   *
   * @param args the command line arguments.
   */
  public static void main(String[] args) {
    try {
      executeCommands(args);

    } catch (FileNotFoundException ex) {
      System.out.println("File was not found.");
    }
  }

  /**
   * This method performs the right operation on the graph based on the user command.
   *
   * @param args the command line arguments.
   * @throws FileNotFoundException an exception thrown if the file is not found.
   */
  private static void executeCommands(String[] args) throws FileNotFoundException {
    ArrayList<Number[]> myInputFile = processInput(args[0]);
    int numberVertices = (int) myInputFile.get(0)[0];
    DGraph myGraph = new DGraph(numberVertices, myInputFile);

    if (args[1].equalsIgnoreCase("Heuristic")) {
      myGraph.useHeuristic(1, false);
    } else if (args[1].equalsIgnoreCase("Backtrack")) {
      myGraph.useBacktrack(1, false);
    } else if (args[1].equalsIgnoreCase("Mine")) {
      myGraph.mineHeuristic(1, false);
    }

    // Mine 2 command is just done for fun.
    else if (args[1].equalsIgnoreCase("Mine2")) {
      myGraph.mineBacktrack(1, false);
    }

    // Mine 3 command is just done for fun.
    else if (args[1].equalsIgnoreCase("Mine3")) {
      myGraph.mineHeuristicNoStorting(1, false);
    } else if (args[1].equalsIgnoreCase("Time")) {
      myGraph.useHeuristic(1, true);
      myGraph.mineHeuristic(1, true);
      myGraph.useBacktrack(1, true);
    }

    // just to check all outputs at once.
    else if (args[1].equalsIgnoreCase("All")) {
      myGraph.useHeuristic(1, false);
      myGraph.mineHeuristicNoStorting(1, false);
      myGraph.mineHeuristic(1, false);
      myGraph.mineBacktrack(1, false);
      myGraph.useBacktrack(1, false);
    }
  }

  /**
   * This method opens the file, processes each line if it is not commented.
   *
   * @param fileName the name of the file to open.
   * @return the list of the number of the vertices and edges.
   * @throws FileNotFoundException an exception thrown if the file is not found.
   */
  public static ArrayList<Number[]> processInput(String fileName) throws FileNotFoundException {
    ArrayList<Number[]> processedInput = new ArrayList<>();
    Scanner inputFile = new Scanner(new File(fileName));

    while (inputFile.hasNext()) {
      String string = inputFile.nextLine();

      if (!string.startsWith("%") && (string.trim().length() > 0)) {
        // call a helped function to correctly parse the input line.
        processedInput.add(processStringLine(string));
      }
    }

    return processedInput;
  }

  /**
   * This method correctly processes the input line and store the well-formatted data in an array.
   *
   * @param string un-procesed input line.
   * @return the list of formatted input.
   */
  public static Number[] processStringLine(String string) {
    Number[] processedInputLine = new Number[3];
    String[] stringArray = string.split("\\s+");
    for (int i = 0; i < stringArray.length; i++) {
      if (i == 0 || i == 1) {
        processedInputLine[i] = Integer.parseInt(stringArray[i]);
      } else processedInputLine[i] = Double.parseDouble(stringArray[i]);
    }

    return processedInputLine;
  }
}
