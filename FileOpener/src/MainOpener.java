import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class MainOpener extends JFrame{

	private JButton openFiles, browse,saveToExcel;		
	private File filePath,saveFilePath;
	private JLabel filePathLabel, staticPathLabel;
	private JCheckBox saveToExcelBox, saveToDBBox;

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
	 * Browse skapar och �ppnar en JFileChooser (Directory Chooser) medan Open Files ropar p� metod i FileOpener. 
	 */
	public void createGUI(){

		//Detta g�r jag(Emil) f�r att det ska se mer ut som windows
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {e.printStackTrace();     }

		this.setTitle("FileOpener");
		this.setSize(300, 200);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBackground(Color.WHITE);

		// F�r att programmet ska �ppnas i mitten av sk�rmen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

		//F�r att det inte ska g� att �ndra storlek p� program rutan
		this.setResizable(false);

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));


		staticPathLabel = new JLabel("Directory: ");
		staticPathLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		this.add(staticPathLabel);

		filePathLabel = new JLabel(" ");			//Label initeras till "".
		filePathLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		if(filePath != null)			//Men skulle n�got finnas i filePath s� s�tts Label till detta.
			filePathLabel.setText(filePath.toString());


		this.add(filePathLabel);

		/*
		 * Skapar knappar.
		 */
		openFiles = new JButton("Open files");
		browse = new JButton("Browse");
		saveToExcelBox = new JCheckBox("Save to excel");
		saveToExcel = new JButton("Save to (excel)");

		saveToDBBox = new JCheckBox("Save to database");

		openFiles.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		browse.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		saveToExcelBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		saveToExcel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		saveToExcel.setEnabled(false);

		saveToDBBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		openFiles.addActionListener(new ActionListener(){		//TODO: OPENFILES

			public void actionPerformed(ActionEvent e){
				if(filePath != null){							//Om inget directory har valts, g�r nothing
					FileOpener FO = new FileOpener(filePath,saveFilePath);
					if(saveFilePath!= null && saveToExcelBox.isSelected()){
						FO.sendToExcel();
						saveFilePath = null;
					}else{
						if(saveToExcelBox.isSelected()){
							JOptionPane.showMessageDialog(null,
									"Must select the destination folder and name of the file to save to Excel.",
									"Inane error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
					if(saveToDBBox.isSelected()){
						FO.sendToDB();
						
						JOptionPane.showMessageDialog(null,
							    "The data has now been sent to the database","Success",JOptionPane.PLAIN_MESSAGE);
						
					}
					if (!saveToExcelBox.isSelected() && !saveToDBBox.isSelected()){
						JOptionPane.showMessageDialog(null,
								"Must choose to save to Excel or send to the database.",
								"Inane error",
								JOptionPane.ERROR_MESSAGE);
					}
				}else if (filePath == null) {
					JOptionPane.showMessageDialog(null,
							"Must select a folder with log files.",
							"Inane error",
							JOptionPane.ERROR_MESSAGE);
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


				// skapar en Dialog ruta f�r att v�lja en mapp d�r logfilerna ligger
				JFrame parentFrame = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
				fileChooser.setDialogTitle("Open folder");  

				int userSelection = fileChooser.showOpenDialog(parentFrame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					filePath = fileChooser.getSelectedFile();
				}
				if(filePath == null){						 
					filePathLabel.setText(" ");
				}
				else{
					filePathLabel.setText(filePath.toString());
				}
			} 
		});
		/*
		 * lyssnare f�r att aktivera spara till Excel, om ej aktiv ska den inte spara till excel fil
		 */
		saveToExcelBox.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveToExcel.setEnabled(saveToExcelBox.isSelected());
			}
		});
		saveToExcel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame parentFrame = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Save file");  

				int userSelection = fileChooser.showSaveDialog(parentFrame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					saveFilePath = fileChooser.getSelectedFile();
					//System.out.println("Save as file: " + saveFilePath.getAbsolutePath());
				}
			}
		});
		this.add(browse);
		this.add(openFiles);
		this.add(saveToExcelBox);
		this.add(saveToExcel);
		this.add(saveToDBBox);
		this.setVisible(true);
	}
}
