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

public class Student
{

	private String studentID;
	private String firstName;
	private String lastName;
	private String address;
	private String email;
	private String phone;

	boolean exist = false;

	public Student(String studentID, String firstName, String lastName, String address, String email, String phone)
	{

		this.studentID = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.email = email;
		this.phone = phone;
	}

	public boolean addStudent()
	{

		// inserting new staff details

		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		do
		{
			System.out.println("Enter Student ID:");
			studentID = scan.nextLine();
			viewStudentDetails(studentID, 3);
		} while (exist == true);
		if (exist == false)
		{
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
				writeUsingFileWriter(studentID, firstName, lastName, address, email, phone);
			} catch (FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("New student successfully added");

		}
		return true;

	}

	public void writeUsingFileWriter(String studentIDs, String firstName, String lastName, String address, String email,
			String phone) throws FileNotFoundException
	{
		File file = new File(System.getProperty("user.dir"));
		String path = file.getAbsolutePath() + "\\src\\studentDetails.txt";
		File created = new File(path);
		try
		{
			if (created.exists() == false)
			{
				System.out.println("We had to make a new file.");
				created.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(created, true));

			out.append("{" + studentIDs + "," + firstName + "," + lastName + "," + address + "," + email + "," + phone
					+ ",}\n");
			out.close();
		} catch (IOException e)
		{
			System.out.println("COULD NOT ADD RECORD!!");
		}
	}

	public boolean viewStudentDetails(String checkStudentID, int selection)
	{
		File file = new File(System.getProperty("user.dir"));
		String path = file.getAbsolutePath() + "\\src\\studentDetails.txt";
		String fileName = path;

		try
		{
			// file reader to read the fileName variable above
			FileReader filereader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(filereader);
			// variable for lines in the file
			String lineInput;
			String printstudentID = null;

			List<String> list = new ArrayList<String>();
			// read through document while there is a new line in the file
			while ((lineInput = bufferedReader.readLine()) != null)
			{
				if (lineInput.length() > 0)
				{
					list.add(lineInput);
					// array to split text on line to get user name and password
					String[] checkCredentials = lineInput.split("}");
					String[] jobSearch = checkCredentials[0].split(",");
					printstudentID = jobSearch[0].replace("{", "");

					firstName = jobSearch[1];
					lastName = jobSearch[2];
					address = jobSearch[3];
					email = jobSearch[4];
					phone = jobSearch[5];
					// depending on which int value passed in, do a certain
					// logic method
					if (selection == 1)
					{
						if (printstudentID.equals(checkStudentID))
						{
							for (int i = 0; i < jobSearch.length; i++)
							{
								jobSearch[i] = jobSearch[i].replaceAll("[\\[\\](){}]", "");
							}
							studentID = printstudentID;
							System.out.println("");
							System.out.println(
									"-------------------------------------------------------------------------------------"
											+ "---------------");
							System.out.printf("%10s %15s %15s %20s %20s %15s", "Student ID", "First Name", "Last Name",
									"Address", "Email", "Phone");
							System.out.println();
							System.out.println(
									"-------------------------------------------------------------------------------------"
											+ "---------------");

							System.out.format("%10s %15s %15s %20s %20s %15s", studentID, firstName, lastName, address,
									email, phone);
							System.out.println();
							System.out.println(
									"-------------------------------------------------------------------------------------"
											+ "---------------");
							exist = true;
							Course crs1 = new Course("", "", 0, 2);
							crs1.printStudentCourseDetails(studentID);
							System.out.println(
									"-------------------------------------------------------------------------------------"
											+ "---------------");
							break;

						}

					}

					// depending on which int value passed in, do a certain
					// logic method
					else if (selection == 2)
					{
						@SuppressWarnings("resource")
						Scanner scan1 = new Scanner(System.in);
						// check student is the correct student

						viewStudentDetails(checkStudentID, 1);

						if (exist == true)
						{
							System.out.println("Correct student? ");
							System.out.println("1 - Yes, 2 - No, 0 - Quit");
							int confirm = scan1.nextInt();

							// depending on option do certain action
							if (confirm == 1)
							{
								Enrolment enroll = new Enrolment();
								@SuppressWarnings("resource")
								Scanner scan2 = new Scanner(System.in);
								System.out.println("Please enter course ID to enrol student in:");
								String courseEnrolID = scan2.nextLine();
								enroll.setCoursePrice(courseEnrolID, checkStudentID);

								break;
							}

							if (confirm == 2)
							{
								// @SuppressWarnings("resource")
								// Scanner scan2 = new Scanner(System.in);
								System.out.println("Please enter student ID to enrol");
								String enrolID = scan1.nextLine();
								// enrol a specific student into a course
								viewStudentDetails(enrolID, 2);
								break;
							}
						}
						else
						{

							System.out.println("Student record does not exist");
							break;
						}

					}

					else if (selection == 3)
					{
						// if ID exists, do not continue
						if (printstudentID.equals(checkStudentID))
						{
							System.out.println("Sorry, student ID already exists\n");
							studentID = null;
							exist = true;
							break;
						}
						else
							exist = false;
					}
					else if (selection == 4)
					{
						// if ID exists, do not continue
						if (printstudentID.equals(checkStudentID))
						{

							exist = true;
							break;
						}
						else
							exist = false;

					}

				}

			}

			bufferedReader.close();
			/*
			 * if (selection == 1 && exist == false) {
			 * System.out.println("Sorry, student ID does not exist\n"); }
			 */

			// close buffered reader

		} catch (FileNotFoundException ex)
		{
			System.out.println("Unable to open file '" + fileName + "'");
		}
		// catch exception if IOException
		catch (IOException ex)
		{
			System.out.println("Error reading file '" + fileName + "'");

		}
		return exist;

	}

	public boolean gradeStudent()
	{
		String[] courseIDs = { "001", "002", "003", "004", "005" };
		String checkGrade = "";
		@SuppressWarnings("resource")
		Scanner scan2 = new Scanner(System.in);
		do
		{

			System.out.println("Please enter student ID to grade");
			checkGrade = scan2.nextLine();
			viewStudentDetails(checkGrade, 4);
			if (exist == false)
				System.out.println("Student ID does not exist, please try again\n");
		} while (exist == false);

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
			String cost = "";
			boolean exist = false;
			do
			{
				System.out.println("Enter Course ID to see details");
				courseIdDetails = scan2.nextLine();
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

			List<String> list = new ArrayList<String>();

			while ((lineInput = bufferedReader.readLine()) != null)
			{
				String courseCheck = "";
				if (lineInput.length() > 0)
				{
					list.add(lineInput);
					// array to split text on line to get user name and password
					String[] checkCredentials = lineInput.split(" }");
					String[] individualRecord = checkCredentials[0].split(",");
					courseCheck = individualRecord[0].replace("{", "");
					studentID = individualRecord[1];
					cost = individualRecord[2];

					// if correct course, add total amount, and
					// print out student ID and cost
					if ((checkGrade.equals(studentID) && courseIdDetails.equals(courseCheck)))
					{

						System.out.println("Enter grade for course");
						System.out.println("1 - HD\n2 - DI\n3 - C\n4 - Fail\n");
						int selection = scan2.nextInt();
						String givenGrade = "";
						switch (selection)
						{

						case 1:
							givenGrade = "HD";
							break;
						case 2:
							givenGrade = "DI";
							break;
						case 3:
							givenGrade = "C";
							break;
						case 4:
							givenGrade = "FAIL";
							break;

						default:
							System.out.println("Invaild Option!!!");
							break;
						}
						writeUsingFileWriter(courseCheck, studentID, cost, givenGrade);
						break;

					}
				}
			}

			System.out.println();

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

	public void writeUsingFileWriter(String courseEnrolID, String enrolID, String price, String grade)
			throws FileNotFoundException
	{
		// find users current directory
		File file = new File(System.getProperty("user.dir"));
		// then access the course details text file
		String path = file.getAbsolutePath() + "\\src\\student_course_details.txt";
		File created = new File(path);

		try
		{
			if (created.exists() == false)
			{
				System.out.println("We had to make a new file.");
				created.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(created, true));

			if (!courseEnrolID.isEmpty())
			{
				System.out.println("Record Successfully Updated!!");
				out.append("{" + courseEnrolID + "," + enrolID + ", " + price + "," + grade + ",}\n");
				out.close();
			}
		} catch (IOException e)
		{
			System.out.println("COULD NOT UPDATE RECORD!!");
		}

	}

	public void editDetails()
	{

		boolean exist = false;
		try
		{
			BufferedReader file = new BufferedReader(new FileReader("src/studentDetails.txt"));
			String line;
			String input = "";
			@SuppressWarnings("resource")
			Scanner scan = new Scanner(System.in);
			System.out.println("Enter student ID to change:");
			String checkStudentID = scan.nextLine();

			while ((line = file.readLine()) != null)
			{
				// check for line with course ID
				if (line.contains(checkStudentID))
				{
					// read text file and assign line to variables for rewriting
					String[] checkCredentials = line.split("}");
					String[] jobSearch = checkCredentials[0].split(",");
					studentID = jobSearch[0].replace("{", "");

					if (checkStudentID.equals(studentID))
					{
						firstName = jobSearch[1];
						lastName = jobSearch[2];
						Scanner scan3 = new Scanner(System.in);
						System.out.println("Which details would you like to change?");
						System.out.println("1 - Address\n2 - Email\n3 - Phone");
						int change = scan3.nextInt();
						scan3.nextLine();

						switch (change)
						{

						// Scanner scan4 = new Scanner(System.in);
						case 1:
							System.out.println("Enter new Address:");
							String newAddress = scan3.nextLine();
							address = newAddress;
							email = jobSearch[4];
							phone = jobSearch[5];
							line = "";
							exist = true;
							System.out.println("New address successfully updated\n");
							break;

						case 2:
							System.out.println("Enter new Email:");
							String newEmail = scan3.nextLine();
							email = newEmail;
							address = jobSearch[3];
							phone = jobSearch[5];
							line = "";
							exist = true;
							System.out.println("New email address successfully updated\n");
							break;

						case 3:
							System.out.println("Enter new Phone Number:");
							String newPhone = scan3.nextLine();
							phone = newPhone;
							address = jobSearch[3];
							email = jobSearch[4];
							line = "";
							exist = true;
							System.out.println("New Phone number successfully updated\n");
							break;

						default:
							System.out.println("Invalid Option!!!");
							break;
						}
					}

				}

				input += line + '\n';

			}
			if (!studentID.equals(checkStudentID))
			{
				System.out.println("Student does not exist!!!");
				exist = false;
				file.close();

			}

			if (exist == true)
			{
				// assign where to write new variables
				FileOutputStream File = new FileOutputStream("src/studentDetails.txt");
				File.write(input.getBytes());
				writeUsingFileWriter(studentID, firstName, lastName, address, email, phone);
				file.close();
				File.close();

			}

		} catch (Exception e)
		{
			System.out.println("Problem reading file.");

		}

	}

}
