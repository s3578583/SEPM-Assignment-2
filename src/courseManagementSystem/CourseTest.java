package courseManagementSystem;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;

class CourseTest
{

	private Course test_model = new Course("010", "Test Course", 10, 1000);

	@Test
	public void printStudentCourseDetails()
	{
		assertEquals(true, test_model.printStudentCourseDetails("s001"));
	}

	@Test
	public void printStudentCourseDetailsFail()
	{
		assertEquals(false, test_model.printStudentCourseDetails("s005"));
	}

	@Test
	public void calculate()
	{
		assertEquals(true, test_model.calculate());
	}

	@Test
	public void deleteLineFalse()
	{

		assertEquals(false, test_model.deleteLine());

	}

	@Test
	public void deleteLineTrue()
	{

		assertEquals(true, test_model.deleteLine());

	}

	@Test
	public void printCourseDetailsTrue()
	{

		assertEquals(true, test_model.printCourseDetails());

	}

}
