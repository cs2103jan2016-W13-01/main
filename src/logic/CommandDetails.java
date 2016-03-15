package logic;

public class CommandDetails {
	private CommandType cmd;
	private Task task;
	private int inputNum;

	public CommandDetails(CommandType cmd, Task task, int taskNumber) {
		
		this.cmd = cmd;
		this.task = task;
		this.inputNum = taskNumber;
	}
	
	public CommandDetails(CommandType cmd, Task task){
		this.cmd=cmd;
		this.task=task;
		inputNum = 0;
	}
	
	public CommandDetails(CommandType cmd, int taskNumber) {
		
		this.cmd = cmd;
		this.task = null;
		this.inputNum = taskNumber;
	}
	
	public CommandType getCommand() {
		return cmd;
	}
	
	public Task getTask() {
		return task;
	}

	public void setInputNum(int inputNum) {
		this.inputNum = inputNum;
	}
	public int getInputNum() {
		return inputNum;
	}

	
}
