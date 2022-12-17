/**
 * @author: Umid Muzrapov
 * Class CheapHouses allows the users to input file name and house price.
 * It reads data from a CSV file, and plots the the houses 
 * whose cost is below the price specified as small filled circles.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class CheapHouses {
	
	// for this assignment, David allowed to use fields.
	// They are necessary for this code to dynamically change GUI.
	static JPanel mainPanel;
	static JFrame mainWindow;
	static JPanel controlPanel;
	static JTextField fileTextField;
	static JTextField priceTextField;
	
	/**
	 * main is program's starting point and 
	 * calls another method to set up the GUI.
	 * @param args its value is not used here
	 */
	public static void main(String[] args) 
	{
			createAndShowGui();		
	}

/////////////////Calculation and Plot Methods here////////////////
	
	/**
	 * plotData plots the houses below the given price 
	 * as filled circles in PlotPanel.
	 * @param g Graphics object that is used to draw shapes.
	 * @param houseMap HashMap<String, ArrayList<Double>> maps
	 * house addresses, and their latitude, longitude, price.
	 * @throws NumberFormatException Exception thrown if 
	 * non-numeric value is given for the price.
	 */
	static void plotData(Graphics g, HashMap<String, ArrayList<Double>> houseMap)
			throws NumberFormatException
	{
		double[] maxAndMinLatAndLong=findMaxAndMinLatAndLong(houseMap);
		
		// for each house, plot it if it is below the given price.
		for (ArrayList<Double> priceAndCoordinates:houseMap.values())
		{
			if (Double.compare(priceAndCoordinates.get(0),
					Double.parseDouble(priceTextField.getText()))<0)
			{
				double latitude=priceAndCoordinates.get(1);
				double longitude=priceAndCoordinates.get(2);
				double[] xyCoordinates=
						convertLatitudeLongitude(latitude, longitude, maxAndMinLatAndLong);

				g.setColor(new Color(238, 50, 51));
				g.fillOval((int)xyCoordinates[0], (int)xyCoordinates[1], 5, 5);  	
			}
		}
	}
	
	/**
	 * buildHouseMap builds a HashMap that maps address and 
	 * latitude, longitude, price.
	 * @param inputFile Scanner objects used to read a line.
	 * @return  houseMap HashMap<String, ArrayList<Double>> maps
	 * house addresses, and their latitude, longitude, price.
	 * @throws FileNotFoundException Exception thrown if the file is not found.
	 * @throws NoSuchElementException Exception thrown if the file is empty
	 * @throws NumberFormatException Exception thrown if 
	 * non-numeric value is given for the price.
	 */
	static HashMap<String, ArrayList<Double>> buildHouseMap(Scanner inputFile)
		throws FileNotFoundException, NoSuchElementException, NumberFormatException
	{
		HashMap<String, ArrayList<Double>> houseMap=
				new HashMap<String, ArrayList<Double>>();
		String address;
		inputFile.nextLine(); //First line is head rows. Skip it.
		
		while(inputFile.hasNext())
		{
			ArrayList<Double> priceAndCoordinates = new ArrayList<Double>();
			String houseInfString=inputFile.nextLine();
			String[] houseInf=houseInfString.split(",");
			
			// Address is street, city and ZIP code.
			address=houseInf[0]+houseInf[1]+houseInf[2];
			priceAndCoordinates.add(Double.parseDouble(houseInf[9])); 
			priceAndCoordinates.add(Double.parseDouble(houseInf[10]));
			priceAndCoordinates.add(Double.parseDouble(houseInf[11]));
			houseMap.put(address, priceAndCoordinates);
		}

		return houseMap;
	}
	
	/**
	 * convertLatitudeLongitude converts given
	 * latitude and longitude to x and y values.
	 * Latitudes and longitudes can be any rational numbers.
	 * @param latitude double that represent latitude
	 * @param longitude double that represent longitude
	 * @param maxAndMinLatAndLong double array that contains
	 * the max and min latitudes and longitudes.
	 * @return xyCoordinates double array that contains x
	 * and y coordinates.
	 */
	static double[] convertLatitudeLongitude
		(double latitude, double longitude, double[] maxAndMinLatAndLong)
	{
		double minLatitude=maxAndMinLatAndLong[0];
		double maxLatitude=maxAndMinLatAndLong[1];
		double minLongitude=maxAndMinLatAndLong[2];
		double maxLongitude=maxAndMinLatAndLong[3];
		double latitudeRange=maxLatitude-minLatitude;
		double longitudeRange=maxLongitude-minLongitude;
		double x,y;
		
		if(longitude<0)
		{
			x=780+(780*(longitude-maxLongitude))/longitudeRange;
		}
		
		else x=(780*(longitude-minLongitude))/longitudeRange;
		
		if (latitude<0)
		{
			y=620+(620*(latitude-maxLatitude)/latitudeRange);
		}
		
		else y=(620*(latitude-minLatitude)/latitudeRange);
	
		double[] xyCoordinates= {x,y};
		return xyCoordinates;	
	}
	
	/**
	 * findMaxAndMinLatAndLong finds the max and min values of 
	 * latitudes and longitudes.
	 * @param houseMap HashMap<String, ArrayList<Double>> maps
	 * house addresses, and their latitude, longitude, price.
	 * @return maxAndMinLatAndLong double array that contains
	 * the max and min latitudes and longitudes.
	 */
	static double[] findMaxAndMinLatAndLong(HashMap<String, ArrayList<Double>> houseMap)
	{	
		double maxLatitude=-1000;
		double minLatitude=1000;
		double maxLongitude=-1000;
		double minLongitude=1000;
		double latitude, longitude;
		
		for (ArrayList<Double> priceAndCoordinates:houseMap.values())
		{
			latitude=priceAndCoordinates.get(1);
			longitude=priceAndCoordinates.get(2);
			
			if (Double.compare(maxLatitude, latitude)<0)
			{
				maxLatitude=latitude;
			}
			
			if (Double.compare(minLatitude, latitude)>0)
			{
				minLatitude=latitude;
			}
			
			if (Double.compare(maxLongitude, longitude)<0)
			{
				maxLongitude=longitude;
			}
			
			if (Double.compare(minLongitude, longitude)>0)
			{
				minLongitude=longitude;
			}	
		}

		double[]  maxAndMinLatAndLong=
			{minLatitude, maxLatitude, minLongitude, maxLongitude};
		return  maxAndMinLatAndLong;	
	}
	
/////////////GUI Methods and Classes here/////////////
	
	/**
	 * createAndShowGui sets up the GUI.
	 * Initially, plotPanel is not there.
	 */
	static void createAndShowGui()
	{
		mainWindow=new JFrame("Home Price Distribution");
		mainWindow.setSize(800, 800);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// mainPanel contains the control and plot panels.
		mainPanel=new JPanel(null);
		mainPanel.setBackground(new Color(206, 235, 251));
		
		//Stick GUI components together. plotPanel is not added yet.
		controlPanel=buildControlPanel();
		mainPanel.add(controlPanel);
		mainWindow.add(mainPanel);
		
		mainWindow.setVisible(true);	
	}
	
	/**
	 * buildControlPanle creates the control panel
	 * and adds the necessary elements to it.
	 * @return JPanel object that represents control panel.
	 */
	static JPanel buildControlPanel()
	{
		JPanel controlPanel=new JPanel();
		controlPanel.setLocation(0, 640);
		controlPanel.setSize(800, 160);
		controlPanel.setBackground(new Color(163, 214, 245));
		
		// Create some components.
		JLabel fileLabel=new JLabel("File: ");
		fileTextField=new JTextField(20);
		JLabel priceLabel=new JLabel("Price: ");
		priceTextField=new JTextField(20);
		JButton plotButton=new JButton("Plot");
		
		// Add them.
		plotButton.addActionListener(new PlotButtonListener());
		controlPanel.add(fileLabel);
		controlPanel.add(fileTextField);
		controlPanel.add(priceLabel);
		controlPanel.add(priceTextField);
		controlPanel.add(plotButton);
		
		return controlPanel;
	}
	
	/**
	 * Class PlotPanle inherits from JPanel.
	 * It represents a plotPanel that displays data from csv file.
	 */
	static class PlotPanel extends JPanel
	{	
		/**
		 * This is a constructor. It sets up the size and location
		 * of the panel.
		 */
		public PlotPanel()
		{
			setSize(800, 640);
			setLocation(0,0);
		}
		
		/**
		 * paintComponent is main logic of program.
		 * It reads the file, builds the map and plots the Data.
		 * @param g Graphics object that is used to draw shapes.
		 */
		public void paintComponent(Graphics g) 
		{	
			try
			{	
				File file=new File(fileTextField.getText());	
				Scanner inputFile=new Scanner(file);
				HashMap<String, ArrayList<Double>> houseMap= buildHouseMap(inputFile);
				
				if (houseMap.size()>0)
				{	
					plotData(g, houseMap);
				}
			}
			
			catch(FileNotFoundException | NoSuchElementException fileNotFoundOrEmpty)
			{
				g.drawString("The file you asked is not found or is empty.", 280, 320);	
			}
			
			catch (NumberFormatException isNotNumeric)
			{
				g.drawString("The string or nothing was given,"
						+ " where the number was expected.", 280, 320);
				g.drawString("Check your input and the values"
						+ " in file for price, lat and long.", 280, 350);
			}
		}  	
	}
	
	/**
	 * Class PlotButtionListener handles an event raised 
	 * by button-click.
	 */
	private static class PlotButtonListener implements ActionListener
	{
		/**
		 * actionPerformed creates the plotPanel and add it
		 * to the mainPanle when the button is clicked.
		 * @param e ActionEvent object that refer to the event.
		 */
		public void actionPerformed(ActionEvent e)
		{	
			//Create PlotPanel
			JPanel somePanel=new PlotPanel();
			mainPanel.add(somePanel);
			mainWindow.setContentPane(mainPanel);
			//Updates the mainPanel.
			mainPanel.validate();
		}
	}
}


