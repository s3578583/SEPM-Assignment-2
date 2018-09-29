package courseManagementSystem;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class EnrolmentTest
{

	private Enrolment test_model = new Enrolment();
	
	@Test
	public void enrolStudentPass()
	{
		assertEquals(true, test_model.enrol("003", "s003", "300"));
	}
	
	@Test
	public void enrolStudentFail()
	{
		assertEquals(true, test_model.enrol("003", "s003", "300"));
	}
	
	
	@Test
	public void setCoursePricePass() {
		assertEquals(true, test_model.setCoursePrice("001", "003"));
	}
	
	@Test
	public void setCoursePriceFail() {
		assertEquals(true, test_model.setCoursePrice("100", "100"));
	}
	
	@Test
	public void withdrawPass() {
		assertEquals(true, test_model.withdraw());
	}
	
	@Test
	public void withdrawFail() {
		assertEquals(true, test_model.withdraw());
	}
	
	

}
