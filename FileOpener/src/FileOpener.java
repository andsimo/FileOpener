import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class FileOpener {


	private BufferedReader in;
	private File file;
	private File[] listOfFiles;
	private ArrayList<Location> places;
	private ExcelIO eIO;
	private DBConnector dataBase;

	public FileOpener(File filePath, File saveFilePath) {
		//locations = new HashMap<String, SolarReceiver>();
		places = new ArrayList<Location>();
		file = filePath;
		listOfFiles = file.listFiles();
		new WeatherCollector();
		eIO = new ExcelIO(saveFilePath);
		OpenFiles();
		dataBase = new DBConnector();
	}


	/*
	 * Behï¿½ver testas med .LOG samt bï¿½r testas i en map innehï¿½llandes en blandning av andra filer och
	 * mappar. ï¿½r kontrollen mot .txt tillrï¿½cklig? 
	 */
	private void OpenFiles(){					
		for(File file: listOfFiles){

			if(file.toString().toLowerCase().endsWith(".log")){	//Filtrerar bort filer som inte slutar med .log



				try {
					in = new BufferedReader(new FileReader(file));		
					//--------- SolarReceiver solarReceiver = new SolarReceiver();			//Skapar ett SolarReciver-objekt fï¿½r varje lat/long-par.
					Location location = new Location();
					StringBuilder sb = new StringBuilder();
					String line = in.readLine();

					//Lï¿½ser in datum frï¿½n filnamn och sparar i solarReciver
					// ------- solarReceiver.setProductionDate(getDate(file.getName()));
					if(!location.containsFile(file.getName())){
						location.addReceiver(file.getName(), getDate(file.getName()));
					}
					
					while(line != null){

						if(line.contains("Longitude") ){

							sb.append(line);
							sb.append(System.lineSeparator());

							String[] sla = sb.toString().split(":");		//Filtrerar bort "Longitude set as:" och lagrar som double i SolarReciver-objektet
							//------- solarReceiver.setLong(Double.parseDouble(sla[1]));
							location.setLong(Double.parseDouble(sla[1]));

							sb = new StringBuilder();			//Mï¿½ste skapa en ny stringBuilder varje gï¿½ng eftersom 


						}
						else if(line.contains("Latitude") ){		//Kodduplicering? Finns vettig lï¿½sning?


							sb.append(line);
							sb.append(System.lineSeparator());

							String[] sla = sb.toString().split(":");
							//----- solarReceiver.setLat(Double.parseDouble(sla[1]));
							location.setLat(Double.parseDouble(sla[1]));

							sb = new StringBuilder();

						}

						line = in.readLine();

					}

					/*
					 * Sorterar bort koordinater med longitud och latitud (0, 0)...
					 * Mï¿½ste kolla med handledare vad gï¿½ra med dessa! I dagslï¿½get kastas objektet bort.
					 * 
					 */
					if(location.getLat() == 0 && location.getLong() == 0 || location.getLat() > 90 || location.getLat() < -90){
						location = null;
					}
					else{
						//---------locations.put(file.getName(), solarReceiver);
						places.add(location);
						/**
						 * Reverse Geocoding
						 * 
						 * GetLocationInfo GLI = new GetLocationInfo(location.getLat(),location.getLong());
						 * 
						 * GLI.getCountr() för att hämta Land
						 * 
						 * GLI.getLocality() för att hämta stadsnamn
						 */
					}

				} catch (IOException e) {									//Skitdï¿½lig felhantering... it's something!
					System.out.println("File " + file + " not found");
					System.out.println("or something else...");
					e.printStackTrace();
				}



			}

		}
	}

	/**
	 * 
	 * @return Date
	 */
	private String getDate(String fileName) {
		Pattern datePattern = Pattern.compile("(\\d{4}) (\\d{2}) (\\d{2})");
		Matcher dateMatcher = datePattern.matcher(fileName);
		if (dateMatcher.find()){
			String temp = dateMatcher.group(1)+dateMatcher.group(2)+dateMatcher.group(3);
			return temp;
		}
		return null;		
	}


	/*

	public void getWeathers(){


		for(SolarReciver solarReciver: sensors){
			WC.getWeather(solarReciver.getLat(), solarReciver.getLong());

		}
	}
	 */

	public void sendToExcel(){
		//eIO.writeToExcel(locations);
		eIO.writeToExcel(places);
	}

	public void sendToDB() throws Exception{
		
		dataBase.insertToDB(places);
		//dataBase.insertToDB(locations);

	}
}
