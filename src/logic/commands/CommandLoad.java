package logic.commands;

import java.io.IOException;

import logic.ExecutedCommands;
import storage.StorageController;

/* @@author A0112184R
 *
 */
public class CommandLoad implements Command {
	
	private static final String MESSAGE_LOADED = "Data loaded from location: %s";
	private static final String MESSAGE_UNDONE = "Storage location set back to: %s";
	private static final String MESSAGE_UNDO_ERROR = "Sorry, failed to undo action: load %s";
	private static final String MESSAGE_LOAD_ERROR = "Sorry, failed to load data at location: %s";
	private String newPath;
	private String oldPath;

	public CommandType getType() {
		return CommandType.LOAD;
	}
	
	public CommandLoad(String path) {
		newPath = path;
	}
	
	public String execute() {
		try {
			oldPath = StorageController.getPath();
			StorageController.load(newPath);
			ExecutedCommands.addCommand(this);
			return String.format(MESSAGE_LOADED, newPath);
		} catch (IOException e) {
			e.printStackTrace();
			return String.format(MESSAGE_LOAD_ERROR, newPath);
		}
	}
	
	public String undo() {
		try {
			StorageController.load(oldPath);
			return String.format(MESSAGE_UNDONE, oldPath);
		} catch (IOException e) {
			e.printStackTrace();
			return String.format(MESSAGE_UNDO_ERROR, newPath);
		}
	}
}
