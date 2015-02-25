import java.util.Date;


public class SolarReceiver {

	private double longitude, latitude;
	private Date productionDate;


	public SolarReceiver(){}


	public SolarReceiver(double longitude, double latitude, Date productionDate){
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.productionDate = productionDate;
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


	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}


}

