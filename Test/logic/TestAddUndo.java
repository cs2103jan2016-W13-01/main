package logic;

/* @@author a0112184r
 * this class tests the undo method of Add
 */
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import Storage.Storage;
import logic.commands.Command;
import logic.commands.CommandAdd;
import logic.commands.CommandUndo;

public class TestAddUndo {
	
	@Test
	public void testAdd() {
		Task task = new Task("go tutorial", new Date());
		Command commandAdd = new CommandAdd(task);
		TaskProcessor.initialize();
		Storage.clearAllTasks();
		TaskProcessor.executeCommand(commandAdd);
		Command commandUndo = new CommandUndo();
		ArrayList<String> taskList = new ArrayList<String>();
		Response undoResponse = new Response("Command undone successfully", taskList);
		assertEquals(undoResponse, TaskProcessor.executeCommand(commandUndo));
	}
}
