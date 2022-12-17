/**
 * ListStack represents stack, where the elements
 * are added and removed from the top.
 * It uses LinkedList for implementation.
 */
public class ListStack implements StackInterface
{	
	private LinkedList data=new LinkedList();
	
	/**
	 * O(1)
	 * ListStack() is an empty constructor.
	 */
	public ListStack()
	{
	
	}

	/**
	 * O(n)
	 * ListStack(ListStack listStackToCopy) is a copy constructor.
	 * @param listStackToCopy ListStack
	 */
	public ListStack(ListStack listStackToCopy)
	{
		data=new LinkedList(listStackToCopy.data);
	}
	
	@Override
	/**
	 * O(1)
	 * push add the element at the top of the stack.
	 * @param value int
	 */
	public void push(int value)
	{
		data.addFront(value);
		// TODO Auto-generated method stub	
	}
	
	/**
	 * O(1)
	 * getData gets the data field of the calling LsitStack.
	 * @return data LinkedList
	 */
	public LinkedList getData()
	{
		return data;
	}
	
	@Override
	/**
	 * O(1)
	 * pop removes the top element and returns it.
	 * @return int
	 */
	public int pop()
	{
		return data.removeFront();
	}

	@Override
	/**
	 * O(1)
	 * peek gets the top element.
	 * @return int
	 */
	public int peek()
	{
		return data.getFirstElement().value;
	}

	@Override
	/**
	 * O(1)
	 * isEmty checks if the stack is empty.
	 * @return boolean
	 */
	public boolean isEmpty()
	{
		if (data.size()==0)
		{
			return true;
		}
		
		else
		{
			return false;
		}
	}

	@Override
	/**
	 * O(1)
	 * size gets the size of the stack.
	 * @return int
	 */
	public int size()
	{
		return data.size();
	}

	@Override
	/**
	 * O(1)
	 * clear dumps all the elements.
	 */
	public void clear()
	{
		data.clear();
	}
	
	/**
	 * O(n)
	 * toString returns a string of elements from bottom to top.
	 * @return String
	 */
	public String toString()
	{
		return data.toStringReverse();
	}
	
	/**
	 * O(n)
	 * equals checks if the passed object is equal to the stack.
	 * The two are equal if they are of the same type and each 
	 * element matches.
	 * @param object Object
	 * @return  boolean 
	 */
	public boolean equals(Object object)
	{	
		if (!(object instanceof ListStack)) return false;
		
		if (data.equals(((ListStack) object).getData()))
		{
			return true;
		}
		
		else return false;
	}	
}
