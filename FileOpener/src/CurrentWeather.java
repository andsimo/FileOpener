



// TODO: Auto-generated Javadoc
/**
 * The Class CurrentWeather.
 * Holds cloudiness sunrise, sunset and time of weather update
 */
public class CurrentWeather {
	

	/** The cloudiness. */
	private int cloudiness;
	
	/** The sunset. */
	private long sunrise, sunset;
	
	/** The current time date. */
	private long currentTimeDate = 0;
	
	/**
	 * Instantiates a new current weather.
	 */
	public CurrentWeather(){
		this.cloudiness = 1000;
	}
	
	/**
	 * Gets the sunrise.
	 *
	 * @return the sunrise
	 */
	public long getSunrise(){
		return sunrise;
	}
	
	/**
	 * Gets the sunset.
	 *
	 * @return the sunset
	 */
	public long getSunset(){
		return sunset;
	}
	
	/**
	 * Gets the cloudiness.
	 *
	 * @return the cloudiness
	 */
	public int getCloudiness(){
		return cloudiness;
	}
	
	/**
	 * Sets the sunrise.
	 *
	 * @param sunrise the new sunrise time
	 */
	public void setSunrise(long sunrise){
		this.sunrise = sunrise;
	}
	
	/**
	 * Sets the sunset.
	 *
	 * @param sunset the new sunset time
	 */
	public void setSunset(long sunset){
		this.sunset = sunset;
	}
	
	/**
	 * Sets the cloudiness.
	 *
	 * @param cloudiness the new cloudiness
	 */
	public void setCloudiness(int cloudiness){
		this.cloudiness = cloudiness;
	}

	/**
	 * Gets the current time date.
	 *
	 * @return the current time date
	 */
	public long getCurrentTimeDate() {
		return (long) currentTimeDate;
	}

	/**
	 * Sets the current time of weather update.
	 *
	 * @param time the new current time date
	 */
	public void setCurrentTimeDate(long time) {
		this.currentTimeDate = time;
	}
}
