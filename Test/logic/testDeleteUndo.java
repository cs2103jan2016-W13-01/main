package logic;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import Storage.Storage;
import logic.commands.Command;
import logic.commands.CommandAdd;
import logic.commands.CommandDelete;
import logic.commands.CommandUndo;

public class testDeleteUndo {
	
	@Test
	public void testAdd() {
		Task task = new Task("go tutorial", new Date());
		Command commandAdd = new CommandAdd(task);
		TaskProcessor.initialize();
		Storage.clearAllTasks();
		TaskProcessor.executeCommand(commandAdd);
		Command commandDelete = new CommandDelete(1);
		TaskProcessor.executeCommand(commandDelete);
		Command commandUndo = new CommandUndo();
		ArrayList<String> taskList = new ArrayList<String>();
		taskList.add(task.toString());
		Response undoResponse = new Response("Command undone successfully", taskList);
		assertEquals(undoResponse, TaskProcessor.executeCommand(commandUndo));
	}
}
