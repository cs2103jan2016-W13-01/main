
package logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import Storage.Storage;
import logic.commands.Command;
import logic.commands.CommandAdd;

/**
 * @author Bao Linh
 * This class tests add feature of the Logic
 */
public class TestAdd {
	
	@Test
	public void testAdd() {
		Task task = new Task("go tutorial", new Date());
		Command commandAdd = new CommandAdd(task);
		TaskProcessor.initialize();
		Storage.clearAllTasks();
		ArrayList<String> taskList = new ArrayList<String>();
		taskList.add(task.toString());
		Response addResponse = new Response("Task added successfully", taskList);
		assertEquals(addResponse.toString(), TaskProcessor.executeCommand(commandAdd).toString());
	}
	
}