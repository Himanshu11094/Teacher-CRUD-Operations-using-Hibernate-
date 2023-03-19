package ui;

import java.util.Scanner;

import org.hibernate.SessionFactory;

import service.TeacherService;
import database.HibernateConfig;
import database.TeacherDatabase;
 
import model.Teacher;


public class TeacherMenu {
	
	public static void adminMenu()
	{
		System.out.println("Select Option\n"+
	                       "1: See all Teachers\n"+  
				           "2: Fetch Teacher by email\n"+
	                       "3: LogOff\n");		
	}
	
	public static void teacherMenu()
	{
		System.out.println("Select Option\n"+
	                        "1: Show Profile\n"+  
				            "2: Change Password\n"+
				            "3: Edit Profile\n"+
				            "4: Delete Profile\n"+
	                        "5: LogOff\n");		
	}

	public static void main(String[] args) {
		
		TeacherDatabase teacherDatabase = new TeacherDatabase();
		TeacherService teacherService = new TeacherService(teacherDatabase);
		
		String email, firstName, lastName, password, id;
		int choice;
		boolean flag=true;
		
		Scanner sc = new Scanner(System.in);
		
		do
		{
			// Home Page
			System.out.println("Select Operation to perform: \n" + 
	                 "1: Login\n" + 
			         "2: Register Teacher \n"+
	                 "3: EXIT");
			
			choice = sc.nextInt();
			
			switch(choice)
			{
			
			case 1: 
						
				System.out.println("enter the email \n");
				email = sc.next();
				System.out.println("enter the password\n");
				password = sc.next();
				
				if(email.equals("admin@gmail.com") && password.equals("admin"))
				{
					adminDashboard(sc, teacherService);
				}
				
				else
				{
					try {
						if(teacherService.validCredentials(email, password))
							teacherDashboard(sc, teacherService, email, teacherDatabase);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				
				break;
				
			case 2: System.out.println("Register the Teacher");
					registration(sc, teacherService);
					break;
			
			case 3: System.out.println("Exiting the application");
			        flag = !flag;    
			        break;
			    
				
			default: System.out.println("enter the valid input");
			
			}
			
			
		}while(flag);
	

	}
	
	public static void adminDashboard(Scanner sc, TeacherService teacherService)
	{
		boolean flag =true;
		
		do 
		{
			System.out.println("\n************ Admin Dashboard************ \n");
			adminMenu();
			int choice = sc.nextInt();
			
			switch(choice)
			{
			case 1:   
				try {
					for(Teacher teacher: teacherService.getAllTeachers())
					{
						System.out.println(teacher);
					}
					break;
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				break;
				
			case 2:
				System.out.println("enter the email of teacher");
				String email = sc.next();
				
				try {
					Teacher teacher =  teacherService.getTeacherByEmail(email);
					System.out.println("First Name: " + teacher.getFirstName()+
					                   "\n Last Name: "+ teacher.getLastName() +
					                   "\n id: "+ teacher.getId()+
					                   "\n email: "+ teacher.getEmail());
				}
				catch(Exception e)
				{e.printStackTrace();}
				break;
				
			case 3: System.out.println("Logging Off...");
			        flag = !flag;
			        break;
			        
				
			default: System.out.println("Wrong choice");
					
			
			}
			
		} while(flag);

	}
	
	
	public static void teacherDashboard(Scanner sc, TeacherService teacherService, String email, TeacherDatabase  teacherDatabase)
	{
		boolean flag = true;
		
		do 
		{
			System.out.println("\n************ Teacher Dashboard************ \n");
			teacherMenu();
			int choice = sc.nextInt();
			
			switch(choice)
			{
			
			case 1: try {
				Teacher teacher =  teacherService.getTeacherByEmail(email);
				System.out.println("First Name: " + teacher.getFirstName()+
				                   "\n Last Name: "+ teacher.getLastName() +
				                   "\n id: "+ teacher.getId()+
				                   "\n email: "+ teacher.getEmail());
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
			break;
			
			case 2: 
				try {
					Teacher teacher= teacherService.getTeacherByEmail(email);
					
					 
						System.out.println("Enter the new Password...");
						String newPassword = sc.next();
						teacher.setPassword(newPassword);
						teacherDatabase.updateTeacher(teacher);
					 

						System.out.println("Password has been updated...");
						break;
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
				
			case 3:   email = editProfile(sc, teacherService, email, teacherDatabase);
					  break;
					  
			case 4: try {
				Teacher teacher =  teacherService.getTeacherByEmail(email);
				
				System.out.println("\nPress 1 to delete the profile..."+ 
									"\n 0 to exit...");
				int num = sc.nextInt();
				if(num==1)
				{
					teacherDatabase.deleteTeacher(teacher);
					System.out.println("Profile has been deleted...");
				}				
				else break;
				
				break;
			}
			catch(Exception e)
			{e.printStackTrace();}
			break;
			
			case 5: System.out.println("Logging Off...");
			        flag = !flag;
			        break;
				
			default: System.out.println("Wrong choice");
			}
			
			
		}while(flag);
		
	}
	
	public static String editProfile(Scanner sc, TeacherService teacherService, String email, TeacherDatabase teacherDatabase)
	{
		boolean flag=true;
		
		do {
			System.out.println("\nSelect Option\n"+
                    "1: Edit email\n"+  
		            "2: Edit First Name\n"+
		            "3: Edit Last Name\n"+
                    "4: EXIT\n");
			
			int choice = sc.nextInt();
			
			switch(choice)
			{
			case 1:
				
				try {
					Teacher teacher = teacherService.getTeacherByEmail(email);
					
					System.out.println("Old Email: " + teacher.getEmail());
					System.out.println("Enter the new email to update: ");
					String newEmail = sc.next(); 
					teacher.setEmail(newEmail);
					teacherDatabase.updateTeacher(teacher);
					System.out.println("Email has been updated...");
					email = newEmail;
					break;
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
			case 2: 
				try {
					Teacher teacher = teacherService.getTeacherByEmail(email);
					
					System.out.println("Old First Name: " + teacher.getFirstName());
					System.out.println("Enter the new First Name to update: ");
					teacher.setFirstName(sc.next());
					teacherDatabase.updateTeacher(teacher);
					System.out.println("First Name has been updated...");
					break;
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				
				
			case 3:
				try {
					Teacher teacher = teacherService.getTeacherByEmail(email);
					
					System.out.println("Old Last Name: " + teacher.getLastName());
					System.out.println("Enter the new Last Name to update: ");
					teacher.setLastName(sc.next());
					teacherDatabase.updateTeacher(teacher);
					System.out.println("Last Name has been updated...");
					break;
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			
				
			case 4: System.out.println("Exiting...");
	                flag = !flag;
	                break;
				
			default: System.out.println("Wrong choice");
				
			}
			
		}while(flag);
		
		return email;
	}
	
	
	public static void registration(Scanner sc, TeacherService teacherService)
	{
		System.out.println("Enter the email: ");
		String email = sc.next();
		System.out.println("Enter the Password: ");
		String password = sc.next();
		System.out.println("Enter the First Name: ");
		String firstName = sc.next();
		System.out.println("Enter the Last Name: ");
		String lastName = sc.next();
		
		
		Teacher teacher = new Teacher(firstName, lastName, email, password);
		
		try {
			if(teacherService.registerTeacher(teacher))
				System.out.println("registration completed...");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
