package logic;

/* @@author A0112184R
 * This class tests the delete feature of Logic
 */
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import logic.commands.Command;
import logic.commands.CommandAdd;
import logic.commands.CommandDelete;
import logic.tasks.Deadline;
import storage.Storage;

public class TestDelete {
	
	@Test
	public void testDelete() {
		Deadline task = new Deadline("go tutorial", new Date());
		Command commandAdd = new CommandAdd(task);
		TaskProcessor.initialize();
		Storage.clearAllTasks();
		TaskProcessor.executeCommand(commandAdd);
		Command commandDelete = new CommandDelete(1);
		ArrayList<String> taskList = new ArrayList<String>();
		assertEquals(taskList, TaskProcessor.executeCommand(commandDelete));
	}
}
