package excelInputAndOutput;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelInteraction {
	private Logger logger = Logger.getLogger("flightReservation");
	Workbook workbook;
	FileInputStream inputstream;
	public Sheet getSheet(String filePath,String fileName) throws IOException{
		File file = new File(filePath+"\\"+fileName);
		inputstream = new FileInputStream(file);
		String fileExtension = fileName.substring(fileName.indexOf("."));
		if(fileExtension.equals(".xls")){
			workbook = new HSSFWorkbook(inputstream);
		}else if(fileExtension.equals(".xlsx")){
			workbook = new XSSFWorkbook(inputstream);
		}
		
		Sheet sheet = workbook.getSheet("TestData");
		
		return sheet;
		
	}
	
	
	public void closeInputStream() throws IOException{
		inputstream.close();
	}

	public void generateExcelReport(String excelOutput){
		try {
			FileOutputStream outputstream = new FileOutputStream(new File(excelOutput));
			workbook.write(outputstream);
			outputstream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Status -- Failed | Exception occurred while generating excel report : "+e.getMessage());
		}
	}

	public CellStyle getCellStyle(String status) {
		CellStyle style = workbook.createCellStyle();
		style.setBorderBottom(BorderStyle.THIN);
		style.setBorderTop(BorderStyle.THIN);
		style.setBorderLeft(BorderStyle.THIN);
		style.setBorderRight(BorderStyle.THIN);
		
		if(status.equals("Pass")){
			style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}else if(status.equals("Fail")){
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}else{
			
		}
		
		return style;
	}

}
