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
	
	private void help(){
		
	}

	public static void writeToExcel(){
		excelSheetName= "Sheet";
		excelFileName = "Test1";
		
	
		
		//skapar ett excel ark
		Workbook workbook = new HSSFWorkbook();

		//Skapar ett blad vid namn "excelSheetName"
		Sheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName((excelSheetName)));

		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] {"Sensor ID", "Longitude", "Latitude", "File"});
		data.put("2", new Object[] {1, "98,464", "-15,54835", ""});

		Set<String> keyset = data.keySet();
		
		//itererar igenom alla objekt i mappen och lï¿½gger in dem pï¿½ varje rad
		int rownum = 0;
		for (String key : keyset)
		{
			Row row = sheet.createRow(rownum++);
			Object [] objArr = data.get(key);
			//itererar igenom objektets innehï¿½ll och lï¿½gger de i kolumner
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
	}
	
	
	public void writeManyToExcel(HashMap<String, Coord> locations){
		excelSheetName= "Sheet";
		excelFileName = "Test2";
		
	
		
		//skapar ett excel ark
		Workbook workbook = new HSSFWorkbook();

		//Skapar ett blad vid namn "excelSheetName"
		Sheet sheet = workbook.createSheet(WorkbookUtil.createSafeSheetName((excelSheetName)));

		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		data.put("1", new Object[] {1, "Longitude", "Latitude", "File"});


		int i = 1;
		for(Entry<String, Coord> entry : locations.entrySet()){
			String fileName = entry.getKey();
			Double latitude = entry.getValue().getLat();
			Double longitude = entry.getValue().getLong();
			
			data.put(""+i, new Object[] {i, longitude, latitude, fileName});	//Får ej ordning på det här. Kanske Emil kan kolla på? 
			i++;
		}
		
		Set<String> keyset = data.keySet();
		
		//itererar igenom alla objekt i mappen och lï¿½gger in dem pï¿½ varje rad
		int rownum = 0;
		for (String key : keyset)
		{
			Row row = sheet.createRow(rownum++);
			Object [] objArr = data.get(key);
			//itererar igenom objektets innhåll och lägger de i kolumner
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
	}
}