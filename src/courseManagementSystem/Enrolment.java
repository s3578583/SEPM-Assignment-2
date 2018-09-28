package courseManagementSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Enrolment {
	private boolean exceeded = false;
	private boolean exist = false;
	private double coursePrice;

	public void enrol(String courseEnrolID, String enrolID, String price) {
		try {
			BufferedReader file = new BufferedReader(new FileReader("src/student_course_details.txt"));
			String line;
			// String input = "";
			// int numStudents = 0;

			while ((line = file.readLine()) != null) {
				if (line.length() > 0) {
					// check for line with course ID
					if (line.contains(courseEnrolID)) {
						// read text file and assign line to variables for
						// rewriting
						String[] checkCredentials = line.split("}");
						String[] jobSearch = checkCredentials[0].split(",");
						String checkID = jobSearch[1];
						line = "";

						if (enrolID.equalsIgnoreCase(checkID)) {
							System.out.println("Student is already enrolled in this subject");
							exist = true;
							break;
						}
						// input += line + '\n';
					}
				}

			}

			if (exist == false) {
				// assign where to write new variables
				writeUsingFileWriter(courseEnrolID, enrolID, price);
				file.close();
			}
		} catch (Exception e) {
			System.out.println("Problem reading file.");
		}
	}

	public void writeUsingFileWriter(String courseEnrolID, String enrolID, String price) throws FileNotFoundException {
		// find users current directory
		File file = new File(System.getProperty("user.dir"));
		// then access the course details text file
		String path = file.getAbsolutePath() + "\\src\\student_course_details.txt";
		File created = new File(path);

		try {
			if (created.exists() == false) {
				System.out.println("We had to make a new file.");
				created.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(created, true));

			if (!courseEnrolID.isEmpty()) {
				System.out.println("Record Successfully Updated!!");
				out.append("{" + courseEnrolID + "," + enrolID + ", " + price + ",}\n");
				out.close();
			}
		} catch (IOException e) {
			System.out.println("COULD NOT UPDATE RECORD!!");
		}

	}

	public void getCourseNumber(String checkCourseID) {

		@SuppressWarnings("resource")
		File file = new File(System.getProperty("user.dir"));
		String path = file.getAbsolutePath() + "\\src\\student_course_details.txt";
		String fileName = path;
		try {
			// file reader to read the fileName variable above
			FileReader filereader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(filereader);
			// variable for lines in the file
			String lineInput;
			int totalCourseNumber = 0;
			int maxNum = 0;
			if ((checkCourseID.equals("001")) || (checkCourseID.equals("002")) || (checkCourseID.equals("003")))
				maxNum = 10;
			else
				maxNum = 15;

			String courseID = "";
			List<String> list = new ArrayList<String>();

			while ((lineInput = bufferedReader.readLine()) != null) {
				if (lineInput.length() > 0) {
					list.add(lineInput);
					// array to split text on line to get user name and password
					String[] checkCredentials = lineInput.split(" }");
					String[] individualRecord = checkCredentials[0].split(",");
					courseID = individualRecord[0].replace("{", "");

					if (courseID.equals(checkCourseID))
						totalCourseNumber++;

					if (totalCourseNumber > maxNum)
						exceeded = true;

				}

			}

			bufferedReader.close();

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		}
		// catch exception if IOException
		catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public void setCoursePrice(String courseID, String studentID) {
		File file = new File(System.getProperty("user.dir"));
		String path = file.getAbsolutePath() + "\\src\\courseDetails.txt";
		String fileName = path;
		String checkID = null;
		try {
			// file reader to read the fileName variable above
			FileReader filereader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(filereader);
			// variable for lines in the file
			String lineInput;
			// reader to reader through text file
			List<String> list = new ArrayList<String>();
			while ((lineInput = bufferedReader.readLine()) != null) {
				if (lineInput.length() > 0) {
					list.add(lineInput);
					// array to split text on line to get user name and password
					String[] checkCredentials = lineInput.split(" }");
					String[] individualRecord = checkCredentials[0].split(",");
					checkID = individualRecord[0].replace("{", "");

					Double cost = Double.parseDouble(individualRecord[3]);
					// get course fee and set value
					if (checkID.equals(courseID)) {
						coursePrice = cost;
					}
				}
			}
			// get course price from course ID entered
			getCourseNumber(courseID);
			if (exceeded == false) {
				@SuppressWarnings("resource")
				Scanner scan2 = new Scanner(System.in);
				System.out.println("Are they an existing student?:");
				System.out.print("1. Yes \n2. No\n");
				// if existing student, give discount
				int input = scan2.nextInt();
				if (input == 1) {
					coursePrice = (coursePrice / 100) * 80;
				}
				String tempCost = String.valueOf(coursePrice);
				// write to file
				enrol(courseID, studentID, tempCost);
				// close reader
				bufferedReader.close();
			} else
				System.out.println("Unable to add student, max number has been exceeded");

		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		}
		// catch exception if IOException
		catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}
	}

	public void withdraw() {

		File file = new File(System.getProperty("user.dir"));
		String path = file.getAbsolutePath() + "\\src\\student_course_details.txt";
		String fileName = path;
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter student ID to withdraw");
		String checkID = scan.nextLine();
		System.out.println("Enter course ID to withdraw student from");
		String checkCourseID = scan.nextLine();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));

			// String buffer to store contents of the file
			StringBuffer sb = new StringBuffer("");

			String line;
			String courseID = "", studentID = "", cost = "";
			String courseID2 = "", studentID2 = "", cost2 = "";

			while ((line = br.readLine()) != null) {

				if (line.length() > 0) {

					// array to split text on line to get user name and password
					String[] checkCredentials = line.split("}");
					String[] jobSearch = checkCredentials[0].split(",");
					courseID = jobSearch[0].replace("{", "");
					studentID = jobSearch[1];
					cost = jobSearch[2];

					// Store each valid line in the string buffer
					if (!(studentID.equals(checkID) && courseID.equals(checkCourseID))) {
						sb.append(line + "\n");
					}
					else if ((studentID.equals(checkID) && courseID.equals(checkCourseID))) {
						courseID2 = courseID;
						studentID2 = studentID;
						cost2 = cost;
					}
				}
			}refund(courseID2,studentID2,cost2);
			br.close();

			FileWriter fw = new FileWriter(new File(fileName));
			// Write entire string buffer into the file
			fw.write(sb.toString());
			System.out.println("Record successfully removed!!!\n");
			fw.close();
		} catch (Exception e) {
			System.out.println("Something went horribly wrong: " + e.getMessage());
		}
	}
	public void refund(String courseID, String enrolID, String price) throws FileNotFoundException {
		Scanner scan = new Scanner (System.in);
		String refundType = "";
		System.out.print("Get refund?\n1.Yes\n2.No\n");
		int ans = scan.nextInt();
		if (ans == 1) {
			System.out.print("Refund type: \n1.Cash\n2.Card\n");
			int type = scan.nextInt();
			if (type == 1) {
				refundType = "Cash";
			} else if (type == 2) {
				System.out.print("Payment method: \n1.MasterCard\n2.Visa\n3.American Express\n");
				int type2 = scan.nextInt();
				if (type2 == 1) {
					refundType = "Card - Mastercard";
				} else if (type2 == 2) {
					refundType = "Card - Visa";
				} else if (type2 == 3) {
					refundType = "Card - AMEX";
				}
			}
			writeUsingFileWriter2(courseID, enrolID, price, refundType);
		} 
	}
	public void writeUsingFileWriter2(String courseEnrolID, String enrolID, String price, String type)
			throws FileNotFoundException {
		// find users current directory
		File file = new File(System.getProperty("user.dir"));
		// then access the course details text file
		String path = file.getAbsolutePath() + "\\src\\refund.txt";
		File created = new File(path);

		try {
			if (created.exists() == false) {
				System.out.println("We had to make a new file.");
				created.createNewFile();
			}
			PrintWriter out = new PrintWriter(new FileWriter(created, true));

			if (!courseEnrolID.isEmpty()) {
				System.out.println("Refund success!!");
				out.append("{" + courseEnrolID + "," + enrolID + ", " + price + "," + type + ",}\n");
				out.close();
			}
		} catch (IOException e) {
			System.out.println("COULD NOT UPDATE RECORD!!");
		}

	}
}