package com.utility.nls;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelToMemory {
	
	private static Map<String, LanguagePresenter> masterData = null;
	
	static {
		Sheet sheet;
		try {
			sheet = loadExcel("src/MasterSheet.xlsx");
			masterData = load(sheet);
			System.out.println("Master data has been loaded.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static LanguagePresenter getLanguageValue(String key) {
		return masterData.get(key);
	}

	private static Sheet loadExcel(String path) throws Exception {
		Workbook workbook = WorkbookFactory.create(new File(path));
		return workbook.getSheetAt(0);
	}

	private static Map<String, LanguagePresenter> load(Sheet sheet) {
		Map<String, LanguagePresenter> languages = new HashMap<>();
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			String usEnglish = row.getCell(1).getStringCellValue();
			String canadianFrench = row.getCell(2).getStringCellValue();
			String french = row.getCell(3).getStringCellValue();
			String indonasian = row.getCell(4).getStringCellValue();
			String simplifiedChinese = row.getCell(5).getStringCellValue();
			String traditionalChinese = row.getCell(6).getStringCellValue();
			String maxicanSpenish = row.getCell(7).getStringCellValue();
			String vietnamese = row.getCell(8).getStringCellValue();
			String polish = row.getCell(9).getStringCellValue();

			LanguagePresenter language = new LanguagePresenter(usEnglish, canadianFrench, french, indonasian,
					simplifiedChinese, traditionalChinese, maxicanSpenish, vietnamese, polish);

			languages.put(row.getCell(0).getStringCellValue(), language);

		}
		return languages;
	}
}
