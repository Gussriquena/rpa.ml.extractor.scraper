package rpa.ml.extractor.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import rpa.ml.extractor.constants.PageEnum;
import rpa.ml.extractor.model.Product;
import rpa.ml.extractor.model.ProductOutput;

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
					if (cell.getCellType() == CellType.STRING && !cell.getStringCellValue().contains("PRODUTO_NOME")) {
						product.add(new Product(row.getRowNum(), cell.getStringCellValue()));
					}
				}
 			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return product;
	}
	
	public void writeOutputExcel(List<ProductOutput> producstOutput) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("All products");
			
			int rowNumber = 0;
			for (ProductOutput product : producstOutput) {
				Row row = sheet.createRow(rowNumber++);
				
				Cell cell = row.createCell(0);
				cell.setCellValue(product.getName());
				
				cell = row.createCell(1);
				cell.setCellValue(product.getPrice().toString());
				
				cell = row.createCell(2);
				cell.setCellValue(product.getSalesAmount());
			}
			
			FileOutputStream outputStream = new FileOutputStream(PageEnum.EXCEL_PRODUCTS_OUTPUT.getValue() + "products_output.xlsx");
			workbook.write(outputStream);
			workbook.close();
			
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	private String findExcel() {
		String filePath = PageEnum.EXCEL_PRODUCTS_INPUT.getValue();
		
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
