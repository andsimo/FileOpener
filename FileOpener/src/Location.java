
public class Location {

	private Coord coord;
	private int numSensors;
	private String city;
	private String country;
	
	
	
	public Location(){
		
	}
	
	public Location(String city, String country, int numSensors, Coord coord){
		this.coord = coord;
		this.city = city;
		this.country = country;
		this.numSensors = numSensors;
	}
	
	
	
	public void addSensor(){
		numSensors++;
	}
	
}
