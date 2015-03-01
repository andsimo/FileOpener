import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


@SuppressWarnings("deprecation")
public class GetLocationInfo {
	private String info;
	public GetLocationInfo(double lat, double lng){
		// get lat and lng value
		JSONObject ret = getLocationInfo(lat,lng); 
		JSONObject location;
		String location_string;
		try {
			//Get JSON Array called "results" and then get the 0th complete object as JSON        
			location = ret.getJSONArray("results").getJSONObject(0); 
			// Get the value of the attribute whose name is "formatted_string"
			location_string = location.getString("formatted_address");
			//System.out.println(location_string.toString());
			info = location_string.toString();
		} catch (JSONException e1) {
			e1.printStackTrace();

		}
	}
	
	public String getInfo(){
		return info;
	}
	private JSONObject getLocationInfo( double lat, double lng) {

		HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?latlng="+lat+","+lng+"&sensor=false");
		HttpClient client = new DefaultHttpClient();
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
