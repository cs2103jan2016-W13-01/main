package logic;

public class CommandDetails {
	private CommandType cmd;
	private Task task;

	private int taskNumber;


	public CommandDetails(CommandType cmd, Task task, int taskNumber) {
		
		this.cmd = cmd;
		this.task = task;
		this.taskNumber = taskNumber;
	}
	
	public CommandDetails(CommandType cmd, Task task){

		this.cmd=cmd;
		this.task=task;
		this.taskNumber = 0;
	}
	
	public CommandDetails(CommandType cmd, int taskNumber) {
		
		this.cmd = cmd;
		this.task = null;
		this.taskNumber = taskNumber;
	}
	
	public CommandType getCommand() {
		return cmd;
	}
	
	public Task getTask() {
		return task;
	}

	public int getTaskNumber() {
		return taskNumber;
	}

}
