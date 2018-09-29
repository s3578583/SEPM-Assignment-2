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
	private String[] courseIDs = { "001", "002", "003", "004", "005" };

	// constructor
	public Course(String courseID, String courseName, int maxStudents, double coursePrice)
	{

		this.courseID = courseID;
		this.description = courseName;
		this.maxNum = maxStudents;
		this.cost = coursePrice;
	}

	// Writer to write details to text file
	public boolean writeUsingFileWriter(String courseID, String description, int maxNum, double cost)
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
			return false;
		}
		return true;

	}

	// delete line in text file
	public boolean deleteLine()
	{
		boolean exist = false;
		try
		{
			BufferedReader file = new BufferedReader(new FileReader("src/courseDetails.txt"));
			String line;
			String input = "";
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter course ID to change:");
			String checkCourseID = scan.nextLine();
			System.out.println("Enter new course cost:");
			cost = scan.nextDouble();
			while ((line = file.readLine()) != null)
			{
				// check for line with course ID
				if (line.contains(checkCourseID))
				{
					// read text file and assign line to variables for rewriting
					String[] checkCredentials = line.split("}");
					String[] jobSearch = checkCredentials[0].split(",");
					courseID = jobSearch[0].replace("{", "");
					if (checkCourseID.equals(courseID))
					{
						description = jobSearch[1];
						maxNum = Integer.parseInt(jobSearch[2]);
						line = "";
						exist = true;

					}

				}

				input += line + '\n';

			}
			if (!checkCourseID.equals(courseID))
			{
				System.out.println("Course does not exist!!!");
				exist = false;
				file.close();

			}

			if (exist == true)
			{
				// assign where to write new variables
				FileOutputStream File = new FileOutputStream("src/courseDetails.txt");
				File.write(input.getBytes());
				writeUsingFileWriter(courseID, description, maxNum, cost);
				file.close();
				File.close();
				return true;
			}

		} catch (Exception e)
		{
			System.out.println("Problem reading file.");
			return false;
		}
		return false;
	}

	public boolean viewCourseDetails()
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
			return false;
		}
		// catch exception if IOException
		catch (IOException ex)
		{
			System.out.println("Error reading file '" + fileName + "'");
			return false;

		}
		return true;

	}

	public boolean printCourseDetails()
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
			String courseIdDetails = "";
			boolean exist = false;
			do
			{
				System.out.println("Enter Course ID to see details");
				courseIdDetails = scan.nextLine();
				for (int i = 0; i < courseIDs.length; i++)
				{
					if (courseIdDetails.equals(courseIDs[i]))
					{
						exist = true;
						break;
					}

				}
				if (exist == false)
					System.out.println("Course ID does not exist, please try again\n");
			} while (exist == false);

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
					// if correct course, add total amount, and
					// print out student ID and cost
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
		return true;
	}

	public boolean printStudentCourseDetails(String checkID)
	{

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
			String studentID = null;
			int totalCourses = 0;
			String courseName = "";
			List<String> list = new ArrayList<String>();
			
			System.out.println("\nCourses Taken by " + checkID);
			// read through document while there is a new line in the file
			System.out
					.println("-------------------------------------------------------------------------------------\n");
			System.out.printf("%10s %30s", "Course ID", "Course Name\n");
			// System.out.println();
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

					if (courseID.equals("001"))
						courseName = "Italian Cooking";
					if (courseID.equals("002"))
						courseName = "Seafood Cooking";
					if (courseID.equals("003"))
						courseName = "Sewing";
					if (courseID.equals("004"))
						courseName = "Creative writing";
					if (courseID.equals("005"))
						courseName = "Business writing";

					// if correct course, add total amount, and
					// print out student ID and cost
					if (studentID.equals(checkID))
					{
						totalCourses++;
						System.out.format("%10s %30s  ", courseID, courseName);
						System.out.println();

					}

				}

			}

			bufferedReader.close();

			if (!studentID.equals(checkID))
				return false;

			else
			{
				System.out.println();
				System.out.format("%15s %25d  ", "Total Courses:", totalCourses);
				System.out.println();
				System.out.println(
						"-------------------------------------------------------------------------------------");
				System.out.println();
			}
		} catch (FileNotFoundException ex)
		{
			System.out.println("Unable to open file '" + fileName + "'");
			return false;
		}
		// catch exception if IOException
		catch (IOException ex)
		{
			System.out.println("Error reading file '" + fileName + "'");
			return false;
		}
		return true;
	}

	public boolean calculate()
	{

		for (int i = 0; i < courseIDs.length; i++)
		{

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
				String courseID = null;
				String courseName = "";

				List<String> list = new ArrayList<String>();

				double cost;
				double runningCost = 0;
				double profit = 0;
				double totalCost = 0;
				int numOfStudents = 0;

				while ((lineInput = bufferedReader.readLine()) != null)
				{
					if (lineInput.length() > 0)
					{
						list.add(lineInput);
						// array to split text on line to get user name and
						// password

						String[] checkCredentials = lineInput.split(" }");
						String[] individualRecord = checkCredentials[0].split(",");
						courseID = individualRecord[0].replace("{", "");
						// System.out.println("Course ID:" +courseID);

						if (courseIDs[i].equals(courseID))
						{
							cost = Double.parseDouble(individualRecord[2]);
							totalCost += cost;
							numOfStudents++;
							if (courseIDs[i] == "001")
							{
								courseName = "Italian Cooking";
								runningCost = 1000;
							}
							if (courseIDs[i] == "002")
							{
								courseName = "Seafood Cooking";
								runningCost = 1000;
							}
							if (courseIDs[i] == "003")
							{
								courseName = "Sewing";
								runningCost = 100 * numOfStudents;
							}
							if (courseIDs[i] == "004")
							{
								courseName = "Creative Writing";
								runningCost = 800;
							}
							if (courseIDs[i] == "005")
							{
								courseName = "Business Writing";
								runningCost = 600;
							}
							profit = totalCost - runningCost;
						}

					}
				}
				bufferedReader.close();
				System.out.println();
				System.out.format("%10s %5s %17s %15s %5s %10s %10.2f %10s %10.2f %10s %10.2f", "Course:", courseIDs[i],
						courseName, "Students:", numOfStudents, "Cost:", runningCost, "Income:", totalCost, "Profit:",
						profit);
				System.out.println();
				System.out.println(
						"----------------------------------------------------------------------------------------------------------------------------");
				System.out.println();
			}

			catch (FileNotFoundException ex)
			{
				System.out.println("Unable to open file '" + fileName + "'");
				return false;
			}
			// catch exception if IOException
			catch (IOException ex)
			{
				System.out.println("Error reading file '" + fileName + "'");
				return false;
			}
		}
		return true;
	}

}