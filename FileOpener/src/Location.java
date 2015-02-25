import java.util.Date;
import java.util.HashMap;


public class Location {

	private double latitude, longitude;
	private int sensors;
	//private ArrayList<String> files;
	private HashMap<String, Date> files;
	private CurrentWeather currentWeather;
	

	
	
	
	public Location(){
		currentWeather = new CurrentWeather();
		files = new HashMap<String, Date>();
	}
	
	public Location(double latitude, double longitude, String file, Date productionDate){
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		files.put(file, productionDate);
		
	}
	
	
	
	
		public void addSensor(){
			this.sensors++;
		}
	

		public CurrentWeather getWeather(){
			return currentWeather;
		}
		
		public void setCurrentWeather(CurrentWeather weather){
			this.currentWeather = weather;
		}

		public void setLat(double latitude){
			this.latitude = latitude;
		}
		
		public void setLong(double longitude){
			this.longitude = longitude;
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
	    
	    public Date getProductionDate(){
	    	if(files != null)
	    		return files.entrySet().iterator().next().getValue(); //Hämtar första date den hittar
	    	else
	    		return null;
	    }
	    
	    public Date getProductionDate(String file){
	    	if(files != null)
	    		return files.get(file);
	    	else
	    		return null;
	    }
	    
	    public void addReceiver(String file, Date productionDate){
	    	files.put(file, productionDate);
	    }
	    
	    
	    public boolean containsFile(String file){
	    	if(files == null)
	    		return false;
	    	else{
	    	if(files.containsKey(file))
	    		return true;
	    	else
	    		return false;
	    	}
	    		
	    }
	    
	    public HashMap<String, Date> getFiles(){
	    	return files;
	    }
	    
	    


	
	
}
