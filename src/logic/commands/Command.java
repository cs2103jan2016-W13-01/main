package logic.commands;

/* @@author a0112184r
 * This interface represents the commands entered by the user
 */
public interface Command {
	
	public CommandType getType();
	
	public String execute();
	
	public String undo();
	
}
