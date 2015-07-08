package com.resoneuronance.campus.web.bo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.resoneuronance.campus.web.domain.Notification;
import com.resoneuronance.campus.web.domain.Teacher;

public interface TeacherBO {
	
	boolean login(Teacher teacher,String collegeName);
	boolean registerTeacher(Teacher teacher, String collegeName);
	com.resoneuronance.campus.web.bo.domain.Teacher getCurrentTeacher();
	void tagDepartmentToTeacher(com.resoneuronance.campus.web.bo.domain.Teacher teacher);
	void notify(Notification notification, MultipartFile file) throws IOException;
	void deleteNotification(int id);
	InputStream downloadDocument(int id) throws FileNotFoundException;
	void loadDepartment(int id);
}
