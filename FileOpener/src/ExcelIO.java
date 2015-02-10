import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

//Tim testar
public class ExcelIO {

	private static String excelSheetName;
	private static String excelFileName;
	private static Map<String, Coord> data;

	/*public static void main(String[] args) {
		data = new TreeMap<String, Coord>();
		TreeMap<String, Coord> tempData = new TreeMap<String, Coord>();
		tempData.put("1", new Coord(-85.232113,12.123142));
		writeToExcel(tempData);
	}
	 */
	public static void writeToExcel(Map<String, Coord> toWrite){
		data = toWrite;
		writeToExcel();
	}

	private static void writeToExcel(){
		excelSheetName= "Sheet";
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
			Row row = sheet.createRow(rownum++);
			String fileName = entry.getKey();
			Cell cell = row.createCell(0);
			cell.setCellValue(fileName);

			Double latitude = entry.getValue().getLat();
			Cell cell1 = row.createCell(1);
			cell1.setCellValue(latitude);

			Double longitude = entry.getValue().getLong();
			Cell cell2 = row.createCell(2);
			cell2.setCellValue(longitude);

			try {
				FileOutputStream output = new FileOutputStream(excelFileName+".xls");
				workbook.write(output);
				output.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}

		/*

		Set<String> keyset = data.keySet();

		//itererar igenom alla objekt i mappen och l�gger in dem p� varje rad



		//int rownum = 0;
		for (String key : keyset)
		{
			Row row = sheet.createRow(rownum++);
			Object [] objArr = data.get(key);
			//itererar igenom objektets inneh�ll och l�gger de i kolumner
			int cellnum = 0;
			for (Object obj : objArr)
			{
				Cell cell = row.createCell(cellnum++);
				if(obj instanceof String)
					cell.setCellValue((String)obj);
				else if(obj instanceof Integer)
					cell.setCellValue((Integer)obj);
			}

			try {
				FileOutputStream output = new FileOutputStream(excelFileName+".xls");
				workbook.write(output);
				output.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		 */
	}
}