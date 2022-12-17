/**
 *ListQueue represents a queue, where we remove the element 
 *from the front and add at the bottom --LIFO.
 *It uses LinkedList for implementation.
 */
public class ListQueue implements QueueInterface
{
	private LinkedList data=new LinkedList();
	
	/**
	 * O(1)
	 * ListQueue() is an empty constructor.
	 */
	public ListQueue()
	{
		
	}
	
	/**
	 * O(n)
	 * (ListQueue listQuequeToCopy) is a copy constructor.
	 * @param listQuequeToCopy ListQueue
	 */
	public ListQueue (ListQueue listQuequeToCopy)
	{
		data=new LinkedList(listQuequeToCopy.getData());
	}
	
	/**
	 * o(1)
	 * getData gets the data field of the queue.
	 * @return data LinkedList
	 */
	public LinkedList getData()
	{
		return data;
	}
	
	@Override
	/**
	 * O(n)
	 * enqueue adds the element to the back of the queue.
	 */
	public void enqueue(int value)
	{
		data.addBack(value);
	}

	@Override
	/**
	 * O(1)
	 * dequeue removes and returns the front element.
	 * @retun int
	 */
	public int dequeue()
	{	
		return data.removeFront();
	}

	@Override
	/**
	 * O(1)
	 * peek gets the first front element.
	 * @return int
	 */
	public int peek()
	{	
		if (data.size()==0) return -1;
		return data.getFirstElement().value;
	}

	@Override
	/**
	 * O(1)
	 * isEmpty is true if the queue is empty.
	 * @retun boolean
	 */
	public boolean isEmpty()
	{
		if (data.size()==0)
		{
			return true;
		}
		
		else return false;
	}

	@Override
	/**
	 * O(1)
	 * size returns the number of elements of the queue.
	 * @return int
	 */
	public int size()
	{
		return data.size();
	}

	@Override
	/**
	 * O(1)
	 * clear removes all the elements of queue.
	 */
	public void clear()
	{
		data.clear();
		
	}
	
	/**
	 * O(n)
	 * toString returns the string that contains 
	 * elements from the bottom to top.
	 */
	public String toString()
	{
		return data.toString();
	}
	
	/**
	 * O(n)
	 * equals checks if the passed object and queue 
	 * are equals. The two objects are equals if they have the same
	 * type and each element matched.
	 * @param object Object
	 * @return boolean
	 */
	public boolean equals(Object object)
	{
		if(!(object instanceof ListQueue)) return false;
		
		if(this.data.equals(((ListQueue) object).getData()))
		{
			return true;
		}
		
		else return false;
	}

}
