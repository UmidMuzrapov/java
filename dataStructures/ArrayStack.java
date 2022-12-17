/**
 * ArrayStack represents stack structure, where
 * the elements are added and removed from the top.
 * The class uses DynamicArray for implementation.
 */
public class ArrayStack implements StackInterface
{
	private DynamicArray data=new DynamicArray();
	
	/**
	 * O(1)
	 * ArrayStack() is an empty constructor.
	 */
	public ArrayStack()
	{
		
	}
	
	/**
	 * O(n)
	 * ArrayStack(ArrayStack stackToCopy) is a copy
	 * constructor.
	 * @param stackToCopy ArrayStack
	 */
	public ArrayStack(ArrayStack stackToCopy)
	{
		data=new DynamicArray(stackToCopy.getData());
	}
	
	@Override
	/**
	 * O(1)*. *O(n) -> if the array needs resizing.
	 * push adds an element to the top of the stack.
	 * @param value int
	 */
	public void push(int value)
	{
		data.add(this.size(),value);	
	}

	@Override
	/**
	 * O(1)
	 * pop removes and returns the element at the top.
	 * @return int
	 */
	public int pop()
	{
		return data.remove(data.size()-1);
	}

	@Override
	/**
	 * O(1)
	 * peek gets the element at the top.
	 * @return int
	 */
	public int peek()
	{
		return data.get(data.size()-1);
	}

	@Override
	/**
	 * O(1)
	 * checks if the stack is empty.
	 * @return boolean
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
	 * size returns the number of elements in the stack.
	 * @return int
	 */
	public int size()
	{
		return data.size();
	}
	
	@Override
	/**
	 * O(1)
	 * clear dumps all elements.
	 */
	public void clear()
	{
		data.clear();
	}
	
	/**
	 * O(n)
	 * toString prints element from bottom to top.
	 * @return string String
	 */
	public String toString()
	{
		return data.toString();
	}
	
	/**
	 * O(1)
	 * getData gets the data field of the the stack.
	 * @return DynamicArray
	 */
	public DynamicArray getData()
	{
		return data;
	}
	
	/**
	 * O(n)
	 * equals checks if the passed object is equal to the stack.
	 * The two are equal if they are of the same
	 * type and each value matches.
	 * @param object Object
	 * @return boolean
	 */
	public boolean equals(Object object)
	{
		if (!(object instanceof ArrayStack)) return false;
		
		if(data.equals(((ArrayStack) object).getData()))
		{
			return true;
		}
		
		else return false;
	}
	
}
