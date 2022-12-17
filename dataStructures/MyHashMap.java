import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * MyHashMap represents a data structure that maps keys to values.
 * It implements HashTable.
 * @author Umid Muzrapov
 * @param <K> : Generic that will be replaced by a concrete type for key.
 * @param <V> : Generic that will be replaced by a concrete type for value.
 */
public class MyHashMap<K, V> 
{
	//Here we actually store our data.
	 private HashTable<K, V> hashTable;

	/**
	 * This method is an empty constructor.
	 */
	public MyHashMap()
	{
		hashTable=new HashTable<K, V>();
	}
	
	
	/**
	 * The method clears the hashTable.
	 */
	public void clear()
	{
		hashTable.clear();
	}
	
	/**
	 * The method checks if the hashMap contains the key.
	 * @param key of type K
	 * @return true if the map contains the key.
	 */
	public boolean containsKey(K key)
	{
		return this.hashTable.containsKey(key);
	}
	
	/**
	 * The method checks if the map contains the value.
	 * @param value of type V
	 * @return true of the map contains value
	 */
	public boolean containsValue(V value)
	{
		return this.hashTable.containsValue(value);
	}
	
	/**
	 * The method gets the value associated with the key.
	 * @param key of type K
	 * @return value of type V
	 */
	public V get (K key)
	{
		return this.hashTable.get(key);
	}
	
	/**
	 * The method returns the size of the map.
	 * @return int
	 */
	public boolean isEmpty()
	{
		return this.hashTable.isEmpty();
	}
	
	/**
	 * The method gets the set containing the map's keys.
	 * @return HashSet<K>
	 */
	public Set<K> keySet()
	{
		return this.hashTable.keySet();
	}
	
	/**
	 * The method prints out the map -- each slot, number of conflicts
	 * in each slot, and finally the total# of conflicts.
	 */
	public void printTable()
	{
		this.hashTable.printTable();
	}
	
	
	/**
	 * The method associated the given key with a value in the map.
	 * If the key exists, its value is replaced with a new value.
	 * @param key of type K
	 * @param value of type V
	 * @return value of type V
	 */
	public V put(K key, V value)
	{
		if (key==null) return null;
		
		return this.hashTable.put(key, value);
	}
	
	/**
	 * The method returns the size of the map.
	 * @return int
	 */
	public int size()
	{
		return this.hashTable.size();
	}
	
	/**
	 * The method removes the given key and returns its associated value.
	 * If the key is not there, null is returned.
	 * @param K key
	 * @return value of type V
	 */
	public V remove(K key)
	{
		if (key==null) return null;
		
		return this.hashTable.remove(key);
	}			
	
	
	/**
	 * This Class represent the HashTable.
	 * It implements LinkedList and ArrayList.
	 * It can be used by HashSet and HashMap.
	 */
	private class HashTable<Key, Value>
	{
		private int CAPACITY=8;
		private int size=0;
		public ArrayList<LinkedList<Entry<Key, Value>>> hashTable;
		Set<Key> set=new HashSet<>();
		
		/**
		 * This is an empty constructor for the hashTable.
		 */
		public HashTable()
		{
			createHashTable();
		}
		
		/**
		 * This method renders the ArrayList of size 8.
		 * Necessary to use the get method of the ArrayList.
		 */
		private void createHashTable()
		{
			 hashTable=new ArrayList<LinkedList<Entry<Key, Value>>>(CAPACITY);
			 
			 for (int i=0; i<8; i++)
			 {
				 // adding null so far: will be useful for other methods.
				 hashTable.add(null);
			 }
		}
		
		/**
		 * The method clears the hashTable.
		 */
		public void clear()
		{
			this.createHashTable();
			size=0;
		}
		
		/**
		 * The method checks if the hashTable contains the key.
		 * @param key of type K
		 * @return true if the map contains the key.
		 */
		public boolean containsKey(Key key)
		{
			if (get(key)!=null) return true;
			
			else return false;
		}
		
		/**
		 * The method checks if the hashTable contains the value.
		 * @param value of type V
		 * @return true if the hasTable contains value
		 */
		public boolean containsValue(Value value)
		{
			//Since there are 8 slots, this loop is O(1) at this level.
			for (LinkedList<Entry<Key, Value>> slot: hashTable)
			{
				if (slot!=null) //if slot is empty, skip it.
				{
					for (Entry<Key,Value> entry: slot)
					{
						if(entry.getValue().equals(value))
							return true;
					}
				}
			}
			return false;
		}
		
		/**
		 * The method gets the value associated with the key.
		 * @param K key
		 * @return value of type V
		 */
		public Value get (Key key)
		{
			int index=hash(key);
			
			//O(1) just trying to check on my own.
			if (hashTable.get(index)!=null)
			{
				for (Entry<Key, Value> entry: hashTable.get(index))
				{
					if (entry.getKey().equals(key))
						return entry.getValue();
				}
			}
			
			return null;
		}
		
		/**
		 * The method returns the number of entries in the table.
		 * @return true if the map is empty.
		 */
		public boolean isEmpty()
		{
			return size==0;
		}
		
		/**
		 * The method gets the set of the table's keys.
		 * @return HashSet<K>
		 */
		public Set<Key> keySet()
		{	
			return set;
		}
		
		/**
		 * The method prints out the hashTable -- each slot, number of conflicts
		 * in each slot, and finally the total# of conflicts.
		 */		
		public void printTable()
		{
			int totalConflicts=0;
			
			for (int index=0; index<8; index++)
			{
				System.out.print("Index "+index+": ");
				LinkedList<Entry<Key,Value>> slot= hashTable.get(index); //O(1)
				
				if (slot==null) //If empty
				{
					System.out.println("(0 conflicts), []");
				}
				
				else
				{
					// get the number of conflicts and key list.
					int numberConflicts=slot.size()-1;
					totalConflicts=totalConflicts+numberConflicts;
					String string="[";
					
					for (var entry: slot)
					{
						string=string+entry.getKey()+", ";
					}

					System.out.println("("+numberConflicts+" conflicts), "+string+"]");
				}
			}	
			System.out.println("Total # of conflicts: "+totalConflicts);
		}
		
		/**
		 * The method associated the given key with a value in the table.
		 * If the key exists, its value is replaced with a new value.
		 * @param key K
		 * @param value V
		 * @return value V
		 */
		public Value put(Key key, Value value)
		{
			int index =hash(key);
			
			//If the key exists, return the previous value.
			if (get(key)!=null)
			{
				LinkedList<Entry<Key,Value>> slot= hashTable.get(index);
				
				for (Entry<Key, Value> entry: slot)
				{
					if(entry.getKey().equals(key))
					{
						Value previousValue=entry.getValue();
						entry.value=value;
						return previousValue;
					}
				}
			}
			
			// if the slot is empty, add LinkList slot.
			// Having null in empty slots makes all code efficient.
			if (hashTable.get(index)==null)
			{
				hashTable.set(index, new LinkedList<Entry<Key,Value>>()) ;
			}
			
			// Add the new entry.
			hashTable.get(index).addFirst(new Entry<Key, Value>(key, value));
			size++;
			set.add(key);
			
			return null;
		}
		
		/**
		 * The method returns the size of the hashtable.
		 * @return int
		 */
		public int size()
		{
			return size;
		}
		
		/**
		 * The method removes the given key and returns if associated value.
		 * If the key is not there, null is returned.
		 * @param key of type K
		 * @return value of type V
		 */
		public Value remove(Key key)
		{
			int index=hash(key);
			
			//Check if there is something in the slot.
			if (hashTable.get(index)!=null)
			{
				var slot= hashTable.get(index);
				
				for (Entry<Key,Value> entry: slot)
				{
					if (entry.getKey().equals(key))
					{
						slot.remove(entry);
						set.remove(key);
						size--;
						return entry.getValue();
					}
				}
			}
			
			return null;
		}
		
		/**
		 * This is a hash function.
		 * @param key of type K
		 * @return int
		 */
		private int hash(Key key)
		{
			int hashCode=key.hashCode();
			int index=hashCode%CAPACITY;
			return Math.abs(index);
		}
	}
	
	/**
	 * This class represents a node in the linked list.
	 * Each entry store K and V data types.
	 * @param <K>: Generic that will be replaced by a concrete data type.
	 * @param <V>: Generic that will be replaced by a concrete data type.
	 */
	private class Entry<K, V>
	{
		K key;
		V value;
		
		/**
		 * This is a constructor.
		 * @param key K
		 * @param value of type V
		 */
		public Entry(K key, V value)
		{
			this.key=key;
			this.value=value;
		}
		
		/**
		 * This method gets the key of the entry.
		 * @return K key
		 */
		public K getKey()
		{
			return key;
		}
		
		/**
		 * This method gets the value of the entry.
		 * @return value of type V
		 */
		public V getValue()
		{
			return value;
		}
		
		/**
		 * This method return the key as string.
		 * @return String
		 */
		public String toString()
		{
			return key.toString();
		}
	}
}
