import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class DBConnector {

	private String url = "jdbc:mysql://46.239.117.17:3306/";	//Just nu endast lokalt. min IP 46.239.117.17    localhost
	private String DbName = "GVS";						//Schemats namn satt av Simon
	private String driver ="com.mysql.jdbc.Driver";		//Väljer vilken typ av db vi kopplar upp oss mot. Kräver buildpath.
	private String username = "fileopener";				//Användarnamn satt av Simon.
	private String password = "parans";					//Lösenord satt av Simon.
	
	
	public DBConnector(){
		
	}
	
	public void receiveFromDB(){
		
		try{
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url+DbName, username, password);
			
			Statement st = conn.createStatement();
			ResultSet result = st.executeQuery("SELECT * FROM locations");
			
			while(result.next()){
				
			String fileName = result.getString("fileName");
			Double latitude = result.getDouble("latitude");
			Double longitude = result.getDouble("longitude");
			
			System.out.println(fileName + " lat: " + latitude + " long: " + longitude);
				
			}
			
			
			conn.close();
		} catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	
	public void insertToDB(ArrayList<Location> places){
		String fileName = null;
		int i = 0;
		
		try{
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(url+DbName, username, password);
			
			//for(Entry<String, SolarReceiver> entry : locations.entrySet())
			for(Location location : places){
				for(Entry<String, java.util.Date> receiver : location.getFiles().entrySet()){
				fileName = receiver.getKey();
				Double latitude = location.getLat();
				Double longitude = location.getLong();
				
				Statement st = conn.createStatement();
				int val = st.executeUpdate("REPLACE INTO locations VALUES('" + fileName + "', " + latitude + ", " + longitude + ")"); //INSERT IGNORE VS REPLACE...
				if(val == 1)
						i++;
				
			}
			}
			System.out.println("Success, " + i + " files were added to GVS table Locations");
			conn.close();
			
			} catch (Exception e){
			System.out.println(fileName + " error!");
			e.printStackTrace();
			
		}
		
		
		
	}
	
	
	
	
}
