/**
 * ArrayQueue represents a queue, where we remove the element from the front and add at the bottom
 * --LIFO. It uses DynamicArray for implementation.
 */
public class ArrayQueue implements QueueInterface {
  DynamicArray data = new DynamicArray();

  /** O(1) ArrayQueue() is an empty constructor. */
  public ArrayQueue() {}

  /**
   * O(n) ArrayQueue(ArrayQueue arrayQueueToCopy) is a copy constructor.
   *
   * @param arrayQueueToCopy
   */
  public ArrayQueue(ArrayQueue arrayQueueToCopy) {
    data = new DynamicArray(arrayQueueToCopy.getData());
  }

  @Override
  /** O(1)*. * if it need resizing -> O(n) enqueue adds the element to the back of the queue. */
  public void enqueue(int value) {
    data.add(data.size(), value);
  }

  /**
   * O(1) getData returns the dataField of the queue.
   *
   * @return DynamicArray
   */
  public DynamicArray getData() {
    return data;
  }

  @Override
  /**
   * O(n) dequeue removes the front element and returns it.
   *
   * @return int
   */
  public int dequeue() {
    return data.remove(0);
  }

  @Override
  /**
   * O(1) peek return the front element.
   *
   * @return int
   */
  public int peek() {
    return data.get(0);
  }

  @Override
  /**
   * O(1) isEmpty checks if the queue is empty
   *
   * @return boolean
   */
  public boolean isEmpty() {
    if (data.size() == 0) {
      return true;
    } else return false;
  }

  @Override
  /**
   * O(1) size returns the number of element in the queue.
   *
   * @return int
   */
  public int size() {
    return data.size();
  }

  @Override
  /** O(1) clear removes all the elements of the queue. */
  public void clear() {
    data.clear();
  }

  /**
   * O(n) toString returns the string containing all the elements of the queue.
   *
   * @return String
   */
  public String toString() {
    return data.toString();
  }

  /**
   * O(n) equals if the passed object is equal to the queue. The two objects are equal if they are
   * of the same type and each element matches.
   *
   * @param object
   * @return
   */
  public boolean equals(Object object) {
    if (!(object instanceof ArrayQueue)) return false;

    if (this.data.equals(((ArrayQueue) object).getData())) {
      return true;
    } else return false;
  }
}
