/**
 * @author: Umid Muzrapov
 * Garden class represent a garden,
 * where plants can be removed and planted.
 * The garden can also show the plants it has.
 */
import java.util.ArrayList;
import java.util.HashMap;

public class Garden {
	
	// we need these fields.
	public Plant[][] gardenArray;
	int numInnerArrays;
	int numElemenetInInnerArray;
	
	/**
	 * The constructor initializes the garden 
	 * of the given rows and columns.
	 * @param numInnerArrays int 
	 * @param numElementInInnerArray int 
	 */
	public Garden(int numInnerArrays, int numElementInInnerArray)
	{
		this.numInnerArrays=numInnerArrays;
		this.numElemenetInInnerArray=numElementInInnerArray;
		this.gardenArray=new Plant[numInnerArrays][numElementInInnerArray];
	}
	
	/////Remove Methods/////
	
	/**
	 * remove(Plant plantType) removes all plants that belong to 
	 * the given class type.
	 * @param plantType Plant
	 */
	public void remove(Plant plantType)
	{
		
		for (int numPlantRow=0; numPlantRow<numInnerArrays; numPlantRow++)
		{
			for (int indexElementInInnerArray=0;
					indexElementInInnerArray<this.numElemenetInInnerArray; indexElementInInnerArray++)
			{
				if (this.gardenArray[numPlantRow][indexElementInInnerArray]!=null &&
						this.gardenArray[numPlantRow][indexElementInInnerArray].
						getClass().equals(plantType.getClass()))
				{
					this.gardenArray[numPlantRow][indexElementInInnerArray]=null;
				}
			}
		}
	}
	
	/**
	 * remove (String specificPlantName) removes all plants whose
	 * plant name (banana, sunflower, etc.,) matches specificPlantName.
	 * @param specificPlantName String
	 */
	public void remove (String specificPlantName)
	{
		for (int numPlantRow=0; numPlantRow<numInnerArrays; numPlantRow++)
		{
			for (int indexElementInInnerArray=0;
					indexElementInInnerArray<this.numElemenetInInnerArray; indexElementInInnerArray++)
			{	
				Plant plant=this.gardenArray[numPlantRow][indexElementInInnerArray];
				if (plant!=null && plant.plantName.equals(specificPlantName.toLowerCase()))
				{
					this.gardenArray[numPlantRow][indexElementInInnerArray]=null;
				}
			}
		}
	}
	
	/**
	 *  remove(Plant plantClass, int numPlantRow, int indexElementInInnerArray)
	 *  tries to remove the plant of the specified class in the given coordinates.
	 * @param plantClass Plant
	 * @param numPlantRow int
	 * @param indexElementInInnerArray int
	 */
	public void remove(Plant plantClass, int numPlantRow, int indexElementInInnerArray)
	{	
		
		if (!indexOKAndNotNull(numPlantRow, indexElementInInnerArray)||!this.gardenArray[numPlantRow]
				[indexElementInInnerArray].getClass().equals(plantClass.getClass()))
		{
			System.out.println("Can't "+chooseVerb(plantClass,
					numPlantRow, indexElementInInnerArray).toLowerCase()+" there.\n");
		}

		else
		{
			this.gardenArray[numPlantRow][indexElementInInnerArray]=null;
		}
		
	}
	
	/**
	 * showPlants prints out garden to the console.
	 */
	public void showPlants()
	{
		for (int numPlantRow=0; numPlantRow<numInnerArrays; numPlantRow++)
		{
			for (int index=0; index<5; index++)
			{
				for (int indexElementInInnerArray=0; indexElementInInnerArray<
						this.numElemenetInInnerArray; indexElementInInnerArray++)
				{	
					
					if (this.gardenArray[numPlantRow][indexElementInInnerArray]!=null)
					{
						System.out.print(this.gardenArray[numPlantRow]
								[indexElementInInnerArray].plantArray[index]);
					}
					
					else
					{	
						System.out.print(".....");
						
					}
				}
			System.out.println("");
			}		
		}
		System.out.println(" ");
	}
	
	/////Grow Methods here/////
	
	/**
	 * growMyPlantType grows the plants belonging to plantType class
	 * the given times.
	 * @param timesToGrow int
	 * @param plantType String 
	 */
	public void growMyPlantType(int timesToGrow, String plantType)
	{
		HashMap<String, Plant> stringToClassMap = getStringToClassMap();
		
		for (int numPlantRow=0; numPlantRow<numInnerArrays; numPlantRow++)
		{
			for (int indexElementInInnerArray=0; indexElementInInnerArray
					<this.numElemenetInInnerArray; indexElementInInnerArray++)
			{	
				Plant plant=this.gardenArray[numPlantRow][indexElementInInnerArray];
				if (plant!=null && plant.getClass().equals
						(stringToClassMap.get(plantType).getClass()))
				{
					plant.grow(timesToGrow);
				}
			}
		}
	}
	
	/**
	 * growMyGarden grows all plants the given number of times.
	 * @param timesToGrow int
	 */
	public void growMyGarden(int timesToGrow)
	{
		for (Plant[] innerPlantArray:this.gardenArray)
		{
			for (Plant plant:innerPlantArray)
			{
				if (plant!=null)
				{
					plant.grow(timesToGrow);
				}
			}
		}
	}
	
	/**
	 * growPlantInPlot grow the plant in specified coordinates if 
	 * the plant there is planted.
	 * @param timesToGrow int
	 * @param numPlantRow int
	 * @param indexElementInInnerArray int
	 */
	public void growPlantInPlot(int timesToGrow,
			int numPlantRow, int indexElementInInnerArray)
	{	
		if (!indexOKAndNotNull(numPlantRow, indexElementInInnerArray))
		{
			System.out.println("Can't grow there.\n");
		}
		else
		{
			this.gardenArray[numPlantRow][indexElementInInnerArray].grow(timesToGrow);
		}
	}
	
	/**
	 * plantInGarden plants a plant in the garden.
	 * @param numPlantRow int
	 * @param indexElementInInnerArray int
	 * @param plantName String
	 */
	public void plantInGarden(int numPlantRow, int indexElementInInnerArray, String plantName)
	{
		ArrayList<ArrayList<String>> listsOfPlants=getListsOfPlants();
		
		plantName=plantName.toLowerCase();
		
		if (numPlantRow<5 && indexElementInInnerArray<5)
		{
			if (listsOfPlants.get(0).contains(plantName))
			{
				this.gardenArray[numPlantRow][indexElementInInnerArray]=new Flower(plantName);
			}
			
			else if (listsOfPlants.get(1).contains(plantName))
			{
				this.gardenArray[numPlantRow][indexElementInInnerArray]=new Vegetable(plantName);
			}
			
			else if (listsOfPlants.get(2).contains(plantName))
			{
				this.gardenArray[numPlantRow][indexElementInInnerArray]=new Tree(plantName);
			}
			else System.out.println("I cannot grow "+plantName+".\n");
		}
	}
	
	/////Helper Functions/////
	
	/**
	 * getListOfPlants gives a list of possible plants.
	 * @return listsOfPlants ArrayList<ArrayList<String>>
	 */
	public ArrayList<ArrayList<String>> getListsOfPlants()
	{
		ArrayList<ArrayList<String>> listsOfPlants=new ArrayList<ArrayList<String>>();
		
		//all vegetables
		ArrayList<String> vegetableList=new ArrayList<String>(){{
			add("garlic"); add("zucchini"); add("tomato"); add("yam"); add("lettuce");
		}};
		
		//all trees
		ArrayList<String> treeList=new ArrayList<String>(){{
			add("oak"); add("willow"); add("banana"); add("coconut"); add("pine");
		}};
		
		//all flowers
		ArrayList<String> flowerList=new ArrayList<String>(){{
			add("iris"); add("lily"); add("rose"); add("daisy"); add("tulip"); add("sunflower");
		}};
		
		listsOfPlants.add(flowerList);
		listsOfPlants.add(vegetableList);
		listsOfPlants.add(treeList);
		
		return listsOfPlants;
	}
	
	/**
	 * chooseVerb chooses the right verb based on the plant class
	 * for the remove method.
	 * @param plantClass Plant
	 * @param numPlantRow int
	 * @param indexElementInInnerArray int
	 * @return String 
	 * the remove verb.
	 */
	private String chooseVerb(Plant plantClass,
			int numPlantRow, int indexElementInInnerArray )
	{
		if (plantClass.getClass().equals(new Flower("test").getClass()))
		{
			return "PICK";
			
		}
		else if (plantClass.getClass().equals(new Vegetable("test").getClass()))
		{
			return "HARVEST";
		}
		
		else return "CUT";
	}
	
	/**
	 * indesOKAdnNotNull makes sure that the indices are good,
	 * and the plant exists there.
	 * @param numPlantRow
	 * @param indexElementInInnerArray
	 * @return boolean
	 * true if indices are good and there is a plant.
	 */
	public boolean indexOKAndNotNull(int numPlantRow, int indexElementInInnerArray)
	{
		if (numPlantRow>=this.numInnerArrays || 
				indexElementInInnerArray>=this.numElemenetInInnerArray
				|| this.gardenArray[numPlantRow][indexElementInInnerArray]==null)
			return false;
		else return true;
	}
	
	/**
	 * getStringToClassMap matches the removal verb to
	 * the the right class.
	 * @return stringToClassMap hashMap<String, Plant>
	 */
	private HashMap<String, Plant> getStringToClassMap()
	{
		HashMap<String, Plant>stringToClassMap=new HashMap<String, Plant>();
		stringToClassMap.put("tree", new Tree("TestTree"));
		stringToClassMap.put("flower", new Flower("FlowerTree"));
		stringToClassMap.put("vegetable", new Vegetable("VegetableTree"));
		return stringToClassMap;
	}
}
