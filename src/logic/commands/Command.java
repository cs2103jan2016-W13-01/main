package logic.commands;

/* @@author A0112184R
 * This interface represents the commands entered by the user
 */
public interface Command {
	
	public CommandType getType();
	
	public String execute();
	
	public String undo();
	
}
