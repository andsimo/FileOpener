import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherCollector {

	private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?"; // L�nk
																						// till
																						// v�derserver:
																						// http://openweathermap.org/
	private static String API_KEY = "&APPID=0ab3b60b021121ca736c3e9fdc584aa2"; // API_Nyckel
																				// som
																				// tilldelas
																				// vid
																				// registrering
																				// p�
																				// hemsidan.

	//private StaticMapCreator smc;

	public WeatherCollector() {
		//smc = new StaticMapCreator();

	}

	/*
	 * Metod som h�mtar data. Inparametrar latitud och longitud f�r plats av
	 * relevans.
	 */
	public void getWeather(Location location) throws IOException {

		String lat = "lat=" + location.getLat() + "&";
		String lon = "lon=" + location.getLong();

		HttpURLConnection con = null;
		InputStream is = null;

		CurrentWeather weather = new CurrentWeather();

		try {
			con = (HttpURLConnection) (new URL(BASE_URL + lat + lon + API_KEY))
					.openConnection(); // Syr ihop en str�ng till URL och skapar
										// en connection till servern.
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();

			// Let's read the response
			StringBuffer buffer = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;

			while ((line = br.readLine()) != null) { // L�ser och printar ut
														// resultatet fr�n
														// query.
				buffer.append(line + "\r\n");
				getClouds(line, weather);
				getSunTimes(line, weather);
				// S�TT IN H�R!
				System.out.println(line);

			}
			is.close(); // St�nger ned str�mmen!
			con.disconnect();
			
			location.setCurrentWeather(weather);

			// smc.createMap(latitude, longitude); //Avkommentera f�r att f� en statisk bild �ver omr�det.
			// 
			//return buffer.toString();

		} catch (JSONException e){
			//e.printStackTrace();
		}
		finally {
			try {
				is.close();
			} catch (Throwable t) {
			}
			try {
				con.disconnect();
			} catch (Throwable t) {
			}
		}

		//return null;

	}

	public static void getClouds(String data, CurrentWeather weather) {
		// System.out.println("data = " + data);

		try {
			JSONObject jObj = new JSONObject(data);
			JSONObject cOjb = getObject("clouds", jObj);
			weather.setCloudiness(getInt("all", cOjb));
		} catch (JSONException e) {
			// e.printStackTrace();
			weather.setCloudiness(1000);
			// System.out.println("KONSTIGT CLOUD VALUE!   long  " +
			// location.getLong() +"  lat  " + location.getLat());
			System.out.println("Strange cloud value");
		}
	}

	public static void getSunTimes(String data, CurrentWeather weather)
			throws JSONException {
		JSONObject jObj = new JSONObject(data);
		JSONObject cOjb = getObject("sys", jObj);
		weather.setSunrise(getLong("sunrise", cOjb));
		weather.setSunset(getLong("sunset", cOjb));
	}

	private static JSONObject getObject(String tagName, JSONObject jObj)
			throws JSONException {
		return jObj.getJSONObject(tagName);
	}

	private static long getLong(String tagName, JSONObject jObj)
			throws JSONException {
		return jObj.getLong(tagName);
	}

	private static int getInt(String tagName, JSONObject jObj)
			throws JSONException {
		return jObj.getInt(tagName);
	}

}
