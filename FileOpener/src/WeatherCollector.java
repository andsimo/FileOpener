import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherCollector {

	private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?"; // Lï¿½nk
																						// till
																						// vï¿½derserver:
																						// http://openweathermap.org/
	private static String API_KEY = "&APPID=0ab3b60b021121ca736c3e9fdc584aa2"; // API_Nyckel
																				// som
																				// tilldelas
																				// vid
																				// registrering
																				// pï¿½
																				// hemsidan.

	private StaticMapCreator smc;

	public WeatherCollector() {
		smc = new StaticMapCreator();

	}

	/*
	 * Metod som hämtar data. Inparametrar latitud och longitud fï¿½r plats av
	 * relevans.
	 */
	public String getWeather(Location location) {

		String lat = "lat=" + location.getLat() + "&";
		String lon = "lon=" + location.getLong();

		HttpURLConnection con = null;
		InputStream is = null;

		CurrentWeather weather = new CurrentWeather();

		try {
			con = (HttpURLConnection) (new URL(BASE_URL + lat + lon + API_KEY))
					.openConnection(); // Syr ihop en sträng till URL och skapar
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

			while ((line = br.readLine()) != null) { // Läser och printar ut
														// resultatet frï¿½n
														// query.
				buffer.append(line + "\r\n");
				getClouds(line, weather);
				getSunTimes(line, weather);
				// SÄTT IN HÄR!
				System.out.println(line);

			}
			is.close(); // Stänger ned strömmen!
			con.disconnect();
			location.setCurrentWeather(weather);

			// smc.createMap(latitude, longitude); //Avkommentera för att få en
			// statisk bild över området.
			return buffer.toString();

		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Throwable t) {
			}
			try {
				con.disconnect();
			} catch (Throwable t) {
			}
		}

		return null;

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
