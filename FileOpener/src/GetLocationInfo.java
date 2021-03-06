import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


// TODO: Auto-generated Javadoc
/**
 * The Class GetLocationInfo.
 * Reverse Geocoding
 * F�r att h�mta namn p� stad och land f�r en specifk lat/long
 * 
 */
@SuppressWarnings("deprecation")
public class GetLocationInfo {
	
	/** The neighborhood. */
	private String neighborhood;
	
	/** The countr. */
	private String countr;
	
	/** The sublocality. */
	private String sublocality;
	
	/**
	 * Gets the neighborhood.
	 *
	 * @return the neighborhood
	 */
	public String getNeighborhood() {
		return neighborhood;
	}

	/**
	 * Gets the countr.
	 *
	 * @return the countr
	 */
	public String getCountr() {
		return countr;
	}

	/**
	 * Gets the sublocality.
	 *
	 * @return the sublocality
	 */
	public String getSublocality() {
		return sublocality;
	}

	/**
	 * Gets the postalcode.
	 *
	 * @return the postalcode
	 */
	public String getPostalcode() {
		return postalcode;
	}

	/**
	 * Gets the admin area.
	 *
	 * @return the admin area
	 */
	public String getAdminArea() {
		return adminArea;
	}

	/**
	 * Gets the subadmin area.
	 *
	 * @return the subadmin area
	 */
	public String getSubadminArea() {
		return subadminArea;
	}

	/**
	 * Gets the locality.
	 *
	 * @return the locality
	 */
	public String getLocality() {
		return locality;
	}

	/** The postalcode. */
	private String postalcode;
	
	/** The admin area. */
	private String adminArea;
	
	/** The subadmin area. */
	private String subadminArea;
	
	/** The locality. */
	private String locality;

	private HttpClient client;
	
	/**
	 * Instantiates a new gets the location info.
	 *
	 * @param lat the lat
	 * @param lng the lng
	 */
	public GetLocationInfo(double lat, double lng){
		// get lat and lng value
		JSONObject ret = getLocationInfo(lat,lng); 
		JSONObject location;
		
		
		
		try {
			//Get JSON Array called "results" and then get the 0th complete object as JSON        
			location = ret.getJSONArray("results").getJSONObject(0);

			JSONArray addrComp = location.getJSONArray("address_components");

			System.out.println("_______________________________________");
			for( int j = 0; j < addrComp.length(); j++){
				String tempNeighborhood = ((JSONArray)((JSONObject)addrComp.get(j)).get("types")).getString(0);
				if (tempNeighborhood.compareTo("neighborhood") == 0) {
					neighborhood = ((JSONObject)addrComp.get(j)).getString("long_name");
					System.out.println("neighborhood: "+neighborhood);
				} 
				
				String tempLocality = ((JSONArray)((JSONObject)addrComp.get(j)).get("types")).getString(0);
                if (tempLocality.equals("locality")) {
                	locality = ((JSONObject)addrComp.get(j)).getString("long_name");
                	System.out.println("locality: "+locality);
                }
				
				String tempSubadminArea = ((JSONArray)((JSONObject)addrComp.get(j)).get("types")).getString(0);
                if (tempSubadminArea.compareTo("administrative_area_level_2") == 0) {
                	subadminArea = ((JSONObject)addrComp.get(j)).getString("long_name");
                	System.out.println("subadminArea: "+subadminArea);
                }
				
				String tempAdminArea = ((JSONArray)((JSONObject)addrComp.get(j)).get("types")).getString(0);
                if (tempAdminArea.compareTo("administrative_area_level_1") == 0) {
                	adminArea = ((JSONObject)addrComp.get(j)).getString("long_name");
                	System.out.println("adminArea: "+adminArea);
                }
				
				String tempPostalcode = ((JSONArray)((JSONObject)addrComp.get(j)).get("types")).getString(0);
                if (tempPostalcode.compareTo("postal_code") == 0) {
                	postalcode = ((JSONObject)addrComp.get(j)).getString("long_name");
                	System.out.println("postalcode: "+ postalcode);
                }
				
				String tempSublocality = ((JSONArray)((JSONObject)addrComp.get(j)).get("types")).getString(0);
                if (tempSublocality.compareTo("sublocality") == 0) {
                	sublocality = ((JSONObject)addrComp.get(j)).getString("long_name");
                	System.out.println("sublocality: "+ sublocality);
                }
				
				String tempCountr = ((JSONArray)((JSONObject)addrComp.get(j)).get("types")).getString(0);
                if (tempCountr.compareTo("country") == 0) {
                	countr = ((JSONObject)addrComp.get(j)).getString("long_name");
                	System.out.println("countr: " +countr);
                }
			}
		} catch (JSONException e1) {

		}
	}

	/**
	 * Gets the location info.
	 *
	 * @param lat the lat
	 * @param lng the lng
	 * @return the location info
	 */
	private JSONObject getLocationInfo( double lat, double lng) {
		HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=false");
		client = new DefaultHttpClient();
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
		} catch (IOException e) {
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
