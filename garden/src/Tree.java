/**
 * @author: Umid Muzrapov Tree class inherits from Plant. It represents a tree.
 */
public class Tree extends Plant {
  /**
   * The constructor builds a tree with a specified name.
   *
   * @param treeName String
   */
  public Tree(String treeName) {
    super.plantName = treeName.toLowerCase();
    super.plantArray[4][2] = this.plantName.charAt(0);
  }

  /**
   * grow grows the tree up specified number of times.
   *
   * @param timesToGrow int
   */
  public void grow(int timesToGrow) {
    int count = 0;

    while (count < timesToGrow) {
      for (int rowNumber = 3; rowNumber >= 0; rowNumber--) {
        if (super.plantArray[rowNumber][2] == '.') {
          plantArray[rowNumber][2] = this.plantName.charAt(0);
          break;
        }
      }
      count++;
    }
  }
}
