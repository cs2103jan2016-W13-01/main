package logic.commands;

public interface Command {
	
	public CommandType getType();
	
	public String execute();
	
	public String undo();
	
}
