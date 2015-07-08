package com.resoneuronance.campus.web.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.resoneuronance.campus.web.bo.CollegeBO;
import com.resoneuronance.campus.web.domain.Department;
import com.resoneuronance.campus.web.domain.Student;
import com.resoneuronance.campus.web.domain.Teacher;

public class FileUtils {
	
	private static final String ROOT_FOLDER = "src/main/resources/";
	private static final int START_CELL_INDEX = 0;
	private static final int START_ROW_INDEX = 0;
	private static final int SHEET_INEDX = 0;

	public static void  uploadTeacherDocument(MultipartFile file,com.resoneuronance.campus.web.bo.domain.Teacher teacher) throws IOException {
		if(file.isEmpty()) {
			return;
		}
		byte[] bytes = file.getBytes();
		if(teacher.getCollege() == null) {
			return;
		}
		String dirPath =  ROOT_FOLDER +  teacher.getCollege().getId() + "/" + teacher.getId();
		File dir = new File(dirPath);
		dir.mkdirs();
		String documentPath = dirPath +  "/" + file.getOriginalFilename();
		File document = new File(documentPath);
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(document));
		stream.write(bytes);
		stream.close();
		teacher.getCurrentNotification().setFilePath(documentPath);
	}
	
	
	public static String uploadDepartmentsExcel(InputStream is, CollegeBO collegeBo) {
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
            department.setName(getCellValue(departmentName));
            department.setCollegeId(collegeId);
			departments.add(department);
			count++;
        }
        
        collegeBo.addDepartments(departments);
        
        result = count + " records uploaded successfully!";
        return result;
	}
	
	public static String uploadTeachersExcel(InputStream is, CollegeBO collegeBo) {
		Workbook wb = null;
		String result = "";
		int collegeId = collegeBo.getCurrentCollege().getId();
		List<Teacher> teachers = new ArrayList<Teacher>();
		try {
			wb = new XSSFWorkbook(is);
		} catch (Exception e) {
			try {
				wb = new HSSFWorkbook(is);
			} catch (Exception e2) {
			}
		}
		
		Sheet sheet = wb.getSheetAt(SHEET_INEDX);
		Iterator<Row> rowIterator = sheet.iterator();
		int count= 0;
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell teacherName = row.getCell(START_CELL_INDEX);
            Cell teacherEmail = row.getCell(START_CELL_INDEX +1);
            System.out.println(teacherName + ":" + collegeId);
            Teacher teacher = new Teacher();
            teacher.setName(getCellValue(teacherName));
            teacher.setEmail(getCellValue(teacherEmail));
            teacher.setCollegeId(collegeId);
			teachers.add(teacher);
			count++;
        }
        
        collegeBo.addTeachers(teachers);
        
        result = count + " records uploaded successfully!";
        return result;
	}
	
	public static String uploadStudentsExcel(InputStream is, CollegeBO collegeBo) {
		Workbook wb = null;
		String result = "";
		int collegeId = collegeBo.getCurrentCollege().getId();
		List<Student> students = new ArrayList<Student>();
		try {
			wb = new XSSFWorkbook(is);
		} catch (Exception e) {
			try {
				wb = new HSSFWorkbook(is);
			} catch (Exception e2) {
			}
		}
		
		Sheet sheet = wb.getSheetAt(SHEET_INEDX);
		Iterator<Row> rowIterator = sheet.iterator();
		int count= 0;
        while(rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Cell studentName = row.getCell(START_CELL_INDEX);
            Cell studentEmail = row.getCell(START_CELL_INDEX +1);
            System.out.println(studentName + ":" + collegeId);
            Student student = new Student();
            student.setName(getCellValue(studentName));
            student.setEmail(getCellValue(studentEmail));
            student.setCollegeId(collegeId);
			students.add(student);
			count++;
        }
        
        collegeBo.addStudents(students);
        
        result = count + " records uploaded successfully!";
        return result;
	}

	private static String getCellValue(Cell teacherName) {
		if(StringUtils.isNotBlank(teacherName.getStringCellValue()))
			return teacherName.getStringCellValue();
		else
			return "";
	}

}
