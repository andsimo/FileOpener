import java.util.ArrayList;


public class Location {

	private double latitude, longitude;
	private int sensors;
	private String city;
	private String country;
	private ArrayList<String> files;
	private CurrentWeather currentWeather;
	

	
	
	
	public Location(){
		currentWeather = new CurrentWeather();
	}
	
	public Location(double latitude, double longitude){
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	
	
	public void addSensor(){
		sensors++;
	}
	

	/*
	    public void addSensors(int i){
	        sensors = sensors + i;
	    }
	*/

		public CurrentWeather getWeather(){
			return currentWeather;
		}
		
		public void setCurrentWeather(CurrentWeather weather){
			this.currentWeather = weather;
		}

	    public double getLat(){
	        return latitude;
	    }

	    public double getLong(){
	        return longitude;
	    }

	    public int getNumSensors(){
	        return sensors;
	    }
	    
	    


	
	
}
