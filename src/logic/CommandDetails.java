package logic;

public class CommandDetails {
	private CommandType cmd;
	private Task task;
<<<<<<< HEAD

	private int taskNumber;

=======
	private int inputNum;
>>>>>>> a4fb3a2bda54b2d0b98ca12255f9b79d62744ba8

	public CommandDetails(CommandType cmd, Task task, int taskNumber) {
		
		this.cmd = cmd;
		this.task = task;
		this.inputNum = taskNumber;
	}
	
<<<<<<< HEAD

	public CommandDetails(CommandType cmd, Task task){
		
=======
	public CommandDetails(CommandType cmd, Task task){
>>>>>>> a4fb3a2bda54b2d0b98ca12255f9b79d62744ba8
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
<<<<<<< HEAD
	

	public int getTaskNumber() {
		return taskNumber;
	}
=======

	public void setInputNum(int inputNum) {
		this.inputNum = inputNum;
	}
	public int getInputNum() {
		return inputNum;
	}

>>>>>>> a4fb3a2bda54b2d0b98ca12255f9b79d62744ba8
	
}
