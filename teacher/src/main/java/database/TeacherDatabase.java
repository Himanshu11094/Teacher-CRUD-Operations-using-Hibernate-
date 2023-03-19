package database;

import java.util.List;
import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import model.Teacher;


public class TeacherDatabase {
	
	
	private static SessionFactory factory = HibernateConfig.getSessionFactory();
	
	public static void insertTeacher(Teacher teacher)
	{
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.persist(teacher);
		tx.commit();
		session.close();	 
	}
	
	public static void updateTeacher(Teacher teacher)
	{
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.update(teacher);
		tx.commit();
		session.close();	 
	}
	
	public Teacher getTeacherByEmail(String email)
	{
		Session session = factory.openSession();
		String hql = "from Teacher where email= :x";
		
    	//Teacher teacher= session.get(Teacher.class, id);
		
    	Query q= session.createQuery(hql);
    	q.setParameter("x", email);
    	
    	List<Teacher> list = q.list();
    	
    	for(Teacher t: list)
    	{
    		if(t.getEmail().equals(email)) return t;
    	}
    	
    	session.close();
    	return null;
	}
	
	public void deleteTeacher(Teacher teacher)
	{
		Session session = factory.openSession();
    	Transaction tx = session.beginTransaction();
    	session.remove(teacher);
    	tx.commit();
    	session.close();
	}
	
	 public static List<Teacher> getAllTeachers()
	    {
	    	Session session = factory.openSession();
	    	List<Teacher> teachers= session.createQuery("select a from Teacher a", Teacher.class).getResultList();
	    	session.close();
	    	return teachers;
	    }
	 
	 public boolean login(String email, String pasword)
	 {
		 List<Teacher> teachers = getAllTeachers();
		 
		 for(Teacher t: teachers)
		 {
			 if(t.getEmail().equals(email))
			 {
				 if(t.getPassword().equals(pasword))
					 return true;
			 }
		 }
		 
		 return false;
	 }

}
