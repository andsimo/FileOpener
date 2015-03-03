import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class ExcelIO.
 * Takes care of writing to excel
 */
public class ExcelIO {

	/** The excel sheet name. */
	private String excelSheetName;
	
	/**Data of the Locations. */
	private ArrayList<Location> data;
	
	/** The file. */
	private File file;
	
	/** The workbook. */
	private Workbook workbook;

	/**
	 * Instantiates a new excel IO.
	 *
	 * @param saveFilePath the save file path
	 */
	public ExcelIO(File saveFilePath) {
		file = saveFilePath;
	}

	/**
	 * Write to excel.
	 *
	 * @param locations, places where solar panels are installed
	 */
	public void writeToExcel(ArrayList<Location> locations) {
		data = locations;
		writeToExcel();
	}

	/**
	 * This method prints all data to an Excel file
	 */
	private void writeToExcel() {
		excelSheetName = "Locations";
		workbook = new HSSFWorkbook();

		// Skapar ett blad vid namn "excelSheetName"
		Sheet sheet = workbook.createSheet(WorkbookUtil
				.createSafeSheetName((excelSheetName)));

		Row rowInfo = sheet.createRow(0);
		Cell cellInfo0 = rowInfo.createCell(0);
		cellInfo0.setCellValue("ID");

		Cell cellInfo1 = rowInfo.createCell(1);
		cellInfo1.setCellValue("Latitude");

		Cell cellInfo2 = rowInfo.createCell(2);
		cellInfo2.setCellValue("Longitude");

		Cell cellInfo3 = rowInfo.createCell(3);
		cellInfo3.setCellValue("Produktionsdatum");

		Cell cellInfo4 = rowInfo.createCell(4);
		cellInfo4.setCellValue("Soltimmar");

		int rownum = 1;
		for (Location location : data) {
			for (Entry<String, String> entry : location.getFiles().entrySet()) {

				// skapar en ny rad för varje nyckel i hashmappen
				// Sedan hämtas Nyckeln som är ID för solfångaren
				// hårdkodat villka cellnummer de skrivs ut på
				Row row = sheet.createRow(rownum++);
				String fileName = entry.getKey();
				Cell cell = row.createCell(0);
				cell.setCellValue(fileName);

				// Hämtas latitude och den har hårdkodats till cellnummer 1
				Double latitude = location.getLat();
				Cell cell1 = row.createCell(1);
				cell1.setCellValue(latitude);

				// Hämtas longitude och den har hårdkodats till cellnummer 2
				Double longitude = location.getLong();
				Cell cell2 = row.createCell(2);
				cell2.setCellValue(longitude);

				String Produktionsdatum = entry.getValue(); // getProductionDate()
				Cell cell3 = row.createCell(3);
				cell3.setCellValue(Produktionsdatum);

				Cell cell4 = row.createCell(4);
				cell4.setCellValue("N/A");
			}
		}

				try {
					// System.out.println(""+file.toString());
					FileOutputStream output = new FileOutputStream(file
							+ ".xls");
					workbook.write(output);
					output.close();
					JOptionPane.showMessageDialog(null,
							"Excel file is now complete. \nAnd saved as: "
									+ file.getName() + ".xls", "Success",
							JOptionPane.PLAIN_MESSAGE);

				} catch (Exception e) {
					e.printStackTrace();
				}		
	}
}