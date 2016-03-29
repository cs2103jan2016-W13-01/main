package logic.commands;

public class CommandHelp implements Command{
	public CommandType getType() {
		return CommandType.HELP;
	}
	
	public String execute() {
		return null;
	}
	
	public String undo() {
		return null;
	}

}
