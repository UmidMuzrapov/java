/**
 * @author: Umid Muzrapov Vegetable class inherits from the Vegetable class. It represents a
 *     vegetable.
 */
public class Vegetable extends Plant {
  /**
   * The constructor builds the vegetable with specified name.
   *
   * @param vegetableName String
   */
  public Vegetable(String vegetableName) {
    super.plantName = vegetableName;
    super.plantArray[0][2] = this.plantName.charAt(0);
  }

  /**
   * grow makes the vegetable grow top to bottom.
   *
   * @param timesToGrow int
   */
  public void grow(int timesToGrow) {
    int count = 0;

    while (count < timesToGrow) {
      for (int rowNumber = 0; rowNumber < 5; rowNumber++) {
        if (super.plantArray[rowNumber][2] == '.') {
          plantArray[rowNumber][2] = this.plantName.charAt(0);
          break;
        }
      }
      count++;
    }
  }
}
