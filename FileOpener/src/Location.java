import java.util.ArrayList;


public class Location {

	private double latitude, longitude;
	private int numSensors;
	private String city;
	private String country;
	private ArrayList<String> files;
	
	
	
	public Location(){
		
	}
	
	
	
	
	public void addSensor(){
		numSensors++;
	}
	
}
