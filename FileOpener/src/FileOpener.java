import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;



public class FileOpener {
	

	private BufferedReader in;
	private File file;
	private File[] listOfFiles;
	private HashMap<String, Coord> locations;
	private WeatherCollector wC;
	private ExcelIO eIO;
	private DBConnector dataBase;
	
	
	public FileOpener(File filePath){
		locations = new HashMap<String, Coord>();
		file = filePath;
		listOfFiles = file.listFiles();
		wC = new WeatherCollector();
		dataBase = new DBConnector();
		eIO = new ExcelIO();

	}
	
	
	/*
	 * Behöver testas med .LOG samt bör testas i en map innehållandes en blandning av andra filer och
	 * mappar. Är kontrollen mot .txt tillräcklig? 
	 */
	public void OpenFiles(){					
		for(File file: listOfFiles){
	
			if(file.toString().toLowerCase().endsWith(".log")){	//Filtrerar bort filer som inte slutar med .log
			
			
			
			try {
				in = new BufferedReader(new FileReader(file));		
				Coord coord = new Coord();			//Skapar ett Coord-objekt för varje lat/long-par.
				
				StringBuilder sb = new StringBuilder();
				String line = in.readLine();
		
				
				while(line != null){
					
					if(line.contains("Longitude") ){
						
						sb.append(line);
						sb.append(System.lineSeparator());
						
						String[] sla = sb.toString().split(":");		//Filtrerar bort "Longitude set as:" och lagrar som double i Coord-objektet
						coord.setLong(Double.parseDouble(sla[1]));
						
						sb = new StringBuilder();			//Måste skapa en ny stringBuilder varje gång eftersom 
	
					
					}
					else if(line.contains("Latitude") ){		//Kodduplicering? Finns vettig lösning?
						
						
						sb.append(line);
						sb.append(System.lineSeparator());
						
						String[] sla = sb.toString().split(":");
						coord.setLat(Double.parseDouble(sla[1]));
						
						sb = new StringBuilder();
						
					}
					
					line = in.readLine();
					
				}
				
				/*
				 * Sorterar bort koordinater med longitud och latitud (0, 0)...
				 * Måste kolla med handledare vad göra med dessa! I dagsläget kastas objektet bort.
				 * 
				 */
				if(coord.getLat() == 0 && coord.getLong() == 0){
					coord = null;
				}
				else{
				locations.put(file.getName(), coord);
				}
			
			} catch (IOException e) {									//Skitdålig felhantering... it's something!
				System.out.println("File " + file + " not found");
				System.out.println("or something else...");
				e.printStackTrace();
			}
			
			
			
		}
			
		}
		
		//sendToExcel();
		//getWeathers();
		//sendToDB();
		//eIO.writeToExcel();
		sendToExcel();
	}
	
	
	/*
	 * Kommer i framtiden på något vis skicka till excel!
	 *
	public void sendToExcel(){	
		    
		for(Coord coord: sensors){
			System.out.println("Long: " + coord.getLong() + "\t Lat: " + coord.getLat() + "\n");
		}
	}
	
	public void getWeathers(){
		
		
		for(Coord coord: sensors){
			WC.getWeather(coord.getLat(), coord.getLong());
			
		}
	}
	*/
	
	public void sendToExcel(){
		eIO.writeManyToExcel(locations);
	}
	
	public void sendToDB(){
		
		dataBase.insertToDB(locations);
		
	}
	
	
	
	
	
	
}
