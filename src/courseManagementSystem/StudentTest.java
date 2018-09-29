package courseManagementSystem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentTest
{
	
		private Student	test_model = new Student("s111", "Test", "Case", "1 Test Lane", "test@gmail.com", "000 0000 000");
			
		
	
		@Test
		public void testHireStaff()
		{
			assertTrue(test_model.addStudent());
		}

		@Test
		public void viewStudentDetailsFail()
		{
			assertEquals(false,test_model.viewStudentDetails("s1101",1));
		}
		
		@Test
		public void viewStudentDetailsPass()
		{
			assertEquals(true,test_model.viewStudentDetails("s001",1));
		}
		
		
	
		@Test
		public void viewStudentDetails2Pass()
		{
			assertTrue(test_model.viewStudentDetails("s001",2));
		}
		
		
		@Test
		public void viewStudentDetails2Fail()
		{
			assertEquals(false,test_model.viewStudentDetails("s1101",2));
		}
	
	
		
		
		
}

