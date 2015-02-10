import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

public class ExcelIO {

	private String excelSheetName;
	private String excelFileName;
	private  Map<String, Coord> data;
	private File file;


	public ExcelIO(File saveFilePath) {
		file = saveFilePath;
	}
	public ExcelIO() {
	}

	public  void writeToExcel(Map<String, Coord> toWrite){
		data = toWrite;
		writeToExcel();
	}

	private void writeToExcel(){
		excelSheetName= "Locations";
		excelFileName = "TestFileName";

		//skapar ett excel ark
		Workbook workbook = new HSSFWorkbook();

		//Skapar ett blad vid namn "excelSheetName"
		Sheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName((excelSheetName)));

		Row rowInfo = sheet.createRow(0);
		Cell cellInfo0 = rowInfo.createCell(0);
		cellInfo0.setCellValue("ID");

		Cell cellInfo1 = rowInfo.createCell(1);
		cellInfo1.setCellValue("Latitude");

		Cell cellInfo2 = rowInfo.createCell(2);
		cellInfo2.setCellValue("Longitude");


		int rownum = 1;
		for(Entry<String, Coord> entry : data.entrySet()){

			// skapar en ny rad för varje nyckel i hashmappen
			// Sedan hämtas Nyckeln som är ID för solfångaren
			// hårdkodat villka cellnummer de skrivs ut på
			Row row = sheet.createRow(rownum++);
			String fileName = entry.getKey();
			Cell cell = row.createCell(0);
			cell.setCellValue(fileName);

			// Hämtas latitude och den har hårdkodats till cellnummer 1
			Double latitude = entry.getValue().getLat();
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(latitude);

			// Hämtas longitude och den har hårdkodats till cellnummer 2
			Double longitude = entry.getValue().getLong();
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(longitude);

		}
		try {
			//System.out.println(""+file.toString());
			FileOutputStream output = new FileOutputStream(file + ".xls");
			workbook.write(output);
			output.close();
			JOptionPane.showMessageDialog(null,
				    "Excel filen �r nu klar. \nOch sparad som : " + file.getName()+ ".xls","Success",JOptionPane.PLAIN_MESSAGE);
			
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
}