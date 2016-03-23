package logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import Storage.Storage;
import logic.commands.Command;
import logic.commands.CommandAdd;
import logic.commands.CommandDelete;

public class TestDelete {
	
	@Test
	public void testDelete() {
		Task task = new Task("go tutorial", new Date());
		Command commandAdd = new CommandAdd(task);
		TaskProcessor.initialize();
		Storage.clearAllTasks();
		TaskProcessor.executeCommand(commandAdd);
		Command commandDelete = new CommandDelete(1);
		ArrayList<String> taskList = new ArrayList<String>();
		assertEquals(taskList, TaskProcessor.executeCommand(commandDelete));
	}
}
