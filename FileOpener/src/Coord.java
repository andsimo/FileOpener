/*
 * Vårt object för att lagra coordinater i. Lagrar longitud och latitude som doubles samt filnamnet ifall detta skulle behövas.
 * Enkel att bygga ut.
 */
public class Coord {

	private double longitude, latitude;

	
	public Coord(){}

	
	public Coord(double longitude, double latitude){
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		
	}
	
	
	
	/*
	 * GETTERS och SETTERS
	 */
	
	public void setLong(double longitude){
		this.longitude = longitude;
	}
	
	public void setLat(double latitude){
		this.latitude = latitude;
	}

	public double getLong(){
		return longitude;
	}
	
	public double getLat(){
		return latitude;
	}


}
