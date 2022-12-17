/**
 * @author: Umid Muzrapov
 * Plant class is an abstract class 
 * that serves as a blueprint for Flower, Tree and
 * Vegetable classes.
 */
public abstract class Plant
{
	char[][] plantArray;
	String plantName;
	
	/**
	 * The constructor builds the generic Plant, without
	 * specifying plant name;
	 */
	public Plant()
	{
		this.plantName="";
		this.plantArray= new char[][] {
		        {'.','.', '.','.','.'},
		        {'.','.', '.','.','.'},
		        {'.','.', '.','.','.'},
		        {'.','.', '.','.','.'},
		        {'.','.', '.','.','.'}};
	}
	
	/**
	 * grow should grow the plant specified number of
	 * times. It should be over-written by subclass.
	 * @param timesToGrow int
	 */
	public abstract void grow(int timesToGrow);	
}
