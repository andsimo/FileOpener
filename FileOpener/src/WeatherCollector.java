import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherCollector {

	private static String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?"; //Länk till väderserver: http://openweathermap.org/
	private static String API_KEY ="&APPID=0ab3b60b021121ca736c3e9fdc584aa2"; //API_Nyckel som tilldelas vid registrering på hemsidan.
	
	
	public WeatherCollector(){
		
	}
	
	/*
	 * Metod som hämtar data. Inparametrar latitud och longitud för plats av relevans.
	 */
	public String getWeather(double latitude, double longitude){

		String lat = "lat="+latitude+"&";
		String lon = "lon="+longitude;


		HttpURLConnection con = null ;
		InputStream is = null;


		try {
			con = (HttpURLConnection) ( new URL(BASE_URL+lat+lon+API_KEY)).openConnection(); //Syr ihop en sträng till URL och skapar en connection till servern.
			con.setRequestMethod("GET");
			con.setDoInput(true);
			con.setDoOutput(true);
			con.connect();

			// Let's read the response
			StringBuffer buffer = new StringBuffer();
			is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while (  (line = br.readLine()) != null ){ //Läser och printar ut resultatet från query.
				buffer.append(line + "\r\n");
				System.out.println(line);
			}
			is.close();					//Stänger ned strömmen!
			con.disconnect();
			return buffer.toString();
		}
		catch(Throwable t) {
			t.printStackTrace();
		}
		finally {
			try { is.close(); } catch(Throwable t) {}
			try { con.disconnect(); } catch(Throwable t) {}
		}



		return null;

	}
	
	
}
