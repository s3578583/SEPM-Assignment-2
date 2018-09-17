package courseManagementSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Student
{

	private String studentID;
	private  String firstName;
	private  String lastName;
	private String address;
	private String email;
	private String phone;
	
	
	public Student(String studentID, String firstName, String lastName, String address, String email, String phone)
	{
		
		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}


	public void addStudent() {
		
		// inserting new staff details
				@SuppressWarnings("resource")
				Scanner scan = new Scanner(System.in);

				System.out.println("Enter Student ID:");
				studentID = scan.nextLine();
				System.out.println("Enter First Name:");
				firstName = scan.nextLine();
				System.out.println("Enter Last Name:");
				lastName = scan.nextLine();
				System.out.println("Enter residential address:");
				address = scan.nextLine();
				System.out.println("Enter email address:");
				email = scan.nextLine();
				System.out.println("Enter phone number:");
				phone = scan.nextLine();
				
				
				try
				{
					writeUsingFileWriter(studentID, firstName, lastName, address,email,phone);
				} catch (FileNotFoundException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("New student successfully added");
		
	}
	
	public void writeUsingFileWriter(String studentID, String firstName, String lastName,String address,
									String email,String phone)
			throws FileNotFoundException
	{
		File file = new File(System.getProperty("user.dir"));

		String path = file.getAbsolutePath()+"\\src\\studentDetails.txt";
		File created = new File(path);
		try
		{
			if (created.exists() == false)
			{
				System.out.println("We had to make a new file.");
				created.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(created, true));

			out.append("{"+studentID + "," + firstName + "," + lastName + "," + address + "," + email +"," + 
					phone +",}\n");
			out.close();
		} catch (IOException e)
		{
			System.out.println("COULD NOT ADD RECORD!!");
		}

	}
	
	
	public void viewStudentDetails(String checkJob)
	{
		File file = new File(System.getProperty("user.dir"));

		String path = file.getAbsolutePath()+"\\src\\studentDetails.txt";
		String fileName = path;
		try
		{
			// file reader to read the fileName variable above
			FileReader filereader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(filereader);
			// variable for lines in the file
			String lineInput;

			studentID = null;

			List<String> list = new ArrayList<String>();
			// read through document while there is a new line in the file
			while ((lineInput = bufferedReader.readLine()) != null)
			{
				list.add(lineInput);
				// array to split text on line to get user name and password
				String[] checkCredentials = lineInput.split(" ");
				String[] jobSearch = checkCredentials[0].split(",");
				studentID = jobSearch[0].replace("{", "");
				if (studentID.equals(checkJob))
				{
					System.out.println("This is working!!");;
					break;

				}

				// condition to check if user exists in the file

			}
			if (!studentID.equals(checkJob))
			{
				System.out.println("Sorry, please enter a correct student ID!!");

			}
			// close buffered reader
			bufferedReader.close();
		} catch (FileNotFoundException ex)
		{
			System.out.println("Unable to open file '" + fileName + "'");
		}
		// catch exception if IOException
		catch (IOException ex)
		{
			System.out.println("Error reading file '" + fileName + "'");

		}
	}
	
	
	
	
	
	
	
	
	
}
