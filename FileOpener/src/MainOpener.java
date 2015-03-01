import java.awt.AWTException;
import java.awt.Component;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -1219242683331411437L;
	private File filePath, saveFilePath;
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
	private JMenuItem menuItemRunning, menuItemExit, timeToNextUpdate;
	private ImageIcon red, green;
	private JPanel statusPanel;
	private JLabel consoleLabel;

	private volatile boolean running = false;
	private Runnable timeRunnable;
	private int time;
	private ScheduledExecutorService executor;

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


		initGUI();
		//UpdateWeather();
	}


	/**
	 * Sets the consoleLabel text.
	 * is a separate thread to write to consoleLabel , while other processes are running
	 * @param text 
	 */
	private void setConsoleText(final String text){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				consoleLabel.setText(text);
			}
		});
	}


	/**
	 * Update weather.
	 * Function is responsible for updating the weather in the database.
	 * Retrieves locations from the database
	 * Use this data to fetch weather from OpenWeatherMap
	 * Then it updates the weather in the database
	 * 
	 * Displays error messages when problems occur with connection to the database or OpenWeatherMap
	 * 
	 */
	private void updateWeather() {


		setConsoleText("Updating weather, please wait!");
		//this.setEnabled(false);							//---varför?

		WeatherCollector WC = new WeatherCollector();
		DBConnector DB = new DBConnector();
		ArrayList<Location> tempList = new ArrayList<>();
		try {
			tempList.addAll(DB.receiveFromDB());
		} catch (Exception e) {
			// If unable to establish a connection with the database error message appears
			setConsoleText("<html><font color='red'>An error occurred while attempting to connect to the database.</font></html>");
			JOptionPane
			.showMessageDialog(
					null,
					"An error occurred while attempting to connect to the database.",
					"Inane error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			this.setEnabled(true);
			return;
		}

		if(tempList.size() > 0){

			for(int i = 0; i < tempList.size(); i++){
				try {
					WC.getWeather(tempList.get(i));
					tempList.get(i).setWeatherTime(System.currentTimeMillis());
				} catch (IOException e) {
					// If unable to establish a connection with OpenWeatherMap database error message appears
					setConsoleText("<html><font color='red'>An error occurred while attempting to connect to OpenWeatherMap.</font></html>");
					JOptionPane
					.showMessageDialog(
							null,
							"An error occurred while attempting to connect to OpenWeatherMap.",
							"Inane error",
							JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
			try {
				DB.weatherUpdate(tempList);
			} catch (Exception e) {
				// If unable to establish a connection with the database error message appears
				setConsoleText("<html><font color='red'>An error occurred while attempting to connect to the database.</font></html>");
			JOptionPane
			.showMessageDialog(
					null,
					"An error occurred while attempting to connect to the database.",
					"Inane error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			}

			setConsoleText("Ready!");
		}
		else{
			JOptionPane
			.showMessageDialog(
					null,
					"Empty DB.",
					"Inane error",
					JOptionPane.ERROR_MESSAGE);
		}
		//this.setEnabled(true);
	}

	/*
	 * Skapar GUI med 2st labels som visar senast valda directory samt 2 knappar
	 * Browse(browse) och Open Files(openFiles). Browse skapar och ï¿½ppnar en
	 * JFileChooser (Directory Chooser) medan Open Files ropar pï¿½ metod i
	 * FileOpener.
	 */
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


					if (dbCheckBox.isSelected() && excelCheckBox.isSelected()) {		//Om bï¿½da checkboxarna ï¿½r iklickade
						selectSaveLocation();
						FileOpener FO = new FileOpener(filePath, saveFilePath);
						FO.sendToExcel();
						try {
							FO.sendToDB();
						} catch (Exception e) {
							setConsoleText("<html><font color='red'>An error occurred while attempting to connect to the database.</font></html>");
							JOptionPane
							.showMessageDialog(
									null,
									"An error occurred while attempting to connect to the database.",
									"Inane error",
									JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}
					} 
					else if (dbCheckBox.isSelected()			//Om endast Databas ï¿½r icheckad
							&& !excelCheckBox.isSelected()) {
						FileOpener FO = new FileOpener(filePath, saveFilePath);
						try {
							FO.sendToDB();
						} catch (Exception e) {
							setConsoleText("<html><font color='red'>An error occurred while attempting to connect to the database.</font></html>");
							JOptionPane
							.showMessageDialog(
									null,
									"An error occurred while attempting to connect to the database.",
									"Inane error",
									JOptionPane.ERROR_MESSAGE);
							e.printStackTrace();
						}

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

	/**
	 * Time counter.
	 * Counts down every second and prints the new time on timeToNextUpdate
	 */
	private void TimeCounter(){
		if(time == 0)
			time = 3600;
		int minutes = (time % 3600) / 60;
		int seconds = time-- % 60;	
		timeToNextUpdate.setText("Time to to next update: " + String.format("%02d:%02d", minutes, seconds));
	}
	public void initMenubarSettings() {
		menuBar = new JMenuBar();
		statusMenu = new JMenu("Status");

		fileMenu = new JMenu("File");

		red = new ImageIcon("red.gif");
		green = new ImageIcon("green.gif");
		
		timeRunnable = new Runnable() {
		    public void run() {
		    	TimeCounter();
		    }
		};
		
		timeToNextUpdate = new JMenuItem();
		timeToNextUpdate.setEnabled(false);
		
		menuItemRunning = new JMenuItem("Stopped", red);
		menuItemRunning.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable(){
					public void run(){
						try {
							while(running){
								updateWeather();
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
					time = 3600;
					statusMenu.add(timeToNextUpdate);

					executor = Executors.newScheduledThreadPool(1);
					executor.scheduleAtFixedRate(timeRunnable, 0, 1, TimeUnit.SECONDS);
					t.start();
					
					//UpdateWeather();
					// Kï¿½R KOD!
				} else {
					menuItemRunning.setText("Stopped");
					menuItemRunning.setIcon(red);
					running = false;
					t.interrupt();
					setConsoleText("Waiting for input");
					executor.shutdown();
					statusMenu.remove(timeToNextUpdate);
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
