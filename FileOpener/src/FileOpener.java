import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileOpener {
	

	private BufferedReader in;
	private File file;
	private File[] listOfFiles;
										//L�gga till n�got s�tt att lagra texten... eller r�ttare sagt, den relevanta delen av texten.
	
	public FileOpener(File filePath){
		
		file = filePath;
		listOfFiles = file.listFiles();
		

		
	}
	
	/*
	 * 2015-01-26 Har endast testat med .txt-filer. Beh�ver testas med .LOG samt b�r testas i en map inneh�llandes en blandning av andra filer och
	 * mappar. �r kontrollen mot .txt tillr�cklig? 
	 */
	public void OpenFiles(){					
		for(File file: listOfFiles){
			
			if(file.toString().toLowerCase().endsWith(".txt") || file.toString().toLowerCase().endsWith(".log")){
			System.out.println(file.toString());
			
			
			
			try {
				in = new BufferedReader(new FileReader(file));
				
				StringBuilder sb = new StringBuilder();
				String line = in.readLine();
		
				
				while(line != null){
					sb.append(line);
					sb.append(System.lineSeparator());
					line = in.readLine();
				}
				
				String sl = sb.toString();
				System.out.println(sl);
				
			
			
			} catch (IOException e) {
				System.out.println("File " + file + " not found");
				System.out.println("or something else...");
				e.printStackTrace();
			} 
			
		}
		}
		
	
	}
	
	
	
	
}
