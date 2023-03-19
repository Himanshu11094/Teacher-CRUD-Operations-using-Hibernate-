package service;

import java.util.List;

import database.TeacherDatabase;
import model.Teacher;

public class TeacherService {
	
private TeacherDatabase teacherDatabase;
	
	public TeacherService(TeacherDatabase teacherDatabase)
	{
		this.teacherDatabase = teacherDatabase;
	}
	
	public List<Teacher> getAllTeachers() throws Exception
	{
		if(teacherDatabase.getAllTeachers().size() == 0)
			throw new Exception("No teachers are registered yet");
		
		return this.teacherDatabase.getAllTeachers();
	}
	
	public boolean registerTeacher(Teacher teacher) throws Exception
	{
		if(teacher.getEmail() == null || teacher.getEmail().isEmpty())
		{
			throw new Exception("email cannot be empty");
		}
		try {teacherDatabase.insertTeacher(teacher);}
		
		catch (Exception e)
		{
			e.printStackTrace();  
			return false;
		}
		return true;
	}
	
	public Teacher getTeacherByEmail(String email) throws Exception
	{
		if(email==null || email.isEmpty())
		{
			throw new Exception("email can't be empty or null");
		}
		 Teacher teacher = this.teacherDatabase.getTeacherByEmail(email);
		 
		 if(teacher==null)
			 throw new Exception("Teacher with email "+ email + " doesn't exist");
		 
		 return teacher;
	}
	
	public boolean validCredentials(String email, String password) throws Exception
	{
		if(email == null || email.isEmpty())
			throw new Exception("email cannot be empty or null");
		
		return this.teacherDatabase.login(email, password);
	}

}
