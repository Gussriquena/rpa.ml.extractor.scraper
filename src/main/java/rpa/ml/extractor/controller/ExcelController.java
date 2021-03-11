package rpa.ml.extractor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import rpa.ml.extractor.constants.PageEnum;
import rpa.ml.extractor.model.Product;

public class ExcelController {

	private static Logger log = Logger.getLogger(ExcelController.class);
	
	public List<Product> readRowsExcel() {
		List<Product> product = new ArrayList<>();
		
		File inputFile = new File(findExcel());
		try (FileInputStream fileInputStream = new FileInputStream(inputFile)){
			XSSFWorkbook fileWorkbook = new XSSFWorkbook(fileInputStream);
			Sheet sheet = fileWorkbook.getSheetAt(0);
			
			Iterator<Row> iterator = sheet.iterator();
			
			while(iterator.hasNext()) {
				Row row = iterator.next();
				Iterator<Cell> cellIterator = row.iterator();
				
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getCellType() == CellType.STRING) {
						product.add(new Product(row.getRowNum(), cell.getStringCellValue()));
					}
				}
 			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return product;
	}
	
	public void writeOutputExcel() {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private String findExcel() {
		String filePath = PageEnum.EXCEL_PRODUTOS_INPUT.getValue();
		
		try {
			File filesInput = new File(filePath);
			String[] files = filesInput.list();
			for (String file : files) {
				if (file.contains(".xlsx")) {
					filePath = filePath + file;
				} 
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return filePath;
	}
	
}
