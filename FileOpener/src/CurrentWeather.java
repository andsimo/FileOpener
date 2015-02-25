
public class CurrentWeather {
	

	private int cloudiness;
	private long sunrise, sunset;

	
	public CurrentWeather(){
		this.cloudiness = 1000;
	}
	
	public long getSunrise(){
		return sunrise;
	}
	public long getSunset(){
		return sunset;
	}
	public int getCloudiness(){
		return cloudiness;
	}

	
	public void setSunrise(long sunrise){
		this.sunrise = sunrise;
	}
	public void setSunset(long sunset){
		this.sunset = sunset;
	}
	public void setCloudiness(int cloudiness){
		this.cloudiness = cloudiness;
	}
	
	
}
