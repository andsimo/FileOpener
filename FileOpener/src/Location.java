import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class Location.
 *	
 */
public class Location {

	/** The longitude. */
	private double latitude, longitude;
	//private ArrayList<String> files;
	/** The fileNames with productiondate. */
	private HashMap<String, String> files;
	
	/** The current weather. */
	private CurrentWeather currentWeather;


	/**
	 * Instantiates a new location.
	 */
	public Location(){
		currentWeather = new CurrentWeather();
		files = new HashMap<String, String>();
	}

	/**
	 * Instantiates a new location.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @param file the file
	 * @param productionDate the production date
	 */
	public Location(double latitude, double longitude, String file, String productionDate){
		
		currentWeather = new CurrentWeather();
		files = new HashMap<String, String>();
		this.latitude = latitude;
		this.longitude = longitude;
		if(productionDate == null)
			productionDate = "N/A";
		if(file == null)
			file = "N/A";

		files.put(file.toString(), productionDate.toString());
	}




	/**
	 * Gets the weather.
	 *
	 * @return the weather
	 */
	public CurrentWeather getWeather(){
		return currentWeather;
	}

	/**
	 * Sets the current weather.
	 *
	 * @param weather the new current weather
	 */
	public void setCurrentWeather(CurrentWeather weather){
		this.currentWeather = weather;
	}

	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLat(double latitude){
		this.latitude = latitude;
	}

	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new long
	 */
	public void setLong(double longitude){
		this.longitude = longitude;
	}


	/**
	 * Gets the latitude.
	 *
	 * @return latitude
	 */
	public double getLat(){
		return latitude;
	}

	/**
	 * Gets the long.
	 *
	 * @return the long
	 */
	public double getLong(){
		return longitude;
	}

	/**
	 * Gets the number of sensors.
	 *
	 * @return the number sensors
	 */
	public int getNumSensors(){
		return files.size();
	}

	/**
	 * Gets the production date.
	 *
	 * @return the production date
	 */
	public String getProductionDate(){
		if(files != null)
			return files.entrySet().iterator().next().getValue(); //H�mtar f�rsta date den hittar
		else
			return null;
	}

	/**
	 * Gets the production date.
	 *
	 * @param file the file
	 * @return the production date
	 */
	public String getProductionDate(String file){
		if(files != null)
			return files.get(file);
		else
			return null;
	}

	/**
	 * Adds the receiver.
	 *
	 * @param file the file
	 * @param productionDate the production date
	 */
	public void addReceiver(String file, String productionDate){
		files.put(file, productionDate);
	}


	/**
	 * Contains file.
	 *
	 * @param file the file
	 * @return true, if successful
	 */
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
	
	/**
	 * Adds the fileName with its production-date.
	 *
	 * @param fileName the file name
	 * @param date the date
	 */
	public void addFile(String fileName, String date){
		files.put(fileName.toString(), date.toString());
	}

	/**
	 * Gets the files names.
	 *
	 * @return the files
	 */
	public HashMap<String, String> getFiles(){
		return files;
	}
	
	/**
	 * Sets the weather time.
	 *
	 * @param time the new weather time
	 */
	public void setWeatherTime(long time){
				currentWeather.setCurrentTimeDate(time);
	}
}
