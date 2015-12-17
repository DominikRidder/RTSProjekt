package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.notification.Failure;
import org.junit.runner.*;

import dataManagement.ImageManager;

public class ManagerTest {
	public static void main(String[] agrs) {
		Result rc = new Result();

		rc = JUnitCore.runClasses(ManagerTest.class);

		System.out.printf("%d tests were executed, %d of them with failures\n",
				rc.getRunCount(), rc.getFailureCount());

		if (!rc.wasSuccessful()) {
			List<Failure> fList = rc.getFailures();
			for (Failure f : fList) {
				System.out.println(f.getTestHeader());
				System.out.println(f.getMessage());
			}// end of for (Failure f: fList)
		}// end of if (!rc.wasSuccessful)
	}// end of method ”main(String[] args)”

	@Test
	public void testRelativNames() {
		File dir = new File("data2/");
		File empty = new File("data2/EmptyDir");
		
		dir.mkdir();
		empty.mkdir();
		
		File unit = new File("data2/Unit.png");
		try {
			File tmp = File.createTempFile("Unit", "png", dir);
			tmp.renameTo(unit);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String datadir = dir.getName();
		String datatypes[] = { ".png" };

//		HashMap<String, String> result = ImageManager.loadRelativNames(datadir,
//				datatypes);
		
//		assertTrue(result.values().size() == 1);
//		assertEquals(result.get("Unit.png"), (unit.getAbsolutePath()));
	
		unit.delete();
		empty.delete();
		dir.delete();
	}
}
