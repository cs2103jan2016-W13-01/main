package logic;

public interface Command {
	
	public CommandType getType();
	
	public String execute();
	
	public String undo();
	
}
