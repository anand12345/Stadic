package com.resoneuronance.campus.web.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.resoneuronance.campus.web.bo.CollegeBO;
import com.resoneuronance.campus.web.domain.Department;

public class FileUtils {
	
	private static final int START_CELL_INDEX = 0;
	private static final int START_ROW_INDEX = 0;
	private static final int SHEET_INEDX = 0;

	public static String uploadExcel(InputStream is, CollegeBO collegeBo) {
		Workbook wb = null;
		String result = "";
		int collegeId = collegeBo.getCurrentCollege().getId();
		List<Department> departments = new ArrayList<Department>();
		try {
			wb = new XSSFWorkbook(is);
		} catch (Exception e) {
			try {
				wb = new HSSFWorkbook(is);
			} catch (Exception e2) {
			}
		}
		
		Sheet sheet = wb.getSheetAt(SHEET_INEDX);
		//Row row = sheet.getRow(START_ROW_INDEX);
		//System.out.println("Value:" + row.getCell(START_CELL_INDEX).getStringCellValue());
		Iterator<Row> rowIterator = sheet.iterator();
		int count= 0;
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            //For each row, iterate through each columns
            //Iterator<Cell> cellIterator = row.cellIterator();
            Cell departmentName = row.getCell(START_CELL_INDEX);
            System.out.println(departmentName + ":" + collegeId);
            Department department = new Department();
            department.setName(departmentName.getStringCellValue());
            department.setCollegeId(collegeId);
			departments.add(department);
			count++;
        }
        
        collegeBo.addDepartments(departments);
        
        result = count + " records uploaded successfully!";
        return result;
	}

}
