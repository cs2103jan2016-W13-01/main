package logic;

public class CommandDetails {
	private CommandType cmd;
	private Task task;
	
	public CommandDetails(CommandType cmd,Task task){
		
		this.cmd=cmd;
		this.task=task;
	}
	
	public CommandType getCommand() {
		return cmd;
	}
	
	public Task getTask() {
		return task;
	}
}
