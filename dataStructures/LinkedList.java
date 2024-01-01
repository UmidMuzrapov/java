/** LinkedList represents a collection of nodes sequentially connected to each other. */
public class LinkedList {
  private Node head;
  private Node lastElement;
  private int size = 0;

  /** LinkedList() is an empty constructor. */
  public LinkedList() {}

  /**
   * LinkedList(LinkedList listToCopy) is a copy constructor.
   *
   * @param listToCopy LinkedList
   */
  public LinkedList(LinkedList listToCopy) {
    Node current = listToCopy.head;

    while (current != null) {
      this.addBack(current.value);
      current = current.next;
    }
  }

  /**
   * getFirstElement gets the first node.
   *
   * @return head Node
   */
  public Node getFirstElement() {
    return head;
  }

  /**
   * getLastElement gets the last node.
   *
   * @return lastElement Node
   */
  public Node getLastElement() {
    return lastElement;
  }

  /**
   * addFront adds a node at the beginning of the linkedlist.
   *
   * @param value int
   */
  public void addFront(int value) {
    Node newNode = new Node(value);

    if (size == 0) {
      head = newNode;
      lastElement = newNode;
    } else {
      newNode.next = head;
      head = newNode;
    }

    size++;
  }

  /**
   * addBack adds a value at the end of the node.
   *
   * @param value int
   */
  public void addBack(int value) {
    Node newNode = new Node(value);

    if (size == 0) {
      head = newNode;
      lastElement = newNode;
    } else {
      lastElement.next = newNode;
      lastElement = newNode;
    }

    size++;
  }

  /**
   * removeFront removes the node at the beginning and returns its value. -1 is returned if it is
   * empty.
   *
   * @return result int
   */
  public int removeFront() {
    if (size == 0) {
      return -1;
    }

    int result = head.value;
    head = head.next;
    size--;
    return result;
  }

  /**
   * removeBack removes the node at the beginning and returns its value. -1 is returned if the
   * linked list is empty.
   *
   * @return result int
   */
  public int removeBack() {
    if (size == 0) {
      return -1;
    }

    if (size == 1) {
      return removeFront();
    } else {
      Node current = head;
      for (int i = 0; i < size - 2; i++) {
        current = current.next;
      }

      int result = lastElement.value;
      lastElement = current;
      current.next = null;
      size--;
      return result;
    }
  }

  /**
   * toString returns the string containing elements from head to lastElement.
   *
   * @return string String
   */
  public String toString() {
    if (size == 0) {
      return "{}";
    } else {
      String string = "{";
      Node current = head;

      while (current != null) {
        string = string + current.value + ",";
        current = current.next;
      }

      string = string.substring(0, string.length() - 1);
      string = string + "}";

      return string;
    }
  }

  /**
   * toStringReverse returns the string containing elements from lastElement to head.
   *
   * @return string String
   */
  public String toStringReverse() {
    if (size == 0) {
      return "{}";
    }

    String[] temp = new String[size];
    String string = "{";
    Node current = head;
    int count = size - 1;

    while (current != null) {
      temp[count] = ((Integer) current.value).toString();
      current = current.next;
      count--;
    }

    string = "{" + String.join(",", temp) + "}";
    return string;
  }

  /**
   * equals checks if the passed object is equal to the linked list. The two are equal if they are
   * of the same type and each value matches.
   *
   * @param object Object
   * @return boolean
   */
  public boolean equals(LinkedList anotherLinkedList) {
    if (this.size() == anotherLinkedList.size()) {
      Node currentThis = this.head;
      Node currentAnother = anotherLinkedList.head;
      while (currentThis != null) {
        if (currentThis.value != currentAnother.value) {
          return false;
        }
        currentThis = currentThis.next;
        currentAnother = currentAnother.next;
      }

      return true;
    } else return false;
  }

  /**
   * size returns the number of elements in the linked list.
   *
   * @return int
   */
  public int size() {
    return this.size;
  }

  /** clear dumps the elements of the linked list. */
  public void clear() {
    size = 0;
    head = null;
    lastElement = null;
  }

  /**
   * Node represents an element in LInkedList. Node contains the value and reference to the next
   * node.
   */
  public class Node {
    Node next;
    int value = 0;

    public Node(int value) {
      this.value = value;
    }
  }
}
