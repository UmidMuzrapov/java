/**
 * @author: Umid Muzrapov
 * Flower class inherits from abstract Plant class.
 * It represent a flower.
 */
public class Flower extends Plant
{
	int growthTimes=0;
	
	/**
	 * The constructor build a flower with the specified name.
	 * @param flowerName String
	 */
	public Flower(String flowerName)
	{
		super.plantName=flowerName.toLowerCase();
		super.plantArray[2][2]=this.plantName.charAt(0);
	}
	
	/**
	 * grow makes the tree to bloom specified number of times.
	 * It does so by calling a recursive function.
	 * @param timesToGrow int
	 */
	public void grow(int timesToGrow)
	{	
		growthTimes=growthTimes+timesToGrow;
		recursivelyGrow(2, 2, growthTimes);
	}
	
	/**
	 * recursivleyGrow makes the flower blossom, filling 
	 * the bordering empty cells recursively.
	 * @param rowNumber int
	 * @param colNumber int
	 * @param timesToGrow int
	 */
	private void recursivelyGrow(int rowNumber, int colNumber, int timesToGrow)
	{
		if (timesToGrow!=0)
		{
			int upRow=rowNumber-1;
			int upCol=colNumber;
			
			int downRow=rowNumber+1;
			int downCol=colNumber;
			
			int leftRow=rowNumber;
			int leftCol=colNumber-1;
			
			int rightRow=rowNumber;
			int rightCol=colNumber+1;
			
			if (indexOK(upRow, upCol))
			{
				this.plantArray[upRow][upCol]=this.plantName.charAt(0);
				recursivelyGrow(upRow, upCol, timesToGrow-1);
			}
			
			if (indexOK(downRow, downCol))
			{
				this.plantArray[downRow][downCol]=this.plantName.charAt(0);
				recursivelyGrow(downRow, downCol, timesToGrow-1);
			}
			
			if (indexOK(leftRow, leftCol))
			{
				this.plantArray[leftRow][leftCol]=this.plantName.charAt(0);
				recursivelyGrow(leftRow, leftCol, timesToGrow-1);
			}
			
			if (indexOK(rightRow, rightCol))
			{
				this.plantArray[rightRow][rightCol]=this.plantName.charAt(0);
				recursivelyGrow(rightRow, rightCol, timesToGrow-1);
			}
		}
	}
	
	/**
	 * indexOK makes sure that given rowNumber and colNumber 
	 * are within range.
	 * @param rowNumber int
	 * @param colNumber int
	 * @return indexOK boolean
	 */
	private boolean indexOK(int rowNumber, int colNumber)
	{
		
		if (rowNumber<5 && rowNumber>=0 && colNumber<5 && colNumber>=0)
		{
			return true;
		}
		else return false;
	}
}
