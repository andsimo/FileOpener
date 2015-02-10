import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;


public class MainOpener extends JFrame{

	private JButton openFiles, browse,saveTo;		
	private File filePath,saveFilePath;
	private JFileChooser fileChooser;
	private JLabel filePathLabel, staticPathLabel;
	private JCheckBox saveToExcelBox;
	private boolean saveToExcel = false;

	public static void main(String[] args) {
		new MainOpener(); 

		while(true){		

		}

	}


	public MainOpener(){
		filePath = null;

		/*DBConnector db = new DBConnector();
		db.receiveFromDB();
		db.insertIntoDB("fil1", -12.1212, 123.123032);
		 */

		createGUI();
	}

	/*
	 * Skapar GUI med 2st labels som visar senast valda directory samt 2 knappar Browse(browse) och  Open Files(openFiles).
	 * Browse skapar och ï¿½ppnar en JFileChooser (Directory Chooser) medan Open Files ropar pï¿½ metod i FileOpener. 
	 */
	public void createGUI(){
		
		//Detta gör jag(Emil) för att det ska se mer ut som windows
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (Exception e) {e.printStackTrace();     }

		this.setTitle("FileOpener");
		this.setSize(300, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(Color.WHITE);

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));


		staticPathLabel = new JLabel("Directory: ");
		staticPathLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		this.add(staticPathLabel);

		filePathLabel = new JLabel(" ");			//Label initeras till "".
		filePathLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		if(filePath != null)			//Men skulle nï¿½got finnas i filePath sï¿½ sï¿½tts Label till detta.
			filePathLabel.setText(filePath.toString());


		this.add(filePathLabel);





		/*
		 * Skapar knappar.
		 */
		openFiles = new JButton("Open files");
		browse = new JButton("Browse");
		saveToExcelBox = new JCheckBox("Save To Excel");
		saveTo = new JButton("Save To");

		openFiles.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		browse.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		saveToExcelBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		saveTo.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		saveTo.setEnabled(false);

		openFiles.addActionListener(new ActionListener(){		//TODO: OPENFILES

			public void actionPerformed(ActionEvent e){
				if(filePath != null){							//Om inget directory har valts, gï¿½r nothing

					System.out.println("saveFilePath: " +(saveFilePath != null));
					System.out.println("saveToExcel: "+ saveToExcel);
					if(saveFilePath!= null && saveToExcel){
						FileOpener FO = new FileOpener(filePath,saveFilePath);
						FO.OpenFiles();
					}else{
						FileOpener FO = new FileOpener(filePath);
						FO.OpenFiles();
					}
				}
			}
		});

		//******************************************************//

		/*
		 * ActionListener fï¿½r browse. Skapar en fileChooser och lagrar det valda directoriet
		 * i filePath, samt uppdaterar dennes Label.
		 */
		browse.addActionListener(new ActionListener(){			
			public void actionPerformed(ActionEvent e){


				fileChooser = new JFileChooser();

				/*
				 * Fixa sï¿½ att man kan visa filer ocksï¿½, sï¿½ att det blir enklare att vï¿½lja rï¿½tt directory
				 * JFileChooser.Files_AND_DIRECTORIES... leder dock till en bugg dï¿½r fï¿½ltet ï¿½r tomt.
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

				//fileChooser.showSaveDialog(null);						//Bï¿½r kollas upp vad denna rad gï¿½r... 
				fileChooser.setFileHidingEnabled(false);

				int result = fileChooser.showSaveDialog(fileChooser);	//Bugg nedan lï¿½st med att spara resultatet som en int och kolla att filen valts med APPROVE_OPTION
				//System.out.println(result);

				if(result == JFileChooser.APPROVE_OPTION){
					filePath = fileChooser.getSelectedFile();



					if(filePath == null)						 
						filePathLabel.setText("");
					else
						filePathLabel.setText(filePath.toString());
				}
				else if(result == JFileChooser.CANCEL_OPTION){	//Om filen "valts" med cancel eller x i hï¿½rnet... gï¿½r nï¿½tt vettigt... antar jag...
					//System.out.println("cancel");
				}

			}
		});

		/*
		 * lyssnare för att aktivera spara till Excel, om ej aktiv ska den inte spara till excel fil
		 */
		saveToExcelBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveToExcel = !saveToExcel;
				saveTo.setEnabled(saveToExcel);
			}

		});

		saveTo.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame parentFrame = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Spara fil som");  

				int userSelection = fileChooser.showSaveDialog(parentFrame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					saveFilePath = fileChooser.getSelectedFile();
					System.out.println("Save as file: " + saveFilePath.getAbsolutePath());
				}
			}
		});

		this.add(browse);
		this.add(openFiles);
		this.add(saveToExcelBox);
		this.add(saveTo);

		this.setVisible(true);
	}
}
