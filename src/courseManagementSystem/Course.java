package courseManagementSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Course
{
	private String courseID;
	private String description;
	private int maxNum;
	private double cost;

	// constructor
	public Course(String courseID, String courseName, int maxStudents, double coursePrice)
	{

		this.courseID = courseID;
		this.description = courseName;
		this.maxNum = maxStudents;
		this.cost = coursePrice;
	}

	// Writer to write details to text file
	public void writeUsingFileWriter(String courseID, String description, int maxNum, double cost)
			throws FileNotFoundException
	{
		// find users current directory
		File file = new File(System.getProperty("user.dir"));
		// then access the course details text file
		String path = file.getAbsolutePath() + "\\src\\courseDetails.txt";
		File created = new File(path);
		try
		{
			if (created.exists() == false)
			{
				System.out.println("We had to make a new file.");
				created.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(created, true));
			System.out.println("Record Successfully Updated!!");
			out.append("{" + courseID + "," + description + "," + maxNum + "," + cost + ",}\n");
			out.close();
		} catch (IOException e)
		{
			System.out.println("COULD NOT UPDATE RECORD!!");
		}

	}

	// delete line in text file
	public void deleteLine()
	{
		try
		{
			BufferedReader file = new BufferedReader(new FileReader("src/courseDetails.txt"));
			String line;
			String input = "";
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter course ID to change:");
			courseID = scan.nextLine();
			System.out.println("Enter new course cost:");
			cost = scan.nextDouble();
			while ((line = file.readLine()) != null)
			{
				// check for line with course ID
				if (line.contains(courseID))
				{
					// read text file and assign line to variables for rewriting
					String[] checkCredentials = line.split("}");
					String[] jobSearch = checkCredentials[0].split(",");
					courseID = jobSearch[0].replace("{", "");
					description = jobSearch[1];
					maxNum = Integer.parseInt(jobSearch[2]);
					line = "";
				}
				input += line + '\n';
			}

			// assign where to write new variables
			FileOutputStream File = new FileOutputStream("src/courseDetails.txt");
			File.write(input.getBytes());
			writeUsingFileWriter(courseID, description, maxNum, cost);
			file.close();
			File.close();
		} catch (Exception e)
		{
			System.out.println("Problem reading file.");
		}
	}

	public void viewCourseDetails()
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		File file = new File(System.getProperty("user.dir"));
		String path = file.getAbsolutePath() + "\\src\\courseDetails.txt";
		String fileName = path;
		try
		{
			// file reader to read the fileName variable above
			FileReader filereader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(filereader);
			// variable for lines in the file
			String lineInput;

			List<String> list = new ArrayList<String>();
			// read through document while there is a new line in the file
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.printf("%10s %30s %20s %10s", "Course ID", "Course Name", "Max Slots", "Price $");
			System.out.println();
			System.out.println("-------------------------------------------------------------------------------------");
			while ((lineInput = bufferedReader.readLine()) != null)
			{
				if (lineInput.length() > 0)
				{
					list.add(lineInput);
					// array to split text on line to get user name and password
					String[] checkCredentials = lineInput.split(" }");
					String[] individualRecord = checkCredentials[0].split(",");
					courseID = individualRecord[0].replace("{", "");
					description = individualRecord[1];

					maxNum = Integer.parseInt(individualRecord[2]);
					cost = Double.parseDouble(individualRecord[3]);

					System.out.format("%10s %30s %20s %10.2f  ", courseID, description, maxNum, cost);
					System.out.println();

				}
			}
			System.out.println("-------------------------------------------------------------------------------------");
			// close buffered reader
			System.out.print("1. Edit course fee\n2. View Specific Course Numbers\n3. Back to Menu\n");
			int input = scan.nextInt();
			if (input == 1)
			{
				deleteLine();
			}
			else if (input == 2)
			{
				printCourseDetails();
			}
			else if (input <= 0 || input > 3)
			{
				System.out.println("Invalid input");
				viewCourseDetails();
			}
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

	public void printCourseDetails()
	{

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		File file = new File(System.getProperty("user.dir"));
		String path = file.getAbsolutePath() + "\\src\\student_course_details.txt";
		String fileName = path;
		try
		{
			// file reader to read the fileName variable above
			FileReader filereader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(filereader);
			// variable for lines in the file
			String lineInput;
			System.out.println("Enter Course ID to see details");
			String courseIdDetails = scan.nextLine();
			String studentID = null;
			double cost = 0;
			double totalCost = 0;
			List<String> list = new ArrayList<String>();
			// read through document while there is a new line in the file
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.printf("%10s %30s", "Student ID", "Fee Paid $");
			System.out.println();
			System.out.println("-------------------------------------------------------------------------------------");
			while ((lineInput = bufferedReader.readLine()) != null)
			{
				if (lineInput.length() > 0)
				{
					list.add(lineInput);
					// array to split text on line to get user name and password
					String[] checkCredentials = lineInput.split(" }");
					String[] individualRecord = checkCredentials[0].split(",");
					courseID = individualRecord[0].replace("{", "");
					studentID = individualRecord[1];
					cost = Double.parseDouble(individualRecord[2]);
					//if correct course, add total amount, and 
					//print out student ID and cost
					if (courseID.equals(courseIdDetails))
					{
						totalCost += cost;
						System.out.format("%10s %30.2f  ", studentID, cost);
						System.out.println();
					}
				}
			}
			System.out.println();
			System.out.format("%15s %25.2f  ", "Total Amount $:", totalCost);
			System.out.println();
			System.out.println("-------------------------------------------------------------------------------------");
			System.out.println();
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
