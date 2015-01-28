import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;


public class ExcelIO {

	private static  String excelSheetName;
	private static  String fileName;
	private static  Map<String, Object[]> data;

	public static void main(String[] args) {
		Map<String, Object[]> test = new TreeMap<String, Object[]>();
		test.put("1", new Object[] {"Sensor ID", "Longitude", "Latitude", "File"});
		test.put("2", new Object[] {1, "98,464", "-15,54835", "LA.log"});

		if(test.isEmpty())
			System.out.println("Empty 1 (data)");
		else
			writeToExcel(test);
	}

	public static  void writeToExcel(Map<String, Object[]> inPutData){
		if(inPutData.isEmpty())
			System.out.println("Empty 2 (inPutData)");
		data.putAll(inPutData);
		writeToExcel();
	}

	private static  void writeToExcel(){
		excelSheetName= "Sheet";
		fileName = "EmilTestarLite";

		//skapar ett excel ark
		Workbook workbook = new HSSFWorkbook();

		//Skapar ett blad vid namn "excelSheetName"
		Sheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName((excelSheetName)));

		Set<String> keyset = data.keySet();

		//itererar igenom alla objekt i mappen och lägger in dem på varje rad
		int rownum = 0;
		for (String key : keyset)
		{
			Row row = sheet.createRow(rownum++);
			Object [] objArr = data.get(key);
			//itererar igenom objektets innehåll och lägger de i kolumner
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
				FileOutputStream output = new FileOutputStream(fileName+".xls");
				workbook.write(output);
				output.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}