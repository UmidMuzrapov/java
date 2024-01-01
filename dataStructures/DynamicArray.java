/**
 * DynamicArray stores a collection of integers sequentially. It uses int[] for implementation and
 * resizes dynamically.
 */
public class DynamicArray {
  // The initial size is 16.
  public final int INITIAL_SIZE = 16;
  private int[] data = new int[INITIAL_SIZE];
  private int size = 0;

  /** DynamicArray() is an empty constructor. */
  public DynamicArray() {}

  /**
   * DynamicArray(DynamicArray anotherDynamicArray) is a copy constructor.
   *
   * @param anotherDynamicArray DynamicArray
   */
  public DynamicArray(DynamicArray anotherDynamicArray) {
    int[] intArray = anotherDynamicArray.getData();

    for (int i = 0; i < anotherDynamicArray.size(); i++) {
      this.add(size, intArray[i]);
    }
  }

  /**
   * getData returns the data field.
   *
   * @return int[]
   */
  public int[] getData() {
    return data;
  }

  /**
   * add adds a value at the specified index.
   *
   * @param index int
   * @param newInteger int
   */
  public void add(int index, int newInteger) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException("Index is invalid.");
    }
    adjustCapacity();

    // shift the elements
    for (int i = size - 1; i >= index; i--) {
      data[i + 1] = data[i];
    }

    data[index] = newInteger;
    size++;
  }

  /** clear removes all the elements in the dynamic array. */
  public void clear() {
    data = new int[INITIAL_SIZE];
    size = 0;
  }

  /**
   * contains checks if the value is in the dynamic array.
   *
   * @param integer int
   * @return boolean
   */
  public boolean contains(int integer) {
    for (int i = 0; i < size; i++) {
      if (data[i] == integer) return true;
    }
    return false;
  }

  /**
   * get returns the element at the specified index.
   *
   * @param index int
   * @return int
   */
  public int get(int index) {
    validateIndex(index);
    return data[index];
  }

  /**
   * remove removes and returns an element at the specified index.
   *
   * @param index int
   * @return removedInteger int
   */
  public int remove(int index) {
    //
    // return -1 if ArrayList object is empty.
    if (size == 0) {
      return -1;
    } else {
      int removedInteger = data[index];

      for (int i = index; i < size - 1; i++) {
        data[i] = data[i + 1];
      }

      data[size - 1] = 0;
      size--;

      return removedInteger;
    }
  }

  /**
   * toString returns the string that contains the elements of the dynamic array.
   *
   * @return string String
   */
  public String toString() {
    String string = "{";

    if (size > 0) {
      for (int i = 0; i < size - 1; i++) {
        string = string + data[i] + ",";
      }
      string = string + data[size - 1] + "}";
    } else {
      string = "{}";
    }

    return string;
  }

  /**
   * size returns the number of elements in dynamic array.
   *
   * @return size int
   */
  public int size() {
    return size;
  }

  /**
   * equals checks if the given object is equal to the dynamic array. The two objects are equal if
   * they are of the same type and each element matches.
   *
   * @param Object object
   * @return boolean
   */
  public boolean equals(Object object) {
    if (!(object instanceof DynamicArray)) return false;

    if (((DynamicArray) object).size() != size) return false;
    else {
      for (int i = 0; i < size; i++) {
        if (((DynamicArray) object).get(i) != this.get(i)) {
          return false;
        }
      }
      return true;
    }
  }

  /**
   * adjustCapacity increases the capacity of dynamic array twice if the current capacity has filled
   * up.
   */
  private void adjustCapacity() {
    if (size >= data.length) {
      int[] newData = new int[size * 2];
      copyArray(data, newData);
      data = newData;
    }
  }

  /**
   * copyArray deep copies the given array.
   *
   * @param oldArray int[]
   * @param newArray int[]
   */
  private void copyArray(int[] oldArray, int[] newArray) {
    for (int i = 0; i < size; i++) {
      newArray[i] = oldArray[i];
    }
  }

  /**
   * validateIndex ensures that the given index is valid.
   *
   * @param index int
   */
  private void validateIndex(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException(index + " is out of bounds for size " + size);
    }
  }
}
