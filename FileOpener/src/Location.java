
public class Location {

	private Coord coord;
	private int numSensors;
	private String city;
	private String country;
	
	
	
	public Location(){
		
	}
	
	public Location(Coord coord, String city, String country){
		this.coord = coord;
		this.city = city;
		this.country = country;
	}
	
}
