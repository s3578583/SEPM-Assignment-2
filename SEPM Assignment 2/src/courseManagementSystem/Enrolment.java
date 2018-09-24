package courseManagementSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Enrolment
{

	private boolean exist = false;

	public void enrol(String courseEnrolID, String enrolID)
	{

		try
		{
			BufferedReader file = new BufferedReader(new FileReader("src/student_course_details.txt"));
			String line;
			String input = "";

			while ((line = file.readLine()) != null)
			{
				if (line.length() > 0)
				{
					// check for line with course ID
					if (line.contains(courseEnrolID))
					{
						// read text file and assign line to variables for
						// rewriting
						String[] checkCredentials = line.split("}");
						String[] jobSearch = checkCredentials[0].split(",");
						String checkID = jobSearch[1];
						line = "";

						if (enrolID.equalsIgnoreCase(checkID))
						{
							System.out.println("Student is already enrolled in this subject");
							exist = true;
							break;
						}

					}
					input += line + '\n';
				}
			}

			if (exist == false)
			{
				// assign where to write new variables
				FileOutputStream File = new FileOutputStream("src/student_course_details.txt");
				File.write(input.getBytes());
				writeUsingFileWriter(courseEnrolID, enrolID);
				file.close();
				File.close();
			}

		} catch (Exception e)
		{
			System.out.println("Problem reading file.");
		}
	}

	public void writeUsingFileWriter(String courseEnrolID, String enrolID) throws FileNotFoundException
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
			System.out.println("Record Successfully Updated!!");
			out.append("{" + courseEnrolID + "," + enrolID + "}\n");
			out.close();
		} catch (IOException e)
		{
			System.out.println("COULD NOT UPDATE RECORD!!");
		}

	}

}
