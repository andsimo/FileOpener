import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class MainOpener extends JFrame {

	private JButton openFiles, browse, saveToExcel;
	private File filePath, saveFilePath;
	private JLabel filePathLabel, staticPathLabel;
	private JCheckBox saveToExcelBox, saveToDBBox;
	private JTextField browseField;

	private final JFrame thisFrame = this;
	private JCheckBox excelCheckBox, dbCheckBox;

	private TrayIcon trayIcon;
	private SystemTray sysTray;
	private Image paransImage;
	private PopupMenu trayMenu;
	private MenuItem menuItem1, menuItem2;

	private JMenuBar menuBar;
	private JMenu statusMenu, fileMenu;
	private JMenuItem menuItemRunning, menuItemExit;
	private ImageIcon red, green;
	private JPanel statusPanel;
	private JLabel consoleLabel;

	private volatile boolean running = false;
	
	public static void main(String[] args) {
		new MainOpener();
		
		while (true) {

		}

	}

	public MainOpener() {
		filePath = null;

		/*
		 * DBConnector db = new DBConnector(); db.receiveFromDB();
		 * db.insertIntoDB("fil1", -12.1212, 123.123032);
		 */

		//createGUI();
		initGUI();
		//UpdateWeather();
	}
	
	
		private void setConsoleText(final String text){
			SwingUtilities.invokeLater(new Runnable(){
				public void run(){
					consoleLabel.setText(text);
				}
			});
		}


	private void UpdateWeather() {

		
		setConsoleText("Updating weather, please wait!");
		this.setEnabled(false);
		
		WeatherCollector WC = new WeatherCollector();
		DBConnector DB = new DBConnector();
		ArrayList<Location> tempList = DB.receiveFromDB();
		
		if(tempList.size() > 0){
		
		for(int i = 0; i < tempList.size(); i++){
			WC.getWeather(tempList.get(i));
			tempList.get(i).setWeatherTime(System.currentTimeMillis());
		}
		DB.weatherUpdate(tempList);
		}
		else{
			JOptionPane
			.showMessageDialog(
					null,
					"Empty DB.",
					"Inane error",
					JOptionPane.ERROR_MESSAGE);
		}
	
		//updateBackground();
		
		this.setEnabled(true);

		setConsoleText("Ready!");
	}

	/*
	 * Skapar GUI med 2st labels som visar senast valda directory samt 2 knappar
	 * Browse(browse) och Open Files(openFiles). Browse skapar och �ppnar en
	 * JFileChooser (Directory Chooser) medan Open Files ropar p� metod i
	 * FileOpener.
	 */
	public void createGUI() {

		// Detta g�r jag(Emil) f�r att det ska se mer ut som windows
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setTitle("FileOpener");
		this.setSize(300, 200);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setBackground(Color.WHITE);

		// F�r att programmet ska �ppnas i mitten av sk�rmen
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height
				/ 2 - this.getSize().height / 2);

		// F�r att det inte ska g� att �ndra storlek p� program rutan
		this.setResizable(false);

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		staticPathLabel = new JLabel("Directory: ");
		staticPathLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		this.add(staticPathLabel);

		filePathLabel = new JLabel(" "); // Label initeras till "".
		filePathLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		if (filePath != null) // Men skulle n�got finnas i filePath s�
								// s�tts Label till detta.
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

		openFiles.addActionListener(new ActionListener() { // TODO: OPENFILES

					public void actionPerformed(ActionEvent e) {
						if (filePath != null) { // Om inget directory har valts,
												// g�r nothing
							FileOpener FO = new FileOpener(filePath,
									saveFilePath);
							if (saveFilePath != null
									&& saveToExcelBox.isSelected()) {
								FO.sendToExcel();
								saveFilePath = null;
							} else {
								if (saveToExcelBox.isSelected()) {
									JOptionPane
											.showMessageDialog(
													null,
													"Must select the destination folder and name of the file to save to Excel.",
													"Inane error",
													JOptionPane.ERROR_MESSAGE);
								}
							}
							if (saveToDBBox.isSelected()) {
								FO.sendToDB();

								JOptionPane
										.showMessageDialog(
												null,
												"The data has now been sent to the database",
												"Success",
												JOptionPane.PLAIN_MESSAGE);

							}
							if (!saveToExcelBox.isSelected()
									&& !saveToDBBox.isSelected()) {
								JOptionPane
										.showMessageDialog(
												null,
												"Must choose to save to Excel or send to the database.",
												"Inane error",
												JOptionPane.ERROR_MESSAGE);
							}
						} else if (filePath == null) {
							JOptionPane.showMessageDialog(null,
									"Must select a folder with log files.",
									"Inane error", JOptionPane.ERROR_MESSAGE);
						}
					}
				});

		// ******************************************************//
		/*
		 * ActionListener f�r browse. Skapar en fileChooser och lagrar det
		 * valda directoriet i filePath, samt uppdaterar dennes Label.
		 */
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// skapar en Dialog ruta f�r att v�lja en mapp d�r
				// logfilerna ligger
				JFrame parentFrame = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setDialogTitle("Open folder");

				int userSelection = fileChooser.showOpenDialog(parentFrame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					filePath = fileChooser.getSelectedFile();
				}
				if (filePath == null) {
					filePathLabel.setText(" ");
				} else {
					filePathLabel.setText(filePath.toString());
				}
			}
		});
		/*
		 * lyssnare f�r att aktivera spara till Excel, om ej aktiv ska den
		 * inte spara till excel fil
		 */
		saveToExcelBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveToExcel.setEnabled(saveToExcelBox.isSelected());
			}
		});
		saveToExcel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame parentFrame = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setDialogTitle("Save file");

				int userSelection = fileChooser.showSaveDialog(parentFrame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					saveFilePath = fileChooser.getSelectedFile();
					// System.out.println("Save as file: " +
					// saveFilePath.getAbsolutePath());
				}
			}
		});
		initMenubarSettings();
		this.add(browse);
		this.add(openFiles);
		this.add(saveToExcelBox);
		this.add(saveToExcel);
		this.add(saveToDBBox);
		this.setVisible(true);
		initTraySettings();
	}

	public void initGUI() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setResizable(false);
		this.setBounds(100, 100, 423, 265);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		this.getContentPane().setLayout(new MigLayout("", "[grow][][]", "[][][][][][][]"));

		JLabel lblDirectory = new JLabel("Directory:");
		this.getContentPane().add(lblDirectory, "cell 0 0");

		browseField = new JTextField();
		this.getContentPane().add(browseField, "cell 0 1 2 1,growx");
		browseField.setColumns(10);

		JButton browseButton = new JButton("Browse");
		browseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFrame parentFrame = new JFrame();
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setDialogTitle("Open folder");

				int userSelection = fileChooser.showOpenDialog(parentFrame);

				if (userSelection == JFileChooser.APPROVE_OPTION) {
					filePath = fileChooser.getSelectedFile();
				}
				if (filePath == null) {
					browseField.setText(" ");
				} else {
					browseField.setText(filePath.toString());
				}
			}

		});
		this.getContentPane().add(browseButton, "cell 2 1");

		JLabel exportLabel = new JLabel("Export options");
		exportLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.getContentPane().add(exportLabel, "cell 1 2,growx,aligny center");

		excelCheckBox = new JCheckBox("Save to Excel");
		this.getContentPane().add(excelCheckBox, "cell 1 3");

		dbCheckBox = new JCheckBox("Save to Database");
		dbCheckBox.setSelected(true);
		this.getContentPane().add(dbCheckBox, "cell 1 4");

		JButton exportButton = new JButton("Export");
		exportButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (filePath != null) {


					if (dbCheckBox.isSelected() && excelCheckBox.isSelected()) {		//Om b�da checkboxarna �r iklickade
						selectSaveLocation();
						FileOpener FO = new FileOpener(filePath, saveFilePath);
						FO.sendToExcel();
						FO.sendToDB();
					} 
					else if (dbCheckBox.isSelected()			//Om endast Databas �r icheckad
							&& !excelCheckBox.isSelected()) {
						FileOpener FO = new FileOpener(filePath, saveFilePath);
						FO.sendToDB();
						
					}
					else if(!dbCheckBox.isSelected() && excelCheckBox.isSelected()){ //Om endast 
						selectSaveLocation();
						FileOpener FO = new FileOpener(filePath, saveFilePath);
						FO.sendToExcel();
		
					}

				} else {
					JOptionPane.showMessageDialog(null,
							"Must select a folder containing log files.",
							"inane error", JOptionPane.ERROR_MESSAGE);
				}

			}

		});
		this.getContentPane().add(exportButton, "cell 2 4");

		Component verticalStrut = Box.createVerticalStrut(20);
		this.getContentPane().add(verticalStrut, "cell 1 5");
		
		statusPanel = new JPanel();
		statusPanel.setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.getContentPane().add(statusPanel, "cell 0 6 3 1,grow, newline push");
		statusPanel.setLayout(new MigLayout("", "[grow]", "[]"));
		
		consoleLabel = new JLabel("Waiting for input");
		
		statusPanel.add(consoleLabel, "cell 0 0,growx");

		initMenubarSettings();
		this.setVisible(true);
		initTraySettings();
	}
	
	
	
	
	
	
	
	
	public void selectSaveLocation(){
		JFrame parentFrame = new JFrame();
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save file");

		int userSelection = fileChooser
				.showSaveDialog(parentFrame);

		if (userSelection == JFileChooser.APPROVE_OPTION)
			saveFilePath = fileChooser.getSelectedFile();
	}

	
	
	
	
	
	
	
	
	
	public void initTraySettings() {
		if (!SystemTray.isSupported()) {
			System.out.println("SystemTray not supported!");
			return;
		}

		sysTray = SystemTray.getSystemTray();
		paransImage = Toolkit.getDefaultToolkit().getImage(
				"./paransTrayIcon.gif");
		trayMenu = new PopupMenu();
		menuItem1 = new MenuItem("Open");
		menuItem2 = new MenuItem("Exit");

		menuItem1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				thisFrame.setVisible(true);
			}

		});

		menuItem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});

		trayMenu.add(menuItem1);
		trayMenu.add(menuItem2);

		trayIcon = new TrayIcon(paransImage, "FileOpener.", trayMenu);
		try {
			sysTray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println(e.getMessage());
		}

	}

	public void initMenubarSettings() {
		menuBar = new JMenuBar();
		statusMenu = new JMenu("Status");

		fileMenu = new JMenu("File");

		red = new ImageIcon("red.gif");
		green = new ImageIcon("green.gif");

		menuItemRunning = new JMenuItem("Stopped", red);
		menuItemRunning.addActionListener(new ActionListener() {

			
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable(){
					public void run(){
						try {
							while(running){
								System.out.println(new Date());
								UpdateWeather();
							Thread.sleep(1000*3600);
						
							}
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}
				});
				if (menuItemRunning.getText().equals("Stopped")) {
					menuItemRunning.setText("Running...");
					menuItemRunning.setIcon(green);
					running = true;
					
					
					t.start();
					//UpdateWeather();
					// K�R KOD!
				} else {
					menuItemRunning.setText("Stopped");
					menuItemRunning.setIcon(red);
					running = false;
					t.interrupt();
					setConsoleText("Waiting for input");
				}
				
				
			}

		});

		menuItemExit = new JMenuItem("Close program");
		menuItemExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);

			}

		});

		fileMenu.add(menuItemExit);
		statusMenu.add(menuItemRunning);

		menuBar.add(fileMenu);
		menuBar.add(statusMenu);

		this.setJMenuBar(menuBar);

	}

}
