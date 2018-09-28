package courseManagementSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CMSDriver {

	public static void main(String[] args) throws Exception {
		// login boolean value
		boolean login = false;
		do {
			// scanner to get user input

			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			System.out.print("Please enter your username:");
			// variable for user name
			String userName = input.nextLine();
			System.out.print("Please enter your password:");
			// variable for password
			String password = input.nextLine();
			String typeOfUser = "";

			// variable for file location
			// please change the path to your specific path of the users.txt
			// file
			File file = new File(System.getProperty("user.dir"));

			String path = file.getAbsolutePath() + "\\src\\users.txt";

			String fileName = path;

			try {
				// file reader to read the fileName variable above
				FileReader filereader = new FileReader(fileName);

				BufferedReader bufferedReader = new BufferedReader(filereader);
				// variable for lines in the file
				String lineInput;
				// set user to null once they have logged out
				String user = null;
				// set password to null once they have logged out
				String pass = null;

				List<String> list = new ArrayList<String>();
				// read through document while there is a new line in the file
				while ((lineInput = bufferedReader.readLine()) != null) {
					list.add(lineInput);
					// array to split text on line to get user name and password
					String[] checkCredentials = lineInput.split(" ");
					user = checkCredentials[0];
					pass = checkCredentials[1];

					// condition to check if user exists in the file
					if (userName.equals(user) && password.equals(pass)) {
						System.out.println("Successfully logged in as: " + user);
						typeOfUser = checkCredentials[2];
						login = true;
						break;
					}

				}
				// not equal condition to display message to user
				if (!userName.equals(user) && !password.equals(pass)) {
					System.out.println("\nIncorrect password for " + userName + " user please try again");

				}
				// close buffered reader
				bufferedReader.close();

				// catch exception if file not found
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to open file '" + fileName + "'");
			}
			// catch exception if IOException
			catch (IOException ex) {
				System.out.println("Error reading file '" + fileName + "'");

			}
			// variable to get user selection
			int selection;

			// menu options for an Course manager user
			if (typeOfUser.equals("course") && login == true) {
				do {
					System.out.println("\nPlease enter your selection:");
					System.out.println("\n1 - Add new Student:");
					System.out.println("2 - View a Students details:");
					System.out.println("3 - Enrol a Student in a Course:");
					System.out.println("4 - View Course Details:");
					System.out.println("5 - Withdraw a student from course:");
					System.out.println("6 - View profits:");
					System.out.println("\n0 - Logout");

					selection = input.nextInt();

					// switch, depending on option do certain action
					switch (selection) {

					case 1:

						Student std1 = new Student("", "", "", "", "", "");
						std1.addStudent();
						break;

					case 2:

						Student std2 = new Student("", "", "", "", "", "");
						Scanner scan = new Scanner(System.in);
						System.out.print("Please enter student ID to search:");
						String checkID = scan.nextLine();
						// pass student ID and specific int value
						std2.viewStudentDetails(checkID, 1);
						break;

					case 3:
						Student std3 = new Student("", "", "", "", "", "");
						@SuppressWarnings("resource")
						Scanner scan1 = new Scanner(System.in);
						System.out.println("Please enter student ID to enrol");
						String enrolID = scan1.nextLine();
						// pass student ID and specific int value
						std3.viewStudentDetails(enrolID, 2);
						break;

					case 4:
						Course crs = new Course("", "", 0, 2);
						crs.viewCourseDetails();
						break;

					case 5:
						Enrolment withdraw = new Enrolment();
						withdraw.withdraw();
						break;
					case 6:
						Course crs1 = new Course("", "", 0, 2);
						crs1.calculate();
						break;
					default:
						System.out.println("Invaild Option!!!");
						break;

					case 0:
						// logout the user and result values
						userName = null;
						password = null;
						System.out.print("You have been logged out\n");
						// set variable to logout
						login = false;

					}

				} while (selection != 0);
			}

		} while (login == false);

	}

}
