import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class MainOpener extends JFrame{

	private JButton openFiles, browse;		
	private File filePath;
	private JFileChooser fileChooser;
	private JLabel filePathLabel, staticPathLabel;
	
	
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
 * Browse skapar och öppnar en JFileChooser (Directory Chooser) medan Open Files ropar på metod i FileOpener. 
 */
public void createGUI(){

	this.setTitle("FileOpener");
	this.setSize(300, 200);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.setBackground(Color.WHITE);
	
	getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
	
	
	staticPathLabel = new JLabel("File: ");
	staticPathLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
	this.add(staticPathLabel);
	
	filePathLabel = new JLabel(" ");			//Label initeras till "".
	filePathLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
	
	if(filePath != null)			//Men skulle något finnas i filePath så sätts Label till detta.
		filePathLabel.setText(filePath.toString());
	
	
	this.add(filePathLabel);
	
	
	
	
	
	/*
	 * Skapar knappar.
	 */
	openFiles = new JButton("Open files");
	browse = new JButton("Browse");
	
	openFiles.setAlignmentX(JComponent.CENTER_ALIGNMENT);
	browse.setAlignmentX(JComponent.CENTER_ALIGNMENT);

	
	openFiles.addActionListener(new ActionListener(){		//TODO: OPENFILES
		
		public void actionPerformed(ActionEvent e){
			if(filePath != null){							//Om inget directory har valts, gör nothing
			FileOpener FO = new FileOpener(filePath);
			FO.OpenFiles();
			}
		}
	});
	
	//******************************************************//
	
	/*
	 * ActionListener för browse. Skapar en fileChooser och lagrar det valda directoriet
	 * i filePath, samt uppdaterar dennes Label.
	 */
	browse.addActionListener(new ActionListener(){			
		public void actionPerformed(ActionEvent e){
			
			
			fileChooser = new JFileChooser();
			
			/*
			 * Fixa så att man kan visa filer också, så att det blir enklare att välja rätt directory
			 * JFileChooser.Files_AND_DIRECTORIES... leder dock till en bugg där fältet är tomt.
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
			
			//fileChooser.showSaveDialog(null);						//Bör kollas upp vad denna rad gör... 
			fileChooser.setFileHidingEnabled(false);
				
			int result = fileChooser.showSaveDialog(fileChooser);	//Bugg nedan löst med att spara resultatet som en int och kolla att filen valts med APPROVE_OPTION
			//System.out.println(result);
			
			if(result == JFileChooser.APPROVE_OPTION){
				filePath = fileChooser.getSelectedFile();
				
				
				
				if(filePath == null)						 
					filePathLabel.setText("");
				else
					filePathLabel.setText(filePath.toString());
			}
			else if(result == JFileChooser.CANCEL_OPTION){	//Om filen "valts" med cancel eller x i hörnet... gör nått vettigt... antar jag...
				//System.out.println("cancel");
			}
	
		}
	});
	
	
	this.add(browse);
	this.add(openFiles);
	
	this.setVisible(true);
}



}
