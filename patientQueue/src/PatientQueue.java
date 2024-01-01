import java.util.NoSuchElementException;

/**
 * This class represent a PatientQueue, where patients with a lower priority number have a higher
 * priority.
 *
 * @author Umid Muzrapov
 */
public class PatientQueue {

  private MyHeap heap;

  /** This a constructor for the queue. Initially heap's size is 10. */
  public PatientQueue() {
    heap = new MyHeap();
  }

  /**
   * This method adds a new patient in the queue based on their priority. If the priorities are the
   * same, the the person's name will decide priority.
   *
   * @param name a person's name
   * @param priority their priority
   */
  public void enqueue(String name, int priority) {
    Patient newPatient = new Patient(name, priority);
    heap.add(newPatient);
  }

  /**
   * This method adds a new patient in the queue based on their priority. If the priorities are the
   * same, the the person's name will decide priority.
   *
   * @param patient a new Patient
   */
  public void enqueue(Patient patient) {
    Patient newPatient = new Patient(patient.name, patient.priority);
    heap.add(newPatient);
  }

  /**
   * The method removes the patient at the front of the queue. Exception is thrown if the queue is
   * empty.
   *
   * @return name of the frontmost patient.
   */
  public String dequeue() {
    return heap.remove().name;
  }

  /**
   * This method returns the priority of the frontmost patient without removing them.
   *
   * @return priority of the frontmost patient.
   */
  public int peekPriority() {
    heap.checkHeap();
    return heap.getData()[1].priority;
  }

  /**
   * This method returns the name of the frontmost patient without removing them.
   *
   * @return name of the frontmost patient.
   */
  public String peek() {
    heap.checkHeap();
    return heap.getData()[1].name;
  }

  /**
   * This method changes the priority of the given patient. If the name is not in the queue, nothing
   * happens.
   *
   * @param name name of the patient
   * @param newPriority the new priority of the patient.
   */
  public void changePriority(String name, int newPriority) {
    heap.modidyPatient(name, newPriority);
  }

  /**
   * This method checks if the queue is empty.
   *
   * @return true if it is empty.
   */
  public boolean isEmpty() {
    return (heap.size() == 0);
  }

  /**
   * This method returns the string representation of queue.
   *
   * @return queue state
   */
  public String toString() {
    return heap.toString();
  }

  /** This methods clears the queue. */
  public void clear() {
    heap.clear();
  }

  /**
   * This method returns the number of patients in the queue.
   *
   * @return size of the queue.
   */
  public int size() {
    return heap.size();
  }
}

/**
 * This class represents a minimum heap.
 *
 * @author Umid Muzrapov
 */
class MyHeap {

  private final int INITIAL_SIZE = 10;
  private Patient[] data;
  private int size = 0;

  /** This is a constructor for my heap. Initial capacity is 10. */
  public MyHeap() {
    data = new Patient[INITIAL_SIZE];
  }

  /**
   * This method add a new patient to the heap.
   *
   * @param newPatient a new patient
   */
  public void add(Patient newPatient) {
    adjustCapacity();

    // add the new patient to the end.
    data[size + 1] = newPatient;
    size++;
    int currentIndex = size;

    // bubble up if person's priority is higher.
    while (currentIndex > 1) {
      int parentIndex = (int) Math.floor(currentIndex / 2);

      if (newPatient.priority < this.data[parentIndex].priority) {
        Patient temp = this.data[parentIndex];
        data[parentIndex] = newPatient;
        data[currentIndex] = temp;
      } else if (newPatient.priority == this.data[parentIndex].priority) {

        if (newPatient.name.compareToIgnoreCase(this.data[parentIndex].name) < 0) {
          Patient temp = this.data[parentIndex];
          data[parentIndex] = newPatient;
          data[currentIndex] = temp;
        } else break;
      } else break;

      currentIndex = parentIndex;
    }
  }

  /**
   * This methods gets the copy of the heap data/elements.
   *
   * @return patient[] a heap of the patients.
   */
  public Patient[] getData() {
    return data;
  }

  /**
   * This method removes the frontmost patient and reorders the heap accordingly.
   *
   * @return Patient removed patient
   */
  public Patient remove() {
    checkHeap();

    Patient removedPatient = data[1];
    data[1] = data[size];
    size--;
    int currentIndex = 1;

    bubbleDown(currentIndex);

    return removedPatient;
  }

  /** This method adjusts the capacity if we are exceeding the size. */
  private void adjustCapacity() {
    if (size + 1 >= data.length) {
      // it is 2size+1 because we are beginning at index 1.
      Patient[] newData = new Patient[size * 2 + 1];
      copyArray(data, newData);
      data = newData;
    }
  }

  /**
   * This method copies the element of the old array to a larger array.
   *
   * @param oldArray
   * @param newArray
   */
  private void copyArray(Patient[] oldArray, Patient[] newArray) {
    for (int i = 1; i < size + 1; i++) {
      newArray[i] = oldArray[i];
    }
  }

  /**
   * This method throw an exception if the queue is empty when patient need to be removed or peeked.
   */
  public void checkHeap() {
    if (size == 0) {
      throw new NoSuchElementException("The queue is empty.");
    }
  }

  /** This method clears the heap. */
  public void clear() {
    data = new Patient[INITIAL_SIZE];
    size = 0;
  }

  /**
   * This method returns the string representation of the heap.
   *
   * @return String that represents the heap.
   */
  public String toString() {
    String string = "{";

    if (size > 0) {
      for (int i = 1; i < size; i++) {
        string = string + data[i] + ", ";
      }
      string = string + data[size] + "}";
    } else string = "{}";
    return string;
  }

  /**
   * This methods returns the number of elements in heap.
   *
   * @return the size of the heap.
   */
  public int size() {
    return size++;
  }

  /**
   * This method is the main logic for changing the patients priority.
   *
   * @param name name of the searched patient
   * @param newPriority new priority
   */
  public void modidyPatient(String name, int newPriority) {
    int currentIndex = 1;
    boolean found = false;

    while (found || currentIndex <= size) {

      if (data[currentIndex].name.equalsIgnoreCase(name)) {
        int oldPriority = data[currentIndex].priority;
        data[currentIndex].priority = newPriority;
        position(currentIndex, oldPriority);
        found = true;
        return;
      } else currentIndex++;
    }
  }

  /**
   * this one positions the patient in the right order based on their new priority.
   *
   * @param currentIndex current index of the patient
   * @param oldPriority the old priority of the patient.
   */
  private void position(int currentIndex, int oldPriority) {
    int newPriority = data[currentIndex].priority;

    if (newPriority == oldPriority) return;

    if (newPriority < oldPriority) {
      bubbleUp(currentIndex, newPriority);
    } else bubbleDown(currentIndex);
  }

  /**
   * This method bubbles up the patient if their priority is lower.
   *
   * @param currentIndex current index of the patient
   * @param newPriority the new priority of the patient.
   */
  private void bubbleUp(int currentIndex, int newPriority) {
    while (currentIndex > 1) {
      int parentIndex = (int) Math.floor(currentIndex / 2);

      if (newPriority < this.data[parentIndex].priority) {
        Patient temp = this.data[parentIndex];
        data[parentIndex] = data[currentIndex];
        data[currentIndex] = temp;
      } else if (newPriority == this.data[parentIndex].priority) {

        if (data[newPriority].name.compareToIgnoreCase(this.data[parentIndex].name) < 0) {
          Patient temp = this.data[parentIndex];
          data[parentIndex] = data[currentIndex];
          data[currentIndex] = temp;
        } else break;
      } else break;

      currentIndex = parentIndex;
    }
  }

  /**
   * This method bubbles up the patient if their priority is larger.
   *
   * @param currentIndex current index of the patient
   */
  private void bubbleDown(int currentIndex) {
    while (currentIndex <= size) {
      int leftChildIndex = 2 * currentIndex;
      int rightChildIndex = 2 * currentIndex + 1;

      if (leftChildIndex > size) break;

      int indexMin = leftChildIndex;

      // if there is a right child.
      if (rightChildIndex <= size) {
        // choose smaller of the two.
        if (data[rightChildIndex].priority < data[leftChildIndex].priority) {
          indexMin = rightChildIndex;
        } else if (data[rightChildIndex].priority == data[leftChildIndex].priority) {
          // choose smaller alphabetically if their priorities are the same.
          if (data[rightChildIndex].name.compareToIgnoreCase(data[leftChildIndex].name) < 0) {
            indexMin = rightChildIndex;
          }
        }
      }

      if (data[currentIndex].priority > data[indexMin].priority) {
        Patient temp = data[indexMin];
        data[indexMin] = data[currentIndex];
        data[currentIndex] = temp;
        currentIndex = indexMin;
      }

      // What to do if the child's and parent's priority are the same.
      else if (data[currentIndex].priority == data[indexMin].priority) {

        // if the parent's is larger alpha., swap them.
        if (data[currentIndex].name.compareToIgnoreCase(this.data[indexMin].name) > 0) {
          Patient temp = data[indexMin];
          data[indexMin] = data[currentIndex];
          data[currentIndex] = temp;
          currentIndex = indexMin;
        } else break;
      } else break;
    }
  }
}
