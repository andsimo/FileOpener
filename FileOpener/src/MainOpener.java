import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;





public class MainOpener extends JFrame{

	private JButton openFiles, browse;		
	private File filePath;
	private JFileChooser fileChooser;
	private JLabel label;
	
	
	public static void main(String[] args) {
		new MainOpener();
		
		while(true){		
			
		}

	}
	
	
public MainOpener(){
		filePath = null;		
		createGUI();
}

/*
 * Skapar GUI med 1st label som visar senast valda directory samt 2 knappar Browse(browse) och  Open Files(openFiles).
 * Browse skapar och �ppnar en JFileChooser (Directory Chooser) medan Open Files ropar p� metod i FileOpener. 
 */
public void createGUI(){

	this.setTitle("FileOpener");
	this.setSize(300, 200);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setBackground(Color.WHITE);
	
	getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
	
	label = new JLabel("");			//Label initeras till "". 
	
	if(filePath != null)			//Men skulle n�got finnas i filePath s� s�tts Label till detta.
		label.setText(filePath.toString());
	
	this.add(label);
	
	
	
	openFiles = new JButton("Open files");
	browse = new JButton("Browse");
	

	
	openFiles.addActionListener(new ActionListener(){		//TODO: OPENFILES
		
		public void actionPerformed(ActionEvent e){
			if(filePath != null){							//Om inget directory har valts, g�r nothing
			FileOpener FO = new FileOpener(filePath);
			FO.OpenFiles();
			}
		}
	});
	
	//******************************************************//
	
	/*
	 * ActionListener f�r browse. Skapar en fileChooser och lagrar det valda directoriet
	 * i filePath, samt uppdaterar dennes Label.
	 */
	browse.addActionListener(new ActionListener(){			
		public void actionPerformed(ActionEvent e){
			
			
			fileChooser = new JFileChooser();
			
			/*
			 * Fixa s� att man kan visa filer ocks�, s� att det blir enklare att v�lja r�tt directory
			 * JFileChooser.Files_AND_DIRECTORIES... leder dock till en bugg d�r f�ltet �r tomt.
			 * 
			 * if(!filePath.isDirectory() || filePath == null){			
					filePath = filePath.getParentFile();
				}
			 *	
			 */
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);		
																					
			if(filePath != null){
				fileChooser.setCurrentDirectory(filePath);
			}
			
			//fileChooser.showSaveDialog(null);						//B�r kollas upp vad denna rad g�r... 
			fileChooser.setFileHidingEnabled(false);
				
			int result = fileChooser.showSaveDialog(fileChooser);	//Bugg nedan l�st med att spara resultatet som en int och kolla att filen valts med APPROVE_OPTION
			//System.out.println(result);
			
			if(result == JFileChooser.APPROVE_OPTION){
				filePath = fileChooser.getSelectedFile();
				
				
				
				if(filePath == null)						 
					label.setText("");
				else
					label.setText(filePath.toString());
			}
			else if(result == JFileChooser.CANCEL_OPTION){	//Om filen "valts" med cancel eller x i h�rnet... g�r n�tt vettigt... antar jag...
				System.out.println("cancel");
			}
			
			
			/*filePath = fileChooser.getSelectedFile();
			if(filePath == null)						//BUGG: Ifall man trycker avbryt lagras ibland str�ngen iallafall... [L�st ovan, sparas "ifall att"]
				label.setText("");
			else
				label.setText(filePath.toString());
			*/
		}
	});
	
	
	this.add(browse);
	this.add(openFiles);
	
	this.setVisible(true);
}



}
