import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class DBConnector {

	private String url = "jdbc:mysql://46.239.117.17:3306/";	//Just nu endast lokalt. min IP 46.239.117.17    localhost
	private String DbName = "GVS";						//Schemats namn satt av Simon
	private String driver ="com.mysql.jdbc.Driver";		//V�ljer vilken typ av db vi kopplar upp oss mot. Kr�ver buildpath.
	private String username = "fileopener";				//Anv�ndarnamn satt av Simon.
	private String password = "parans";					//L�senord satt av Simon.


	public DBConnector(){

	}

	public ArrayList<Location> receiveFromDB() throws Exception{

		ArrayList<Location> places = new ArrayList<Location>();
		Location tempPlace;
		Class.forName(driver).newInstance();
		//Connection conn = DriverManager.getConnection(url+DbName, username, password);
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test","root", "");

		Statement st = conn.createStatement();
		ResultSet result = st.executeQuery("SELECT * FROM locations");

		while(result.next()){

			String fileName = result.getString("fileName");
			Double latitude = result.getDouble("latitude");
			Double longitude = result.getDouble("longitude");
			String date = result.getString("prodDate");

			boolean found = false;

			for (int i = 0; i < places.size(); i++){
				Location cumLoc = places.get(i);

				if(cumLoc.getLat() == latitude && cumLoc.getLong()== longitude ){
					cumLoc.addFile(fileName, date);
					found = true;
				}
			}
			if(!found){
				if( !(latitude > 90 || latitude < -90 || longitude > 180 || longitude < -180)){
					tempPlace = new Location(latitude,longitude,fileName, date);
					places.add(tempPlace);
				}
			}
		}
		conn.close();
		return places;
	}

	public void insertToDB(ArrayList<Location> places) throws Exception {

		String fileName = null;
		int i = 0;
		Class.forName(driver).newInstance();
		//Connection conn = DriverManager.getConnection(url+DbName, username, password);
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test","root", "");

		//for(Entry<String, SolarReceiver> entry : locations.entrySet())
		for(Location location : places){
			for(Entry<String, String> receiver : location.getFiles().entrySet()){
				fileName = receiver.getKey();
				Double latitude = location.getLat();
				Double longitude = location.getLong();
				String date = location.getProductionDate();

				Statement st = conn.createStatement();
				int val = st.executeUpdate("REPLACE INTO locations VALUES('" + fileName + "', " + latitude + ", " + longitude + ", " + date + ")"); //INSERT IGNORE VS REPLACE...
				if(val == 1)
					i++;

			}
		}
		System.out.println("Success, " + i + " files were added to GVS table Locations");
		conn.close();
	}


	/**
	 * Weather update, updates the database with the latest weather data
	 *
	 * @param places, a list that contains the class Location 
	 * @throws Exception, throws Exception if there is something wrong with the connection to the database
	 */
	public void weatherUpdate(ArrayList<Location> places) throws Exception{
		int i = 0;
		Class.forName(driver).newInstance();
		//Connection conn = DriverManager.getConnection(url+DbName, username, password);
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test","root", "");

		//for(Entry<String, SolarReceiver> entry : locations.entrySet())
		for(Location location : places){
			Double latitude = location.getLat();
			Double longitude = location.getLong();
			CurrentWeather CW = location.getWeather();
			Double sunrise = (double) CW.getSunrise();
			Double sunset = (double) CW.getSunset();
			Double cloud = (double) CW.getCloudiness();
			long date = CW.getCurrentTimeDate();
			int sensors = location.getNumSensors();


			Statement st = conn.createStatement();
			//int val = st.executeUpdate("REPLACE INTO WeatherData VALUES('" + date + "', " + latitude + ", " + longitude + " , " + cloud + " , " + sunrise + " , " + sunset + ", " + sensors + ")");
			int val = st.executeUpdate("INSERT INTO WeatherData VALUES('" + date + "', " + latitude + ", " + longitude + " , " + cloud + " , " + sunrise + " , " + sunset + ", " + sensors + ")"); //INSERT IGNORE VS REPLACE...
			if(val == 1)
				i++;

		}
		System.out.println("Success, " + i + " files were added to table WeatherData");
		conn.close();
	}
}
