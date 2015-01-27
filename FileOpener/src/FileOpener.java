import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;


public class FileOpener {
	

	private BufferedReader in;
	private File file;
	private File[] listOfFiles;
	private ArrayList<Coord> sensors;	
	
	
	public FileOpener(File filePath){
		sensors = new ArrayList<Coord>();
		//i = 0;
		file = filePath;
		listOfFiles = file.listFiles();
		

		
	}
	
	/*
	 * 2015-01-26 Har endast testat med .txt-filer. Behöver testas med .LOG samt bör testas i en map innehållandes en blandning av andra filer och
	 * mappar. Är kontrollen mot .txt tillräcklig? 
	 */
	public void OpenFiles(){					
		for(File file: listOfFiles){
			
			
			
			if(file.toString().toLowerCase().endsWith(".txt") || file.toString().toLowerCase().endsWith(".log")){
			//System.out.println(file.toString());
			
			
			
			try {
				in = new BufferedReader(new FileReader(file));
				Coord coord = new Coord(file.toString());
				
				StringBuilder sb = new StringBuilder();
				String line = in.readLine();
		
				
				while(line != null){
					
					if(line.contains("Longitude") ){
						
						
						sb.append(line);
						sb.append(System.lineSeparator());
						
						String[] sla = sb.toString().split(":");
						coord.setLong(Double.parseDouble(sla[1]));
						
						sb = new StringBuilder();
	
					
					}
					else if(line.contains("Latitude") ){
						
						
						sb.append(line);
						sb.append(System.lineSeparator());
						
						String[] sla = sb.toString().split(":");
						coord.setLat(Double.parseDouble(sla[1]));
						
						sb = new StringBuilder();
						
					}
					
					line = in.readLine();
					
				}
				
				
				if(coord.getLat() == 0 && coord.getLong() == 0){
					coord = null;
				}
				else{
				sensors.add(coord);
				}
			
			} catch (IOException e) {
				System.out.println("File " + file + " not found");
				System.out.println("or something else...");
				e.printStackTrace();
			}
			
			
			
		}
			
		}
		
		sendToExcel();
	
	}
	
	
	public void sendToExcel(){
		
		
		
		/*
		 * Avkommentera ifall utskrivning önskas!
		 * 
		    for(Coord coord: sensors){
			System.out.println(coord.getFile());
			System.out.println("Long: " + coord.getLong() + "\t Lat: " + coord.getLat() + "\n");
		}*/
	}
	
	
	
	
}
