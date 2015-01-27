import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class FileOpener {
	

	private BufferedReader in;
	private File file;
	private File[] listOfFiles;
	private ArrayList<Coord> sensors;	
	
	
	public FileOpener(File filePath){
		sensors = new ArrayList<Coord>();
		file = filePath;
		listOfFiles = file.listFiles();

	}
	
	
	/*
	 * 2015-01-26 Har endast testat med .txt-filer. Behöver testas med .LOG samt bör testas i en map innehållandes en blandning av andra filer och
	 * mappar. Är kontrollen mot .txt tillräcklig? 
	 */
	public void OpenFiles(){					
		for(File file: listOfFiles){
	
			if(file.toString().toLowerCase().endsWith(".txt") || file.toString().toLowerCase().endsWith(".log")){	//Filtrerar bort filer som inte slutar på .txt och .log (bör kanske även sortera bort txt-filer?)
			//System.out.println(file.toString());
			
			
			
			try {
				in = new BufferedReader(new FileReader(file));		
				Coord coord = new Coord(file.toString());			//Skapar ett Coord-objekt för varje lat/long-par.
				
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
				sensors.add(coord);
				}
			
			} catch (IOException e) {									//Skitdålig felhantering... it's something!
				System.out.println("File " + file + " not found");
				System.out.println("or something else...");
				e.printStackTrace();
			}
			
			
			
		}
			
		}
		
		sendToExcel();
	
	}
	
	
	/*
	 * Kommer i framtiden på något vis skicka till excel!
	 */
	public void sendToExcel(){	
		/*
		 * Avkommentera ifall utskrivning önskas!
		 */
		    for(Coord coord: sensors){
			System.out.println(coord.getFile());
			System.out.println("Long: " + coord.getLong() + "\t Lat: " + coord.getLat() + "\n");
		}//*/
	}
	
	
	
	
}
