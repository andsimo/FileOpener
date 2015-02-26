import java.util.Date;
import java.util.HashMap;


public class Location {

	private double latitude, longitude;
	//private ArrayList<String> files;
	private HashMap<String, String> files;
	private CurrentWeather currentWeather;





	public Location(){
		currentWeather = new CurrentWeather();
		files = new HashMap<String, String>();
	}

	public Location(double latitude, double longitude, String file, String productionDate){
		
		currentWeather = new CurrentWeather();
		files = new HashMap<String, String>();
		System.out.println(productionDate);
		this.latitude = latitude;
		this.longitude = longitude;
		files.put(file.toString(), productionDate.toString());
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
		return files.size();
	}

	public String getProductionDate(){
		if(files != null)
			return files.entrySet().iterator().next().getValue(); //H�mtar f�rsta date den hittar
		else
			return null;
	}

	public String getProductionDate(String file){
		if(files != null)
			return files.get(file);
		else
			return null;
	}

	public void addReceiver(String file, String productionDate){
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
	
	public void addFile(String fileName, String date){
		files.put(fileName.toString(), date.toString());
	}

	public HashMap<String, String> getFiles(){
		return files;
	}

	public void setWeatherTime(long time){
				currentWeather.setCurrentTimeDate(time);
	}






}
