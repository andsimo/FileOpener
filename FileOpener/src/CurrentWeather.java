import java.sql.Date;




public class CurrentWeather {
	

	private int cloudiness;
	private long sunrise, sunset;
	private long currentTimeDate = 0;
	
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

	public long getCurrentTimeDate() {
		return (long) currentTimeDate;
	}

	public void setCurrentTimeDate(long time) {
		this.currentTimeDate = time;
	}
}
